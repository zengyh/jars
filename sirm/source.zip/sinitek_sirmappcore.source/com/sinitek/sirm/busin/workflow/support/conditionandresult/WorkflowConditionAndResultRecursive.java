// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowConditionAndResultRecursive.java

package com.sinitek.sirm.busin.workflow.support.conditionandresult;

import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepCondition;
import com.sinitek.sirm.busin.workflow.service.IWorkflowWebService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.busin.workflow.support.IWorkflowConditionAndResult;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.List;
import java.util.Map;

public class WorkflowConditionAndResultRecursive
    implements IWorkflowConditionAndResult
{

    public WorkflowConditionAndResultRecursive()
    {
    }

    public boolean condition(Map map, Map returnmap)
    {
        IWorkflowWebService workweb = WorkflowServiceFactory.getWorkflowWebService();
        int wsrecursivenum = NumberTool.convertMapKeyToInt(map, "wsrecursivenum", Integer.valueOf(0)).intValue();
        if(wsrecursivenum < 1)
        {
            wsrecursivenum = 1;
            returnmap.put("wsrecursivenum", Integer.valueOf(wsrecursivenum));
        }
        double wsrecursiveresult = NumberTool.safeToDouble(map.get((new StringBuilder()).append("wsrecursive").append(wsrecursivenum).append("result").toString()), Double.valueOf(-1D)).doubleValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        List ownerlist = workweb.getAllExampleOwnerListByExampleStepid(examplestepid);
        double sumnum = 0.0D;
        double yesnum = 0.0D;
        double nonum = 0.0D;
        for(int i = 0; i < ownerlist.size(); i++)
        {
            Map ownermap = (Map)ownerlist.get(i);
            int preownerid = NumberTool.convertMapKeyToInt(ownermap, "preownerid", Integer.valueOf(0)).intValue();
            int value = NumberTool.convertMapKeyToInt(ownermap, "value", Integer.valueOf(0)).intValue();
            if(preownerid == 0)
                sumnum += value;
            int condition = NumberTool.convertMapKeyToInt(ownermap, "condition", Integer.valueOf(0)).intValue();
            if(condition == WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue())
            {
                yesnum += value;
                continue;
            }
            if(condition == WorkflowStepCondition.WF_STEP_REJECT.getEnumItemValue())
                nonum += value;
        }

        if(wsrecursiveresult == -1D)
        {
            if(yesnum > 0.0D)
            {
                returnmap.put("wstempresult", Integer.valueOf(1));
                return true;
            }
            if(nonum > 0.0D)
            {
                returnmap.put("wstempresult", Integer.valueOf(2));
                return true;
            }
        } else
        if(wsrecursiveresult >= 0.0D && wsrecursiveresult <= 1.0D)
        {
            if(yesnum >= sumnum * wsrecursiveresult)
            {
                returnmap.put("wstempresult", Integer.valueOf(1));
                return true;
            }
            if(nonum > sumnum * (1.0D - wsrecursiveresult))
            {
                returnmap.put("wstempresult", Integer.valueOf(2));
                return true;
            }
        }
        return false;
    }

    public int result(Map map, Map returnmap)
    {
        int wstempresult = NumberTool.convertMapKeyToInt(map, "wstempresult", Integer.valueOf(0)).intValue();
        return wstempresult;
    }
}
