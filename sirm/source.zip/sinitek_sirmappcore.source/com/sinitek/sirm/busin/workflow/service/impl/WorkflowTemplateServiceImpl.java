// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowTemplateServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.WorkflowPublicString;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

public class WorkflowTemplateServiceImpl extends MetaDBContextSupport
    implements IWorkflowTemplateService
{

    public WorkflowTemplateServiceImpl()
    {
    }

    public List doSpecialOperate(List list)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowSupportService _worksup = WorkflowServiceFactory.getWorkflowSupportService();
        WorkflowPublicString a = new WorkflowPublicString();
        if(list.size() > 0)
        {
            Map _markmap = (Map)list.get(0);
            int _exampleid = NumberTool.convertMapKeyToInt(_markmap, "exampleid", Integer.valueOf(0)).intValue();
            IWorkflowExample _example = _workapp.loadExample(_exampleid);
            IWorkflowProcess _process = _workbase.loadProcess(_example.getProcessId().intValue());
            String _specialmark = StringUtil.safeToString(_process.getSpecialMark(), "");
            if(_specialmark.length() > 0)
            {
                char ch = _specialmark.charAt(0);
                if(ch == '1')
                {
                    for(int i = 0; i < list.size(); i++)
                    {
                        Map _map = (Map)list.get(i);
                        int _examplesteptype = NumberTool.convertMapKeyToInt(_map, "examplesteptype", Integer.valueOf(0)).intValue();
                        if(_examplesteptype == WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
                        {
                            int _owenrstatus = NumberTool.convertMapKeyToInt(_map, "ownerstatus", Integer.valueOf(0)).intValue();
                            if(_owenrstatus == WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue())
                            {
                                int _ownerid = NumberTool.convertMapKeyToInt(_map, "ownerid", Integer.valueOf(0)).intValue();
                                int _preownerid = NumberTool.convertMapKeyToInt(_map, "preownerid", Integer.valueOf(0)).intValue();
                                if(_ownerid == _example.getStarterId().intValue() && _preownerid == 0)
                                {
                                    Map _ownermap = new HashMap();
                                    _ownermap.put("condition", Integer.valueOf(WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue()));
                                    _ownermap.put("approveopinion", a.getJudgeBySelf());
                                    _map.put("ownermap", _ownermap);
                                    Map _returnmap = _worksup.subProcessStep(_map);
                                    byte byte0 = 100;
                                }
                            }
                        }
                    }

                }
            } else
            {
                return list;
            }
        }
        return list;
    }

    public List doOrdinaryOperate(List list)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowSupportService _worksup = WorkflowServiceFactory.getWorkflowSupportService();
        WorkflowPublicString a = new WorkflowPublicString();
        List _list = new ArrayList();
        if(list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Map _map = (Map)list.get(i);
                int _examplestepid = NumberTool.convertMapKeyToInt(_map, "examplestepid", Integer.valueOf(0)).intValue();
                IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
                String _stepbeigin = StringUtil.safeToString(_examplestep.getStepBeigin(), "");
                int _skipmark = 0;
                String _stepbeiginArr[] = _stepbeigin.split(" ");
                int j = 0;
                do
                {
                    if(j >= _stepbeiginArr.length - 1)
                        break;
                    if("skipmark".endsWith(_stepbeiginArr[j]))
                    {
                        _skipmark = NumberTool.safeToInteger(_stepbeiginArr[j + 1], Integer.valueOf(0)).intValue();
                        break;
                    }
                    j++;
                } while(true);
                if(_skipmark == 2)
                {
                    int _owenrstatus = NumberTool.convertMapKeyToInt(_map, "ownerstatus", Integer.valueOf(0)).intValue();
                    if(_owenrstatus != WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue())
                        continue;
                    Map _ownermap = new HashMap();
                    _ownermap.put("status", Integer.valueOf(WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue()));
                    _ownermap.put("condition", Integer.valueOf(WorkflowStepCondition.WF_STEP_PASS.getEnumItemValue()));
                    _ownermap.put("approveopinion", a.getJudgeBySystem());
                    _map.put("ownermap", _ownermap);
                    Map _returnmap = _worksup.subProcessStep(_map);
                    List _newlist = ((List) (_returnmap.get("resultlist") != null ? (List)_returnmap.get("resultlist") : ((List) (new ArrayList()))));
                    for(int _newi = 0; _newi < _newlist.size(); _newi++)
                    {
                        Map _newmap = (Map)_newlist.get(_newi);
                        _list.add(_newmap);
                    }

                } else
                {
                    _list.add(_map);
                }
            }

            return _list;
        } else
        {
            return _list;
        }
    }

    public void doStepAftDoOperate(Map map)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        if(map.get("stepmap") != null)
        {
            Map _stepmap = (Map)map.get("stepmap");
            int _exampleid = MapUtils.getIntValue(_stepmap, "exampleid", 0);
            int _examplestepid = MapUtils.getIntValue(_stepmap, "examplestepid", 0);
            int _processstepid = MapUtils.getIntValue(_stepmap, "stepid", 0);
            List _ownerlist = new ArrayList();
            if(_stepmap.get("ownerlist") != null)
                _ownerlist = (List)_stepmap.get("ownerlist");
            else
                _ownerlist = _workapp.getExampleOwnerList(_examplestepid);
            Map paramap = _workapp.loadParaMap(_exampleid, _examplestepid, 0);
            Map param = new HashMap();
            param.put("exampleid", Integer.valueOf(_exampleid));
            param.put("examplestepid", Integer.valueOf(_examplestepid));
            param.put("ownerlist", _ownerlist);
            param.put("paramap", paramap);
            List _stepdolist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_AFTDO.getEnumItemValue());
            if(_stepdolist.size() > 0)
                LOGGER.debug((new StringBuilder()).append("\u6B65\u9AA4\u540E\u6267\u884C\uFF1A").append(map.toString()).toString());
            Iterator i$ = _stepdolist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _stepdomap = (Map)i$.next();
                int _taskid = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                if(_taskid > 0)
                    _workin.judgeSpecialTask(_taskid, param);
            } while(true);
        }
    }

    public void doStepPreDoOperate(List list)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        if(list.size() > 0)
        {
            Map _markmap = (Map)list.get(0);
            int _exampleid = NumberTool.convertMapKeyToInt(_markmap, "exampleid", Integer.valueOf(0)).intValue();
            int _examplestepid = NumberTool.convertMapKeyToInt(_markmap, "examplestepid", Integer.valueOf(0)).intValue();
            IWorkflowExampleStep _examplestep = _workapp.loadExampleStep(_examplestepid);
            List _ownerlist = _workapp.getExampleOwnerList(_examplestepid);
            Map paramap = _workapp.loadParaMap(_exampleid, _examplestepid, 0);
            Map param = new HashMap();
            param.put("exampleid", Integer.valueOf(_exampleid));
            param.put("examplestepid", Integer.valueOf(_examplestepid));
            param.put("ownerlist", _ownerlist);
            param.put("paramap", paramap);
            List _stepdolist = _workbase.getProcessStepDoList(_examplestep.getProcessStepId().intValue(), WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue());
            if(_stepdolist.size() > 0)
                LOGGER.debug((new StringBuilder()).append("\u6B65\u9AA4\u524D\u6267\u884C\uFF1A").append(list.toString()).toString());
            Iterator i$ = _stepdolist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _stepdomap = (Map)i$.next();
                int _taskid = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                if(_taskid > 0)
                    _workin.judgeSpecialTask(_taskid, param);
            } while(true);
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowTemplateServiceImpl);

}
