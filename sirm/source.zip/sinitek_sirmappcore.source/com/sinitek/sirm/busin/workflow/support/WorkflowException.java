// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowException.java

package com.sinitek.sirm.busin.workflow.support;


public class WorkflowException
{

    public WorkflowException()
    {
        errorbase = -100;
        classfaild = -101;
        actionfaild = -102;
        outofarray = -500;
        severalfrontdealer = -501;
        processnotfound = -1001;
        stepnotfound = -1002;
        ownernotfound = -1003;
        stepbecompleted = -1005;
    }

    public int errorbase;
    public int classfaild;
    public int actionfaild;
    public int outofarray;
    public int severalfrontdealer;
    public int processnotfound;
    public int stepnotfound;
    public int ownernotfound;
    public int stepbecompleted;
}
