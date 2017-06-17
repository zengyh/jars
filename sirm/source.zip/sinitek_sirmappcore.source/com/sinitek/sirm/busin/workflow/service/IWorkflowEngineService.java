// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowEngineService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExample;
import com.sinitek.sirm.busin.workflow.entity.IWorkflowProcess;
import java.util.Map;

public interface IWorkflowEngineService
{

    public abstract Map newProcess(IWorkflowExample iworkflowexample);

    public abstract Map subProcessStep(Map map);

    public abstract Map getStartStep(int i);

    public abstract Map getProcessByCodeAndVersion(String s, int i);

    public abstract Map getProcessBySyscode(String s);

    public abstract IWorkflowProcess getProcessById(int i);
}
