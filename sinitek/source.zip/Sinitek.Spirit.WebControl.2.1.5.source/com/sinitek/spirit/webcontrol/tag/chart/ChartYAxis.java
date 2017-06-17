// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartYAxis.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            ChartXAxis

public class ChartYAxis extends ChartXAxis
{

    public ChartYAxis(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-yaxis";
    }

    protected String getDefaultTemplate()
    {
        return "chart-yaxis-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_yaxis");
    }

    private static final String TEMPLATE = "chart-yaxis";
    private static final String TEMPLATE_CLOSE = "chart-yaxis-close";
}
