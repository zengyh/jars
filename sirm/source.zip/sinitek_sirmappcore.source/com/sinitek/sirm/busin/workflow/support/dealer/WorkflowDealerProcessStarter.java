// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDealerProcessStarter.java

package com.sinitek.sirm.busin.workflow.support.dealer;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExample;
import com.sinitek.sirm.busin.workflow.service.IWorkflowAppService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.busin.workflow.support.IWorkflowDealer;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

public class WorkflowDealerProcessStarter
    implements IWorkflowDealer
{

    public WorkflowDealerProcessStarter()
    {
    }

    public List dealer(Map map, Map returnmap)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        IWorkflowExample example = _workapp.loadExample(exampleid);
        int starterid = NumberTool.safeToInteger(example.getStarterId(), Integer.valueOf(0)).intValue();
        List _list = new ArrayList();
        Map _map = new HashMap();
        _map.put("orgid", Integer.valueOf(starterid));
        _map.put("orgtype", Integer.valueOf(8));
        _list.add(_map);
        return _list;
    }
}
