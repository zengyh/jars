// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Aftertablequery.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.event:
//            AbsEvent

public class Aftertablequery extends AbsEvent
{

    public Aftertablequery(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "event-aftertablequery";
    }

    protected String getDefaultTemplate()
    {
        return "event-aftertablequery-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("eventName", "aftertablequery");
        addProperty("dataVar", dataVar);
    }

    public void setDataVar(String dataVar)
    {
        this.dataVar = dataVar;
    }

    public volatile void setMouseYVar(String x0)
    {
        super.setMouseYVar(x0);
    }

    public volatile void setMouseXVar(String x0)
    {
        super.setMouseXVar(x0);
    }

    public volatile void setKeyCodeVar(String x0)
    {
        super.setKeyCodeVar(x0);
    }

    public volatile void setTextVar(String x0)
    {
        super.setTextVar(x0);
    }

    public volatile void setCheckedVar(String x0)
    {
        super.setCheckedVar(x0);
    }

    public volatile void setValVar(String x0)
    {
        super.setValVar(x0);
    }

    private static final String TEMPLATE = "event-aftertablequery";
    private static final String TEMPLATE_CLOSE = "event-aftertablequery-close";
    private String dataVar;
}