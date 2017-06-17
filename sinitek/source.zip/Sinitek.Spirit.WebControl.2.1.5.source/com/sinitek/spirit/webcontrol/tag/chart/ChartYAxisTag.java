// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartYAxisTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            ChartXAxisTag, ChartYAxis

public class ChartYAxisTag extends ChartXAxisTag
{

    public ChartYAxisTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartYAxis(stack, req, res);
    }
}
