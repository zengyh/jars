// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckBoxGroup.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListField

public class CheckBoxGroup extends ListField
{

    public CheckBoxGroup(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "checkboxgroup";
    }

    protected String getDefaultTemplate()
    {
        return "checkboxgroup-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "checkboxgroup");
        addProperty("width", width, "99%");
        addProperty("height", height);
        addProperty("columnWidth", columnWidth, "120px");
        addProperty("columnHeight", columnHeight, "30px");
        addProperty("checkedValue", value);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setColumnWidth(String columnWidth)
    {
        this.columnWidth = columnWidth;
    }

    public void setColumnHeight(String columnHeight)
    {
        this.columnHeight = columnHeight;
    }

    private static final String TEMPLATE = "checkboxgroup";
    private static final String TEMPLATE_CLOSE = "checkboxgroup-close";
    private String width;
    private String height;
    private String columnWidth;
    private String columnHeight;
}
