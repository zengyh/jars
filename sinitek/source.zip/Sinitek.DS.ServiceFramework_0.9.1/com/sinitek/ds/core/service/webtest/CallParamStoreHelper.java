// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallParamStoreHelper.java

package com.sinitek.ds.core.service.webtest;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// Referenced classes of package com.sinitek.ds.core.service.webtest:
//            CallParamInfo, ParamContainer

public class CallParamStoreHelper
{

    public CallParamStoreHelper()
    {
    }

    public static List getStoredParamMap(HttpServletRequest request, String functionCode)
    {
        List ret = new ArrayList();
        String path = request.getSession().getServletContext().getRealPath((new StringBuilder()).append("/WEB-INF/callparam/").append(functionCode).toString());
        File dir = new File(path);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if(dir.exists())
        {
            File files[] = dir.listFiles(new FileFilter() {

                public boolean accept(File pathname)
                {
                    return !pathname.isDirectory() && pathname.getName().toLowerCase().endsWith(".dat") && pathname.getName().length() == 18;
                }

            }
);
            for(int i = 0; i < files.length; i++)
            {
                File file = files[i];
                try
                {
                    String timeStamp = file.getName().substring(0, 14);
                    Date date = sdf.parse(timeStamp);
                    CallParamInfo info = new CallParamInfo(date, file);
                    ret.add(info);
                }
                catch(ParseException e)
                {
                    e.printStackTrace();
                }
            }

        }
        return ret;
    }

    public static synchronized CallParamInfo loadCurrent(HttpServletRequest request, String functionCode, String fileName)
    {
        String path = request.getSession().getServletContext().getRealPath((new StringBuilder()).append("/WEB-INF/callparam/").append(functionCode).append("/").append(fileName).toString());
        File file = new File(path);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if(file.exists())
            try
            {
                String timeStamp = file.getName().substring(0, 14);
                Date date = sdf.parse(timeStamp);
                return new CallParamInfo(date, file);
            }
            catch(ParseException e)
            {
                throw new RuntimeException("\u8BFB\u53D6\u6587\u4EF6\u5931\u8D25", e);
            }
        else
            return null;
    }

    public static synchronized void deleteSaveFile(HttpServletRequest request, String functionCode, String fileName)
    {
        String path = request.getSession().getServletContext().getRealPath((new StringBuilder()).append("/WEB-INF/callparam/").append(functionCode).append("/").append(fileName).toString());
        File file = new File(path);
        if(file.exists())
            file.delete();
    }

    public static synchronized void saveParamMap(HttpServletRequest request, String functionCode, ParamContainer container)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = (new StringBuilder()).append(sdf.format(new Date())).append(".dat").toString();
        saveParamMap(request, functionCode, container, fileName);
    }

    public static synchronized void saveParamMap(HttpServletRequest request, String functionCode, ParamContainer container, String fileName)
    {
        try
        {
            String path = request.getSession().getServletContext().getRealPath((new StringBuilder()).append("/WEB-INF/callparam/").append(functionCode).toString());
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();
            File saveFile = new File((new StringBuilder()).append(path).append("/").append(fileName).toString());
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            FileOutputStream fs = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fs);
            oos.writeObject(container);
            oos.flush();
            oos.close();
            fs.flush();
            fs.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("\u4FDD\u5B58\u6587\u4EF6\u5931\u8D25", e);
        }
    }
}
