// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessType.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowProcessType extends AbstractEnumItem
{

    protected WorkflowProcessType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowProcessType WF_TYPE_REPORT = new WorkflowProcessType("1", 1, "\u62A5\u544A\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_ROUTINE = new WorkflowProcessType("2", 2, "\u65E5\u5E38\u5DE5\u4F5C\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_POOL = new WorkflowProcessType("3", 3, "\u6295\u8D44\u6C60\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_RELATION = new WorkflowProcessType("4", 4, "\u5BA2\u6237\u5173\u7CFB\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_COMBINATION = new WorkflowProcessType("5", 5, "\u7EC4\u5408\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_PUBLIC = new WorkflowProcessType("6", 6, "\u516C\u7528\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_PROJECT = new WorkflowProcessType("7", 7, "\u9879\u76EE\u7BA1\u7406\u7C7B", null);
    public static final WorkflowProcessType WF_TYPE_TEST = new WorkflowProcessType("99", 99, "\u6D41\u7A0B\u6D4B\u8BD5\u7C7B", null);

}
