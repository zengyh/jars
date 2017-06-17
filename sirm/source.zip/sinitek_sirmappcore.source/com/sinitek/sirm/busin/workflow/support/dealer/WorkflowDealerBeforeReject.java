// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDealerBeforeReject.java

package com.sinitek.sirm.busin.workflow.support.dealer;

import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepCondition;
import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepOwnerStatus;
import com.sinitek.sirm.busin.workflow.service.IWorkflowWebService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.busin.workflow.support.IWorkflowDealer;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

public class WorkflowDealerBeforeReject
    implements IWorkflowDealer
{

    public WorkflowDealerBeforeReject()
    {
    }

    public List dealer(Map map, Map returnmap)
    {
        IWorkflowWebService _workweb = WorkflowServiceFactory.getWorkflowWebService();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int processstepid = NumberTool.convertMapKeyToInt(map, "processstepid", Integer.valueOf(0)).intValue();
        List stepownerlist = ((List) (map.get("stepownerlist") != null ? (List)map.get("stepownerlist") : ((List) (new ArrayList()))));
        List oldownerlist = _workweb.getTotalExampleOwnerListByProcessStepid(processstepid, exampleid);
        if(oldownerlist.size() == 0)
            return stepownerlist;
        Map markmap = new HashMap();
        for(int i = 0; i < oldownerlist.size(); i++)
        {
            Map oldownermap = (Map)oldownerlist.get(i);
            int _status = NumberTool.convertMapKeyToInt(oldownermap, "status", Integer.valueOf(0)).intValue();
            if(_status != WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue())
                continue;
            int _ownerid = NumberTool.convertMapKeyToInt(oldownermap, "ownerid", Integer.valueOf(0)).intValue();
            int _preownerid = NumberTool.convertMapKeyToInt(oldownermap, "preownerid", Integer.valueOf(0)).intValue();
            int _condition = NumberTool.convertMapKeyToInt(oldownermap, "condition", Integer.valueOf(0)).intValue();
            if(_preownerid != 0)
                markmap.put((new StringBuilder()).append("").append(_preownerid).toString(), Integer.valueOf(_condition));
            else
                markmap.put((new StringBuilder()).append("").append(_ownerid).toString(), Integer.valueOf(_condition));
        }

        List newownerlist = new ArrayList();
        for(int i = 0; i < stepownerlist.size(); i++)
        {
            Map stepownermap = (Map)stepownerlist.get(i);
            int _orgid = NumberTool.convertMapKeyToInt(stepownermap, "orgid", Integer.valueOf(0)).intValue();
            int _value = NumberTool.convertMapKeyToInt(markmap, (new StringBuilder()).append("").append(_orgid).toString(), Integer.valueOf(0)).intValue();
            if(_value != WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue())
                newownerlist.add(stepownermap);
        }

        return newownerlist;
    }
}
