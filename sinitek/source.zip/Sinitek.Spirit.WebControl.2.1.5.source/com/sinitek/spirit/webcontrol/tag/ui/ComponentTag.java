// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComponentTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.inject.Container;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public abstract class ComponentTag extends AbstractClosingTag
{

    public ComponentTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        com.sinitek.spirit.webcontrol.tag.ui.Component cmp = (com.sinitek.spirit.webcontrol.tag.ui.Component)component;
        if((id instanceof String) && "".equals(id))
            id = null;
        if((key instanceof String) && "".equals(key))
            key = null;
        if(id == null && key == null)
            cmp.setId((new StringBuilder()).append("UUID").append(UUID.randomUUID().toString().replaceAll("-", "")).toString());
        cmp.setHidden(hidden);
        cmp.setHideMode(hideMode);
        cmp.setFeature(feature);
        cmp.setThemeName(themeName);
    }

    public int doEndTag()
    {
        StringWriter sw = new StringWriter();
        component.end(sw, getBody());
        try
        {
            pageContext.getOut().println(sw.getBuffer().toString());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        component = null;
        return 6;
    }

    public int doStartTag()
    {
        component = getBean(getStack(), (HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        StringWriter sw = new StringWriter();
        boolean evalBody = component.start(sw);
        try
        {
            pageContext.getOut().println(sw.getBuffer().toString());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        if(evalBody)
            return component.usesBody() ? 2 : 1;
        else
            return 0;
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
    private String feature;
    private String themeName;

}
