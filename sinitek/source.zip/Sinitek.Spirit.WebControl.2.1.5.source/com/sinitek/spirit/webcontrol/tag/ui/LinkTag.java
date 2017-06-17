// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LinkTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Link

public class LinkTag extends ComponentTag
{

    public LinkTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Link(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Link cmp = (Link)component;
        cmp.setHref(href);
        cmp.setTarget(target);
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    private String href;
    private String target;
}
