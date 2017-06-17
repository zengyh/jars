// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Checker.java

package com.sinitek.spirit.webcontrol.tag.function;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

class Checker extends UIBean
{

    public Checker(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        super(valueStack, httpServletRequest, httpServletResponse);
    }

    protected String getDefaultTemplate()
    {
        return "checker";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        if(target != null)
            addParameter("target", findString(target));
        if(targetName != null)
            addParameter("targetName", findString(targetName));
        if(allowBlank != null)
            addParameter("allowBlank", findString(allowBlank));
        if(blankText != null)
            addParameter("blankText", findString(blankText));
        if(disabled != null)
            addParameter("disabled", findString(disabled));
        if(maxLength != null)
            addParameter("maxLength", findString(maxLength));
        if(maxLengthText != null)
            addParameter("maxLengthText", findString(maxLengthText));
        if(minLengthText != null)
            addParameter("minLengthText", findString(minLengthText));
        if(minLength != null)
            addParameter("minLength", findString(minLength));
        if(regex != null)
            addParameter("regex", findString(regex));
        if(regexText != null)
            addParameter("regexText", findString(regexText));
        if(validateOnBlur != null)
            addParameter("validateOnBlur", findString(validateOnBlur));
        if(validator != null)
            addParameter("validator", findString(validator));
        if(value != null)
            addParameter("value", findString(value));
        if(msgTarget != null)
            addParameter("msgTarget", findString(msgTarget));
    }

    protected boolean supportsImageType()
    {
        return false;
    }

    public void setAllowBlank(String allowBlank)
    {
        this.allowBlank = allowBlank;
    }

    public void setBlankText(String blankText)
    {
        this.blankText = blankText;
    }

    public void setDisabled(String disabled)
    {
        this.disabled = disabled;
    }

    public void setMaxLength(String maxLength)
    {
        this.maxLength = maxLength;
    }

    public void setMaxLengthText(String maxLengthText)
    {
        this.maxLengthText = maxLengthText;
    }

    public void setMinLength(String minLength)
    {
        this.minLength = minLength;
    }

    public void setMinLengthText(String minLengthText)
    {
        this.minLengthText = minLengthText;
    }

    public void setRegex(String regex)
    {
        this.regex = regex;
    }

    public void setRegexText(String regexText)
    {
        this.regexText = regexText;
    }

    public void setValidateOnBlur(String validateOnBlur)
    {
        this.validateOnBlur = validateOnBlur;
    }

    public void setValidator(String validator)
    {
        this.validator = validator;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setMsgTarget(String msgTarget)
    {
        this.msgTarget = msgTarget;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public void setTargetName(String targetName)
    {
        this.targetName = targetName;
    }

    private static final String TEMPLATE = "checker";
    private String allowBlank;
    private String blankText;
    private String maxLength;
    private String maxLengthText;
    private String minLength;
    private String minLengthText;
    private String regex;
    private String regexText;
    private String validateOnBlur;
    private String validator;
    private String msgTarget;
    private String target;
    private String targetName;
}
