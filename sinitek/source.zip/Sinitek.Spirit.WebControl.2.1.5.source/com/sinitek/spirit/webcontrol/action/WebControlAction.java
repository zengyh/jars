// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebControlAction.java

package com.sinitek.spirit.webcontrol.action;

import com.opensymphony.xwork2.ActionSupport;
import java.io.*;
import java.net.URLDecoder;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class WebControlAction extends ActionSupport
{

    public WebControlAction()
    {
    }

    public String tableExport()
    {
        return "success";
    }

    public String getDownloadFileName()
    {
        String downFileName = fileName;
        try
        {
            downFileName = new String(downFileName.getBytes(), "ISO8859-1");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return downFileName;
    }

    public InputStream getInputStream()
        throws Exception
    {
        try
        {
            String realFileName = URLDecoder.decode(fileName, "utf-8");
            String filenamedownload = (new StringBuilder()).append(ServletActionContext.getServletContext().getRealPath("/spirittemp/")).append(File.separator).append(realFileName).toString();
            if(realFileName.contains("_"))
                fileName = (new StringBuilder()).append(realFileName.substring(0, realFileName.lastIndexOf("_"))).append(realFileName.substring(realFileName.lastIndexOf("."), realFileName.length())).toString();
            else
                fileName = realFileName;
            return new FileInputStream(filenamedownload);
        }
        catch(Exception e)
        {
            LOG.error("\u4E0B\u8F7DExcel\u6587\u4EF6\u5F02\u5E38", e);
            throw e;
        }
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/action/WebControlAction);
    private String fileName;

}
