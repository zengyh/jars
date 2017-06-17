// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckBoxGroupTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListFieldTag, CheckBoxGroup

public class CheckBoxGroupTag extends ListFieldTag
{

    public CheckBoxGroupTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new CheckBoxGroup(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        CheckBoxGroup cmp = (CheckBoxGroup)component;
        cmp.setWidth(width);
        cmp.setHeight(height);
        cmp.setColumnWidth(columnWidth);
        cmp.setColumnHeight(columnHeight);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setColumnWidth(String columnWidth)
    {
        this.columnWidth = columnWidth;
    }

    public void setColumnHeight(String columnHeight)
    {
        this.columnHeight = columnHeight;
    }

    private String width;
    private String height;
    private String columnWidth;
    private String columnHeight;
}
