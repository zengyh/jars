// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnNumber.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnNumber extends Column
{

    public ColumnNumber(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-number";
    }

    protected String getDefaultTemplate()
    {
        return "column-number-close";
    }

    public boolean start(Writer writer)
    {
        super.start(writer);
        return true;
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "column");
        addProperty("colType", "number");
        addProperty("pointPlace", pointPlace, "0");
        addProperty("isMoney", isMoney, "false");
    }

    public void setPointPlace(String pointPlace)
    {
        this.pointPlace = pointPlace;
    }

    public void setIsMoney(String money)
    {
        isMoney = money;
    }

    private static final String TEMPLATE = "column-number";
    private static final String TEMPLATE_CLOSE = "column-number-close";
    private String pointPlace;
    private String isMoney;
}
