// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartTitleTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartTitle

public class ChartTitleTag extends AbsChartTag
{

    public ChartTitleTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartTitle(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartTitle param = (ChartTitle)component;
        param.setAlign(align);
        param.setMargin(margin);
        param.setText(text);
        param.setVerticalAlign(verticalAlign);
        param.setX(x);
        param.setY(y);
        param.setRotation(rotation);
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setMargin(String margin)
    {
        this.margin = margin;
    }

    public void setText(String text)
    {
        this.text = text;
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

    public void setRotation(String rotation)
    {
        this.rotation = rotation;
    }

    private String align;
    private String margin;
    private String text;
    private String verticalAlign;
    private String x;
    private String y;
    private String rotation;
}
