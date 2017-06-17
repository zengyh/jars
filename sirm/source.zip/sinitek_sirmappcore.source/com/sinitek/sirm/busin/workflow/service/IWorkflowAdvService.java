// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowAdvService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.*;

public interface IWorkflowAdvService
{

    public abstract List getProcessStepList(int i);

    public abstract List getOrderStepList(int i);

    public abstract List getPhysicalStepList(int i, int j, int k, int l, int i1, int j1);

    public abstract List getLogicStepList(int i, int j, int k, int l, int i1, int j1);

    public abstract Map getProcessStep(int i);

    public abstract int deleteProcessStep(int i);

    public abstract int copyProcess(int i);

    public abstract int copyProcess_new(int i);

    public abstract int cloneProcess(int i);

    public abstract int freshProcess(int i);

    public abstract int freshProcess_new(int i);

    public abstract int recoverProcess(int i);

    public abstract List getAgentsList(Map map);

    public abstract List getAllAgentsList(Map map);

    public abstract int freshAgents(int i);

    public abstract int releaseProcess(int i, Map map);

    public abstract int releaseProcess_new(int i, Map map);

    public abstract Map detectProcess(int i);

    public abstract Map detectProcess_new(int i);

    public abstract void achieveProcess(int i);

    public abstract Map changeProcess(int i);

    public abstract int translateExampleData(Map map);

    public abstract boolean canbeAgents(int i, int j, int k, int l, int i1, Date date, Date date1, 
            Map map, int j1);
}
