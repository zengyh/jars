// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowMobileServiceImpl.java

package com.sinitek.sirm.tool.workflow.special.impl;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExampleStepOwner;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.tool.workflow.special.IWorkflowMobileService;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

public class WorkflowMobileServiceImpl extends MetaDBContextSupport
    implements IWorkflowMobileService
{

    public WorkflowMobileServiceImpl()
    {
    }

    public List getUndoExampleTask(String orgid, Map param)
    {
        Map _map = new HashMap();
        _map.putAll(param);
        _map.put("orgid", orgid);
        List list = WorkflowServiceFactory.getWorkflowAppService().getUndoExampleTaskForMobile(_map);
        return list;
    }

    public int getUndoExampleTaskNum(String orgid)
    {
        Map _map = new HashMap();
        _map.put("orgid", orgid);
        return WorkflowServiceFactory.getWorkflowAppService().getUndoExampleTaskNumForMobile(_map);
    }

    public void dealExampleTask(Map param)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int exampleownerid = MapUtils.getIntValue(param, "exampleownerid", 0);
        int condition = MapUtils.getIntValue(param, "condition", 0);
        String approveopinion = MapUtils.getString(param, "approveopinion", "");
        if(exampleownerid > 0)
        {
            IWorkflowExampleStepOwner _exampleowner = _workapp.loadExampleStepOwner(exampleownerid);
            if(!_exampleowner.isEmpty())
            {
                int exampleid = _exampleowner.getExampleId().intValue();
                int examplestepid = _exampleowner.getExampleStepId().intValue();
                if(WorkflowServiceFactory.getWorkflowWebService().judgeNowOwners(_exampleowner.getOwnerId().intValue(), exampleownerid))
                {
                    Map _examplemap = new HashMap();
                    Map _ownermap = new HashMap();
                    _ownermap.put("condition", Integer.valueOf(condition));
                    _ownermap.put("approveopinion", approveopinion);
                    _examplemap.put("ownermap", _ownermap);
                    _examplemap.put("exampleid", Integer.valueOf(exampleid));
                    _examplemap.put("examplestepid", Integer.valueOf(examplestepid));
                    _examplemap.put("exampleownerid", Integer.valueOf(exampleownerid));
                    WorkflowServiceFactory.getWorkflowSupportService().subProcessStep(_examplemap);
                } else
                {
                    LOGGER.info("\u8BE5\u4EFB\u52A1\u5DF2\u88AB\u5904\u7406");
                }
            } else
            {
                LOGGER.info((new StringBuilder()).append("\u4EFB\u52A1\u83B7\u53D6\u5931\u8D25\uFF0Cexampleownerid = ").append(exampleownerid).toString());
            }
        }
    }

    public List getDoneExampleTask(String orgid, Map param)
    {
        Map _map = new HashMap();
        _map.putAll(param);
        _map.put("orgid", orgid);
        List list = WorkflowServiceFactory.getWorkflowAppService().getDoneExampleTaskForMobile(_map);
        return list;
    }

    public List getExampleInfo(Map param)
    {
        List _list = new ArrayList();
        _list.add(param);
        List _exampleidlist = WorkflowServiceFactory.getWorkflowAppService().getProcessListByEntryList(_list);
        if(_exampleidlist.size() > 0)
        {
            int _exampleid = MapUtils.getIntValue((Map)_exampleidlist.get(0), "exampleid", 0);
            List _steplist = showcolorworkflow(_exampleid);
            int _status = WorkflowServiceFactory.getWorkflowWebService().getProcessStatus(_exampleid);
            if(_steplist.size() > 0)
            {
                Map stepmap = (Map)_steplist.get(_steplist.size() - 1);
                if("2".equals(stepmap.get("steptype").toString()))
                    _steplist.remove(_steplist.size() - 1);
                stepmap = (Map)_steplist.get(0);
                if("1".equals(stepmap.get("steptype").toString()))
                    _steplist.remove(0);
            }
            if(_status == WorkflowProcessStatus.WF_PROCESS_LAUNCH.getEnumItemValue() || _status == WorkflowProcessStatus.WF_PROCESS_BEING.getEnumItemValue())
            {
                Map _lastmap = (Map)_steplist.get(_steplist.size() - 1);
                if(_lastmap != null)
                    _lastmap.put("examplestatus", Integer.valueOf(0));
            } else
            {
                Map _lastmap = (Map)_steplist.get(_steplist.size() - 1);
                if(_lastmap != null)
                    _lastmap.put("examplestatus", Integer.valueOf(1));
            }
            return _steplist;
        } else
        {
            return new ArrayList();
        }
    }

    private List showcolorworkflow(int exampleid)
    {
        IWorkflowSupportService _worksup = WorkflowServiceFactory.getWorkflowSupportService();
        WorkflowPublicString a = new WorkflowPublicString();
        List _steplist = _worksup.getMainWorkflow(exampleid);
        List _newsteplist = new ArrayList();
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            String processresult = _stepmap.get("stepconditonvalue") != null ? _stepmap.get("stepconditonvalue").toString() : "";
            String stepstatusvalue = _stepmap.get("stepstatusvalue") != null ? _stepmap.get("stepstatusvalue").toString() : "";
            if(WorkflowStepStatus.WF_STEP_PROCESS.getEnumItemInfo().equals(stepstatusvalue))
                stepstatusvalue = processresult != null ? processresult : stepstatusvalue;
            _stepmap.put("stepstatusvalue", stepstatusvalue);
            List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            List _newownerlist = new ArrayList();
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                int _ownerstatus = NumberTool.convertMapKeyToInt(_ownermap, "ownerstatus", Integer.valueOf(0)).intValue();
                if(_ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue())
                {
                    String _ownercondition = StringUtil.safeToString(_ownermap.get("ownercondition"), "");
                    if(!_ownercondition.equals("\u65E0"))
                        _ownermap.put("ownerstatusvalue", _ownercondition);
                }
                if(a.getOpinionMaxLength() > 0)
                {
                    String _approveopinion = StringUtil.safeToString(_ownermap.get("approveopinion"), "");
                    if(_approveopinion.length() > a.getOpinionMaxLength())
                        _ownermap.put("approveopinion", _approveopinion.substring(0, a.getOpinionMaxLength()));
                    _ownermap.put("approveopiniontip", _approveopinion);
                }
                _newownerlist.add(_ownermap);
            }

            _stepmap.put("ownerlistsize", Integer.valueOf(_newownerlist.size()));
            _stepmap.put("ownerlist", _newownerlist);
            _newsteplist.add(_stepmap);
        }

        return _newsteplist;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/tool/workflow/special/impl/WorkflowMobileServiceImpl);

}
