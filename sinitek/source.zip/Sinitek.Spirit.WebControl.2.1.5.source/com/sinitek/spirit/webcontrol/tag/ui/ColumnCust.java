// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnCust.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnCust extends Column
{

    public ColumnCust(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-cust";
    }

    protected String getDefaultTemplate()
    {
        return "column-cust-close";
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
        addProperty("colType", "cust");
        addProperty("method", method);
        String defaultClass = null;
        Map parent = getParentParam();
        if(parent != null)
            defaultClass = (String)parent.get("actionClass");
        addProperty("clazz", clazz, defaultClass);
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    private static final String TEMPLATE = "column-cust";
    private static final String TEMPLATE_CLOSE = "column-cust-close";
    private String clazz;
    private String method;
}
