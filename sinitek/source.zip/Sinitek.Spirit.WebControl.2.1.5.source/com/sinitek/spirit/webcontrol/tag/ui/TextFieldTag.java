// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextFieldTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, TextField

public class TextFieldTag extends FieldTag
{

    public TextFieldTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new TextField(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        TextField cmp = (TextField)component;
        cmp.setSize(size);
        cmp.setEmptyText(emptyText);
        cmp.setGrow(grow);
        cmp.setGrowMax(growMax);
        cmp.setGrowMin(growMin);
        cmp.setMask(mask);
        cmp.setMaskConfig(maskConfig);
        cmp.setMaxLength(maxLength);
        cmp.setSelectOnFocus(selectOnFocus);
        cmp.setType(type);
        cmp.setTextAlign(textAlign);
        cmp.setIcon(icon);
        cmp.setAreaEdit(areaEdit);
        cmp.setAreaRows(areaRows);
        cmp.setAreaWidth(areaWidth);
        cmp.setPasswordStrength(passwordStrength);
    }

    public void setPasswordStrength(String passwordStrength)
    {
        this.passwordStrength = passwordStrength;
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

    public void setMinLength(String minLength)
    {
        this.minLength = minLength;
    }

    public void setMinLengthText(String minLengthText)
    {
        this.minLengthText = minLengthText;
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

    public void setAreaWidth(String areaWidth)
    {
        this.areaWidth = areaWidth;
    }

    private String size;
    private String emptyText;
    private String grow;
    private String growMax;
    private String growMin;
    private String mask;
    private String maskConfig;
    private String maxLength;
    private String minLength;
    private String minLengthText;
    private String selectOnFocus;
    private String type;
    private String textAlign;
    private String icon;
    private String areaEdit;
    private String areaRows;
    private String areaWidth;
    private String passwordStrength;
}
