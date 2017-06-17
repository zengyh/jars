// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginRadioBox.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public class ColPluginRadioBox extends AbsColumnPlugin
{

    public ColPluginRadioBox(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colplugin-radiobox";
    }

    protected String getDefaultTemplate()
    {
        return "colplugin-radiobox-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colplugin");
        addProperty("type", "radiobox");
        addProperty("name", name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private static final String TEMPLATE = "colplugin-radiobox";
    private static final String TEMPLATE_CLOSE = "colplugin-radiobox-close";
    private String name;
}
