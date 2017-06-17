// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartMarker.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartMarker extends AbsChart
{

    public ChartMarker(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-marker";
    }

    protected String getDefaultTemplate()
    {
        return "chart-marker-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_marker");
        addProperty("enabled", enabled);
        addProperty("fillColor", fillColor);
        addProperty("lineColor", lineColor);
        if(StringUtils.isNotBlank(lineWidth))
            lineWidth = lineWidth.replaceAll("px", "");
        addProperty("lineWidth", lineWidth);
        addProperty("radius", radius);
        addProperty("symbol", symbol);
    }

    public void setFillColor(String fillColor)
    {
        this.fillColor = fillColor;
    }

    public void setLineColor(String lineColor)
    {
        this.lineColor = lineColor;
    }

    public void setLineWidth(String lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    public void setRadius(String radius)
    {
        this.radius = radius;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    private static final String TEMPLATE = "chart-marker";
    private static final String TEMPLATE_CLOSE = "chart-marker-close";
    private String enabled;
    private String fillColor;
    private String lineColor;
    private String lineWidth;
    private String radius;
    private String symbol;
}
