// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TabsTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            BoxTag, Tabs

public class TabsTag extends BoxTag
{

    public TabsTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Tabs(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Tabs cmp = (Tabs)component;
        cmp.setDisabled(disabled);
        cmp.setSelected(selected);
        cmp.setTabWidth(tabWidth);
    }

    public void setDisabled(String disabled)
    {
        this.disabled = disabled;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
    }

    public void setTabWidth(String tabWidth)
    {
        this.tabWidth = tabWidth;
    }

    private String disabled;
    private String selected;
    private String tabWidth;
}
