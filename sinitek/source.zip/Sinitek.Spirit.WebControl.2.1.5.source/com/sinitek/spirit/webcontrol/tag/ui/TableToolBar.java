// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableToolBar.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ToolBar

public class TableToolBar extends ToolBar
{

    public TableToolBar(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "tabletoolbar";
    }

    protected String getDefaultTemplate()
    {
        return "tabletoolbar-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "tabletoolbar");
        addProperty("position", position);
        Map parent = getParentParam();
        if(parent != null)
            addProperty("tableId", parent.get("id"));
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    private static final String TEMPLATE = "tabletoolbar";
    private static final String TEMPLATE_CLOSE = "tabletoolbar-close";
    private String position;
}
