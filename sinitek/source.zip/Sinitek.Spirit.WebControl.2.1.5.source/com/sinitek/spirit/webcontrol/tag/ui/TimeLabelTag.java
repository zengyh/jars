// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeLabelTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            LabelTag, TimeLabel

public class TimeLabelTag extends LabelTag
{

    public TimeLabelTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new TimeLabel(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        TimeLabel cmp = (TimeLabel)component;
        cmp.setFormat(format);
        cmp.setDefaultValue(defaultValue);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    private String format;
    private Object defaultValue;
}
