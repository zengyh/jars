// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ButtonTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, Button

public class ButtonTag extends FieldTag
{

    public ButtonTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Button(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Button cmp = (Button)component;
        cmp.setIcon(icon);
        cmp.setText(text);
        cmp.setType(type);
        cmp.setTargetMenu(targetMenu);
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setTargetMenu(String targetMenu)
    {
        this.targetMenu = targetMenu;
    }

    private String icon;
    private String type;
    private String text;
    private String targetMenu;
}
