// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LabelTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Label

public class LabelTag extends ComponentTag
{

    public LabelTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Label(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Label cmp = (Label)component;
        cmp.setWidth(width);
        cmp.setAlign(align);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    private String width;
    private String align;
}
