// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowBaseServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepType;
import com.sinitek.sirm.busin.workflow.service.IWorkflowBaseService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class WorkflowBaseServiceImpl extends MetaDBContextSupport
    implements IWorkflowBaseService
{

    public WorkflowBaseServiceImpl()
    {
    }

    public int saveProcess(IWorkflowProcess process)
    {
        super.save(process);
        if(process.getSort() == null || process.getSort().intValue() == 0)
        {
            process.setSort(Integer.valueOf(process.getId()));
            super.save(process);
        }
        return process.getId();
    }

    public int saveProcessStep(IWorkflowProcessStep step)
    {
        super.save(step);
        if(step.getStepCode() == null || step.getStepCode().intValue() <= 0)
        {
            step.setStepCode(Integer.valueOf(step.getId()));
            super.save(step);
        }
        return step.getId();
    }

    public int saveProcessStepLink(IWorkflowProcessStepLink link)
    {
        super.save(link);
        return link.getId();
    }

    public int saveProcessStepLinkIf(IWorkflowProcessStepLinkIf linkif)
    {
        super.save(linkif);
        return linkif.getId();
    }

    public int saveProcessStepLinkDo(IWorkflowProcessStepLinkDo linkdo)
    {
        super.save(linkdo);
        return linkdo.getId();
    }

    public int saveProcessStepDo(IWorkflowProcessStepDo stepdo)
    {
        super.save(stepdo);
        return stepdo.getId();
    }

    public int saveProcessOwner(IWorkflowProcessOwner processwner)
    {
        super.save(processwner);
        return processwner.getId();
    }

    public int saveProcessOwnerLink(IWorkflowProcessOwnerLink ownerlink)
    {
        super.save(ownerlink);
        return ownerlink.getId();
    }

    public int saveAgents(IWorkflowAgents agents)
    {
        super.save(agents);
        return agents.getId();
    }

    public int saveProcessStepHistory(IWorkflowProcessStepHistory stephistory)
    {
        super.save(stephistory);
        return stephistory.getId();
    }

    public int saveProcessUrl(IWorkflowProcessUrl processurl)
    {
        super.save(processurl);
        return processurl.getId();
    }

    public int saveProcessList(IWorkflowProcessList processlist)
    {
        super.save(processlist);
        return processlist.getId();
    }

    public IWorkflowProcess loadProcess(int processid)
    {
        if(processid <= 0)
            return new WorkflowProcessImpl();
        else
            return (IWorkflowProcess)getMetaDBContext().get("WFPROCESS", processid);
    }

    public IWorkflowProcessStep loadProcessStep(int stepid)
    {
        if(stepid <= 0)
            return new WorkflowProcessStepImpl();
        else
            return (IWorkflowProcessStep)getMetaDBContext().get("WFPROCESSSTEP", stepid);
    }

    public IWorkflowProcessStepLink loadProcessStepLink(int linkid)
    {
        if(linkid <= 0)
            return new WorkflowProcessStepLinkImpl();
        else
            return (IWorkflowProcessStepLink)getMetaDBContext().get("WFPROCESSSTEPLINK", linkid);
    }

    public IWorkflowProcessStepLinkIf loadProcessStepLinkIf(int linkifid)
    {
        if(linkifid <= 0)
            return new WorkflowProcessStepLinkIfImpl();
        else
            return (IWorkflowProcessStepLinkIf)getMetaDBContext().get("WFPROCESSSTEPLINKIF", linkifid);
    }

    public IWorkflowProcessStepLinkDo loadProcessStepLinkDo(int linkdo)
    {
        if(linkdo <= 0)
            return new WorkflowProcessStepLinkDoImpl();
        else
            return (IWorkflowProcessStepLinkDo)getMetaDBContext().get("WFPROCESSSTEPLINKDO", linkdo);
    }

    public IWorkflowProcessStepDo loadProcessStepDo(int stepdoid)
    {
        if(stepdoid <= 0)
            return new WorkflowProcessStepDoImpl();
        else
            return (IWorkflowProcessStepDo)getMetaDBContext().get("WFPROCESSSTEPDO", stepdoid);
    }

    public IWorkflowProcessOwner loadProcessOwner(int ownerid)
    {
        if(ownerid <= 0)
            return new WorkflowProcessOwnerImpl();
        else
            return (IWorkflowProcessOwner)getMetaDBContext().get("WFPROCESSOWNER", ownerid);
    }

    public IWorkflowProcessOwnerLink loadProcessOwnerLink(int ownerlinkid)
    {
        if(ownerlinkid <= 0)
            return new WorkflowProcessOwnerLinkImpl();
        else
            return (IWorkflowProcessOwnerLink)getMetaDBContext().get("WFPROCESSOWNERLINK", ownerlinkid);
    }

    public IWorkflowAgents loadAgents(int agentsid)
    {
        if(agentsid <= 0)
            return new WorkflowAgentsImpl();
        else
            return (IWorkflowAgents)getMetaDBContext().get("WFAGENTS", agentsid);
    }

    public IWorkflowProcessStepHistory loadProcessStepHistory(int stephistoryid)
    {
        if(stephistoryid <= 0)
            return new WorkflowProcessStepHistoryImpl();
        else
            return (IWorkflowProcessStepHistory)getMetaDBContext().get("WFPROCESSSTEPHISTORY", stephistoryid);
    }

    public IWorkflowProcessUrl loadProcessUrl(int processurlid)
    {
        if(processurlid <= 0)
            return new WorkflowProcessUrlImpl();
        else
            return (IWorkflowProcessUrl)getMetaDBContext().get("WFPROCESSURL", processurlid);
    }

    public IWorkflowProcessList loadProcessList(int processlistid)
    {
        if(processlistid <= 0)
            return new WorkflowProcessListImpl();
        else
            return (IWorkflowProcessList)getMetaDBContext().get("WFPROCESSLIST", processlistid);
    }

    public int addProcess(String name)
    {
        return addProcess(name, 0);
    }

    public int addProcess(String name, int processid)
    {
        IWorkflowProcess _process = loadProcess(processid);
        _process.setName(name);
        _process.setStatus(Integer.valueOf(1));
        return saveProcess(_process);
    }

    public int addProcess(String name, int processid, String processcode)
    {
        IWorkflowProcess _process = loadProcess(processid);
        _process.setName(name);
        _process.setStatus(Integer.valueOf(1));
        _process.setProcessCode(processcode);
        return saveProcess(_process);
    }

    public int addProcessStep(String name, int processid, int steptype)
    {
        return addProcessStep(name, processid, steptype, 0);
    }

    public int addProcessStep(String name, int processid, int steptype, int stepid)
    {
        IWorkflowProcessStep _step = loadProcessStep(stepid);
        _step.setName(name);
        _step.setProcessId(Integer.valueOf(processid));
        _step.setStepTypeId(Integer.valueOf(steptype));
        _step.setStatus(Integer.valueOf(1));
        List _list = getProcessStepList(processid);
        int _sort = 5000;
        if(_list.size() >= 2)
        {
            Map _map = (Map)_list.get(_list.size() - 2);
            _sort = NumberTool.convertMapKeyToInt(_map, "sort", Integer.valueOf(_sort)).intValue();
        } else
        if(_list.size() == 1)
            _sort++;
        _sort++;
        if(steptype == 1)
            _sort = 1;
        else
        if(steptype == 2)
            _sort = 10000;
        _step.setSort(Integer.valueOf(_sort));
        return saveProcessStep(_step);
    }

    public boolean testProcessNameUsed(int processid, String name)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.objid!=").append(processid).append(" and p.name='").append(name).append("'");
        _sql.append(" and (p.status=1 or p.status=2) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list.size() == 0;
    }

    public List getProcessByName(String name, int processid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status \n");
        _sql.append("from WF_PROCESS p \n");
        _sql.append("where p.name='").append(name).append("' \n");
        _sql.append(" and (p.status=1 or p.status=2) \n");
        if(processid != 0)
            _sql.append(" and p.objid!=").append(processid).append(" \n");
        _sql.append(" order by p.objid desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public Map getProcessBySyscode(String syscode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p2.objid as processid,p2.name as processname,p2.status,p2.processtype ");
        _sql.append("from WF_PROCESS p2 ");
        _sql.append("where p2.objid in( ");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.syscode='").append(syscode).append("' and (p.status=2 or p.status=3 or p.status=100) ");
        _sql.append("group by p.syscode) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map getProcessStepByStepcode(String stepcode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select sp2.name,sp2.stepcode,sp2.processid \n");
        _sql.append("from WF_PROCESSSTEP sp2 \n");
        _sql.append("where sp2.processid in( \n");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.status!=1 and p.status!=5 ");
        _sql.append(")\n");
        _sql.append(" and sp2.status!=100  and sp2.steptypeid not in (1,2) \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public List getProcessListBySyscode(String syscode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.syscode='").append(syscode).append("'");
        _sql.append(" and (p.status=1 or p.status=2) ");
        _sql.append(" order by p.objid desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public boolean testProcessCodeUsed(int processid, String code)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.objid!=").append(processid).append(" and p.processcode='").append(code).append("'");
        _sql.append(" and (p.status=1 or p.status=2) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list.size() == 0;
    }

    public boolean testProcessStepNameUsed(int processid, int stepid, String name)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("where ps.objid!=").append(stepid).append(" and ps.processid=").append(processid).append(" and ps.name='").append(name).append("'");
        _sql.append(" and (ps.status=1 or ps.status=2) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list.size() == 0;
    }

    public List getProcessList(Map condition)
    {
        String _name = StringUtil.safeToString(condition.get("processname"), "");
        String _processtype = StringUtil.safeToString(condition.get("processtype"), "-1");
        String _status = StringUtil.safeToString(condition.get("status"), "-1");
        int _type = NumberTool.convertMapKeyToInt(condition, "type", Integer.valueOf(0)).intValue();
        int claimType = NumberTool.safeToInteger(condition.get("claimType"), Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _paramQ = new HashMap();
        _sql.append("select p.objid,p.name,p.status,pl.value as statusname,p.processcode,pl2.value as processtypename,p.processtype, ");
        _sql.append("p.processbrief,p.sysversion, p.phoneshow ");
        _sql.append("from WF_PROCESS p left join WF_PROCESSLIST pl on pl.name='ProcessStatus' and p.status=pl.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='ProcessType' and p.processtype=pl2.key ");
        if(_type == 0)
            _sql.append("where (p.status=1 or p.status=2) ");
        else
        if(_type == 1)
            _sql.append("where p.status=2 ");
        else
        if(_type == -1)
            _sql.append("where 1=1 ");
        else
            _sql.append("where 1=0 ");
        if(claimType == 0)
            _sql.append(" and (p.claimType is null or p.claimType=0) ");
        else
            _sql.append(" and p.claimType=").append(claimType).append(" ");
        if(!"".equals(_name))
        {
            _sql.append(" and p.name like :name ");
            _paramQ.put("name", (new StringBuilder()).append("%").append(_name).append("%").toString());
        }
        if(!"-1".equals(_processtype))
            _sql.append(" and p.processtype=").append(_processtype).append(" ");
        if(!"-1".equals(_status))
        {
            _sql.append(" and p.status=:status ");
            _paramQ.put("status", _status);
        }
        if(_type != 0)
            _sql.append("order by p.sort desc ");
        else
            _sql.append("order by p.updatetimestamp desc");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_paramQ);
        return _dbQuery.getResult();
    }

    public List getProcessList2(Map map)
    {
        String _name = StringUtil.safeToString(map.get("processname"), "");
        String _processtype = StringUtil.safeToString(map.get("processtype"), "-1");
        String _status = StringUtil.safeToString(map.get("status"), "-1");
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status,pl.value as statusname,p.processcode,pl2.value as processtypename,p.processtype, ");
        _sql.append("p.processbrief,p.sysversion,mo.name as oname ");
        _sql.append("from WF_PROCESS p left join WF_PROCESSLIST pl on pl.name='ProcessStatus' and p.status=pl.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='ProcessType' and p.processtype=pl2.key ");
        _sql.append("left join WF_MODELPROCESS mp on p.objid = mp.processid ");
        _sql.append("left join WF_MODELONE mo on mp.templateid = mo.objid ");
        if(_type == 0)
            _sql.append("where (p.status=1 or p.status=2) ");
        else
        if(_type == 1)
            _sql.append("where p.status=2 ");
        else
        if(_type == -1)
            _sql.append("where 1=1 ");
        else
            _sql.append("where 1=0 ");
        if(!"".equals(_name))
            _sql.append(" and p.name like '%").append(_name).append("%' ");
        if(!"-1".equals(_processtype))
            _sql.append(" and p.processtype=").append(_processtype).append(" ");
        if(!"-1".equals(_status))
            _sql.append(" and p.status=").append(_status).append(" ");
        if(_type != 0)
            _sql.append("order by p.sort desc ");
        else
            _sql.append("order by p.updatetimestamp desc");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getProcessList(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status,pl.value as statusname,p.processcode,pl2.value as processtypename,p.processtype, ");
        _sql.append("p.processbrief,p.sysversion ");
        _sql.append("from WF_PROCESS p left join WF_PROCESSLIST pl on pl.name='ProcessStatus' and p.status=pl.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='ProcessType' and p.processtype=pl2.key ");
        if(type == 0)
            _sql.append("where (p.status=1 or p.status=2) ");
        else
        if(type == 1)
            _sql.append("where p.status=2 ");
        else
        if(type == -1)
            _sql.append("where 1=1 ");
        else
            _sql.append("where 1=0 ");
        if(type != 0)
            _sql.append("order by p.sort desc ");
        else
            _sql.append("order by p.updatetimestamp desc");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getProcessByNameAndStatus(String name, int status)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid as processid,p.name,p.status,p.processcode,p.sysversion ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.name=:name ");
        _params.put("name", name);
        if(status > 0)
        {
            _sql.append("and status=:status ");
            _params.put("status", Integer.valueOf(status));
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getProcessListByType(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p2.name as processname,p2.objid as processid ");
        _sql.append("from WF_PROCESS p2 ");
        _sql.append("where(p2.status=2 or p2.status=3) and p2.objid in( ");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where (p.status=2 or p.status=3 or p.status=100) ");
        if(type != 0)
            _sql.append("and p.processtype=").append(type).append(" ");
        _sql.append("and p.syscode is not null ");
        _sql.append("group by p.syscode) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getProcessHistoryList(int objid)
    {
        IWorkflowProcess _process = loadProcess(objid);
        if(_process.getSysCode() == null || _process.getSysCode().length() == 0)
            return new ArrayList();
        else
            return getProcessHistoryList(_process.getSysCode());
    }

    public List getProcessHistoryList(String syscode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid,p.name,p.status,pl.value as statusname,p.processcode,pl2.value as processtypename,p.processtype, ");
        _sql.append("p.processbrief,p.sysversion ");
        _sql.append("from WF_PROCESS p left join WF_PROCESSLIST pl on pl.name='ProcessStatus' and p.status=pl.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='ProcessType' and p.processtype=pl2.key ");
        _sql.append("where 1=1 and p.syscode='");
        int codeid = NumberTool.safeToInteger(syscode, Integer.valueOf(0)).intValue();
        if(codeid < 0)
            codeid = -codeid;
        _sql.append(codeid).append("' ");
        _sql.append("order by p.sysversion ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getProcessStepList(int processid)
    {
        IWorkflowProcess _process = loadProcess(processid);
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid,ps.name,ps.status,pl.value as statusname,ps.steptypeid,pl1.value as steptypename,ps.sort, \n");
        _sql.append("ps.steptypeads,ps.pointtypeid,pl2.value as pointtypename,ps.urlmark, ps.stepcode,ps.condition,ps.phoneshow \n");
        _sql.append("from WF_PROCESSSTEP ps left join WF_PROCESSLIST pl on pl.name='StepStatus' and ps.status=pl.key ");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='StepType' and ps.steptypeid=pl1.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepStage' and ps.pointtypeid=pl2.key and pl2.type=:processtype and pl2.status=1 ");
        _sql.append("where ps.processid=:processid and (ps.status=1 or ps.status=2 or ps.status=3)");
        _sql.append("order by ps.sort");
        _params.put("processtype", _process.getProcessType());
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getSingleStepList(int processid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid as processstepid,ps.objid as stepid,ps.steptypeid,ps.steptypeid as steptype,ps.name ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("where ps.processid=:processid and (ps.status=1 or ps.status=2 or ps.status=3) ");
        _sql.append("order by ps.sort");
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getProcessStepList(int processid, int steptype)
    {
        IWorkflowProcess _process = loadProcess(processid);
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid,ps.name,ps.status,pl.value as statusname,ps.steptypeid,pl1.value as steptypename,ps.sort, \n");
        _sql.append("ps.steptypeads,ps.pointtypeid,pl2.value as pointtypename,ps.urlmark, ps.stepcode,ps.condition,ps.phoneshow \n");
        _sql.append("from WF_PROCESSSTEP ps left join WF_PROCESSLIST pl on pl.name='StepStatus' and ps.status=pl.key \n");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='StepType' and ps.steptypeid=pl1.key \n");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepStage' and ps.pointtypeid=pl2.key and pl2.type=:processtype and pl2.status=1 \n");
        _sql.append("where ps.processid=:processid and (ps.status=1 or ps.status=2 or ps.status=3)\n");
        if(steptype > 0)
        {
            _sql.append(" and ps.steptypeid=:steptype\n");
            _params.put("steptype", Integer.valueOf(steptype));
        }
        _sql.append("order by ps.sort\n");
        _params.put("processtype", _process.getProcessType());
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getProcessLinkList(int processid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psl.aftstepid,psl.prestepid,psl.sort \r\n");
        _sql.append("from WF_PROCESSSTEPLINK psl \r\n");
        _sql.append("where psl.status=1 and psl.processid=:processid \r\n");
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getReleaseStepList(int processid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid,ps.name,ps.steptypeid,pu.actionurl,pu.showurl,pu.status as urlstatus ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("left join WF_PROCESSURL pu on ps.urlmark=pu.objid ");
        _sql.append("where ps.processid=:processid and (ps.status=1 or ps.status=2 or ps.status=3) ");
        Map _params = new HashMap();
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getOwnerStepList(int processid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select ps.objid as stepid ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("where ps.processid=:processid and ps.status<100 and (ps.steptypeid=3 or ps.steptypeid=7) ");
        _sql.append("order by ps.sort");
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getAgentStepList(int processid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select ps.objid as stepid,ps.stepcode ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("left join WF_PROCESSOWNER po on po.ownergoto=0 and po.orgtype=-2 and po.stepid=ps.objid ");
        _sql.append("where ps.processid=:processid and ps.status<100 and (ps.steptypeid=3 or ps.steptypeid=7) ");
        _sql.append("and (po.orgid is null or po.orgid != 2) ");
        _sql.append("order by ps.sort");
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public int isAgentStep(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("Select ps.objid stepid \n");
        _sql.append(" From  WF_PROCESSSTEP ps \n");
        _sql.append("Where ps.objid=:stepid and (ps.steptypeid = 3 or ps.steptypeid = 7) \n");
        _sql.append("  and ps.agenttype = 1 \n");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResultCount();
    }

    public List getAgentsList(Map map)
    {
        int objid = NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(0)).intValue();
        int typeid = NumberTool.convertMapKeyToInt(map, "typeid", Integer.valueOf(0)).intValue();
        int ownerid = NumberTool.convertMapKeyToInt(map, "ownerid", Integer.valueOf(0)).intValue();
        int agentsid = NumberTool.convertMapKeyToInt(map, "agentsid", Integer.valueOf(0)).intValue();
        int statustype = NumberTool.convertMapKeyToInt(map, "statustype", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select a.objid,a.ownerid,a.agentsid,cast(a.starttime as timestamp) as starttime,cast(a.endtime as timestamp) as endtime,a.status, ");
        _sql.append("a.processtype,pl.value as processtypevalue,a.processid,p.name as processname,a.processstepid,ps.name as stepname,e.exampleid, ");
        _sql.append("so.orgname as agentsname,so1.orgname as ownername ");
        _sql.append("from WF_AGENTS a left join WF_PROCESSLIST pl on pl.name='ProcessType' and a.processtype=pl.key ");
        _sql.append("left join WF_PROCESS p on to_char(a.processid)=p.syscode and (p.status=2 or p.status=3 or p.syscode='0') ");
        _sql.append("left join WF_PROCESSSTEP ps on a.processstepid=ps.stepcode and (p.objid=ps.processid or ps.stepcode=0) ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(a.agentsid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(a.ownerid) \n  left join wf_exampleentry e\n    on e.sourcename='WFAGENTS' and e.sourceid=a.objid\n");
        _sql.append("where 1=1 ");
        if(objid > 0)
        {
            _sql.append("and a.objid=:objid \n");
            _params.put("objid", Integer.valueOf(objid));
        } else
        {
            if(ownerid > 0)
            {
                _sql.append("and a.ownerid=:ownerid ");
                _params.put("ownerid", Integer.valueOf(ownerid));
            }
            if(agentsid > 0)
            {
                _sql.append("and a.agentsid=:agentsid ");
                _params.put("agentsid", Integer.valueOf(agentsid));
            }
            if(statustype == 1)
                _sql.append("and (a.status=1 or a.status=2 or a.status=49 or a.status=50 or a.status=80) ");
            else
            if(statustype == 3)
                _sql.append("and (a.status=3 or a.status=4) ");
            else
            if(statustype == 0)
                _sql.append("and a.status<100 ");
            else
            if(statustype == 100)
                _sql.append("and a.status=100 ");
            else
            if(statustype == -1)
                _sql.append("and a.processstepid>0 ");
            else
            if(statustype == -2)
                _sql.append("and a.processstepid<=0 ");
            if(typeid > 0)
                _sql.append("and a.processid=:typeid ");
            else
            if(typeid < 0)
            {
                _sql.append("and a.processtype=:typeid ");
                typeid = -typeid;
            }
            _sql.append("order by a.updatetimestamp desc ");
            _params.put("typeid", Integer.valueOf(typeid));
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getAgentsList(String orgid, int statustype)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select a.objid,a.ownerid,a.agentsid,cast(a.starttime as timestamp) as starttime,cast(a.endtime as timestamp) as endtime,a.status, ");
        _sql.append("a.processtype,pl.value as processtypevalue,a.processid,p.name as processname,a.processstepid,ps.name as stepname, ");
        _sql.append("so.orgname as agentsname,so1.orgname as ownername ");
        _sql.append("from WF_AGENTS a left join WF_PROCESSLIST pl on pl.name='ProcessType' and a.processtype=pl.key ");
        _sql.append("left join WF_PROCESS p on to_char(a.processid)=p.syscode and (p.status=2 or p.status=3) ");
        _sql.append("left join WF_PROCESSSTEP ps on a.processstepid=ps.stepcode and p.objid=ps.processid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(a.agentsid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(a.ownerid) ");
        _sql.append("where 1=1 ");
        if(orgid.length() > 0)
            _sql.append("and a.ownerid in (").append(orgid).append(")");
        if(statustype == 1)
            _sql.append("and (a.status=1 or a.status=2) ");
        else
        if(statustype == 3)
            _sql.append("and (a.status=3 or a.status=4) ");
        else
        if(statustype == 0)
            _sql.append("and a.status<100 ");
        else
        if(statustype == 100)
            _sql.append("and a.status=100 ");
        _sql.append("order by a.updatetimestamp desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public Map getProcessStepMain(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid,ps.name,ps.processid,ps.steptypeid,pl1.value as steptypename,ps.steptypeads, \n");
        _sql.append("ps.actionurl,ps.showurl,ps.urlmark,ps.condition,ps.pointtypeid,pl2.value as pointtypevalue,ps.stepspecial, \n");
        _sql.append("ps.stepcode,ps.phoneshow \n");
        _sql.append("from WF_PROCESSSTEP ps \n");
        _sql.append("inner join WF_PROCESS p on ps.processid=p.objid \n");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='StepType' and ps.steptypeid=pl1.key \n");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepStage' and ps.pointtypeid=pl2.key and pl2.status=1 and pl2.type=p.processtype \n");
        _sql.append("where ps.objid=").append(stepid);
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() == 0)
            return null;
        else
            return (Map)_list.get(0);
    }

    public List getOwnerList(int type, int gotoid)
    {
        return getOwnerListByGoto(type, gotoid, 0);
    }

    public List getOwnerListByGoto(int type, int gotoid, int gototype)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select po.objid,po.orgid,po.orgtype,po.value,so.orgname as orgname ");
        _sql.append("from WF_PROCESSOWNER po ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(po.orgid) ");
        _sql.append("where po.status=1 and po.ownergotoid=:gotoid and po.ownergoto=:ownergoto ");
        if(type != 0)
            if(type == -1)
                _sql.append("and po.orgtype=-1 ");
            else
            if(type == -100)
                _sql.append("and po.orgtype>0 ");
        Map _params = new HashMap();
        _params.put("gotoid", Integer.valueOf(gotoid));
        _params.put("ownergoto", Integer.valueOf(gototype));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getOwnerListByStepid(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select po.objid,po.orgid,po.orgtype,po.value,so.orgname as orgname ");
        _sql.append("from WF_PROCESSOWNER po ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(po.orgid) ");
        _sql.append("where po.status=1 and po.stepid=:stepid ");
        Map _params = new HashMap();
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getSingleOwnerList(int stepid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select po.objid as stepownerid ");
        _sql.append("from WF_PROCESSOWNER po ");
        _sql.append("where po.status=1 and po.stepid=:stepid ");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getOwnerLinkList(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pol.objid,pol.linkroot,pol.linkleaf,pl1.value as linkleafname,pol.ownerstarter,pol.ownerender ");
        _sql.append("from WF_PROCESSOWNERLINK pol ");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='OwnerLinkOrigin' and pol.linkleaf=pl1.key ");
        _sql.append("where pol.stepid=").append(stepid).append(" and pol.status=1 ");
        _sql.append("order by pol.sort");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getSingleOwnerLinkList(int stepid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pol.objid as ownerlinkid ");
        _sql.append("from WF_PROCESSOWNERLINK pol ");
        _sql.append("where pol.stepid=:stepid and pol.status=1 ");
        _sql.append("order by pol.sort");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getOwnerLinkIfList(int linkid)
    {
        return null;
    }

    public List getStepLinkList(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psl.objid,psl.prestepid,psl.aftstepid,psl.sort,ps.name as aftstepname , ps.steptypeid as aftsteptype \n");
        _sql.append("from WF_ProcessStepLink psl ");
        _sql.append("left join WF_ProcessStep ps on ps.objid=psl.aftstepid ");
        _sql.append("where psl.prestepid=").append(stepid).append(" and psl.status=1 ");
        _sql.append("order by psl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getSingleLinkList(int stepid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psl.objid as steplinkid  ");
        _sql.append("from WF_ProcessStepLink psl ");
        _sql.append("where psl.prestepid=:stepid and psl.status=1 ");
        _sql.append("order by psl.sort ");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getStepLinkListByAftstepid(int aftstepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psl.objid,psl.prestepid,psl.aftstepid,psl.sort,ps.name as aftstepname , ps.steptypeid as aftsteptype \n");
        _sql.append("from WF_ProcessStepLink psl ");
        _sql.append("left join WF_ProcessStep ps on ps.objid=psl.aftstepid ");
        _sql.append("where psl.aftstepid=").append(aftstepid).append(" and psl.status=1 ");
        _sql.append("order by psl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getStepLinkIfList(int linkid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psi.objid,psi.linkid,psi.iftype,psi.ifand,psi.ifads ");
        _sql.append("from WF_ProcessStepLinkIf psi ");
        _sql.append("where psi.linkid=").append(linkid);
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getSingleLinkIfList(int linkid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psi.objid as linkifid ");
        _sql.append("from WF_ProcessStepLinkIf psi ");
        _sql.append("where psi.linkid=:linkid");
        _params.put("linkid", Integer.valueOf(linkid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getStepLinkDoList(int linkid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psd.objid,psd.linkid,psd.dotype,psd.doads,psd.domark ");
        _sql.append("from WF_ProcessStepLinkDo psd ");
        _sql.append("where psd.linkid=").append(linkid);
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public List getSingleLinkDoList(int linkid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psd.objid as linkdoid ");
        _sql.append("from WF_ProcessStepLinkDo psd ");
        _sql.append("where psd.linkid=:linkid");
        _params.put("linkid", Integer.valueOf(linkid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List getStepHistoryList(int markid, int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select psh.preprocessid,psh.prestepid,psh.newprocessid,psh.newstepid ");
        _sql.append("from WF_PROCESSSTEPHISTORY psh ");
        _sql.append("inner join WF_PROCESSSTEP ps on psh.newstepid=ps.objid and (ps.status=1 or ps.status=2 or ps.status=3) ");
        _sql.append("where 1=1 ");
        if(type == 1)
            _sql.append("and psh.preprocessid=:markid ");
        else
        if(type == 2)
            _sql.append("and psh.prestepid=:markid ");
        else
        if(type == 3)
            _sql.append("and psh.newprocessid=:markid ");
        else
        if(type == 4)
            _sql.append("and psh.newstepid=:markid ");
        Map _params = new HashMap();
        _params.put("markid", Integer.valueOf(markid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public Map getHistoryStepMap(int type1, int type2, int processid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select psh.prestepid,psh.newstepid from WF_PROCESSSTEPHISTORY psh ");
        _sql.append("where 1=1 ");
        if(type1 == 0)
            _sql.append("and psh.newprocessid=:processid ");
        else
        if(type1 != -1)
            _sql.append("and psh.preprocessid=:processid ");
        _params.put("processid", Integer.valueOf(processid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        Map map = new HashMap();
        if(type2 == 0)
        {
            for(int i = 0; i < _list.size(); i++)
            {
                Map _map = (Map)_list.get(i);
                int _prestepid = NumberTool.convertMapKeyToInt(_map, "prestepid", Integer.valueOf(0)).intValue();
                int _newstepid = NumberTool.convertMapKeyToInt(_map, "newstepid", Integer.valueOf(0)).intValue();
                map.put((new StringBuilder()).append("").append(_newstepid).toString(), Integer.valueOf(_prestepid));
            }

        } else
        {
            for(int i = 0; i < _list.size(); i++)
            {
                Map _map = (Map)_list.get(i);
                int _prestepid = NumberTool.convertMapKeyToInt(_map, "prestepid", Integer.valueOf(0)).intValue();
                int _newstepid = NumberTool.convertMapKeyToInt(_map, "newstepid", Integer.valueOf(0)).intValue();
                map.put((new StringBuilder()).append("").append(_prestepid).toString(), Integer.valueOf(_newstepid));
            }

        }
        return map;
    }

    public Map getStepHistoryMap(String syscde)
    {
        Map resultmap = new HashMap();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select psh.prestepid,psh.newstepid ");
        _sql.append("from WF_PROCESSSTEPHISTORY psh ");
        _sql.append("inner join WF_PROCESS p on psh.newprocessid=p.objid and (p.status=2 or p.status=3) ");
        _sql.append("where 1=1 ");
        if(syscde.length() > 0)
        {
            _sql.append("and p.syscode=:syscode ");
            _params.put("syscode", syscde);
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int prestepid = NumberTool.convertMapKeyToInt(_map, "prestepid", Integer.valueOf(0)).intValue();
            int newstepid = NumberTool.convertMapKeyToInt(_map, "newstepid", Integer.valueOf(0)).intValue();
            resultmap.put((new StringBuilder()).append("").append(prestepid).toString(), Integer.valueOf(newstepid));
        }

        return resultmap;
    }

    public IMetaDBQuery getProcessUrlList(Map map)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pu.objid,pu.actionurl,pu.showurl,pu.viewurl,pu.name,pu.sort,pu.type,pl.value as typevalue \n");
        _sql.append("  from WF_PROCESSURL pu \n");
        _sql.append("     left join WF_PROCESSLIST pl on (pl.name='ProcessType' and pl.key=pu.type) \n");
        _sql.append("where pu.status=1 \n");
        int _searchtype = MapUtils.getIntValue(map, "searchtype", -1);
        if(_searchtype != -1)
        {
            _sql.append("   and pu.type=:searchtype \n");
            _params.put("searchtype", Integer.valueOf(_searchtype));
        }
        String _searchname = MapUtils.getString(map, "searchname", "");
        if(StringUtils.isNotBlank(_searchname))
        {
            _sql.append("   and ( upper(pu.name) like :searchname \n");
            _sql.append("       or pu.objid in (select b.objid from WF_PROCESSURL b where F_TRANS_PINYIN_CAPITAL(b.name) like :searchname) \n");
            _sql.append("       ) \n");
            _params.put("searchname", (new StringBuilder()).append("%").append(_searchname.toUpperCase()).append("%").toString());
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        _dbQuery.setDefaultOrderBy(" pu.sort asc, nlssort(pu.name,'NLS_SORT=SCHINESE_PINYIN_M')");
        return _dbQuery;
    }

    public int getProcessUrlMaxSort()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select max(pu.sort) maxsort ");
        _sql.append("from WF_PROCESSURL pu ");
        _sql.append("left join WF_PROCESSLIST pl on pl.name='ProcessType' and pl.key=pu.type ");
        _sql.append("where pu.status=1 ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List list = _dbQuery.getResult();
        return list.size() <= 0 ? 0 : MapUtils.getIntValue((Map)list.get(0), "maxsort", 0);
    }

    public List getProcessUrlListByType(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pu.objid as key,pu.name ");
        _sql.append("from WF_PROCESSURL pu ");
        _sql.append("where pu.status=1 ");
        Map _params = new HashMap();
        if(type > 0)
        {
            _sql.append("and (pu.type=:type or pu.type=0) ");
            _params.put("type", Integer.valueOf(type));
        } else
        if(type == -1)
            _sql.append("and pu.type>0 ");
        if(type > 0)
            _sql.append("order by pu.sort, NLSSORT(pu.name,'NLS_SORT = SCHINESE_PINYIN_M'),pu.type ");
        else
            _sql.append("order by pu.sort, NLSSORT(pu.name,'NLS_SORT = SCHINESE_PINYIN_M') ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List findParaListByName(String name)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.key,pl.value,pl.objid ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.status=1 and pl.name=:name ");
        _sql.append("order by pl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        Map _params = new HashMap();
        _params.put("name", name);
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List findProcessTypeList()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.value,pl.valueads,pl2.value as valueadsvalue,pl.sort ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='SpecialTask' and pl2.type=pl.key and pl.valueads=to_char(pl2.key) ");
        _sql.append("where pl.status=1 and pl.name='ProcessType' ");
        _sql.append("order by pl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        Map _params = new HashMap();
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public List findParaListByNameAndType(String name, int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.value,pl.type,pl.sort,pl.valueads,pl1.value as typevalue ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='ProcessType' and pl1.key=pl.type ");
        _sql.append("where pl.status=1 and pl.name=:name ");
        Map _params = new HashMap();
        _params.put("name", name);
        if(type > 0)
        {
            _sql.append("and (pl.type=:type or pl.type=0) ");
            _params.put("type", Integer.valueOf(type));
        } else
        if(type == -1)
            _sql.append("and pl.type>0 ");
        if(type > 0)
            _sql.append("order by NLSSORT(pl.value,'NLS_SORT = SCHINESE_PINYIN_M'), pl.type, pl.sort ");
        else
            _sql.append("order by NLSSORT(pl.value,'NLS_SORT = SCHINESE_PINYIN_M'), pl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery.getResult();
    }

    public Map findParaMapByNameKey(String name, int key)
    {
        return findParaMapByNameTypeKey(name, 0, key);
    }

    public Map findParaMapByNameTypeKey(String name, int type, int key)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.value,pl.type,pl.sort,pl.valueads,pl1.value as typevalue ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='ProcessType' and pl1.key=pl.type ");
        _sql.append("where pl.status=1 and pl.name=:name and pl.key=:key ");
        Map _params = new HashMap();
        _params.put("name", name);
        _params.put("key", Integer.valueOf(key));
        if(type > 0)
        {
            _sql.append("and (pl.type=:type or pl.type=0) ");
            _params.put("type", Integer.valueOf(type));
        } else
        if(type == -1)
            _sql.append("and pl.type>0 ");
        if(type > 0)
            _sql.append("order by pl.type,pl.sort ");
        else
            _sql.append("order by pl.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public IMetaDBQuery searchParaListByName(Map map)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pl.objid,pl.key,pl.value,pl.type,pl.sort,pl.valueads,pl1.value as typevalue \n");
        _sql.append("from WF_PROCESSLIST pl \n");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='ProcessType' and pl1.key=pl.type \n");
        _sql.append("where pl.status=1 and pl.type>0 \n");
        String name = MapUtils.getString(map, "name", "");
        if(StringUtils.isNotBlank(name))
        {
            _sql.append("   and pl.name=:name \n");
            _params.put("name", name);
        }
        int _searchtype = MapUtils.getIntValue(map, "searchtype", -1);
        if(_searchtype != -1)
        {
            _sql.append("   and pl.type=:searchtype \n");
            _params.put("searchtype", Integer.valueOf(_searchtype));
        }
        String _searchname = MapUtils.getString(map, "searchname", "");
        if(StringUtils.isNotBlank(_searchname))
        {
            _sql.append("   and ( upper(pl.value) like :searchname \n");
            _sql.append("       or pl.objid in (select b.objid from WF_PROCESSLIST b where b.name=:name \n");
            _sql.append("        and F_TRANS_PINYIN_CAPITAL(b.value) like :searchname) \n");
            _sql.append("       ) \n");
            _params.put("searchname", (new StringBuilder()).append("%").append(_searchname.toUpperCase()).append("%").toString());
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        _dbQuery.setDefaultOrderBy(" pl.sort asc, NLSSORT(pl.value,'NLS_SORT = SCHINESE_PINYIN_M')");
        return _dbQuery;
    }

    public int getParaListMaxSortByName(String name)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select max(pl.sort) maxsort \n");
        _sql.append("from WF_PROCESSLIST pl \n");
        _sql.append("left join WF_PROCESSLIST pl1 on pl1.name='ProcessType' and pl1.key=pl.type \n");
        _sql.append("where pl.status=1 and pl.type>0 and pl.name=:name \n");
        Map _params = new HashMap();
        _params.put("name", name);
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List list = _dbQuery.getResult();
        if(list.size() > 0)
        {
            Map _map = (Map)list.get(0);
            return MapUtils.getIntValue(_map, "maxsort", 0);
        } else
        {
            return 0;
        }
    }

    public List findParaNormal()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.name,pl.value,pl.status,pl.sort,pl.valueads ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.status<100 and pl.type=0 ");
        _sql.append("order by pl.name,pl.key ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        return _dbQuery.getResult();
    }

    public Map findParaMapByName(String name)
    {
        List _list = findParaListByName(name);
        Map _paramap = new HashMap();
        String _key;
        String _value;
        for(Iterator i$ = _list.iterator(); i$.hasNext(); _paramap.put(_key, _value))
        {
            Map _map = (Map)i$.next();
            _key = StringUtil.safeToString(_map.get("key"), "");
            _value = StringUtil.safeToString(_map.get("value"), "");
        }

        return _paramap;
    }

    public int getMaxProcesslistId()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select max(pl.objid) as maxid ");
        _sql.append("from WF_PROCESSLIST pl ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() == 0)
        {
            return 0;
        } else
        {
            Map _map = (Map)_list.get(0);
            int _maxid = NumberTool.convertMapKeyToInt(_map, "maxid", Integer.valueOf(0)).intValue();
            return _maxid;
        }
    }

    public Map findAllParaMap()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.key,pl.value,pl.name,pl.type ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.status=1 ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        Map _resultmap = new HashMap();
        for(Iterator i$ = _list.iterator(); i$.hasNext();)
        {
            Map _map = (Map)i$.next();
            String _name = StringUtil.safeToString(_map.get("name"), "");
            String _key = StringUtil.safeToString(_map.get("key"), "");
            String _value = StringUtil.safeToString(_map.get("value"), "");
            String _type = StringUtil.safeToString(_map.get("type"), "");
            if(_type.endsWith("0"))
                _resultmap.put((new StringBuilder()).append(_name).append(_key).toString(), _value);
            else
                _resultmap.put((new StringBuilder()).append(_type).append(_name).append(_key).toString(), _value);
        }

        return _resultmap;
    }

    public Map getSingleParaMap()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.name,pl.type ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.status=1 ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        Map _resultmap = new HashMap();
        for(Iterator i$ = _list.iterator(); i$.hasNext();)
        {
            Map _map = (Map)i$.next();
            String _name = StringUtil.safeToString(_map.get("name"), "");
            String _key = StringUtil.safeToString(_map.get("key"), "");
            String _objid = StringUtil.safeToString(_map.get("objid"), "");
            String _type = StringUtil.safeToString(_map.get("type"), "");
            if(_type.endsWith("0"))
                _resultmap.put((new StringBuilder()).append(_name).append(_key).toString(), _objid);
            else
                _resultmap.put((new StringBuilder()).append(_type).append(_name).append(_key).toString(), _objid);
        }

        return _resultmap;
    }

    public Map findParaByEndNameAndKey(String name, int key)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.value,pl.name,pl.valueads ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.name like '%").append(name).append("' and pl.key=").append(key).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map findParaByNameAndKey(String name, int key)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.key,pl.value,pl.name,pl.valueads ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.name='").append(name).append("' and pl.key=").append(key).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map findAvailableParaByEndNameAndKey(String name, int key)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select pl.objid,pl.key,pl.value,pl.name,pl.valueads \n");
        _sql.append("from WF_PROCESSLIST pl \n");
        _sql.append("where pl.status!=100 \n");
        _sql.append("and pl.name like '%").append(name).append("' and pl.key=").append(key).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public List getUrlExistList(String name, String url1, String url2, int type)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pu.objid as urlmark \n");
        _sql.append("  from WF_PROCESSURL pu \n");
        _sql.append("where pu.status=1 \n");
        if(StringUtils.isNotBlank(name))
        {
            _sql.append("   and pu.name=:name \n");
            _params.put("name", name);
        }
        if(type > 0)
        {
            _sql.append("   and pu.type=:type \n");
            _params.put("type", Integer.valueOf(type));
        }
        if(url1 != null)
            if(StringUtils.isBlank(url1))
            {
                _sql.append("and (pu.actionurl is null or pu.actionurl = '') \n");
            } else
            {
                _sql.append("and pu.actionurl=:actionurl \n");
                _params.put("actionurl", url1);
            }
        if(url2 != null)
            if(StringUtils.isBlank(url2))
            {
                _sql.append("and (pu.showurl is null or pu.showurl = '') \n");
            } else
            {
                _sql.append("and pu.showurl=:showurl \n");
                _params.put("showurl", url2);
            }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public boolean testUrlNameUsed(int urlmark, String name)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pu.objid as urlmark ");
        _sql.append("from WF_PROCESSURL pu ");
        _sql.append("where pu.name=:name and pu.status=1 and pu.objid!=:urlmark ");
        _params.put("name", name);
        _params.put("urlmark", Integer.valueOf(urlmark));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list.size() <= 0;
    }

    public List getListExistList(String name, String value, String valueads, int type)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pl.key as keymark, pl.objid \n");
        _sql.append("  from WF_PROCESSLIST pl \n");
        _sql.append("where pl.status=1 \n");
        if(StringUtils.isNotBlank(name))
        {
            _sql.append("   and pl.name=:name \n");
            _params.put("name", name);
        }
        if(type > 0)
        {
            _sql.append("   and pl.type=:type \n");
            _params.put("type", Integer.valueOf(type));
        }
        if(StringUtils.isNotBlank(value))
        {
            _sql.append("   and pl.value=:value \n");
            _params.put("value", value);
        }
        if(valueads != null)
            if(StringUtils.isBlank(valueads))
            {
                _sql.append("and (pl.valueads is null or pl.valueads = '') \n");
            } else
            {
                _sql.append("and pl.valueads=:valueads \n");
                _params.put("valueads", valueads);
            }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public boolean testListNameUsed(int listid, String name, String value)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select pl.objid as listid ");
        _sql.append("from WF_PROCESSLIST pl ");
        _sql.append("where pl.name=:name and pl.status=1 and pl.objid!=:listid and pl.value=:value ");
        _params.put("name", name);
        _params.put("value", value);
        _params.put("listid", Integer.valueOf(listid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list.size() <= 0;
    }

    public List getStepNoCode()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid from WF_PROCESSSTEP ps ");
        _sql.append("where ps.stepcode is null ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        try
        {
            List _list = _dbQuery.getResult();
            return _list;
        }
        catch(Exception e)
        {
            LOGGER.error("\u67E5\u8BE2\u6CA1\u6709stepcode\u7684\u6B65\u9AA4\u5931\u8D25", e);
        }
        return new ArrayList();
    }

    public List getStepNextStepList(int processid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.name as stepname,ps.objid as stepid,ps.steptypeid,psl.prestepid,psl.aftstepid,psl.sort \r\n");
        _sql.append("from WF_ProcessStepLink psl \r\n");
        _sql.append("left join WF_ProcessStep ps on psl.prestepid=ps.objid \r\n");
        _sql.append("where psl.processid=").append(processid).append(" \r\n");
        _sql.append("and psl.status=1 and ps.status=1 \r\n");
        _sql.append("order by psl.prestepid,psl.sort \r\n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public Map getStepLinkStepMap(int processid)
    {
        Map resultmap = new HashMap();
        List list = getStepNextStepList(processid);
        for(int i = 0; i < list.size(); i++)
        {
            Map map = (Map)list.get(i);
            int stepid = NumberTool.safeToInteger(map.get("stepid"), Integer.valueOf(0)).intValue();
            int sort = NumberTool.safeToInteger(map.get("sort"), Integer.valueOf(0)).intValue();
            int steptypeid = NumberTool.safeToInteger(map.get("steptypeid"), Integer.valueOf(0)).intValue();
            if(steptypeid == WorkflowStepType.WF_TYPE_START.getEnumItemValue())
                resultmap.put("startstep", Integer.valueOf(stepid));
            resultmap.put((new StringBuilder()).append("").append(stepid).toString(), Integer.valueOf(sort));
            resultmap.put((new StringBuilder()).append("").append(stepid).append("_").append(sort).toString(), map);
        }

        resultmap.put("stepnum", Integer.valueOf(list.size()));
        return resultmap;
    }

    public List judgeAgentsList(Map map)
    {
        int type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        int orgid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        int objid = NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(0)).intValue();
        int processtype = NumberTool.safeToInteger(map.get("processtype"), Integer.valueOf(-1)).intValue();
        int processcode = NumberTool.safeToInteger(map.get("processcode"), Integer.valueOf(-1)).intValue();
        int stepcode = NumberTool.safeToInteger(map.get("stepcode"), Integer.valueOf(-1)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select a.ownerid, a.agentsid, a.endtime,a.starttime,a.status  \r\n");
        _sql.append("from WF_AGENTS a \r\n");
        _sql.append("where 1=1 ");
        if(type == 0)
            _sql.append("and a.ownerid=:orgid \r\n");
        else
        if(type == 1)
            _sql.append("and a.agentsid=:orgid \r\n");
        _params.put("orgid", Integer.valueOf(orgid));
        if(objid != 0)
        {
            _sql.append("and a.objid!=:objid \r\n");
            _params.put("objid", Integer.valueOf(objid));
        }
        if(processtype >= 0)
        {
            _sql.append("and a.processtype=:processtype \r\n");
            _params.put("processtype", Integer.valueOf(processtype));
        }
        if(processcode >= 0)
        {
            _sql.append("and a.processid=:processcode \r\n");
            _params.put("processcode", Integer.valueOf(processcode));
        }
        if(stepcode >= 0)
        {
            _sql.append("and a.processstepid=:stepcode \r\n");
            _params.put("stepcode", Integer.valueOf(stepcode));
        }
        _sql.append("and a.status in (1,2,49,50) \r\n");
        _sql.append("order by a.starttime ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getProcessStepDoList(int processstepid, int type)
    {
        StringBuilder _sql = new StringBuilder();
        Map _param = new HashMap();
        _sql.append("Select objid, processid, processstepid, type, taskid \n");
        _sql.append(" From WF_ProcessStepDo \n");
        _sql.append("Where 1=1 and status=1 \n");
        if(processstepid > 0)
        {
            _sql.append("   and processstepid=:stepid \n");
            _param.put("stepid", Integer.valueOf(processstepid));
        } else
        {
            _sql.append("   and 1=2");
        }
        if(type > 0)
        {
            _sql.append("   and type=:type \n");
            _param.put("type", Integer.valueOf(type));
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowBaseServiceImpl);

}
