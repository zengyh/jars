// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableFixTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TableTag, TableFix

public class TableFixTag extends TableTag
{

    public TableFixTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new TableFix(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        TableFix cmp = (TableFix)component;
        cmp.setFixWidth(fixWidth);
        cmp.setAdjustRow(adjustRow);
    }

    public void setFixWidth(String fixWidth)
    {
        this.fixWidth = fixWidth;
    }

    public void setAdjustRow(String adjustRow)
    {
        this.adjustRow = adjustRow;
    }

    private String fixWidth;
    private String adjustRow;
}
