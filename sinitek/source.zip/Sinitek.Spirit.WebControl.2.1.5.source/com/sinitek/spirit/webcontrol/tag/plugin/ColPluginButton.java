// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginButton.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public class ColPluginButton extends AbsColumnPlugin
{

    public ColPluginButton(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colplugin-button";
    }

    protected String getDefaultTemplate()
    {
        return "colplugin-button-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colplugin");
        addProperty("type", "button");
        addProperty("text", text);
        addProperty("icon", icon);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    private static final String TEMPLATE = "colplugin-button";
    private static final String TEMPLATE_CLOSE = "colplugin-button-close";
    private String text;
    private String icon;
}
