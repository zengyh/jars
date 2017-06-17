// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginRadioBoxTag.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPluginTag, ColPluginRadioBox

public class ColPluginRadioBoxTag extends AbsColumnPluginTag
{

    public ColPluginRadioBoxTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColPluginRadioBox(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColPluginRadioBox cmp = (ColPluginRadioBox)component;
        cmp.setName(name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name;
}
