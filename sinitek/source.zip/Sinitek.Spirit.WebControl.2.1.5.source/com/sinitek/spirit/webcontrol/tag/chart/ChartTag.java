// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.ComponentTag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            Chart

public class ChartTag extends ComponentTag
{

    public ChartTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Chart(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Chart chart = (Chart)component;
        chart.setClazz(clazz);
        chart.setJsParam(jsParam);
        chart.setAllowInit(allowInit);
        chart.setHeight(height);
        chart.setInverted(inverted);
        chart.setMargin(margin);
        chart.setMarginTop(marginTop);
        chart.setMarginRight(marginRight);
        chart.setMarginBottom(marginBottom);
        chart.setMarginLeft(marginLeft);
        chart.setSpacingTop(spacingTop);
        chart.setSpacingRight(spacingRight);
        chart.setSpacingBottom(spacingBottom);
        chart.setSpacingLeft(spacingLeft);
        chart.setType(type);
        chart.setWidth(width);
        chart.setDataVar(dataVar);
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setJsParam(String jsParam)
    {
        this.jsParam = jsParam;
    }

    public void setAllowInit(String allowInit)
    {
        this.allowInit = allowInit;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setInverted(String inverted)
    {
        this.inverted = inverted;
    }

    public void setMargin(String margin)
    {
        this.margin = margin;
    }

    public void setMarginTop(String marginTop)
    {
        this.marginTop = marginTop;
    }

    public void setMarginRight(String marginRight)
    {
        this.marginRight = marginRight;
    }

    public void setMarginBottom(String marginBottom)
    {
        this.marginBottom = marginBottom;
    }

    public void setMarginLeft(String marginLeft)
    {
        this.marginLeft = marginLeft;
    }

    public void setSpacingTop(String spacingTop)
    {
        this.spacingTop = spacingTop;
    }

    public void setSpacingRight(String spacingRight)
    {
        this.spacingRight = spacingRight;
    }

    public void setSpacingBottom(String spacingBottom)
    {
        this.spacingBottom = spacingBottom;
    }

    public void setSpacingLeft(String spacingLeft)
    {
        this.spacingLeft = spacingLeft;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setDataVar(String dataVar)
    {
        this.dataVar = dataVar;
    }

    private String clazz;
    private String jsParam;
    private String allowInit;
    private String height;
    private String inverted;
    private String margin;
    private String marginTop;
    private String marginRight;
    private String marginBottom;
    private String marginLeft;
    private String spacingTop;
    private String spacingRight;
    private String spacingBottom;
    private String spacingLeft;
    private String type;
    private String width;
    private String dataVar;
}
