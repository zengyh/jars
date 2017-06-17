// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnDateTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnDate

public class ColumnDateTag extends ColumnTag
{

    public ColumnDateTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnDate(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnDate cmp = (ColumnDate)component;
        cmp.setFormat(format);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    private String format;
}
