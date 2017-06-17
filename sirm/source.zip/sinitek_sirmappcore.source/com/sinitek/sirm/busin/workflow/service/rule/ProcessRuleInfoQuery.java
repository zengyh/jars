// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProcessRuleInfoQuery.java

package com.sinitek.sirm.busin.workflow.service.rule;


public class ProcessRuleInfoQuery
{

    public ProcessRuleInfoQuery()
    {
        ruleName = null;
        ruleType = null;
        info = null;
        status = null;
        brief = null;
    }

    public String getRuleName()
    {
        return ruleName;
    }

    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    public Integer getRuleType()
    {
        return ruleType;
    }

    public void setRuleType(Integer ruleType)
    {
        this.ruleType = ruleType;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getBrief()
    {
        return brief;
    }

    public void setBrief(String brief)
    {
        this.brief = brief;
    }

    private String ruleName;
    private Integer ruleType;
    private String info;
    private Integer status;
    private String brief;
}
