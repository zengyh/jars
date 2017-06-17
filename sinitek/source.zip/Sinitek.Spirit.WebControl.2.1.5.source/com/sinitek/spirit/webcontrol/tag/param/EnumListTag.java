// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumListTag.java

package com.sinitek.spirit.webcontrol.tag.param;

import com.sinitek.base.enumsupport.EnumItemContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class EnumListTag extends BodyTagSupport
{

    public EnumListTag()
    {
    }

    public int doEndTag()
        throws JspException
    {
        java.util.List list = EnumItemContext.getInstance().getEnumItems(clazz);
        pageContext.setAttribute(var, list);
        return 1;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getVar()
    {
        return var;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    private String clazz;
    private String var;
}
