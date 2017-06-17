// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OntableorderTag.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.event:
//            AbsEventTag, Ontableorder

public class OntableorderTag extends AbsEventTag
{

    public OntableorderTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Ontableorder(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Ontableorder evt = (Ontableorder)component;
        evt.setPropertyVar(propertyVar);
        evt.setOrderTypeVar(orderTypeVar);
    }

    public void setPropertyVar(String property)
    {
        propertyVar = property;
    }

    public void setOrderTypeVar(String orderType)
    {
        orderTypeVar = orderType;
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

    private String propertyVar;
    private String orderTypeVar;
}
