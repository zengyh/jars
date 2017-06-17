// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionRecursive.java

package com.sinitek.sirm.busin.workflow.support.action;

import com.sinitek.sirm.busin.workflow.support.IWorkflowAction;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

public class WorkflowActionRecursive
    implements IWorkflowAction
{

    public WorkflowActionRecursive()
    {
    }

    public void action(Map map, Map returnmap)
    {
        int wsrecursivenum = NumberTool.convertMapKeyToInt(map, "wsrecursivenum", Integer.valueOf(0)).intValue();
        returnmap.put("wsrecursivenum", Integer.valueOf(wsrecursivenum + 1));
    }
}
