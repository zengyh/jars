// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Team.java

package com.sinitek.sirm.org.busin.entity;


public class Team
{

    public Team()
    {
    }

    public String getTeamId()
    {
        return teamId;
    }

    public void setTeamId(String teamId)
    {
        this.teamId = teamId;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamDescription()
    {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription)
    {
        this.teamDescription = teamDescription;
    }

    public boolean isResearchFlag()
    {
        return researchFlag;
    }

    public void setResearchFlag(boolean researchFlag)
    {
        this.researchFlag = researchFlag;
    }

    private String teamId;
    private String teamName;
    private String teamDescription;
    private boolean researchFlag;
}
