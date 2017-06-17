// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableToolBarTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ToolBarTag, TableToolBar

public class TableToolBarTag extends ToolBarTag
{

    public TableToolBarTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new TableToolBar(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        TableToolBar cmp = (TableToolBar)component;
        cmp.setPosition(position);
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    private String position;
}
