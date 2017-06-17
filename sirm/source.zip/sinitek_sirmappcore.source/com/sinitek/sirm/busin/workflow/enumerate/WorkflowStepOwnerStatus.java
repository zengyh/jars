// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowStepOwnerStatus.java

package com.sinitek.sirm.busin.workflow.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class WorkflowStepOwnerStatus extends AbstractEnumItem
{

    public WorkflowStepOwnerStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static String changeKeyToValue(int key)
    {
        String value;
        switch(key)
        {
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

        case 6: // '\006'
            value = "\u88AB\u62A2\u5360";
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

        case 10: // '\n'
            value = "\u88AB\u8F6C\u79FB";
            break;

        case 12: // '\f'
            value = "\u5DF2\u8F6C\u79FB";
            break;

        case 100: // 'd'
            value = "\u5DF2\u4EE3\u7406";
            break;

        case 101: // 'e'
            value = "\u4EE3\u7406\u5B8C\u6210";
            break;

        case 1000: 
            value = "\u8DF3\u8FC7";
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

    public static final WorkflowStepOwnerStatus WF_OWNER_PEND = new WorkflowStepOwnerStatus("0", 0, "\u5F85\u5904\u7406", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_BEING = new WorkflowStepOwnerStatus("1", 1, "\u6B63\u5728\u5904\u7406", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_AUTO = new WorkflowStepOwnerStatus("2", 2, "\u81EA\u52A8\u5B8C\u6210", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_TERMINAT = new WorkflowStepOwnerStatus("3", 3, "\u5DF2\u7EC8\u6B62", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_RECOVER = new WorkflowStepOwnerStatus("4", 4, "\u64A4\u56DE", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_SCRAP = new WorkflowStepOwnerStatus("5", 5, "\u5E9F\u5F03", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_BEPREEMPT = new WorkflowStepOwnerStatus("6", 6, "\u88AB\u62A2\u5360", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_BESKIP = new WorkflowStepOwnerStatus("7", 7, "\u88AB\u8DF3\u8FC7", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_PROCESS = new WorkflowStepOwnerStatus("8", 8, "\u5DF2\u5904\u7406", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_BECOMPLETED = new WorkflowStepOwnerStatus("9", 9, "\u88AB\u5B8C\u6210", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_BETRANSFER = new WorkflowStepOwnerStatus("10", 10, "\u88AB\u8F6C\u79FB", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_TRANSFER = new WorkflowStepOwnerStatus("12", 12, "\u5DF2\u8F6C\u79FB", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_AGENTS = new WorkflowStepOwnerStatus("100", 100, "\u5DF2\u4EE3\u7406", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_AGENTSED = new WorkflowStepOwnerStatus("101", 101, "\u4EE3\u7406\u5B8C\u6210", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_DIRECT = new WorkflowStepOwnerStatus("1000", 1000, "\u8DF3\u8FC7", null);
    public static final WorkflowStepOwnerStatus WF_OWNER_RETURN = new WorkflowStepOwnerStatus("1001", 1001, "\u9000\u56DE", null);

}
