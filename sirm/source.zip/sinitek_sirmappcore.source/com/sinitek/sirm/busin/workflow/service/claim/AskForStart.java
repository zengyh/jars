// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AskForStart.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class AskForStart
{

    public AskForStart(String exampleOwnerId)
    {
        this.exampleOwnerId = NumberTool.safeToInteger(exampleOwnerId, Integer.valueOf(-1)).intValue();
        askForId = 0;
        ownerList = new ArrayList();
    }

    public AskForStart(int askForId)
    {
        exampleOwnerId = 0;
        this.askForId = askForId;
        ownerList = new ArrayList();
    }

    public int getExampleOwnerId()
    {
        return exampleOwnerId;
    }

    public List getOwnerList()
    {
        return ownerList;
    }

    public void setOwnerList(List ownerList)
    {
        this.ownerList = new ArrayList();
        for(int i = 0; i < ownerList.size(); i++)
        {
            Map ownerMap = (Map)ownerList.get(i);
            String orgId = StringUtil.safeToString(ownerMap.get("orgid"), "");
            this.ownerList.add(orgId);
        }

    }

    public int getAskForId()
    {
        return askForId;
    }

    public void addOwner(String owner)
    {
        ownerList.add(owner);
    }

    private int exampleOwnerId;
    private int askForId;
    private List ownerList;
}
