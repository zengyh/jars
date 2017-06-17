// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckersTag.java

package com.sinitek.spirit.webcontrol.tag.function;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.ComponentTag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.function:
//            Checkers

public class CheckersTag extends ComponentTag
{

    public CheckersTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Checkers(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ((Checkers)component).setFormId(formId);
        ((Checkers)component).setMsgTarget(msgTarget);
        ((Checkers)component).setValidateOnBlur(validateOnBlur);
        ((Checkers)component).setOneByOne(oneByOne);
        ((Checkers)component).setAlertOnFalse(alertOnFalse);
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

    private String formId;
    private String msgTarget;
    private String validateOnBlur;
    private String oneByOne;
    private String alertOnFalse;
}
