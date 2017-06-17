// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowConditionRecursive.java

package com.sinitek.sirm.busin.workflow.support.condition;

import com.sinitek.sirm.busin.workflow.support.IWorkflowCondition;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

public class WorkflowConditionRecursive
    implements IWorkflowCondition
{

    public WorkflowConditionRecursive()
    {
    }

    public boolean condition(Map map, Map returnmap)
    {
        int wsrecursivenum = NumberTool.convertMapKeyToInt(map, "wsrecursivenum", Integer.valueOf(0)).intValue();
        int wsrecursiveend = NumberTool.convertMapKeyToInt(map, "wsrecursiveend", Integer.valueOf(0)).intValue();
        return wsrecursivenum < wsrecursiveend;
    }
}
