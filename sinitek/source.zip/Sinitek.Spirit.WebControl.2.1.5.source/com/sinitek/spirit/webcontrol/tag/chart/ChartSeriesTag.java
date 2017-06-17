// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartSeriesTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartSeries

public class ChartSeriesTag extends AbsChartTag
{

    public ChartSeriesTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartSeries(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartSeries param = (ChartSeries)component;
        param.setChartStack(chartStack);
        param.setXAxis(xAxis);
        param.setYAxis(yAxis);
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

    private String chartStack;
    private String xAxis;
    private String yAxis;
}
