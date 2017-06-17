// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Box.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Box extends Component
{

    public Box(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "box";
    }

    protected String getDefaultTemplate()
    {
        return "box-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "box");
        addProperty("layout", layout, "none");
        addProperty("width", width);
        addProperty("height", height);
        addProperty("cellWidth", cellWidth);
        addProperty("cellHeight", cellHeight);
        addProperty("cols", cols);
        addProperty("labelWidth", labelWidth, "100px");
        addProperty("align", align, "left");
        addProperty("cellUnderLine", cellUnderLine, "false");
        addProperty("cellMarginBottom", cellMarginBottom);
    }

    public void setCellMarginBottom(String cellMarginBottom)
    {
        this.cellMarginBottom = cellMarginBottom;
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

    private static final String TEMPLATE = "box";
    private static final String TEMPLATE_CLOSE = "box-close";
    private String layout;
    private String align;
    String width;
    private String height;
    private String cols;
    private String labelWidth;
    private String cellHeight;
    private String cellWidth;
    private String cellUnderLine;
    private String cellMarginBottom;
}
