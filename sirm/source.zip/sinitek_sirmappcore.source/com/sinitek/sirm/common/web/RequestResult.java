// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestResult.java

package com.sinitek.sirm.common.web;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class RequestResult
{

    public RequestResult()
    {
        result = 0;
        message = null;
        data = new HashMap();
    }

    public RequestResult(int result, String message)
    {
        this.result = 0;
        this.message = null;
        data = new HashMap();
        this.result = result;
        this.message = message;
    }

    public String toJSON()
    {
        Map map = new HashMap();
        map.put("result", Integer.valueOf(result));
        map.put("message", message);
        map.put("data", data);
        String str = (new JSONObject(map)).toString();
        str = StringUtils.replace(str, "\\r", "\\\\r");
        str = StringUtils.replace(str, "\\n", "\\\\n");
        return str;
    }

    public void putValue(String name, String value)
    {
        data.put(name, value);
    }

    public String getValue(String name)
    {
        return (String)data.get(name);
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Map getData()
    {
        return data;
    }

    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int FAILURE = -1;
    private int result;
    private String message;
    private Map data;
}
