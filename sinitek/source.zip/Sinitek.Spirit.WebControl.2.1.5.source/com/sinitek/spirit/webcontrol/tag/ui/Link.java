// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Link.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Link extends Component
{

    public Link(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "a";
    }

    protected String getDefaultTemplate()
    {
        return "a-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "link");
        addProperty("href", href);
        addProperty("target", target);
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    private static final String TEMPLATE = "a";
    private static final String TEMPLATE_CLOSE = "a-close";
    private String href;
    private String target;
}
