// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BoxCellTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, BoxCell

public class BoxCellTag extends ComponentTag
{

    public BoxCellTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new BoxCell(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        BoxCell cmp = (BoxCell)component;
        cmp.setWidth(width);
        cmp.setHeight(height);
        cmp.setLabelWidth(labelWidth);
        cmp.setAlign(align);
        cmp.setUnderLine(underLine);
        cmp.setMarginBottom(marginBottom);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setLabelWidth(String labelWidth)
    {
        this.labelWidth = labelWidth;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setUnderLine(String underLine)
    {
        this.underLine = underLine;
    }

    public void setMarginBottom(String marginBottom)
    {
        this.marginBottom = marginBottom;
    }

    private String width;
    private String height;
    private String align;
    private String labelWidth;
    private String underLine;
    private String marginBottom;
}
