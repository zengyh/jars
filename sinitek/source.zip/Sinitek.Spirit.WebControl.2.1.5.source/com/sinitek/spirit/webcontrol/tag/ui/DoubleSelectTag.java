// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoubleSelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            SelectTag, DoubleSelect

public class DoubleSelectTag extends SelectTag
{

    public DoubleSelectTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new DoubleSelect(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        DoubleSelect cmp = (DoubleSelect)component;
        cmp.setHeight(height);
        cmp.setSelectTitle(selectTitle);
        cmp.setSelectWidth(selectWidth);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setSelectTitle(String selectTitle)
    {
        this.selectTitle = selectTitle;
    }

    public void setSelectWidth(String selectWidth)
    {
        this.selectWidth = selectWidth;
    }

    private String height;
    private String selectTitle;
    private String selectWidth;
}
