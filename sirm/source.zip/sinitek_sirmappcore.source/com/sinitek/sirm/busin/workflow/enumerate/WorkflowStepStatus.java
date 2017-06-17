// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepStatus.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepStatus extends AbstractEnumItem
{

    protected WorkflowStepStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static String changeKeyToValue(int key)
    {
        String value;
        switch(key)
        {
        case -1: 
            value = "\u6B63\u5728\u751F\u6210";
            break;

        case 0: // '\0'
            value = "\u5F85\u5904\u7406";
            break;

        case 1: // '\001'
            value = "\u6B63\u5728\u5904\u7406";
            break;

        case 2: // '\002'
            value = "\u81EA\u52A8\u5B8C\u6210";
            break;

        case 3: // '\003'
            value = "\u5DF2\u7EC8\u6B62";
            break;

        case 4: // '\004'
            value = "\u64A4\u56DE";
            break;

        case 5: // '\005'
            value = "\u5E9F\u5F03";
            break;

        case 7: // '\007'
            value = "\u88AB\u8DF3\u8FC7";
            break;

        case 8: // '\b'
            value = "\u5DF2\u5904\u7406";
            break;

        case 9: // '\t'
            value = "\u88AB\u5B8C\u6210";
            break;

        case 11: // '\013'
            value = "\u5F02\u5E38";
            break;

        case 1000: 
            value = "\u5B8C\u6210";
            break;

        case 1001: 
            value = "\u9000\u56DE";
            break;

        default:
            value = "";
            break;
        }
        return value;
    }

    public static final WorkflowStepStatus WF_STEP_GENERATE = new WorkflowStepStatus("-1", -1, "\u6B63\u5728\u751F\u6210", null);
    public static final WorkflowStepStatus WF_STEP_PEND = new WorkflowStepStatus("0", 0, "\u5F85\u5904\u7406", null);
    public static final WorkflowStepStatus WF_STEP_BEING = new WorkflowStepStatus("1", 1, "\u6B63\u5728\u5904\u7406", null);
    public static final WorkflowStepStatus WF_STEP_AUTO = new WorkflowStepStatus("2", 2, "\u81EA\u52A8\u5B8C\u6210", null);
    public static final WorkflowStepStatus WF_STEP_TERMINAT = new WorkflowStepStatus("3", 3, "\u5DF2\u7EC8\u6B62", null);
    public static final WorkflowStepStatus WF_STEP_RECOVER = new WorkflowStepStatus("4", 4, "\u64A4\u56DE", null);
    public static final WorkflowStepStatus WF_STEP_SCRAP = new WorkflowStepStatus("5", 5, "\u5E9F\u5F03", null);
    public static final WorkflowStepStatus WF_STEP_BESKIP = new WorkflowStepStatus("7", 7, "\u88AB\u8DF3\u8FC7", null);
    public static final WorkflowStepStatus WF_STEP_PROCESS = new WorkflowStepStatus("8", 8, "\u5DF2\u5904\u7406", null);
    public static final WorkflowStepStatus WF_STEP_BECOMPLETED = new WorkflowStepStatus("9", 9, "\u88AB\u5B8C\u6210", null);
    public static final WorkflowStepStatus WF_STEP_ABSTAIN = new WorkflowStepStatus("11", 11, "\u5F02\u5E38", null);
    public static final WorkflowStepStatus WF_STEP_DIRECT = new WorkflowStepStatus("1000", 1000, "\u5B8C\u6210", null);
    public static final WorkflowStepStatus WF_STEP_RETURN = new WorkflowStepStatus("1001", 1001, "\u9000\u56DE", null);

}
