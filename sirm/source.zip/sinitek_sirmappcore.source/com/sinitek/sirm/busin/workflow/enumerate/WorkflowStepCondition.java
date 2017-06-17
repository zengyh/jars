// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepCondition.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepCondition extends AbstractEnumItem
{

    public WorkflowStepCondition(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowStepCondition WF_STEP_NULL = new WorkflowStepCondition("0", 0, "\u65E0", null);
    public static final WorkflowStepCondition WF_STEP_PASS = new WorkflowStepCondition("1", 1, "\u901A\u8FC7", null);
    public static final WorkflowStepCondition WF_STEP_REJECT = new WorkflowStepCondition("2", 2, "\u9A73\u56DE", null);
    public static final WorkflowStepCondition WF_STEP_SUBMIT = new WorkflowStepCondition("3", 3, "\u63D0\u4EA4", null);
    public static final WorkflowStepCondition WF_STEP_ABSTAIN = new WorkflowStepCondition("4", 4, "\u5F03\u6743", null);
    public static final WorkflowStepCondition WF_STEP_SKIP = new WorkflowStepCondition("5", 5, "\u8DF3\u8FC7", null);

}
