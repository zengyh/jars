// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Button.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class Button extends Field
{

    public Button(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "button";
    }

    protected String getDefaultTemplate()
    {
        return "button-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "button");
        addProperty("icon", icon);
        addProperty("text", text);
        addProperty("type", type, "button");
        addProperty("targetMenu", targetMenu);
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setTargetMenu(String targetMenu)
    {
        this.targetMenu = targetMenu;
    }

    private static final String TEMPLATE = "button";
    private static final String TEMPLATE_CLOSE = "button-close";
    private String icon;
    private String type;
    private String text;
    private String targetMenu;
}
