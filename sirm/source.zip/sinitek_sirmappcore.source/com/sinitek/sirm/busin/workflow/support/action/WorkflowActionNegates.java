// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionNegates.java

package com.sinitek.sirm.busin.workflow.support.action;

import com.sinitek.sirm.busin.workflow.support.IWorkflowAction;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

public class WorkflowActionNegates
    implements IWorkflowAction
{

    public WorkflowActionNegates()
    {
    }

    public void action(Map map, Map returnmap)
    {
        Iterator i$ = map.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Object object = i$.next();
            int value = NumberTool.convertMapKeyToInt(map, object.toString(), Integer.valueOf(0)).intValue();
            if(value != 0)
                returnmap.put(object, Integer.valueOf(-value));
        } while(true);
    }
}
