// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HSpacingTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, HSpacing

public class HSpacingTag extends ComponentTag
{

    public HSpacingTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new HSpacing(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        HSpacing cmp = (HSpacing)component;
        cmp.setWidth(width);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    private String width;
}
