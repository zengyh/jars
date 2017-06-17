// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginButtonTag.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPluginTag, ColPluginButton

public class ColPluginButtonTag extends AbsColumnPluginTag
{

    public ColPluginButtonTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColPluginButton(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColPluginButton cmp = (ColPluginButton)component;
        cmp.setText(text);
        cmp.setIcon(icon);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    private String text;
    private String icon;
}
