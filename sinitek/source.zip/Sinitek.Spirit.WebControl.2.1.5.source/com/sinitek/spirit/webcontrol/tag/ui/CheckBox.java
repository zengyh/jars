// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckBox.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class CheckBox extends Field
{

    public CheckBox(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "checkbox";
    }

    protected String getDefaultTemplate()
    {
        return "checkbox-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "checkbox");
        addProperty("checked", checked, "false");
        addProperty("swichStyle", swichStyle);
    }

    public void setSwichStyle(String swichStyle)
    {
        this.swichStyle = swichStyle;
    }

    public void setChecked(String checked)
    {
        this.checked = checked;
    }

    private static final String TEMPLATE = "checkbox";
    private static final String TEMPLATE_CLOSE = "checkbox-close";
    private String checked;
    private String swichStyle;
}
