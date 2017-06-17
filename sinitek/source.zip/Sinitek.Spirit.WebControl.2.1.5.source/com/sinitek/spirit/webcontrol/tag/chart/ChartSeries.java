// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartSeries.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartSeries extends AbsChart
{

    public ChartSeries(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-series";
    }

    protected String getDefaultTemplate()
    {
        return "chart-series-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_series");
        addProperty("chartStack", chartStack);
        addProperty("xAxis", xAxis);
        addProperty("yAxis", yAxis);
    }

    public void setChartStack(String chartStack)
    {
        this.chartStack = chartStack;
    }

    public void setXAxis(String xAxis)
    {
        this.xAxis = xAxis;
    }

    public void setYAxis(String yAxis)
    {
        this.yAxis = yAxis;
    }

    private static final String TEMPLATE = "chart-series";
    private static final String TEMPLATE_CLOSE = "chart-series-close";
    private String chartStack;
    private String xAxis;
    private String yAxis;
}
