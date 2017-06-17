// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Field.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public abstract class Field extends Component
{

    Field(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("readOnly", readOnly, "false");
        addProperty("width", width);
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
                    if(dv.get(i) instanceof Number)
                        jsString = (new StringBuilder()).append(jsString).append(dv.get(i)).toString();
                    else
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
        addProperty("sDefaultValue", sDefaultValue);
        addProperty("oDefaultValue", defaultValue);
        addProperty("defaultValueType", defaultValueType);
    }

    public void setReadOnly(String readOnly)
    {
        this.readOnly = readOnly;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    private String readOnly;
    Object defaultValue;
    String width;
}
