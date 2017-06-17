// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeLabel.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Label

public class TimeLabel extends Label
{

    public TimeLabel(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "timelabel";
    }

    protected String getDefaultTemplate()
    {
        return "timelabel-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "timelabel");
        Object defaultValueType = null;
        String sDefaultValue = null;
        if(defaultValue != null)
            if(defaultValue instanceof Object[])
            {
                Object dv[] = (Object[])(Object[])defaultValue;
                defaultValueType = "array";
                String jsString = "[";
                for(int i = 0; i < dv.length; i++)
                {
                    jsString = (new StringBuilder()).append(jsString).append("'").append(dv[i]).append("'").toString();
                    if(i + 1 != dv.length)
                        jsString = (new StringBuilder()).append(jsString).append(",").toString();
                }

                jsString = (new StringBuilder()).append(jsString).append("]").toString();
                sDefaultValue = jsString;
            } else
            if(defaultValue instanceof List)
            {
                List dv = (List)defaultValue;
                defaultValueType = "array";
                String jsString = "[";
                for(int i = 0; i < dv.size(); i++)
                {
                    jsString = (new StringBuilder()).append(jsString).append("'").append(dv.get(i)).append("'").toString();
                    if(i + 1 != dv.size())
                        jsString = (new StringBuilder()).append(jsString).append(",").toString();
                }

                jsString = (new StringBuilder()).append(jsString).append("]").toString();
                sDefaultValue = jsString;
            } else
            {
                defaultValueType = "string";
                sDefaultValue = (new StringBuilder()).append("'").append(defaultValue.toString()).append("'").toString();
            }
        addProperty("format", format, "yyyy-MM-dd");
        addProperty("sDefaultValue", sDefaultValue);
        addProperty("oDefaultValue", defaultValue);
        addProperty("defaultValueType", defaultValueType);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    private static final String TEMPLATE = "timelabel";
    private static final String TEMPLATE_CLOSE = "timelabel-close";
    private String format;
    private Object defaultValue;
}
