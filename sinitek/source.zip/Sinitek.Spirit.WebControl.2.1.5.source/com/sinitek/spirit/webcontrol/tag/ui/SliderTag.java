// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SliderTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, Slider

public class SliderTag extends FieldTag
{

    public SliderTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Slider(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Slider cmp = (Slider)component;
        cmp.setMax(max);
        cmp.setMin(min);
        cmp.setOrientation(orientation);
        cmp.setRange(range);
        cmp.setStep(step);
        cmp.setSize(size);
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

    private String max;
    private String min;
    private String orientation;
    private String range;
    private String step;
    private String size;
}
