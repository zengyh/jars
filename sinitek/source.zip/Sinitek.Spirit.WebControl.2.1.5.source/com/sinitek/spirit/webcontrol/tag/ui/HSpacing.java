// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HSpacing.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class HSpacing extends Component
{

    public HSpacing(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "hspacing";
    }

    protected String getDefaultTemplate()
    {
        return "hspacing-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "hspacing");
        addProperty("width", width);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    private static final String TEMPLATE = "hspacing";
    private static final String TEMPLATE_CLOSE = "hspacing-close";
    private String width;
}
