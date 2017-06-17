// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Uploader.java

package com.sinitek.sirm.framework.utils.io;

import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.io.*;
import java.util.*;
import javax.servlet.ServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.sirm.framework.utils.io:
//            UploaderFileList, UploaderFile

public class Uploader
{
    public static class MapParameterHelper
        implements IParameterHelper
    {

        public String getParameter(String name)
        {
            return (String)map.get(name);
        }

        public String[] getParameterValues(String name)
        {
            String str = (String)map.get(name);
            return str != null ? str.split(",") : null;
        }

        private Map map;

        public MapParameterHelper(Map map)
        {
            this.map = map;
        }
    }

    public static class RequestParameterHelper
        implements IParameterHelper
    {

        public String getParameter(String name)
        {
            return request.getParameter(name);
        }

        public String[] getParameterValues(String name)
        {
            return request.getParameterValues(name);
        }

        private ServletRequest request;

        public RequestParameterHelper(ServletRequest request)
        {
            this.request = request;
        }
    }

    public static interface IParameterHelper
    {

        public abstract String getParameter(String s);

        public abstract String[] getParameterValues(String s);
    }


    public Uploader()
    {
    }

    public static UploaderFileList getFileList()
    {
        return threadLocal.get() != null ? new UploaderFileList((Collection)threadLocal.get()) : new UploaderFileList();
    }

    public static UploaderFile getFile(String id)
    {
        for(Iterator i$ = getFileList().iterator(); i$.hasNext();)
        {
            UploaderFile file = (UploaderFile)i$.next();
            if(file.getId() != null && file.getId().equals(id))
                return file;
        }

        return null;
    }

    public static void addFile(UploaderFile file)
    {
        UploaderFileList list = (UploaderFileList)threadLocal.get();
        if(list == null)
        {
            list = new UploaderFileList();
            threadLocal.set(list);
        }
        list.add(file);
    }

    public static void addAll(Collection fileList)
    {
        List list = (List)threadLocal.get();
        if(list == null)
            threadLocal.set(new UploaderFileList(fileList));
        else
            list.addAll(fileList);
    }

    public static void removeFile(UploaderFile file)
    {
        List list = (List)threadLocal.get();
        if(list == null)
        {
            return;
        } else
        {
            file.delete();
            list.remove(file);
            return;
        }
    }

    public static void removeAll()
    {
        List list = (List)threadLocal.get();
        if(list == null)
            return;
        if(!list.isEmpty())
        {
            File dir = ((UploaderFile)list.get(0)).getParentFile();
            File arr$[] = dir.listFiles();
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                File file = arr$[i$];
            }

        }
        threadLocal.remove();
    }

    public static UploaderFileList parseRequest(ServletRequest request)
    {
        return parseRequest(((IParameterHelper) (new RequestParameterHelper(request))));
    }

    public static UploaderFileList parseRequest(ServletRequest request, String uploaderId)
    {
        return parseRequest(((IParameterHelper) (new RequestParameterHelper(request))), uploaderId);
    }

    public static UploaderFileList parseRequest(IParameterHelper helper)
    {
        String uploaderId = helper.getParameter("uploader");
        return parseRequest(helper, uploaderId);
    }

    public static UploaderFileList parseRequest(IParameterHelper helper, String uploaderId)
    {
        UploaderFileList list = new UploaderFileList();
        if(StringUtils.isBlank(uploaderId))
            return list;
        String ids[] = helper.getParameterValues(uploaderId);
        if(ids == null)
            return list;
        String arr$[] = ids;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String id = arr$[i$];
            String fileName = helper.getParameter(id);
            if(!StringUtils.isNotBlank(fileName))
                continue;
            String fileSuffix = FilenameUtils.getExtension(fileName);
            String filePath = (new StringBuilder()).append(tempDir).append(File.separator).append(uploaderId).append(File.separator).append(id).append(".").append(fileSuffix).toString();
            if(StringUtils.isBlank(fileSuffix))
                filePath = (new StringBuilder()).append(filePath).append("tmp").toString();
            UploaderFile tempFile = new UploaderFile(filePath, fileName, id);
            if(tempFile.exists())
                list.add(tempFile);
        }

        return list;
    }

    public static UploaderFileList parseRequest(Map map)
    {
        return parseRequest(((IParameterHelper) (new MapParameterHelper(map))));
    }

    public static UploaderFileList parseRequest(Map map, String uploaderId)
    {
        return parseRequest(((IParameterHelper) (new MapParameterHelper(map))), uploaderId);
    }

    public static boolean saveFile(File upload, String uploaderId, String fileName, int chunks, int chunk, int chunkSize)
    {
        String dstPath = (new StringBuilder()).append(tempDir).append(File.separator).append(uploaderId).append(File.separator).append(fileName).toString();
        File dstFile = new File(dstPath);
        if(chunks > 1)
        {
            if(chunkSize > 0)
            {
                File file = null;
                if(chunk == 0 && dstFile.exists())
                    dstFile.delete();
                else
                if(!(file = dstFile.getParentFile()).exists())
                    file.mkdirs();
                appendChunk(upload, dstFile, chunkSize);
            } else
            {
                return false;
            }
        } else
        {
            try
            {
                FileUtils.copyFile(upload, dstFile);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void appendChunk(File src, File dst, int bufferSize)
    {
        InputStream in;
        OutputStream out;
        in = null;
        out = null;
        if(dst.exists())
            out = new BufferedOutputStream(new FileOutputStream(dst, true), bufferSize);
        else
            out = new BufferedOutputStream(new FileOutputStream(dst), bufferSize);
        in = new BufferedInputStream(new FileInputStream(src), bufferSize);
        byte buffer[] = new byte[bufferSize];
        for(int len = 0; (len = in.read(buffer)) > 0;)
            out.write(buffer, 0, len);

        IOException e;
        if(null != in)
            try
            {
                in.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                e.printStackTrace();
            }
        if(null != out)
            try
            {
                out.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                e.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_240;
        e;
        e.printStackTrace();
        if(null != in)
            try
            {
                in.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                e.printStackTrace();
            }
        if(null != out)
            try
            {
                out.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                e.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_240;
        Exception exception;
        exception;
        if(null != in)
            try
            {
                in.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        if(null != out)
            try
            {
                out.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        throw exception;
    }

    private static final ThreadLocal threadLocal = new ThreadLocal();
    private static final String tempDir;

    static 
    {
        tempDir = (new StringBuilder()).append(SettingUtils.getStringValue("COMMON", "TEMPDIR")).append(File.separator).append("uploader").toString();
    }
}
