// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OgnlUtils.java

package com.sinitek.spirit.webcontrol.utils;

import java.util.Map;
import ognl.*;

public class OgnlUtils
{

    public OgnlUtils(String className)
    {
        context = null;
        target = null;
        try
        {
            target = Class.forName(className).newInstance();
            context = Ognl.createDefaultContext(target);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public OgnlUtils(Object bean)
    {
        context = null;
        target = null;
        try
        {
            target = bean;
            context = Ognl.createDefaultContext(target);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Object getValue(String expression)
        throws Exception
    {
        DefaultMemberAccess aMemberAccess = new DefaultMemberAccess(true);
        Ognl.setMemberAccess(context, aMemberAccess);
        try
        {
            return Ognl.getValue(expression, context, target);
        }
        catch(OgnlException e)
        {
            e.printStackTrace();
            throw new Exception((new StringBuilder()).append("ognl\u8868\u8FBE\u5F0F\u89E3\u6790\u5931\u8D25:").append(e.getMessage()).toString());
        }
    }

    private Map context;
    private Object target;
}
