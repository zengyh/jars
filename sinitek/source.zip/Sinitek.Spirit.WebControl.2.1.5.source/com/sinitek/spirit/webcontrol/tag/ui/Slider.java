// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Slider.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class Slider extends Field
{

    public Slider(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "slider";
    }

    protected String getDefaultTemplate()
    {
        return "slider-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "slider");
        addProperty("max", max);
        addProperty("min", min);
        addProperty("orientation", orientation, "horizontal");
        addProperty("range", range, "'min'");
        addProperty("step", step);
        addProperty("_size", size, "200px");
        Object defaultValueType = null;
        String sDefaultValue = null;
        if((defaultValue instanceof String) && ((String)defaultValue).contains("[") && ((String)defaultValue).contains("]"))
        {
            defaultValueType = "array";
            sDefaultValue = defaultValue.toString();
        }
        addProperty("sDefaultValue", sDefaultValue);
        addProperty("oDefaultValue", defaultValue);
        addProperty("defaultValueType", defaultValueType);
    }

    public void setMax(String max)
    {
        this.max = max;
    }

    public void setMin(String min)
    {
        this.min = min;
    }

    public void setOrientation(String orientation)
    {
        this.orientation = orientation;
    }

    public void setRange(String range)
    {
        this.range = range;
    }

    public void setStep(String step)
    {
        this.step = step;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    private static final String TEMPLATE = "slider";
    private static final String TEMPLATE_CLOSE = "slider-close";
    private String max;
    private String min;
    private String orientation;
    private String range;
    private String step;
    private String size;
}
