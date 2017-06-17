// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartMarkerTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartMarker

public class ChartMarkerTag extends AbsChartTag
{

    public ChartMarkerTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartMarker(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartMarker param = (ChartMarker)component;
        param.setFillColor(fillColor);
        param.setLineColor(lineColor);
        param.setLineWidth(lineWidth);
        param.setRadius(radius);
        param.setSymbol(symbol);
        param.setEnabled(enabled);
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

    private String enabled;
    private String fillColor;
    private String lineColor;
    private String lineWidth;
    private String radius;
    private String symbol;
}
