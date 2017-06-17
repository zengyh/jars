// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VSpacingTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, VSpacing

public class VSpacingTag extends ComponentTag
{

    public VSpacingTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new VSpacing(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        VSpacing cmp = (VSpacing)component;
        cmp.setHeight(height);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    private String height;
}
