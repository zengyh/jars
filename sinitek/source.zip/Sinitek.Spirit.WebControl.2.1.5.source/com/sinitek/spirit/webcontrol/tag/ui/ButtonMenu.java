// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ButtonMenu.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Button

public class ButtonMenu extends Button
{

    public ButtonMenu(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "buttonmenu";
    }

    protected String getDefaultTemplate()
    {
        return "buttonmenu-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "buttonmenu");
        addProperty("targetMenu", targetMenu);
        addProperty("clickToShow", clickToShow, "true");
    }

    public void setTargetMenu(String targetMenu)
    {
        this.targetMenu = targetMenu;
    }

    public void setClickToShow(String clickToShow)
    {
        this.clickToShow = clickToShow;
    }

    private static final String TEMPLATE = "buttonmenu";
    private static final String TEMPLATE_CLOSE = "buttonmenu-close";
    private String targetMenu;
    private String clickToShow;
}
