// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProgressBar.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class ProgressBar extends Field
{

    public ProgressBar(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "pb";
    }

    protected String getDefaultTemplate()
    {
        return "pb-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "pb");
        addProperty("showText", showText, "true");
        addProperty("max", max, Integer.valueOf(100));
        addProperty("textFormat", textFormat, "percentage");
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

    private static final String TEMPLATE = "pb";
    private static final String TEMPLATE_CLOSE = "pb-close";
    private String showText;
    private String max;
    private String textFormat;
}
