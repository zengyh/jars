// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowAppService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import java.util.List;
import java.util.Map;

public interface IWorkflowAppService
{

    public abstract int saveExample(IWorkflowExample iworkflowexample);

    public abstract int saveExampleStep(IWorkflowExampleStep iworkflowexamplestep);

    public abstract int saveExampleEntry(IWorkflowExampleEntry iworkflowexampleentry);

    public abstract int saveExampleStepOwner(IWorkflowExampleStepOwner iworkflowexamplestepowner);

    public abstract int saveExampleStepLink(IWorkflowExampleStepLink iworkflowexamplesteplink);

    public abstract int saveExamplePara(IWorkflowExamplePara iworkflowexamplepara);

    public abstract int saveExampleRelation(IWorkflowExampleRelation iworkflowexamplerelation);

    public abstract IWorkflowExampleRelation loadExampleRelation(int i);

    public abstract IWorkflowExample loadExample(int i);

    public abstract IWorkflowExampleStep loadExampleStep(int i);

    public abstract IWorkflowExampleEntry loadExampleEntry(int i);

    public abstract IWorkflowExampleStepOwner loadExampleStepOwner(int i);

    public abstract IWorkflowExampleStepLink loadExampleStepLink(int i);

    public abstract IWorkflowExamplePara loadExamplePara(int i);

    public abstract List getNowExampleList();

    public abstract List getNowExampleList(Map map);

    public abstract IMetaDBQuery getNowExampleMetadb(Map map);

    public abstract List getMyInitiateProcessList(Map map);

    public abstract List getMyRecoverProcessList(Map map);

    public abstract List getPastExampleList();

    public abstract List getPastExampleList(Map map);

    public abstract IMetaDBQuery getPastExampleMetadb(Map map);

    public abstract List getExampleStepListByExampleId(int i);

    public abstract List getExampleStepList(int i);

    public abstract List getExampleTask(int i, int j);

    public abstract List getExampleTask(Map map);

    public abstract IMetaDBQuery getExampleTaskMetadb(Map map);

    public abstract List getUndoExampleTaskForMobile(Map map);

    public abstract int getUndoExampleTaskNumForMobile(Map map);

    public abstract List getDoneExampleTaskForMobile(Map map);

    public abstract List getTaskByMap(Map map);

    public abstract List getExampleOwnerList(int i);

    public abstract List getExampleOwnerList(int i, int j);

    public abstract int changeExample(int i, int j);

    public abstract int changeExampleO(int i, int j);

    public abstract int changeExampleS(int i, int j);

    public abstract int changeExampleStep(int i, int j);

    public abstract int changeExampleStepO(int i, int j);

    public abstract int changeExampleStepS(int i, int j);

    public abstract int changeExampleOwner(int i, int j);

    public abstract int findEntryId(int i, int j, String s);

    public abstract List getReleaseProcesss(int i);

    public abstract List getIssueProcess(int i);

    public abstract List getProcessByType(int i);

    public abstract List getIssueProcessStep(int i);

    public abstract List getExampleEntrys(int i);

    public abstract List getAgentsList(int i);

    public abstract List getLiveAgentsList(int i);

    public abstract List getLiveAgentsList(Map map);

    public abstract List getProcessListByTypeAndId(String s, int i);

    public abstract List getProcessListByEntryList(List list);

    public abstract int saveParaMap(int i, int j, int k, Map map);

    public abstract Map loadParaMap(int i, int j, int k);

    public abstract List loadParaList(int i, int j, int k);

    public abstract List findParaListByName(String s);

    public abstract Map findAllParaMap();

    public abstract List getExampleOwnerListByExampleid(int i, int j);

    public abstract List getDetailOwnerListByExampleid(int i);

    public abstract List getExampleOwnerListByStepid(int i, int j);

    public abstract List getExampleOwnerListByStepid(int i, int j, int k);

    public abstract List getTotalExampleOwnerListByStepid(int i, int j, int k);

    public abstract List getAgentsByOwnerAndId(int i, int j, int k, int l);

    public abstract List getAgentsByOwnerAndId(int i, int j, int k);

    public abstract Map getStepTempleteByStepid(int i);

    public abstract Map getProcessTempleteByExampleid(int i);

    public abstract Map getCurrentExampleStep(int i);

    public abstract List getFrontExampleStepList(int i);

    public abstract List getMyProcessList(int i, int j, int k, String s);

    public abstract List getExampleOwnerOpinion(int i, int j, int k);

    public abstract List getOwnerListByExampleid(int i);

    public abstract List getHistorySourceList(Map map);

    public abstract Map getHistoryStepMap(int i);

    public abstract IMetaDBQuery getModuleSPTask(Map map);

    public abstract List getDetailTaskList(Map map);

    public abstract IMetaDBQuery getDealerTaskCountList(Map map);

    public abstract List getDetailOwnerList(Map map);

    public abstract List getRelationList(Map map);

    public abstract List getExampleStepLinkList(int i, int j);

    public abstract List getExampleTask2(Map map);

    public abstract IMetaDBQuery getStepLinkInfo(Map map);

    public abstract List findStepNextList(int i);
}
