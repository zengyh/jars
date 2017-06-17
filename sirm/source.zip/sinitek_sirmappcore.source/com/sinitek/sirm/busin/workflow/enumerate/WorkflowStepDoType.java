// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepDoType.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepDoType extends AbstractEnumItem
{

    protected WorkflowStepDoType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final WorkflowStepDoType WF_DOTYPE_PREDO = new WorkflowStepDoType("1", 1, "\u6B65\u9AA4\u524D\u6267\u884C", null);
    public static final WorkflowStepDoType WF_DOTYPE_AFTDO = new WorkflowStepDoType("2", 2, "\u6B65\u9AA4\u540E\u6267\u884C", null);

}
