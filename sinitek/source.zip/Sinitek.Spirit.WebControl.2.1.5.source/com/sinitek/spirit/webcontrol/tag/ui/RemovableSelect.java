// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemovableSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ListField

public class RemovableSelect extends ListField
{

    public RemovableSelect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "removableselect";
    }

    protected String getDefaultTemplate()
    {
        return "removableselect-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "removableselect");
        if(value != null)
            addParameter("nameValue", value);
        addProperty("width", width, "200px");
        addProperty("height", height, "25px");
        addProperty("removeFunction", removeFunction);
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setRemoveFunction(String removeFunction)
    {
        this.removeFunction = removeFunction;
    }

    private static final String TEMPLATE = "removableselect";
    private static final String TEMPLATE_CLOSE = "removableselect-close";
    private String height;
    private String removeFunction;
}
