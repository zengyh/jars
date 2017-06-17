// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowTemplateStatus.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowTemplateStatus extends AbstractEnumItem
{

    protected WorkflowTemplateStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowTemplateStatus WF_TEMPLATE_UNRELEASE = new WorkflowTemplateStatus("1", 1, "\u672A\u53D1\u5E03", null);
    public static final WorkflowTemplateStatus WF_TEMPLATE_RELEASE = new WorkflowTemplateStatus("2", 2, "\u5DF2\u53D1\u5E03", null);
    public static final WorkflowTemplateStatus WF_TEMPLATE_RELEASED = new WorkflowTemplateStatus("3", 3, "\u8FC7\u53BB\u53D1\u5E03", null);
    public static final WorkflowTemplateStatus WF_TEMPLATE_DELETE = new WorkflowTemplateStatus("100", 100, "\u5DF2\u5220\u9664", null);

}
