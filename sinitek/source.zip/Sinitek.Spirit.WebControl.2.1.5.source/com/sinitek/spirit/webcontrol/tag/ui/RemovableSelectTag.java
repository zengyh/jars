// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemovableSelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListFieldTag, RemovableSelect

public class RemovableSelectTag extends ListFieldTag
{

    public RemovableSelectTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new RemovableSelect(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        RemovableSelect select = (RemovableSelect)component;
        select.setHeight(height);
        select.setRemoveFunction(removeFunction);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setRemoveFunction(String removeFunction)
    {
        this.removeFunction = removeFunction;
    }

    private String height;
    private String removeFunction;
}
