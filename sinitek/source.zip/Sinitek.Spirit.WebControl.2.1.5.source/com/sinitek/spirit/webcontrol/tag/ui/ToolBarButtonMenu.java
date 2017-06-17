// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToolBarButtonMenu.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ButtonMenu

public class ToolBarButtonMenu extends ButtonMenu
{

    public ToolBarButtonMenu(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "toolbarbuttonmenu";
    }

    protected String getDefaultTemplate()
    {
        return "toolbarbuttonmenu-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "toolbarbuttonmenu");
    }

    private static final String TEMPLATE = "toolbarbuttonmenu";
    private static final String TEMPLATE_CLOSE = "toolbarbuttonmenu-close";
}
