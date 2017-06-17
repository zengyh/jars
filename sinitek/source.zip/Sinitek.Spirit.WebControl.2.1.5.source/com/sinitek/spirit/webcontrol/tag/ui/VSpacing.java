// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VSpacing.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class VSpacing extends Component
{

    public VSpacing(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "vspacing";
    }

    protected String getDefaultTemplate()
    {
        return "vspacing-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "vspacing");
        addProperty("height", height, "0");
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    private static final String TEMPLATE = "vspacing";
    private static final String TEMPLATE_CLOSE = "vspacing-close";
    private String height;
}
