// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowConditionImpl.java

package com.sinitek.sirm.busin.workflow.support;

import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support:
//            IWorkflowCondition

public class WorkflowConditionImpl
    implements IWorkflowCondition
{

    public WorkflowConditionImpl()
    {
    }

    public boolean condition(Map map, Map returnmap)
    {
        int sum = 0;
        for(int i = 0; i < 10; i++)
            sum += i * i;

        returnmap.put("sum", Integer.valueOf(sum));
        int type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        return type != 0;
    }
}
