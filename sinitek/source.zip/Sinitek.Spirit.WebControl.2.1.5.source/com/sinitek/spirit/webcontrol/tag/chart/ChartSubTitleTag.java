// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartSubTitleTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            ChartTitleTag, ChartSubTitle

public class ChartSubTitleTag extends ChartTitleTag
{

    public ChartSubTitleTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartSubTitle(stack, req, res);
    }
}
