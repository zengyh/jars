// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowResultImpl.java

package com.sinitek.sirm.busin.workflow.support;

import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support:
//            IWorkflowResult

public class WorkflowResultImpl
    implements IWorkflowResult
{

    public WorkflowResultImpl()
    {
    }

    public int result(Map map, Map returnmap)
    {
        int _condition = NumberTool.convertMapKeyToInt(map, "condition", Integer.valueOf(0)).intValue();
        return _condition;
    }
}
