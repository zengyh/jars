// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RadioBoxGroup.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            CheckBoxGroup

public class RadioBoxGroup extends CheckBoxGroup
{

    public RadioBoxGroup(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "radioboxgroup";
    }

    protected String getDefaultTemplate()
    {
        return "radioboxgroup-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "radioboxgroup");
    }

    private static final String TEMPLATE = "radioboxgroup";
    private static final String TEMPLATE_CLOSE = "radioboxgroup-close";
}
