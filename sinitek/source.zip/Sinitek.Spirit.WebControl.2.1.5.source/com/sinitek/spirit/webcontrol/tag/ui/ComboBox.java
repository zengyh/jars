// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComboBox.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Select

public class ComboBox extends Select
{

    public ComboBox(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "combobox";
    }

    protected String getDefaultTemplate()
    {
        return "combobox-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "combobox");
        addProperty("defaultText", defaultText);
        addProperty("direction", direction, "bottom");
        addProperty("width", width, "120px");
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public void setDefaultText(String defaultText)
    {
        this.defaultText = defaultText;
    }

    private static final String TEMPLATE = "combobox";
    private static final String TEMPLATE_CLOSE = "combobox-close";
    private String direction;
    private String defaultText;
}
