// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartToolTip.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartToolTip extends AbsChart
{

    public ChartToolTip(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-tooltip";
    }

    protected String getDefaultTemplate()
    {
        return "chart-tooltip-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_tooltip");
        addProperty("crosshairs", crosshairs);
        addProperty("shared", shared);
    }

    public void setCrosshairs(String crosshairs)
    {
        this.crosshairs = crosshairs;
    }

    public void setShared(String shared)
    {
        this.shared = shared;
    }

    private static final String TEMPLATE = "chart-tooltip";
    private static final String TEMPLATE_CLOSE = "chart-tooltip-close";
    private String crosshairs;
    private String shared;
}
