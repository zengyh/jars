// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckerTag.java

package com.sinitek.spirit.webcontrol.tag.function;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.function:
//            Checker

public class CheckerTag extends AbstractUITag
{

    public CheckerTag()
    {
    }

    public Component getBean(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return new Checker(valueStack, httpServletRequest, httpServletResponse);
    }

    protected void populateParams()
    {
        super.populateParams();
        ((Checker)component).setAllowBlank(allowBlank);
        ((Checker)component).setBlankText(blankText);
        ((Checker)component).setMaxLength(maxLength);
        ((Checker)component).setMaxLengthText(maxLengthText);
        ((Checker)component).setMinLength(minLength);
        ((Checker)component).setMinLengthText(minLengthText);
        ((Checker)component).setRegex(regex);
        ((Checker)component).setRegexText(regexText);
        ((Checker)component).setValidateOnBlur(validateOnBlur);
        ((Checker)component).setValidator(validator);
        ((Checker)component).setValue(value);
        ((Checker)component).setDisabled(disabled);
        ((Checker)component).setMsgTarget(msgTarget);
        ((Checker)component).setTarget(target);
        ((Checker)component).setTargetName(targetName);
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

    public void setHeight(String height)
    {
        this.height = height;
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

    private String allowBlank;
    private String blankText;
    private String disabled;
    private String height;
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
