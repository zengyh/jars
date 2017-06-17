// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiSelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            SelectTag, MultiSelect, Component

public class MultiSelectTag extends SelectTag
{

    public MultiSelectTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        ((MultiSelect)component).setWidth(width);
        ((MultiSelect)component).setColumnWidth(columnWidth);
        ((MultiSelect)component).setColumnHeight(columnHeight);
        ((MultiSelect)component).setDirection(direction);
        ((MultiSelect)component).setTextWidth(textWidth);
        ((MultiSelect)component).setPositionX(positionX);
        ((MultiSelect)component).setPositionY(positionY);
    }

    public com.sinitek.spirit.webcontrol.tag.ui.Component getBean(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return new MultiSelect(valueStack, httpServletRequest, httpServletResponse);
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

    public volatile Component getBean(ValueStack x0, HttpServletRequest x1, HttpServletResponse x2)
    {
        return getBean(x0, x1, x2);
    }

    private String width;
    private String columnWidth;
    private String columnHeight;
    private String direction;
    private String textWidth;
    private String positionX;
    private String positionY;
}
