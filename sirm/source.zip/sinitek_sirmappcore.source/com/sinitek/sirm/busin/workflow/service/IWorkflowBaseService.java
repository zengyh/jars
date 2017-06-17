// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowBaseService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import java.util.List;
import java.util.Map;

public interface IWorkflowBaseService
{

    public abstract int saveProcess(IWorkflowProcess iworkflowprocess);

    public abstract int saveProcessStep(IWorkflowProcessStep iworkflowprocessstep);

    public abstract int saveProcessStepLink(IWorkflowProcessStepLink iworkflowprocesssteplink);

    public abstract int saveProcessStepLinkIf(IWorkflowProcessStepLinkIf iworkflowprocesssteplinkif);

    public abstract int saveProcessStepLinkDo(IWorkflowProcessStepLinkDo iworkflowprocesssteplinkdo);

    public abstract int saveProcessStepDo(IWorkflowProcessStepDo iworkflowprocessstepdo);

    public abstract int saveProcessOwner(IWorkflowProcessOwner iworkflowprocessowner);

    public abstract int saveProcessOwnerLink(IWorkflowProcessOwnerLink iworkflowprocessownerlink);

    public abstract int saveAgents(IWorkflowAgents iworkflowagents);

    public abstract int saveProcessStepHistory(IWorkflowProcessStepHistory iworkflowprocessstephistory);

    public abstract int saveProcessUrl(IWorkflowProcessUrl iworkflowprocessurl);

    public abstract int saveProcessList(IWorkflowProcessList iworkflowprocesslist);

    public abstract IWorkflowProcess loadProcess(int i);

    public abstract IWorkflowProcessStep loadProcessStep(int i);

    public abstract IWorkflowProcessStepLink loadProcessStepLink(int i);

    public abstract IWorkflowProcessStepLinkIf loadProcessStepLinkIf(int i);

    public abstract IWorkflowProcessStepLinkDo loadProcessStepLinkDo(int i);

    public abstract IWorkflowProcessStepDo loadProcessStepDo(int i);

    public abstract IWorkflowProcessOwner loadProcessOwner(int i);

    public abstract IWorkflowProcessOwnerLink loadProcessOwnerLink(int i);

    public abstract IWorkflowAgents loadAgents(int i);

    public abstract IWorkflowProcessStepHistory loadProcessStepHistory(int i);

    public abstract IWorkflowProcessUrl loadProcessUrl(int i);

    public abstract IWorkflowProcessList loadProcessList(int i);

    public abstract int addProcess(String s);

    public abstract int addProcess(String s, int i);

    public abstract int addProcess(String s, int i, String s1);

    public abstract int addProcessStep(String s, int i, int j);

    public abstract int addProcessStep(String s, int i, int j, int k);

    public abstract boolean testProcessNameUsed(int i, String s);

    public abstract List getProcessByName(String s, int i);

    public abstract Map getProcessBySyscode(String s);

    public abstract Map getProcessStepByStepcode(String s);

    public abstract List getProcessListBySyscode(String s);

    public abstract boolean testProcessCodeUsed(int i, String s);

    public abstract boolean testProcessStepNameUsed(int i, int j, String s);

    public abstract List getProcessList(int i);

    public abstract List getProcessList(Map map);

    public abstract List getProcessList2(Map map);

    public abstract List getProcessByNameAndStatus(String s, int i);

    public abstract List getProcessListByType(int i);

    public abstract List getProcessHistoryList(int i);

    public abstract List getProcessHistoryList(String s);

    public abstract List getProcessStepList(int i);

    public abstract List getSingleStepList(int i);

    public abstract List getProcessStepList(int i, int j);

    public abstract List getProcessLinkList(int i);

    public abstract List getReleaseStepList(int i);

    public abstract List getOwnerStepList(int i);

    public abstract List getAgentStepList(int i);

    public abstract int isAgentStep(int i);

    public abstract List getAgentsList(Map map);

    public abstract List getAgentsList(String s, int i);

    public abstract Map getProcessStepMain(int i);

    public abstract List getOwnerList(int i, int j);

    public abstract List getOwnerListByGoto(int i, int j, int k);

    public abstract List getOwnerListByStepid(int i);

    public abstract List getSingleOwnerList(int i);

    public abstract List getOwnerLinkList(int i);

    public abstract List getSingleOwnerLinkList(int i);

    public abstract List getOwnerLinkIfList(int i);

    public abstract List getStepLinkList(int i);

    public abstract List getSingleLinkList(int i);

    public abstract List getStepLinkListByAftstepid(int i);

    public abstract List getStepLinkIfList(int i);

    public abstract List getSingleLinkIfList(int i);

    public abstract List getStepLinkDoList(int i);

    public abstract List getSingleLinkDoList(int i);

    public abstract List getStepHistoryList(int i, int j);

    public abstract Map getHistoryStepMap(int i, int j, int k);

    public abstract Map getStepHistoryMap(String s);

    public abstract IMetaDBQuery getProcessUrlList(Map map);

    public abstract int getProcessUrlMaxSort();

    public abstract List getProcessUrlListByType(int i);

    public abstract List findParaListByName(String s);

    public abstract List findProcessTypeList();

    public abstract List findParaListByNameAndType(String s, int i);

    public abstract Map findParaMapByNameKey(String s, int i);

    public abstract Map findParaMapByNameTypeKey(String s, int i, int j);

    public abstract IMetaDBQuery searchParaListByName(Map map);

    public abstract int getParaListMaxSortByName(String s);

    public abstract List findParaNormal();

    public abstract Map findParaMapByName(String s);

    public abstract int getMaxProcesslistId();

    public abstract Map findAllParaMap();

    public abstract Map getSingleParaMap();

    public abstract Map findParaByEndNameAndKey(String s, int i);

    public abstract Map findParaByNameAndKey(String s, int i);

    public abstract Map findAvailableParaByEndNameAndKey(String s, int i);

    public abstract List getUrlExistList(String s, String s1, String s2, int i);

    public abstract boolean testUrlNameUsed(int i, String s);

    public abstract List getListExistList(String s, String s1, String s2, int i);

    public abstract boolean testListNameUsed(int i, String s, String s1);

    public abstract List getStepNoCode();

    public abstract List getStepNextStepList(int i);

    public abstract Map getStepLinkStepMap(int i);

    public abstract List judgeAgentsList(Map map);

    public abstract List getProcessStepDoList(int i, int j);
}
