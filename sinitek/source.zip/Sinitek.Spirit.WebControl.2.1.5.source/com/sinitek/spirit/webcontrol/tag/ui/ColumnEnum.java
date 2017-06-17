// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnEnum.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnEnum extends Column
{

    public ColumnEnum(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-enum";
    }

    protected String getDefaultTemplate()
    {
        return "column-enum-close";
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
        addProperty("colType", "enum");
        addProperty("format", format, "info");
        addProperty("clazz", clazz);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    private static final String TEMPLATE = "column-enum";
    private static final String TEMPLATE_CLOSE = "column-enum-close";
    private String format;
    private String clazz;
}
