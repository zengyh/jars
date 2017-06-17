// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlUtils.java

package com.sinitek.sirm.common.utils;


public class HtmlUtils
{

    public HtmlUtils()
    {
    }

    public static String filterHtml(String s)
    {
        if(!s.equals("") || s != null)
        {
            String str = s.replaceAll("<[.[^<]]*>|", "");
            return str;
        } else
        {
            return s;
        }
    }
}
