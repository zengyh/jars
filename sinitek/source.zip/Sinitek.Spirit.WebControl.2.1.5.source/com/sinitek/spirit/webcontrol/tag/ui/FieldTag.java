// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FieldTag.java

package com.sinitek.spirit.webcontrol.tag.ui;


// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Field

public abstract class FieldTag extends ComponentTag
{

    public FieldTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        Field cmp = (Field)component;
        cmp.setReadOnly(readOnly);
        cmp.setDefaultValue(defaultValue);
        cmp.setWidth(width);
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
    private Object defaultValue;
    String width;
}
