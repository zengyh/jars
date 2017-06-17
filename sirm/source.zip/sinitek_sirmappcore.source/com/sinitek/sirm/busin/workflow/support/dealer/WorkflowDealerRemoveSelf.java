// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDealerRemoveSelf.java

package com.sinitek.sirm.busin.workflow.support.dealer;

import com.sinitek.sirm.busin.workflow.service.IWorkflowWebService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.busin.workflow.support.IWorkflowDealer;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

public class WorkflowDealerRemoveSelf
    implements IWorkflowDealer
{

    public WorkflowDealerRemoveSelf()
    {
    }

    public List dealer(Map map, Map returnmap)
    {
        IWorkflowWebService workweb = WorkflowServiceFactory.getWorkflowWebService();
        List stepownerlist = ((List) (map.get("stepownerlist") != null ? (List)map.get("stepownerlist") : ((List) (new ArrayList()))));
        List newstepownerlist = new ArrayList();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int starterid = workweb.getExampleStarter(exampleid);
        for(int i = 0; i < stepownerlist.size(); i++)
        {
            Map stepownermap = (Map)stepownerlist.get(i);
            int orgid = NumberTool.convertMapKeyToInt(stepownermap, "orgid", Integer.valueOf(0)).intValue();
            if(orgid != starterid)
                newstepownerlist.add(stepownermap);
        }

        return newstepownerlist;
    }
}
