// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Position.java

package com.sinitek.sirm.org.busin.entity;


public class Position
{

    public Position()
    {
    }

    public String getOrgid()
    {
        return orgid;
    }

    public void setOrgid(String orgid)
    {
        this.orgid = orgid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSupBusinId()
    {
        return supBusinId;
    }

    public void setSupBusinId(String supBusinId)
    {
        this.supBusinId = supBusinId;
    }

    public String getSupExecuteId()
    {
        return supExecuteId;
    }

    public void setSupExecuteId(String supExecuteId)
    {
        this.supExecuteId = supExecuteId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    private String orgid;
    private String name;
    private String supBusinId;
    private String supExecuteId;
    private String description;
}
