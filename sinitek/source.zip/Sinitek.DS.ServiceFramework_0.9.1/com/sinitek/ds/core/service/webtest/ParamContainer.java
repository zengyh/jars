// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParamContainer.java

package com.sinitek.ds.core.service.webtest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ParamContainer
    implements Serializable
{

    public ParamContainer()
    {
        paramMap = new HashMap();
        nullMarkMap = new HashMap();
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public Map getNullMarkMap()
    {
        return nullMarkMap;
    }

    public void setNullMarkMap(Map nullMarkMap)
    {
        this.nullMarkMap = nullMarkMap;
    }

    public Map getParamMap()
    {
        return paramMap;
    }

    public void setParamMap(Map paramMap)
    {
        this.paramMap = paramMap;
    }

    private Map paramMap;
    private Map nullMarkMap;
    private String info;
}
