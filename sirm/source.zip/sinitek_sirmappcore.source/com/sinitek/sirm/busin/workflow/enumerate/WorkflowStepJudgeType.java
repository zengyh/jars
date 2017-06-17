// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepJudgeType.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepJudgeType extends AbstractEnumItem
{

    protected WorkflowStepJudgeType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowStepJudgeType WF_JUDGE_SEIZE = new WorkflowStepJudgeType("1", 1, "\u62A2\u5360\u5BA1\u6279", null);
    public static final WorkflowStepJudgeType WF_JUDGE_VOTE = new WorkflowStepJudgeType("2", 2, "\u6295\u7968\u5BA1\u6279", null);
    public static final WorkflowStepJudgeType WF_JUDGE_WAIT = new WorkflowStepJudgeType("5", 5, "\u6295\u7968\u7B49\u5F85", null);
    public static final WorkflowStepJudgeType WF_JUDGE_SP = new WorkflowStepJudgeType("99", 99, "\u7279\u6B8A\u5BA1\u6279", null);
    public static final WorkflowStepJudgeType WF_JUDGE_OTHER = new WorkflowStepJudgeType("100", 100, "\u81EA\u5B9A\u4E49\u5BA1\u6279", null);

}
