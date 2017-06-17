// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClaimOwner.java

package com.sinitek.sirm.busin.workflow.service.claim;


public class ClaimOwner
{

    public ClaimOwner(String orgId, int orgType, String orgName)
    {
        this.orgId = orgId;
        this.orgType = orgType;
        this.orgName = orgName;
    }

    public String getOrgId()
    {
        return orgId;
    }

    public int getOrgType()
    {
        return orgType;
    }

    public String getOrgName()
    {
        return orgName;
    }

    private String orgId;
    private int orgType;
    private String orgName;
}
