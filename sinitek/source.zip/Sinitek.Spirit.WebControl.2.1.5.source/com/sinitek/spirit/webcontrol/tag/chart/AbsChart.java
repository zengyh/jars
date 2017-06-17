// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsChart.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import java.util.Map;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.components.ClosingUIBean;

abstract class AbsChart extends ClosingUIBean
{

    AbsChart(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
    }

    void addProperty(String name, String value)
    {
        if(value != null)
            addParameter(name, value);
    }

    protected void addProperty(String name, String value, String defaultValue)
    {
        if(defaultValue != null)
        {
            if(value == null)
                addParameter(name, defaultValue);
            else
                addParameter(name, value);
        } else
        {
            addProperty(name, value);
        }
    }

    Map getParentParam()
    {
        Stack stack = getComponentStack();
        if(stack.size() > 1)
            return ((Component)stack.get(stack.size() - 2)).getParameters();
        else
            return null;
    }

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tag/chart/AbsChart);

}
