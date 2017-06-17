// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Component.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.components.ClosingUIBean;

public abstract class Component extends ClosingUIBean
{

    public Component(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("id", id);
        addProperty("hidden", hidden, "false");
        addProperty("hideMode", hideMode, "display");
        addProperty("feature", feature);
        addProperty("themeName", themeName);
    }

    protected void addProperty(String name, Object value)
    {
        if(value != null)
            addParameter(name, value);
    }

    void addPropertyNoPx(String name, String value)
    {
        if(value != null)
        {
            value = value.replaceAll("px", "");
            addParameter(name, value);
        }
    }

    protected Object getProperty(String name)
    {
        return getParameters().get("name");
    }

    protected void addProperty(String name, Object value, Object defaultValue)
    {
        if((value instanceof String) && "".equals(value))
            value = null;
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

    protected Map getParentParam()
    {
        Stack stack = getComponentStack();
        if(stack.size() > 1)
            return ((Component)stack.get(stack.size() - 2)).getParameters();
        else
            return null;
    }

    public void setHidden(String hidden)
    {
        this.hidden = hidden;
    }

    public void setHideMode(String hideMode)
    {
        this.hideMode = hideMode;
    }

    public void setFeature(String feature)
    {
        this.feature = feature;
    }

    public void setThemeName(String themeName)
    {
        this.themeName = themeName;
    }

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tag/ui/Component);
    private String hidden;
    private String hideMode;
    String feature;
    private String themeName;

}
