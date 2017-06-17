// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ButtonMenuTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ButtonTag, ButtonMenu

public class ButtonMenuTag extends ButtonTag
{

    public ButtonMenuTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ButtonMenu(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ButtonMenu cmp = (ButtonMenu)component;
        cmp.setTargetMenu(targetMenu);
        cmp.setClickToShow(clickToShow);
    }

    public void setTargetMenu(String targetMenu)
    {
        this.targetMenu = targetMenu;
    }

    public void setClickToShow(String clickToShow)
    {
        this.clickToShow = clickToShow;
    }

    private String targetMenu;
    private String clickToShow;
}
