// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComboBoxTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            SelectTag, ComboBox

public class ComboBoxTag extends SelectTag
{

    public ComboBoxTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ComboBox(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ComboBox cmp = (ComboBox)component;
        cmp.setDefaultText(defaultText);
        cmp.setDirection(direction);
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public void setDefaultText(String defaultText)
    {
        this.defaultText = defaultText;
    }

    private String direction;
    private String defaultText;
}
