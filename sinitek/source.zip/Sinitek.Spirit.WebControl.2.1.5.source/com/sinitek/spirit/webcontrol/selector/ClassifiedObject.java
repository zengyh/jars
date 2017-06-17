// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassifiedObject.java

package com.sinitek.spirit.webcontrol.selector;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.spirit.webcontrol.selector:
//            SelectorData

public class ClassifiedObject
{

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public ClassifiedObject()
    {
    }

    public ClassifiedObject(String text)
    {
        this.text = text;
    }

    public ClassifiedObject(String id, String text)
    {
        this.id = id;
        this.text = text;
    }

    public List getChildren()
    {
        return children;
    }

    public void setChildren(List children)
    {
        this.children = children;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void add(SelectorData data)
    {
        if(children == null)
            children = new ArrayList();
        children.add(data);
    }

    public void addAll(List datas)
    {
        if(children == null)
            children = new ArrayList();
        children.addAll(datas);
    }

    private String id;
    private String text;
    private List children;
}
