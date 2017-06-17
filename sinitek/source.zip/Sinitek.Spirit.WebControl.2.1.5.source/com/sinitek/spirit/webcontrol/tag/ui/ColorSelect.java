// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColorSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class ColorSelect extends Field
{

    public ColorSelect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colorselect";
    }

    protected String getDefaultTemplate()
    {
        return "colorselect-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colorselect");
        addProperty("simple", simple, "false");
    }

    public void setSimple(String simple)
    {
        this.simple = simple;
    }

    private static final String TEMPLATE = "colorselect";
    private static final String TEMPLATE_CLOSE = "colorselect-close";
    private String simple;
}
