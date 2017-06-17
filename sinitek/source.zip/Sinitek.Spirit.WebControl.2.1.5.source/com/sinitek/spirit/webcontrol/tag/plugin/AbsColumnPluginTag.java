// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsColumnPluginTag.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.sinitek.spirit.webcontrol.tag.ui.ComponentTag;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.plugin:
//            AbsColumnPlugin

public abstract class AbsColumnPluginTag extends ComponentTag
{

    public AbsColumnPluginTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        AbsColumnPlugin cmp = (AbsColumnPlugin)component;
        cmp.setDataVar(dataVar);
        cmp.setCheckedVar(checkedVar);
    }

    public void setDataVar(String dataVar)
    {
        this.dataVar = dataVar;
    }

    public void setCheckedVar(String checkedVar)
    {
        this.checkedVar = checkedVar;
    }

    private String dataVar;
    private String checkedVar;
}
