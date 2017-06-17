// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartLegendTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartLegend

public class ChartLegendTag extends AbsChartTag
{

    public ChartLegendTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartLegend(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartLegend param = (ChartLegend)component;
        param.setAlign(align);
        param.setMargin(margin);
        param.setLayout(layout);
        param.setVerticalAlign(verticalAlign);
        param.setX(x);
        param.setY(y);
        param.setFloating(floating);
        param.setEnabled(enabled);
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setMargin(String margin)
    {
        this.margin = margin;
    }

    public void setVerticalAlign(String verticalAlign)
    {
        this.verticalAlign = verticalAlign;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

    public void setFloating(String floating)
    {
        this.floating = floating;
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    private String align;
    private String margin;
    private String layout;
    private String verticalAlign;
    private String x;
    private String y;
    private String floating;
    private String enabled;
}
