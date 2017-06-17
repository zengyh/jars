// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginRowNum.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public class ColPluginRowNum extends AbsColumnPlugin
{

    public ColPluginRowNum(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colplugin-rownum";
    }

    protected String getDefaultTemplate()
    {
        return "colplugin-rownum-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colplugin");
        addProperty("type", "rownum");
    }

    private static final String TEMPLATE = "colplugin-rownum";
    private static final String TEMPLATE_CLOSE = "colplugin-rownum-close";
}
