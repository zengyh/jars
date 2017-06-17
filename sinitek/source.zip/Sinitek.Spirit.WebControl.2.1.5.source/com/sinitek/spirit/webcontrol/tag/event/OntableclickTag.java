// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OntableclickTag.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.event:
//            AbsEventTag, Ontableclick

public class OntableclickTag extends AbsEventTag
{

    public OntableclickTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Ontableclick(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Ontableclick evt = (Ontableclick)component;
        evt.setDataVar(dataVar);
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

    private String dataVar;
}
