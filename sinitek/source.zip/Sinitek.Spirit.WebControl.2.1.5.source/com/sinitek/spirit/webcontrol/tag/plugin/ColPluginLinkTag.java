// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginLinkTag.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPluginTag, ColPluginLink

public class ColPluginLinkTag extends AbsColumnPluginTag
{

    public ColPluginLinkTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColPluginLink(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColPluginLink cmp = (ColPluginLink)component;
        cmp.setText(text);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    private String text;
}
