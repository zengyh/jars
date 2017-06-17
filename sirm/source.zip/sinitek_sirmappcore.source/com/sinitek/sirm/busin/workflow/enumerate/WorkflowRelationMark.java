// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowRelationMark.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowRelationMark extends AbstractEnumItem
{

    protected WorkflowRelationMark(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowRelationMark WF_RELATION_NULL = new WorkflowRelationMark("0", 0, "\u65E0", null);
    public static final WorkflowRelationMark WF_RELATION_PASS = new WorkflowRelationMark("1", 1, "\u901A\u8FC7", null);
    public static final WorkflowRelationMark WF_RELATION_REJECT = new WorkflowRelationMark("2", 2, "\u9A73\u56DE", null);

}
