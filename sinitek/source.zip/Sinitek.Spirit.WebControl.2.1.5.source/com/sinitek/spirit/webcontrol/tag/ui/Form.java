// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Form.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Form extends Component
{

    public Form(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "form";
    }

    protected String getDefaultTemplate()
    {
        return "form-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "form");
        addProperty("clazz", clazz);
        addProperty("method", method);
        addProperty("allowFileUpload", allowFileUpload);
        addProperty("action", action, "#");
        addProperty("actionMethod", actionMethod, "post");
        addProperty("htmlConvertIgnore", htmlConvertIgnore);
        Map parent = getParentParam();
        if(parent != null)
        {
            String labelWidth = (String)parent.get("labelWidth");
            String cellHeight = (String)parent.get("cellHeight");
            String cellWidth = (String)parent.get("cellWidth");
            String cellUnderLine = (String)parent.get("cellUnderLine");
            String cellMarginBottom = (String)parent.get("cellMarginBottom");
            String feature = (String)parent.get("feature");
            addProperty("labelWidth", labelWidth);
            addProperty("cellHeight", cellHeight);
            addProperty("cellWidth", cellWidth);
            addProperty("cellUnderLine", cellUnderLine);
            addProperty("cellMarginBottom", cellMarginBottom);
            addProperty("feature", feature);
        }
    }

    public void setAction(String action)
    {
        this.action = action;
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

    public void setActionMethod(String actionMethod)
    {
        this.actionMethod = actionMethod;
    }

    public void setHtmlConvertIgnore(String htmlConvertIgnore)
    {
        this.htmlConvertIgnore = htmlConvertIgnore;
    }

    private static final String TEMPLATE = "form";
    private static final String TEMPLATE_CLOSE = "form-close";
    private String clazz;
    private String method;
    private String action;
    private String actionMethod;
    private String allowFileUpload;
    private String htmlConvertIgnore;
}
