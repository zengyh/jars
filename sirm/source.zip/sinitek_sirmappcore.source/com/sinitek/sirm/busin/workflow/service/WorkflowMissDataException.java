// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowMissDataException.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.sirm.busin.workflow.WorkflowBaseException;

public class WorkflowMissDataException extends WorkflowBaseException
{

    public WorkflowMissDataException(int type)
    {
        super(changeType(type));
    }

    public WorkflowMissDataException(int type, int mark)
    {
        super((new StringBuilder()).append(changeType(type)).append(" : ").append(mark).toString());
    }

    public WorkflowMissDataException(int type, String mark)
    {
        super((new StringBuilder()).append(changeType(type)).append(" : ").append(mark).toString());
    }

    private static String changeType(int type)
    {
        String str = "Workflow Miss Data";
        switch(type)
        {
        case 1: // '\001'
            str = "Workflow Example Lose";
            break;

        case 2: // '\002'
            str = "Workflow Example Step Lose";
            break;

        case 3: // '\003'
            str = "Workflow Example Owner Lose";
            break;

        case 10: // '\n'
            str = "Workflow Process Code Lose";
            break;

        case 11: // '\013'
            str = "Workflow Process Lose";
            break;

        case 12: // '\f'
            str = "Workflow Process Step Lose";
            break;

        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        default:
            str = (new StringBuilder()).append("Undefault Value ").append(type).toString();
            break;
        }
        return str;
    }

    public static final int LOSE_EXAMPLE = 1;
    public static final int LOSE_EXAMPLE_STEP = 2;
    public static final int LOSE_EXAMPLE_OWNER = 3;
    public static final int LOSE_PROCESS_CODE = 10;
    public static final int LOSE_PROCESS = 11;
    public static final int LOSE_PROCESS_STEP = 12;
}
