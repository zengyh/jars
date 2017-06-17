// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProgressBarTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, ProgressBar

public class ProgressBarTag extends FieldTag
{

    public ProgressBarTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ProgressBar(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ProgressBar cmp = (ProgressBar)component;
        cmp.setShowText(showText);
        cmp.setMax(max);
        cmp.setTextFormat(textFormat);
    }

    public void setShowText(String showText)
    {
        this.showText = showText;
    }

    public void setMax(String max)
    {
        this.max = max;
    }

    public void setTextFormat(String textFormat)
    {
        this.textFormat = textFormat;
    }

    private String showText;
    private String max;
    private String textFormat;
}
