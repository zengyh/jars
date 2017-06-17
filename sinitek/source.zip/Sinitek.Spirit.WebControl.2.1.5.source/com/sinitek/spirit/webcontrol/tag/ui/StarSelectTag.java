// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarSelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, StarSelect

public class StarSelectTag extends FieldTag
{

    public StarSelectTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new StarSelect(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        StarSelect cmp = (StarSelect)component;
        cmp.setMaxStar(maxStar);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setMaxStar(String maxStar)
    {
        this.maxStar = maxStar;
    }

    private String maxStar;
}
