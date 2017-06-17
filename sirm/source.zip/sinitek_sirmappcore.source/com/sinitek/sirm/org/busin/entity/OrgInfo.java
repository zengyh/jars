// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgInfo.java

package com.sinitek.sirm.org.busin.entity;


public class OrgInfo
{

    public OrgInfo()
    {
    }

    public String getUnitname()
    {
        return unitname;
    }

    public void setUnitname(String unitname)
    {
        this.unitname = unitname;
    }

    public String getPositionname()
    {
        return positionname;
    }

    public void setPositionname(String positionname)
    {
        this.positionname = positionname;
    }

    public String getTeamname()
    {
        return teamname;
    }

    public void setTeamname(String teamname)
    {
        this.teamname = teamname;
    }

    public String getRolename()
    {
        return rolename;
    }

    public void setRolename(String rolename)
    {
        this.rolename = rolename;
    }

    private String unitname;
    private String positionname;
    private String teamname;
    private String rolename;
}
