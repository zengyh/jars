// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AskForSubmit.java

package com.sinitek.sirm.busin.workflow.service.claim;

import java.util.Date;

public class AskForSubmit
{

    public AskForSubmit(int askForId)
    {
        this.askForId = askForId;
        askForStatus = 0;
        opinion = "";
        brief = "";
        approveTime = "";
        ownerName = "";
    }

    public int getAskForId()
    {
        return askForId;
    }

    public int getAskForStatus()
    {
        return askForStatus;
    }

    public void setAskForStatus(Integer askForStatus)
    {
        if(askForStatus != null)
            this.askForStatus = askForStatus.intValue();
    }

    public String getOpinion()
    {
        return opinion;
    }

    public void setOpinion(String opinion)
    {
        this.opinion = opinion;
    }

    public String getBrief()
    {
        return brief;
    }

    public void setBrief(String brief)
    {
        this.brief = brief;
    }

    void setApproveTime(String approveTime)
    {
        this.approveTime = approveTime;
    }

    void setApproveTime(Date approveTime)
    {
        if(approveTime != null)
        {
            String str = approveTime.toString();
            this.approveTime = str.substring(0, 16);
        }
    }

    public String getApproveTime()
    {
        return approveTime;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getStatusShow()
    {
        return showStatus(askForStatus);
    }

    public static String showStatus(int status)
    {
        switch(status)
        {
        case 0: // '\0'
            return "\u672A\u5904\u7406";

        case 1: // '\001'
            return "\u540C\u610F";

        case 2: // '\002'
            return "\u4E0D\u540C\u610F";

        case 99: // 'c'
            return "\u4E0D\u786E\u5B9A";
        }
        return (new StringBuilder()).append("status: ").append(status).toString();
    }

    public static int ASK_FOR_NULL = 0;
    public static int ASK_FOR_AGREE = 1;
    public static int ASK_FOR_DISAGREE = 2;
    public static int ASK_FOR_OTHER = 99;
    private int askForId;
    private int askForStatus;
    private String opinion;
    private String brief;
    private String approveTime;
    private String ownerName;

}
