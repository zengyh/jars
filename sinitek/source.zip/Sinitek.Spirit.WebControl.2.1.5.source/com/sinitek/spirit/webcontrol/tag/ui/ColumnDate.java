// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnDate.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnDate extends Column
{

    public ColumnDate(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-date";
    }

    protected String getDefaultTemplate()
    {
        return "column-date-close";
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
        addProperty("colType", "date");
        addProperty("format", format, "yyyy-MM-dd");
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    private static final String TEMPLATE = "column-date";
    private static final String TEMPLATE_CLOSE = "column-date-close";
    private String format;
}
