// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Panel.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Box

public class Panel extends Box
{

    public Panel(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "panel";
    }

    protected String getDefaultTemplate()
    {
        return "panel-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "panel");
        addProperty("collapsed", collapsed, "false");
        addProperty("collapsible", collapsible, "false");
        addProperty("title", title);
        addProperty("width", width, "100%");
    }

    public void setCollapsed(String collapsed)
    {
        this.collapsed = collapsed;
    }

    public void setCollapsible(String collapsible)
    {
        this.collapsible = collapsible;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private static final String TEMPLATE = "panel";
    private static final String TEMPLATE_CLOSE = "panel-close";
    String collapsed;
    String collapsible;
    String title;
}
