// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowRelationStatus.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowRelationStatus extends AbstractEnumItem
{

    protected WorkflowRelationStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowRelationStatus WF_RELATION_PEND = new WorkflowRelationStatus("0", 0, "\u672A\u5B8C\u6210", null);
    public static final WorkflowRelationStatus WF_RELATION_COMPLETED = new WorkflowRelationStatus("9", 9, "\u5DF2\u5B8C\u6210", null);
    public static final WorkflowRelationStatus WF_RELATION_GF_RETURN = new WorkflowRelationStatus("10000", 10000, "\u7ED3\u675F", null);
    public static final WorkflowRelationStatus WF_RELATION_GF_RECOVER = new WorkflowRelationStatus("10100", 10100, "\u6536\u56DE", null);

}
