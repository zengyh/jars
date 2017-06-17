// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowTranslateServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class WorkflowTranslateServiceImpl extends MetaDBContextSupport
    implements IWorkflowTranslateService
{

    public WorkflowTranslateServiceImpl()
    {
    }

    public Map saveHistoryProcessData(Map map)
    {
        Map _map = new HashMap();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(0);
        String _name = StringUtil.safeToString(map.get("name"), "");
        _process.setName(_name);
        int _sort = NumberTool.convertMapKeyToInt(map, "sort", Integer.valueOf(0)).intValue();
        _process.setSort(Integer.valueOf(_sort));
        String _processcode = StringUtil.safeToString(map.get("processcode"), "");
        _process.setProcessCode(_processcode);
        int _processversion = NumberTool.convertMapKeyToInt(map, "processversion", Integer.valueOf(0)).intValue();
        _process.setProcessVersion(Integer.valueOf(_processversion));
        int _processtype = NumberTool.convertMapKeyToInt(map, "processtype", Integer.valueOf(0)).intValue();
        _process.setProcessType(Integer.valueOf(_processtype));
        String _processbrief = StringUtil.safeToString(map.get("processbrief"), "");
        _process.setProcessBrief(_processbrief);
        String _specialmark = StringUtil.safeToString(map.get("specialmark"), "");
        _process.setSpecialMark(_specialmark);
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        _process.setStatus(Integer.valueOf(_status));
        int _processid = _workbase.saveProcess(_process);
        String _syscode = StringUtil.safeToString(map.get("syscode"), "");
        int _sysversion = NumberTool.convertMapKeyToInt(map, "sysversion", Integer.valueOf(1)).intValue();
        int _intcode = NumberTool.safeToInteger(_syscode, Integer.valueOf(0)).intValue();
        if(_intcode == 0 || _intcode > _processid)
        {
            _process.setSysCode((new StringBuilder()).append("").append(_processid).toString());
            _process.setSysVersion(Integer.valueOf(1));
            _workbase.saveProcess(_process);
        } else
        {
            _process.setSysCode(_syscode);
            _process.setSysVersion(Integer.valueOf(_sysversion));
            _workbase.saveProcess(_process);
        }
        List _steplist = ((List) (map.get("steplist") != null ? (List)map.get("steplist") : ((List) (new ArrayList()))));
        List _returnlist = new ArrayList();
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _returnmap = new HashMap();
            Map _stepmap = (Map)_steplist.get(i);
            IWorkflowProcessStep _step = _workbase.loadProcessStep(0);
            _step.setProcessId(Integer.valueOf(_processid));
            String _name2 = StringUtil.safeToString(_stepmap.get("name"), "");
            _step.setName(_name2);
            int _sort2 = NumberTool.convertMapKeyToInt(_stepmap, "sort", Integer.valueOf(0)).intValue();
            _step.setSort(Integer.valueOf(_sort2));
            int _status2 = NumberTool.convertMapKeyToInt(_stepmap, "status", Integer.valueOf(0)).intValue();
            _step.setStatus(Integer.valueOf(_status2));
            int _steptypeid = NumberTool.convertMapKeyToInt(_stepmap, "steptypeid", Integer.valueOf(0)).intValue();
            _step.setStepTypeId(Integer.valueOf(_steptypeid));
            String _steptypeads = StringUtil.safeToString(_stepmap.get("steptypeads"), "");
            _step.setStepTypeAds(_steptypeads);
            int _pointtypeid = NumberTool.convertMapKeyToInt(_stepmap, "pointtypeid", Integer.valueOf(0)).intValue();
            _step.setPointTypeId(Integer.valueOf(_pointtypeid));
            String _condition = StringUtil.safeToString(_stepmap.get("condition"), "");
            _step.setCondition(_condition);
            int _urlmark = NumberTool.convertMapKeyToInt(_stepmap, "urlmark", Integer.valueOf(0)).intValue();
            _step.setUrlMark(Integer.valueOf(_urlmark));
            String _actionurl = StringUtil.safeToString(_stepmap.get("actionurl"), "");
            _step.setActionUrl(_actionurl);
            String _showurl = StringUtil.safeToString(_stepmap.get("showurl"), "");
            _step.setShowUrl(_showurl);
            int _stepid = _workbase.saveProcessStep(_step);
            _returnmap.put("stepid", Integer.valueOf(_stepid));
            _returnlist.add(_returnmap);
        }

        _map.put("processid", Integer.valueOf(_processid));
        _map.put("steplist", _returnlist);
        return _map;
    }

    public int exportProcess(int processid, int type)
    {
        return 0;
    }

    public int importProcess(String filename)
    {
        return 0;
    }

    public int saveHistoryExampleData(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample _example = _workapp.loadExample(0);
        String _processname = StringUtil.safeToString(map.get("processname"), "");
        _example.setProcessName(_processname);
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        _example.setProcessId(Integer.valueOf(_processid));
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        _example.setStatus(Integer.valueOf(_status));
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        if(_brief.length() == 0)
            _brief = "\u65E0\u63CF\u8FF0";
        if(_brief.length() > 66)
            _example.setBrief(_brief.substring(0, 66));
        else
            _example.setBrief(_brief);
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(0)).intValue();
        _example.setStarterId(Integer.valueOf(_starterid));
        Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : new Date();
        _example.setStartTime(_starttime);
        Date _endtime = map.get("endtime") != null ? (Date)map.get("endtime") : new Date();
        _example.setEndTime(_endtime);
        String _starterads = StringUtil.safeToString(map.get("starterads"), "");
        _example.setStarterAds(_starterads);
        String _datafrom = StringUtil.safeToString(map.get("datafrom"), "");
        _example.setDataFrom(_datafrom);
        int _exampleid = _workapp.saveExample(_example);
        List _entrylist = ((List) (map.get("entrylist") != null ? (List)map.get("entrylist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _entrylist.size(); i++)
        {
            Map _entrymap = (Map)_entrylist.get(i);
            IWorkflowExampleEntry _entry = _workapp.loadExampleEntry(0);
            _entry.setExampleId(Integer.valueOf(_exampleid));
            int _status2 = NumberTool.convertMapKeyToInt(_entrymap, "status", Integer.valueOf(0)).intValue();
            _entry.setStatus(Integer.valueOf(_status2));
            String _sourcename = StringUtil.safeToString(_entrymap.get("sourcename"), "");
            _entry.setSourceName(_sourcename);
            int _sourceid = NumberTool.convertMapKeyToInt(_entrymap, "sourceid", Integer.valueOf(0)).intValue();
            _entry.setSourceId(Integer.valueOf(_sourceid));
            Date _changetime = map.get("changetime") != null ? (Date)map.get("changetime") : new Date();
            _entry.setChangeTime(_changetime);
            _workapp.saveExampleEntry(_entry);
        }

        List _ownerlist2 = ((List) (map.get("ownerlist") != null ? (List)map.get("ownerlist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _ownerlist2.size(); i++)
        {
            Map _ownermap = (Map)_ownerlist2.get(i);
            IWorkflowExampleStepOwner _owner = _workapp.loadExampleStepOwner(0);
            _owner.setExampleId(Integer.valueOf(_exampleid));
            _owner.setExampleStepId(Integer.valueOf(0));
            int _ownerid = NumberTool.convertMapKeyToInt(_ownermap, "ownerid", Integer.valueOf(0)).intValue();
            _owner.setOwnerId(Integer.valueOf(_ownerid));
            int _preownerid = NumberTool.convertMapKeyToInt(_ownermap, "preownerid", Integer.valueOf(0)).intValue();
            _owner.setPreOwnerId(Integer.valueOf(_preownerid));
            int _status3 = NumberTool.convertMapKeyToInt(_ownermap, "status", Integer.valueOf(0)).intValue();
            _owner.setStatus(Integer.valueOf(_status3));
            Date _starttime3 = _ownermap.get("starttime") != null ? (Date)_ownermap.get("starttime") : new Date();
            _owner.setStartTime(_starttime3);
            Date _approvetime = _ownermap.get("approvetime") != null ? (Date)_ownermap.get("approvetime") : new Date();
            _owner.setApproveTime(_approvetime);
            int _approvestatus = NumberTool.convertMapKeyToInt(_ownermap, "approvestatus", Integer.valueOf(0)).intValue();
            _owner.setApproveStatus(Integer.valueOf(_approvestatus));
            String _approveopinion = StringUtil.safeToString(_ownermap.get("approveopinion"), "");
            _owner.setApproveOpinion(_approveopinion);
            String _approverbrief = StringUtil.safeToString(_ownermap.get("approverbrief"), "");
            _owner.setApproveBrief(_approverbrief);
            int _value = NumberTool.convertMapKeyToInt(_ownermap, "value", Integer.valueOf(1)).intValue();
            _owner.setValue(Integer.valueOf(_value));
            _workapp.saveExampleStepOwner(_owner);
        }

        List _steplist = ((List) (map.get("steplist") != null ? (List)map.get("steplist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            IWorkflowExampleStep _step = _workapp.loadExampleStep(0);
            _step.setExampleId(Integer.valueOf(_exampleid));
            String _processstepname = StringUtil.safeToString(_stepmap.get("processstepname"), "");
            _step.setProcessStepName(_processstepname);
            int _processstepid = NumberTool.convertMapKeyToInt(_stepmap, "processstepid", Integer.valueOf(0)).intValue();
            _step.setProcessStepId(Integer.valueOf(_processstepid));
            int _processid2 = NumberTool.convertMapKeyToInt(_stepmap, "processid", Integer.valueOf(0)).intValue();
            if(_processid2 == 0)
                _processid2 = _processid;
            _step.setProcessId(Integer.valueOf(_processid2));
            int _status2 = NumberTool.convertMapKeyToInt(_stepmap, "status", Integer.valueOf(0)).intValue();
            _step.setStatus(Integer.valueOf(_status2));
            String _brief2 = StringUtil.safeToString(_stepmap.get("brief"), "");
            _step.setBrief(_brief2);
            String _stepbeigin = StringUtil.safeToString(_stepmap.get("stepbeigin"), "");
            _step.setStepBeigin(_stepbeigin);
            int _stepcondition = NumberTool.convertMapKeyToInt(_stepmap, "stepcondition", Integer.valueOf(0)).intValue();
            _step.setStepCondition(Integer.valueOf(_stepcondition));
            int _steptype = NumberTool.convertMapKeyToInt(_stepmap, "steptype", Integer.valueOf(0)).intValue();
            _step.setStepType(Integer.valueOf(_steptype));
            String _actionurl = StringUtil.safeToString(_stepmap.get("actionurl"), "");
            _step.setActionUrl(_actionurl);
            String _showurl = StringUtil.safeToString(_stepmap.get("showurl"), "");
            _step.setShowUrl(_showurl);
            int _urlmark = NumberTool.convertMapKeyToInt(_stepmap, "urlmark", Integer.valueOf(0)).intValue();
            _step.setUrlMark(Integer.valueOf(_urlmark));
            Date _starttime2 = _stepmap.get("starttime") != null ? (Date)_stepmap.get("starttime") : new Date();
            _step.setStartTime(_starttime2);
            if(_status2 != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue())
            {
                Date _endtime2 = _stepmap.get("endtime") != null ? (Date)_stepmap.get("endtime") : new Date();
                _step.setEndTime(_endtime2);
            }
            String _stepend = StringUtil.safeToString(_stepmap.get("stepend"), "");
            List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            if(_status2 == WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue())
                if(_steptype == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
                    _stepend = ";";
                else
                    _stepend = getStepEnd(_stepend, _ownerlist);
            _step.setStepEnd(_stepend);
            int _stepid = _workapp.saveExampleStep(_step);
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                IWorkflowExampleStepOwner _owner = _workapp.loadExampleStepOwner(0);
                _owner.setExampleId(Integer.valueOf(_exampleid));
                _owner.setExampleStepId(Integer.valueOf(_stepid));
                int _ownerid = NumberTool.convertMapKeyToInt(_ownermap, "ownerid", Integer.valueOf(0)).intValue();
                _owner.setOwnerId(Integer.valueOf(_ownerid));
                int _preownerid = NumberTool.convertMapKeyToInt(_ownermap, "preownerid", Integer.valueOf(0)).intValue();
                _owner.setPreOwnerId(Integer.valueOf(_preownerid));
                int _status3 = NumberTool.convertMapKeyToInt(_ownermap, "status", Integer.valueOf(0)).intValue();
                _owner.setStatus(Integer.valueOf(_status3));
                Date _starttime3 = _ownermap.get("starttime") != null ? (Date)_ownermap.get("starttime") : new Date();
                _owner.setStartTime(_starttime3);
                if(_status3 != WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue() && _status3 != WorkflowStepOwnerStatus.WF_OWNER_BEING.getEnumItemValue())
                {
                    Date _approvetime = _ownermap.get("approvetime") != null ? (Date)_ownermap.get("approvetime") : new Date();
                    _owner.setApproveTime(_approvetime);
                }
                int _approvestatus = NumberTool.convertMapKeyToInt(_ownermap, "approvestatus", Integer.valueOf(0)).intValue();
                _owner.setApproveStatus(Integer.valueOf(_approvestatus));
                String _approveopinion = StringUtil.safeToString(_ownermap.get("approveopinion"), "");
                _owner.setApproveOpinion(_approveopinion);
                String _approverbrief = StringUtil.safeToString(_ownermap.get("approverbrief"), "");
                _owner.setApproveBrief(_approverbrief);
                int _value = NumberTool.convertMapKeyToInt(_ownermap, "value", Integer.valueOf(1)).intValue();
                _owner.setValue(Integer.valueOf(_value));
                _workapp.saveExampleStepOwner(_owner);
            }

        }

        return _exampleid;
    }

    private String getStepEnd(String stepend, List list)
    {
        StringBuffer _stepend = new StringBuffer();
        int _totalnum = 0;
        int _totalvalue = 0;
        int _approveyes = 0;
        int _approveno = 0;
        int _numyes = 0;
        int _numno = 0;
        for(int i = 0; i < list.size(); i++)
        {
            Map _map = (Map)list.get(i);
            int _value = NumberTool.convertMapKeyToInt(_map, "value", Integer.valueOf(1)).intValue();
            int _approvestatus = NumberTool.convertMapKeyToInt(_map, "approveopinion", Integer.valueOf(0)).intValue();
            if(_approvestatus == WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue())
            {
                _numyes++;
                _approveyes += _value;
            } else
            if(_approvestatus == WorkflowStepCondition.WF_STEP_REJECT.getEnumItemValue())
            {
                _numno++;
                _approveno += _value;
            }
            _totalvalue += _value;
            _totalnum++;
        }

        String _stependArr[] = stepend.split(" ");
        if(_stependArr.length == 1)
            _stepend.append(_totalnum).append(" ").append(_totalvalue).append(";1 0 0,2 0 0;1 2 1,2 2 1;");
        else
        if(_stependArr.length == 2)
            _stepend.append(_stependArr[0]).append(";").append(_stependArr[1]).append(";");
        else
        if(_stependArr.length == 3)
        {
            _stepend.append(_totalnum).append(" ").append(_totalvalue).append(";");
            _stepend.append("1 ").append(_numyes).append(" ").append(_approveyes).append(",2 ").append(_numno).append(" ").append(_approveno).append(";");
            int _judgetype = NumberTool.safeToInteger(_stependArr[1], Integer.valueOf(0)).intValue();
            double _judgenum = NumberTool.safeToDouble(_stependArr[2], Double.valueOf(0.0D)).doubleValue();
            if(_judgetype == 1)
                _stepend.append("1 ").append(_judgetype).append(" ").append(_judgenum).append(",2 ").append(_judgetype).append(" ").append(100.09999999999999D - _judgenum).append(";");
            else
            if(_judgetype == 2)
                _stepend.append("1 ").append(_judgetype).append(" ").append(_judgenum).append(",2 ").append(_judgetype).append(" ").append(((double)_totalnum + 0.10000000000000001D) - _judgenum).append(";");
            else
            if(_judgetype == 3)
                _stepend.append("1 ").append(_judgetype).append(" ").append(_judgenum).append(",2 ").append(_judgetype).append(" ").append(100.09999999999999D - _judgenum).append(";");
            else
            if(_judgetype == 4)
                _stepend.append("1 ").append(_judgetype).append(" ").append(_judgenum).append(",2 ").append(_judgetype).append(" ").append(((double)_totalvalue + 0.10000000000000001D) - _judgenum).append(";");
        }
        return _stepend.toString();
    }
}
