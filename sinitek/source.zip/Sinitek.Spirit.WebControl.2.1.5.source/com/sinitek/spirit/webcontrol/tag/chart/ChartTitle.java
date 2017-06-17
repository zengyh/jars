// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartTitle.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartTitle extends AbsChart
{

    public ChartTitle(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-title";
    }

    protected String getDefaultTemplate()
    {
        return "chart-title-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_title");
        addProperty("margin", margin);
        addProperty("align", align);
        addProperty("text", text);
        addProperty("verticalAlign", verticalAlign);
        addProperty("x", x);
        addProperty("y", y);
        addProperty("rotation", rotation);
    }

    public String getAlign()
    {
        return align;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public String getMargin()
    {
        return margin;
    }

    public void setMargin(String margin)
    {
        this.margin = margin;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getVerticalAlign()
    {
        return verticalAlign;
    }

    public void setVerticalAlign(String verticalAlign)
    {
        this.verticalAlign = verticalAlign;
    }

    public String getX()
    {
        return x;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public String getY()
    {
        return y;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public void setRotation(String rotation)
    {
        this.rotation = rotation;
    }

    private static final String TEMPLATE = "chart-title";
    private static final String TEMPLATE_CLOSE = "chart-title-close";
    private String align;
    private String margin;
    private String text;
    private String verticalAlign;
    private String x;
    private String y;
    private String rotation;
}
