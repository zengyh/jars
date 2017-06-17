// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListFieldTag, Select

public class SelectTag extends ListFieldTag
{

    public SelectTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Select(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Select select = (Select)component;
        select.setEmptyOption(emptyOption);
        select.setHeaderKey(headerKey);
        select.setHeaderValue(headerValue);
        select.setMultiple(multiple);
        select.setSize(size);
        select.setInteractAction(interactAction);
        select.setInteractId(interactId);
        select.setAllowTip(allowTip);
    }

    public void setEmptyOption(String emptyOption)
    {
        this.emptyOption = emptyOption;
    }

    public void setHeaderKey(String headerKey)
    {
        this.headerKey = headerKey;
    }

    public void setHeaderValue(String headerValue)
    {
        this.headerValue = headerValue;
    }

    public void setMultiple(String multiple)
    {
        this.multiple = multiple;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public void setInteractAction(String interactAction)
    {
        this.interactAction = interactAction;
    }

    public void setInteractId(String interactId)
    {
        this.interactId = interactId;
    }

    public void setAllowTip(String allowTip)
    {
        this.allowTip = allowTip;
    }

    private String emptyOption;
    private String headerKey;
    private String headerValue;
    private String multiple;
    private String size;
    private String interactAction;
    private String interactId;
    private String allowTip;
}
