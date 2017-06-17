// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartLabels.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartLabels extends AbsChart
{

    public ChartLabels(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-labels";
    }

    protected String getDefaultTemplate()
    {
        return "chart-labels-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_title");
        addProperty("align", align);
        addProperty("x", x);
        addProperty("y", y);
        addProperty("rotation", rotation);
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public void setRotation(String rotation)
    {
        this.rotation = rotation;
    }

    private static final String TEMPLATE = "chart-labels";
    private static final String TEMPLATE_CLOSE = "chart-labels-close";
    private String align;
    private String x;
    private String y;
    private String rotation;
}
