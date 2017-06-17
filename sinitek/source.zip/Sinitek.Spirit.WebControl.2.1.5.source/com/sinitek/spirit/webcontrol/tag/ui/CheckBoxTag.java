// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckBoxTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, CheckBox

public class CheckBoxTag extends FieldTag
{

    public CheckBoxTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new CheckBox(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        CheckBox cmp = (CheckBox)component;
        cmp.setChecked(checked);
        cmp.setSwichStyle(swichStyle);
    }

    public void setSwichStyle(String swichStyle)
    {
        this.swichStyle = swichStyle;
    }

    public void setChecked(String checked)
    {
        this.checked = checked;
    }

    private String checked;
    private String swichStyle;
}
