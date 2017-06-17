// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartXAxisTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartXAxis

public class ChartXAxisTag extends AbsChartTag
{

    public ChartXAxisTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartXAxis(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartXAxis param = (ChartXAxis)component;
        param.setCategories(categories);
        param.setShowFirstLabel(showFirstLabel);
        param.setShowLastLabel(showLastLabel);
        param.setTickInterval(tickInterval);
        param.setMaxZoom(maxZoom);
        param.setMax(max);
        param.setMin(min);
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

    private String categories;
    private String showFirstLabel;
    private String showLastLabel;
    private String tickInterval;
    private String maxZoom;
    private String max;
    private String min;
}
