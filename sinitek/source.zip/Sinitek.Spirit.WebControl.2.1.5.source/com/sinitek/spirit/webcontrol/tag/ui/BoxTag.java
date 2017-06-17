// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BoxTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Box

public class BoxTag extends ComponentTag
{

    public BoxTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Box(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Box cmp = (Box)component;
        cmp.setLayout(layout);
        cmp.setAlign(align);
        cmp.setWidth(width);
        cmp.setHeight(height);
        cmp.setCols(cols);
        cmp.setLabelWidth(labelWidth);
        cmp.setCellHeight(cellHeight);
        cmp.setCellWidth(cellWidth);
        cmp.setCellUnderLine(cellUnderLine);
        cmp.setCellMarginBottom(cellMarginBottom);
    }

    public void setCellUnderLine(String cellUnderLine)
    {
        this.cellUnderLine = cellUnderLine;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setCols(String cols)
    {
        this.cols = cols;
    }

    public void setLabelWidth(String labelWidth)
    {
        this.labelWidth = labelWidth;
    }

    public void setCellHeight(String cellHeight)
    {
        this.cellHeight = cellHeight;
    }

    public void setCellWidth(String cellWidth)
    {
        this.cellWidth = cellWidth;
    }

    public void setCellMarginBottom(String cellMarginBottom)
    {
        this.cellMarginBottom = cellMarginBottom;
    }

    private String layout;
    private String align;
    private String width;
    private String height;
    private String cols;
    private String labelWidth;
    private String cellHeight;
    private String cellWidth;
    private String cellUnderLine;
    private String cellMarginBottom;
}
