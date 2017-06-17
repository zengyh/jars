// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowSupportServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.service.IMessageEngineService;
import com.sinitek.sirm.busin.routine.utils.MessageContext;
import com.sinitek.sirm.busin.routine.utils.MessageReceiver;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.service.rule.IWorkflowRuleService;
import com.sinitek.sirm.busin.workflow.support.WorkflowException;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class WorkflowSupportServiceImpl extends MetaDBContextSupport
    implements IWorkflowSupportService
{

    public WorkflowSupportServiceImpl()
    {
    }

    public Map startProcess(Map map)
    {
        LOGGER.info((new StringBuilder()).append("startProcess: ").append(map.toString()).toString());
        WorkflowException exception = new WorkflowException();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        Map _map = new HashMap();
        Map _mainmap = ((Map) (map.get("mainmap") != null ? (Map)map.get("mainmap") : ((Map) (new HashMap()))));
        int _exampleid = saveExample(_mainmap);
        if(_exampleid <= 0)
        {
            LOGGER.info("\u53D1\u8D77\u6D41\u7A0B\u65F6\uFF0C\u627E\u4E0D\u5230\u6D41\u7A0B\u6A21\u677F");
            return dealException(exception.processnotfound, 0, 0);
        }
        IWorkflowExample _example = _workapp.loadExample(_exampleid);
        List _entrylist = ((List) (map.get("entrylist") != null ? (List)map.get("entrylist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _entrylist.size(); i++)
        {
            Map _entrymap = (Map)_entrylist.get(i);
            int _exampleid2 = NumberTool.convertMapKeyToInt(_entrymap, "exampleid", Integer.valueOf(0)).intValue();
            if(_exampleid2 == 0)
                _entrymap.put("exampleid", Integer.valueOf(_exampleid));
            saveExampleEntry(_entrymap);
        }

        Map _stepmap = _workengine.newProcess(_example);
        _stepmap.put("exampleid", Integer.valueOf(_example.getId()));
        int _examplestepid = saveExampleStep(_stepmap);
        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
        Map _ownermap = ((Map) (map.get("ownermap") != null ? (Map)map.get("ownermap") : ((Map) (new HashMap()))));
        _ownermap.put("status", Integer.valueOf(8));
        _ownermap.put("exampleid", _examplestep.getExampleId());
        _ownermap.put("examplestepid", Integer.valueOf(_examplestepid));
        if(_ownermap.get("ownerid") == null)
            _ownermap.put("ownerid", _example.getStarterId());
        if(_ownermap.get("approvestatus") == null)
            _ownermap.put("approvestatus", Integer.valueOf(3));
        int _exampleownerid = saveExampleOwner(_ownermap);
        Map _paramap = ((Map) (map.get("paramap") != null ? (Map)map.get("paramap") : ((Map) (new HashMap()))));
        _workapp.saveParaMap(_exampleid, 0, 0, _paramap);
        Map _firstmap = new HashMap();
        Map _mainmap2 = changeExampleToMap(_example);
        Map _stepmap2 = new HashMap();
        _stepmap2.put("examplestepid", Integer.valueOf(_examplestep.getId()));
        _stepmap2.put("stepid", _examplestep.getProcessStepId());
        _stepmap2.put("steptypeid", _examplestep.getStepType());
        _stepmap2.put("stepstatus", Integer.valueOf(2));
        List _ownerlist = _workapp.getExampleOwnerList(_examplestepid);
        _stepmap2.put("ownerlist", _ownerlist);
        Map _insteadmap = ((Map) (map.get("insteadmap") != null ? (Map)map.get("insteadmap") : ((Map) (new HashMap()))));
        Map _addmap = ((Map) (map.get("addmap") != null ? (Map)map.get("addmap") : ((Map) (new HashMap()))));
        Map _removemap = ((Map) (map.get("removemap") != null ? (Map)map.get("removemap") : ((Map) (new HashMap()))));
        Map _forbidmap = ((Map) (map.get("forbidmap") != null ? (Map)map.get("forbidmap") : ((Map) (new HashMap()))));
        _firstmap.put("insteadmap", _insteadmap);
        _firstmap.put("addmap", _addmap);
        _firstmap.put("removemap", _removemap);
        _firstmap.put("forbidmap", _forbidmap);
        _firstmap.put("mainmap", _mainmap2);
        _firstmap.put("stepmap", _stepmap2);
        _firstmap.put("paramap", _paramap);
        Map _nextmap = _workengine.subProcessStep(_firstmap);
        int _exception = NumberTool.convertMapKeyToInt(_nextmap, "exception", Integer.valueOf(0)).intValue();
        if(_exception != 0)
            return dealException(_exception, _exampleid, _examplestepid);
        IWorkflowTemplateService _worktemp = WorkflowServiceFactory.getWorkflowTemplateService();
        int _targetsize = NumberTool.convertMapKeyToInt(_nextmap, "targetsize", Integer.valueOf(0)).intValue();
        if(_targetsize > 0)
            _worktemp.doStepAftDoOperate(_nextmap);
        List _resultlist = saveExampleSteps(_nextmap);
        List _resultlist1 = _worktemp.doOrdinaryOperate(_resultlist);
        List _resultlist0 = _worktemp.doSpecialOperate(_resultlist1);
        _map.put("resultlist", _resultlist1);
        if(_targetsize > 0)
        {
            _worktemp.doStepPreDoOperate(_resultlist1);
            Iterator i$ = _resultlist1.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _mailownerlist = (Map)i$.next();
                Map _mailmap = new HashMap();
                _mailmap.put("exampleid", Integer.valueOf(MapUtils.getIntValue(_mailownerlist, "exampleid", 0)));
                _mailmap.put("examplestepid", Integer.valueOf(MapUtils.getIntValue(_mailownerlist, "examplestepid", 0)));
                _mailmap.put("exampleownerid", Integer.valueOf(MapUtils.getIntValue(_mailownerlist, "exampleownerid", 0)));
                int _ownerstatus = MapUtils.getIntValue(_mailownerlist, "ownerstatus", 0);
                if(_ownerstatus == 0)
                    sendmessage(_mailmap);
            } while(true);
        }
        LOGGER.info((new StringBuilder()).append("Process Exception: ").append(MapUtils.getIntValue(_map, "exception", 0)).toString());
        return _map;
    }

    public Map subProcessStep(Map map)
    {
        LOGGER.info((new StringBuilder()).append("subProcessStep: ").append(map.toString()).toString());
        map = WorkflowServiceFactory.getWorkflowRuleService().pretreatedSubProcessStep(map);
        WorkflowException exception = new WorkflowException();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        int passMark = 0;
        Map _changemap = ((Map) (map.get("changemap") != null ? (Map)map.get("changemap") : ((Map) (new HashMap()))));
        List _changesteplist = ((List) (_changemap.get("steplist") != null ? (List)_changemap.get("steplist") : ((List) (new ArrayList()))));
        if(_changesteplist.size() > 0)
            passMark = 1;
        Map _map = new HashMap();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        Map _mainmap = ((Map) (map.get("mainmap") != null ? (Map)map.get("mainmap") : ((Map) (new HashMap()))));
        if(_exampleid > 0)
            _mainmap.put("exampleid", Integer.valueOf(_exampleid));
        else
        if(_exampleid == 0)
        {
            _exampleid = NumberTool.convertMapKeyToInt(_mainmap, "exampleid", Integer.valueOf(0)).intValue();
            if(_exampleid == 0)
            {
                _exampleid = NumberTool.convertMapKeyToInt(_mainmap, "objid", Integer.valueOf(0)).intValue();
                if(_exampleid == 0)
                {
                    LOGGER.info("\u6D41\u7A0B\u63D0\u4EA4\u4E00\u6B65\u65F6\uFF0C \u65E0\u6CD5\u627E\u5230\u5BF9\u5E94\u7684\u6D41\u7A0Bid");
                    return dealException(exception.processnotfound, 0, 0);
                }
                _mainmap.put("exampleid", Integer.valueOf(_exampleid));
            }
        }
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        Map _stepmap = ((Map) (map.get("stepmap") != null ? (Map)map.get("stepmap") : ((Map) (new HashMap()))));
        if(_examplestepid > 0)
            _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
        else
        if(_examplestepid == 0)
        {
            _examplestepid = NumberTool.convertMapKeyToInt(_stepmap, "examplestepid", Integer.valueOf(0)).intValue();
            if(_examplestepid == 0)
            {
                _examplestepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
                if(_examplestepid == 0 && passMark == 0)
                {
                    LOGGER.info("\u6D41\u7A0B\u63D0\u4EA4\u4E00\u6B65\u65F6\uFF0C \u65E0\u6CD5\u627E\u5230\u5BF9\u5E94\u7684\u6D41\u7A0B\u6B65\u9AA4id");
                    return dealException(exception.stepnotfound, 0, 0);
                }
                _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
            }
        }
        int _exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        Map _ownermap = ((Map) (map.get("ownermap") != null ? (Map)map.get("ownermap") : ((Map) (new HashMap()))));
        if(_exampleownerid != 0)
        {
            _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
        } else
        {
            _exampleownerid = NumberTool.convertMapKeyToInt(_ownermap, "exampleownerid", Integer.valueOf(0)).intValue();
            if(_exampleownerid == 0)
            {
                _exampleownerid = NumberTool.convertMapKeyToInt(_ownermap, "objid", Integer.valueOf(0)).intValue();
                if(_exampleownerid == 0 && passMark == 0)
                {
                    LOGGER.info("\u6D41\u7A0B\u63D0\u4EA4\u4E00\u6B65\u65F6\uFF0C \u65E0\u6CD5\u627E\u5230\u5BF9\u5E94\u7684\u6D41\u7A0B\u6B65\u9AA4\u5904\u7406\u4EBAid");
                    return dealException(exception.ownernotfound, 0, 0);
                }
                _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
            }
        }
        _mainmap.put("examplestepid", Integer.valueOf(_examplestepid));
        _mainmap.put("exampleownerid", Integer.valueOf(_exampleownerid));
        _stepmap.put("exampleid", Integer.valueOf(_exampleid));
        _stepmap.put("exampleownerid", Integer.valueOf(_exampleownerid));
        _ownermap.put("exampleid", Integer.valueOf(_exampleid));
        _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
        changeExample(_mainmap);
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        IWorkflowExample _example = _workapp.loadExample(_exampleid);
        if(_status != -1)
            if(_status == 1)
            {
                if(_example.getStatus().intValue() == 1)
                    _workapp.changeExample(_exampleid, 0);
            } else
            if(_example.getStatus().intValue() == 0)
                _workapp.changeExample(_exampleid, 1);
        Map _mainmap2 = changeExampleToMap(_example);
        List _entrylist = ((List) (map.get("entrylist") != null ? (List)map.get("entrylist") : ((List) (new ArrayList()))));
        for(int i = 0; i < _entrylist.size(); i++)
        {
            Map _entrymap = (Map)_entrylist.get(i);
            int _exampleid2 = NumberTool.convertMapKeyToInt(_entrymap, "exampleid", Integer.valueOf(0)).intValue();
            if(_exampleid2 == 0)
                _entrymap.put("exampleid", Integer.valueOf(_exampleid));
            saveExampleEntry(_entrymap);
        }

        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
        Map _stepmap2 = changeExampleStepToMap(_examplestep);
        Map _paramap = ((Map) (map.get("paramap") != null ? (Map)map.get("paramap") : ((Map) (new HashMap()))));
        _workapp.saveParaMap(_exampleid, 0, 0, _paramap);
        Map _paramap2 = _workapp.loadParaMap(_exampleid, 0, 0);
        Map _insteadmap = ((Map) (map.get("insteadmap") != null ? (Map)map.get("insteadmap") : ((Map) (new HashMap()))));
        Map _addmap = ((Map) (map.get("addmap") != null ? (Map)map.get("addmap") : ((Map) (new HashMap()))));
        Map _removemap = ((Map) (map.get("removemap") != null ? (Map)map.get("removemap") : ((Map) (new HashMap()))));
        Map _forbidmap = ((Map) (map.get("forbidmap") != null ? (Map)map.get("forbidmap") : ((Map) (new HashMap()))));
        Map _firstmap = new HashMap();
        if(_exampleownerid >= 0)
        {
            IWorkflowExampleStepOwner _testowner = _workapp.loadExampleStepOwner(_exampleownerid);
            int _teststatus = NumberTool.safeToInteger(_testowner.getStatus(), Integer.valueOf(0)).intValue();
            int _ownerstatus = NumberTool.convertMapKeyToInt(_ownermap, "status", Integer.valueOf(-1)).intValue();
            if(_teststatus > WorkflowStepOwnerStatus.WF_OWNER_AUTO.getEnumItemValue())
            {
                if(_ownerstatus > -1)
                    saveExampleOwner(_ownermap);
                String _msg = "\u8BE5\u4EFB\u52A1\u5DF2\u7ECF\u88AB\u5904\u7406";
                if(_teststatus == WorkflowStepOwnerStatus.WF_OWNER_RECOVER.getEnumItemValue())
                    _msg = "\u8BE5\u4EFB\u52A1\u5DF2\u7ECF\u88AB\u53D1\u8D77\u4EBA\u64A4\u56DE";
                _map.put("message", _msg);
                _map.put("exception", Integer.valueOf(exception.stepbecompleted));
                LOGGER.info((new StringBuilder()).append("Process Exception: ").append(MapUtils.getIntValue(_map, "exception", 0)).toString());
                return _map;
            }
            if(_ownerstatus == -1)
                _ownermap.put("status", Integer.valueOf(WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue()));
            int _examplestepownerid = saveExampleOwner(_ownermap);
            IWorkflowExampleStepOwner _stepowner = _workapp.loadExampleStepOwner(_examplestepownerid);
            if(_ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_TRANSFER.getEnumItemValue())
            {
                try
                {
                    _stepmap2 = changeStepmapByChange(_stepmap2, _stepowner, _changemap);
                    String _stepend = StringUtil.safeToString(_stepmap2.get("stepend"), "");
                    _examplestep.setStepEnd(_stepend);
                }
                catch(Exception e)
                {
                    LOGGER.error((new StringBuilder()).append("Process Exception:").append(exception.outofarray).toString(), e);
                    return dealException(exception.outofarray, 0, 0);
                }
                List _resultlist2 = ((List) (_changemap.get("resultlist") != null ? (List)_changemap.get("resultlist") : ((List) (new ArrayList()))));
                List _resultlist0 = WorkflowServiceFactory.getWorkflowTemplateService().doSpecialOperate(_resultlist2);
                _map.put("resultlist", _resultlist2);
                LOGGER.info((new StringBuilder()).append("Process Exception: ").append(MapUtils.getIntValue(_map, "exception", 0)).toString());
                return _map;
            }
            if(_ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_BETRANSFER.getEnumItemValue())
            {
                try
                {
                    _stepmap2 = changeStepmapByChange(_stepmap2, _stepowner, _changemap);
                    String _stepend = StringUtil.safeToString(_stepmap2.get("stepend"), "");
                    _examplestep.setStepEnd(_stepend);
                }
                catch(Exception e)
                {
                    LOGGER.error((new StringBuilder()).append("Process Exception:").append(exception.outofarray).toString(), e);
                    return dealException(exception.outofarray, 0, 0);
                }
                List _resultlist2 = ((List) (_changemap.get("resultlist") != null ? (List)_changemap.get("resultlist") : ((List) (new ArrayList()))));
                List _resultlist0 = WorkflowServiceFactory.getWorkflowTemplateService().doSpecialOperate(_resultlist2);
                _map.put("resultlist", _resultlist2);
                LOGGER.info((new StringBuilder()).append("Process Exception: ").append(MapUtils.getIntValue(_map, "exception", 0)).toString());
                return _map;
            }
            Map _ownermap2 = changeExampleOwnerToMap(_stepowner);
            _stepmap2 = changeStepmapByOwner(_stepmap2, _stepowner);
            _stepmap2.put("stepstatus", Integer.valueOf(8));
            List _ownerlist = _workapp.getExampleOwnerList(_examplestepid);
            _stepmap2.put("ownerlist", _ownerlist);
            _firstmap.put("ownermap", _ownermap2);
        }
        _firstmap.put("mainmap", _mainmap2);
        _firstmap.put("stepmap", _stepmap2);
        _firstmap.put("paramap", _paramap2);
        _firstmap.put("insteadmap", _insteadmap);
        _firstmap.put("addmap", _addmap);
        _firstmap.put("removemap", _removemap);
        _firstmap.put("forbidmap", _forbidmap);
        _firstmap.put("changemap", _changemap);
        Map oncelinkmap = (Map)map.get("oncelinkmap");
        Boolean zcbh = Boolean.valueOf(false);
        Integer currentstepid = NumberTool.safeToInteger(_stepmap2.get("stepid"), Integer.valueOf(-1));
        Integer condition = NumberTool.safeToInteger(_stepmap2.get("condition"), Integer.valueOf(-1));
        if(oncelinkmap != null && NumberTool.safeToInteger(oncelinkmap.get("aftstepid"), Integer.valueOf(-1)).intValue() != -1)
        {
            Integer aftstepid = NumberTool.safeToInteger(oncelinkmap.get("aftstepid"), Integer.valueOf(-1));
            Integer returntargetstepid = NumberTool.safeToInteger(oncelinkmap.get("returntargetstepid"), Integer.valueOf(-1));
            _firstmap.put("oncelinkmap", oncelinkmap);
            if(returntargetstepid.intValue() != -1)
                zcbh = Boolean.valueOf(true);
        } else
        if(oncelinkmap != null && NumberTool.safeToInteger(oncelinkmap.get("aftstepid"), Integer.valueOf(-1)).intValue() == -1 && NumberTool.safeToInteger(oncelinkmap.get("returntargetstepid"), Integer.valueOf(-1)).intValue() != -1)
            zcbh = Boolean.valueOf(true);
        else
        if(_paramap2 != null && _paramap2.get((new StringBuilder()).append("wf_returntargetstepid_").append(currentstepid).toString()) != null && (condition.intValue() == WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue() || condition.intValue() == WorkflowStepCondition.WF_STEP_SUBMIT.getEnumItemValue()))
        {
            oncelinkmap = new HashMap();
            Integer aftstepid = NumberTool.safeToInteger(_paramap2.get((new StringBuilder()).append("wf_returntargetstepid_").append(currentstepid).toString()), Integer.valueOf(-1));
            if(aftstepid.intValue() != -1)
            {
                oncelinkmap.put("aftstepid", aftstepid);
                _firstmap.put("oncelinkmap", oncelinkmap);
                Map newpara = new HashMap();
                newpara.put((new StringBuilder()).append("wf_returntargetstepid_").append(currentstepid).toString(), "");
                _workapp.saveParaMap(_exampleid, 0, 0, newpara);
            }
        }
        Map _nextmap = _workengine.subProcessStep(_firstmap);
        if(zcbh.booleanValue())
        {
            Integer aftstepid = Integer.valueOf(-1);
            if(_nextmap.get("targetlist") != null)
            {
                List targetlist = (List)_nextmap.get("targetlist");
                if(targetlist.size() > 0)
                    aftstepid = NumberTool.safeToInteger(((Map)targetlist.get(0)).get("stepid"), Integer.valueOf(-1));
            }
            Integer returntargetstepid = NumberTool.safeToInteger(oncelinkmap.get("returntargetstepid"), Integer.valueOf(-1));
            if(returntargetstepid.intValue() != -1)
            {
                Map newpara = new HashMap();
                newpara.put((new StringBuilder()).append("wf_returntargetstepid_").append(aftstepid).toString(), returntargetstepid);
                _workapp.saveParaMap(_exampleid, 0, 0, newpara);
            }
        }
        int _exception = NumberTool.convertMapKeyToInt(_nextmap, "exception", Integer.valueOf(0)).intValue();
        if(_exception != 0)
            return dealException(_exception, _exampleid, _examplestepid);
        IWorkflowTemplateService _worktemp = WorkflowServiceFactory.getWorkflowTemplateService();
        int _targetsize = NumberTool.convertMapKeyToInt(_nextmap, "targetsize", Integer.valueOf(0)).intValue();
        if(_targetsize > 0)
            _worktemp.doStepAftDoOperate(_nextmap);
        int sptype = NumberTool.convertMapKeyToInt(map, "sptype", Integer.valueOf(0)).intValue();
        if(sptype != 0)
        {
            _nextmap.put("sptype", Integer.valueOf(sptype));
            _nextmap.put("spbrief", map.get("spbrief"));
        }
        List _resultlist = saveExampleSteps(_nextmap);
        List _resultlist1 = _worktemp.doOrdinaryOperate(_resultlist);
        List _resultlist0 = _worktemp.doSpecialOperate(_resultlist1);
        _map.put("resultlist", _resultlist1);
        if(_targetsize > 0)
        {
            _worktemp.doStepPreDoOperate(_resultlist1);
            Iterator i$ = _resultlist1.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _ownerlist = (Map)i$.next();
                Map _mailmap = new HashMap();
                _mailmap.put("exampleid", Integer.valueOf(MapUtils.getIntValue(_ownerlist, "exampleid", 0)));
                _mailmap.put("examplestepid", Integer.valueOf(MapUtils.getIntValue(_ownerlist, "examplestepid", 0)));
                _mailmap.put("exampleownerid", Integer.valueOf(MapUtils.getIntValue(_ownerlist, "exampleownerid", 0)));
                int _ownerstatus = MapUtils.getIntValue(_ownerlist, "ownerstatus", 0);
                if(_ownerstatus == 0)
                    sendmessage(_mailmap);
            } while(true);
        }
        LOGGER.info((new StringBuilder()).append("Process Exception: ").append(MapUtils.getIntValue(_map, "exception", 0)).toString());
        return _map;
    }

    private int saveExampleEntry(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        String _sourcename = StringUtil.safeToString(map.get("sourcename"), "");
        int _sourceid = NumberTool.convertMapKeyToInt(map, "sourceid", Integer.valueOf(0)).intValue();
        int _mark = NumberTool.convertMapKeyToInt(map, "mark", Integer.valueOf(0)).intValue();
        if(_mark == 0)
            _mark = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        int _entryid = NumberTool.convertMapKeyToInt(map, "entryid", Integer.valueOf(0)).intValue();
        if(_entryid == 0)
            _entryid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
        if(_entryid == 0)
            _entryid = _workapp.findEntryId(_exampleid, _sourceid, _sourcename);
        IWorkflowExampleEntry _entry = _workapp.loadExampleEntry(_entryid);
        if(_entry.getStatus() == null)
            if(_mark == 0 || _mark == 1)
            {
                _entry.setChangeTime(new Date());
                _entry.setExampleId(Integer.valueOf(_exampleid));
                _entry.setSourceId(Integer.valueOf(_sourceid));
                _entry.setSourceName(_sourcename);
                _entry.setStatus(Integer.valueOf(1));
                return _workapp.saveExampleEntry(_entry);
            } else
            {
                return 0;
            }
        if(_mark == -1 || _mark == 100)
        {
            _entry.setChangeTime(new Date());
            _entry.setStatus(Integer.valueOf(100));
        } else
        {
            _entry.setChangeTime(new Date());
            if(_exampleid != 0)
                _entry.setExampleId(Integer.valueOf(_exampleid));
            if(_sourceid != 0)
                _entry.setSourceId(Integer.valueOf(_sourceid));
            if(_sourcename.length() > 0)
                _entry.setSourceName(_sourcename);
            _entry.setStatus(Integer.valueOf(1));
        }
        return _workapp.saveExampleEntry(_entry);
    }

    private Map changeStepmapByChange(Map stepmap, IWorkflowExampleStepOwner stepowner, Map changemap)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        String _stepend = StringUtil.safeToString(stepmap.get("stepend"), "");
        List _stepownerlist = ((List) (changemap.get("stepownerlist") != null ? (List)changemap.get("stepownerlist") : ((List) (new ArrayList()))));
        int _basevalue = stepowner.getValue().intValue();
        int _listsize = _stepownerlist.size();
        List _resultlist = new ArrayList();
        String _endArr[] = _stepend.split(";");
        StringBuffer _stepend2 = new StringBuffer();
        if(_listsize > 0)
        {
            if(_endArr.length == 1)
                _stepend2.append(_endArr[0]).append(";");
            else
            if(_endArr.length == 2)
                _stepend2.append(_endArr[0]).append(";").append(_endArr[1]).append(";");
            else
            if(_endArr.length == 3)
            {
                String _sumMsg[] = _endArr[0].split(" ");
                int _sumren = NumberTool.safeToInteger(_sumMsg[0], Integer.valueOf(0)).intValue();
                double _sumpiao = NumberTool.safeToDouble(_sumMsg[1], Double.valueOf(0.0D)).doubleValue();
                _sumren = (_sumren + _listsize) - 1;
                _sumpiao *= _listsize;
                int _sumtype = WorkflowStepJudgeType.WF_JUDGE_VOTE.getEnumItemValue();
                if(_sumMsg.length >= 3)
                    _sumtype = NumberTool.safeToInteger(_sumMsg[2], Integer.valueOf(WorkflowStepJudgeType.WF_JUDGE_VOTE.getEnumItemValue())).intValue();
                _stepend2.append(_sumren).append(" ").append(_sumpiao).append(" ").append(_sumtype).append(";");
                String _baseMsg[] = _endArr[1].split(",");
                for(int i = 0; i < _baseMsg.length; i++)
                {
                    String _baseArr[] = _baseMsg[i].split(" ");
                    if(_baseArr.length < 3)
                        continue;
                    int _basetype = NumberTool.safeToInteger(_baseArr[0], Integer.valueOf(0)).intValue();
                    int _baseren = NumberTool.safeToInteger(_baseArr[1], Integer.valueOf(0)).intValue();
                    double _basepiao = NumberTool.safeToDouble(_baseArr[2], Double.valueOf(0.0D)).doubleValue();
                    _basepiao *= _listsize;
                    if(i != 0)
                        _stepend2.append(",");
                    _stepend2.append(_basetype).append(" ").append(_baseren).append(" ").append(_basepiao);
                }

                _stepend2.append(";");
                String _judgeMsg[] = _endArr[2].split(",");
                for(int i = 0; i < _judgeMsg.length; i++)
                {
                    String _judgeArr[] = _judgeMsg[i].split(" ");
                    if(_judgeArr.length < 3)
                        continue;
                    int _judgeresult = NumberTool.safeToInteger(_judgeArr[0], Integer.valueOf(0)).intValue();
                    int _judgetype = NumberTool.safeToInteger(_judgeArr[1], Integer.valueOf(0)).intValue();
                    double _judgemark = NumberTool.safeToDouble(_judgeArr[2], Double.valueOf(0.0D)).doubleValue();
                    if(_judgetype == 4)
                        _judgemark *= _listsize;
                    if(i != 0)
                        _stepend2.append(",");
                    _stepend2.append(_judgeresult).append(" ").append(_judgetype).append(" ").append(_judgemark);
                }

            }
            List _ownerlist = _workapp.getExampleOwnerListByStepid(stepowner.getExampleStepId().intValue(), -1);
            for(int i = 0; i < _ownerlist.size(); i++)
            {
                Map _ownermap = (Map)_ownerlist.get(i);
                int _exampleownerid = NumberTool.convertMapKeyToInt(_ownermap, "exampleownerid", Integer.valueOf(0)).intValue();
                IWorkflowExampleStepOwner _stepowner2 = _workapp.loadExampleStepOwner(_exampleownerid);
                _stepowner2.setValue(Integer.valueOf(_stepowner2.getValue().intValue() * _listsize));
                _workapp.saveExampleStepOwner(_stepowner2);
            }

            for(int i = 0; i < _listsize; i++)
            {
                Map _ownermap = (Map)_stepownerlist.get(i);
                _ownermap.put("exampleid", stepowner.getExampleId());
                _ownermap.put("examplestepid", stepowner.getExampleStepId());
                _ownermap.put("value", Integer.valueOf(_basevalue));
                List _ownerlist2 = detailExampleOwner(_ownermap);
                for(int j = 0; j < _ownerlist2.size(); j++)
                {
                    Map _ownermap2 = (Map)_ownerlist2.get(j);
                    int _exampleownerid = saveExampleOwner(_ownermap2);
                    Map _mailmap = new HashMap();
                    _mailmap.put("exampleid", stepowner.getExampleId());
                    _mailmap.put("examplestepid", stepowner.getExampleStepId());
                    _mailmap.put("exampleownerid", Integer.valueOf(_exampleownerid));
                    int _ownerstatus = NumberTool.convertMapKeyToInt(_ownermap2, "status", Integer.valueOf(0)).intValue();
                    if(_ownerstatus == 0)
                        sendmessage(_mailmap);
                    _resultlist.add(_mailmap);
                }

            }

        }
        stepmap.put("stepend", _stepend2.toString());
        changemap.put("resultlist", _resultlist);
        return stepmap;
    }

    private Map changeStepmapByOwner(Map stepmap, IWorkflowExampleStepOwner stepowner)
    {
        String _stepend = StringUtil.safeToString(stepmap.get("stepend"), "");
        String _endArr[] = _stepend.split(";");
        StringBuffer _stepend2 = new StringBuffer();
        if(_endArr.length == 1)
        {
            if(_endArr.length > 0)
                _stepend2.append(_endArr[0]).append(";");
        } else
        if(_endArr.length == 2)
            _stepend2.append(_endArr[0]).append(";").append(_endArr[1]).append(";");
        else
        if(_endArr.length == 3)
        {
            _stepend2.append(_endArr[0]).append(";");
            String _baseMsg[] = _endArr[1].split(",");
            for(int i = 0; i < _baseMsg.length; i++)
            {
                String _baseArr[] = _baseMsg[i].split(" ");
                if(_baseArr.length < 3)
                    continue;
                int _basetype = NumberTool.safeToInteger(_baseArr[0], Integer.valueOf(0)).intValue();
                int _baseren = NumberTool.safeToInteger(_baseArr[1], Integer.valueOf(0)).intValue();
                int _basepiao = NumberTool.safeToInteger(_baseArr[2], Integer.valueOf(0)).intValue();
                if(_basetype == stepowner.getApproveStatus().intValue())
                {
                    _baseren++;
                    _basepiao += stepowner.getValue().intValue();
                }
                if(i != 0)
                    _stepend2.append(",");
                _stepend2.append(_basetype).append(" ").append(_baseren).append(" ").append(_basepiao);
            }

            _stepend2.append(";").append(_endArr[2]);
        }
        stepmap.put("condition", stepowner.getApproveStatus());
        stepmap.put("stepend", _stepend2.toString());
        return stepmap;
    }

    private Map changeExampleToMap(IWorkflowExample example)
    {
        Map _mainmap = new HashMap();
        _mainmap.put("exampleid", Integer.valueOf(example.getId()));
        _mainmap.put("processname", example.getProcessName());
        _mainmap.put("processid", example.getProcessId());
        _mainmap.put("starttime", example.getStartTime());
        _mainmap.put("status", example.getStatus());
        _mainmap.put("starterid", example.getStarterId());
        _mainmap.put("starterads", example.getStarterAds());
        _mainmap.put("brief", example.getBrief());
        return _mainmap;
    }

    private Map changeExampleStepToMap(IWorkflowExampleStep step)
    {
        Map _stepmap = new HashMap();
        _stepmap.put("examplestepid", Integer.valueOf(step.getId()));
        _stepmap.put("stepid", step.getProcessStepId());
        _stepmap.put("steptypeid", step.getStepType());
        _stepmap.put("stepend", step.getStepEnd());
        _stepmap.put("stepstatus", Integer.valueOf(2));
        _stepmap.put("stepbeigin", step.getStepBeigin());
        return _stepmap;
    }

    private Map changeExampleOwnerToMap(IWorkflowExampleStepOwner owner)
    {
        Map _ownermap = new HashMap();
        _ownermap.put("exampleownerid", Integer.valueOf(owner.getId()));
        _ownermap.put("ownerid", owner.getOwnerId());
        _ownermap.put("condition", owner.getApproveStatus());
        return _ownermap;
    }

    private List saveExampleSteps(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _stepmap = ((Map) (map.get("stepmap") != null ? (Map)map.get("stepmap") : ((Map) (new HashMap()))));
        int _targetsize = NumberTool.convertMapKeyToInt(map, "targetsize", Integer.valueOf(0)).intValue();
        int _exampleid = NumberTool.convertMapKeyToInt(_stepmap, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(_stepmap, "examplestepid", Integer.valueOf(0)).intValue();
        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
        String _stepend = StringUtil.safeToString(_stepmap.get("stepend"), "");
        _examplestep.setStepEnd(_stepend);
        int sptype = NumberTool.safeToInteger(map.get("sptype"), Integer.valueOf(0)).intValue();
        int i;
        if(_targetsize > 0)
        {
            int _stepcondition = NumberTool.convertMapKeyToInt(_stepmap, "stepcondition", Integer.valueOf(0)).intValue();
            int _stepstatus = NumberTool.convertMapKeyToInt(_stepmap, "stepstatus", Integer.valueOf(0)).intValue();
            _examplestep.setStepCondition(Integer.valueOf(_stepcondition));
            _examplestep.setStatus(Integer.valueOf(_stepstatus));
            _workapp.changeExampleStepO(_examplestepid, WorkflowStepOwnerStatus.WF_OWNER_BESKIP.getEnumItemValue());
            if(sptype == 1)
            {
                _examplestep.setRecoverFlag(Integer.valueOf(1));
                String spbrief = StringUtil.safeToString(map.get("spbrief"), "");
                if(spbrief.length() > 0)
                {
                    List _ownerlist = _workapp.getExampleOwnerList(_examplestepid);
                    for(i = 0; i < _ownerlist.size(); i++)
                    {
                        Map _ownermap = (Map)_ownerlist.get(i);
                        int _status = NumberTool.convertMapKeyToInt(_ownermap, "ownerstatus", Integer.valueOf(0)).intValue();
                        if(_status == 7)
                        {
                            int _exampleownerid = NumberTool.convertMapKeyToInt(_ownermap, "exampleownerid", Integer.valueOf(0)).intValue();
                            IWorkflowExampleStepOwner exampleowner = _workapp.loadExampleStepOwner(_exampleownerid);
                            exampleowner.setStatus(Integer.valueOf(WorkflowStepOwnerStatus.WF_OWNER_RECOVER.getEnumItemValue()));
                            exampleowner.setApproveOpinion(spbrief);
                            _workapp.saveExampleStepOwner(exampleowner);
                        }
                    }

                }
            }
            Map _mailmap = new HashMap();
            _mailmap.put("exampleid", _examplestep.getExampleId());
            _mailmap.put("examplestepid", Integer.valueOf(_examplestep.getId()));
            sendmessage(_mailmap);
        }
        _workapp.saveExampleStep(_examplestep);
        List _returnlist = new ArrayList();
        List _targetlist = ((List) (map.get("targetlist") != null ? (List)map.get("targetlist") : ((List) (new ArrayList()))));
        i = 0;
        do
        {
            if(i >= _targetsize)
                break;
            Map _targetmap = (Map)_targetlist.get(i);
            _targetmap.put("exampleid", Integer.valueOf(_exampleid));
            int _targetid = saveExampleStep(_targetmap);
            int _steptypeid = NumberTool.convertMapKeyToInt(_targetmap, "steptypeid", Integer.valueOf(0)).intValue();
            List _ownerlist = _workapp.getExampleOwnerList(_targetid);
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                _ownermap.put("exampleid", Integer.valueOf(_exampleid));
                _ownermap.put("examplestepid", Integer.valueOf(_targetid));
                _ownermap.put("examplesteptype", Integer.valueOf(_steptypeid));
                _returnlist.add(_ownermap);
            }

            IWorkflowExampleStepLink _steplink = _workapp.loadExampleStepLink(0);
            _steplink.setExampleId(Integer.valueOf(_exampleid));
            _steplink.setAftStepId(Integer.valueOf(_targetid));
            _steplink.setPreStepId(Integer.valueOf(_examplestepid));
            _steplink.setStatus(Integer.valueOf(1));
            _workapp.saveExampleStepLink(_steplink);
            int _targettype = NumberTool.convertMapKeyToInt(_targetmap, "steptypeid", Integer.valueOf(0)).intValue();
            if(_targettype == 2)
            {
                _workapp.changeExampleStep(_targetid, 2);
                Map _mailmap = new HashMap();
                _mailmap.put("exampleid", Integer.valueOf(_exampleid));
                _mailmap.put("examplestepid", Integer.valueOf(_targetid));
                _mailmap.put("examplesteptype", Integer.valueOf(_targettype));
                _mailmap.put("ownerstatus", Integer.valueOf(-1));
                _returnlist.add(_mailmap);
                _workapp.changeExampleS(_exampleid, 9);
                break;
            }
            i++;
        } while(true);
        return _returnlist;
    }

    private int saveExampleStep(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        if(_examplestepid == 0)
            _examplestepid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
        String _processstepname = StringUtil.safeToString(map.get("processstepname"), "");
        if(_processstepname.length() == 0)
            _processstepname = StringUtil.safeToString(map.get("stepname"), "");
        int _processstepid = NumberTool.convertMapKeyToInt(map, "processstepid", Integer.valueOf(0)).intValue();
        if(_processstepid == 0)
            _processstepid = NumberTool.convertMapKeyToInt(map, "stepid", Integer.valueOf(0)).intValue();
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        String _stepbeigin = StringUtil.safeToString(map.get("stepbeigin"), "");
        String _stepend = StringUtil.safeToString(map.get("stepend"), "");
        int _stepcondition = NumberTool.convertMapKeyToInt(map, "stepcondition", Integer.valueOf(0)).intValue();
        int _steptype = NumberTool.convertMapKeyToInt(map, "steptypeid", Integer.valueOf(0)).intValue();
        if(_steptype == 3 && _stepend.length() == 0)
            _stepend = StringUtil.safeToString(map.get("steptypeads"), "");
        String _actionurl = StringUtil.safeToString(map.get("actionurl"), "");
        String _showurl = StringUtil.safeToString(map.get("showurl"), "");
        IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
        if(_examplestep.getProcessStepName() == null || _examplestep.getProcessStepName().length() == 0)
            _examplestep.setProcessStepName(_processstepname);
        else
        if(_processstepname.length() > 0)
            _examplestep.setProcessStepName(_processstepname);
        if(_examplestep.getProcessStepId() == null || _examplestep.getProcessStepId().intValue() == 0)
            _examplestep.setProcessStepId(Integer.valueOf(_processstepid));
        else
        if(_processstepid > 0)
            _examplestep.setProcessStepId(Integer.valueOf(_processstepid));
        if(_examplestep.getProcessId() == null || _examplestep.getProcessId().intValue() == 0)
            _examplestep.setProcessId(Integer.valueOf(_processid));
        else
        if(_processid > 0)
            _examplestep.setProcessId(Integer.valueOf(_processid));
        if(_examplestep.getExampleId() == null || _examplestep.getExampleId().intValue() == 0)
            _examplestep.setExampleId(Integer.valueOf(_exampleid));
        else
        if(_exampleid > 0)
            _examplestep.setExampleId(Integer.valueOf(_exampleid));
        if(_examplestep.getBrief() == null || _examplestep.getBrief().length() == 0)
            _examplestep.setBrief(_brief);
        else
        if(_brief.length() > 0)
            _examplestep.setBrief(_brief);
        if(_examplestep.getStepType() == null || _examplestep.getStepType().intValue() == 0)
            _examplestep.setStepType(Integer.valueOf(_steptype));
        else
        if(_steptype > 0)
            _examplestep.setStepType(Integer.valueOf(_steptype));
        _examplestep.setStepBeigin(_stepbeigin);
        _examplestep.setStepEnd(_stepend);
        Map _paramap = _workapp.loadParaMap(_exampleid, 0, 0);
        if(_examplestep.getActionUrl() == null || _examplestep.getActionUrl().length() == 0)
        {
            _actionurl = changeUrlByMap(_actionurl, _paramap);
            _examplestep.setActionUrl(_actionurl);
        } else
        if(_actionurl.length() > 0)
        {
            _actionurl = changeUrlByMap(_actionurl, _paramap);
            _examplestep.setActionUrl(_actionurl);
        }
        if(_examplestep.getShowUrl() == null || _examplestep.getShowUrl().length() == 0)
        {
            _showurl = changeUrlByMap(_showurl, _paramap);
            _examplestep.setShowUrl(_showurl);
        } else
        if(_showurl.length() > 0)
        {
            _showurl = changeUrlByMap(_showurl, _paramap);
            _examplestep.setShowUrl(_showurl);
        }
        if(_status == -1)
            _examplestep.setStatus(Integer.valueOf(_status));
        else
        if(_status > 2)
        {
            _examplestep.setStatus(Integer.valueOf(_status));
            if(_examplestep.getStartTime() == null)
            {
                Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : new Date();
                _examplestep.setStartTime(_starttime);
            }
            Date _endtime = map.get("endtime") != null ? (Date)map.get("endtime") : new Date();
            _examplestep.setEndTime(_endtime);
            _examplestep.setStepCondition(Integer.valueOf(_stepcondition));
        } else
        {
            Date _oldtime = new Date();
            if(_examplestep.getStartTime() != null)
                _oldtime = _examplestep.getStartTime();
            if(_examplestep.getStatus() == null || _examplestep.getStatus().intValue() == -1)
            {
                Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : new Date();
                _examplestep.setStartTime(_starttime);
            } else
            {
                Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : _oldtime;
                _examplestep.setStartTime(_starttime);
            }
            _examplestep.setStatus(Integer.valueOf(_status));
        }
        int _newstepid = _workapp.saveExampleStep(_examplestep);
        _examplestep = _workapp.loadExampleStep(_newstepid);
        List _ownerlist = ((List) (map.get("ownerlist") != null ? (List)map.get("ownerlist") : ((List) (new ArrayList()))));
        int _ownernum = 0;
        int _ownervalue = 0;
        for(int i = 0; i < _ownerlist.size(); i++)
        {
            Map _ownermap = (Map)_ownerlist.get(i);
            _ownermap.put("exampleid", _examplestep.getExampleId());
            _ownermap.put("examplestepid", Integer.valueOf(_newstepid));
            int _value = NumberTool.convertMapKeyToInt(_ownermap, "value", Integer.valueOf(1)).intValue();
            List _ownerlist2 = detailExampleOwner(_ownermap);
            int _marknum = 0;
            for(int j = 0; j < _ownerlist2.size(); j++)
            {
                Map _ownermap2 = (Map)_ownerlist2.get(j);
                int _exampleownerid = saveExampleOwner(_ownermap2);
                int _ownerstatus = NumberTool.convertMapKeyToInt(_ownermap2, "status", Integer.valueOf(0)).intValue();
                if(_ownerstatus == 0)
                    _marknum++;
            }

            _ownernum += _marknum;
            _ownervalue += _value * _marknum;
        }

        if(_examplestep.getStepType().intValue() == 3 && _examplestep.getStepEnd() != null)
        {
            String _stepend2 = changeTypeAdsToEnd(_examplestep.getStepEnd(), _ownernum, _ownervalue);
            _examplestep.setStepEnd(_stepend2);
            _workapp.saveExampleStep(_examplestep);
        }
        return _newstepid;
    }

    private int sendmessage(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IOrgService _orgService = OrgServiceFactory.getOrgService();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int _exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        List _receiverlist = new ArrayList();
        MessageContext _context = new MessageContext();
        Map _paramap = _workapp.loadParaMap(_exampleid, 0, 0);
        _context.setParams(_paramap);
        String _mailcode = "";
        if(_examplestepid != 0)
        {
            Map _allparamap = _workbase.findAllParaMap();
            IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
            IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_examplestep.getProcessStepId().intValue());
            String _stepcondition = StringUtil.safeToString(_processstep.getCondition(), "");
            String _stepconditionArr[] = _stepcondition.split(";");
            if(_exampleownerid != 0)
            {
                if(_stepconditionArr.length >= 1 && _stepconditionArr[0].length() > 0)
                {
                    _mailcode = _stepconditionArr[0];
                    IWorkflowExampleStepOwner _exampleowner = _workapp.loadExampleStepOwner(_exampleownerid);
                    int _ownerid = _exampleowner.getOwnerId().intValue();
                    Employee _ownerer = _orgService.getEmployeeById(Integer.toString(_ownerid));
                    MessageReceiver _receiver = new MessageReceiver();
                    _receiver.setEmail(_ownerer.getEmail());
                    _receiver.setEmpId(Integer.toString(_ownerid));
                    _receiverlist.add(_receiver);
                } else
                {
                    return 0;
                }
            } else
            if(_stepconditionArr.length >= 4 && _stepconditionArr[3].length() > 0)
            {
                _mailcode = _stepconditionArr[3];
                if(_stepconditionArr.length >= 5)
                {
                    List _stepownerlist = _workbase.getOwnerListByGoto(0, _examplestep.getProcessStepId().intValue(), -2);
                    if(_stepconditionArr.length >= 6)
                    {
                        int _spmark = NumberTool.safeToInteger(_stepconditionArr[5], Integer.valueOf(0)).intValue();
                        if(_spmark > 0)
                        {
                            _paramap.put("exampleid", Integer.valueOf(_exampleid));
                            _paramap.put("examplestepid", Integer.valueOf(_examplestepid));
                            _paramap.put("stepownerlist", _stepownerlist);
                            Map _returnmap = WorkflowServiceFactory.getWorkflowInService().judgeSpecialDealer(_spmark, _paramap);
                            _stepownerlist = _returnmap.get("stepownerlist") != null ? (List)_returnmap.get("stepownerlist") : _stepownerlist;
                        }
                    }
                    for(int si = 0; si < _stepownerlist.size(); si++)
                    {
                        Map _stepownermap = (Map)_stepownerlist.get(si);
                        int _orgtype = NumberTool.convertMapKeyToInt(_stepownermap, "orgtype", Integer.valueOf(0)).intValue();
                        int _orgid = NumberTool.convertMapKeyToInt(_stepownermap, "orgid", Integer.valueOf(0)).intValue();
                        if(_orgtype == 8)
                        {
                            Employee _ownerer = _orgService.getEmployeeById((new StringBuilder()).append("").append(_orgid).toString());
                            if(_ownerer != null)
                            {
                                MessageReceiver _receiver = new MessageReceiver();
                                _receiver.setEmail(_ownerer.getEmail());
                                _receiver.setEmpId((new StringBuilder()).append("").append(_orgid).toString());
                                _receiverlist.add(_receiver);
                            }
                        } else
                        if(_orgtype > 0)
                        {
                            List _employeelist = _orgService.findEmployeesByOrgTypeOrgId(_orgtype, (new StringBuilder()).append("").append(_orgid).toString());
                            for(int ei = 0; ei < _employeelist.size(); ei++)
                            {
                                Employee _ownerer = (Employee)_employeelist.get(ei);
                                if(_ownerer != null)
                                {
                                    MessageReceiver _receiver = new MessageReceiver();
                                    _receiver.setEmail(_ownerer.getEmail());
                                    _receiver.setEmpId(_ownerer.getId());
                                    _receiverlist.add(_receiver);
                                }
                            }

                        }
                    }

                }
            } else
            {
                return 0;
            }
            if(_exampleid != 0)
            {
                IWorkflowExample _example = _workapp.loadExample(_exampleid);
                _context.addParam("wfprocessbrief", _example.getBrief());
                _context.addParam("wfprocessname", _example.getProcessName());
                _context.addParam("wfstarttime", changeDateToString(_example.getStartTime()));
                int _starterid = _example.getStarterId().intValue();
                Employee _starter = _orgService.getEmployeeById(Integer.toString(_starterid));
                _context.addParam("wfstartername", _starter.getEmpName());
                IWorkflowProcess _process = _workbase.loadProcess(_example.getProcessId().intValue());
                int _processtype = _process.getProcessType().intValue();
                _context.addParam("wfprocesstype", _allparamap.get((new StringBuilder()).append("ProcessType").append(_processtype).toString()));
            }
            _context.addParam("wfstepname", _examplestep.getProcessStepName());
            _context.addParam("wfstepbrief", _examplestep.getBrief());
            int _steptype = _examplestep.getStepType().intValue();
            _context.addParam("wfsteptype", _allparamap.get((new StringBuilder()).append("StepType").append(_steptype).toString()));
            StringBuffer _actionurl = new StringBuffer();
            StringBuffer _showurl = new StringBuffer();
            _actionurl.append(_examplestep.getActionUrl()).append("?exampleid=").append(_exampleid);
            _actionurl.append("&examplestepid=").append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
            _showurl.append(_examplestep.getShowUrl()).append("?exampleid=").append(_exampleid);
            _showurl.append("&examplestepid=").append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
            _context.addParam("wfactionurl", _actionurl.toString());
            _context.addParam("wfshowurl", _showurl.toString());
        } else
        {
            return 0;
        }
        _context.setCode(_mailcode);
        _context.setReceivers(_receiverlist);
        try
        {
            _context.addParam("templatetypeid", Integer.valueOf(50));
            _context.addParam("operateurl", _context.getParam("wfactionurl"));
            CommonServiceFactory.getMessageEngineService().asynSendMessage(_context);
            LOGGER.info((new StringBuilder()).append("\u6D88\u606F\u6A21\u677F").append(_mailcode).append("\u6210\u529F\u8C03\u7528\uFF0C\u6536\u4EF6\u4EBA\u589E\u52A0\u6570\uFF1A").append(_receiverlist.size()).toString());
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u6D88\u606F\u6A21\u677F").append(_mailcode).append("\u8C03\u7528\u5931\u8D25").toString(), e);
        }
        return 1;
    }

    private String changeTypeAdsToEnd(String steptypeads, int ownernum, int ownervalue)
    {
        StringBuffer _stepend2 = new StringBuffer();
        String _stependArr[] = steptypeads.split(" ");
        if(_stependArr.length == 1)
        {
            _stepend2.append(ownernum).append(" ").append(ownervalue).append(" ").append(WorkflowStepJudgeType.WF_JUDGE_SEIZE.getEnumItemValue()).append(";");
            _stepend2.append("1 0 0,").append("2 0 0;");
            _stepend2.append("1 2 1,").append("2 2 1;");
        } else
        if(_stependArr.length == 2)
        {
            int _mark = NumberTool.safeToInteger(_stependArr[0], Integer.valueOf(0)).intValue();
            int _type = NumberTool.safeToInteger(_stependArr[1], Integer.valueOf(0)).intValue();
            _stepend2.append(_mark).append(";").append(_type).append(";");
        } else
        if(_stependArr.length == 3)
        {
            _stepend2.append(ownernum).append(" ").append(ownervalue).append(" ").append(_stependArr[0]).append(";");
            _stepend2.append("1 0 0,").append("2 0 0;");
            double _judgeads1 = NumberTool.safeToDouble(_stependArr[2], Double.valueOf(0.0D)).doubleValue();
            double _judgeads2 = 0.0D;
            int _judgetype = NumberTool.safeToInteger(_stependArr[1], Integer.valueOf(0)).intValue();
            if(_judgetype % 2 == 1)
                _judgeads2 = (100D - _judgeads1) + 0.10000000000000001D;
            else
            if(_judgetype == 2)
                _judgeads2 = ((double)ownernum - _judgeads1) + 0.10000000000000001D;
            else
            if(_judgetype == 4)
                _judgeads2 = ((double)ownervalue - _judgeads1) + 0.10000000000000001D;
            _stepend2.append("1 ").append(_judgetype).append(" ").append(_judgeads1).append(",");
            _stepend2.append("2 ").append(_judgetype).append(" ").append(_judgeads2).append(";");
        }
        return _stepend2.toString();
    }

    private List detailExampleOwner(Map map)
    {
        IOrgService _orgService = OrgServiceFactory.getOrgService();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List _ownerlist = new ArrayList();
        int _objid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int _ownerid = NumberTool.convertMapKeyToInt(map, "orgid", Integer.valueOf(0)).intValue();
        int _ownertype = NumberTool.convertMapKeyToInt(map, "orgtype", Integer.valueOf(0)).intValue();
        int _value = NumberTool.convertMapKeyToInt(map, "value", Integer.valueOf(1)).intValue();
        String _datefrom = StringUtil.safeToString(map.get("datefrom"), "");
        IWorkflowExampleStep _step = _workapp.loadExampleStep(_examplestepid);
        IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_step.getProcessStepId().intValue());
        IWorkflowProcess _process = _workbase.loadProcess(_step.getProcessId().intValue());
        List _employeelist = _orgService.findEmployeesByOrgTypeOrgId(_ownertype, Integer.toString(_ownerid));
        for(int i = 0; i < _employeelist.size(); i++)
        {
            Employee _employee = (Employee)_employeelist.get(i);
            Map _ownermap = new HashMap();
            _ownermap.put("exampleid", Integer.valueOf(_exampleid));
            _ownermap.put("examplestepid", Integer.valueOf(_examplestepid));
            _ownermap.put("ownerid", _employee.getId());
            _ownermap.put("preownerid", Integer.valueOf(0));
            _ownermap.put("value", Integer.valueOf(_value));
            _ownermap.put("approvebrief", (new StringBuilder()).append(_ownerid).append(";").append(_ownertype).append(";").append(_datefrom).append(";").toString());
            _ownerlist.add(_ownermap);
            List _agentslist = _workapp.getAgentsByOwnerAndId(_step.getProcessStepId().intValue(), NumberTool.safeToInteger(_employee.getId(), Integer.valueOf(0)).intValue(), NumberTool.safeToInteger(_process.getSysCode(), Integer.valueOf(0)).intValue(), NumberTool.safeToInteger(_processstep.getStepCode(), Integer.valueOf(0)).intValue());
            if(_agentslist.size() <= 0)
                continue;
            Map _agentsmap = (Map)_agentslist.get(0);
            int _agentsid = NumberTool.convertMapKeyToInt(_agentsmap, "agentsid", Integer.valueOf(0)).intValue();
            if(_agentsid != 0)
            {
                String _agentsname = StringUtil.safeToString(_agentsmap.get("agentsname"), "");
                _agentsmap.put("exampleid", Integer.valueOf(_exampleid));
                _agentsmap.put("examplestepid", Integer.valueOf(_examplestepid));
                _agentsmap.put("ownerid", Integer.valueOf(_agentsid));
                _agentsmap.put("preownerid", _employee.getId());
                _agentsmap.put("value", Integer.valueOf(_value));
                _agentsmap.put("status", Integer.valueOf(0));
                _agentsmap.put("approvebrief", (new StringBuilder()).append(_ownerid).append(";").append(_ownertype).append(";").append(_datefrom).append(";").toString());
                _ownerlist.add(_agentsmap);
                _ownermap.put("status", Integer.valueOf(WorkflowStepOwnerStatus.WF_OWNER_AGENTS.getEnumItemValue()));
                _ownermap.put("approveopinion", (new StringBuilder()).append("\u5DF2\u4EE3\u7406\u7ED9").append(_agentsname).toString());
            }
        }

        return _ownerlist;
    }

    private int saveExampleOwner(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int _exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        if(_exampleownerid == 0)
            _exampleownerid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int _ownerid = NumberTool.convertMapKeyToInt(map, "ownerid", Integer.valueOf(0)).intValue();
        int _preownerid = NumberTool.convertMapKeyToInt(map, "preownerid", Integer.valueOf(0)).intValue();
        int _value = NumberTool.convertMapKeyToInt(map, "value", Integer.valueOf(0)).intValue();
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        Date _approvetime = map.get("approvetime") != null ? (Date)map.get("approvetime") : new Date();
        int _approvestatus = NumberTool.convertMapKeyToInt(map, "approvestatus", Integer.valueOf(-1)).intValue();
        if(_approvestatus == -1)
            _approvestatus = NumberTool.convertMapKeyToInt(map, "condition", Integer.valueOf(-1)).intValue();
        String _approveopinion = StringUtil.safeToString(map.get("approveopinion"), "");
        String _approvebrief = StringUtil.safeToString(map.get("approvebrief"), "");
        IWorkflowExampleStepOwner _stepowner = _workapp.loadExampleStepOwner(_exampleownerid);
        if(_stepowner.getExampleId() == null)
            _stepowner.setExampleId(Integer.valueOf(_exampleid));
        else
        if(_exampleid != 0)
            _stepowner.setExampleId(Integer.valueOf(_exampleid));
        if(_stepowner.getExampleStepId() == null)
            _stepowner.setExampleStepId(Integer.valueOf(_examplestepid));
        else
        if(_examplestepid != 0)
            _stepowner.setExampleStepId(Integer.valueOf(_examplestepid));
        if(_stepowner.getOwnerId() == null)
            _stepowner.setOwnerId(Integer.valueOf(_ownerid));
        else
        if(_ownerid != 0)
            _stepowner.setOwnerId(Integer.valueOf(_ownerid));
        if(_stepowner.getPreOwnerId() == null)
            _stepowner.setPreOwnerId(Integer.valueOf(_preownerid));
        else
        if(_preownerid != 0)
            _stepowner.setPreOwnerId(Integer.valueOf(_preownerid));
        if(_stepowner.getValue() == null)
        {
            if(_value == 0)
                _stepowner.setValue(Integer.valueOf(1));
            else
                _stepowner.setValue(Integer.valueOf(_value));
        } else
        if(_value != 0)
            _stepowner.setValue(Integer.valueOf(_value));
        _stepowner.setStatus(Integer.valueOf(_status));
        if(_status >= 2)
            _stepowner.setApproveTime(_approvetime);
        Date _oldtime = new Date();
        if(_stepowner.getStartTime() != null)
            _oldtime = _stepowner.getStartTime();
        Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : _oldtime;
        _stepowner.setStartTime(_starttime);
        if(_status == 100)
            _stepowner.setApproveTime(_approvetime);
        if(_approvestatus >= 0)
        {
            _stepowner.setApproveTime(_approvetime);
            _stepowner.setApproveStatus(Integer.valueOf(_approvestatus));
        }
        if(_stepowner.getApproveOpinion() == null)
            _stepowner.setApproveOpinion(_approveopinion);
        else
        if(_approveopinion.length() > 0)
            _stepowner.setApproveOpinion(_approveopinion);
        if(_stepowner.getApproveBrief() == null)
            _stepowner.setApproveBrief(_approvebrief);
        else
        if(_approvebrief.length() > 0)
            _stepowner.setApproveBrief(_approvebrief);
        return _workapp.saveExampleStepOwner(_stepowner);
    }

    private int changeExample(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int marknum = 0;
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        if(_brief.length() > 0)
        {
            IWorkflowExample _example = _workapp.loadExample(_exampleid);
            IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
            String _oldbrief = StringUtil.safeToString(_example.getBrief(), "");
            _examplestep.setBrief(_oldbrief);
            _workapp.saveExampleStep(_examplestep);
            _example.setBrief(_brief);
            _workapp.saveExample(_example);
            marknum++;
        }
        return marknum;
    }

    private int saveExample(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        WorkflowPublicString a = new WorkflowPublicString();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
        String _processname = StringUtil.safeToString(map.get("processname"), "");
        int _processid = NumberTool.convertMapKeyToInt(map, "processid", Integer.valueOf(0)).intValue();
        String _syscode = StringUtil.safeToString(map.get("syscode"), "");
        String _processcode = StringUtil.safeToString(map.get("processcode"), "");
        int _processversion = NumberTool.convertMapKeyToInt(map, "processversion", Integer.valueOf(0)).intValue();
        if(_processid == 0 && _syscode.length() > 0)
        {
            Map _newprocessmap = _workengine.getProcessBySyscode(_syscode);
            if(_newprocessmap != null)
            {
                int _status = NumberTool.convertMapKeyToInt(_newprocessmap, "status", Integer.valueOf(0)).intValue();
                if(_status == 100)
                    return 0;
                _processid = NumberTool.convertMapKeyToInt(_newprocessmap, "processid", Integer.valueOf(0)).intValue();
                if(_processname.length() == 0)
                    _processname = StringUtil.safeToString(_newprocessmap.get("processname"), "");
            }
        }
        if(_processid == 0)
        {
            if(_processcode.length() == 0)
                return 0;
            Map _newprocessmap = _workengine.getProcessByCodeAndVersion(_processcode, _processversion);
            if(_newprocessmap == null)
                return 0;
            _processid = NumberTool.convertMapKeyToInt(_newprocessmap, "processid", Integer.valueOf(0)).intValue();
            if(_processname.length() == 0)
                _processname = StringUtil.safeToString(_newprocessmap.get("processname"), "");
        } else
        if(_processname.length() == 0)
        {
            IWorkflowProcess _process = _workengine.getProcessById(_processid);
            _processname = _process.getName();
        }
        int _starterid = NumberTool.convertMapKeyToInt(map, "starterid", Integer.valueOf(0)).intValue();
        String _starterads = StringUtil.safeToString(map.get("starterads"), "");
        String _brief = StringUtil.safeToString(map.get("brief"), "");
        int _status = NumberTool.convertMapKeyToInt(map, "status", Integer.valueOf(0)).intValue();
        IWorkflowExample _example = _workapp.loadExample(_exampleid);
        if((_example.getBrief() == null || _example.getBrief().length() == 0) && _brief.length() == 0)
            _brief = "\u65E0\u63CF\u8FF0";
        if(_example.getProcessName() == null)
            _example.setProcessName(_processname);
        else
        if(_processname.length() > 0)
            _example.setProcessName(_processname);
        if(_example.getProcessId() == null)
            _example.setProcessId(Integer.valueOf(_processid));
        else
        if(_processid > 0)
            _example.setProcessId(Integer.valueOf(_processid));
        Date _oldtime = new Date();
        if(_example.getStartTime() != null)
            _oldtime = _example.getStartTime();
        Date _starttime = map.get("starttime") != null ? (Date)map.get("starttime") : _oldtime;
        if(_example.getStartTime() == null)
            _example.setStartTime(_starttime);
        if(_example.getStarterId() == null)
            _example.setStarterId(Integer.valueOf(_starterid));
        if(_example.getStarterAds() == null)
            _example.setStarterAds(_starterads);
        else
        if(_starterads.length() > 0)
            _example.setStarterAds(_starterads);
        if(_example.getBrief() == null)
            _example.setBrief(_brief);
        else
        if(_brief.length() > a.getBreifMaxLength())
            _example.setBrief(_brief.substring(0, a.getBreifMaxLength()));
        else
        if(_brief.length() > 0)
            _example.setBrief(_brief);
        if(_example.getStatus() == null)
            _example.setStatus(Integer.valueOf(_status));
        else
        if(_status > 0)
            _example.setStatus(Integer.valueOf(_status));
        return _workapp.saveExample(_example);
    }

    public Map startNewProcess(Map map)
    {
        return null;
    }

    public Map subNewProcessStep(Map map)
    {
        return null;
    }

    public List getNowStepList()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getNowExampleList();
        return changeNowStepListToShow(_examplelist);
    }

    public List getNowStepList(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getNowExampleList(map);
        return changeNowStepListToShow(_examplelist);
    }

    public List getMyInitiateProcessList(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getMyInitiateProcessList(map);
        return changeNowStepListToShow(_examplelist);
    }

    public List getMyRecoverProcessList(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getMyRecoverProcessList(map);
        return changeHisStepListToShow(_examplelist);
    }

    private List changeNowStepListToShow(List list)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        List _steplist = new ArrayList();
        for(int j = 0; j < list.size(); j++)
        {
            Map _stepmap = new HashMap();
            Map _examplemap2 = (Map)list.get(j);
            int _exampleid = NumberTool.convertMapKeyToInt(_examplemap2, "exampleid", Integer.valueOf(0)).intValue();
            String _processname = StringUtil.safeToString(_examplemap2.get("processname"), "");
            String _processstatusvalue = StringUtil.safeToString(_examplemap2.get("processstatusvalue"), "");
            String _processbrief = StringUtil.safeToString(_examplemap2.get("processbrief"), "");
            int _examplestepid = NumberTool.convertMapKeyToInt(_examplemap2, "examplestepid", Integer.valueOf(0)).intValue();
            String _processstepname = StringUtil.safeToString(_examplemap2.get("processstepname"), "");
            String _steptypevalue = StringUtil.safeToString(_examplemap2.get("steptypevalue"), "");
            int _starterid = NumberTool.convertMapKeyToInt(_examplemap2, "starterid", Integer.valueOf(0)).intValue();
            String _actionurl = StringUtil.safeToString(_examplemap2.get("actionurl"), "");
            String _showurl = StringUtil.safeToString(_examplemap2.get("showurl"), "");
            String _showurl2 = contectShowUrl(_examplemap2);
            String _starttime = "";
            if(_examplemap2.get("starttime") == null)
                _starttime = a.getFirstTime();
            else
                _starttime = changeDateToString((Date)_examplemap2.get("starttime"));
            String _endtime = "";
            if(_examplemap2.get("endtime") == null)
                _endtime = a.getLastTime();
            else
                _endtime = changeDateToString((Date)_examplemap2.get("endtime"));
            String _stepstarttime = "";
            if(_examplemap2.get("stepstarttime") == null)
                _stepstarttime = a.getFirstTime();
            else
                _stepstarttime = changeDateToString((Date)_examplemap2.get("stepstarttime"));
            StringBuffer _ownernames = new StringBuffer();
            StringBuffer _ownerids = new StringBuffer();
            StringBuffer _preownerids = new StringBuffer();
            List _ownerlist = new ArrayList();
            int k;
            for(k = j; k < list.size(); k++)
            {
                Map _ownermap = new HashMap();
                Map _examplemap3 = (Map)list.get(k);
                int _examplestepid2 = NumberTool.convertMapKeyToInt(_examplemap3, "examplestepid", Integer.valueOf(0)).intValue();
                if(_examplestepid2 != _examplestepid)
                    break;
                int _exampleownerid = NumberTool.convertMapKeyToInt(_examplemap3, "exampleownerid", Integer.valueOf(0)).intValue();
                int _ownerid = NumberTool.convertMapKeyToInt(_examplemap3, "ownerid", Integer.valueOf(-1)).intValue();
                int _preownerid = NumberTool.convertMapKeyToInt(_examplemap3, "preownerid", Integer.valueOf(-1)).intValue();
                String _ownerstatusvalue = StringUtil.safeToString(_examplemap3.get("ownerstatusvalue"), "");
                String _ownername = StringUtil.safeToString(_examplemap3.get("ownername"), "");
                String _preownername = StringUtil.safeToString(_examplemap3.get("preownername"), "");
                int _ownerstatus = NumberTool.convertMapKeyToInt(_examplemap3, "ownerstatus", Integer.valueOf(0)).intValue();
                if(_ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue() && _ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue() && _ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_BEING.getEnumItemValue() && _ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_AUTO.getEnumItemValue())
                    continue;
                if(_ownername.length() > 0 && _ownerstatus < 100)
                {
                    if(_ownernames.length() > 0)
                        _ownernames.append("\uFF0C");
                    if(_preownername.length() > 0)
                        _ownernames.append(_ownername).append("\uFF08").append(_preownername).append("\uFF09");
                    else
                        _ownernames.append(_ownername);
                }
                if(_ownerid >= 0)
                {
                    if(_ownerids.length() > 0)
                    {
                        _ownerids.append(",");
                        _preownerids.append(",");
                    }
                    _ownerids.append(_ownerid);
                    _preownerids.append(_preownerid);
                }
                _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
                if(_preownername.length() > 0)
                    _ownermap.put("ownername", (new StringBuilder()).append(_ownername).append("(").append(_preownername).append(")").toString());
                else
                    _ownermap.put("ownername", _ownername);
                _ownermap.put("ownerstatusvalue", _ownerstatusvalue);
                _ownerlist.add(_ownermap);
            }

            if(k > j)
                j = k - 1;
            _stepmap.put("startername", _examplemap2.get("startername"));
            _stepmap.put("starterid", Integer.valueOf(_starterid));
            _stepmap.put("ownernames", _ownernames.toString());
            _stepmap.put("ownerids", _ownerids.toString());
            _stepmap.put("preownerids", _preownerids.toString());
            _stepmap.put("actionurl", _actionurl);
            _stepmap.put("showurl", _showurl);
            _stepmap.put("showurl2", _showurl2);
            _stepmap.put("starttime", _starttime);
            _stepmap.put("endtime", _endtime);
            _stepmap.put("stepstarttime", _stepstarttime);
            _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
            _stepmap.put("processstepname", _processstepname);
            _stepmap.put("steptypevalue", _steptypevalue);
            String ownername = "";
            String ownerstatusvalue = "";
            String exampleownerid = "";
            for(Iterator i$ = _ownerlist.iterator(); i$.hasNext();)
            {
                Map map = (Map)i$.next();
                String _ownername = map.get("ownername") != null ? map.get("ownername").toString() : "";
                String _ownerstatusvalue = map.get("ownerstatusvalue") != null ? map.get("ownerstatusvalue").toString() : "";
                String _exampleownerid = map.get("exampleownerid") != null ? map.get("exampleownerid").toString() : "";
                ownername = "".equals(ownername) ? (new StringBuilder()).append(_ownername).append("*").toString() : (new StringBuilder()).append(ownername).append(_ownername).append("*").toString();
                ownerstatusvalue = "".equals(ownerstatusvalue) ? (new StringBuilder()).append(_ownerstatusvalue).append("*").toString() : (new StringBuilder()).append(ownerstatusvalue).append(_ownerstatusvalue).append("*").toString();
                exampleownerid = "".equals(exampleownerid) ? (new StringBuilder()).append(_exampleownerid).append("*").toString() : (new StringBuilder()).append(exampleownerid).append(_exampleownerid).append("*").toString();
            }

            _stepmap.put("ownername", ownername);
            _stepmap.put("ownerstatusvalue", ownerstatusvalue);
            _stepmap.put("exampleownerid", exampleownerid);
            _stepmap.put("ownerlist", _ownerlist);
            _stepmap.put("exampleid", Integer.valueOf(_exampleid));
            _stepmap.put("objid", Integer.valueOf(_exampleid));
            _stepmap.put("processbrief", _processbrief);
            _stepmap.put("processname", _processname);
            _stepmap.put("processstatusvalue", _processstatusvalue);
            _steplist.add(_stepmap);
        }

        return _steplist;
    }

    private List changeHisStepListToShow(List list)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        List _steplist = new ArrayList();
        for(int j = 0; j < list.size(); j++)
        {
            Map _stepmap = new HashMap();
            Map _examplemap2 = (Map)list.get(j);
            int _exampleid = NumberTool.convertMapKeyToInt(_examplemap2, "exampleid", Integer.valueOf(0)).intValue();
            String _processname = StringUtil.safeToString(_examplemap2.get("processname"), "");
            String _processstatusvalue = StringUtil.safeToString(_examplemap2.get("processstatusvalue"), "");
            String _processbrief = StringUtil.safeToString(_examplemap2.get("processbrief"), "");
            int _examplestepid = NumberTool.convertMapKeyToInt(_examplemap2, "examplestepid", Integer.valueOf(0)).intValue();
            String _processstepname = StringUtil.safeToString(_examplemap2.get("processstepname"), "");
            String _steptypevalue = StringUtil.safeToString(_examplemap2.get("steptypevalue"), "");
            int _starterid = NumberTool.convertMapKeyToInt(_examplemap2, "starterid", Integer.valueOf(0)).intValue();
            String _actionurl = StringUtil.safeToString(_examplemap2.get("actionurl"), "");
            String _showurl = StringUtil.safeToString(_examplemap2.get("showurl"), "");
            String _showurl2 = contectShowUrl(_examplemap2);
            String _starttime = "";
            if(_examplemap2.get("starttime") == null)
                _starttime = a.getFirstTime();
            else
                _starttime = changeDateToString((Date)_examplemap2.get("starttime"));
            String _endtime = "";
            if(_examplemap2.get("endtime") == null)
                _endtime = a.getLastTime();
            else
                _endtime = changeDateToString((Date)_examplemap2.get("endtime"));
            String _stepstarttime = "";
            if(_examplemap2.get("stepstarttime") == null)
                _stepstarttime = a.getFirstTime();
            else
                _stepstarttime = changeDateToString((Date)_examplemap2.get("stepstarttime"));
            StringBuffer _ownernames = new StringBuffer();
            StringBuffer _ownerids = new StringBuffer();
            StringBuffer _preownerids = new StringBuffer();
            List _ownerlist = new ArrayList();
            int k;
            for(k = j; k < list.size(); k++)
            {
                Map _ownermap = new HashMap();
                Map _examplemap3 = (Map)list.get(k);
                int _examplestepid2 = NumberTool.convertMapKeyToInt(_examplemap3, "examplestepid", Integer.valueOf(0)).intValue();
                if(_examplestepid2 != _examplestepid)
                    break;
                int _exampleownerid = NumberTool.convertMapKeyToInt(_examplemap3, "exampleownerid", Integer.valueOf(0)).intValue();
                int _ownerid = NumberTool.convertMapKeyToInt(_examplemap3, "ownerid", Integer.valueOf(-1)).intValue();
                int _preownerid = NumberTool.convertMapKeyToInt(_examplemap3, "preownerid", Integer.valueOf(-1)).intValue();
                String _ownerstatusvalue = StringUtil.safeToString(_examplemap3.get("ownerstatusvalue"), "");
                String _ownername = StringUtil.safeToString(_examplemap3.get("ownername"), "");
                String _preownername = StringUtil.safeToString(_examplemap3.get("preownername"), "");
                int _ownerstatus = NumberTool.convertMapKeyToInt(_examplemap3, "ownerstatus", Integer.valueOf(0)).intValue();
                if(_ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_TRANSFER.getEnumItemValue() && _ownerstatus != WorkflowStepOwnerStatus.WF_OWNER_AGENTS.getEnumItemValue())
                    continue;
                if(_ownername.length() > 0 && _ownerstatus < 100)
                {
                    if(_ownernames.length() > 0)
                        _ownernames.append("\uFF0C");
                    if(_preownername.length() > 0)
                        _ownernames.append(_ownername).append("\uFF08").append(_preownername).append("\uFF09");
                    else
                        _ownernames.append(_ownername);
                }
                if(_ownerid >= 0)
                {
                    if(_ownerids.length() > 0)
                    {
                        _ownerids.append(",");
                        _preownerids.append(",");
                    }
                    _ownerids.append(_ownerid);
                    _preownerids.append(_preownerid);
                }
                _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
                if(_preownername.length() > 0)
                    _ownermap.put("ownername", (new StringBuilder()).append(_ownername).append("(").append(_preownername).append(")").toString());
                else
                    _ownermap.put("ownername", _ownername);
                _ownermap.put("ownerstatusvalue", _ownerstatusvalue);
                _ownerlist.add(_ownermap);
            }

            if(k > j)
                j = k - 1;
            _stepmap.put("startername", _examplemap2.get("startername"));
            _stepmap.put("starterid", Integer.valueOf(_starterid));
            _stepmap.put("ownernames", _ownernames.toString());
            _stepmap.put("ownerids", _ownerids.toString());
            _stepmap.put("preownerids", _preownerids.toString());
            _stepmap.put("actionurl", _actionurl);
            _stepmap.put("showurl", _showurl);
            _stepmap.put("showurl2", _showurl2);
            _stepmap.put("starttime", _starttime);
            _stepmap.put("endtime", _endtime);
            _stepmap.put("stepstarttime", _stepstarttime);
            _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
            _stepmap.put("processstepname", _processstepname);
            _stepmap.put("steptypevalue", _steptypevalue);
            String ownername = "";
            String ownerstatusvalue = "";
            String exampleownerid = "";
            for(Iterator i$ = _ownerlist.iterator(); i$.hasNext();)
            {
                Map map = (Map)i$.next();
                String _ownername = map.get("ownername") != null ? map.get("ownername").toString() : "";
                String _ownerstatusvalue = map.get("ownerstatusvalue") != null ? map.get("ownerstatusvalue").toString() : "";
                String _exampleownerid = map.get("exampleownerid") != null ? map.get("exampleownerid").toString() : "";
                ownername = "".equals(ownername) ? (new StringBuilder()).append(_ownername).append("*").toString() : (new StringBuilder()).append(ownername).append(_ownername).append("*").toString();
                ownerstatusvalue = "".equals(ownerstatusvalue) ? (new StringBuilder()).append(_ownerstatusvalue).append("*").toString() : (new StringBuilder()).append(ownerstatusvalue).append(_ownerstatusvalue).append("*").toString();
                exampleownerid = "".equals(exampleownerid) ? (new StringBuilder()).append(_exampleownerid).append("*").toString() : (new StringBuilder()).append(exampleownerid).append(_exampleownerid).append("*").toString();
            }

            _stepmap.put("ownername", ownername);
            _stepmap.put("ownerstatusvalue", ownerstatusvalue);
            _stepmap.put("exampleownerid", exampleownerid);
            _stepmap.put("ownerlist", _ownerlist);
            _stepmap.put("exampleid", Integer.valueOf(_exampleid));
            _stepmap.put("objid", Integer.valueOf(_exampleid));
            _stepmap.put("processbrief", _processbrief);
            _stepmap.put("processname", _processname);
            _stepmap.put("processstatusvalue", _processstatusvalue);
            _steplist.add(_stepmap);
        }

        return _steplist;
    }

    public List getPastProcessList()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getPastExampleList();
        return changePastProcessListToShow(_examplelist);
    }

    public List getPastProcessList(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _examplelist = _workapp.getPastExampleList(map);
        return changePastProcessListToShow(_examplelist);
    }

    private List changePastProcessListToShow(List list)
    {
        WorkflowPublicString a = new WorkflowPublicString();
        List _processlist = new ArrayList();
        for(int j = 0; j < list.size(); j++)
        {
            Map _examplemap = (Map)list.get(j);
            Map _processmap = _examplemap;
            Date _starttime0 = (Date)_examplemap.get("starttime");
            String _starttime;
            if(_starttime0 == null)
                _starttime = a.getFirstTime();
            else
                _starttime = changeDateToString(_starttime0);
            Date _endtime0 = (Date)_examplemap.get("endtime");
            String _endtime;
            if(_endtime0 == null)
                _endtime = a.getLastTime();
            else
                _endtime = changeDateToString(_endtime0);
            _processmap.put("starttime", _starttime);
            _processmap.put("endtime", _endtime);
            _processlist.add(_processmap);
        }

        return _processlist;
    }

    public List getNowWorkflow()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IOrgService _orgService = OrgServiceFactory.getOrgService();
        List _workflowlist = new ArrayList();
        List _examplelist = _workapp.getNowExampleList();
        for(int i = 0; i < _examplelist.size(); i++)
        {
            Map _workflowmap = new HashMap();
            Map _examplemap = (Map)_examplelist.get(i);
            int _exampleid = NumberTool.convertMapKeyToInt(_examplemap, "exampleid", Integer.valueOf(0)).intValue();
            String _processname = StringUtil.safeToString(_examplemap.get("processname"), "");
            String _processstatusvalue = StringUtil.safeToString(_examplemap.get("processstatusvalue"), "");
            List _steplist = new ArrayList();
            int j;
            for(j = i; j < _examplelist.size(); j++)
            {
                Map _stepmap = new HashMap();
                Map _examplemap2 = (Map)_examplelist.get(j);
                int _exampleid2 = NumberTool.convertMapKeyToInt(_examplemap2, "exampleid", Integer.valueOf(0)).intValue();
                if(_exampleid2 != _exampleid)
                {
                    i = j;
                    break;
                }
                int _examplestepid = NumberTool.convertMapKeyToInt(_examplemap2, "examplestepid", Integer.valueOf(0)).intValue();
                String _processstepname = StringUtil.safeToString(_examplemap2.get("processstepname"), "");
                String _steptypevalue = StringUtil.safeToString(_examplemap2.get("steptypevalue"), "");
                Date _starttime0 = _examplemap2.get("starttime") != null ? (Date)_examplemap2.get("starttime") : new Date();
                String _starttime = changeDateToString(_starttime0);
                List _ownerlist = new ArrayList();
                int k;
                for(k = j; k < _examplelist.size(); k++)
                {
                    Map _ownermap = new HashMap();
                    Map _examplemap3 = (Map)_examplelist.get(k);
                    int _examplestepid2 = NumberTool.convertMapKeyToInt(_examplemap3, "examplestepid", Integer.valueOf(0)).intValue();
                    if(_examplestepid2 != _examplestepid)
                        break;
                    int _ownerid = NumberTool.convertMapKeyToInt(_examplemap3, "ownerid", Integer.valueOf(0)).intValue();
                    int _preownerid = NumberTool.convertMapKeyToInt(_examplemap3, "preownerid", Integer.valueOf(0)).intValue();
                    String _ownerstatusvalue = StringUtil.safeToString(_examplemap3.get("ownerstatusvalue"), "");
                    String _ownername = "";
                    Employee _owner = _orgService.getEmployeeById(Integer.toString(_ownerid));
                    if(_ownerid != 0)
                        if(_preownerid == 0)
                        {
                            _ownername = _owner.getEmpName();
                        } else
                        {
                            Employee _preowner = _orgService.getEmployeeById(Integer.toString(_preownerid));
                            _ownername = (new StringBuilder()).append(_owner.getEmpName()).append("(").append(_preowner.getEmpName()).append(")").toString();
                        }
                    _ownermap.put("ownername", _ownername);
                    _ownermap.put("ownerstatusvalue", _ownerstatusvalue);
                    _ownerlist.add(_ownermap);
                }

                if(k > j)
                    j = k - 1;
                _stepmap.put("starttime", _starttime);
                _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
                _stepmap.put("processstepname", _processstepname);
                _stepmap.put("steptypevalue", _steptypevalue);
                _stepmap.put("ownerlist", _ownerlist);
                _steplist.add(_stepmap);
            }

            if(j > i)
                i = j - 1;
            _workflowmap.put("exampleid", Integer.valueOf(_exampleid));
            _workflowmap.put("processname", _processname);
            _workflowmap.put("processstatusvalue", _processstatusvalue);
            _workflowmap.put("steplist", _steplist);
            _workflowlist.add(_workflowmap);
        }

        return _workflowlist;
    }

    public List getMainWorkflow(int exampleid)
    {
        Map _showmap = WorkflowServiceFactory.getWorkflowBaseService().findParaByNameAndKey("WorkflowShow", 2);
        int _startshow = NumberTool.convertMapKeyToInt(_showmap, "valueads", Integer.valueOf(0)).intValue();
        WorkflowPublicString a = new WorkflowPublicString();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IOrgService _orgService = OrgServiceFactory.getOrgService();
        List _steplist = new ArrayList();
        long startTime = System.nanoTime();
        LOGGER.info((new StringBuilder()).append("getMainWorkflow\u5F00\u59CB\u65F6\u95F4:").append(TimeUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss")).toString());
        List _examplelist = _workapp.getExampleStepListByExampleId(exampleid);
        for(int i = 0; i < _examplelist.size(); i++)
        {
            Map _stepmap = new HashMap();
            Map _examplemap = (Map)_examplelist.get(i);
            int _examplestepid = NumberTool.convertMapKeyToInt(_examplemap, "examplestepid", Integer.valueOf(0)).intValue();
            String _processstepname = StringUtil.safeToString(_examplemap.get("processstepname"), "");
            Date _starttime0 = _examplemap.get("starttime") != null ? (Date)_examplemap.get("starttime") : new Date();
            String _starttime = changeDateToString(_starttime0);
            String _stepbrief = StringUtil.safeToString(_examplemap.get("stepbrief"), "");
            String _stepstatusvalue = StringUtil.safeToString(_examplemap.get("stepstatusvalue"), "");
            int _stepstatus = NumberTool.convertMapKeyToInt(_examplemap, "stepstatus", Integer.valueOf(0)).intValue();
            String _stepconditonvalue = StringUtil.safeToString(_examplemap.get("stepconditonvalue"), "");
            String _steptypevalue = StringUtil.safeToString(_examplemap.get("steptypevalue"), "");
            int _steptype = NumberTool.convertMapKeyToInt(_examplemap, "steptype", Integer.valueOf(0)).intValue();
            List _ownerlist = new ArrayList();
            int j;
            for(j = i; j < _examplelist.size(); j++)
            {
                Map _ownermap = new HashMap();
                Map _examplemap3 = (Map)_examplelist.get(j);
                int _examplestepid2 = NumberTool.convertMapKeyToInt(_examplemap3, "examplestepid", Integer.valueOf(0)).intValue();
                if(_examplestepid2 != _examplestepid)
                    break;
                int _exampleownerid = NumberTool.convertMapKeyToInt(_examplemap3, "exampleownerid", Integer.valueOf(0)).intValue();
                if(_exampleownerid == 0)
                    break;
                int _ownerid = NumberTool.convertMapKeyToInt(_examplemap3, "ownerid", Integer.valueOf(0)).intValue();
                String _ownername2 = MapUtils.getString(_examplemap3, "ownername", "");
                int _preownerid = NumberTool.convertMapKeyToInt(_examplemap3, "preownerid", Integer.valueOf(0)).intValue();
                String _preownername2 = MapUtils.getString(_examplemap3, "preownername", "");
                int _ownerstatus = NumberTool.convertMapKeyToInt(_examplemap3, "ownerstatus", Integer.valueOf(0)).intValue();
                String _ownercondition = StringUtil.safeToString(_examplemap3.get("ownercondition"), "");
                int _approvestatus = MapUtils.getIntValue(_examplemap3, "approvestatus", 0);
                String _ownerstatusvalue = StringUtil.safeToString(_examplemap3.get("ownerstatusvalue"), "");
                String _ownername = "";
                if(_ownerid == 0)
                    _ownername = a.getSystemName();
                else
                if(_preownerid == 0)
                {
                    if(StringUtils.isBlank(_ownername2))
                        _ownername = (new StringBuilder()).append("ID:").append(_ownerid).toString();
                    else
                        _ownername = _ownername2;
                } else
                if(StringUtils.isBlank(_ownername2) && StringUtils.isBlank(_preownername2))
                    _ownername = (new StringBuilder()).append("ID:").append(_ownerid).append("(ID:").append(_preownerid).append(")").toString();
                else
                if(StringUtils.isBlank(_ownername2))
                    _ownername = (new StringBuilder()).append("ID:").append(_ownerid).append("(").append(_preownername2).append(")").toString();
                else
                if(StringUtils.isBlank(_preownername2))
                    _ownername = (new StringBuilder()).append(_ownername2).append("(ID:").append(_preownerid).append(")").toString();
                else
                    _ownername = (new StringBuilder()).append(_ownername2).append("(").append(_preownername2).append(")").toString();
                String _approveopinion = StringUtil.safeToString(_examplemap3.get("approveopinion"), "");
                String _approvetime = "";
                if(_examplemap3.get("approvetime") != null)
                {
                    Date _approvetime0 = (Date)_examplemap3.get("approvetime");
                    _approvetime = changeDateToString(_approvetime0);
                }
                _ownermap.put("exampleownerid", Integer.valueOf(_exampleownerid));
                _ownermap.put("ownerid", Integer.valueOf(_ownerid));
                _ownermap.put("approvetime", _approvetime);
                _ownermap.put("approveopinion", _approveopinion);
                _ownermap.put("ownername", _ownername);
                _ownermap.put("ownerstatus", Integer.valueOf(_ownerstatus));
                _ownermap.put("ownercondition", _ownercondition);
                _ownermap.put("approvestatus", Integer.valueOf(_approvestatus));
                _ownermap.put("ownerstatusvalue", _ownerstatusvalue);
                if(_startshow != 1 || !_approveopinion.equals(a.getJudgeBySystem()))
                    _ownerlist.add(_ownermap);
            }

            if(j > i)
                i = j - 1;
            _stepmap.put("ownerlist", _ownerlist);
            _stepmap.put("examplestepid", Integer.valueOf(_examplestepid));
            _stepmap.put("processstepname", _processstepname);
            _stepmap.put("starttime", _starttime);
            _stepmap.put("steptypevalue", _steptypevalue);
            _stepmap.put("steptype", Integer.valueOf(_steptype));
            _stepmap.put("stepbrief", _stepbrief);
            _stepmap.put("stepstatus", Integer.valueOf(_stepstatus));
            _stepmap.put("stepstatusvalue", _stepstatusvalue);
            _stepmap.put("stepconditonvalue", _stepconditonvalue);
            _steplist.add(_stepmap);
        }

        long endTime = System.nanoTime();
        LOGGER.info((new StringBuilder()).append("getMainWorkflow\u7ED3\u675F\u65F6\u95F4:").append(TimeUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss")).toString());
        LOGGER.info((new StringBuilder()).append("getMainWorkflow\u8017\u65F6\uFF1A").append(TimeUnit.NANOSECONDS.toSeconds(endTime - startTime)).append("\u79D2\n\n").toString());
        return _steplist;
    }

    public List getUnfinishTasks(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        map.put("type", Integer.valueOf(0));
        List _tasklist = _workapp.getExampleTask(map);
        return changeTaskToShow(_tasklist);
    }

    public List getDatchTasks(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        map.put("type", Integer.valueOf(2));
        List _tasklist = _workapp.getExampleTask(map);
        return changeTaskToShow(_tasklist);
    }

    public IMetaDBQuery getUnfinishTasksMetadb(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        map.put("type", Integer.valueOf(0));
        IMetaDBQuery iMetaDBQuery = _workapp.getExampleTaskMetadb(map);
        return iMetaDBQuery;
    }

    public List getHomePageTasks(String orgid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int _orgid = NumberTool.safeToInteger(orgid, Integer.valueOf(0)).intValue();
        List _tasklist = _workapp.getExampleTask(_orgid, 0);
        List tasklist = new ArrayList();
        for(int i = 0; i < 6 && i < _tasklist.size(); i++)
        {
            Map _taskmap = (Map)_tasklist.get(i);
            Map taskmap = new HashMap();
            taskmap.put("exampleownerid", _taskmap.get("exampleownerid"));
            taskmap.put("exampleid", _taskmap.get("exampleid"));
            taskmap.put("actionurl", _taskmap.get("actionurl"));
            taskmap.put("actionurl2", contectActionUrl(_taskmap));
            String showmain = showHomePageMain(_taskmap);
            taskmap.put("showmaintip", showmain);
            taskmap.put("showmain", showmain.length() <= 60 ? ((Object) (showmain)) : ((Object) ((new StringBuilder()).append(showmain.substring(0, 60)).append("...").toString())));
            tasklist.add(taskmap);
        }

        return tasklist;
    }

    private String showHomePageMain(Map map)
    {
        StringBuffer _main = new StringBuffer();
        String _processstepname = StringUtil.safeToString(map.get("processstepname"), "");
        _main.append("[").append(_processstepname).append("]");
        Date _starttime = (Date)map.get("starttime");
        Calendar c = Calendar.getInstance();
        c.setTime(_starttime);
        int hour = c.get(11);
        String shour = hour >= 10 ? StringUtil.safeToString(Integer.valueOf(hour), "") : (new StringBuilder()).append("0").append(hour).toString();
        int minite = c.get(12);
        String sminite = minite >= 10 ? StringUtil.safeToString(Integer.valueOf(minite), "") : (new StringBuilder()).append("0").append(minite).toString();
        String time = (new StringBuilder()).append(shour).append(":").append(sminite).append(" ").toString();
        long betweenDay = TimeUtil.getBetweenDays(_starttime, new Date());
        if(betweenDay == 0L)
            _main.append((new StringBuilder()).append("\u4ECA\u5929").append(time).toString());
        else
        if(betweenDay == 1L)
            _main.append((new StringBuilder()).append("\u6628\u5929").append(time).toString());
        else
        if(betweenDay < 7L)
        {
            int day = c.get(7);
            if(day == 1)
                _main.append((new StringBuilder()).append("\u5468\u65E5").append(time).toString());
            else
            if(day == 2)
                _main.append((new StringBuilder()).append("\u5468\u4E00").append(time).toString());
            else
            if(day == 3)
                _main.append((new StringBuilder()).append("\u5468\u4E8C").append(time).toString());
            else
            if(day == 4)
                _main.append((new StringBuilder()).append("\u5468\u4E09").append(time).toString());
            else
            if(day == 5)
                _main.append((new StringBuilder()).append("\u5468\u56DB").append(time).toString());
            else
            if(day == 6)
                _main.append((new StringBuilder()).append("\u5468\u4E94").append(time).toString());
            else
            if(day == 7)
                _main.append((new StringBuilder()).append("\u5468\u516D").append(time).toString());
        } else
        {
            _main.append(TimeUtil.formatDate(_starttime, "yy-MM-dd"));
        }
        String _processbrief = StringUtil.safeToString(map.get("processbrief"), "");
        _main.append(_processbrief);
        return _main.toString();
    }

    public List getFinishedTasks(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        map.put("type", Integer.valueOf(1));
        List _tasklist = _workapp.getExampleTask(map);
        return changeTaskToShow(_tasklist);
    }

    private List changeTaskToShow(List list)
    {
        List _tasklist = new ArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            Map _map = (Map)list.get(i);
            Map _taskmap = _map;
            _taskmap.put("actionurl2", contectActionUrl(_taskmap));
            _taskmap.put("showurl2", contectShowUrl(_taskmap));
            _tasklist.add(_taskmap);
        }

        return _tasklist;
    }

    private String contectShowUrl(Map map)
    {
        StringBuffer showurl = new StringBuffer();
        String _url = StringUtil.safeToString(map.get("showurl"), "");
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int _exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        if(_url.indexOf("?") == -1)
        {
            showurl.append(_url).append("?exampleid=").append(_exampleid).append("&examplestepid=");
            showurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        } else
        if(_url.endsWith("?") || _url.endsWith("&"))
        {
            showurl.append(_url).append("exampleid=").append(_exampleid).append("&examplestepid=");
            showurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        } else
        {
            showurl.append(_url).append("&exampleid=").append(_exampleid).append("&examplestepid=");
            showurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        }
        return showurl.toString();
    }

    private String contectActionUrl(Map map)
    {
        StringBuffer actionurl = new StringBuffer();
        String _url = StringUtil.safeToString(map.get("actionurl"), "");
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        int _exampleownerid = NumberTool.convertMapKeyToInt(map, "exampleownerid", Integer.valueOf(0)).intValue();
        if(!_url.contains("?"))
        {
            actionurl.append(_url).append("?exampleid=").append(_exampleid).append("&examplestepid=");
            actionurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        } else
        if(_url.endsWith("?") || _url.endsWith("&"))
        {
            actionurl.append(_url).append("exampleid=").append(_exampleid).append("&examplestepid=");
            actionurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        } else
        {
            actionurl.append(_url).append("&exampleid=").append(_exampleid).append("&examplestepid=");
            actionurl.append(_examplestepid).append("&exampleownerid=").append(_exampleownerid);
        }
        return actionurl.toString();
    }

    private String changeUrlByMap(String url, Map map)
    {
        StringBuffer newurl = new StringBuffer();
        int marki = url.indexOf("${");
        int markj = -1;
        do
        {
            if(marki == -1)
                break;
            newurl.append(url.substring(markj + 1, marki));
            markj = url.indexOf("}", marki);
            if(markj != -1)
            {
                String _name = url.substring(marki + 2, markj);
                Object _value = map.get(_name);
                if(_value instanceof Integer)
                {
                    int value = NumberTool.safeToInteger(_value, Integer.valueOf(0)).intValue();
                    newurl.append(value);
                } else
                if(_value instanceof Double)
                {
                    Double value = NumberTool.safeToDouble(_value, Double.valueOf(0.0D));
                    newurl.append(value);
                } else
                if(_value instanceof Float)
                {
                    Float value = NumberTool.safeToFloat(_value, Float.valueOf(0.0F));
                    newurl.append(value);
                } else
                if(_value instanceof String)
                {
                    String value = StringUtil.safeToString(_value, "");
                    newurl.append(value);
                }
                marki = url.indexOf("${", markj);
            } else
            {
                marki = -1;
            }
            if(marki == -1 && markj != url.length() - 1)
                newurl.append(url.substring(markj + 1));
        } while(true);
        if(newurl.length() == 0)
            return url;
        else
            return newurl.toString();
    }

    private String changeDateToString(Date date)
    {
        if(date == null)
            date = new Date();
        String _date = TimeUtil.formatDate(date, "yyyy-MM-dd HH:mm");
        return _date;
    }

    public List getReleaseProcessList()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getReleaseProcesss(0);
    }

    public List getReleaseProcessAndCodeList()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getReleaseProcesss(1);
    }

    public List getReleaseProcessAndNullList()
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getReleaseProcesss(-1);
    }

    public Map getExampleMain(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample _example = _workapp.loadExample(exampleid);
        return changeExampleToMap(_example);
    }

    public List getExampleEntryList(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _entrylist = _workapp.getExampleEntrys(exampleid);
        return _entrylist;
    }

    public List getFrontExampleStepList(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _list = _workapp.getFrontExampleStepList(examplestepid);
        return _list;
    }

    public List getDetailCadidateListByExampleStepid(int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep _step = _workapp.loadExampleStep(examplestepid);
        return getDetailCadidateListByProcessStepid(_step.getProcessStepId().intValue());
    }

    public List getDetailCadidateListBySysCode(String syscode)
    {
        IWorkflowEngineService _workengine = WorkflowServiceFactory.getWorkflowEngineService();
        Map _processmap = _workengine.getProcessBySyscode(syscode);
        int _processid = NumberTool.convertMapKeyToInt(_processmap, "processid", Integer.valueOf(0)).intValue();
        Map _startmap = _workengine.getStartStep(_processid);
        int _processstepid = NumberTool.convertMapKeyToInt(_startmap, "objid", Integer.valueOf(0)).intValue();
        return getDetailCadidateListByProcessStepid(_processstepid);
    }

    public List getDetailCadidateListByProcessStepid(int processstepid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IOrgService _orgService = OrgServiceFactory.getOrgService();
        List _list = _workbase.getOwnerListByGoto(0, processstepid, -1);
        List _newlist = new ArrayList();
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _orgtype = NumberTool.convertMapKeyToInt(_map, "orgtype", Integer.valueOf(0)).intValue();
            int _orgid = NumberTool.convertMapKeyToInt(_map, "orgid", Integer.valueOf(0)).intValue();
            String _orgname = StringUtil.safeToString(_map.get("orgname"), "");
            if(_orgname.length() == 0 || _orgtype <= 0)
                continue;
            if(_orgtype == 8)
            {
                Map _newmap = new HashMap();
                _newmap.put("orgname", _orgname);
                _newmap.put("orgid", Integer.valueOf(_orgid));
                _newlist.add(_newmap);
                continue;
            }
            List _employeelist = _orgService.findEmployeesByOrgTypeOrgId(_orgtype, Integer.toString(_orgid));
            for(int j = 0; j < _employeelist.size(); j++)
            {
                Employee _employee = (Employee)_employeelist.get(j);
                Map _newmap = new HashMap();
                _newmap.put("orgname", (new StringBuilder()).append(_orgname).append("\u2014").append(_employee.getEmpName()).toString());
                _newmap.put("orgid", (new StringBuilder()).append(_orgid).append("_").append(_employee.getId()).toString());
                _newlist.add(_newmap);
            }

        }

        return _newlist;
    }

    private Map dealException(int exception, int exampleid, int examplestepid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _map = new HashMap();
        if(examplestepid != 0)
            _workapp.changeExampleStep(examplestepid, 11);
        if(exampleid != 0)
            _workapp.changeExampleS(exampleid, 5);
        _map.put("exception", Integer.valueOf(exception));
        _map.put("resultlist", new ArrayList());
        LOGGER.warn((new StringBuilder()).append("Process Exception: ").append(exception).toString());
        return _map;
    }

    public List getWorkflowOpinion(int exampleid, int sort, int type)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _newlist = new ArrayList();
        List _list = _workapp.getExampleOwnerOpinion(exampleid, sort, 1);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _ownerstatus = NumberTool.convertMapKeyToInt(_map, "ownerstatus", Integer.valueOf(0)).intValue();
            String _approvestatusvalue = StringUtil.safeToString(_map.get("approvestatusvalue"), "");
            String _ownerstatusvalue = StringUtil.safeToString(_map.get("ownerstatusvalue"), "");
            String _approvetime = "";
            if(_map.get("approvetime") != null)
            {
                Date _approvetime0 = (Date)_map.get("approvetime");
                _approvetime = changeDateToString(_approvetime0);
            }
            _map.put("approvetimeshow", _approvetime);
            if(_approvestatusvalue.length() > 0)
                _map.put("statusvalue", _approvestatusvalue);
            else
                _map.put("statusvalue", _ownerstatusvalue);
            if(type == 1)
            {
                String _approveopinion = StringUtil.safeToString(_map.get("approveopinion"), "");
                if(_approveopinion.length() > 0)
                    _newlist.add(_map);
                continue;
            }
            if(type == 2)
                _newlist.add(_map);
        }

        return _newlist;
    }

    public List getWorkflowNOpinion(int exampleid, int sort, int type)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _newlist = new ArrayList();
        List _list = _workapp.getExampleOwnerOpinion(exampleid, sort, 1);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _ownerstatus = NumberTool.convertMapKeyToInt(_map, "ownerstatus", Integer.valueOf(0)).intValue();
            String _approvestatusvalue = StringUtil.safeToString(_map.get("approvestatusvalue"), "");
            String _ownerstatusvalue = StringUtil.safeToString(_map.get("ownerstatusvalue"), "");
            String _approvetime = "";
            if(_map.get("approvetime") != null)
            {
                Date _approvetime0 = (Date)_map.get("approvetime");
                _approvetime = changeDateToString(_approvetime0);
            }
            _map.put("approvetimeshow", _approvetime);
            if(_approvestatusvalue.length() > 0)
                _map.put("statusvalue", _approvestatusvalue);
            else
                _map.put("statusvalue", _ownerstatusvalue);
            if(type == 1)
            {
                String _approveopinion = StringUtil.safeToString(_map.get("approveopinion"), "");
                _newlist.add(_map);
                continue;
            }
            if(type == 2)
                _newlist.add(_map);
        }

        return _newlist;
    }

    public List getWorkflowSOpinion(int exampleid, int sort, int type)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _newlist = new ArrayList();
        List _list = _workapp.getExampleOwnerOpinion(exampleid, sort, 10);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _ownerstatus = NumberTool.convertMapKeyToInt(_map, "ownerstatus", Integer.valueOf(0)).intValue();
            String _approvestatusvalue = StringUtil.safeToString(_map.get("approvestatusvalue"), "");
            String _ownerstatusvalue = StringUtil.safeToString(_map.get("ownerstatusvalue"), "");
            String _approvetime = "";
            if(_map.get("approvetime") != null)
            {
                Date _approvetime0 = (Date)_map.get("approvetime");
                _approvetime = changeDateToString(_approvetime0);
            }
            _map.put("approvetimeshow", _approvetime);
            if(_approvestatusvalue.length() > 0)
                _map.put("statusvalue", _approvestatusvalue);
            else
                _map.put("statusvalue", _ownerstatusvalue);
            if(type == 1)
            {
                String _approveopinion = StringUtil.safeToString(_map.get("approveopinion"), "");
                if(_approveopinion.length() > 0)
                    _newlist.add(_map);
                continue;
            }
            if(type == 2)
                _newlist.add(_map);
        }

        return _newlist;
    }

    private String subStringBySize(String str, int size)
    {
        int _length = 0;
        StringBuffer _strbuffer = new StringBuffer();
        if(size <= 0)
            return str;
        int i = 0;
        do
        {
            if(i >= str.length())
                break;
            if(str.charAt(i) >= '\177')
                _length += 2;
            else
                _length++;
            _strbuffer.append(str.charAt(i));
            if(_length >= size)
                break;
            i++;
        } while(true);
        if(i < str.length() - 1)
            _strbuffer.append("...");
        return _strbuffer.toString();
    }

    public String getFirstShowUrl(int exampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _list = _workapp.getExampleStepListByExampleId(exampleid);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            String _showurl = StringUtil.safeToString(_map.get("showurl"), "");
            if(_showurl.length() > 0)
                return contectShowUrl(_map);
        }

        return "";
    }

    public IMetaDBQuery getWorkflowDealerList(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        return _workapp.getDealerTaskCountList(map);
    }

    public int doDirectFinish(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        String _finishopinion = StringUtil.safeToString(map.get("finishopinion"), "");
        List _list = _workapp.getExampleOwnerListByExampleid(_exampleid, 0);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _exampleownerid = NumberTool.convertMapKeyToInt(_map, "exampleownerid", Integer.valueOf(0)).intValue();
            IWorkflowExampleStepOwner stepowner = _workapp.loadExampleStepOwner(_exampleownerid);
            stepowner.setApproveOpinion(_finishopinion);
            _workapp.saveExampleStepOwner(stepowner);
        }

        _workapp.changeExampleS(_exampleid, WorkflowProcessStatus.WF_PROCESS_DIRECT.getEnumItemValue());
        return _list.size();
    }

    public int doJudgeReturn(int exampleownerid, String returnopinion)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStepOwner stepowner = _workapp.loadExampleStepOwner(exampleownerid);
        stepowner.setApproveOpinion(returnopinion);
        _workapp.saveExampleStepOwner(stepowner);
        _workapp.changeExampleS(stepowner.getExampleId().intValue(), WorkflowProcessStatus.WF_PROCESS_RETURN.getEnumItemValue());
        return stepowner.getExampleId().intValue();
    }

    private int getMaxRelationSort(int exampleid, int examplestepid, int exampleownerid, int subexampleid)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map map = new HashMap();
        map.put("exampleid", Integer.valueOf(exampleid));
        map.put("examplestepid", Integer.valueOf(examplestepid));
        map.put("exampleownerid", Integer.valueOf(exampleownerid));
        map.put("subexampleid", Integer.valueOf(subexampleid));
        List list = _workapp.getRelationList(map);
        int maxsort = 0;
        if(list.size() > 0)
            maxsort = NumberTool.convertMapKeyToInt((Map)list.get(0), "sort", Integer.valueOf(0)).intValue();
        return maxsort;
    }

    public Map gotoProcessStart(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int exampleid = NumberTool.safeToInteger(map.get("exampleid"), Integer.valueOf(0)).intValue();
        IWorkflowExample example = _workapp.loadExample(exampleid);
        if(example.getStatus().intValue() == 0)
        {
            example.setStatus(Integer.valueOf(1));
            _workapp.saveExample(example);
        }
        List steplist = _workapp.getExampleStepList(exampleid);
        int processstepid = 0;
        int i = 0;
        do
        {
            if(i >= steplist.size())
                break;
            Map stepmap = (Map)steplist.get(i);
            int steptype = NumberTool.safeToInteger(stepmap.get("steptype"), Integer.valueOf(0)).intValue();
            if(steptype == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
            {
                processstepid = NumberTool.safeToInteger(stepmap.get("processstepid"), Integer.valueOf(0)).intValue();
                break;
            }
            i++;
        } while(true);
        WorkflowException exception = new WorkflowException();
        if(processstepid == 0)
        {
            throw new WorkflowMissDataException(12);
        } else
        {
            Map changemap = new HashMap();
            List steplist2 = new ArrayList();
            Map stepmap2 = new HashMap();
            stepmap2.put("stepid", Integer.valueOf(processstepid));
            steplist2.add(stepmap2);
            changemap.put("steplist", steplist2);
            map.put("exampleownerid", Integer.valueOf(-1));
            map.put("changemap", changemap);
            map.put("status", Integer.valueOf(1));
            return subProcessStep(map);
        }
    }

    public List getTasksTakepart(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        map.put("type", Integer.valueOf(1));
        List _tasklist = _workapp.getExampleTask2(map);
        return changeTaskToShow(_tasklist);
    }

    public List getMyTasks(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List _tasklist = _workapp.getExampleTask2(map);
        return changeTaskToShow(_tasklist);
    }

    public boolean isNextStepNew(int stepId)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        List ownerList = appService.findStepNextList(stepId);
        for(int i = 0; i < ownerList.size(); i++)
        {
            Map ownerMap = (Map)ownerList.get(i);
            int status = NumberTool.safeToInteger(ownerMap.get("status"), Integer.valueOf(0)).intValue();
            if(status != WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue() && status != WorkflowStepOwnerStatus.WF_OWNER_BEING.getEnumItemValue() && status != WorkflowStepOwnerStatus.WF_OWNER_AGENTS.getEnumItemValue())
                return false;
        }

        return true;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowSupportServiceImpl);

}
