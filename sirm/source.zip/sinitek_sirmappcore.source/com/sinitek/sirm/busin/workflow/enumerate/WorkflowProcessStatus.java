// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStatus.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

// Referenced classes of package com.sinitek.sirm.busin.workflow.enumerate:
//            WorkflowStepCondition

public class WorkflowProcessStatus extends AbstractEnumItem
{

    protected WorkflowProcessStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowStepCondition WF_PROCESS_LAUNCH = new WorkflowStepCondition("0", 0, "\u53D1\u8D77", null);
    public static final WorkflowStepCondition WF_PROCESS_BEING = new WorkflowStepCondition("1", 1, "\u6267\u884C\u4E2D", null);
    public static final WorkflowStepCondition WF_PROCESS_TERMINAT = new WorkflowStepCondition("3", 3, "\u7EC8\u6B62", null);
    public static final WorkflowStepCondition WF_PROCESS_RECOVER = new WorkflowStepCondition("4", 4, "\u64A4\u56DE", null);
    public static final WorkflowStepCondition WF_PROCESS_ABSTAIN = new WorkflowStepCondition("5", 5, "\u5F02\u5E38", null);
    public static final WorkflowStepCondition WF_PROCESS_COMPLETED = new WorkflowStepCondition("9", 9, "\u5B8C\u6210", null);
    public static final WorkflowProcessStatus WF_PROCESS_DIRECT = new WorkflowProcessStatus("1000", 1000, "\u5B8C\u6210", null);
    public static final WorkflowProcessStatus WF_PROCESS_RETURN = new WorkflowProcessStatus("1001", 1001, "\u9000\u56DE", null);

}
