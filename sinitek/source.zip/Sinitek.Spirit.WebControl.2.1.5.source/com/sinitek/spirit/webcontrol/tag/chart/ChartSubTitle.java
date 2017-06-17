// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartSubTitle.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            ChartTitle

public class ChartSubTitle extends ChartTitle
{

    public ChartSubTitle(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_subtitle");
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-subtitle";
    }

    protected String getDefaultTemplate()
    {
        return "chart-subtitle-close";
    }

    private static final String TEMPLATE = "chart-subtitle";
    private static final String TEMPLATE_CLOSE = "chart-subtitle-close";
}
