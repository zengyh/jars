// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chart.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Chart extends Component
{

    public Chart(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart";
    }

    protected String getDefaultTemplate()
    {
        return "chart-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart");
        addProperty("clazz", clazz);
        addProperty("jsParam", jsParam);
        addProperty("allowInit", allowInit, "true");
        addProperty("height", height);
        addProperty("inverted", inverted);
        addProperty("margin", margin);
        addProperty("marginTop", marginTop);
        addProperty("marginRight", marginRight);
        addProperty("marginBottom", marginBottom);
        addProperty("marginLeft", marginLeft);
        addProperty("spacingTop", spacingTop);
        addProperty("spacingRight", spacingRight);
        addProperty("spacingBottom", spacingBottom);
        addProperty("spacingLeft", spacingLeft);
        addProperty("type", type, "line");
        addProperty("width", width);
        addProperty("dataVar", dataVar, "result");
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

    private static final String TEMPLATE = "chart";
    private static final String TEMPLATE_CLOSE = "chart-close";
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
