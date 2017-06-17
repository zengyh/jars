// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFeatureException.java

package com.sinitek.sirm.busin.workflow.service.feature;


public class WorkflowFeatureException extends RuntimeException
{

    public WorkflowFeatureException(String message)
    {
        super(message);
    }

    public WorkflowFeatureException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
