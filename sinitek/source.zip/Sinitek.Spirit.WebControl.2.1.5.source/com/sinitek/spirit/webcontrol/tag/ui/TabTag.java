// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TabTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            BoxTag, Tab

public class TabTag extends BoxTag
{

    public TabTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Tab(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Tab cmp = (Tab)component;
        cmp.setTitle(title);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private String title;
}