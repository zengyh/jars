// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Select.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListField

public class Select extends ListField
{

    public Select(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "select";
    }

    protected String getDefaultTemplate()
    {
        return "select-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "select");
        if(value != null)
            addParameter("nameValue", value);
        if(emptyOption != null)
            addParameter("emptyOption", findValue(emptyOption, java/lang/Boolean));
        if(multiple != null)
            addParameter("multiple", findValue(multiple, java/lang/Boolean));
        if(size != null)
            addParameter("size", findString(size));
        if(headerKey != null && headerValue != null)
        {
            addParameter("headerKey", findString(headerKey));
            addParameter("headerValue", findString(headerValue));
        }
        addProperty("allowTip", allowTip, "false");
        addProperty("interactAction", interactAction);
        addProperty("interactId", interactId);
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

    private static final String TEMPLATE = "select";
    private static final String TEMPLATE_CLOSE = "select-close";
    private String emptyOption;
    private String headerKey;
    private String headerValue;
    private String multiple;
    private String size;
    private String interactAction;
    private String interactId;
    private String allowTip;
}
