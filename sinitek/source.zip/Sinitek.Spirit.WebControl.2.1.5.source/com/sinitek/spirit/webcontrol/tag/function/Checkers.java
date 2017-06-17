// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Checkers.java

package com.sinitek.spirit.webcontrol.tag.function;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Checkers extends Component
{

    public Checkers(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "checkers";
    }

    protected String getDefaultTemplate()
    {
        return "checkers-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("formId", formId, id);
        if(validateOnBlur != null)
            addParameter("validateOnBlur", findString(validateOnBlur));
        else
            addParameter("validateOnBlur", "true");
        if(msgTarget != null)
            addParameter("msgTarget", findString(msgTarget));
        else
            addParameter("msgTarget", "qtip");
        if(oneByOne != null)
            addParameter("oneByOne", findString(oneByOne));
        else
            addParameter("oneByOne", "false");
        Map parent = getParentParam();
        if(parent != null && "form".equals(parent.get("componentName")))
            addProperty("formId", parent.get("id"));
        addProperty("alertOnFalse", alertOnFalse, "true");
    }

    protected Class getValueClassType()
    {
        return java/lang/Boolean;
    }

    public void setFormId(String formId)
    {
        this.formId = formId;
    }

    public void setMsgTarget(String msgTarget)
    {
        this.msgTarget = msgTarget;
    }

    public void setValidateOnBlur(String validateOnBlur)
    {
        this.validateOnBlur = validateOnBlur;
    }

    public void setOneByOne(String oneByOne)
    {
        this.oneByOne = oneByOne;
    }

    public void setAlertOnFalse(String alertOnFalse)
    {
        this.alertOnFalse = alertOnFalse;
    }

    private static final String TEMPLATE = "checkers";
    private static final String TEMPLATE_CLOSE = "checkers-close";
    private String msgTarget;
    private String validateOnBlur;
    private String formId;
    private String oneByOne;
    private String alertOnFalse;
}
