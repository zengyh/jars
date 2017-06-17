// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnEnumTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnEnum

public class ColumnEnumTag extends ColumnTag
{

    public ColumnEnumTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnEnum(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnEnum cmp = (ColumnEnum)component;
        cmp.setFormat(format);
        cmp.setClazz(clazz);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    private String format;
    private String clazz;
}
