// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsEventTag.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.sinitek.spirit.webcontrol.tag.ui.Component;
import org.apache.log4j.Logger;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.event:
//            AbsEvent

abstract class AbsEventTag extends AbstractClosingTag
{

    AbsEventTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        AbsEvent evt = (AbsEvent)component;
        evt.setDataVar(dataVar);
        evt.setValVar(valVar);
        evt.setCheckedVar(checkedVar);
        evt.setTextVar(textVar);
        evt.setKeyCodeVar(keyCodeVar);
        evt.setMouseXVar(mouseXVar);
        evt.setMouseYVar(mouseYVar);
    }

    public void setValVar(String valVar)
    {
        this.valVar = valVar;
    }

    public void setCheckedVar(String checkedVar)
    {
        this.checkedVar = checkedVar;
    }

    public void setTextVar(String textVar)
    {
        this.textVar = textVar;
    }

    public void setKeyCodeVar(String keyCodeVar)
    {
        this.keyCodeVar = keyCodeVar;
    }

    public void setMouseXVar(String mouseXVar)
    {
        this.mouseXVar = mouseXVar;
    }

    public void setMouseYVar(String mouseYVar)
    {
        this.mouseYVar = mouseYVar;
    }

    public void setDataVar(String dataVar)
    {
        this.dataVar = dataVar;
    }

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tag/ui/Component);
    private String dataVar;
    private String valVar;
    private String checkedVar;
    private String textVar;
    private String keyCodeVar;
    private String mouseXVar;
    private String mouseYVar;

}
