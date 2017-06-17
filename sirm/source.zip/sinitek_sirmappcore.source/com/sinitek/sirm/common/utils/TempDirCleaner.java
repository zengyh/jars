// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempDirCleaner.java

package com.sinitek.sirm.common.utils;

import com.sinitek.base.starter.IStarter;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.io.File;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            NumberTool, IOUtil

public class TempDirCleaner
    implements IStarter, Runnable
{

    public TempDirCleaner()
    {
        logHomes = new ArrayList();
        logExpiredDays = 30;
    }

    public void start(Properties properties)
        throws Exception
    {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
        String strLogHomes = properties.getProperty("loghomes");
        if(StringUtils.isNotBlank(strLogHomes))
        {
            String strLogHomesItems[] = strLogHomes.split(";");
            String arr$[] = strLogHomesItems;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String str = arr$[i$];
                if(StringUtils.isNotEmpty(str) && (new File(str)).exists())
                    logHomes.add(str);
            }

        }
        logExpiredDays = NumberTool.safeToInteger(properties.get("loghomes"), Integer.valueOf(0)).intValue();
        if(logExpiredDays <= 0)
            logExpiredDays = 30;
    }

    public void run()
    {
        do
        {
            clearTempDir();
            clearLogs();
            LOGGER.debug("sleep tempdir clean");
            try
            {
                Thread.sleep(0x5265c00L);
            }
            catch(InterruptedException ex)
            {
                return;
            }
        } while(true);
    }

    private void clearTempDir()
    {
        String tempdir = SettingUtils.getStringValue("COMMON", "TEMPDIR");
        LOGGER.debug((new StringBuilder()).append("start tempdir clean...[").append(tempdir).append("]").toString());
        if(StringUtils.isNotBlank(tempdir))
            clearFiles(tempdir, 1);
    }

    private static void clearFiles(String targetDir, int expiredDays)
    {
        Calendar cldr = Calendar.getInstance();
        cldr.add(5, -expiredDays);
        long yesterday = cldr.getTime().getTime();
        File file = new File(targetDir);
        File files[] = file.listFiles();
        if(files != null && files.length > 0)
        {
            Stack fset = new Stack();
            File arr$[] = files;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                File f = arr$[i$];
                if(f.isDirectory())
                {
                    fset.push(f);
                    continue;
                }
                if(f.lastModified() <= yesterday)
                    IOUtil.deleteFile(f);
            }

            while(!fset.isEmpty()) 
            {
                File dir = (File)fset.pop();
                files = dir.listFiles();
                if(files != null && files.length > 0)
                {
                    File arr$[] = files;
                    int len$ = arr$.length;
                    int i$ = 0;
                    while(i$ < len$) 
                    {
                        File f = arr$[i$];
                        if(f.isDirectory())
                            fset.push(f);
                        else
                        if(f.lastModified() <= yesterday)
                            IOUtil.deleteFile(f);
                        i$++;
                    }
                } else
                {
                    IOUtil.deleteFile(dir);
                }
            }
        }
    }

    private void clearLogs()
    {
        if(logExpiredDays > 0 && logHomes.size() > 0)
        {
            String logHome;
            for(Iterator i$ = logHomes.iterator(); i$.hasNext(); clearFiles(logHome, logExpiredDays))
            {
                logHome = (String)i$.next();
                LOGGER.debug((new StringBuilder()).append("clear files in [").append(logHome).append("] before ").append(logExpiredDays).append(" days").toString());
            }

        }
    }

    public static void main(String args[])
    {
        TempDirCleaner cleaner = new TempDirCleaner();
        cleaner.logHomes.add("/Users/terrywangxp81/sirmproject/\u9879\u76EEBUG");
        cleaner.clearLogs();
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/utils/TempDirCleaner);
    private List logHomes;
    private int logExpiredDays;

}
