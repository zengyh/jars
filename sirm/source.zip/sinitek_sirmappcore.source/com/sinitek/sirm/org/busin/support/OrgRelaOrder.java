// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelaOrder.java

package com.sinitek.sirm.org.busin.support;

import java.io.Serializable;

public class OrgRelaOrder
    implements Serializable
{

    public OrgRelaOrder()
    {
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent(String parent)
    {
        this.parent = parent;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getOrgObject()
    {
        return orgObject;
    }

    public void setOrgObject(String orgObject)
    {
        this.orgObject = orgObject;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    private String parent;
    private String unit;
    private String type;
    private String orgObject;
    private boolean flag;
}
