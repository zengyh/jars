// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoFormAction.java

package com.sinitek.spirit.webcontrol.demo;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoFormAction
{

    public DemoFormAction()
    {
    }

    public static String test(Map param, HttpServletRequest request)
    {
        String t1 = (String)param.get("t1");
        return (new StringBuilder()).append("\u63D0\u4EA4\u4E86t1:").append(t1).toString();
    }

    public static Map init(Map param, HttpServletRequest request)
    {
        Map result = new HashMap();
        String objid = (String)param.get("objid");
        if("1111".equals(objid))
            result.put("demostring", "\u83B7\u5F97\u6210\u529F");
        return result;
    }
}
