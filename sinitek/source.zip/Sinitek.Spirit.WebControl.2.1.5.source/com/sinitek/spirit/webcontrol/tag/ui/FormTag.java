// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FormTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Form

public class FormTag extends ComponentTag
{

    public FormTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Form(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Form cmp = (Form)component;
        cmp.setClazz(clazz);
        cmp.setMethod(method);
        cmp.setAllowFileUpload(allowFileUpload);
        cmp.setAction(action);
        cmp.setActionMethod(actionMethod);
        cmp.setHtmlConvertIgnore(htmlConvertIgnore);
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public void setAllowFileUpload(String allowFileUpload)
    {
        this.allowFileUpload = allowFileUpload;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public void setActionMethod(String actionMethod)
    {
        this.actionMethod = actionMethod;
    }

    public void setHtmlConvertIgnore(String htmlConvertIgnore)
    {
        this.htmlConvertIgnore = htmlConvertIgnore;
    }

    private String clazz;
    private String method;
    private String allowFileUpload;
    private String action;
    private String actionMethod;
    private String htmlConvertIgnore;
}
