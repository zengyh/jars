// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmTag.java

package com.sinitek.sirm.common.web.tag;

import com.sinitek.sirm.common.exception.SirmAppException;
import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class SirmTag extends BodyTagSupport
{

    public SirmTag()
    {
    }

    public abstract int doStartTag()
        throws JspException;

    public abstract int doEndTag()
        throws JspException;

    protected void println(String str)
    {
        try
        {
            pageContext.getOut().println(str);
        }
        catch(IOException e)
        {
            throw new SirmAppException(e.getMessage(), "0001");
        }
    }
}
