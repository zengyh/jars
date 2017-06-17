// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColPluginCheckBoxTag.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPluginTag, ColPluginCheckBox

public class ColPluginCheckBoxTag extends AbsColumnPluginTag
{

    public ColPluginCheckBoxTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColPluginCheckBox(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColPluginCheckBox cmp = (ColPluginCheckBox)component;
        cmp.setName(name);
        cmp.setSelectAll(selectAll);
        cmp.setAllowCache(allowCache);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSelectAll(String selectAll)
    {
        this.selectAll = selectAll;
    }

    public void setAllowCache(String allowCache)
    {
        this.allowCache = allowCache;
    }

    private String name;
    private String selectAll;
    private String allowCache;
}
