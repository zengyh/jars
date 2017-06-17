// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionImpl.java

package com.sinitek.sirm.busin.workflow.support;

import java.util.Map;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support:
//            IWorkflowAction

public class WorkflowActionImpl
    implements IWorkflowAction
{

    public WorkflowActionImpl()
    {
    }

    public void action(Map map, Map returnmap)
    {
        int sum = 0;
        for(int i = 0; i < 10; i++)
            sum += i * i;

        returnmap.put("sum", Integer.valueOf(sum));
    }
}
