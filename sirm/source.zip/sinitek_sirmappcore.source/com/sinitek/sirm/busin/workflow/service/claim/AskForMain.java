// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AskForMain.java

package com.sinitek.sirm.busin.workflow.service.claim;


public class AskForMain
{

    public AskForMain(int askForId)
    {
        this.askForId = askForId;
        showUrl = "";
        sourceName = "";
        sourceId = "";
    }

    public String getShowUrl()
    {
        return showUrl;
    }

    public void setShowUrl(String showUrl)
    {
        this.showUrl = showUrl;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    public String getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public int getExampleId()
    {
        return exampleId;
    }

    public void setExampleId(int exampleId)
    {
        this.exampleId = exampleId;
    }

    public int getExampleStepId()
    {
        return exampleStepId;
    }

    public void setExampleStepId(int exampleStepId)
    {
        this.exampleStepId = exampleStepId;
    }

    public int getExampleOwnerId()
    {
        return exampleOwnerId;
    }

    public void setExampleOwnerId(int exampleOwnerId)
    {
        this.exampleOwnerId = exampleOwnerId;
    }

    private int askForId;
    private String showUrl;
    private String sourceName;
    private String sourceId;
    private int exampleId;
    private int exampleStepId;
    private int exampleOwnerId;
}
