// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepType.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepType extends AbstractEnumItem
{

    protected WorkflowStepType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowStepType WF_TYPE_START = new WorkflowStepType("1", 1, "\u5F00\u59CB\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_END = new WorkflowStepType("2", 2, "\u7ED3\u675F\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_DEAL = new WorkflowStepType("3", 3, "\u5904\u7406\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_LOGIC = new WorkflowStepType("4", 4, "\u903B\u8F91\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_BRANCH = new WorkflowStepType("5", 5, "\u5206\u652F\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_MERGER = new WorkflowStepType("6", 6, "\u5408\u5E76\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_SUBMIT = new WorkflowStepType("7", 7, "\u63D0\u4EA4\u8282\u70B9", null);
    public static final WorkflowStepType WF_TYPE_REPORT = new WorkflowStepType("12", 2, "\u7ED3\u675F\u8282\u70B92", null);

}
