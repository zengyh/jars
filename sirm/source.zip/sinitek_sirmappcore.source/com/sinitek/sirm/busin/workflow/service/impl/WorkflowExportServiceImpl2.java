// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExportServiceImpl2.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class WorkflowExportServiceImpl2 extends MetaDBContextSupport
    implements IWorkflowExportService
{

    public WorkflowExportServiceImpl2()
    {
    }

    public Map getProcessTotal(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        WorkflowPublicString a = new WorkflowPublicString();
        Map _paramap = _workbase.getSingleParaMap();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        _paramap.put("0", _process.getProcessType());
        Map _prcocessmap = changeProcessToMap(_process, _paramap);
        List _steplist = new ArrayList();
        List _processsteplist = _workbase.getSingleStepList(processid);
        for(int i = 0; i < _processsteplist.size(); i++)
        {
            Map _processstepmap = (Map)_processsteplist.get(i);
            int _processstepid = NumberTool.convertMapKeyToInt(_processstepmap, "processstepid", Integer.valueOf(a.getMoveNullInt())).intValue();
            IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_processstepid);
            Map _stepmap = changeProcessStepToMap(_processstep, _paramap);
            _steplist.add(_stepmap);
        }

        _prcocessmap.put("steplist", _steplist);
        return _prcocessmap;
    }

    private Map changeProcessListToMap(IWorkflowProcessList processlist)
    {
        Map _map = new HashMap();
        int _key = NumberTool.safeToInteger(processlist.getKey(), Integer.valueOf(0)).intValue();
        _map.put("key", Integer.valueOf(_key));
        String _value = StringUtil.safeToString(processlist.getValue(), "");
        _map.put("value", _value);
        int _sort = NumberTool.safeToInteger(processlist.getSort(), Integer.valueOf(0)).intValue();
        _map.put("sort", Integer.valueOf(_sort));
        String _name = StringUtil.safeToString(processlist.getName(), "");
        _map.put("name", _name);
        String _valueads = StringUtil.safeToString(processlist.getValueAds(), "");
        _map.put("valueads", _valueads);
        int _type = NumberTool.safeToInteger(processlist.getType(), Integer.valueOf(0)).intValue();
        _map.put("type", Integer.valueOf(_type));
        return _map;
    }

    private Map changeProcessOwnerLinkToMap(IWorkflowProcessOwnerLink ownerlink, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        Map _map = new HashMap();
        String _ownerstarter = StringUtil.safeToString(ownerlink.getOwnerStarter(), "");
        _map.put("ownerstarter", _ownerstarter);
        String _ownerender = StringUtil.safeToString(ownerlink.getOwnerEnder(), "");
        _map.put("ownerender", _ownerender);
        int _sort = NumberTool.safeToInteger(ownerlink.getSort(), Integer.valueOf(0)).intValue();
        _map.put("sort", Integer.valueOf(_sort));
        int _linkroot = NumberTool.safeToInteger(ownerlink.getLinkRoot(), Integer.valueOf(0)).intValue();
        _map.put("linkroot", Integer.valueOf(_linkroot));
        int _linkleaf = NumberTool.safeToInteger(ownerlink.getLinkLeaf(), Integer.valueOf(0)).intValue();
        _map.put("linkleaf", Integer.valueOf(_linkleaf));
        return _map;
    }

    private Map changeProcessStepOwnerToMap(IWorkflowProcessOwner stepowner, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        Map _map = new HashMap();
        int _orgtype = NumberTool.safeToInteger(stepowner.getOrgType(), Integer.valueOf(0)).intValue();
        _map.put("orgtype", Integer.valueOf(_orgtype));
        int _orgid = NumberTool.safeToInteger(stepowner.getOrgId(), Integer.valueOf(0)).intValue();
        _map.put("orgid", Integer.valueOf(_orgid));
        int _value;
        if(_orgtype == -1)
        {
            _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("StepDealer").append(_orgid).toString(), Integer.valueOf(0)).intValue();
            if(_value == 0)
                _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("StepDealer").append(_orgid).toString(), Integer.valueOf(0)).intValue();
            IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
            Map _map2 = changeProcessListToMap(_processlist);
            _map.put("orgtypemap", _map2);
        }
        _value = NumberTool.safeToInteger(stepowner.getValue(), Integer.valueOf(1)).intValue();
        _map.put("value", Integer.valueOf(_value));
        int _ownergoto = NumberTool.safeToInteger(stepowner.getOwnerGoto(), Integer.valueOf(0)).intValue();
        _map.put("ownergoto", Integer.valueOf(_ownergoto));
        int _ownergotoid = NumberTool.safeToInteger(stepowner.getOwnerGotoId(), Integer.valueOf(0)).intValue();
        _map.put("ownergotoid", Integer.valueOf(_ownergotoid));
        return _map;
    }

    private Map changeProcessStepLinkIfToMap(IWorkflowProcessStepLinkIf linkif, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _map = new HashMap();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        int _iftype = NumberTool.safeToInteger(linkif.getIfType(), Integer.valueOf(0)).intValue();
        _map.put("iftype", Integer.valueOf(_iftype));
        if(_iftype >= 0x186a0)
        {
            int _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("LinkIfType").append(_iftype).toString(), Integer.valueOf(0)).intValue();
            if(_value == 0)
                _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("LinkIfType").append(_iftype).toString(), Integer.valueOf(0)).intValue();
            IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
            Map _map2 = changeProcessListToMap(_processlist);
            _map.put("iftypemap", _map2);
        }
        int _ifand = NumberTool.safeToInteger(linkif.getIfAnd(), Integer.valueOf(0)).intValue();
        _map.put("ifand", Integer.valueOf(_ifand));
        String _ifads = StringUtil.safeToString(linkif.getIfAds(), "");
        _map.put("ifads", _ifads);
        return _map;
    }

    private Map changeProcessStepLinkDoToMap(IWorkflowProcessStepLinkDo linkdo, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        Map _map = new HashMap();
        int _dotype = NumberTool.safeToInteger(linkdo.getDoType(), Integer.valueOf(0)).intValue();
        _map.put("dotype", Integer.valueOf(_dotype));
        if(_dotype > 0)
        {
            int _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("SpecialTask").append(_dotype).toString(), Integer.valueOf(0)).intValue();
            if(_value == 0)
                _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("SpecialTask").append(_dotype).toString(), Integer.valueOf(0)).intValue();
            IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
            Map _map2 = changeProcessListToMap(_processlist);
            _map.put("dotypemap", _map2);
        }
        int _domark = NumberTool.safeToInteger(linkdo.getDoMark(), Integer.valueOf(0)).intValue();
        _map.put("domark", Integer.valueOf(_domark));
        String _doads = StringUtil.safeToString(linkdo.getDoAds(), "");
        _map.put("doads", _doads);
        return _map;
    }

    private Map changeProcessStepLinkToMap(IWorkflowProcessStepLink steplink, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        Map _map = new HashMap();
        int _objid = NumberTool.safeToInteger(Integer.valueOf(steplink.getId()), Integer.valueOf(0)).intValue();
        int _sort = NumberTool.safeToInteger(steplink.getSort(), Integer.valueOf(0)).intValue();
        _map.put("sort", Integer.valueOf(_sort));
        int _aftstepid = NumberTool.safeToInteger(steplink.getAftStepId(), Integer.valueOf(0)).intValue();
        _map.put("aftstepid", Integer.valueOf(_aftstepid));
        int _prestepid = NumberTool.safeToInteger(steplink.getPreStepId(), Integer.valueOf(0)).intValue();
        _map.put("prestepid", Integer.valueOf(_prestepid));
        List _iflist = new ArrayList();
        List _linkiflist = _workbase.getSingleLinkIfList(_objid);
        for(int i = 0; i < _linkiflist.size(); i++)
        {
            Map _linkifmap = (Map)_linkiflist.get(i);
            int _linkifid = NumberTool.convertMapKeyToInt(_linkifmap, "linkifid", Integer.valueOf(0)).intValue();
            IWorkflowProcessStepLinkIf _linkif = _workbase.loadProcessStepLinkIf(_linkifid);
            Map _ifmap = changeProcessStepLinkIfToMap(_linkif, paramap);
            _iflist.add(_ifmap);
        }

        _map.put("iflist", _iflist);
        List _dolist = new ArrayList();
        List _linkdolist = _workbase.getSingleLinkDoList(_objid);
        for(int i = 0; i < _linkdolist.size(); i++)
        {
            Map _linkdomap = (Map)_linkdolist.get(i);
            int _linkdoid = NumberTool.convertMapKeyToInt(_linkdomap, "linkdoid", Integer.valueOf(0)).intValue();
            IWorkflowProcessStepLinkDo _linkdo = _workbase.loadProcessStepLinkDo(_linkdoid);
            Map _domap = changeProcessStepLinkDoToMap(_linkdo, paramap);
            _dolist.add(_domap);
        }

        _map.put("dolist", _dolist);
        return _map;
    }

    private Map changeProcessStepToMap(IWorkflowProcessStep processstep, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        WorkflowPublicString a = new WorkflowPublicString();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "0", Integer.valueOf(0)).intValue();
        Map _map = new HashMap();
        int _objid = NumberTool.safeToInteger(Integer.valueOf(processstep.getId()), Integer.valueOf(a.getMoveNullInt())).intValue();
        _map.put("objid", Integer.valueOf(_objid));
        String _name = StringUtil.safeToString(processstep.getName(), a.getMoveNullString());
        _map.put("name", _name);
        int _steptype = NumberTool.safeToInteger(processstep.getStepTypeId(), Integer.valueOf(a.getMoveNullInt())).intValue();
        _map.put("steptype", Integer.valueOf(_steptype));
        String _steptypeads = StringUtil.safeToString(processstep.getStepTypeAds(), "");
        _map.put("steptypeads", _steptypeads);
        String _steptypeadsArr[] = _steptypeads.split(" ");
        if(_steptypeadsArr.length == 2)
        {
            int _steptypeadsid = NumberTool.safeToInteger(_steptypeadsArr[1], Integer.valueOf(0)).intValue();
            if(_steptypeadsid != 0)
            {
                int _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("ResultDeal").append(_steptypeadsid).toString(), Integer.valueOf(0)).intValue();
                if(_value == 0)
                    _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("ResultDeal").append(_steptypeadsid).toString(), Integer.valueOf(0)).intValue();
                IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
                Map _map2 = changeProcessListToMap(_processlist);
                _map.put("steptypemap", _map2);
            }
        }
        String _condition = StringUtil.safeToString(processstep.getCondition(), "");
        _map.put("condition", _condition);
        String _conditionArr[] = _condition.split(";");
        if(_conditionArr.length == 6)
        {
            int _conditionid = NumberTool.safeToInteger(_conditionArr[5], Integer.valueOf(0)).intValue();
            if(_conditionid != 0)
            {
                int _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("StepDealer").append(_conditionid).toString(), Integer.valueOf(0)).intValue();
                if(_value == 0)
                    _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("StepDealer").append(_conditionid).toString(), Integer.valueOf(0)).intValue();
                IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
                Map _map2 = changeProcessListToMap(_processlist);
                _map.put("conditionmap", _map2);
            }
        }
        int _sort = NumberTool.safeToInteger(processstep.getSort(), Integer.valueOf(0)).intValue();
        _map.put("sort", Integer.valueOf(_sort));
        int _pointtype = NumberTool.safeToInteger(processstep.getPointTypeId(), Integer.valueOf(0)).intValue();
        _map.put("pointtype", Integer.valueOf(_pointtype));
        if(_pointtype != 0)
        {
            int _value = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append(_processtype).append("StepStage").append(_pointtype).toString(), Integer.valueOf(0)).intValue();
            IWorkflowProcessList _processlist = _workbase.loadProcessList(_value);
            Map _map2 = changeProcessListToMap(_processlist);
            _map.put("pointtypemap", _map2);
        }
        int _urlmark = NumberTool.safeToInteger(processstep.getUrlMark(), Integer.valueOf(0)).intValue();
        IWorkflowProcessUrl _processurl = _workbase.loadProcessUrl(_urlmark);
        String _urlname = StringUtil.safeToString(_processurl.getName(), a.getMoveNullString());
        _map.put("urlname", _urlname);
        String _actionurl = StringUtil.safeToString(processstep.getActionUrl(), StringUtil.safeToString(_processurl.getActionUrl(), ""));
        _map.put("actionurl", _actionurl);
        String _showurl = StringUtil.safeToString(processstep.getShowUrl(), StringUtil.safeToString(_processurl.getShowUrl(), ""));
        _map.put("showurl", _showurl);
        List _linklist = new ArrayList();
        List _steplinklist = _workbase.getSingleLinkList(_objid);
        for(int i = 0; i < _steplinklist.size(); i++)
        {
            Map _steplinkmap = (Map)_steplinklist.get(i);
            int _steplinkid = NumberTool.convertMapKeyToInt(_steplinkmap, "steplinkid", Integer.valueOf(0)).intValue();
            IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(_steplinkid);
            Map _linkmap = changeProcessStepLinkToMap(_steplink, paramap);
            _linklist.add(_linkmap);
        }

        _map.put("linklist", _linklist);
        List _ownerlist = new ArrayList();
        List _stepownerlist = _workbase.getSingleOwnerList(_objid);
        for(int i = 0; i < _stepownerlist.size(); i++)
        {
            Map _stepownermap = (Map)_stepownerlist.get(i);
            int _stepownerid = NumberTool.convertMapKeyToInt(_stepownermap, "stepownerid", Integer.valueOf(0)).intValue();
            IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(_stepownerid);
            Map _ownermap = changeProcessStepOwnerToMap(_stepowner, paramap);
            _ownerlist.add(_ownermap);
        }

        _map.put("ownerlist", _ownerlist);
        List _ownerlinklist = new ArrayList();
        List _stepownerlinklist = _workbase.getSingleOwnerLinkList(_objid);
        for(int i = 0; i < _stepownerlinklist.size(); i++)
        {
            Map _stepownerlinkmap = (Map)_stepownerlinklist.get(i);
            int _stepownerlinkid = NumberTool.convertMapKeyToInt(_stepownerlinkmap, "ownerlinkid", Integer.valueOf(0)).intValue();
            IWorkflowProcessOwnerLink _ownerlink = _workbase.loadProcessOwnerLink(_stepownerlinkid);
            Map _ownerlinkmap = changeProcessOwnerLinkToMap(_ownerlink, paramap);
            _ownerlinklist.add(_ownerlinkmap);
        }

        _map.put("ownerlinklist", _ownerlinklist);
        return _map;
    }

    private Map changeProcessToMap(IWorkflowProcess process, Map paramap)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        Map _map = new HashMap();
        String _name = StringUtil.safeToString(process.getName(), a.getMoveNullString());
        _map.put("name", _name);
        String _processcode = StringUtil.safeToString(process.getProcessCode(), "");
        _map.put("processcode", _processcode);
        int _processtype = NumberTool.safeToInteger(process.getProcessType(), Integer.valueOf(a.getMoveNullInt())).intValue();
        _map.put("processtype", Integer.valueOf(_processtype));
        String _processbrief = StringUtil.safeToString(process.getProcessBrief(), "");
        _map.put("processbrief", _processbrief);
        return _map;
    }

    public int putProcessTotal(Map map)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        WorkflowPublicString a = new WorkflowPublicString();
        Map _paramap = new HashMap();
        IWorkflowProcess _process = _workbase.loadProcess(0);
        String _name = StringUtil.safeToString(map.get("name"), a.getMoveNullString());
        int _processtype = NumberTool.safeToInteger(map.get("processtype"), Integer.valueOf(a.getMoveNullInt())).intValue();
        if(_name == a.getMoveNullString() || _processtype == a.getMoveNullInt())
            return 0;
        if(_workbase.testProcessNameUsed(0, _name))
        {
            _process.setName(_name);
        } else
        {
            int mark;
            for(mark = 1; !_workbase.testProcessNameUsed(0, (new StringBuilder()).append(_name).append(mark).toString()); mark++);
            _process.setName((new StringBuilder()).append(_name).append(mark).toString());
        }
        _process.setProcessType(Integer.valueOf(_processtype));
        String _processcode = StringUtil.safeToString(map.get("processcode"), "");
        _process.setProcessCode(_processcode);
        String _processbrief = StringUtil.safeToString(map.get("processbrief"), "");
        _process.setProcessBrief(_processbrief);
        _process.setSysVersion(Integer.valueOf(1));
        _process.setStatus(Integer.valueOf(1));
        _process.setSpecialMark("00000");
        _workbase.saveProcess(_process);
        if(_process.getSysCode() == null || _process.getSysCode().length() == 0)
        {
            _process.setSysCode((new StringBuilder()).append("").append(_process.getId()).toString());
            _workbase.saveProcess(_process);
        }
        _paramap.put("processid", Integer.valueOf(_process.getId()));
        _paramap.put("processtype", _process.getProcessType());
        List _steplist = ((List) (map.get("steplist") != null ? (List)map.get("steplist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _oldid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            int _nowid = keepProcessStep(_stepmap, _paramap);
            _paramap.put((new StringBuilder()).append("step").append(_oldid).toString(), Integer.valueOf(_nowid));
        }

        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            _paramap.put("stepid", Integer.valueOf(_stepid));
            List _linklist = ((List) (_stepmap.get("linklist") != null ? (List)_stepmap.get("linklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _linklist.size(); j++)
            {
                Map _linkmap = (Map)_linklist.get(j);
                keepProcessLink(_linkmap, _paramap);
            }

            List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                keepProcessOwner(_ownermap, _paramap);
            }

            List _ownerlinklist = ((List) (_stepmap.get("ownerlinklist") != null ? (List)_stepmap.get("ownerlinklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlinklist.size(); j++)
            {
                Map _ownerlinkmap = (Map)_ownerlinklist.get(j);
                keepProcessOwnerLink(_ownerlinkmap, _paramap);
            }

        }

        return _process.getId();
    }

    public void saveProcess_new(Map map1)
        throws Exception
    {
    }

    public int putProcessTotal(Map map, int mark)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        WorkflowPublicString a = new WorkflowPublicString();
        Map _paramap = new HashMap();
        IWorkflowProcess _process = _workbase.loadProcess(0);
        String _name = StringUtil.safeToString(map.get("name"), a.getMoveNullString());
        int _processtype = NumberTool.safeToInteger(map.get("processtype"), Integer.valueOf(a.getMoveNullInt())).intValue();
        if(_name == a.getMoveNullString() || _processtype == a.getMoveNullInt())
            return 0;
        int oldprocessid = 0;
        Map oldprocessmap = new HashMap();
        List _usedlist = _workbase.getProcessByName(_name, 0);
        if(_usedlist.size() == 0)
        {
            _process.setName(_name);
            _process.setSysVersion(Integer.valueOf(1));
        } else
        {
            Map _usedmap = (Map)_usedlist.get(0);
            int _usedid = NumberTool.convertMapKeyToInt(_usedmap, "objid", Integer.valueOf(0)).intValue();
            IWorkflowProcess _used = _workbase.loadProcess(_usedid);
            int _usedstatus = NumberTool.safeToInteger(_used.getStatus(), Integer.valueOf(0)).intValue();
            if(_usedstatus == 2)
                _used.setStatus(Integer.valueOf(3));
            else
                _used.setStatus(Integer.valueOf(5));
            _workbase.saveProcess(_used);
            int _usedversion = NumberTool.safeToInteger(_used.getSysVersion(), Integer.valueOf(0)).intValue();
            _process.setName(_name);
            _process.setSysCode(_used.getSysCode());
            _process.setSysVersion(Integer.valueOf(_usedversion + 1));
            Map oldmap = _workbase.getProcessBySyscode(_used.getSysCode());
            oldprocessid = NumberTool.convertMapKeyToInt(oldmap, "processid", Integer.valueOf(0)).intValue();
            List oldsteplist = _workbase.getProcessStepList(oldprocessid);
            for(int i = 0; i < oldsteplist.size(); i++)
            {
                Map oldstepmap = (Map)oldsteplist.get(i);
                String oldstepname = StringUtil.safeToString(oldstepmap.get("name"), "");
                int oldstepid = NumberTool.convertMapKeyToInt(oldstepmap, "objid", Integer.valueOf(0)).intValue();
                String oldstepcode = StringUtil.safeToString(oldstepmap.get("stepcode"), "");
                oldprocessmap.put(oldstepname, Integer.valueOf(oldstepid));
                _paramap.put((new StringBuilder()).append("stepcode_").append(oldstepname).toString(), oldstepcode);
            }

        }
        _process.setProcessType(Integer.valueOf(_processtype));
        String _processcode = StringUtil.safeToString(map.get("processcode"), "");
        _process.setProcessCode(_processcode);
        String _processbrief = StringUtil.safeToString(map.get("processbrief"), "");
        _process.setProcessBrief(_processbrief);
        _process.setStatus(Integer.valueOf(1));
        _process.setSpecialMark("00000");
        _workbase.saveProcess(_process);
        if(_process.getSysCode() == null || _process.getSysCode().length() == 0)
        {
            _process.setSysCode((new StringBuilder()).append("").append(_process.getId()).toString());
            _workbase.saveProcess(_process);
        }
        int processid = _process.getId();
        _paramap.put("processid", Integer.valueOf(processid));
        _paramap.put("processtype", _process.getProcessType());
        List _steplist = ((List) (map.get("steplist") != null ? (List)map.get("steplist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _oldid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            int _nowid = keepProcessStep(_stepmap, _paramap);
            _paramap.put((new StringBuilder()).append("step").append(_oldid).toString(), Integer.valueOf(_nowid));
            if(oldprocessid <= 0)
                continue;
            String name = StringUtil.safeToString(_stepmap.get("name"), "");
            if(name.length() <= 0)
                continue;
            int oldstepid = NumberTool.convertMapKeyToInt(oldprocessmap, name, Integer.valueOf(0)).intValue();
            if(oldstepid > 0)
            {
                IWorkflowProcessStepHistory history = _workbase.loadProcessStepHistory(0);
                history.setPreProcessId(Integer.valueOf(oldprocessid));
                history.setPreStepId(Integer.valueOf(oldstepid));
                history.setNewProcessId(Integer.valueOf(processid));
                history.setNewStepId(Integer.valueOf(_nowid));
                _workbase.saveProcessStepHistory(history);
            }
        }

        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            _paramap.put("stepid", Integer.valueOf(_stepid));
            List _linklist = ((List) (_stepmap.get("linklist") != null ? (List)_stepmap.get("linklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _linklist.size(); j++)
            {
                Map _linkmap = (Map)_linklist.get(j);
                keepProcessLink(_linkmap, _paramap);
            }

            List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                keepProcessOwner(_ownermap, _paramap);
            }

            List _ownerlinklist = ((List) (_stepmap.get("ownerlinklist") != null ? (List)_stepmap.get("ownerlinklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlinklist.size(); j++)
            {
                Map _ownerlinkmap = (Map)_ownerlinklist.get(j);
                keepProcessOwnerLink(_ownerlinkmap, _paramap);
            }

        }

        return _process.getId();
    }

    public int putProcessModel(Map map)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _oldprocessid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        IWorkflowProcess _oldprocess = _workbase.loadProcess(_oldprocessid);
        IWorkflowProcess _process = _workbase.loadProcess(0);
        _process.setName(_oldprocess.getName());
        _process.setProcessBrief(_oldprocess.getProcessBrief());
        _process.setProcessCode(_oldprocess.getProcessCode());
        _process.setProcessType(_oldprocess.getProcessType());
        _process.setProcessVersion(_oldprocess.getProcessVersion());
        _process.setSort(_oldprocess.getSort());
        _process.setSpecialMark(_oldprocess.getSpecialMark());
        _process.setStatus(Integer.valueOf(1));
        _process.setSysCode(_oldprocess.getSysCode());
        _process.setSysVersion(_oldprocess.getSysVersion());
        _oldprocess.setStatus(Integer.valueOf(100));
        _oldprocess.setSysCode((new StringBuilder()).append("-").append(_oldprocess.getSysCode()).toString());
        _workbase.saveProcess(_process);
        _workbase.saveProcess(_oldprocess);
        int processid = _process.getId();
        IWorkflowModelService modelService = WorkflowServiceFactory.getWorkflowModelService();
        IWorkflowModelProcess oldmodelProcess = modelService.loadModelProcess(_oldprocessid);
        IWorkflowModelProcess modelProcess = modelService.loadModelProcess(0);
        oldmodelProcess.setStatus(Integer.valueOf(100));
        modelProcess.setStatus(Integer.valueOf(2));
        modelProcess.setProcessId(Integer.valueOf(processid));
        modelProcess.setTemplateId(oldmodelProcess.getTemplateId());
        modelProcess.setTemplateType(oldmodelProcess.getTemplateType());
        modelService.saveModelProcess(oldmodelProcess);
        modelService.saveModelProcess(modelProcess);
        List modelonelistobjid = modelService.loadModelOneListByProcessId(_oldprocessid).getResult();
        IWorkflowModelOneList modelOneList;
        for(Iterator i$ = modelonelistobjid.iterator(); i$.hasNext(); modelOneList.setProcessId(Integer.valueOf(processid)))
        {
            Map modellistmap = (Map)i$.next();
            String objid = modellistmap.get("objid") != null ? modellistmap.get("objid").toString() : "";
            modelOneList = modelService.loadModelOneList(NumberTool.safeToInteger(objid, Integer.valueOf(0)).intValue());
        }

        Map _paramap = new HashMap();
        _paramap.put("processid", Integer.valueOf(processid));
        _paramap.put("processtype", _process.getProcessType());
        List _steplist = ((List) (map.get("steplist") != null ? (List)map.get("steplist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _oldid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            int _nowid = keepProcessStep(_stepmap, _paramap);
            _paramap.put((new StringBuilder()).append("step").append(_oldid).toString(), Integer.valueOf(_nowid));
        }

        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            _paramap.put("stepid", Integer.valueOf(_stepid));
            List _linklist = ((List) (_stepmap.get("linklist") != null ? (List)_stepmap.get("linklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _linklist.size(); j++)
            {
                Map _linkmap = (Map)_linklist.get(j);
                keepProcessLink(_linkmap, _paramap);
            }

            List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                keepProcessOwner(_ownermap, _paramap);
            }

            List _ownerlinklist = ((List) (_stepmap.get("ownerlinklist") != null ? (List)_stepmap.get("ownerlinklist") : ((List) (new ArrayList()))));
            for(int j = 0; j < _ownerlinklist.size(); j++)
            {
                Map _ownerlinkmap = (Map)_ownerlinklist.get(j);
                keepProcessOwnerLink(_ownerlinkmap, _paramap);
            }

        }

        return processid;
    }

    private int keepProcessLink(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        int _sort = NumberTool.convertMapKeyToInt(map, "sort", Integer.valueOf(0)).intValue();
        int _aftstepid2 = NumberTool.convertMapKeyToInt(map, "aftstepid", Integer.valueOf(0)).intValue();
        int _aftstepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_aftstepid2).toString(), Integer.valueOf(0)).intValue();
        int _prestepid2 = NumberTool.convertMapKeyToInt(map, "prestepid", Integer.valueOf(0)).intValue();
        int _prestepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_prestepid2).toString(), Integer.valueOf(0)).intValue();
        IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(0);
        _steplink.setProcessId(Integer.valueOf(_processid));
        _steplink.setSort(Integer.valueOf(_sort));
        _steplink.setAftStepId(Integer.valueOf(_aftstepid));
        _steplink.setPreStepId(Integer.valueOf(_prestepid));
        _steplink.setStatus(Integer.valueOf(1));
        _workbase.saveProcessStepLink(_steplink);
        paramap.put("linkid", Integer.valueOf(_steplink.getId()));
        List _iflist = ((List) (map.get("iflist") != null ? (List)map.get("iflist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _iflist.size(); i++)
        {
            Map _ifmap = (Map)_iflist.get(i);
            keepProcessLinkIf(_ifmap, paramap);
        }

        List _dolist = ((List) (map.get("dolist") != null ? (List)map.get("dolist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _dolist.size(); i++)
        {
            Map _domap = (Map)_dolist.get(i);
            keepProcessLinkDo(_domap, paramap);
        }

        return 0;
    }

    private int keepProcessLinkDo(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        int _stepid2 = NumberTool.convertMapKeyToInt(paramap, "stepid", Integer.valueOf(0)).intValue();
        int _stepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_stepid2).toString(), Integer.valueOf(0)).intValue();
        int _linkid = NumberTool.convertMapKeyToInt(paramap, "linkid", Integer.valueOf(0)).intValue();
        int _dotype = NumberTool.convertMapKeyToInt(map, "dotype", Integer.valueOf(0)).intValue();
        int _domark = NumberTool.convertMapKeyToInt(map, "domark", Integer.valueOf(0)).intValue();
        String _doads = StringUtil.safeToString(map.get("doads"), "");
        if(_dotype > 0)
        {
            Map _dotypemap = ((Map) (map.get("dotypemap") != null ? (Map)map.get("dotypemap") : ((Map) (new HashMap()))));
            _dotype = keepProcessListMap(_dotypemap);
        }
        IWorkflowProcessStepLinkDo _linkdo = _workbase.loadProcessStepLinkDo(0);
        _linkdo.setProcessId(Integer.valueOf(_processid));
        _linkdo.setStepId(Integer.valueOf(_stepid));
        _linkdo.setLinkId(Integer.valueOf(_linkid));
        _linkdo.setDoType(Integer.valueOf(_dotype));
        _linkdo.setDoMark(Integer.valueOf(_domark));
        _linkdo.setDoAds(_doads);
        _linkdo.setStatus(Integer.valueOf(1));
        _workbase.saveProcessStepLinkDo(_linkdo);
        return _linkdo.getId();
    }

    private int keepProcessLinkIf(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        int _stepid2 = NumberTool.convertMapKeyToInt(paramap, "stepid", Integer.valueOf(0)).intValue();
        int _stepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_stepid2).toString(), Integer.valueOf(0)).intValue();
        int _linkid = NumberTool.convertMapKeyToInt(paramap, "linkid", Integer.valueOf(0)).intValue();
        int _iftype = NumberTool.convertMapKeyToInt(map, "iftype", Integer.valueOf(0)).intValue();
        int _ifand = NumberTool.convertMapKeyToInt(map, "ifand", Integer.valueOf(0)).intValue();
        String _ifads = StringUtil.safeToString(map.get("ifads"), "");
        if(_iftype >= 0x186a0)
        {
            Map _iftypemap = ((Map) (map.get("iftypemap") != null ? (Map)map.get("iftypemap") : ((Map) (new HashMap()))));
            _iftype = keepProcessListMap(_iftypemap);
        }
        IWorkflowProcessStepLinkIf _linkif = _workbase.loadProcessStepLinkIf(0);
        _linkif.setProcessId(Integer.valueOf(_processid));
        _linkif.setStepId(Integer.valueOf(_stepid));
        _linkif.setLinkId(Integer.valueOf(_linkid));
        _linkif.setIfType(Integer.valueOf(_iftype));
        _linkif.setIfAnd(Integer.valueOf(_ifand));
        _linkif.setIfAds(_ifads);
        _linkif.setStatus(Integer.valueOf(1));
        _workbase.saveProcessStepLinkIf(_linkif);
        return _linkif.getId();
    }

    private int keepProcessOwner(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        int _stepid2 = NumberTool.convertMapKeyToInt(paramap, "stepid", Integer.valueOf(0)).intValue();
        int _stepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_stepid2).toString(), Integer.valueOf(0)).intValue();
        int _orgid = NumberTool.safeToInteger(map.get("orgid"), Integer.valueOf(0)).intValue();
        int _orgtype = NumberTool.safeToInteger(map.get("orgtype"), Integer.valueOf(0)).intValue();
        if(_orgtype == -1)
        {
            Map _orgtypemap = ((Map) (map.get("orgtypemap") != null ? (Map)map.get("orgtypemap") : ((Map) (new HashMap()))));
            _orgid = keepProcessListMap(_orgtypemap);
        }
        int _value = NumberTool.safeToInteger(map.get("value"), Integer.valueOf(1)).intValue();
        int _ownergoto = NumberTool.safeToInteger(map.get("ownergoto"), Integer.valueOf(0)).intValue();
        int _ownergotoid = NumberTool.safeToInteger(map.get("ownergotoid"), Integer.valueOf(0)).intValue();
        IWorkflowProcessOwner _owner = _workbase.loadProcessOwner(0);
        _owner.setProcessId(Integer.valueOf(_processid));
        _owner.setStepId(Integer.valueOf(_stepid));
        _owner.setOrgType(Integer.valueOf(_orgtype));
        _owner.setOrgId((new StringBuilder()).append("").append(_orgid).toString());
        _owner.setValue(Integer.valueOf(_value));
        _owner.setOwnerGoto(Integer.valueOf(_ownergoto));
        _owner.setOwnerGotoId(Integer.valueOf(_stepid));
        _owner.setStatus(Integer.valueOf(1));
        _workbase.saveProcessOwner(_owner);
        return _owner.getId();
    }

    private int keepProcessOwnerLink(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        int _stepid2 = NumberTool.convertMapKeyToInt(paramap, "stepid", Integer.valueOf(0)).intValue();
        int _stepid = NumberTool.convertMapKeyToInt(paramap, (new StringBuilder()).append("step").append(_stepid2).toString(), Integer.valueOf(0)).intValue();
        String _ownerstarter = StringUtil.safeToString(map.get("ownerstarter"), "");
        String _ownerender = StringUtil.safeToString(map.get("ownerender"), "");
        int _sort = NumberTool.convertMapKeyToInt(map, "sort", Integer.valueOf(0)).intValue();
        int _linkroot = NumberTool.convertMapKeyToInt(map, "linkroot", Integer.valueOf(0)).intValue();
        int _linkleaf = NumberTool.convertMapKeyToInt(map, "linkleaf", Integer.valueOf(0)).intValue();
        IWorkflowProcessOwnerLink _ownerlink = _workbase.loadProcessOwnerLink(0);
        _ownerlink.setProcessId(Integer.valueOf(_processid));
        _ownerlink.setStepId(Integer.valueOf(_stepid));
        _ownerlink.setOwnerStarter(_ownerstarter);
        _ownerlink.setOwnerEnder(_ownerender);
        _ownerlink.setSort(Integer.valueOf(_sort));
        _ownerlink.setLinkRoot(Integer.valueOf(_linkroot));
        _ownerlink.setLinkLeaf(Integer.valueOf(_linkleaf));
        _ownerlink.setStatus(Integer.valueOf(1));
        _workbase.saveProcessOwnerLink(_ownerlink);
        return _ownerlink.getId();
    }

    private int keepProcessStep(Map map, Map paramap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        WorkflowPublicString a = new WorkflowPublicString();
        int _processtype = NumberTool.convertMapKeyToInt(paramap, "processtype", Integer.valueOf(0)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(paramap, "processid", Integer.valueOf(0)).intValue();
        IWorkflowProcessStep _processstep = _workbase.loadProcessStep(0);
        _processstep.setProcessId(Integer.valueOf(_processid));
        _processstep.setStatus(Integer.valueOf(1));
        String _name = StringUtil.safeToString(map.get("name"), a.getMoveNullString());
        _processstep.setName(_name);
        int _sort = NumberTool.convertMapKeyToInt(map, "sort", Integer.valueOf(0)).intValue();
        _processstep.setSort(Integer.valueOf(_sort));
        int _steptype = NumberTool.safeToInteger(map.get("steptype"), Integer.valueOf(a.getMoveNullInt())).intValue();
        _processstep.setStepTypeId(Integer.valueOf(_steptype));
        String _steptypeads = StringUtil.safeToString(map.get("steptypeads"), "");
        String _steptypeadsArr[] = _steptypeads.split(" ");
        if(_steptypeadsArr.length == 2)
        {
            int _steptypeadsid = NumberTool.safeToInteger(_steptypeadsArr[1], Integer.valueOf(0)).intValue();
            if(_steptypeadsid != 0)
            {
                Map _steptypemap = ((Map) (map.get("steptypemap") != null ? (Map)map.get("steptypemap") : ((Map) (new HashMap()))));
                _steptypeadsid = keepProcessListMap(_steptypemap);
                StringBuffer _steptypeadssb = new StringBuffer();
                _steptypeadssb.append(_steptypeadsArr[0]).append(" ").append(_steptypeadsid);
                _steptypeads = _steptypeadssb.toString();
            }
        }
        _processstep.setStepTypeAds(_steptypeads);
        String _condition = StringUtil.safeToString(map.get("condition"), "");
        String _conditionArr[] = _condition.split(";");
        if(_conditionArr.length == 6)
        {
            int _conditionid = NumberTool.safeToInteger(_conditionArr[5], Integer.valueOf(0)).intValue();
            if(_conditionid != 0)
            {
                Map _conditionmap = ((Map) (map.get("conditionmap") != null ? (Map)map.get("conditionmap") : ((Map) (new HashMap()))));
                _conditionid = keepProcessListMap(_conditionmap);
                StringBuffer _conditionsb = new StringBuffer();
                _conditionsb.append(_conditionArr[0]).append(";").append(_conditionArr[1]).append(";").append(_conditionArr[2]).append(";");
                _conditionsb.append(_conditionArr[3]).append(";").append(_conditionArr[4]).append(";").append(_conditionid).append(";");
                _condition = _conditionsb.toString();
            }
        }
        _processstep.setCondition(_condition);
        int _pointtype = NumberTool.convertMapKeyToInt(map, "pointtype", Integer.valueOf(0)).intValue();
        if(_pointtype != 0)
        {
            Map _pointtypemap = ((Map) (map.get("pointtypemap") != null ? (Map)map.get("pointtypemap") : ((Map) (new HashMap()))));
            _pointtype = keepProcessListMap(_pointtypemap);
        }
        _processstep.setPointTypeId(Integer.valueOf(_pointtype));
        String _urlname = StringUtil.safeToString(map.get("urlname"), a.getMoveNullString());
        String _actionurl = StringUtil.safeToString(map.get("actionurl"), "");
        String _showurl = StringUtil.safeToString(map.get("showurl"), "");
        List _urllist = _workbase.getUrlExistList(_urlname, _actionurl, _showurl, _processtype);
        int _urlmark = 0;
        if(_urlname.equals(a.getMoveNullString()) && _actionurl.length() == 0 && _showurl.length() == 0)
            _urlmark = -1;
        if(_urlmark == -1)
            _urlmark = 0;
        else
        if(_urllist.size() > 0)
        {
            Map _urlmap = (Map)_urllist.get(0);
            _urlmark = NumberTool.convertMapKeyToInt(_urlmap, "urlmark", Integer.valueOf(0)).intValue();
        } else
        if(_workbase.testUrlNameUsed(0, _urlname))
        {
            _urlmark = keepProcessUrl(_urlname, _actionurl, _showurl, _processtype);
        } else
        {
            int mark;
            for(mark = 1; !_workbase.testUrlNameUsed(0, (new StringBuilder()).append(_urlname).append(mark).toString()); mark++);
            _urlmark = keepProcessUrl((new StringBuilder()).append(_urlname).append(mark).toString(), _actionurl, _showurl, _processtype);
        }
        _processstep.setUrlMark(Integer.valueOf(_urlmark));
        _workbase.saveProcessStep(_processstep);
        return _processstep.getId();
    }

    private int keepProcessListMap(Map map)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        String _name = StringUtil.safeToString(map.get("name"), "");
        String _value = StringUtil.safeToString(map.get("value"), "");
        String _valueads = StringUtil.safeToString(map.get("valueads"), "");
        int _type = NumberTool.convertMapKeyToInt(map, "type", Integer.valueOf(0)).intValue();
        int _key = NumberTool.convertMapKeyToInt(map, "key", Integer.valueOf(0)).intValue();
        List _list = _workbase.getListExistList(_name, _value, _valueads, _type);
        int _keymark = 0;
        if(_list.size() > 0)
        {
            Map _map = (Map)_list.get(0);
            _keymark = NumberTool.convertMapKeyToInt(_map, "keymark", Integer.valueOf(0)).intValue();
        } else
        if(_workbase.testListNameUsed(0, _name, _value))
        {
            _keymark = keepProcessList(_name, _value, _valueads, _type, _key);
        } else
        {
            int mark;
            for(mark = 1; !_workbase.testListNameUsed(0, _name, (new StringBuilder()).append(_value).append(mark).toString()); mark++);
            _keymark = keepProcessList(_name, (new StringBuilder()).append(_value).append(mark).toString(), _valueads, _type, _key);
        }
        return _keymark;
    }

    private int keepProcessList(String name, String value, String valueads, int type, int key)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcessList _processlist = _workbase.loadProcessList(0);
        _processlist.setName(name);
        _processlist.setValue(value);
        _processlist.setValueAds(valueads);
        _processlist.setType(Integer.valueOf(type));
        _processlist.setStatus(Integer.valueOf(1));
        _processlist.setSort(Integer.valueOf(0));
        _workbase.saveProcessList(_processlist);
        if(_processlist.getKey() == null)
            if("LinkIfType".equals(name))
            {
                _processlist.setKey(Integer.valueOf(0x186a0 + _processlist.getId()));
                _workbase.saveProcessList(_processlist);
            } else
            if("StepStage".equals(name))
            {
                _processlist.setKey(Integer.valueOf(key));
                _workbase.saveProcessList(_processlist);
            } else
            {
                _processlist.setKey(Integer.valueOf(_processlist.getId()));
                _workbase.saveProcessList(_processlist);
            }
        int keymark = NumberTool.safeToInteger(_processlist.getKey(), Integer.valueOf(0)).intValue();
        return keymark;
    }

    private int keepProcessUrl(String name, String actionurl, String showurl, int type)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcessUrl _processurl = _workbase.loadProcessUrl(0);
        _processurl.setName(name);
        _processurl.setActionUrl(actionurl);
        _processurl.setShowUrl(showurl);
        _processurl.setType(Integer.valueOf(type));
        _processurl.setStatus(Integer.valueOf(1));
        _processurl.setSort(Integer.valueOf(0));
        _workbase.saveProcessUrl(_processurl);
        return _processurl.getId();
    }
}
