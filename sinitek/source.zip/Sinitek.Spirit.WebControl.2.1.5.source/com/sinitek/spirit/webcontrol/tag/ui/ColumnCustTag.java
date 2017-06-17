// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnCustTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnCust

public class ColumnCustTag extends ColumnTag
{

    public ColumnCustTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnCust(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnCust cmp = (ColumnCust)component;
        cmp.setClazz(clazz);
        cmp.setMethod(method);
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    private String clazz;
    private String method;
}
