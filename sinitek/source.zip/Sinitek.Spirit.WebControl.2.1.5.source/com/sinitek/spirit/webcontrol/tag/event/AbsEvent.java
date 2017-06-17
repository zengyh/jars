// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsEvent.java

package com.sinitek.spirit.webcontrol.tag.event;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import java.util.Map;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.components.ClosingUIBean;

abstract class AbsEvent extends ClosingUIBean
{

    AbsEvent(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        Map parent = getParentParam();
        if(parent != null)
        {
            addProperty("targetId", (String)parent.get("id"));
            addProperty("componentName", (String)parent.get("componentName"));
        }
        addProperty("dataVar", dataVar);
        addProperty("valVar", valVar);
        addProperty("checkedVar", checkedVar);
        addProperty("textVar", textVar);
        addProperty("keyCodeVar", keyCodeVar);
        addProperty("mouseXVar", mouseXVar);
        addProperty("mouseYVar", mouseYVar);
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

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tag/event/AbsEvent);
    private String dataVar;
    private String valVar;
    private String checkedVar;
    private String textVar;
    private String keyCodeVar;
    private String mouseXVar;
    private String mouseYVar;

}
