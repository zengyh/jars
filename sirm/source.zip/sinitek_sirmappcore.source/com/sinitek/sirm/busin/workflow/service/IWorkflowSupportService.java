// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowSupportService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.List;
import java.util.Map;

public interface IWorkflowSupportService
{

    public abstract Map startProcess(Map map);

    public abstract Map startNewProcess(Map map);

    public abstract Map subProcessStep(Map map);

    public abstract Map subNewProcessStep(Map map);

    public abstract List getNowStepList();

    public abstract List getNowStepList(Map map);

    public abstract List getMyInitiateProcessList(Map map);

    public abstract List getMyRecoverProcessList(Map map);

    public abstract List getPastProcessList();

    public abstract List getPastProcessList(Map map);

    public abstract List getNowWorkflow();

    public abstract List getMainWorkflow(int i);

    public abstract List getUnfinishTasks(Map map);

    public abstract List getDatchTasks(Map map);

    public abstract IMetaDBQuery getUnfinishTasksMetadb(Map map);

    public abstract List getHomePageTasks(String s);

    public abstract List getFinishedTasks(Map map);

    public abstract List getReleaseProcessList();

    public abstract List getReleaseProcessAndCodeList();

    public abstract List getReleaseProcessAndNullList();

    public abstract Map getExampleMain(int i);

    public abstract List getExampleEntryList(int i);

    public abstract List getFrontExampleStepList(int i);

    public abstract List getDetailCadidateListByExampleStepid(int i);

    public abstract List getDetailCadidateListBySysCode(String s);

    public abstract List getDetailCadidateListByProcessStepid(int i);

    public abstract List getWorkflowOpinion(int i, int j, int k);

    public abstract List getWorkflowNOpinion(int i, int j, int k);

    public abstract List getWorkflowSOpinion(int i, int j, int k);

    public abstract String getFirstShowUrl(int i);

    public abstract IMetaDBQuery getWorkflowDealerList(Map map);

    public abstract int doDirectFinish(Map map);

    public abstract int doJudgeReturn(int i, String s);

    public abstract Map gotoProcessStart(Map map);

    public abstract List getTasksTakepart(Map map);

    public abstract List getMyTasks(Map map);

    public abstract boolean isNextStepNew(int i);
}
