// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartRangeSelector.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartRangeSelector extends AbsChart
{

    public ChartRangeSelector(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-rangeselector";
    }

    protected String getDefaultTemplate()
    {
        return "chart-rangeselector-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_rangeselector");
        addProperty("enabled", enabled);
        addProperty("buttons", buttons);
        addProperty("selected", selected);
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    public void setButtons(String buttons)
    {
        this.buttons = buttons;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
    }

    private static final String TEMPLATE = "chart-rangeselector";
    private static final String TEMPLATE_CLOSE = "chart-rangeselector-close";
    private String enabled;
    private String buttons;
    private String selected;
}
