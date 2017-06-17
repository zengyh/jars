// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowModelServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class WorkflowModelServiceImpl extends MetaDBContextSupport
    implements IWorkflowModelService
{

    public WorkflowModelServiceImpl()
    {
    }

    public int saveModelOne(IWorkflowModelOne modelOne)
    {
        super.save(modelOne);
        return modelOne.getId();
    }

    public int saveModelOneUrl(IWorkflowModelOneUrl modelOneUrl)
    {
        super.save(modelOneUrl);
        return modelOneUrl.getId();
    }

    public int saveModelProcess(IWorkflowModelProcess modelProcess)
    {
        super.save(modelProcess);
        return modelProcess.getId();
    }

    public int saveModelOneList(IWorkflowModelOneList modelOneList)
    {
        super.save(modelOneList);
        return modelOneList.getId();
    }

    public IMetaDBQuery getModelOne(Map condition)
    {
        String name = StringUtil.safeToString(condition.get("name"), "");
        int typeid = NumberTool.convertMapKeyToInt(condition, "typeid", Integer.valueOf(-1)).intValue();
        int operationid = NumberTool.convertMapKeyToInt(condition, "operationid", Integer.valueOf(-1)).intValue();
        StringBuffer _sql = new StringBuffer();
        _sql.append(" select a.objid,a.name,b.key typeid,b.value type,c.key operationid,c.value operation,d.key statusid,d.value status,a.description from WF_MODELONE\n");
        _sql.append(" a left join WF_PROCESSLIST b on a.typeid=b.key and b.status<>100 and b.name='ProcessType' left join WF_MODELENUM c on\n");
        _sql.append(" a.operationid=c.key and c.name='ModelOne' left join WF_MODELENUM d on a.status=d.key\n");
        _sql.append(" and d.name='ModelStatus' where a.status<>100\n ");
        if(!"".equals(name))
            _sql.append(" and lower(a.name) like '%").append(name.toLowerCase()).append("%' ");
        if(typeid != -1)
            _sql.append(" and a.typeid=").append(typeid).append(" ");
        if(operationid != -1)
            _sql.append(" and a.operationid=").append(operationid).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery;
    }

    public IMetaDBQuery getModelOneUrl(Map condition)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append(" select a.objid,b.key typeid,b.value type ,a.approveurl,a.editurl,a.watchurl from WF_MODELONEURL a left join\n ");
        _sql.append(" WF_PROCESSLIST b on a.typeid=b.key and b.name='ProcessType' and b.status<>100 where a.status<>100\n ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery;
    }

    public IMetaDBQuery getModelProcess(Map condition)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append(" select * from WF_MODELPROCESS where status<>100 ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery;
    }

    public IMetaDBQuery getModelOneList(Map condition)
    {
        int processid = NumberTool.convertMapKeyToInt(condition, "processid", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        _sql.append(" select w.*,m.name mname from WF_MODELONELIST w left join sirm_messagetemplate m on ");
        _sql.append(" w.taskremind=m.code where status<>100 and processid=:processid order by sort asc ");
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(param);
        return _dbQuery;
    }

    public IWorkflowModelOne loadModelOne(int objid)
    {
        if(objid <= 0)
            return new WorkflowModelOneImpl();
        else
            return (WorkflowModelOneImpl)getMetaDBContext().get("WFMODELONE", objid);
    }

    public IWorkflowModelOneUrl loadModelOneUrl(int objid)
    {
        if(objid <= 0)
            return new WorkflowModelOneUrlImpl();
        else
            return (WorkflowModelOneUrlImpl)getMetaDBContext().get("WFMODELONEURL", objid);
    }

    public IWorkflowModelProcess loadModelProcess(int processid)
    {
        String sql = "select objid from WF_MODELPROCESS where processid=:processid ";
        Map paramap = new HashMap();
        paramap.put("processid", Integer.valueOf(processid));
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        dbQuery.setParameters(paramap);
        List modelprocesslist = dbQuery.getResult();
        int objid = 0;
        if(!modelprocesslist.isEmpty())
            objid = NumberTool.convertMapKeyToInt((Map)modelprocesslist.get(0), "objid", Integer.valueOf(0)).intValue();
        if(objid == 0)
            return new WorkflowModelProcessImpl();
        else
            return (WorkflowModelProcessImpl)getMetaDBContext().get("WFMODELPROCESS", objid);
    }

    public IWorkflowModelOneList loadModelOneList(int objid)
    {
        if(objid <= 0)
            return new WorkflowModelOneListImpl();
        else
            return (WorkflowModelOneListImpl)getMetaDBContext().get("WFMODELONELIST", objid);
    }

    public boolean isModelOneNameAvailable(int objid, String name)
    {
        String sql = "select objid from WF_MODELONE where name=:name and status<>100 and objid<>:objid";
        Map paramap = new HashMap();
        paramap.put("name", name);
        paramap.put("objid", Integer.valueOf(objid));
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        dbQuery.setParameters(paramap);
        return dbQuery.getResultCount() == 0;
    }

    public boolean isUrlTypeAvailable(int objid, int typeid)
    {
        String sql = "select objid from WF_MODELONEURL where typeid=:typeid and status<>100 and objid<>:objid";
        Map paramap = new HashMap();
        paramap.put("typeid", Integer.valueOf(typeid));
        paramap.put("objid", Integer.valueOf(objid));
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        dbQuery.setParameters(paramap);
        return dbQuery.getResultCount() == 0;
    }

    public IMetaDBQuery getModelOneIdAndName()
    {
        String sql = " select objid,name from WF_MODELONE where status=1 ";
        return getMetaDBContext().createSqlQuery(sql);
    }

    public List getModelOneListByType(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select m.name as value,m.objid as key ");
        _sql.append("from WF_MODELONE m ");
        _sql.append("where m.status=1 ");
        if(type != 0)
            _sql.append("and m.typeid=").append(type).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getModelIdAndName()
    {
        StringBuffer sql = (new StringBuffer()).append(" select o.objid,o.name,p.processid from WF_MODELPROCESS p ");
        sql.append(" left join WF_MODELONE o on o.objid=p.templateid where p.status<>100 and o.status<>100 ");
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        return dbQuery;
    }

    public int getModelOneListMaxSort(int processid)
    {
        String sql = " select max(sort) c from WF_MODELONELIST where processid=:processid and status<>100 ";
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        dbQuery.setParameters(param);
        List resultlist = dbQuery.getResult();
        if(resultlist.isEmpty())
            return 0;
        else
            return NumberTool.convertMapKeyToInt((Map)resultlist.get(0), "c", Integer.valueOf(0)).intValue();
    }

    public IMetaDBQuery loadModelOneListByProcessId(int processid)
    {
        String sql = " select objid from WF_MODELONELIST where processid=:processid and status<>100";
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        dbQuery.setParameters(param);
        return dbQuery;
    }

    public int getProcessModelId(int processid, String templatetype)
    {
        String sql = " select templateid from WF_MODELPROCESS where templatetype=:templatetype and processid=:processid ";
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        param.put("templatetype", templatetype);
        dbQuery.setParameters(param);
        List resultlist = dbQuery.getResult();
        if(resultlist.isEmpty())
        {
            return 0;
        } else
        {
            int modelid = NumberTool.convertMapKeyToInt((Map)resultlist.get(0), "templateid", Integer.valueOf(0)).intValue();
            return modelid;
        }
    }

    public int getModelOperation(int objid, String templatetype)
    {
        String sql = (new StringBuilder()).append(" select operationid from WF_MODEL").append(templatetype.toUpperCase()).append(" where objid=:objid ").toString();
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        Map param = new HashMap();
        param.put("objid", Integer.valueOf(objid));
        dbQuery.setParameters(param);
        List resultlist = dbQuery.getResult();
        if(resultlist.isEmpty())
        {
            return 0;
        } else
        {
            int operationid = NumberTool.convertMapKeyToInt((Map)resultlist.get(0), "operationid", Integer.valueOf(0)).intValue();
            return operationid;
        }
    }

    public IMetaDBQuery getProcess(int process)
    {
        StringBuilder sql = new StringBuilder();
        sql.append(" select p.objid,p.name,p.status,pl.value as statusname,p.processcode,pl2.value as processtypename,p.processtype, \n");
        sql.append(" p.processbrief,p.sysversion \n");
        sql.append(" from WF_PROCESS p left join WF_PROCESSLIST pl on pl.name='ProcessStatus' and p.status=pl.key \n");
        sql.append(" left join WF_PROCESSLIST pl2 on pl2.name='ProcessType' and p.processtype=pl2.key where p.objid=:objid\n");
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        Map param = new HashMap();
        param.put("objid", Integer.valueOf(process));
        dbQuery.setParameters(param);
        return dbQuery;
    }

    public int getProcessStepMaxObjid()
    {
        String sql = " select max(objid) c from WF_PROCESSSTEP ";
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        List resultlist = dbQuery.getResult();
        if(resultlist == null)
            return 0;
        else
            return NumberTool.convertMapKeyToInt((Map)resultlist.get(0), "c", Integer.valueOf(0)).intValue();
    }

    public IMetaDBQuery getProcessUrlById(int processid)
    {
        StringBuilder sql = new StringBuilder();
        sql.append(" select distinct a.objid,b.key typeid,b.value type ,a.approveurl,a.editurl,a.watchurl from WF_MODELONEURL a left join\n");
        sql.append(" WF_PROCESSLIST b on a.typeid=b.key and b.name='ProcessType' and b.status<>100 left join WF_PROCESS p on\n");
        sql.append(" a.typeid=p.processtype where a.status<>100 and p.objid=:processid ");
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        dbQuery.setParameters(param);
        return dbQuery;
    }

    public Map convertModelToFull(int processid)
    {
        Map condition = new HashMap();
        condition.put("processid", Integer.valueOf(processid));
        IMetaDBQuery dbQuery = getModelOneList(condition);
        List resultlist = dbQuery.getResult();
        if(resultlist.isEmpty())
            return new HashMap();
        int processmodelid = getProcessModelId(processid, "One");
        if(processid == 0)
            return new HashMap();
        int modeloperationid = getModelOperation(processmodelid, "One");
        if(modeloperationid == 0)
            return new HashMap();
        List processlist = getProcess(processid).getResult();
        if(processlist.isEmpty())
            return new HashMap();
        String ApproveUrl = null;
        String EditUrl = null;
        String WatchUrl = null;
        String UrlType = null;
        List urllist = getProcessUrlById(processid).getResult();
        if(!urllist.isEmpty())
        {
            Map urlmap = (Map)urllist.get(0);
            if(!urlmap.isEmpty())
            {
                ApproveUrl = StringUtil.convertMapKeyToString(urlmap, "approveurl", "");
                EditUrl = StringUtil.convertMapKeyToString(urlmap, "editurl", "");
                WatchUrl = StringUtil.convertMapKeyToString(urlmap, "watchurl", "");
                UrlType = StringUtil.convertMapKeyToString(urlmap, "type", "");
            }
        }
        String processcode = StringUtil.convertMapKeyToString((Map)processlist.get(0), "processcode", "");
        String processtype = ((Map)processlist.get(0)).get("processtype") != null ? ((Map)processlist.get(0)).get("processtype").toString() : "";
        List steplist = new ArrayList();
        String processname = StringUtil.convertMapKeyToString((Map)processlist.get(0), "name", "");
        String processbrief = StringUtil.convertMapKeyToString((Map)processlist.get(0), "processbrief", "");
        int i = getProcessStepMaxObjid();
        int sorti = 1;
        int i1 = i + 1;
        Map finalmap = new HashMap();
        finalmap.put("processcode", processcode);
        finalmap.put("processtype", processtype);
        finalmap.put("steplist", steplist);
        finalmap.put("name", processname);
        finalmap.put("processbrief", processbrief);
        Map startmap = new HashMap();
        startmap.put("pointtype", "0");
        startmap.put("objid", Integer.valueOf(++i));
        startmap.put("sort", Integer.valueOf(sorti++));
        startmap.put("condition", ";;;;;0;");
        startmap.put("ownerlist", new ArrayList());
        startmap.put("ownerlinklist", new ArrayList());
        List startlinklist = new ArrayList();
        Map startlinkmap = new HashMap();
        startlinkmap.put("sort", "1");
        startlinkmap.put("iflist", new ArrayList());
        startlinkmap.put("aftstepid", Integer.valueOf(i1 + 1));
        startlinkmap.put("prestepid", Integer.valueOf(i1));
        startlinkmap.put("dolist", new ArrayList());
        startlinklist.add(startlinkmap);
        startmap.put("linklist", startlinklist);
        startmap.put("showurl", "");
        startmap.put("steptypeads", "");
        startmap.put("actionurl", "");
        startmap.put("name", "\u5F00\u59CB\u6B65\u9AA4");
        startmap.put("steptype", "1");
        startmap.put("urlname", "\u65E0\u6570\u636E");
        steplist.add(startmap);
        Map submitmap = new HashMap();
        submitmap.put("pointtype", "0");
        submitmap.put("objid", Integer.valueOf(++i));
        int submitid = i;
        submitmap.put("sort", Integer.valueOf(sorti++));
        submitmap.put("condition", ";;;;;0;");
        List submitownerlist = new ArrayList();
        Map submitorgmap = new HashMap();
        submitorgmap.put("orgtype", "-2");
        submitorgmap.put("ownergoto", "0");
        submitorgmap.put("orgid", "1");
        submitorgmap.put("value", "1");
        submitorgmap.put("ownergotoid", Integer.valueOf(submitid));
        submitownerlist.add(submitorgmap);
        submitmap.put("ownerlist", submitownerlist);
        submitmap.put("ownerlinklist", new ArrayList());
        List submitlinklist = new ArrayList();
        Map submitlinkmap = new HashMap();
        submitlinkmap.put("sort", "1");
        submitlinkmap.put("iflist", new ArrayList());
        submitlinkmap.put("aftstepid", Integer.valueOf(submitid + 1));
        submitlinkmap.put("prestepid", Integer.valueOf(submitid));
        submitlinkmap.put("dolist", new ArrayList());
        submitlinklist.add(submitlinkmap);
        submitmap.put("linklist", submitlinklist);
        submitmap.put("showurl", WatchUrl);
        submitmap.put("steptypeads", "2");
        submitmap.put("actionurl", EditUrl);
        submitmap.put("name", "\u63D0\u4EA4");
        submitmap.put("steptype", "7");
        submitmap.put("urlname", (new StringBuilder()).append(UrlType).append(" \u4FEE\u6539\u9875\u9762").toString());
        steplist.add(submitmap);
        int resultcount = resultlist.size();
        Iterator i$ = resultlist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map resultmap = (Map)i$.next();
            resultcount--;
            int i2 = i + 1;
            String name = StringUtil.convertMapKeyToString(resultmap, "name", "");
            String rules = StringUtil.convertMapKeyToString(resultmap, "rules", "");
            String executor = StringUtil.convertMapKeyToString(resultmap, "executor", "");
            String taskremind = StringUtil.convertMapKeyToString(resultmap, "taskremind", "");
            Map stepmap = new HashMap();
            stepmap.put("pointtype", "0");
            stepmap.put("objid", Integer.valueOf(++i));
            stepmap.put("sort", Integer.valueOf(sorti++));
            StringBuilder condition1 = new StringBuilder();
            condition1.append(taskremind).append(";;;;;0");
            stepmap.put("condition", condition1.toString());
            List ownerlist = new ArrayList();
            Map orgmap = new HashMap();
            orgmap.put("orgtype", "-2");
            orgmap.put("ownergoto", "0");
            orgmap.put("orgid", "1");
            orgmap.put("value", "1");
            orgmap.put("ownergotoid", Integer.valueOf(i2));
            ownerlist.add(orgmap);
            boolean flag = true;
            String restexecutor = executor;
            while(flag) 
            {
                String aexecutor = null;
                if(!restexecutor.contains(","))
                {
                    aexecutor = restexecutor;
                    flag = false;
                } else
                {
                    aexecutor = restexecutor.substring(0, restexecutor.indexOf(","));
                    restexecutor = restexecutor.substring(restexecutor.indexOf(",") + 1);
                }
                String orgid = aexecutor.substring(0, aexecutor.indexOf(":"));
                aexecutor = aexecutor.substring(aexecutor.indexOf(":") + 1);
                String orgtype = aexecutor.substring(aexecutor.indexOf(":") + 1);
                Map ownermap = new HashMap();
                ownermap.put("orgtype", orgtype);
                ownermap.put("ownergoto", "0");
                ownermap.put("orgid", orgid);
                ownermap.put("value", "1");
                ownermap.put("ownergotoid", Integer.valueOf(i2));
                ownerlist.add(ownermap);
            }
            stepmap.put("ownerlist", ownerlist);
            stepmap.put("ownerlinklist", new ArrayList());
            List linklist = new ArrayList();
            Map qualifiedmap = new HashMap();
            qualifiedmap.put("sort", "1");
            List qualifiediflist = new ArrayList();
            Map qualifiedifmap = new HashMap();
            qualifiedifmap.put("ifand", "1");
            qualifiedifmap.put("iftype", "2");
            qualifiedifmap.put("ifads", "1");
            qualifiediflist.add(qualifiedifmap);
            qualifiedmap.put("iflist", qualifiediflist);
            if(modeloperationid != 3)
                qualifiedmap.put("aftstepid", Integer.valueOf(i2 + 1));
            else
                qualifiedmap.put("aftstepid", Integer.valueOf(i2 + 2));
            qualifiedmap.put("prestepid", Integer.valueOf(i2));
            qualifiedmap.put("dolist", new ArrayList());
            linklist.add(qualifiedmap);
            Map noqualifiedmap = new HashMap();
            noqualifiedmap.put("sort", "2");
            List noqualifiediflist = new ArrayList();
            Map noqualifiedifmap = new HashMap();
            noqualifiedifmap.put("ifand", "1");
            noqualifiedifmap.put("iftype", "2");
            noqualifiedifmap.put("ifads", "2");
            noqualifiediflist.add(noqualifiedifmap);
            noqualifiedmap.put("iflist", noqualifiediflist);
            if(modeloperationid == 1)
            {
                noqualifiedmap.put("aftstepid", Integer.valueOf(submitid + resultlist.size() + 1));
                noqualifiedmap.put("prestepid", Integer.valueOf(i2));
                noqualifiedmap.put("dolist", new ArrayList());
                linklist.add(noqualifiedmap);
            } else
            if(modeloperationid == 2)
            {
                noqualifiedmap.put("aftstepid", Integer.valueOf(submitid));
                noqualifiedmap.put("prestepid", Integer.valueOf(i2));
                noqualifiedmap.put("dolist", new ArrayList());
                linklist.add(noqualifiedmap);
            } else
            if(modeloperationid == 3)
            {
                noqualifiedmap.put("aftstepid", Integer.valueOf(i2 + 1));
                noqualifiedmap.put("prestepid", Integer.valueOf(i2));
                noqualifiedmap.put("dolist", new ArrayList());
                linklist.add(noqualifiedmap);
            }
            stepmap.put("linklist", linklist);
            stepmap.put("showurl", WatchUrl);
            rules = rules.trim().replaceAll(";", " ");
            if(rules.contains(" "))
                rules = (new StringBuilder()).append(rules).append(".0").toString();
            if(rules.startsWith("1"))
                rules = "1";
            stepmap.put("steptypeads", rules);
            stepmap.put("actionurl", ApproveUrl);
            stepmap.put("name", name);
            stepmap.put("steptype", "3");
            stepmap.put("urlname", (new StringBuilder()).append(UrlType).append(" \u5BA1\u6279\u9875\u9762").toString());
            steplist.add(stepmap);
            if(modeloperationid == 3)
            {
                Map exsubmitmap = new HashMap();
                exsubmitmap.put("pointtype", "0");
                exsubmitmap.put("objid", Integer.valueOf(++i));
                int exsubmitid = i;
                exsubmitmap.put("sort", Integer.valueOf(sorti++));
                exsubmitmap.put("condition", ";;;;;0;");
                List exsubmitownerlist = new ArrayList();
                Map exsubmitorgmap = new HashMap();
                exsubmitorgmap.put("orgtype", "-2");
                exsubmitorgmap.put("ownergoto", "0");
                exsubmitorgmap.put("orgid", "1");
                exsubmitorgmap.put("value", "1");
                exsubmitorgmap.put("ownergotoid", Integer.valueOf(exsubmitid));
                exsubmitownerlist.add(exsubmitorgmap);
                exsubmitmap.put("ownerlist", submitownerlist);
                exsubmitmap.put("ownerlinklist", new ArrayList());
                List exsubmitlinklist = new ArrayList();
                Map exsubmitlinkmap = new HashMap();
                exsubmitlinkmap.put("sort", "1");
                exsubmitlinkmap.put("iflist", new ArrayList());
                exsubmitlinkmap.put("aftstepid", Integer.valueOf(i2));
                exsubmitlinkmap.put("prestepid", Integer.valueOf(exsubmitid));
                exsubmitlinkmap.put("dolist", new ArrayList());
                exsubmitlinklist.add(exsubmitlinkmap);
                exsubmitmap.put("linklist", exsubmitlinklist);
                exsubmitmap.put("showurl", WatchUrl);
                exsubmitmap.put("steptypeads", "2");
                exsubmitmap.put("actionurl", EditUrl);
                exsubmitmap.put("name", (new StringBuilder()).append("\u63D0\u4EA4").append(resultlist.size() - resultcount).toString());
                exsubmitmap.put("steptype", "7");
                exsubmitmap.put("urlname", (new StringBuilder()).append(UrlType).append(" \u4FEE\u6539\u9875\u9762").toString());
                steplist.add(exsubmitmap);
            }
        } while(true);
        Map endmap = new HashMap();
        endmap.put("pointtype", "0");
        endmap.put("objid", Integer.valueOf(++i));
        endmap.put("sort", "10000");
        endmap.put("condition", "");
        endmap.put("ownerlist", new ArrayList());
        endmap.put("ownerlinklist", new ArrayList());
        endmap.put("linklist", new ArrayList());
        endmap.put("showurl", "");
        endmap.put("steptypeads", "");
        endmap.put("actionurl", "");
        endmap.put("name", "\u7ED3\u675F\u6B65\u9AA4");
        endmap.put("steptype", "2");
        endmap.put("urlname", "\u65E0\u6570\u636E");
        steplist.add(endmap);
        return finalmap;
    }

    public IMetaDBQuery getDataByProcessId(String tablename, int processid)
    {
        String sql = (new StringBuilder()).append(" select * from ").append(tablename).append(" where processid=:processid ").toString();
        IMetaDBQuery dbQuery = getMetaDBContext().createSqlQuery(sql);
        Map param = new HashMap();
        param.put("processid", Integer.valueOf(processid));
        dbQuery.setParameters(param);
        return dbQuery;
    }

    public int freshProcessModel(int oldid, int newid)
    {
        IWorkflowModelProcess _oldmodel = loadModelProcess(oldid);
        if(_oldmodel.size() > 0)
        {
            IWorkflowModelProcess _modelprocess = loadModelProcess(0);
            _modelprocess.setTemplateType(_oldmodel.getTemplateType());
            _modelprocess.setTemplateId(_oldmodel.getTemplateId());
            _modelprocess.setStatus(Integer.valueOf(1));
            _modelprocess.setProcessId(Integer.valueOf(newid));
            saveModelProcess(_modelprocess);
            Map condition = new HashMap();
            condition.put("processid", Integer.valueOf(oldid));
            List onelist = getModelOneList(condition).getResult();
            for(int i = 0; i < onelist.size(); i++)
            {
                Map onemap = (Map)onelist.get(i);
                int oneid = NumberTool.convertMapKeyToInt(onemap, "objid", Integer.valueOf(0)).intValue();
                IWorkflowModelOneList modelone = loadModelOneList(oneid);
                IWorkflowModelOneList modelone2 = (IWorkflowModelOneList)modelone.clone();
                modelone2.setProcessId(Integer.valueOf(newid));
                saveModelOneList(modelone2);
            }

            return _modelprocess.getId();
        } else
        {
            return -1;
        }
    }

    public int freshProcessId(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = processid;
        IWorkflowModelProcess modelProcess = loadModelProcess(_processid);
        if(modelProcess.size() > 0 && modelProcess.getStatus().intValue() == 100)
        {
            List processlist = _workbase.getProcessHistoryList(_processid);
            Map processmap = (Map)processlist.get(processlist.size() - 1);
            _processid = NumberTool.convertMapKeyToInt(processmap, "objid", Integer.valueOf(0)).intValue();
            modelProcess = loadModelProcess(_processid);
        }
        return _processid;
    }
}
