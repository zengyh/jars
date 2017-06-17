// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PanelTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            BoxTag, Panel

public class PanelTag extends BoxTag
{

    public PanelTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Panel(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Panel cmp = (Panel)component;
        cmp.setCollapsed(collapsed);
        cmp.setCollapsible(collapsible);
        cmp.setTitle(title);
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

    private String collapsed;
    private String collapsible;
    private String title;
}
