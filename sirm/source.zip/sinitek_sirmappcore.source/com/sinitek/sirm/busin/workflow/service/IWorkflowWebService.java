// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowWebService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.*;

public interface IWorkflowWebService
{

    public abstract boolean detectAgents(int i);

    public abstract boolean detectAgents(int i, Date date, Date date1);

    public abstract boolean judgeOwners(String s, int i);

    public abstract boolean judgeOwners(int i, int j);

    public abstract boolean judgeNowOwners(String s, int i);

    public abstract boolean judgeNowOwners(int i, int j);

    public abstract List getProcessListByEntryList(List list);

    public abstract List getProcessListByAllEntryList(List list);

    public abstract int getMyTaskCount(int i);

    public abstract String getProcessnameBySyscode(String s);

    public abstract int getProcessStatus(int i);

    public abstract int getProcessStepStatus(int i);

    public abstract int getProcessStepCondition(int i);

    public abstract int getProcessCondition(int i, int j);

    public abstract String getListParaValueByNameAndKey(String s, int i);

    public abstract Map loadParaMap(int i);

    public abstract List getNowExampleOwnerListByExampleid(int i);

    public abstract List getAllExampleOwnerListByExampleid(int i);

    public abstract List getAllExampleOwnerListByExampleStepid(int i);

    public abstract List getAllExampleOwnerListByProcessStepid(int i);

    public abstract List getAllExampleOwnerListByProcessStepid(int i, int j);

    public abstract List getTotalExampleOwnerListByProcessStepid(int i);

    public abstract List getTotalExampleOwnerListByProcessStepid(int i, int j);

    public abstract int recoverProcess(int i);

    public abstract int recoverProcess(Map map);

    public abstract int cancelProcess(int i);

    public abstract int cancelProcess(Map map);

    public abstract int endProcess(int i);

    public abstract int endProcess(Map map);

    public abstract int getPointTypeByStepid(int i);

    public abstract List getTaskByOrgListAndType(List list, int i);

    public abstract List getMyProcessListByProcessid(int i, int j);

    public abstract List getMyProcessListBySyscode(int i, int j);

    public abstract List getMyProcessListByProcesstype(int i, int j);

    public abstract List getOwnerListByExampleid(int i);

    public abstract List getHistorySourceListByStep(int i, Map map);

    public abstract List getHistorySourceListByOwner(int i, Map map);

    public abstract List getHistorySourceListByProcessStep(int i, Map map);

    public abstract IMetaDBQuery getSmartPhoneTask(Map map);

    public abstract List getDetailTaskListByStepid(int i);

    public abstract int getStepStageByProcessStepId(int i);

    public abstract List getRecentOwnerListByExampleIdAndStageId(int i, int j);

    public abstract List getRecentOwnerListByExampleIdAndStageId(int i, int ai[]);

    public abstract List getRecentOwnerListByExampleIdAndStageId(int i, String s);

    public abstract String getNowOwnerNamesByEntryList(List list);

    public abstract List getLatelyRealHandleOnwerListByStageId(int i, String s);

    public abstract boolean isSubExampleFinish(int i, int j, int k);

    public abstract boolean isSubExample(int i);

    public abstract int getExampleStarter(int i);
}
