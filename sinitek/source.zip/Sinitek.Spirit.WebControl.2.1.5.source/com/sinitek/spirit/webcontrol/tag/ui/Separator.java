// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Separator.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Separator extends Component
{

    public Separator(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "separator";
    }

    protected String getDefaultTemplate()
    {
        return "separator-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "separator");
        addProperty("width", width, "16px");
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    private static final String TEMPLATE = "separator";
    private static final String TEMPLATE_CLOSE = "separator-close";
    private String width;
}
