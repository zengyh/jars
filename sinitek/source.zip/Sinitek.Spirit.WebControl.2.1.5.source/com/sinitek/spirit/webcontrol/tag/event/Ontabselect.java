// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Ontabselect.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.event:
//            AbsEvent

public class Ontabselect extends AbsEvent
{

    public Ontabselect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "event-ontabselect";
    }

    protected String getDefaultTemplate()
    {
        return "event-ontabselect-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("eventName", "tabsselect");
        addProperty("tabVar", tabVar);
    }

    public void setTabVar(String tabVar)
    {
        this.tabVar = tabVar;
    }

    public volatile void setDataVar(String x0)
    {
        super.setDataVar(x0);
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

    private static final String TEMPLATE = "event-ontabselect";
    private static final String TEMPLATE_CLOSE = "event-ontabselect-close";
    private String tabVar;
}
