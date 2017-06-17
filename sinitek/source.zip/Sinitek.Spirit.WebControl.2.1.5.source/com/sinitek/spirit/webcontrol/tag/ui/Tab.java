// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tab.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Box

public class Tab extends Box
{

    public Tab(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "tab";
    }

    protected String getDefaultTemplate()
    {
        return "tab-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "tab");
        addProperty("title", title);
        addProperty("width", width, "100%");
        Map parent = getParentParam();
        if(parent != null)
            addProperty("parentId", parent.get("id"));
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private static final String TEMPLATE = "tab";
    private static final String TEMPLATE_CLOSE = "tab-close";
    private String title;
}
