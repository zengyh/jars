// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Label.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Label extends Component
{

    public Label(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "label";
    }

    protected String getDefaultTemplate()
    {
        return "label-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "label");
        addProperty("width", width);
        addProperty("align", align, "left");
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    private static final String TEMPLATE = "label";
    private static final String TEMPLATE_CLOSE = "label-close";
    private String width;
    private String align;
}
