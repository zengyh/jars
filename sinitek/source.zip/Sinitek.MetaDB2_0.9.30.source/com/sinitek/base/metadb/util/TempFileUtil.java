// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempFileUtil.java

package com.sinitek.base.metadb.util;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

public class TempFileUtil
{
    private static class CheckTask extends TimerTask
    {

        public void run()
        {
            MetaDBLoggerFactory.LOGGER_CORE.debug("\u5F00\u59CB\u68C0\u67E5\u4E34\u65F6\u6587\u4EF6\uFF0C\u51C6\u5907\u5220\u9664\u65E0\u7528\u6587\u4EF6");
            int deleteCount = 0;
            Stack stack = new Stack();
            stack.push(baseFile);
            do
            {
                if(stack.isEmpty())
                    break;
                File file = (File)stack.pop();
                if(file.isDirectory())
                {
                    File subFiles[] = file.listFiles();
                    int i = 0;
                    while(i < subFiles.length) 
                    {
                        stack.push(subFiles[i]);
                        i++;
                    }
                } else
                if(!filesMap.containsKey(file))
                {
                    if(MetaDBLoggerFactory.LOGGER_CORE.isDebugEnabled())
                        MetaDBLoggerFactory.LOGGER_CORE.debug((new StringBuilder()).append("\u51C6\u5907\u5220\u9664\u65E0\u7528\u7684\u4E34\u65F6\u6587\u4EF6[").append(file.getPath()).append("]").toString());
                    boolean deleteflag = file.delete();
                    if(deleteflag && MetaDBLoggerFactory.LOGGER_CORE.isDebugEnabled())
                    {
                        deleteCount++;
                        MetaDBLoggerFactory.LOGGER_CORE.debug((new StringBuilder()).append("\u5220\u9664\u65E0\u7528\u7684\u4E34\u65F6\u6587\u4EF6[").append(file.getPath()).append("]\u6210\u529F").toString());
                    } else
                    if(MetaDBLoggerFactory.LOGGER_CORE.isDebugEnabled())
                        MetaDBLoggerFactory.LOGGER_CORE.debug((new StringBuilder()).append("\u5220\u9664\u65E0\u7528\u7684\u4E34\u65F6\u6587\u4EF6[").append(file.getPath()).append("]\u5931\u8D25").toString());
                }
            } while(true);
            if(MetaDBLoggerFactory.LOGGER_CORE.isDebugEnabled())
                MetaDBLoggerFactory.LOGGER_CORE.debug((new StringBuilder()).append("\u68C0\u67E5\u4E34\u65F6\u6587\u4EF6\u5B8C\u6210\uFF0C\u5171\u5220\u9664[").append(deleteCount).append("]\u4E2A\u6587\u4EF6").toString());
        }

        private File baseFile;
        private Map filesMap;

        private CheckTask(File baseFile, Map filesMap)
        {
            this.baseFile = baseFile;
            this.filesMap = filesMap;
        }

    }


    public TempFileUtil(String fileBasePath)
        throws MetaDBException
    {
        filesMap = new WeakHashMap();
        LOGGER_CONFIG.debug((new StringBuilder()).append("\u4E34\u65F6\u6587\u4EF6\u6839\u76EE\u5F55[").append(fileBasePath).append("]").toString());
        File basePath = new File(fileBasePath);
        if(!basePath.exists())
        {
            LOGGER_CONFIG.debug("\u4E34\u65F6\u6587\u4EF6\u6839\u76EE\u5F55\u4E0D\u5B58\u5728\uFF0C\u51C6\u5907\u521B\u5EFA\u8BE5\u76EE\u5F55");
            if(!basePath.mkdirs())
                throw new MetaDBConfigException("0034", new Object[] {
                    fileBasePath
                });
            LOGGER_CONFIG.debug("\u4E34\u65F6\u6587\u4EF6\u6839\u76EE\u5F55\u521B\u5EFA\u6210\u529F");
        }
        if(!basePath.isDirectory())
        {
            throw new MetaDBConfigException("0035", new Object[] {
                fileBasePath
            });
        } else
        {
            this.fileBasePath = fileBasePath;
            return;
        }
    }

    public String createTempFileName(String entityName)
        throws MetaDBException
    {
        String dir = genTempDir(entityName);
        File fDir = new File(dir);
        if(!fDir.exists() && !fDir.mkdirs())
        {
            throw new MetaDBCoreException("0010", new Object[] {
                dir
            });
        } else
        {
            String fileName = genTempFileName();
            String f = createUniqueFile((new StringBuilder()).append(dir).append(File.separator).append(fileName).toString()).getAbsolutePath();
            return f;
        }
    }

    public File createTempFile(String fileName)
    {
        File file = new File(fileName);
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException e)
            {
                throw new MetaDBCoreException("0011", e, new Object[] {
                    file.getPath()
                });
            }
            if(!file.exists())
                throw new MetaDBCoreException("0011", new Object[] {
                    file.getPath()
                });
        }
        filesMap.put(file, file.getPath());
        return file;
    }

    public void startCheck(int checkTime)
    {
        stopCheck();
        checkTimer = new Timer(true);
        checkTimer.schedule(new CheckTask(new File(fileBasePath), filesMap), 10000L, checkTime * 1000);
    }

    public void stopCheck()
    {
        if(checkTimer != null)
            checkTimer.cancel();
    }

    private String genTempFileName()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String fileName = (new StringBuilder()).append(sdf.format(new Date())).append("_").append(getPID()).append("_").append(Thread.currentThread().getId()).toString();
        fileName = (new StringBuilder()).append(fileName).append(".tmp").toString();
        return fileName;
    }

    private String getPID()
    {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return pid;
    }

    private static File createUniqueFile(String targetFile)
    {
        File _file = new File(targetFile);
        File _dir = _file.getParentFile();
        if(!_dir.exists())
            _dir.mkdirs();
        boolean fileexists = _file.exists();
        if(!fileexists)
            try
            {
                _file.createNewFile();
            }
            catch(IOException e) { }
        if(fileexists)
        {
            int _no = 2;
            String _fileName = _file.getAbsolutePath();
            int p = _fileName.lastIndexOf(".");
            String _prefix = p >= 0 ? _fileName.substring(0, p) : _fileName;
            String _suffix = p >= 0 ? _fileName.substring(p) : "";
            while(_file.exists()) 
            {
                _fileName = (new StringBuilder()).append(_prefix).append("_").append(_no).append(_suffix).toString();
                _file = new File(_fileName);
                _no++;
            }
        }
        return _file;
    }

    private String genTempDir(String entityName)
    {
        return (new StringBuilder()).append(fileBasePath).append(File.separator).append(entityName).toString();
    }

    private static final Logger LOGGER_CONFIG;
    private String fileBasePath;
    private Map filesMap;
    private Timer checkTimer;

    static 
    {
        LOGGER_CONFIG = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
