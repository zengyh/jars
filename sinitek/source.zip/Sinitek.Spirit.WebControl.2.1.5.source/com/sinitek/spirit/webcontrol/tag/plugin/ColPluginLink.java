// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginLink.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public class ColPluginLink extends AbsColumnPlugin
{

    public ColPluginLink(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colplugin-link";
    }

    protected String getDefaultTemplate()
    {
        return "colplugin-link-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colplugin");
        addProperty("type", "link");
        addProperty("text", text);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    private static final String TEMPLATE = "colplugin-link";
    private static final String TEMPLATE_CLOSE = "colplugin-link-close";
    private String text;
}
