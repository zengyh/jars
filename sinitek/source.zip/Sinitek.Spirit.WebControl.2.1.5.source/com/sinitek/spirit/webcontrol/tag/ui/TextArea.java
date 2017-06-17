// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextArea.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextField

public class TextArea extends TextField
{

    public TextArea(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "textarea";
    }

    protected String getDefaultTemplate()
    {
        return "textarea-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "textarea");
        addProperty("rows", rows);
        addProperty("cols", cols);
    }

    public void setRows(String rows)
    {
        this.rows = rows;
    }

    public void setCols(String cols)
    {
        this.cols = cols;
    }

    private static final String TEMPLATE = "textarea";
    private static final String TEMPLATE_CLOSE = "textarea-close";
    private String rows;
    private String cols;
}
