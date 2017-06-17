// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowConditionOddEven.java

package com.sinitek.sirm.busin.workflow.support.condition;

import com.sinitek.sirm.busin.workflow.support.IWorkflowCondition;
import java.util.*;

public class WorkflowConditionOddEven
    implements IWorkflowCondition
{

    public WorkflowConditionOddEven()
    {
    }

    public boolean condition(Map map, Map returnmap)
    {
        int num = 0;
        for(Iterator i$ = map.keySet().iterator(); i$.hasNext();)
        {
            Object object = i$.next();
            num++;
        }

        return num % 2 != 0;
    }
}
