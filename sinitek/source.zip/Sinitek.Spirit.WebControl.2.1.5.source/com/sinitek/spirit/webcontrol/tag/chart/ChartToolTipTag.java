// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartToolTipTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartToolTip

public class ChartToolTipTag extends AbsChartTag
{

    public ChartToolTipTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartToolTip(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartToolTip param = (ChartToolTip)component;
        param.setCrosshairs(crosshairs);
        param.setShared(shared);
    }

    public void setCrosshairs(String crosshairs)
    {
        this.crosshairs = crosshairs;
    }

    public void setShared(String shared)
    {
        this.shared = shared;
    }

    private String crosshairs;
    private String shared;
}
