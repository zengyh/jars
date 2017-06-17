// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Select

public class MultiSelect extends Select
{

    public String getDefaultOpenTemplate()
    {
        return "multiselect";
    }

    protected String getDefaultTemplate()
    {
        return "multiselect-close";
    }

    public MultiSelect(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        super(valueStack, httpServletRequest, httpServletResponse);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "multiselect");
        addProperty("width", width, "165px");
        addProperty("textWidth", textWidth);
        addProperty("columnWidth", columnWidth, "120px");
        addProperty("columnHeight", columnHeight, "30px");
        addProperty("direction", direction, "bottom");
        if(positionY != null)
            positionY = positionY.replace("px", "");
        if(positionX != null)
            positionX = positionX.replace("px", "");
        addProperty("positionY", positionY, "0");
        addProperty("positionX", positionX, "0");
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setColumnWidth(String columnWidth)
    {
        this.columnWidth = columnWidth;
    }

    public void setColumnHeight(String columnHeight)
    {
        this.columnHeight = columnHeight;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public void setTextWidth(String textWidth)
    {
        this.textWidth = textWidth;
    }

    public void setPositionX(String positionX)
    {
        this.positionX = positionX;
    }

    public void setPositionY(String positionY)
    {
        this.positionY = positionY;
    }

    private static final String TEMPLATE = "multiselect";
    private static final String TEMPLATE_CLOSE = "multiselect-close";
    private String width;
    private String columnWidth;
    private String columnHeight;
    private String direction;
    private String textWidth;
    private String positionX;
    private String positionY;
}
