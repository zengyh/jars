// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartRangeSelectorTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartRangeSelector

public class ChartRangeSelectorTag extends AbsChartTag
{

    public ChartRangeSelectorTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartRangeSelector(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartRangeSelector param = (ChartRangeSelector)component;
        param.setEnabled(enabled);
        param.setButtons(buttons);
        param.setSelected(selected);
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

    private String enabled;
    private String buttons;
    private String selected;
}
