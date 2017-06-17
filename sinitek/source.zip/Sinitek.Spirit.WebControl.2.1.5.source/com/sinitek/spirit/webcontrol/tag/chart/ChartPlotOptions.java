// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartPlotOptions.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartPlotOptions extends AbsChart
{

    public ChartPlotOptions(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-plotoptions";
    }

    protected String getDefaultTemplate()
    {
        return "chart-plotoptions-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_plotoptions");
    }

    private static final String TEMPLATE = "chart-plotoptions";
    private static final String TEMPLATE_CLOSE = "chart-plotoptions-close";
}
