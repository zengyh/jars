// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextField.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class TextField extends Field
{

    public TextField(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "textfield";
    }

    protected String getDefaultTemplate()
    {
        return "textfield-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("size", size);
        addProperty("componentName", "textfield");
        addProperty("emptyText", emptyText);
        addProperty("grow", grow, "false");
        if("true".equals(grow))
        {
            addProperty("growMax", growMax, "100");
            addProperty("growMin", growMin, "1");
        }
        addProperty("mask", mask);
        addProperty("maskConfig", maskConfig);
        addProperty("maxLength", maxLength);
        addProperty("selectOnFocus", selectOnFocus, "false");
        addProperty("type", type, "text");
        addProperty("textAlign", textAlign);
        addProperty("icon", icon);
        addProperty("areaRows", areaRows, "5");
        addProperty("areaWidth", areaWidth, "300px");
        addProperty("areaEdit", areaEdit, "false");
        addProperty("passwordStrength", passwordStrength, "false");
        if("true".equals(areaEdit))
        {
            addParameter("icon", "ui-icon-extlink");
            addParameter("rows", "5");
        }
    }

    public void setEmptyText(String emptyText)
    {
        this.emptyText = emptyText;
    }

    public void setGrow(String grow)
    {
        this.grow = grow;
    }

    public void setGrowMax(String growMax)
    {
        this.growMax = growMax;
    }

    public void setGrowMin(String growMin)
    {
        this.growMin = growMin;
    }

    public void setMask(String mask)
    {
        this.mask = mask;
    }

    public void setMaskConfig(String maskConfig)
    {
        this.maskConfig = maskConfig;
    }

    public void setMaxLength(String maxLength)
    {
        this.maxLength = maxLength;
    }

    public void setSelectOnFocus(String selectOnFocus)
    {
        this.selectOnFocus = selectOnFocus;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public void setTextAlign(String textAlign)
    {
        this.textAlign = textAlign;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public void setAreaEdit(String areaEdit)
    {
        this.areaEdit = areaEdit;
    }

    public void setAreaRows(String areaRows)
    {
        this.areaRows = areaRows;
    }

    public void setPasswordStrength(String passwordStrength)
    {
        this.passwordStrength = passwordStrength;
    }

    public void setAreaWidth(String areaWidth)
    {
        this.areaWidth = areaWidth;
    }

    private static final String TEMPLATE = "textfield";
    private static final String TEMPLATE_CLOSE = "textfield-close";
    String size;
    private String emptyText;
    private String grow;
    private String growMax;
    private String growMin;
    private String mask;
    private String maskConfig;
    private String maxLength;
    private String selectOnFocus;
    private String type;
    private String textAlign;
    private String icon;
    private String areaEdit;
    private String areaRows;
    private String areaWidth;
    private String passwordStrength;
}
