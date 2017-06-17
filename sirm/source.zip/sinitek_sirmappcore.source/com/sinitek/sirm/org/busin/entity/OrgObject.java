// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgObject.java

package com.sinitek.sirm.org.busin.entity;


public class OrgObject
{

    public OrgObject()
    {
        orgId = null;
        orgName = null;
        orgType = 0;
    }

    public OrgObject(String orgId, int orgType)
    {
        this.orgId = null;
        orgName = null;
        this.orgType = 0;
        this.orgId = orgId;
        this.orgType = orgType;
    }

    public String getOrgId()
    {
        return orgId;
    }

    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public int getOrgType()
    {
        return orgType;
    }

    public void setOrgType(int orgType)
    {
        this.orgType = orgType;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    private String orgId;
    private String orgName;
    private int orgType;
}
