// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartPlotOption.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartPlotOption extends AbsChart
{

    public ChartPlotOption(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-plotoption";
    }

    protected String getDefaultTemplate()
    {
        return "chart-plotoption-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_plotoption");
        addProperty("type", type);
        addProperty("pointStart", pointStart);
        addProperty("pointInterval", pointInterval);
        addProperty("visible", visible);
        addProperty("zIndex", zIndex);
        addProperty("lineWidth", lineWidth);
        addProperty("stacking", stacking);
        addProperty("allowPointSelect", allowPointSelect);
        addProperty("cursor", cursor);
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setPointStart(String pointStart)
    {
        this.pointStart = pointStart;
    }

    public void setPointInterval(String pointInterval)
    {
        this.pointInterval = pointInterval;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public void setzIndex(String zIndex)
    {
        this.zIndex = zIndex;
    }

    public void setLineWidth(String lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    public void setStacking(String stacking)
    {
        this.stacking = stacking;
    }

    public void setAllowPointSelect(String allowPointSelect)
    {
        this.allowPointSelect = allowPointSelect;
    }

    public void setCursor(String cursor)
    {
        this.cursor = cursor;
    }

    private static final String TEMPLATE = "chart-plotoption";
    private static final String TEMPLATE_CLOSE = "chart-plotoption-close";
    private String type;
    private String pointStart;
    private String pointInterval;
    private String visible;
    private String zIndex;
    private String lineWidth;
    private String stacking;
    private String allowPointSelect;
    private String cursor;
}
