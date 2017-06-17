// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tabs.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Box

public class Tabs extends Box
{

    public Tabs(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "tabs";
    }

    protected String getDefaultTemplate()
    {
        return "tabs-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "tabs");
        addProperty("disabled", disabled);
        addProperty("selected", selected);
        if(StringUtils.isNotBlank(tabWidth))
            tabWidth = tabWidth.replaceAll("px", "");
        addProperty("tabWidth", tabWidth);
    }

    public void setDisabled(String disabled)
    {
        this.disabled = disabled;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
    }

    public void setTabWidth(String tabWidth)
    {
        this.tabWidth = tabWidth;
    }

    private static final String TEMPLATE = "tabs";
    private static final String TEMPLATE_CLOSE = "tabs-close";
    private String disabled;
    private String selected;
    private String tabWidth;
}
