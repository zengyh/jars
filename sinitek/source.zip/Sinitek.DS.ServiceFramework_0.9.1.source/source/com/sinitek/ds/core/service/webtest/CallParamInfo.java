// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallParamInfo.java

package com.sinitek.ds.core.service.webtest;

import java.io.*;
import java.util.*;

// Referenced classes of package com.sinitek.ds.core.service.webtest:
//            ParamContainer

public class CallParamInfo
{

    public CallParamInfo(Date saveDate, File saveFile)
    {
        FileInputStream is;
        paramMap = Collections.EMPTY_MAP;
        nullMarkMap = Collections.EMPTY_MAP;
        info = "";
        this.saveDate = saveDate;
        this.saveFile = saveFile;
        is = null;
        try
        {
            is = new FileInputStream(saveFile);
            Object o = (new ObjectInputStream(is)).readObject();
            if(o instanceof Map)
            {
                paramMap = (Map)o;
            } else
            {
                ParamContainer container = (ParamContainer)o;
                paramMap = container.getParamMap();
                nullMarkMap = container.getNullMarkMap();
                info = container.getInfo();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("\u8BFB\u53D6\u4FDD\u5B58\u6587\u4EF6\u5931\u8D25", e);
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_170;
        Exception exception;
        exception;
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        throw exception;
    }

    public Date getSaveDate()
    {
        return saveDate;
    }

    public File getSaveFile()
    {
        return saveFile;
    }

    public Map getParamMap()
    {
        return paramMap;
    }

    public String getInfo()
    {
        return info;
    }

    public Map getNullMarkMap()
    {
        return nullMarkMap;
    }

    private Date saveDate;
    private File saveFile;
    private Map paramMap;
    private Map nullMarkMap;
    private String info;
}
