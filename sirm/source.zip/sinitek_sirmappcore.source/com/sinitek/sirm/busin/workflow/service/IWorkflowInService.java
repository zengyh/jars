// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowInService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.List;
import java.util.Map;

public interface IWorkflowInService
{

    public abstract List judgeConditioner(int i, int j, List list);

    public abstract List judgeConditioner(int i, List list, int j);

    public abstract Map judgeSpecialCondition(int i, Map map);

    public abstract Map judgeSpecialTask(int i, Map map);

    public abstract Map judgeSpecialDealer(int i, Map map);

    public abstract Map judgeSpecialApproval(int i, Map map);
}
