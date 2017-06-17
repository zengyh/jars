// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowModelService.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import java.util.List;
import java.util.Map;

public interface IWorkflowModelService
{

    public abstract int saveModelOne(IWorkflowModelOne iworkflowmodelone);

    public abstract int saveModelOneUrl(IWorkflowModelOneUrl iworkflowmodeloneurl);

    public abstract int saveModelProcess(IWorkflowModelProcess iworkflowmodelprocess);

    public abstract int saveModelOneList(IWorkflowModelOneList iworkflowmodelonelist);

    public abstract IMetaDBQuery getModelOne(Map map);

    public abstract IMetaDBQuery getModelOneUrl(Map map);

    public abstract IMetaDBQuery getModelProcess(Map map);

    public abstract IMetaDBQuery getModelOneList(Map map);

    public abstract IWorkflowModelOne loadModelOne(int i);

    public abstract IWorkflowModelOneUrl loadModelOneUrl(int i);

    public abstract IWorkflowModelProcess loadModelProcess(int i);

    public abstract IWorkflowModelOneList loadModelOneList(int i);

    public abstract boolean isModelOneNameAvailable(int i, String s);

    public abstract boolean isUrlTypeAvailable(int i, int j);

    public abstract IMetaDBQuery getModelOneIdAndName();

    public abstract List getModelOneListByType(int i);

    public abstract IMetaDBQuery getModelIdAndName();

    public abstract int getModelOneListMaxSort(int i);

    public abstract IMetaDBQuery loadModelOneListByProcessId(int i);

    public abstract int getProcessModelId(int i, String s);

    public abstract int getModelOperation(int i, String s);

    public abstract IMetaDBQuery getProcess(int i);

    public abstract int getProcessStepMaxObjid();

    public abstract IMetaDBQuery getProcessUrlById(int i);

    public abstract Map convertModelToFull(int i);

    public abstract IMetaDBQuery getDataByProcessId(String s, int i);

    public abstract int freshProcessModel(int i, int j);

    public abstract int freshProcessId(int i);
}
