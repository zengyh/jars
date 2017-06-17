// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowWebServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.org.busin.entity.Employee;
import java.util.*;

public class WorkflowWebServiceImpl extends MetaDBContextSupport
    implements IWorkflowWebService
{

    public WorkflowWebServiceImpl()
    {
    }

    public boolean detectAgents(int orgid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _list = _workapp.getLiveAgentsList(orgid);
        return _list.size() > 0;
    }

    public boolean detectAgents(int orgid, Date starttime, Date endtime)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map map = new HashMap();
        map.put("orgid", Integer.valueOf(orgid));
        if(starttime != null)
            map.put("starttime", starttime);
        if(endtime != null)
            map.put("endtime", endtime);
        List _list = _workapp.getLiveAgentsList(map);
        return _list.size() > 0;
    }

    public boolean judgeOwners(String orgid, int exampleownerid)
    {
        return judgeOwners(NumberTool.safeToInteger(orgid, Integer.valueOf(0)).intValue(), exampleownerid);
    }

    public boolean judgeOwners(int orgid, int exampleownerid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStepOwner _stepowner = _workapp.loadExampleStepOwner(exampleownerid);
        return _stepowner != null && _stepowner.getOwnerId() != null && _stepowner.getOwnerId().intValue() == orgid;
    }

    public boolean judgeNowOwners(String orgid, int exampleownerid)
    {
        return judgeNowOwners(NumberTool.safeToInteger(orgid, Integer.valueOf(0)).intValue(), exampleownerid);
    }

    public boolean judgeNowOwners(int orgid, int exampleownerid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStepOwner _stepowner = _workapp.loadExampleStepOwner(exampleownerid);
        return _stepowner != null && _stepowner.getOwnerId() != null && _stepowner.getOwnerId().intValue() == orgid && (_stepowner.getStatus().intValue() == 0 || _stepowner.getStatus().intValue() == 1);
    }

    public List getProcessListByEntryList(List entrylist)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _processlist = _workapp.getProcessListByEntryList(entrylist);
        Map _markmap = new HashMap();
        for(int i = 0; i < _processlist.size(); i++)
        {
            Map _processmap = (Map)_processlist.get(i);
            int _exampleid = NumberTool.convertMapKeyToInt(_processmap, "exampleid", Integer.valueOf(0)).intValue();
            if(_exampleid != 0)
            {
                int mark = NumberTool.convertMapKeyToInt(_processmap, StringUtil.safeToString(Integer.valueOf(_exampleid), ""), Integer.valueOf(0)).intValue();
                _markmap.put(StringUtil.safeToString(Integer.valueOf(_exampleid), ""), Integer.valueOf(mark + 1));
            }
        }

        int exampleArr[] = new int[_processlist.size()];
        int exampleMark = 0;
        Iterator i$ = _markmap.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Object _key = i$.next();
            int _mark = NumberTool.convertMapKeyToInt(_markmap, StringUtil.safeToString(_key, ""), Integer.valueOf(0)).intValue();
            if(_mark > 0)
            {
                exampleArr[exampleMark] = NumberTool.safeToInteger(_key, Integer.valueOf(0)).intValue();
                exampleMark++;
            }
        } while(true);
        for(int i = 0; i < exampleMark; i++)
        {
            for(int j = 0; j < i; j++)
                if(exampleArr[i] > exampleArr[j])
                {
                    int exampleTemp = exampleArr[j];
                    exampleArr[j] = exampleArr[i];
                    exampleArr[i] = exampleTemp;
                }

        }

        List _processlist2 = new ArrayList();
        for(int i = 0; i < exampleMark; i++)
        {
            Map _processmap2 = new HashMap();
            _processmap2.put("exampleid", Integer.valueOf(exampleArr[i]));
            _processlist2.add(_processmap2);
        }

        return _processlist2;
    }

    public List getProcessListByAllEntryList(List entrylist)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _processlist = _workapp.getProcessListByEntryList(entrylist);
        Map _markmap = new HashMap();
        for(int i = 0; i < _processlist.size(); i++)
        {
            Map _processmap = (Map)_processlist.get(i);
            int _exampleid = NumberTool.convertMapKeyToInt(_processmap, "exampleid", Integer.valueOf(0)).intValue();
            if(_exampleid != 0)
            {
                int mark = NumberTool.convertMapKeyToInt(_processmap, StringUtil.safeToString(Integer.valueOf(_exampleid), ""), Integer.valueOf(0)).intValue();
                _markmap.put(StringUtil.safeToString(Integer.valueOf(_exampleid), ""), Integer.valueOf(mark + 1));
            }
        }

        List _processlist2 = new ArrayList();
        Iterator i$ = _markmap.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Object _key = i$.next();
            Map _processmap2 = new HashMap();
            int _mark = NumberTool.convertMapKeyToInt(_markmap, StringUtil.safeToString(_key, ""), Integer.valueOf(0)).intValue();
            if(_mark == entrylist.size())
            {
                _processmap2.put("exampleid", _key);
                _processlist2.add(_processmap2);
            }
        } while(true);
        return _processlist2;
    }

    public int getMyTaskCount(int orgid)
    {
        IWorkflowSupportService _worksup = WorkflowServiceFactory.getWorkflowSupportService();
        Map _map = new HashMap();
        _map.put("orgid", Integer.valueOf(orgid));
        List _list = _worksup.getUnfinishTasks(_map);
        return _list.size();
    }

    public String getProcessnameBySyscode(String syscode)
    {
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        Map _map = _workengine.getProcessBySyscode(syscode);
        String _processname = StringUtil.safeToString(_map.get("processname"), "");
        return _processname;
    }

    public int getProcessStatus(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample _example = _workapp.loadExample(exampleid);
        if(_example == null || _example.size() == 0)
            return -1;
        else
            return NumberTool.safeToInteger(_example.getStatus(), Integer.valueOf(-1)).intValue();
    }

    public int getProcessStepStatus(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(examplestepid);
        if(_examplestep == null)
            return -1;
        else
            return NumberTool.safeToInteger(_examplestep.getStatus(), Integer.valueOf(-1)).intValue();
    }

    public int getProcessStepCondition(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(examplestepid);
        if(_examplestep == null)
            return -1;
        else
            return NumberTool.safeToInteger(_examplestep.getStepCondition(), Integer.valueOf(-1)).intValue();
    }

    public int getProcessCondition(int exampleid, int examplestepid)
    {
        int _status = getProcessStatus(exampleid);
        if(_status == -1)
            return -100;
        if(_status == 9)
            return getProcessStepCondition(examplestepid);
        else
            return -2;
    }

    public String getListParaValueByNameAndKey(String name, int key)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _list = _workapp.findParaListByName(name);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _key = NumberTool.convertMapKeyToInt(_map, "key", Integer.valueOf(-100)).intValue();
            if(key == _key)
            {
                String _value = StringUtil.safeToString(_map.get("value"), "");
                return _value;
            }
        }

        return "";
    }

    public Map loadParaMap(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.loadParaMap(exampleid, 0, 0);
    }

    public List getNowExampleOwnerListByExampleid(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getExampleOwnerListByExampleid(exampleid, 0);
    }

    public List getAllExampleOwnerListByExampleid(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getExampleOwnerListByExampleid(exampleid, -1);
    }

    public List getAllExampleOwnerListByExampleStepid(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getExampleOwnerListByStepid(examplestepid, -1);
    }

    public List getAllExampleOwnerListByProcessStepid(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _step = _workapp.loadExampleStep(examplestepid);
        return _workapp.getExampleOwnerListByStepid(_step.getProcessStepId().intValue(), -2, _step.getExampleId().intValue());
    }

    public List getAllExampleOwnerListByProcessStepid(int processstepid, int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getExampleOwnerListByStepid(processstepid, -2, exampleid);
    }

    public List getTotalExampleOwnerListByProcessStepid(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _step = _workapp.loadExampleStep(examplestepid);
        return _workapp.getTotalExampleOwnerListByStepid(_step.getProcessStepId().intValue(), -2, _step.getExampleId().intValue());
    }

    public List getTotalExampleOwnerListByProcessStepid(int processstepid, int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getTotalExampleOwnerListByStepid(processstepid, -2, exampleid);
    }

    public int recoverProcess(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        _workapp.changeExampleS(exampleid, 4);
        return exampleid;
    }

    public int recoverProcess(Map map)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int ownerid = NumberTool.convertMapKeyToInt(map, "ownerid", Integer.valueOf(-1)).intValue();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("stepbrief"), "");
        String _owneropinion = StringUtil.safeToString(map.get("owneropinion"), "");
        if(ownerid == -1)
        {
            String stepbrief = "";
            if(_brief.length() > a.getBreifMaxLength())
                stepbrief = _brief.substring(0, a.getBreifMaxLength());
            else
            if(_brief.length() > 0)
                stepbrief = _brief;
            if(stepbrief.length() > 0)
                if(examplestepid != 0)
                {
                    IWorkflowExampleStep step = _workapp.loadExampleStep(examplestepid);
                    if(step == null || step.size() == 0)
                        return 0;
                    if(step.getExampleId().intValue() != exampleid)
                        return -1;
                    step.setBrief(stepbrief);
                    _workapp.saveExampleStep(step);
                } else
                {
                    List steplist = _workapp.getExampleStepList(exampleid);
                    for(int i = 0; i < steplist.size(); i++)
                    {
                        Map stepmap = (Map)steplist.get(i);
                        int _stepstatus = NumberTool.convertMapKeyToInt(stepmap, "stepstatus", Integer.valueOf(0)).intValue();
                        if(_stepstatus != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_BEING.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_GENERATE.getEnumItemValue())
                            continue;
                        int _examplestepid = NumberTool.convertMapKeyToInt(stepmap, "examplestepid", Integer.valueOf(0)).intValue();
                        IWorkflowExampleStep step = _workapp.loadExampleStep(_examplestepid);
                        if(step != null && step.size() != 0 && step.getExampleId().intValue() == exampleid)
                        {
                            step.setBrief(stepbrief);
                            _workapp.saveExampleStep(step);
                        }
                    }

                }
            return recoverProcess(exampleid);
        } else
        {
            _workapp.changeExampleO(exampleid, WorkflowProcessStatus.WF_PROCESS_TERMINAT.getEnumItemValue());
            IWorkflowExample example = _workapp.loadExample(exampleid);
            IWorkflowExampleStep examplestep = _workapp.loadExampleStep(0);
            examplestep.setExampleId(Integer.valueOf(exampleid));
            examplestep.setProcessStepName(a.getRecoverStepName());
            examplestep.setProcessId(example.getProcessId());
            examplestep.setProcessStepId(Integer.valueOf(0));
            examplestep.setStatus(Integer.valueOf(WorkflowStepStatus.WF_STEP_RECOVER.getEnumItemValue()));
            examplestep.setStartTime(new Date());
            int examplestepid2 = _workapp.saveExampleStep(examplestep);
            IWorkflowExampleStepOwner exampleowner = _workapp.loadExampleStepOwner(0);
            exampleowner.setExampleId(Integer.valueOf(exampleid));
            exampleowner.setExampleStepId(Integer.valueOf(examplestepid2));
            exampleowner.setApproveTime(new Date());
            exampleowner.setApproveOpinion(_owneropinion);
            exampleowner.setOwnerId(Integer.valueOf(ownerid));
            exampleowner.setPreOwnerId(Integer.valueOf(0));
            exampleowner.setStatus(Integer.valueOf(WorkflowStepOwnerStatus.WF_OWNER_RECOVER.getEnumItemValue()));
            exampleowner.setValue(Integer.valueOf(1));
            _workapp.saveExampleStepOwner(exampleowner);
            _workapp.changeExample(exampleid, WorkflowProcessStatus.WF_PROCESS_RECOVER.getEnumItemValue());
            return exampleid;
        }
    }

    public int cancelProcess(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        _workapp.changeExampleS(exampleid, 5);
        return exampleid;
    }

    public int cancelProcess(Map map)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("stepbrief"), "");
        String stepbrief = "";
        if(_brief.length() > a.getBreifMaxLength())
            stepbrief = _brief.substring(0, a.getBreifMaxLength());
        else
        if(_brief.length() > 0)
            stepbrief = _brief;
        if(stepbrief.length() > 0)
            if(examplestepid != 0)
            {
                IWorkflowExampleStep step = _workapp.loadExampleStep(examplestepid);
                if(step == null || step.size() == 0)
                    return 0;
                if(step.getExampleId().intValue() != exampleid)
                    return -1;
                step.setBrief(stepbrief);
                _workapp.saveExampleStep(step);
            } else
            {
                List steplist = _workapp.getExampleStepList(exampleid);
                for(int i = 0; i < steplist.size(); i++)
                {
                    Map stepmap = (Map)steplist.get(i);
                    int _stepstatus = NumberTool.convertMapKeyToInt(stepmap, "stepstatus", Integer.valueOf(0)).intValue();
                    if(_stepstatus != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_BEING.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_GENERATE.getEnumItemValue())
                        continue;
                    int _examplestepid = NumberTool.convertMapKeyToInt(stepmap, "examplestepid", Integer.valueOf(0)).intValue();
                    IWorkflowExampleStep step = _workapp.loadExampleStep(_examplestepid);
                    if(step != null && step.size() != 0 && step.getExampleId().intValue() == exampleid)
                    {
                        step.setBrief(stepbrief);
                        _workapp.saveExampleStep(step);
                    }
                }

            }
        return cancelProcess(exampleid);
    }

    public int endProcess(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        _workapp.changeExampleS(exampleid, 3);
        return exampleid;
    }

    public int endProcess(Map map)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("stepbrief"), "");
        String stepbrief = "";
        if(_brief.length() > a.getBreifMaxLength())
            stepbrief = _brief.substring(0, a.getBreifMaxLength());
        else
        if(_brief.length() > 0)
            stepbrief = _brief;
        String _brief2 = StringUtil.safeToString(map.get("ownerbrief"), "");
        String ownerbrief = "";
        if(_brief2.length() > 150)
            ownerbrief = _brief2.substring(0, 150);
        else
        if(_brief2.length() > 0)
            ownerbrief = _brief2;
        if(stepbrief.length() > 0)
            if(examplestepid != 0)
            {
                IWorkflowExampleStep step = _workapp.loadExampleStep(examplestepid);
                if(step == null || step.size() == 0)
                    return 0;
                if(step.getExampleId().intValue() != exampleid)
                    return -1;
                step.setBrief(stepbrief);
                _workapp.saveExampleStep(step);
            } else
            {
                List steplist = _workapp.getExampleStepList(exampleid);
                for(int i = 0; i < steplist.size(); i++)
                {
                    Map stepmap = (Map)steplist.get(i);
                    int _stepstatus = NumberTool.convertMapKeyToInt(stepmap, "stepstatus", Integer.valueOf(0)).intValue();
                    if(_stepstatus != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_BEING.getEnumItemValue() && _stepstatus != WorkflowStepStatus.WF_STEP_GENERATE.getEnumItemValue())
                        continue;
                    int _examplestepid = NumberTool.convertMapKeyToInt(stepmap, "examplestepid", Integer.valueOf(0)).intValue();
                    IWorkflowExampleStep step = _workapp.loadExampleStep(_examplestepid);
                    if(step != null && step.size() != 0 && step.getExampleId().intValue() == exampleid)
                    {
                        step.setBrief(stepbrief);
                        _workapp.saveExampleStep(step);
                    }
                }

            }
        if(ownerbrief.length() > 0)
        {
            List ownerlist = _workapp.getOwnerListByExampleid(exampleid);
            for(int ii = 0; ii < ownerlist.size(); ii++)
            {
                Map ownermap = (Map)ownerlist.get(ii);
                int _ownerstatus = NumberTool.convertMapKeyToInt(ownermap, "ownerstatus", Integer.valueOf(0)).intValue();
                if(_ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue() && _ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_BEING.getEnumItemValue())
                    continue;
                int exampleownerid = NumberTool.convertMapKeyToInt(ownermap, "exampleownerid", Integer.valueOf(0)).intValue();
                IWorkflowExampleStepOwner owner = _workapp.loadExampleStepOwner(exampleownerid);
                if(owner != null && owner.size() != 0 && owner.getExampleId().intValue() == exampleid)
                {
                    owner.setApproveOpinion(ownerbrief);
                    _workapp.saveExampleStepOwner(owner);
                }
            }

        }
        return endProcess(exampleid);
    }

    public int getPointTypeByStepid(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _map = _workapp.getStepTempleteByStepid(examplestepid);
        int _pointtype = NumberTool.convertMapKeyToInt(_map, "pointtypeid", Integer.valueOf(0)).intValue();
        return _pointtype;
    }

    public List getTaskByOrgListAndType(List list, int type)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _map = new HashMap();
        StringBuffer _orgid = new StringBuffer();
        if(list.size() == 0)
            return new ArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            Employee _employee = (Employee)list.get(i);
            if(_employee == null)
                continue;
            if(_orgid.length() > 0)
                _orgid.append(",");
            _orgid.append(_employee.getId());
        }

        _map.put("orgid", _orgid.toString());
        _map.put("processtype", Integer.valueOf(type));
        _map.put("type", Integer.valueOf(0));
        return _workapp.getTaskByMap(_map);
    }

    public List getMyProcessListByProcessid(int orgid, int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample _example = _workapp.loadExample(exampleid);
        if(_example == null || _example.getProcessId() == null)
            return new ArrayList();
        else
            return _workapp.getMyProcessList(orgid, _example.getProcessId().intValue(), 0, "");
    }

    public List getMyProcessListBySyscode(int orgid, int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowExample _example = _workapp.loadExample(exampleid);
        if(_example == null)
            return new ArrayList();
        IWorkflowProcess _process = _workbase.loadProcess(_example.getProcessId().intValue());
        if(_process == null || _process.getProcessType() == null)
            return new ArrayList();
        else
            return _workapp.getMyProcessList(orgid, 0, 0, _process.getSysCode());
    }

    public List getMyProcessListByProcesstype(int orgid, int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowExample _example = _workapp.loadExample(exampleid);
        if(_example == null)
            return new ArrayList();
        IWorkflowProcess _process = _workbase.loadProcess(_example.getProcessId().intValue());
        if(_process == null || _process.getProcessType() == null)
            return new ArrayList();
        else
            return _workapp.getMyProcessList(orgid, 0, _process.getProcessType().intValue(), "");
    }

    public List getOwnerListByExampleid(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getOwnerListByExampleid(exampleid);
    }

    public List getHistorySourceListByOwner(int exampleownerid, Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStepOwner _stepowner = _workapp.loadExampleStepOwner(exampleownerid);
        if(_stepowner == null)
            return new ArrayList();
        int _examplestepid = NumberTool.safeToInteger(_stepowner.getExampleStepId(), Integer.valueOf(0)).intValue();
        int _orgid = NumberTool.safeToInteger(_stepowner.getOwnerId(), Integer.valueOf(0)).intValue();
        if(map.get("orgid") == null)
            map.put("orgid", Integer.valueOf(_orgid));
        return getHistorySourceListByStep(_examplestepid, map);
    }

    public List getHistorySourceListByStep(int examplestepid, Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _step = _workapp.loadExampleStep(examplestepid);
        if(_step == null)
        {
            return new ArrayList();
        } else
        {
            int _stepid = NumberTool.safeToInteger(_step.getProcessStepId(), Integer.valueOf(0)).intValue();
            return getHistorySourceListByProcessStep(_stepid, map);
        }
    }

    public List getHistorySourceListByProcessStep(int processstepid, Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map stephistorymap1 = _workapp.getHistoryStepMap(0);
        Map stephistorymap2 = _workapp.getHistoryStepMap(1);
        StringBuffer stepstring = new StringBuffer();
        int _stepid = processstepid;
        stepstring.append(_stepid);
        int _stepid1 = _stepid;
        do
        {
            if(_stepid1 <= 0)
                break;
            _stepid1 = NumberTool.convertMapKeyToInt(stephistorymap1, (new StringBuilder()).append("").append(_stepid1).toString(), Integer.valueOf(0)).intValue();
            if(_stepid1 > 0)
                stepstring.append(",").append(_stepid1);
        } while(true);
        int _stepid2 = _stepid;
        do
        {
            if(_stepid2 <= 0)
                break;
            _stepid2 = NumberTool.convertMapKeyToInt(stephistorymap2, (new StringBuilder()).append("").append(_stepid2).toString(), Integer.valueOf(0)).intValue();
            if(_stepid2 > 0)
                stepstring.append(",").append(_stepid2);
        } while(true);
        map.put("processstepid", stepstring.toString());
        List _list = _workapp.getHistorySourceList(map);
        return _list;
    }

    public IMetaDBQuery getSmartPhoneTask(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getModuleSPTask(map);
    }

    public List getDetailTaskListByStepid(int stepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowSupportService _worksup = WorkflowServiceFactory.getWorkflowSupportService();
        Map _map = new HashMap();
        _map.put("stepid", Integer.valueOf(stepid));
        IWorkflowExampleStep _step = _workapp.loadExampleStep(stepid);
        if(_step == null)
            return new ArrayList();
        int _exampleid = NumberTool.safeToInteger(_step.getExampleId(), Integer.valueOf(0)).intValue();
        IWorkflowExample _example = _workapp.loadExample(_exampleid);
        String _title = StringUtil.safeToString(_example.getBrief(), "");
        List _entrylist = _worksup.getExampleEntryList(_exampleid);
        String _objectname = "";
        int _objectid = 0;
        if(_entrylist.size() > 0)
        {
            Map _entrymap = (Map)_entrylist.get(0);
            _objectname = StringUtil.safeToString(_entrymap.get("sourcename"), "");
            _objectid = NumberTool.convertMapKeyToInt(_entrymap, "sourceid", Integer.valueOf(0)).intValue();
        }
        List _tasklist = _workapp.getDetailTaskList(_map);
        List _list = new ArrayList();
        for(int i = 0; i < _tasklist.size(); i++)
        {
            Map _taskmap = (Map)_tasklist.get(i);
            _taskmap.put("title", _title);
            _taskmap.put("objectname", _objectname);
            _taskmap.put("objectid", Integer.valueOf(_objectid));
            _list.add(_taskmap);
        }

        return _list;
    }

    public int getStepStageByProcessStepId(int stepid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcessStep _step = _workbase.loadProcessStep(stepid);
        if(_step == null)
        {
            return -1;
        } else
        {
            int _stepstage = NumberTool.safeToInteger(_step.getPointTypeId(), Integer.valueOf(0)).intValue();
            return _stepstage;
        }
    }

    public String getNowOwnerNamesByEntryList(List entrylist)
    {
        StringBuffer names = new StringBuffer();
        List examplelist = getProcessListByEntryList(entrylist);
        Map markmap = new HashMap();
        for(int i = 0; i < examplelist.size(); i++)
        {
            Map examplemap = (Map)examplelist.get(i);
            int exampleid = NumberTool.convertMapKeyToInt(examplemap, "exampleid", Integer.valueOf(0)).intValue();
            if(exampleid <= 0)
                continue;
            List ownerlist = getNowExampleOwnerListByExampleid(exampleid);
            for(int j = 0; j < ownerlist.size(); j++)
            {
                Map ownermap = (Map)ownerlist.get(j);
                int ownerid = NumberTool.convertMapKeyToInt(ownermap, "ownerid", Integer.valueOf(0)).intValue();
                if(ownerid <= 0)
                    continue;
                int ownermark = NumberTool.convertMapKeyToInt(markmap, (new StringBuilder()).append("").append(ownerid).toString(), Integer.valueOf(0)).intValue();
                if(ownermark != 0)
                    continue;
                String ownername = StringUtil.safeToString(ownermap.get("ownername"), "");
                if(ownername.length() > 0)
                {
                    if(names.length() > 0)
                        names.append(",");
                    names.append(ownername);
                }
                markmap.put((new StringBuilder()).append("").append(ownerid).toString(), Integer.valueOf(1));
            }

        }

        return names.toString();
    }

    public List getRecentOwnerListByExampleIdAndStageId(int exampleid, int stageid)
    {
        return getRecentOwnerListByExampleIdAndStageId(exampleid, new int[] {
            stageid
        });
    }

    public List getRecentOwnerListByExampleIdAndStageId(int exampleid, int stageid[])
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List resultlist = new ArrayList();
        List ownerlist = _workapp.getDetailOwnerListByExampleid(exampleid);
        for(int i = 0; i < ownerlist.size(); i++)
        {
            Map ownermap = (Map)ownerlist.get(i);
            int _stageid = NumberTool.convertMapKeyToInt(ownermap, "stageid", Integer.valueOf(0)).intValue();
            if(_stageid == 0)
                continue;
            int j;
            for(j = 0; j < stageid.length && _stageid != stageid[j]; j++);
            if(j == stageid.length)
                continue;
            if(ownermap.get("approvetime") != null)
            {
                Date _approvetime = (Date)ownermap.get("approvetime");
                ownermap.put("approvetime", TimeUtil.formatDate(_approvetime, "yyyy-MM-dd HH:mm:ss"));
            }
            resultlist.add(ownermap);
        }

        return resultlist;
    }

    public List getRecentOwnerListByExampleIdAndStageId(int exampleid, String stageid)
    {
        String stageArr[] = stageid.split(",");
        int stageidArr[] = new int[stageArr.length];
        for(int i = 0; i < stageArr.length; i++)
            stageidArr[i] = NumberTool.safeToInteger(stageArr[i], Integer.valueOf(0)).intValue();

        return getRecentOwnerListByExampleIdAndStageId(exampleid, stageidArr);
    }

    public List getLatelyRealHandleOnwerListByStageId(int exampleid, String stageids)
    {
        List resultlist = new ArrayList();
        List ownerlist = getRecentOwnerListByExampleIdAndStageId(exampleid, stageids);
        Map historymap = new HashMap();
        for(int i = ownerlist.size() - 1; i >= 0; i--)
        {
            Map ownermap = (Map)ownerlist.get(i);
            int approvestatus = NumberTool.convertMapKeyToInt(ownermap, "approvestatus", Integer.valueOf(0)).intValue();
            if(approvestatus == 0)
                continue;
            int _examplestepid = NumberTool.convertMapKeyToInt(ownermap, "examplestepid", Integer.valueOf(0)).intValue();
            int _processstepid = NumberTool.convertMapKeyToInt(ownermap, "processstepid", Integer.valueOf(0)).intValue();
            int _examplestepid2 = NumberTool.convertMapKeyToInt(historymap, (new StringBuilder()).append("").append(_processstepid).toString(), Integer.valueOf(0)).intValue();
            if(_examplestepid2 != 0 && _examplestepid2 != _examplestepid)
                continue;
            if(_examplestepid2 == 0)
                historymap.put((new StringBuilder()).append("").append(_processstepid).toString(), Integer.valueOf(_examplestepid));
            String ownername = StringUtil.safeToString(ownermap.get("ownername"), "");
            if(ownername.length() == 0)
            {
                int ownerid = NumberTool.convertMapKeyToInt(ownermap, "ownerid", Integer.valueOf(0)).intValue();
                if(ownerid == 0)
                {
                    WorkflowPublicString a = new WorkflowPublicString();
                    ownername = a.getSystemName();
                }
            }
            String preownername = StringUtil.safeToString(ownermap.get("preownername"), "");
            if(preownername.length() == 0)
                ownermap.put("usename", ownername);
            else
                ownermap.put("usename", (new StringBuilder()).append(ownername).append("(").append(preownername).append(")").toString());
            resultlist.add(ownermap);
        }

        return resultlist;
    }

    public boolean isSubExampleFinish(int exampleid, int examplestepid, int exampleownerid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map map = new HashMap();
        map.put("exampleid", Integer.valueOf(exampleid));
        map.put("examplestepid", Integer.valueOf(examplestepid));
        map.put("exampleownerid", Integer.valueOf(exampleownerid));
        map.put("type", Integer.valueOf(1));
        List list = _workapp.getRelationList(map);
        return list.size() <= 0;
    }

    public boolean isSubExample(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map map = new HashMap();
        map.put("subexampleid", Integer.valueOf(exampleid));
        List list = _workapp.getRelationList(map);
        return list.size() > 0;
    }

    public int getExampleStarter(int exampleid)
    {
        IWorkflowAppService workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample example = workapp.loadExample(exampleid);
        int starterid = NumberTool.safeToInteger(example.getStarterId(), Integer.valueOf(0)).intValue();
        return starterid;
    }
}
