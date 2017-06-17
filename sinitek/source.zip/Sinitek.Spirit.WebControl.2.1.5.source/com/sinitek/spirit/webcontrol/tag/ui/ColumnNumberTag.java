// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnNumberTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnNumber

public class ColumnNumberTag extends ColumnTag
{

    public ColumnNumberTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnNumber(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnNumber cmp = (ColumnNumber)component;
        cmp.setPointPlace(pointPlace);
        cmp.setIsMoney(isMoney);
    }

    public void setPointPlace(String pointPlace)
    {
        this.pointPlace = pointPlace;
    }

    public void setIsMoney(String money)
    {
        isMoney = money;
    }

    public void setMoney(String money)
    {
        isMoney = money;
    }

    private String pointPlace;
    private String isMoney;
}
