// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelation.java

package com.sinitek.sirm.org.busin.support;

import java.io.Serializable;

public class OrgRelation
    implements Serializable
{

    public OrgRelation()
    {
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getObj()
    {
        return obj;
    }

    public void setObj(String obj)
    {
        this.obj = obj;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    private String type;
    private String obj;
    private String unit;
}
