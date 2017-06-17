// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocEditor.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class DocEditor extends Field
{

    public DocEditor(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "doceditor";
    }

    protected String getDefaultTemplate()
    {
        return "doceditor-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("height", height);
        addProperty("width", width, "550px");
        addProperty("componentName", "doceditor");
        addProperty("emptyText", emptyText);
        addProperty("imageUploadJson", imageUploadJson);
        addProperty("items", items);
        addProperty("contextPath", request.getContextPath());
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setEmptyText(String emptyText)
    {
        this.emptyText = emptyText;
    }

    public void setImageUploadJson(String imageUploadJson)
    {
        this.imageUploadJson = imageUploadJson;
    }

    public void setItems(String items)
    {
        this.items = items;
    }

    private static final String TEMPLATE = "doceditor";
    private static final String TEMPLATE_CLOSE = "doceditor-close";
    private String height;
    private String emptyText;
    private String imageUploadJson;
    private String items;
}
