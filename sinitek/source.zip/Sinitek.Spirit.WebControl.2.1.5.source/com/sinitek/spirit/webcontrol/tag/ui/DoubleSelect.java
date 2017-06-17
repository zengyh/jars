// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoubleSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Select

public class DoubleSelect extends Select
{

    public DoubleSelect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "doubleselect";
    }

    protected String getDefaultTemplate()
    {
        return "doubleselect-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "doubleselect");
        addPropertyNoPx("width", width);
        addPropertyNoPx("height", height);
        addPropertyNoPx("selectWidth", selectWidth);
        addProperty("selectTitle", selectTitle);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setSelectTitle(String selectTitle)
    {
        this.selectTitle = selectTitle;
    }

    public void setSelectWidth(String selectWidth)
    {
        this.selectWidth = selectWidth;
    }

    private static final String TEMPLATE = "doubleselect";
    private static final String TEMPLATE_CLOSE = "doubleselect-close";
    private String height;
    private String selectTitle;
    private String selectWidth;
}
