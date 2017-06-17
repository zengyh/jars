// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginCheckBox.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public class ColPluginCheckBox extends AbsColumnPlugin
{

    public ColPluginCheckBox(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "colplugin-checkbox";
    }

    protected String getDefaultTemplate()
    {
        return "colplugin-checkbox-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "colplugin");
        addProperty("type", "checkbox");
        addProperty("name", name);
        addProperty("selectAll", selectAll, "true");
        addProperty("allowCache", allowCache, "false");
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSelectAll(String selectAll)
    {
        this.selectAll = selectAll;
    }

    public void setAllowCache(String allowCache)
    {
        this.allowCache = allowCache;
    }

    private static final String TEMPLATE = "colplugin-checkbox";
    private static final String TEMPLATE_CLOSE = "colplugin-checkbox-close";
    private String name;
    private String selectAll;
    private String allowCache;
}
