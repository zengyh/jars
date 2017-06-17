// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartLegend.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartLegend extends AbsChart
{

    public ChartLegend(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-legend";
    }

    protected String getDefaultTemplate()
    {
        return "chart-legend-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_title");
        addProperty("margin", margin);
        addProperty("align", align);
        addProperty("layout", layout);
        addProperty("verticalAlign", verticalAlign);
        addProperty("x", x);
        addProperty("y", y);
        addProperty("floating", floating);
        addProperty("enabled", enabled);
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setMargin(String margin)
    {
        this.margin = margin;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

    public void setVerticalAlign(String verticalAlign)
    {
        this.verticalAlign = verticalAlign;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public void setFloating(String floating)
    {
        this.floating = floating;
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    private static final String TEMPLATE = "chart-legend";
    private static final String TEMPLATE_CLOSE = "chart-legend-close";
    private String align;
    private String margin;
    private String layout;
    private String verticalAlign;
    private String x;
    private String y;
    private String floating;
    private String enabled;
}
