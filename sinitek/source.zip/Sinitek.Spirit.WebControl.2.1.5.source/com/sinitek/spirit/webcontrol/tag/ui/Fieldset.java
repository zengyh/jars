// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Fieldset.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Panel

public class Fieldset extends Panel
{

    public Fieldset(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "fieldset";
    }

    protected String getDefaultTemplate()
    {
        return "fieldset-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "fieldset");
        addProperty("collapsed", collapsed, "false");
        addProperty("collapsible", collapsible, "false");
        addProperty("title", title);
    }

    private static final String TEMPLATE = "fieldset";
    private static final String TEMPLATE_CLOSE = "fieldset-close";
}
