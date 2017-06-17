// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartXAxis.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartXAxis extends AbsChart
{

    public ChartXAxis(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-xaxis";
    }

    protected String getDefaultTemplate()
    {
        return "chart-xaxis-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_xaxis");
        addProperty("categories", categories);
        addProperty("showFirstLabel", showFirstLabel);
        addProperty("showLastLabel", showLastLabel);
        addProperty("tickInterval", tickInterval);
        addProperty("maxZoom", maxZoom);
        addProperty("max", max);
        addProperty("min", min);
        Map parent = getParentParam();
        if(parent != null)
            addProperty("chartType", (String)parent.get("type"));
    }

    public void setCategories(String categories)
    {
        this.categories = categories;
    }

    public void setShowFirstLabel(String showFirstLabel)
    {
        this.showFirstLabel = showFirstLabel;
    }

    public void setShowLastLabel(String showLastLabel)
    {
        this.showLastLabel = showLastLabel;
    }

    public void setTickInterval(String tickInterval)
    {
        this.tickInterval = tickInterval;
    }

    public void setMaxZoom(String maxZoom)
    {
        this.maxZoom = maxZoom;
    }

    public void setMax(String max)
    {
        this.max = max;
    }

    public void setMin(String min)
    {
        this.min = min;
    }

    private static final String TEMPLATE = "chart-xaxis";
    private static final String TEMPLATE_CLOSE = "chart-xaxis-close";
    private String categories;
    private String showFirstLabel;
    private String showLastLabel;
    private String tickInterval;
    private String maxZoom;
    private String max;
    private String min;
}
