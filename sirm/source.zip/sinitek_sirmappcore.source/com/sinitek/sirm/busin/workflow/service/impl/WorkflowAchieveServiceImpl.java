// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowAchieveServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.service.IWorkflowAchieveService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import java.util.HashMap;
import java.util.Map;

public class WorkflowAchieveServiceImpl extends MetaDBContextSupport
    implements IWorkflowAchieveService
{

    public WorkflowAchieveServiceImpl()
    {
    }

    public Map judgeLinkByModule(Map map)
    {
        int sum = 0;
        for(int i = 0; i < 10; i++)
            sum += i;

        Map _map = new HashMap();
        _map.put("sum", Integer.valueOf(sum));
        return _map;
    }

    public Map doTaskByModule(Map map)
    {
        int sum = 0;
        for(int i = 0; i < 10; i++)
            sum += i * i;

        Map _map = new HashMap();
        _map.put("sum", Integer.valueOf(sum));
        return _map;
    }
}
