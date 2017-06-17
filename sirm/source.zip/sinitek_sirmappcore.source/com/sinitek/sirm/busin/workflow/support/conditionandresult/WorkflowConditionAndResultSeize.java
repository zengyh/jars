// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowConditionAndResultSeize.java

package com.sinitek.sirm.busin.workflow.support.conditionandresult;

import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepCondition;
import com.sinitek.sirm.busin.workflow.support.IWorkflowConditionAndResult;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

public class WorkflowConditionAndResultSeize
    implements IWorkflowConditionAndResult
{

    public WorkflowConditionAndResultSeize()
    {
    }

    public boolean condition(Map map, Map returnmap)
    {
        int _condition = NumberTool.convertMapKeyToInt(map, "condition", Integer.valueOf(0)).intValue();
        if(_condition == WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue())
            returnmap.put("condition", Integer.valueOf(_condition));
        else
        if(_condition == WorkflowStepCondition.WF_STEP_REJECT.getEnumItemValue())
            returnmap.put("condition", Integer.valueOf(_condition));
        else
            return false;
        return true;
    }

    public int result(Map map, Map returnmap)
    {
        int _condition = NumberTool.convertMapKeyToInt(map, "condition", Integer.valueOf(0)).intValue();
        return _condition;
    }
}
