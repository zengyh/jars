// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListFieldTag.java

package com.sinitek.spirit.webcontrol.tag.ui;


// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, ListField

public abstract class ListFieldTag extends FieldTag
{

    public ListFieldTag()
    {
    }

    protected void populateParams()
    {
        super.populateParams();
        ListField listField = (ListField)component;
        listField.setList(list);
        listField.setListKey(listKey);
        listField.setListValue(listValue);
        listField.setJsMap(jsMap);
    }

    public void setList(String list)
    {
        this.list = list;
    }

    public void setListKey(String listKey)
    {
        this.listKey = listKey;
    }

    public void setListValue(String listValue)
    {
        this.listValue = listValue;
    }

    public void setJsMap(String jsMap)
    {
        this.jsMap = jsMap;
    }

    private String list;
    private String listKey;
    private String listValue;
    private String jsMap;
}
