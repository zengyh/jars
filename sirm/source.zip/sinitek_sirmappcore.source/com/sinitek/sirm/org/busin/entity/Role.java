// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Role.java

package com.sinitek.sirm.org.busin.entity;


public class Role
{

    public Role()
    {
    }

    public String getObjid()
    {
        return objid;
    }

    public void setObjid(String objid)
    {
        this.objid = objid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    private String objid;
    private String name;
    private String description;
}
