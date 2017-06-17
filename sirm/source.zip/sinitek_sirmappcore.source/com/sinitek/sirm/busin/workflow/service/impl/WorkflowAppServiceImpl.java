// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowAppServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.service.claim.ExampleTaskQuery;
import com.sinitek.sirm.busin.workflow.service.claim.IWorkflowClaimDaoService;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class WorkflowAppServiceImpl extends MetaDBContextSupport
    implements IWorkflowAppService
{

    public WorkflowAppServiceImpl()
    {
    }

    public int saveExample(IWorkflowExample example)
    {
        super.save(example);
        return example.getId();
    }

    public int saveExampleStep(IWorkflowExampleStep step)
    {
        super.save(step);
        return step.getId();
    }

    public int saveExampleEntry(IWorkflowExampleEntry entry)
    {
        super.save(entry);
        return entry.getId();
    }

    public int saveExampleStepOwner(IWorkflowExampleStepOwner owner)
    {
        String opinion = StringUtil.safeToString(owner.getApproveOpinion(), "");
        if(opinion.length() > 1333)
        {
            LOGGER.info("\u610F\u89C1\u8FC7\u957F,\u622A\u53D6\u524D\u9762\u76841333\u4E2A\u5B57\u7B26");
            owner.setApproveOpinion(opinion.substring(0, 1333));
        }
        boolean mark = false;
        if(owner.getId() == 0)
            mark = true;
        super.save(owner);
        IWorkflowClaimDaoService daoService = WorkflowServiceFactory.getWorkflowClaimDaoService();
        if(mark)
        {
            IWorkflowExampleStep exampleStep = loadExampleStep(owner.getExampleStepId().intValue());
            if(exampleStep.getStepType().intValue() != WorkflowStepType.WF_TYPE_START.getEnumItemValue())
            {
                IWorkflowExample example = loadExample(owner.getExampleId().intValue());
                IWorkflowExampleTask task = daoService.loadExampleTask(0);
                task.setSourceEntity("Wf_ExampleStepOwner");
                task.setSourceId(Integer.valueOf(owner.getId()));
                task.setDealerId(owner.getOwnerId());
                task.setOrginerId(example.getStarterId());
                task.setStatus(Integer.valueOf(0));
                task.setStartTime(new Date());
                task.setDescription(example.getBrief());
                task.setRemarks((new StringBuilder()).append(example.getProcessName()).append(" ").append(exampleStep.getProcessStepName()).toString());
                task.setSort(Integer.valueOf(0));
                daoService.saveExampleTask(task);
            }
        } else
        {
            ExampleTaskQuery taskQuery = new ExampleTaskQuery();
            taskQuery.setSourceEntity(ExampleTaskQuery.EXAMPLE_OWNER);
            taskQuery.setSourceId(owner.getId());
            List taskList = daoService.findExampleTask(taskQuery);
            if(taskList.size() > 0)
            {
                IWorkflowExampleTask task = (IWorkflowExampleTask)taskList.get(0);
                if(owner.getStatus().intValue() >= 2)
                {
                    task.setStatus(Integer.valueOf(8));
                    task.setEndTime(new Date());
                } else
                {
                    task.setStatus(owner.getStatus());
                }
                daoService.saveExampleTask(task);
            }
        }
        IWorkflowSpecialService _worksp = WorkflowServiceFactory.getWorkflowSpecialService();
        _worksp.doSpecialOwner(owner.getId());
        return owner.getId();
    }

    public int saveExampleStepLink(IWorkflowExampleStepLink link)
    {
        super.save(link);
        return link.getId();
    }

    public int saveExamplePara(IWorkflowExamplePara para)
    {
        super.save(para);
        return para.getId();
    }

    public int saveExampleRelation(IWorkflowExampleRelation reletion)
    {
        LOGGER.info(reletion.toString());
        super.save(reletion);
        return reletion.getId();
    }

    public IWorkflowExampleRelation loadExampleRelation(int reletionid)
    {
        if(reletionid <= 0)
            return new WorkflowExampleRelationImpl();
        else
            return (IWorkflowExampleRelation)getMetaDBContext().get("WFEXAMPLERELATION", reletionid);
    }

    public IWorkflowExample loadExample(int exampleid)
    {
        if(exampleid <= 0)
            return new WorkflowExampleImpl();
        else
            return (IWorkflowExample)getMetaDBContext().get("WFEXAMPLE", exampleid);
    }

    public IWorkflowExampleStep loadExampleStep(int stepid)
    {
        if(stepid <= 0)
            return new WorkflowExampleStepImpl();
        else
            return (IWorkflowExampleStep)getMetaDBContext().get("WFEXAMPLESTEP", stepid);
    }

    public IWorkflowExampleEntry loadExampleEntry(int entryid)
    {
        if(entryid <= 0)
            return new WorkflowExampleEntryImpl();
        else
            return (IWorkflowExampleEntry)getMetaDBContext().get("WFEXAMPLEENTRY", entryid);
    }

    public IWorkflowExampleStepOwner loadExampleStepOwner(int ownerid)
    {
        if(ownerid <= 0)
            return new WorkflowExampleStepOwnerImpl();
        else
            return (IWorkflowExampleStepOwner)getMetaDBContext().get("WFEXAMPLESTEPOWNER", ownerid);
    }

    public IWorkflowExampleStepLink loadExampleStepLink(int linkid)
    {
        if(linkid <= 0)
            return new WorkflowExampleStepLinkImpl();
        else
            return (IWorkflowExampleStepLink)getMetaDBContext().get("WFEXAMPLESTEPLINK", linkid);
    }

    public IWorkflowExamplePara loadExamplePara(int paraid)
    {
        if(paraid <= 0)
            return new WorkflowExampleParaImpl();
        else
            return (IWorkflowExamplePara)getMetaDBContext().get("WFEXAMPLEPARA", paraid);
    }

    public List getNowExampleList()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, ");
        _sql.append("e.starterid,e.starttime,e.endtime, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime as stepstarttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid,eso.status as ownerstatus,el3.value as ownerstatusvalue, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key ");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid and (es.status=0 or es.status=1) ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='OwnerStatus' and eso.status=el3.key ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        _sql.append("where (e.status=0 or e.status=1) ");
        _sql.append("order by e.objid desc , es.objid , eso.objid ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getNowExampleList(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(0)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, \n");
        _sql.append("e.starterid,e.starttime,e.endtime, \n");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime as stepstarttime,pl2.value as steptypevalue, \n");
        _sql.append("es.actionurl,es.showurl, \n");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid,eso.status as ownerstatus,el3.value as ownerstatusvalue, \n");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername \n");
        _sql.append("from WF_EXAMPLE e \n");
        _sql.append("inner join WF_EXAMPLESTEP es on e.objid=es.exampleid and (es.status=0 or es.status=1) \n");
        _sql.append("left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key \n");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key \n");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid \n");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='OwnerStatus' and eso.status=el3.key \n");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) \n");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) \n");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) \n");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.syscode=:syscode \n");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.processtype=:processtype \n");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        _sql.append("where (e.status=0 or e.status=1) \n");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid \n");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and es.starttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and es.starttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("getNowExampleList\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        _sql.append(" order by es.objid desc, eso.objid \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getMyInitiateProcessList(Map map)
    {
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        Map _typeadsmap = MapUtils.getMap(map, "typeads");
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(0)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _params.put("starterid", Integer.valueOf(_starterid));
        _sql.append("select a.*, el1.value as processstatusvalue,  \n");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime as stepstarttime,pl2.value as steptypevalue, \n");
        _sql.append("es.actionurl,es.showurl, \n");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid,eso.status as ownerstatus,el3.value as ownerstatusvalue, \n");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername \n");
        _sql.append("from ( \n");
        _sql.append("      select e.objid as exampleid,e.processname,e.brief as processbrief,e.status,  \n");
        _sql.append("      e.starterid,e.starttime,e.endtime \n");
        _sql.append("      from WF_EXAMPLE e  \n");
        _sql.append("      inner join WF_PROCESS p on p.objid=e.processid  \n");
        _sql.append("      where e.starterid=:starterid  \n");
        _sql.append((new StringBuilder()).append("       and p.processtype in (").append(MapUtils.getString(_typeadsmap, "0", "''")).append(") and (e.status=0 or e.status=1) \n").toString());
        if(_brief.length() > 0)
            _sql.append("       and e.brief like :brief ");
        _sql.append("  union \n");
        _sql.append("      select e.objid as exampleid,e.processname,e.brief as processbrief,e.status,  \n");
        _sql.append("      e.starterid,e.starttime,e.endtime \n");
        _sql.append("      from WF_EXAMPLE e  \n");
        _sql.append("      inner join WF_PROCESS p on p.objid=e.processid  \n");
        _sql.append("      where e.starterid=:starterid  \n");
        _sql.append((new StringBuilder()).append("       and p.processtype in (").append(MapUtils.getString(_typeadsmap, "1", "''")).append(") and e.status=0 \n").toString());
        if(_brief.length() > 0)
            _sql.append("       and e.brief like :brief ");
        _sql.append("  union \n");
        _sql.append("      select e.objid as exampleid,e.processname,e.brief as processbrief,e.status,  \n");
        _sql.append("      e.starterid,e.starttime,e.endtime \n");
        _sql.append("      from WF_EXAMPLE e  \n");
        _sql.append("      inner join WF_PROCESS p on p.objid=e.processid  \n");
        _sql.append("      where e.starterid=:starterid  \n");
        _sql.append((new StringBuilder()).append("       and p.processtype in (").append(MapUtils.getString(_typeadsmap, "2", "''")).append(") and e.status=1 \n").toString());
        if(_brief.length() > 0)
            _sql.append("       and e.brief like :brief ");
        _sql.append(") a \n");
        _sql.append("inner join (select * from WF_EXAMPLESTEP where (status=0 or status=1)) es on a.exampleid=es.exampleid \n");
        _sql.append("left join (select * from WF_EXAMPLELIST where name='ExampleStatus')el1 on a.status=el1.key  \n");
        _sql.append("left join (select * from WF_PROCESSLIST where name='StepType') pl2 on es.steptype=pl2.key  \n");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid  \n");
        _sql.append("left join (select * from WF_EXAMPLELIST where name='OwnerStatus') el3 on eso.status=el3.key \n");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(a.starterid)  \n");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) \n");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) \n");
        _sql.append("where 1=1  and es.steptype!=7 \n");
        if(_brief.length() > 0)
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and es.starttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and es.starttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.warn("getNowExampleList\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        _sql.append(" order by es.objid desc, eso.objid \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getMyRecoverProcessList(Map map)
    {
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _params.put("starterid", Integer.valueOf(_starterid));
        _sql.append("select a.*, el1.value as processstatusvalue,  \n");
        _sql.append("    pl2.value as steptypevalue, \n");
        _sql.append("    eso.objid as exampleownerid,eso.ownerid,eso.preownerid,eso.status as ownerstatus,el3.value as ownerstatusvalue, \n");
        _sql.append("    so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername \n");
        _sql.append("  from ( \n");
        _sql.append("      select e.processname,e.brief as processbrief,e.status,  \n");
        _sql.append("           e.starterid,e.starttime,e.endtime,es.* \n");
        _sql.append("        from WF_EXAMPLE e  \n");
        _sql.append("           inner join WF_PROCESS p on p.objid=e.processid  \n");
        _sql.append("           inner join (  \n");
        _sql.append("                select objid examplestepid,processstepname,starttime stepstarttime  \n");
        _sql.append("                    ,actionurl,showurl,steptype,exampleid  \n");
        _sql.append("                  from WF_EXAMPLESTEP where (status=4)  \n");
        _sql.append("              ) es on e.objid=es.exampleid  \n");
        _sql.append("         where e.starterid=:starterid and e.status=4 \n");
        if(_brief.length() > 0)
            _sql.append("         and e.brief like :brief ");
        _sql.append("        union \n");
        _sql.append("      select e.processname,e.brief as processbrief,e.status,  \n");
        _sql.append("           e.starterid,e.starttime,e.endtime,es.* \n");
        _sql.append("         from WF_EXAMPLE e  \n");
        _sql.append("           inner join WF_PROCESS p on p.objid=e.processid  \n");
        _sql.append("           inner join (  \n");
        _sql.append("               select objid examplestepid,processstepname,starttime stepstarttime  \n");
        _sql.append("                   ,actionurl,showurl,steptype,exampleid  \n");
        _sql.append("                 from WF_EXAMPLESTEP where recoverflag=1 and (status=2) \n");
        _sql.append("            ) es on e.objid=es.exampleid  \n");
        _sql.append("        where e.starterid=:starterid and (e.status=1 or e.status=0) \n");
        if(_brief.length() > 0)
            _sql.append("        and e.brief like :brief ");
        _sql.append("   ) a \n");
        _sql.append("      left join (select * from WF_EXAMPLELIST where name='ExampleStatus')el1 on a.status=el1.key  \n");
        _sql.append("      left join (select * from WF_PROCESSLIST where name='StepType') pl2 on a.steptype=pl2.key  \n");
        _sql.append("      left join WF_EXAMPLESTEPOWNER eso on a.examplestepid=eso.examplestepid  \n");
        _sql.append("      left join (select * from WF_EXAMPLELIST where name='OwnerStatus') el3 on eso.status=el3.key \n");
        _sql.append("      left join sprt_orgobject so on so.orgid=to_char(a.starterid)  \n");
        _sql.append("      left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) \n");
        _sql.append("      left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) \n");
        _sql.append("where 1=1 \n");
        if(_brief.length() > 0)
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and stepstarttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and stepstarttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.warn("getNowExampleList\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        _sql.append(" order by a.examplestepid desc, exampleownerid , endtime desc \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getNowExampleMetadb(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(0)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, \n");
        _sql.append("e.starterid,e.starttime,e.endtime, \n");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime as stepstarttime,pl2.value as steptypevalue, \n");
        _sql.append("es.actionurl,es.showurl, \n");
        _sql.append("so.orgname as startername,eso2.ownernames,eso2.exampleownerids,eso2.preownernames,eso2.ownerstatuss \n");
        _sql.append("from WF_EXAMPLE e left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key \n");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid and (es.status=0 or es.status=1) \n");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key \n");
        _sql.append("left join (select eso.examplestepid,wm_concat(so1.orgname) as ownernames,wm_concat(eso.objid) as exampleownerids,");
        _sql.append("wm_concat(so2.orgname) as preownernames,wm_concat(eso.status) as ownerstatuss \n");
        _sql.append("from WF_EXAMPLESTEPOWNER eso \n");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) \n");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) \n");
        _sql.append("group by eso.examplestepid) eso2 on es.objid=eso2.examplestepid \n");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) \n");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.syscode=:syscode \n");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.processtype=:processtype \n");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        _sql.append("where (e.status=0 or e.status=1) \n");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid \n");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery;
    }

    public List getPastExampleList(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(-1)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(-1)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, ");
        _sql.append("e.starterid,e.starttime,e.endtime, ");
        _sql.append("so.orgname as startername ");
        _sql.append("from WF_EXAMPLE e left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.syscode=:syscode ");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.processtype=:processtype ");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        _sql.append("where (e.status!=0 and e.status!=1) ");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid ");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        _sql.append("order by e.endtime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getPastExampleMetadb(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(-1)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(-1)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, ");
        _sql.append("e.starterid,e.starttime,e.endtime, ");
        _sql.append("so.orgname as startername ");
        _sql.append("from WF_EXAMPLE e left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.syscode=:syscode ");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.processtype=:processtype ");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        _sql.append("where (e.status!=0 and e.status!=1) ");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid ");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        String _endtimestart = StringUtil.safeToString(map.get("endtimestart"), "");
        String _endtimeend = StringUtil.safeToString(map.get("endtimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and e.starttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and e.starttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
            if(_endtimestart.length() > 0)
            {
                Date endtimestart;
                if(_starttimestart.indexOf(':') > 0)
                    endtimestart = dateFormat.parse(_endtimestart);
                else
                    endtimestart = dateFormat.parse((new StringBuilder()).append(_endtimestart).append(" 00:00:00").toString());
                _sql.append("and e.endtime>=:endtimestart ");
                _params.put("endtimestart", endtimestart);
            }
            if(_endtimeend.length() > 0)
            {
                Date endtimeend;
                if(_endtimeend.indexOf(':') > 0)
                    endtimeend = dateFormat.parse(_endtimeend);
                else
                    endtimeend = dateFormat.parse((new StringBuilder()).append(_endtimeend).append(" 23:59:59").toString());
                _sql.append("and e.endtime<=:endtimeend ");
                _params.put("endtimeend", endtimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("getPastExampleMetadb\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        return _dbQuery;
    }

    public List getPastExampleList()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,el1.value as processstatusvalue, ");
        _sql.append("e.starterid,e.starttime,e.endtime, ");
        _sql.append("so.orgname as startername ");
        _sql.append("from WF_EXAMPLE e left join WF_EXAMPLELIST el1 on el1.name='ExampleStatus' and e.status=el1.key ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("where (e.status!=0 and e.status!=1) ");
        _sql.append("order by e.objid desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleTask(int orgid, int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,e.starterid, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl,el2.value as stepstatusvalue, ");
        _sql.append("el3.value as processstatusvalue, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e ");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid ");
        _sql.append("left join WF_EXAMPLELIST el2 on el2.name='StepStatus' and es.status=el2.key ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='ExampleStatus' and e.status=el3.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        _sql.append("where eso.ownerid=:orgid ");
        if(type == 0)
            _sql.append("and (eso.status=0 or eso.status=1) ");
        else
        if(type == 1)
            _sql.append("and (eso.status!=0 and eso.status!=1) ");
        _sql.append("order by eso.starttime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        Map _params = new HashMap();
        _params.put("orgid", Integer.valueOf(orgid));
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleTask(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(-1)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(-1)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        int _orgid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        int _steptype = NumberTool.convertMapKeyToInt(map, "steptype", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        Map _showmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowShow", 1);
        int _startshow = NumberTool.convertMapKeyToInt(_showmap, "valueads", Integer.valueOf(0)).intValue();
        Map _sortmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowSort", 1);
        int _tasksort = NumberTool.convertMapKeyToInt(_sortmap, "valueads", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,e.starterid, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl,el2.value as stepstatusvalue,eso.approvetime, ");
        _sql.append("el3.value as processstatusvalue, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e ");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid ");
        if(_startshow == 1)
            _sql.append("and es.steptype!=1 ");
        _sql.append("left join WF_EXAMPLELIST el2 on el2.name='StepStatus' and es.status=el2.key ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='ExampleStatus' and e.status=el3.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        _sql.append("inner join WF_PROCESS p on p.objid=e.processid ");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("and p.syscode=:syscode ");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("and p.processtype=:processtype ");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        if(_type == 2)
            _sql.append("inner join WF_PROCESSSTEP ps on ps.objid=es.processstepid ");
        _sql.append("where eso.ownerid=:orgid ");
        if(_type == 0 || _type == 2)
            _sql.append("and (eso.status=0 or eso.status=1) ");
        else
        if(_type == 1)
            _sql.append("and (eso.status!=0 and eso.status!=1) ");
        if(_type == 2)
            _sql.append("and ps.stepspecial like '_1%' ");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid ");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        if(_steptype > 0)
        {
            _sql.append("and es.steptype=:steptype ");
            _params.put("steptype", Integer.valueOf(_steptype));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        String _endtimestart = StringUtil.safeToString(map.get("endtimestart"), "");
        String _endtimeend = StringUtil.safeToString(map.get("endtimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and eso.starttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and eso.starttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
            if(_endtimestart.length() > 0)
            {
                Date endtimestart;
                if(_starttimestart.indexOf(':') > 0)
                    endtimestart = dateFormat.parse(_endtimestart);
                else
                    endtimestart = dateFormat.parse((new StringBuilder()).append(_endtimestart).append(" 00:00:00").toString());
                _sql.append("and eso.approvetime>=:endtimestart ");
                _params.put("endtimestart", endtimestart);
            }
            if(_endtimeend.length() > 0)
            {
                Date endtimeend;
                if(_endtimeend.indexOf(':') > 0)
                    endtimeend = dateFormat.parse(_endtimeend);
                else
                    endtimeend = dateFormat.parse((new StringBuilder()).append(_endtimeend).append(" 23:59:59").toString());
                _sql.append("and eso.approvetime<=:endtimeend ");
                _params.put("endtimeend", endtimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("getExampleTask\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        if(_type == 0)
        {
            if(_tasksort == 1)
                _sql.append("order by p.processtype,p.syscode,es.processstepid,eso.starttime desc ");
            else
                _sql.append("order by eso.starttime desc ");
        } else
        if(_type == 1)
            _sql.append("order by eso.approvetime desc ");
        else
        if(_type == 2)
            _sql.append("order by eso.starttime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _params.put("orgid", Integer.valueOf(_orgid));
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getExampleTaskMetadb(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(-1)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(-1)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        int _orgid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        Map _showmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowShow", 1);
        int _startshow = NumberTool.convertMapKeyToInt(_showmap, "valueads", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,e.starterid, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl,el2.value as stepstatusvalue,eso.approvetime, ");
        _sql.append("el3.value as processstatusvalue, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e ");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid ");
        if(_startshow == 1)
            _sql.append("and es.steptype!=1 ");
        _sql.append("left join WF_EXAMPLELIST el2 on el2.name='StepStatus' and es.status=el2.key ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='ExampleStatus' and e.status=el3.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        if(_processtype != -1)
        {
            if(_processid != -1)
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.syscode=:syscode ");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("inner join WF_PROCESS p on p.objid=e.processid and p.processtype=:processtype ");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        } else
        {
            _sql.append("inner join WF_PROCESS p on p.objid=e.processid ");
        }
        _sql.append("where eso.ownerid=:orgid ");
        if(_type == 0)
            _sql.append("and (eso.status=0 or eso.status=1) ");
        else
        if(_type == 1)
            _sql.append("and (eso.status!=0 and eso.status!=1) ");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid ");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _params.put("orgid", Integer.valueOf(_orgid));
        _dbQuery.setParameters(_params);
        return _dbQuery;
    }

    public List getUndoExampleTaskForMobile(Map map)
    {
        StringBuilder sql = new StringBuilder();
        Map param = new HashMap();
        int orgid = MapUtils.getIntValue(map, "orgid", 0);
        int rowstart = MapUtils.getIntValue(map, "rowstart", 0);
        int rowend = MapUtils.getIntValue(map, "rowend", 0);
        sql.append("select title, exampleownerid, entityid, entity, entitytime, steptype \n");
        sql.append(" from ( \n");
        sql.append("   select rownum rnum,v.* from( \n");
        sql.append("    select e.brief as title,to_char(es.starttime,'yyyy-mm-dd hh24:mi:ss') entitytime, \n");
        sql.append("      eso.objid as exampleownerid, ps.steptypeid steptype, \n");
        sql.append("      ee.sourceid entityid, ee.sourcename entity \n");
        sql.append("     from WF_EXAMPLE e  \n");
        sql.append("         left join WF_EXAMPLEENTRY ee on e.objid=ee.exampleid \n");
        sql.append("         left join WF_EXAMPLESTEP es on e.objid=es.exampleid  \n");
        sql.append("         left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid  \n");
        sql.append("         inner join WF_PROCESS p on p.objid=e.processid  \n");
        sql.append("         inner join WF_PROCESSSTEP ps on ps.objid=es.processstepid  \n");
        sql.append("    where (eso.status=0 or eso.status=1) and (ps.steptypeid!=1 and ps.steptypeid!=2) \n");
        sql.append("          and (p.phoneshow=1 or ps.phoneshow=1) \n");
        if(orgid > 0)
        {
            sql.append("      and eso.ownerid=:orgid \n");
            param.put("orgid", Integer.valueOf(orgid));
        } else
        {
            sql.append("      and 1=2 \n");
        }
        sql.append("    order by eso.starttime desc  \n");
        sql.append("  ) v ");
        if(rowend > 0)
        {
            sql.append("    where rownum <= :rowend ");
            param.put("rowend", Integer.valueOf(rowend));
        }
        sql.append(") where 1=1 ");
        if(rowstart > 0)
        {
            sql.append("and rnum >= :rowstart ");
            param.put("rowstart", Integer.valueOf(rowstart));
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        return query.getResult();
    }

    public int getUndoExampleTaskNumForMobile(Map map)
    {
        StringBuilder sql = new StringBuilder();
        Map param = new HashMap();
        int orgid = MapUtils.getIntValue(map, "orgid", 0);
        sql.append("    select count(e.objid) num \n");
        sql.append("     from WF_EXAMPLE e  \n");
        sql.append("         left join WF_EXAMPLESTEP es on e.objid=es.exampleid  \n");
        sql.append("         left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid  \n");
        sql.append("         inner join WF_PROCESS p on p.objid=e.processid  \n");
        sql.append("         inner join WF_PROCESSSTEP ps on ps.objid=es.processstepid  \n");
        sql.append("    where (eso.status=0 or eso.status=1) \n");
        sql.append("          and (ps.steptypeid!=1 and ps.steptypeid!=2) \n");
        sql.append("          and (p.phoneshow=1 or ps.phoneshow=1) \n");
        if(orgid > 0)
        {
            sql.append("      and eso.ownerid=:orgid \n");
            param.put("orgid", Integer.valueOf(orgid));
        } else
        {
            sql.append("      and 1=2 \n");
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        List list = query.getResult();
        Map _res = (Map)list.get(0);
        return MapUtils.getInteger(_res, "num", Integer.valueOf(0)).intValue();
    }

    public List getDoneExampleTaskForMobile(Map map)
    {
        StringBuilder sql = new StringBuilder();
        Map param = new HashMap();
        int orgid = MapUtils.getIntValue(map, "orgid", 0);
        int rowstart = MapUtils.getIntValue(map, "rowstart", 0);
        int rowend = MapUtils.getIntValue(map, "rowend", 0);
        int _processtype = MapUtils.getIntValue(map, "processtype", 0);
        String _syscode = MapUtils.getString(map, "syscode", "");
        int _starterid = MapUtils.getIntValue(map, "starterid", 0);
        String _approvestarttime = MapUtils.getString(map, "approvestarttime", "");
        String _approveendtime = MapUtils.getString(map, "approveendtime", "");
        String _keyword = MapUtils.getString(map, "keyword", "");
        sql.append("select title, starterid, stepname, entityid, entity, entitytime, steptype \n");
        sql.append(" from ( \n");
        sql.append("     select rownum rnum,v.* from( \n");
        sql.append("        select rank() over(partition by eso.ownerid, e.objid order by eso.approvetime desc) rk \n");
        sql.append("            ,e.brief title ,e.starterid ,es.processstepname stepname \n");
        sql.append("            ,to_char(eso.approvetime,'yyyy-mm-dd hh24:mi:ss') entitytime  \n");
        sql.append("            ,ps.steptypeid steptype,ee.sourceid entityid, ee.sourcename entity \n");
        sql.append("        from WF_EXAMPLE e \n");
        sql.append("            left join WF_EXAMPLEENTRY ee on e.objid=ee.exampleid \n");
        sql.append("            left join WF_EXAMPLESTEP es on e.objid=es.exampleid  \n");
        sql.append("            left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid  \n");
        sql.append("            inner join WF_PROCESS p on p.objid=e.processid  \n");
        sql.append("            inner join WF_PROCESSSTEP ps on ps.objid=es.processstepid  \n");
        sql.append("        where (eso.status!=0 and eso.status!=1) and (ps.steptypeid!=1 and ps.steptypeid!=2) \n");
        sql.append("            and (p.phoneshow=1 or ps.phoneshow=1) \n");
        if(orgid > 0)
        {
            sql.append("        and eso.ownerid=:orgid \n");
            param.put("orgid", Integer.valueOf(orgid));
        } else
        {
            sql.append("        and 1=2 \n");
        }
        if(_processtype > 0)
        {
            sql.append("        and p.processtype=:processtype \n");
            param.put("processtype", Integer.valueOf(_processtype));
        }
        if(StringUtils.isNotBlank(_syscode))
        {
            sql.append("        and p.syscode=:syscode \n");
            param.put("syscode", _syscode);
        }
        if(_starterid > 0)
        {
            sql.append("        and e.starterid=:starterid \n");
            param.put("starterid", Integer.valueOf(_starterid));
        }
        if(StringUtils.isNotBlank(_approvestarttime))
        {
            sql.append("        and eso.approvetime > :approvestarttime \n");
            param.put("approvestarttime", TimeUtil.formatDate(_approvestarttime, "yyyy-MM-dd"));
        }
        if(StringUtils.isNotBlank(_approveendtime))
        {
            sql.append("        and eso.approvetime < :approveendtime \n");
            param.put("approveendtime", TimeUtil.getPreOrNextDay(TimeUtil.formatDate(_approveendtime, "yyyy-MM-dd"), 1));
        }
        if(StringUtils.isNotBlank(_keyword))
        {
            sql.append("        and e.brief like :keyword \n");
            param.put("keyword", (new StringBuilder()).append("%").append(_keyword).append("%").toString());
        }
        sql.append("      order by eso.approvetime desc  \n");
        sql.append("  ) v where v.rk=1 \n");
        if(rowend > 0)
        {
            sql.append("    and rownum <= :rowend ");
            param.put("rowend", Integer.valueOf(rowend));
        }
        sql.append(") where 1=1 ");
        if(rowstart > 0)
        {
            sql.append("and rnum >= :rowstart ");
            param.put("rowstart", Integer.valueOf(rowstart));
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        return query.getResult();
    }

    public List getTaskByMap(Map map)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(0)).intValue();
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        String _orgid = StringUtil.safeToString(map.get("orgid"), "");
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,e.starterid, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl,el2.value as stepstatusvalue, ");
        _sql.append("el3.value as processstatusvalue, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e ");
        if(_processtype > 0)
        {
            _sql.append("inner join WF_PROCESS p on e.processid=p.objid and p.processtype=:processtype ");
            _params.put("processtype", Integer.valueOf(_processtype));
        }
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid ");
        _sql.append("left join WF_EXAMPLELIST el2 on el2.name='StepStatus' and es.status=el2.key ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='ExampleStatus' and e.status=el3.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        _sql.append("where 1=1 ");
        if(_orgid.length() > 0)
            _sql.append("and eso.ownerid in (").append(_orgid).append(") ");
        if(_type == 0)
            _sql.append("and (eso.status=0 or eso.status=1) ");
        else
        if(_type == 1)
            _sql.append("and (eso.status!=0 and eso.status!=1) ");
        _sql.append("order by eso.starttime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleStepListByExampleId(int exampleid)
    {
        Map _showmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowShow", 1);
        int _startshow = NumberTool.convertMapKeyToInt(_showmap, "valueads", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select es.objid as examplestepid,es.processstepname,es.starttime,es.brief as stepbrief, ");
        _sql.append("es.status as stepstatus,el1.value as stepstatusvalue,pl2.value as steptypevalue, ");
        _sql.append("es.showurl,es.actionurl,el3.value as ownerstatusvalue,el4.value as stepconditonvalue, ");
        _sql.append("es.exampleid,eso.objid as exampleownerid,es.steptype, ");
        _sql.append("eso.ownerid,eso.preownerid,eso.approveopinion,eso.approvetime, ");
        _sql.append("eso.status as ownerstatus,eso.approvestatus,el5.value as ownercondition ");
        _sql.append(",sprt1.orgname as ownername,sprt2.orgname as preownername \n");
        _sql.append("from WF_EXAMPLESTEP es ");
        _sql.append("left join WF_EXAMPLELIST el1 on el1.name='StepStatus' and es.status=el1.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLELIST el4 on el4.name='StepCondition' and es.stepcondition=el4.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='OwnerStatus' and eso.status=el3.key ");
        _sql.append("left join WF_EXAMPLELIST el5 on el5.name='StepCondition' and eso.approvestatus=el5.key ");
        _sql.append("left join SPRT_ORGOBJECT sprt1 on sprt1.orgid=eso.ownerid \n");
        _sql.append("left join SPRT_ORGOBJECT sprt2 on sprt2.orgid=eso.preownerid \n");
        _sql.append("where es.exampleid=").append(exampleid).append(" ");
        if(_startshow == 1)
            _sql.append("and es.steptype!=1 ");
        else
        if(_startshow == 2)
            _sql.append("and (es.steptype!=1 and es.steptype!=2 or es.steptype is null) ");
        _sql.append("order by es.objid , eso.objid ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleStepList(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select es.objid as examplestepid,es.processstepname,es.status as stepstatus,es.processstepid ");
        _sql.append("  ,es.stepcondition, es.steptype ");
        _sql.append("from WF_EXAMPLESTEP es ");
        _sql.append("where es.exampleid=").append(exampleid).append(" ");
        _sql.append("order by es.objid ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleOwnerList(int examplestepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select eso.objid as exampleownerid,eso.ownerid,eso.status as ownerstatus,eso.preownerid ,eso.approvestatus,eso.value ");
        _sql.append("from WF_EXAMPLESTEPOWNER eso ");
        _sql.append("where eso.examplestepid=").append(examplestepid).append(" ");
        _sql.append("order by eso.objid ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleOwnerList(int exampleid, int sort)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select eso.objid as exampleownerid,eso.ownerid,eso.status as ownerstatus,eso.preownerid,eso.approvestatus,eso.value ");
        _sql.append(",es.processstepid,eso.examplestepid ");
        _sql.append("from WF_EXAMPLESTEP es ");
        _sql.append("inner join WF_EXAMPLESTEPOWNER eso on eso.examplestepid=es.objid ");
        _sql.append("where eso.exampleid=").append(exampleid).append(" ");
        if(sort == 0)
            _sql.append("order by es.objid,eso.objid ");
        else
        if(sort == 1)
            _sql.append("order by es.objid desc,eso.objid ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public int changeExample(int exampleid, int status)
    {
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowExample _example = loadExample(exampleid);
        _example.setStatus(Integer.valueOf(status));
        _example.setEndTime(new Date());
        int _exampleid = saveExample(_example);
        if(status != WorkflowProcessStatus.WF_PROCESS_LAUNCH.getEnumItemValue() && status != WorkflowProcessStatus.WF_PROCESS_BEING.getEnumItemValue())
        {
            Map choosemap = new HashMap();
            choosemap.put("subexampleid", Integer.valueOf(_exampleid));
            List relationlist = getRelationList(choosemap);
            for(int i = 0; i < relationlist.size(); i++)
            {
                Map relationmap = (Map)relationlist.get(i);
                int objid = NumberTool.convertMapKeyToInt(relationmap, "objid", Integer.valueOf(0)).intValue();
                IWorkflowExampleRelation relation = loadExampleRelation(objid);
                if(relation.getStatus().intValue() == WorkflowRelationStatus.WF_RELATION_PEND.getEnumItemValue())
                {
                    relation.setStatus(Integer.valueOf(WorkflowRelationStatus.WF_RELATION_COMPLETED.getEnumItemValue()));
                    saveExampleRelation(relation);
                }
            }

        }
        Map _templetemap = getProcessTempleteByExampleid(_exampleid);
        int _processtype = NumberTool.convertMapKeyToInt(_templetemap, "processtype", Integer.valueOf(0)).intValue();
        Map _mainmap = _workbase.findParaByEndNameAndKey("ProcessType", _processtype);
        int _valueads = NumberTool.convertMapKeyToInt(_mainmap, "valueads", Integer.valueOf(0)).intValue();
        if(_valueads > 0)
        {
            Map _paramap = loadParaMap(_exampleid, 0, 0);
            _paramap.put("exampleid", Integer.valueOf(_exampleid));
            _workin.judgeSpecialTask(_valueads, _paramap);
        }
        return _exampleid;
    }

    public int changeExampleO(int exampleid, int status)
    {
        List _steplist = getExampleStepList(exampleid);
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _status = NumberTool.convertMapKeyToInt(_stepmap, "stepstatus", Integer.valueOf(0)).intValue();
            if(_status == 0 || _status == 1 || _status == -1)
            {
                int _examplestepid = NumberTool.convertMapKeyToInt(_stepmap, "examplestepid", Integer.valueOf(0)).intValue();
                changeExampleStepS(_examplestepid, status);
            }
        }

        return 0;
    }

    public int changeExampleS(int exampleid, int status)
    {
        int _exampleid = changeExample(exampleid, status);
        changeExampleO(exampleid, status);
        return _exampleid;
    }

    public int changeExampleStep(int stepid, int status)
    {
        IWorkflowExampleStep _examplestep = loadExampleStep(stepid);
        _examplestep.setStatus(Integer.valueOf(status));
        _examplestep.setEndTime(new Date());
        return saveExampleStep(_examplestep);
    }

    public int changeExampleStepO(int stepid, int status)
    {
        List _ownerlist = getExampleOwnerList(stepid);
        for(int i = 0; i < _ownerlist.size(); i++)
        {
            Map _ownermap = (Map)_ownerlist.get(i);
            int _status = NumberTool.convertMapKeyToInt(_ownermap, "ownerstatus", Integer.valueOf(0)).intValue();
            if(_status == 0 || _status == 1)
            {
                int _exampleownerid = NumberTool.convertMapKeyToInt(_ownermap, "exampleownerid", Integer.valueOf(0)).intValue();
                changeExampleOwner(_exampleownerid, status);
            }
        }

        return 0;
    }

    public int changeExampleStepS(int stepid, int status)
    {
        int _stepid = changeExampleStep(stepid, status);
        changeExampleStepO(stepid, status);
        return _stepid;
    }

    public int changeExampleOwner(int ownerid, int status)
    {
        IWorkflowExampleStepOwner _stepowner = loadExampleStepOwner(ownerid);
        _stepowner.setStatus(Integer.valueOf(status));
        _stepowner.setApproveTime(new Date());
        return saveExampleStepOwner(_stepowner);
    }

    public int findEntryId(int exampleid, int sourceid, String sourcename)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ee.objid ");
        _sql.append("from WF_EXAMPLEENTRY ee ");
        _sql.append("where ee.exampleid=").append(exampleid).append(" and ee.sourceid=").append(sourceid);
        _sql.append(" and ee.sourcename='").append(sourcename).append("' ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
        {
            Map _map = (Map)_list.get(0);
            return NumberTool.convertMapKeyToInt(_map, "objid", Integer.valueOf(0)).intValue();
        } else
        {
            return 0;
        }
    }

    public List getReleaseProcesss(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid as processid,p.name,p.processcode ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.status=2 ");
        if(type == -1)
            _sql.append("and p.processcode is null ");
        else
        if(type == 1)
            _sql.append("and (p.processcode is not null and p.processcode !='') ");
        _sql.append("order by p.sort ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getIssueProcess(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p2.name,p2.syscode ");
        _sql.append("from WF_PROCESS p2 ");
        _sql.append("where p2.objid in( ");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.status!=1 and p.status!=5 ");
        if(type != 0)
            _sql.append("and p.processtype=").append(type).append(" ");
        _sql.append("and p.syscode is not null ");
        _sql.append("group by p.syscode) and (p2.status=2 or p2.status=3) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setDefaultOrderBy(" NLSSORT(p2.name,'NLS_SORT = SCHINESE_PINYIN_M')");
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getIssueProcessStep(int syscode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select sp2.name,sp2.stepcode \n");
        _sql.append("from WF_PROCESSSTEP sp2 \n");
        _sql.append("where sp2.processid in( \n");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.status!=1 and p.status!=5 ");
        if(syscode != 0)
            _sql.append("and p.syscode='").append(syscode).append("' ");
        _sql.append(")\n");
        _sql.append(" and sp2.status!=100  and sp2.steptypeid not in (1,2) \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getProcessByType(int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p2.name as value,p2.objid as key ");
        _sql.append("from WF_PROCESS p2 ");
        _sql.append("where (p2.status=2 or p2.status=3) and p2.objid in( ");
        _sql.append("select Max(p.objid) ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where (p.status=2 or p.status=3 or p.status=100) ");
        if(type != 0)
            _sql.append("and p.processtype=").append(type).append(" ");
        _sql.append("and p.syscode is not null ");
        _sql.append("group by p.syscode) ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleEntrys(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ee.objid as entryid,ee.sourcename,ee.sourceid,ee.status,ee.changetime ");
        _sql.append("from WF_EXAMPLEENTRY ee ");
        _sql.append("where ee.status!=100 and ee.exampleid=").append(exampleid).append(" ");
        _sql.append("order by ee.changetime ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getAgentsList(int orgid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select a.objid,a.typesource,a.agentsid,a.status,a.endtime,a.starttime ");
        _sql.append("from WF_AGENTS a ");
        _sql.append("where a.ownerid=").append(orgid).append(" ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getLiveAgentsList(int orgid)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select a.objid,a.typesource,a.agentsid,a.status,a.endtime,a.starttime ");
        _sql.append("from WF_AGENTS a ");
        _sql.append("where a.ownerid=").append(orgid).append(" and a.status=1 ");
        _sql.append("and a.starttime<=:time and a.endtime>=:time ");
        _params.put("time", new Date());
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getLiveAgentsList(Map map)
    {
        Map _params = new HashMap();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select a.objid,a.typesource,a.agentsid,a.status,a.endtime,a.starttime ");
        _sql.append("from WF_AGENTS a ");
        _sql.append("where a.status=1 ");
        int _orgid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        if(_orgid > 0)
        {
            _sql.append("and a.ownerid=:orgid ");
            _params.put("orgid", Integer.valueOf(_orgid));
        }
        if(map.get("starttime") != null)
        {
            Date _starttime = (Date)map.get("starttime");
            _sql.append("and a.starttime<=:starttime ");
            _params.put("starttime", _starttime);
        }
        if(map.get("endtime") != null)
        {
            Date _endtime = (Date)map.get("endtime");
            _sql.append("and a.endtime>=:endtime ");
            _params.put("endtime", _endtime);
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getProcessListByTypeAndId(String entrytype, int entryid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ee.objid,ee.exampleid ");
        _sql.append("from WF_ExampleEntry ee ");
        _sql.append("where ee.sourcename='").append(entrytype).append("' and ee.sourceid=").append(entryid).append(" ");
        _sql.append("and ee.status=1 ");
        _sql.append(" order by ee.exampleid desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getProcessListByEntryList(List entrylist)
    {
        if(entrylist.size() == 0)
            return new ArrayList();
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ee.exampleid ");
        _sql.append("from WF_ExampleEntry ee ");
        _sql.append("where (ee.status=1 or ee.status=0) ");
        if(entrylist.size() > 0)
            _sql.append("and ( ");
        for(int i = 0; i < entrylist.size(); i++)
        {
            Map _entrymap = (Map)entrylist.get(i);
            String _entryname = StringUtil.safeToString(_entrymap.get("sourcename"), "");
            int _entryid = NumberTool.convertMapKeyToInt(_entrymap, "sourceid", Integer.valueOf(0)).intValue();
            if(i > 0)
                _sql.append("or ");
            _sql.append("(ee.sourcename='").append(_entryname).append("' and ee.sourceid=").append(_entryid).append(") ");
        }

        if(entrylist.size() > 0)
            _sql.append(") ");
        _sql.append(" order by ee.exampleid desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public int saveParaMap(int exampleid, int examplestepid, int exampleownerid, Map map)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        int num = 0;
        int type = 0;
        for(Iterator i$ = map.keySet().iterator(); i$.hasNext();)
        {
            Object _key = i$.next();
            Object _value = map.get(_key);
            if(_value instanceof Integer)
                type = 1;
            else
            if(_value instanceof Double)
                type = 2;
            else
            if(_value instanceof Float)
                type = 3;
            else
            if(_value instanceof String)
                type = 4;
            else
            if(_value instanceof Map)
                type = 5;
            else
            if(_value instanceof List)
                type = 6;
            else
                type = 0;
            String value = _value.toString();
            if(value.length() >= a.getParaMaxLength())
            {
                LOGGER.info((new StringBuilder()).append("\u53C2\u6570").append(_key.toString()).append("\u4E3A ").append(value).toString());
                LOGGER.info((new StringBuilder()).append("\u8D85\u51FA\u53C2\u6570\u6700\u5927\u957F\u5EA6\uFF0C\u81EA\u52A8\u622A\u53D6\u4E3A ").append(value.substring(0, a.getParaMaxLength())).toString());
                value = value.substring(0, a.getParaMaxLength());
            }
            IWorkflowExamplePara _para = loadExamplePara(0);
            _para.setExampleId(Integer.valueOf(exampleid));
            _para.setExampleStepId(Integer.valueOf(examplestepid));
            _para.setExampleOwnerId(Integer.valueOf(exampleownerid));
            _para.setName(_key.toString());
            _para.setType(Integer.valueOf(type));
            _para.setValue(value);
            saveExamplePara(_para);
            num++;
        }

        return num;
    }

    public Map loadParaMap(int exampleid, int examplestepid, int exampleownerid)
    {
        List _list = loadParaList(exampleid, examplestepid, exampleownerid);
        Map map = new HashMap();
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            String _name = StringUtil.safeToString(_map.get("name"), "");
            String _value = StringUtil.safeToString(_map.get("value"), "");
            int _type = NumberTool.convertMapKeyToInt(_map, "type", Integer.valueOf(0)).intValue();
            if(_type == 1)
            {
                int value = NumberTool.safeToInteger(_value, Integer.valueOf(0)).intValue();
                map.put(_name, Integer.valueOf(value));
                continue;
            }
            if(_type == 2)
            {
                Double value = NumberTool.safeToDouble(_value, Double.valueOf(0.0D));
                map.put(_name, value);
                continue;
            }
            if(_type == 3)
            {
                Float value = NumberTool.safeToFloat(_value, Float.valueOf(0.0F));
                map.put(_name, value);
                continue;
            }
            if(_type == 4)
            {
                String value = _value;
                map.put(_name, value);
                continue;
            }
            if(_type != 5)
                if(_type != 6);
        }

        return map;
    }

    public List loadParaList(int exampleid, int examplestepid, int exampleownerid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ep.name,ep.value,ep.type,ep.exampleid,ep.examplestepid,ep.exampleownerid ");
        _sql.append("from WF_EXAMPLEPARA ep ");
        _sql.append("where ep.exampleid=").append(exampleid).append(" ");
        if(examplestepid != 0)
            _sql.append("and (ep.examplestepid=0 or examplestepid=").append(examplestepid).append(") ");
        if(exampleownerid != 0)
            _sql.append("and (ep.exampleownerid=0 or exampleownerid=").append(exampleownerid).append(") ");
        _sql.append("order by ep.objid");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List findParaListByName(String name)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select el.key,el.value ");
        _sql.append("from WF_EXAMPLELIST el ");
        _sql.append("where el.status=1 and el.name='").append(name).append("'");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }

    public Map findAllParaMap()
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select el.key,el.value,el.name ");
        _sql.append("from WF_EXAMPLELIST el ");
        _sql.append("where el.status=1 ");
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

    public List getExampleOwnerListByExampleid(int exampleid, int type)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select eo.examplestepid,eo.objid as exampleownerid,eo.ownerid,so1.orgname as ownername ");
        _sql.append("from WF_EXAMPLESTEPOWNER eo ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eo.ownerid) ");
        _sql.append("where 1=1 ");
        if(type == 0)
            _sql.append("and (eo.status=1 or eo.status=0) ");
        else
        if(type != -1);
        _sql.append("and eo.exampleid=:exampleid ");
        Map _params = new HashMap();
        _params.put("exampleid", Integer.valueOf(exampleid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getDetailOwnerListByExampleid(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select eo.examplestepid,eo.objid as exampleownerid,eo.ownerid,eo.approvestatus,eo.approveopinion,\n ");
        _sql.append("eo.approvetime,es.processstepid,ps.pointtypeid as stageid,eo.preownerid,\n ");
        _sql.append("so1.orgname as ownername,so2.orgname as preownername\n ");
        _sql.append("from WF_EXAMPLESTEPOWNER eo\n ");
        _sql.append("inner join WF_EXAMPLESTEP es on es.objid=eo.examplestepid\n ");
        _sql.append("inner join WF_PROCESSSTEP ps on ps.objid=es.processstepid\n ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eo.ownerid)\n ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eo.preownerid)\n ");
        _sql.append("where 1=1 \n");
        _sql.append("and eo.exampleid=:exampleid \n");
        _sql.append("order by eo.objid \n");
        Map _params = new HashMap();
        _params.put("exampleid", Integer.valueOf(exampleid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleOwnerListByStepid(int stepid, int type)
    {
        return getExampleOwnerListByStepid(stepid, type, 0);
    }

    public List getExampleOwnerListByStepid(int stepid, int type, int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eo.exampleid,eo.examplestepid,eo.objid as exampleownerid,eo.ownerid,eo.preownerid, ");
        _sql.append("eo.status,eo.approvestatus as condition,eo.value,eo.approveopinion ");
        _sql.append("from WF_EXAMPLESTEPOWNER eo ");
        _sql.append("left join WF_EXAMPLESTEP es on eo.examplestepid=es.objid ");
        _sql.append("where 1=1 ");
        if(type == 0 || type == 1)
        {
            _sql.append("and (eo.status=1 or eo.status=0) ");
            _sql.append("and eo.examplestepid=:stepid ");
        } else
        if(type == -1)
            _sql.append("and eo.examplestepid=:stepid ");
        else
        if(type == 2)
        {
            _sql.append("and (eo.status=1 or eo.status=0) ");
            _sql.append("and es.processstepid=:stepid ");
        } else
        if(type == -2)
            _sql.append("and es.processstepid=:stepid ");
        if(exampleid != 0)
        {
            _sql.append("and eo.exampleid=:exampleid ");
            _params.put("exampleid", Integer.valueOf(exampleid));
        }
        _sql.append("order by eo.starttime ");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getTotalExampleOwnerListByStepid(int stepid, int type, int exampleid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eo.exampleid,eo.examplestepid,eo.objid as exampleownerid,eo.ownerid,eo.preownerid, ");
        _sql.append("eo.status,eo.approvestatus as condition,eo.value,eo.approveopinion ");
        _sql.append("from WF_EXAMPLESTEPOWNER eo ");
        _sql.append("left join WF_EXAMPLESTEP es on eo.examplestepid=es.objid ");
        _sql.append("left join WF_EXAMPLE e on e.objid=eo.exampleid ");
        _sql.append("left join WF_PROCESS p on e.processid=p.objid ");
        _sql.append("where 1=1 ");
        if(type == 0 || type == 1)
        {
            _sql.append("and (eo.status=1 or eo.status=0) ");
            _sql.append("and eo.examplestepid=:stepid ");
        } else
        if(type == -1)
            _sql.append("and eo.examplestepid=:stepid ");
        else
        if(type == 2)
        {
            _sql.append("and (eo.status=1 or eo.status=0) ");
            _sql.append("and es.processstepid=:stepid ");
        } else
        if(type == -2)
            _sql.append("and es.processstepid=:stepid ");
        if(exampleid != 0)
        {
            IWorkflowExample _example = loadExample(exampleid);
            IWorkflowProcess _process = _workbase.loadProcess(_example.getProcessId().intValue());
            _sql.append("and p.syscode=:syscode ");
            _params.put("syscode", _process.getSysCode());
        }
        _sql.append("order by eo.starttime ");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getAgentsByOwnerAndId(int currentstepid, int ownerid, int syscode, int stepcode)
    {
        IWorkflowBaseService workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List _list2 = new ArrayList();
        if(stepcode > 0)
        {
            int agentmark = workbase.isAgentStep(currentstepid);
            if(agentmark == 0)
                return _list2;
        }
        IWorkflowProcess process = workbase.loadProcess(syscode);
        IWorkflowProcessStep step = workbase.loadProcessStep(currentstepid);
        int processtype = NumberTool.safeToInteger(process.getProcessType(), Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select a.agentsid,so.orgname as agentsname,a.status ");
        _sql.append("from WF_Agents a ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(a.agentsid) ");
        _sql.append("where (a.status=1 or a.status=2) and a.starttime<=:time and a.endtime>=:time and a.ownerid=:ownerid ");
        if(processtype > 0)
            _sql.append("and (a.processtype=:processtype or a.processtype=0) ");
        if(syscode > 0)
            _sql.append("and (a.processid=:processid or a.processid=0) ");
        if(stepcode > 0)
            _sql.append("and (a.processstepid=:stepid or a.processstepid=0) ");
        _sql.append("order by a.processstepid desc,a.processid desc,a.processtype desc ");
        _params.put("time", new Date());
        _params.put("processtype", Integer.valueOf(processtype));
        _params.put("processid", Integer.valueOf(syscode));
        _params.put("stepid", Integer.valueOf(stepcode));
        _params.put("ownerid", Integer.valueOf(ownerid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int status = NumberTool.convertMapKeyToInt(_map, "status", Integer.valueOf(0)).intValue();
            if(status == 1)
            {
                _list2.add(_map);
                return _list2;
            }
            if(status == 2)
                return _list2;
        }

        return _list2;
    }

    public List getAgentsByOwnerAndId(int ownerid, int processid, int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select a.agentsid,so.orgname as agentsname ");
        _sql.append("from WF_Agents a ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(a.agentsid) ");
        _sql.append("where a.status=1 and a.starttime<=:time and a.endtime>=:time and a.ownerid=:ownerid ");
        if(processid > 0)
            _sql.append("and a.processid=:processid ");
        if(stepid > 0)
            _sql.append("and a.processstepid=:stepid ");
        _params.put("time", new Date());
        _params.put("processid", Integer.valueOf(processid));
        _params.put("stepid", Integer.valueOf(stepid));
        _params.put("ownerid", Integer.valueOf(ownerid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public Map getStepTempleteByStepid(int stepid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select ps.pointtypeid,ps.name as stepname ");
        _sql.append("from WF_ExampleStep es ");
        _sql.append("left join WF_ProcessStep ps on es.processid=ps.processid and es.processstepid=ps.objid ");
        _sql.append("where es.objid=:stepid ");
        _params.put("stepid", Integer.valueOf(stepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map getProcessTempleteByExampleid(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select p.processtype ");
        _sql.append("from WF_Example e ");
        _sql.append("left join WF_Process p on e.processid=p.objid ");
        _sql.append("where e.objid=:exampleid ");
        _params.put("exampleid", Integer.valueOf(exampleid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map getCurrentExampleStep(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select exampleid, objid examplestepid, processid, processstepid,status,processstepname,steptype \n");
        _sql.append(" from wf_examplestep \n");
        _sql.append("where objid in (select max(objid) from wf_examplestep where exampleid=:exampleid) \n");
        _params.put("exampleid", Integer.valueOf(exampleid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public List getFrontExampleStepList(int examplestepid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select esl.prestepid,esl.aftstepid,ps.steptypeid as presteptype,ps.pointtypeid as prepointtype ");
        _sql.append("from WF_EXAMPLESTEPLINK esl ");
        _sql.append("left join WF_EXAMPLESTEP es on esl.prestepid=es.objid ");
        _sql.append("left join WF_PROCESSSTEP ps on es.processstepid=ps.objid ");
        _sql.append("where esl.aftstepid=:examplestepid ");
        _params.put("examplestepid", Integer.valueOf(examplestepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getMyProcessList(int orgid, int processid, int processtype, String syscode)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.status,e.processid,e.starterid,e.starttime ");
        _sql.append("from WF_EXAMPLE e ");
        _sql.append("left join WF_PROCESS p on e.processid=p.objid ");
        _sql.append("where 1=1 ");
        if(orgid >= 0)
        {
            _sql.append("and e.starterid=:orgid ");
            _params.put("orgid", Integer.valueOf(orgid));
        }
        if(processid != 0)
        {
            _sql.append("and e.processid=:processid ");
            _params.put("processid", Integer.valueOf(processid));
        }
        if(processtype != 0)
        {
            _sql.append("and p.processtype=:processtype ");
            _params.put("processtype", Integer.valueOf(processtype));
        }
        if(syscode != null && syscode.length() > 0)
        {
            _sql.append("and p.syscode=:syscode ");
            _params.put("syscode", syscode);
        }
        _sql.append("order by e.starttime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleOwnerOpinion(int exampleid, int sort, int type)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eso.ownerid,eso.approveopinion,eso.approvetime,eso.status as ownerstatus, ");
        _sql.append("so.orgname as ownername,el.value as approvestatusvalue,el1.value ownerstatusvalue ");
        _sql.append("from WF_EXAMPLESTEPOWNER eso ");
        _sql.append("left join WF_EXAMPLELIST el on el.name='StepCondition' and el.key=eso.approvestatus ");
        _sql.append("left join WF_EXAMPLELIST el1 on el1.name='OwnerStatus' and el1.key=eso.status ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(eso.ownerid) ");
        _sql.append("where eso.exampleid=:exampleid ");
        _params.put("exampleid", Integer.valueOf(exampleid));
        if(type == 1)
            _sql.append("and (eso.status=8) ");
        else
        if(type == 10)
            _sql.append("and eso.examplestepid=0 ");
        if(sort == 1)
            _sql.append("order by eso.approvetime asc ");
        else
        if(sort == 2)
            _sql.append("order by eso.approvetime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getOwnerListByExampleid(int exampleid)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eso.ownerid,eso.status as ownerstatus,eso.approvestatus ");
        _sql.append("from WF_EXAMPLESTEPOWNER eso ");
        _sql.append("where eso.exampleid=:exampleid ");
        _params.put("exampleid", Integer.valueOf(exampleid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getHistorySourceList(Map map)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        int _ownerid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        String _orgid = StringUtil.safeToString(map.get("orgid"), "");
        String _processstepid = StringUtil.safeToString(map.get("processstepid"), "");
        String _sourcename = StringUtil.safeToString(map.get("sourcename"), "");
        String _starttime = StringUtil.safeToString(map.get("starttime"), "");
        String _endtime = StringUtil.safeToString(map.get("endtime"), "");
        int _approvestatus = NumberTool.convertMapKeyToInt(map, "approvestatus", Integer.valueOf(0)).intValue();
        _sql.append("select ee.sourcename,ee.sourceid,ee.exampleid from WF_EXAMPLEENTRY ee \n");
        _sql.append("where exampleid in( \n");
        _sql.append("select eso.exampleid from WF_EXAMPLESTEPOWNER eso \n");
        _sql.append("inner join WF_EXAMPLESTEP es on eso.examplestepid=es.objid \n");
        _sql.append("where 1=1 \n");
        if(_approvestatus > 0)
        {
            _sql.append("and eso.approvestatus=:approvestatus \n");
            _params.put("approvestatus", Integer.valueOf(_approvestatus));
        } else
        if(_approvestatus == 0)
            _sql.append("and eso.approvestatus is not null \n");
        else
        if(_approvestatus == -1)
            _sql.append("and eso.approvestatus is null \n");
        else
        if(_approvestatus != -2);
        if(_ownerid != 0)
        {
            _sql.append("and eso.ownerid=:ownerid \n");
            _params.put("ownerid", Integer.valueOf(_ownerid));
        } else
        if(_orgid.length() > 0)
            _sql.append("and eso.ownerid in (").append(_orgid).append(") \n");
        if(_processstepid.length() == 0)
            _sql.append("and 1=0 ");
        else
            _sql.append("and es.processstepid in (").append(_processstepid).append(") \n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            if(_starttime.length() > 0)
            {
                Date starttime;
                if(_starttime.indexOf(':') > 0)
                    starttime = dateFormat.parse(_starttime);
                else
                    starttime = dateFormat.parse((new StringBuilder()).append(_starttime).append(" 00:00:00").toString());
                _sql.append("and eso.starttime>=:starttime ");
                _params.put("starttime", starttime);
            }
            if(_endtime.length() > 0)
            {
                Date endtime;
                if(_endtime.indexOf(':') > 0)
                    endtime = dateFormat.parse(_endtime);
                else
                    endtime = dateFormat.parse((new StringBuilder()).append(_endtime).append(" 23:59:59").toString());
                _sql.append("and eso.approvetime<=:endtime ");
                _params.put("endtime", endtime);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("getHistorySourceList\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        _sql.append(") ");
        if(_sourcename.length() > 0)
        {
            _sql.append("and ee.sourcename=:sourcename \n");
            _params.put("sourcename", _sourcename);
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_params != null)
            _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public Map getHistoryStepMap(int type)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select psh.prestepid,psh.newstepid from WF_PROCESSSTEPHISTORY psh");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        Map map = new HashMap();
        if(type == 0)
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

    public IMetaDBQuery getModuleSPTask(Map map)
    {
        int _userid = NumberTool.convertMapKeyToInt(map, "userid", Integer.valueOf(-1)).intValue();
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        String _keyword = StringUtil.safeToString(map.get("keyword"), "");
        String _begindate = StringUtil.safeToString(map.get("begindate"), "");
        String _enddate = StringUtil.safeToString(map.get("enddate"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.brief as title,eso.approvetime as tdate,es.objid as stepid,eso.objid as taskid ");
        _sql.append("from WF_EXAMPLESTEPOWNER eso ");
        _sql.append("inner join WF_EXAMPLESTEP es on es.objid=eso.examplestepid ");
        _sql.append("inner join WF_EXAMPLE e on es.objid=eso.examplestepid and e.objid=es.exampleid ");
        _sql.append("where 1=1 ");
        if(_userid == -1)
        {
            _sql.append("and 1=0 ");
        } else
        {
            _sql.append("and eso.ownerid=:userid ");
            _params.put("userid", Integer.valueOf(_userid));
        }
        if(_keyword.length() > 0)
        {
            _sql.append("and e.brief like :keyword ");
            _params.put("keyword", (new StringBuilder()).append("%").append(_keyword).append("%").toString());
        }
        if(_status == 1)
            _sql.append("and (eso.status=0 or eso.status=1) and es.steptype!=1 ");
        else
        if(_status == 2)
            _sql.append("and (eso.status!=0 and eso.status!=1) and es.steptype!=1 ");
        else
        if(_status == 3)
            _sql.append("and es.steptype=1 ");
        else
        if(_status != 10)
            _sql.append("and 2=0 ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            if(_begindate.length() > 0)
            {
                Date begindate;
                if(_begindate.indexOf(':') > 0)
                    begindate = dateFormat.parse(_begindate);
                else
                    begindate = dateFormat.parse((new StringBuilder()).append(_begindate).append(" 00:00:00").toString());
                _sql.append("and eso.starttime>=:begindate ");
                _params.put("begindate", begindate);
            }
            if(_enddate.length() > 0)
            {
                Date enddate;
                if(_enddate.indexOf(':') > 0)
                    enddate = dateFormat.parse(_enddate);
                else
                    enddate = dateFormat.parse((new StringBuilder()).append(_enddate).append(" 23:59:59").toString());
                _sql.append("and eso.starttime<=:enddate ");
                _params.put("enddate", enddate);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("getHistorySourceList\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        String sql = _sql.toString();
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _dbQuery.setParameters(_params);
        if(_status == 1)
            _dbQuery.setDefaultOrderBy("eso.starttime desc ");
        else
            _dbQuery.setDefaultOrderBy("eso.approvetime desc ");
        return _dbQuery;
    }

    public List getDetailTaskList(Map map)
    {
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "stepid", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eso.objid as taskid,eso.approvetime as tdate, ");
        _sql.append("el.value as approvestatusvalue,el1.value as ownerstatusvalue ");
        _sql.append("from WF_EXAMPLESTEPOWNER eso ");
        _sql.append("left join WF_EXAMPLELIST el on el.name='StepCondition' and el.key=eso.approvestatus ");
        _sql.append("left join WF_EXAMPLELIST el1 on el1.name='OwnerStatus' and el1.key=eso.status ");
        _sql.append("where 1=1 ");
        if(_examplestepid == 0)
        {
            _sql.append("and 1=0 ");
        } else
        {
            _sql.append("and eso.examplestepid=:examplestepid ");
            _params.put("examplestepid", Integer.valueOf(_examplestepid));
        }
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_params != null)
            _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getDealerTaskCountList(Map map)
    {
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select so.orgname,eo.ownerid,count(1) as tasknum from WF_EXAMPLESTEPOWNER eo \n");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(eo.ownerid) \n");
        _sql.append("inner join WF_EXAMPLE e on e.objid=eo.exampleid \n");
        _sql.append("inner join WF_PROCESS p on p.objid=e.processid \n");
        _sql.append("where eo.status=0 and so.orgname is not null group by so.orgname,eo.ownerid \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_params != null)
            _dbQuery.setParameters(_params);
        return _dbQuery;
    }

    public List getDetailOwnerList(Map map)
    {
        String _ownerids = StringUtil.safeToString(map.get("ownerids"), "");
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select eso.objid as exampleownerid,es.objid as examplestepid,e.objid as exampleid,\n ");
        _sql.append("eso.status as ownerstatus,eso.approvestatus,es.actionurl,es.processstepname,es.stepcondition,\n ");
        _sql.append("es.status as stepstatus,e.brief,so1.orgname as ownername,so2.orgname as startername\n ");
        _sql.append("from Wf_Examplestepowner eso\n ");
        _sql.append("inner join WF_EXAMPLESTEP es on eso.examplestepid = es.objid\n ");
        _sql.append("inner join WF_EXAMPLE e on eso.exampleid = e.objid\n ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid)\n ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(e.starterid)\n ");
        _sql.append("where 1=1 ");
        if(_ownerids.length() > 0)
            _sql.append("and eso.objid in (").append(_ownerids).append(")");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_params != null)
            _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getRelationList(Map map)
    {
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        int subexampleid = NumberTool.convertMapKeyToInt(map, "subexampleid", Integer.valueOf(0)).intValue();
        int type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select er.exampleid,er.examplestepid,er.exampleownerid,er.subexampleid,\n ");
        _sql.append("er.status,er.sort,er.mark,er.brief,er.objid,er.createtimestamp as starttime, \n ");
        _sql.append("er.updatetimestamp as endtime ");
        _sql.append("from WF_EXAMPLERELATION er \n");
        _sql.append("where 1=1 \n");
        if(exampleid != 0)
        {
            _sql.append("and er.exampleid=:exampleid \n");
            _params.put("exampleid", Integer.valueOf(exampleid));
        }
        if(examplestepid != 0)
        {
            _sql.append("and er.examplestepid=:examplestepid \n");
            _params.put("examplestepid", Integer.valueOf(examplestepid));
        }
        if(exampleownerid != 0)
        {
            _sql.append("and er.exampleownerid=:exampleownerid \n");
            _params.put("exampleownerid", Integer.valueOf(exampleownerid));
        }
        if(subexampleid != 0)
        {
            _sql.append("and er.subexampleid=:subexampleid \n");
            _params.put("subexampleid", Integer.valueOf(subexampleid));
        }
        if(type != 0 && type == 1)
            _sql.append("and er.status=0 ");
        _sql.append("order by er.sort desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_params != null)
            _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public List getExampleStepLinkList(int examplestepid, int type)
    {
        StringBuilder _sql = new StringBuilder();
        Map _param = new HashMap();
        _sql.append(" SELECT esl.objid, esl.prestepid, esl.aftstepid \n");
        _sql.append("  , es.stepcondition, es.status stepstatus \n");
        _sql.append(" FROM WF_EXAMPLESTEPLINK esl \n");
        _sql.append(" LEFT JOIN WF_EXAMPLESTEP es ");
        if(type == 1)
            _sql.append(" ON es.objid=esl.aftstepid \n");
        else
            _sql.append(" ON es.objid=esl.prestepid \n");
        _sql.append(" WHERE esl.status=1 \n");
        if(type == 1)
            _sql.append(" AND esl.aftstepid=:examplestepid \n");
        else
            _sql.append(" AND esl.prestepid=:examplestepid \n");
        _param.put("examplestepid", Integer.valueOf(examplestepid));
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        if(_param != null)
            _dbQuery.setParameters(_param);
        return _dbQuery.getResult();
    }

    public List getExampleTask2(Map map)
    {
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(-1)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(-1)).intValue();
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(-1)).intValue();
        int _orgid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        int _steptype = NumberTool.convertMapKeyToInt(map, "steptype", Integer.valueOf(0)).intValue();
        String exampleStatus = StringUtil.convertMapKeyToString(map, "examplestatus", "");
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        Map _showmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowShow", 1);
        int _startshow = NumberTool.convertMapKeyToInt(_showmap, "valueads", Integer.valueOf(0)).intValue();
        Map _sortmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowSort", 1);
        int _tasksort = NumberTool.convertMapKeyToInt(_sortmap, "valueads", Integer.valueOf(0)).intValue();
        StringBuffer _sql = new StringBuffer();
        Map _params = new HashMap();
        _sql.append("select e.objid as exampleid,e.processname,e.brief as processbrief,e.starterid, ");
        _sql.append("es.objid as examplestepid,es.processstepname,es.starttime,pl2.value as steptypevalue, ");
        _sql.append("es.actionurl,es.showurl,el2.value as stepstatusvalue,eso.approvetime, ");
        _sql.append("el3.value as processstatusvalue, ");
        _sql.append("eso.objid as exampleownerid,eso.ownerid,eso.preownerid, ");
        _sql.append("so.orgname as startername,so1.orgname as ownername,so2.orgname as preownername ");
        _sql.append("from WF_EXAMPLE e ");
        _sql.append("left join WF_EXAMPLESTEP es on e.objid=es.exampleid ");
        if(_startshow == 1)
            _sql.append("and es.steptype!=1 ");
        _sql.append("left join WF_EXAMPLELIST el2 on el2.name='StepStatus' and es.status=el2.key ");
        _sql.append("left join WF_EXAMPLELIST el3 on el3.name='ExampleStatus' and e.status=el3.key ");
        _sql.append("left join WF_PROCESSLIST pl2 on pl2.name='StepType' and es.steptype=pl2.key ");
        _sql.append("left join WF_EXAMPLESTEPOWNER eso on es.objid=eso.examplestepid ");
        _sql.append("left join sprt_orgobject so on so.orgid=to_char(e.starterid) ");
        _sql.append("left join sprt_orgobject so1 on so1.orgid=to_char(eso.ownerid) ");
        _sql.append("left join sprt_orgobject so2 on so2.orgid=to_char(eso.preownerid) ");
        _sql.append("inner join WF_PROCESS p on p.objid=e.processid ");
        if(_processtype != -1)
            if(_processid != -1)
            {
                _sql.append("and p.syscode=:syscode ");
                _params.put("syscode", (new StringBuilder()).append("").append(_processid).toString());
            } else
            {
                _sql.append("and p.processtype=:processtype ");
                _params.put("processtype", Integer.valueOf(_processtype));
            }
        _sql.append("where (eso.exampleid,eso.createtimestamp ,eso.ownerid )in(select exampleid, max(createtimestamp) createtimestamp, max(ownerid) ownerid \n  from wf_examplestepowner\n where exampleid in (select distinct exampleid\n                       from wf_examplestepowner\n                      where ownerid = :orgid)\n group by exampleid\n) ");
        if(_starterid >= 0)
        {
            _sql.append("and e.starterid=:starterid ");
            _params.put("starterid", Integer.valueOf(_starterid));
        }
        if(_steptype > 0)
        {
            _sql.append("and es.steptype=:steptype ");
            _params.put("steptype", Integer.valueOf(_steptype));
        }
        if(!exampleStatus.equals(""))
            _sql.append((new StringBuilder()).append("and e.status in (").append(exampleStatus).append(") ").toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _starttimestart = StringUtil.safeToString(map.get("starttimestart"), "");
        String _starttimeend = StringUtil.safeToString(map.get("starttimeend"), "");
        String _endtimestart = StringUtil.safeToString(map.get("endtimestart"), "");
        String _endtimeend = StringUtil.safeToString(map.get("endtimeend"), "");
        try
        {
            if(_starttimestart.length() > 0)
            {
                Date starttimestart;
                if(_starttimestart.indexOf(':') > 0)
                    starttimestart = dateFormat.parse(_starttimestart);
                else
                    starttimestart = dateFormat.parse((new StringBuilder()).append(_starttimestart).append(" 00:00:00").toString());
                _sql.append("and eso.starttime>=:starttimestart ");
                _params.put("starttimestart", starttimestart);
            }
            if(_starttimeend.length() > 0)
            {
                Date starttimeend;
                if(_starttimeend.indexOf(':') > 0)
                    starttimeend = dateFormat.parse(_starttimeend);
                else
                    starttimeend = dateFormat.parse((new StringBuilder()).append(_starttimeend).append(" 23:59:59").toString());
                _sql.append("and eso.starttime<=:starttimeend ");
                _params.put("starttimeend", starttimeend);
            }
            if(_endtimestart.length() > 0)
            {
                Date endtimestart;
                if(_starttimestart.indexOf(':') > 0)
                    endtimestart = dateFormat.parse(_endtimestart);
                else
                    endtimestart = dateFormat.parse((new StringBuilder()).append(_endtimestart).append(" 00:00:00").toString());
                _sql.append("and eso.approvetime>=:endtimestart ");
                _params.put("endtimestart", endtimestart);
            }
            if(_endtimeend.length() > 0)
            {
                Date endtimeend;
                if(_endtimeend.indexOf(':') > 0)
                    endtimeend = dateFormat.parse(_endtimeend);
                else
                    endtimeend = dateFormat.parse((new StringBuilder()).append(_endtimeend).append(" 23:59:59").toString());
                _sql.append("and eso.approvetime<=:endtimeend ");
                _params.put("endtimeend", endtimeend);
            }
        }
        catch(Exception e)
        {
            LOGGER.warn("getExampleTask\u65F6\u95F4\u53C2\u6570\u8F6C\u6362\u5F02\u5E38", e);
        }
        if(_brief.length() > 0)
        {
            _sql.append("and e.brief like :brief ");
            _params.put("brief", (new StringBuilder()).append("%").append(_brief).append("%").toString());
        }
        if(_type == 0)
        {
            if(_tasksort == 1)
                _sql.append("order by p.processtype,p.syscode,es.processstepid,eso.starttime desc ");
            else
                _sql.append("order by eso.starttime desc ");
        } else
        if(_type == 1)
            _sql.append("order by eso.approvetime desc ");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        _params.put("orgid", Integer.valueOf(_orgid));
        _dbQuery.setParameters(_params);
        List _list = _dbQuery.getResult();
        return _list;
    }

    public IMetaDBQuery getStepLinkInfo(Map map)
    {
        Integer prestepid = NumberTool.safeToInteger(map.get("prestepid"), Integer.valueOf(-1));
        Integer ifads = NumberTool.safeToInteger(map.get("ifads"), Integer.valueOf(-1));
        Integer status = NumberTool.safeToInteger(map.get("status"), Integer.valueOf(-1));
        IMetaDBQuery metaDBQuery = null;
        Map params = new HashMap();
        String sqlcmd = "select link.*,p2.name as aftstepname\n  from wf_processsteplink link\n  left join wf_processsteplinkif linkif\n    on link.objid = linkif.linkid\n  left join wf_processstep p2\n    on link.aftstepid=p2.objid\n  where 1=1\n";
        if(prestepid.intValue() != -1)
        {
            sqlcmd = (new StringBuilder()).append(sqlcmd).append(" and link.prestepid=:prestepid").toString();
            params.put("prestepid", prestepid);
        }
        if(ifads.intValue() != -1)
        {
            sqlcmd = (new StringBuilder()).append(sqlcmd).append(" and linkif.ifads=:ifads").toString();
            params.put("ifads", ifads);
        }
        if(status.intValue() != -1)
        {
            sqlcmd = (new StringBuilder()).append(sqlcmd).append(" and link.status=:status").toString();
            params.put("status", status);
        }
        sqlcmd = (new StringBuilder()).append(sqlcmd).append("  order by link.sort asc").toString();
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(params);
        return metaDBQuery;
    }

    public List findStepNextList(int stepId)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("select eso.status \r\n");
        sb.append("from WF_ExampleStepLink esl \r\n");
        sb.append("left join WF_ExampleStepOwner eso on esl.aftStepId = eso.exampleStepId \r\n");
        sb.append("where esl.preStepId = :preStepId \r\n");
        sb.append("order by eso.objid \r\n");
        Map params = new HashMap();
        params.put("preStepId", Integer.valueOf(stepId));
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sb.toString());
        metaDBQuery.setParameters(params);
        return metaDBQuery.getResult();
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowAppServiceImpl);

}
