// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class StarSelect extends Field
{

    public StarSelect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "selectstar";
    }

    protected String getDefaultTemplate()
    {
        return "selectstar-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "starselect");
        addProperty("maxStar", maxStar, "5");
    }

    public void setMaxStar(String maxStar)
    {
        this.maxStar = maxStar;
    }

    private static final String TEMPLATE = "selectstar";
    private static final String TEMPLATE_CLOSE = "selectstar-close";
    private String maxStar;
}
