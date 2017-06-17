// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartPlotOptionTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartPlotOption

public class ChartPlotOptionTag extends AbsChartTag
{

    public ChartPlotOptionTag()
    {
    }

    public org.apache.struts2.components.Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartPlotOption(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartPlotOption evt = (ChartPlotOption)component;
        evt.setType(type);
        evt.setPointStart(pointStart);
        evt.setPointInterval(pointInterval);
        evt.setVisible(visible);
        evt.setzIndex(zIndex);
        evt.setLineWidth(lineWidth);
        evt.setStacking(stacking);
        evt.setAllowPointSelect(allowPointSelect);
        evt.setCursor(cursor);
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

    public void setType(String type)
    {
        this.type = type;
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

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tag/ui/Component);
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
