// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowBaseException.java

package com.sinitek.sirm.busin.workflow;


public class WorkflowBaseException extends RuntimeException
{

    public WorkflowBaseException()
    {
    }

    public WorkflowBaseException(String message)
    {
        super(message);
    }

    public WorkflowBaseException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public WorkflowBaseException(Throwable cause)
    {
        super(cause);
    }
}
