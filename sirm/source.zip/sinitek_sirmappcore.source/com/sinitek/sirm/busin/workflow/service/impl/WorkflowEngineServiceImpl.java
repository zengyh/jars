// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowEngineServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.WorkflowException;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class WorkflowEngineServiceImpl extends MetaDBContextSupport
    implements IWorkflowEngineService
{

    public WorkflowEngineServiceImpl()
    {
    }

    public Map newProcess(IWorkflowExample example)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = example.getProcessId().intValue();
        Map _startstepmap = getStartStep(_processid);
        int _startstepid = NumberTool.convertMapKeyToInt(_startstepmap, "objid", Integer.valueOf(0)).intValue();
        IWorkflowProcessStep _step = _workbase.loadProcessStep(_startstepid);
        return changeWorkflowProcessStep(_step);
    }

    public Map subProcessStep(Map map)
    {
        WorkflowException exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        Map _mainmap = ((Map) (map.get("mainmap") != null ? (Map)map.get("mainmap") : ((Map) (new HashMap()))));
        int _exampleid = NumberTool.convertMapKeyToInt(_mainmap, "exampleid", Integer.valueOf(0)).intValue();
        Map _stepmap = ((Map) (map.get("stepmap") != null ? (Map)map.get("stepmap") : ((Map) (new HashMap()))));
        int _examplestepid = NumberTool.convertMapKeyToInt(_stepmap, "examplestepid", Integer.valueOf(0)).intValue();
        int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "stepid", Integer.valueOf(0)).intValue();
        int _steptypeid = NumberTool.convertMapKeyToInt(_stepmap, "steptypeid", Integer.valueOf(0)).intValue();
        Map _passownermap = ((Map) (map.get("ownermap") != null ? (Map)map.get("ownermap") : ((Map) (new HashMap()))));
        Map _paramap = ((Map) (map.get("paramap") != null ? (Map)map.get("paramap") : ((Map) (new HashMap()))));
        Map _insteadmap = ((Map) (map.get("insteadmap") != null ? (Map)map.get("insteadmap") : ((Map) (new HashMap()))));
        Map _oncelinkmap = ((Map) (map.get("oncelinkmap") != null ? (Map)map.get("oncelinkmap") : ((Map) (new HashMap()))));
        Map _addmap = ((Map) (map.get("addmap") != null ? (Map)map.get("addmap") : ((Map) (new HashMap()))));
        Map _changemap = ((Map) (map.get("changemap") != null ? (Map)map.get("changemap") : ((Map) (new HashMap()))));
        Map resultmap = new HashMap();
        List _targetlist = new ArrayList();
        _paramap.put("exampleid", Integer.valueOf(_exampleid));
        _paramap.put("examplestepid", Integer.valueOf(_examplestepid));
        int _ownercondition = NumberTool.convertMapKeyToInt(_passownermap, "condition", Integer.valueOf(0)).intValue();
        _paramap.put("condition", Integer.valueOf(_ownercondition));
        List _changesteplist = ((List) (_changemap.get("steplist") != null ? (List)_changemap.get("steplist") : ((List) (new ArrayList()))));
        int _exitcondition = -1;
        if(_changesteplist.size() > 0)
            _exitcondition = 0;
        else
            _exitcondition = judgeExitStep(_stepmap, _paramap);
        if(_exitcondition >= 0)
        {
            _stepmap.put("stepcondition", Integer.valueOf(_exitcondition));
            _paramap.put("condition", Integer.valueOf(_exitcondition));
            List _targetlist2 = new ArrayList();
            List _insteadsteplist = ((List) (_insteadmap.get("steplist") != null ? (List)_insteadmap.get("steplist") : ((List) (new ArrayList()))));
            if(_changesteplist.size() > 0)
            {
                for(int j = 0; j < _changesteplist.size(); j++)
                {
                    Map _changestepmap = (Map)_changesteplist.get(j);
                    _targetlist2.add(_changestepmap);
                }

            } else
            if(_oncelinkmap != null && NumberTool.safeToInteger(_oncelinkmap.get("aftstepid"), Integer.valueOf(-1)).intValue() != -1)
            {
                Integer aftstepid = NumberTool.safeToInteger(_oncelinkmap.get("aftstepid"), Integer.valueOf(-1));
                Map _targetmap = new HashMap();
                _targetmap.put("stepid", aftstepid);
                _targetlist2.add(_targetmap);
            } else
            if(_insteadsteplist.size() > 0)
            {
                for(int j = 0; j < _insteadsteplist.size(); j++)
                {
                    Map _insteadstepmap = (Map)_insteadsteplist.get(j);
                    _targetlist2.add(_insteadstepmap);
                }

            } else
            {
                List _targetlist3 = new ArrayList();
                List _linklist = _workbase.getStepLinkList(_stepid);
                int i = 0;
                do
                {
                    if(i >= _linklist.size())
                        break;
                    Map _targetmap = new HashMap();
                    Map _linkmap = (Map)_linklist.get(i);
                    int _linkid = NumberTool.convertMapKeyToInt(_linkmap, "objid", Integer.valueOf(0)).intValue();
                    List _linkiflist = _workbase.getStepLinkIfList(_linkid);
                    int j = 0;
                    do
                    {
                        if(j >= _linkiflist.size())
                            break;
                        Map _linkifmap = (Map)_linkiflist.get(j);
                        if(!judgeLinkIf(_linkifmap, _paramap))
                        {
                            int _exception = NumberTool.convertMapKeyToInt(_linkifmap, "excption", Integer.valueOf(0)).intValue();
                            if(_exception > 0 || _exitcondition < exception.errorbase)
                            {
                                resultmap.put("exception", Integer.valueOf(_exception));
                                return resultmap;
                            }
                            break;
                        }
                        j++;
                    } while(true);
                    if(j == _linkiflist.size())
                    {
                        int _targetid = NumberTool.convertMapKeyToInt(_linkmap, "aftstepid", Integer.valueOf(0)).intValue();
                        _paramap.put("processstepid", Integer.valueOf(_targetid));
                        List _linkdolist = _workbase.getStepLinkDoList(_linkid);
                        for(int k = 0; k < _linkdolist.size(); k++)
                        {
                            Map _linkdomap = (Map)_linkdolist.get(k);
                            int _exception = judgeLinkDo(_linkdomap, _paramap);
                            if(_exception > 0 || _exitcondition < exception.errorbase)
                            {
                                resultmap.put("exception", Integer.valueOf(_exception));
                                return resultmap;
                            }
                        }

                        _targetmap.put("stepid", Integer.valueOf(_targetid));
                        _targetlist3.add(_targetmap);
                    }
                    if(_steptypeid != WorkflowStepType.WF_TYPE_BRANCH.getEnumItemValue() && _targetlist3.size() > 0)
                        break;
                    i++;
                } while(true);
                for(i = 0; i < _targetlist3.size(); i++)
                {
                    Map _targetmap = (Map)_targetlist3.get(i);
                    int _targetid = NumberTool.safeToInteger(_targetmap.get("stepid"), Integer.valueOf(0)).intValue();
                    List _insteadsteplist2 = ((List) (_insteadmap.get((new StringBuilder()).append("steplist_").append(_targetid).toString()) != null ? (List)_insteadmap.get((new StringBuilder()).append("steplist_").append(_targetid).toString()) : ((List) (new ArrayList()))));
                    if(_insteadsteplist2.size() > 0)
                    {
                        for(int j = 0; j < _insteadsteplist2.size(); j++)
                        {
                            Map _targetmap2 = (Map)_insteadsteplist2.get(j);
                            _targetlist2.add(_targetmap2);
                        }

                    } else
                    {
                        _targetlist2.add(_targetmap);
                    }
                }

            }
            for(int i = 0; i < _targetlist2.size(); i++)
            {
                Map _targetmap = (Map)_targetlist2.get(i);
                int _targetid = NumberTool.convertMapKeyToInt(_targetmap, "stepid", Integer.valueOf(0)).intValue();
                IWorkflowProcessStep _targetstep = _workbase.loadProcessStep(_targetid);
                _targetmap = changeWorkflowProcessStep(_targetstep);
                if(_targetstep.getStepTypeId().intValue() == WorkflowStepType.WF_TYPE_END.getEnumItemValue())
                {
                    _targetlist.add(_targetmap);
                    continue;
                }
                List _ownerlist = new ArrayList();
                List _insteadownerlist = ((List) (_insteadmap.get("stepownerlist") != null ? (List)_insteadmap.get("stepownerlist") : ((List) (new ArrayList()))));
                if(_insteadownerlist.size() == 0)
                    _insteadownerlist = ((List) (_insteadmap.get((new StringBuilder()).append("stepownerlist_").append(_targetid).toString()) != null ? (List)_insteadmap.get((new StringBuilder()).append("stepownerlist_").append(_targetid).toString()) : ((List) (new ArrayList()))));
                if(_insteadownerlist.size() > 0)
                {
                    for(int j = 0; j < _insteadownerlist.size(); j++)
                    {
                        Map _insteadownermap = (Map)_insteadownerlist.get(j);
                        _insteadownermap.put("datefrom", "insteadmap");
                        _ownerlist.add(_insteadownermap);
                    }

                    _targetmap.put("ownerlist", _ownerlist);
                } else
                if(_targetstep.getStepTypeId().intValue() == 3)
                {
                    List _ownerlinklist = _workbase.getOwnerLinkList(_targetid);
                    int _starterid = NumberTool.convertMapKeyToInt(_mainmap, "starterid", Integer.valueOf(0)).intValue();
                    if(_ownerlinklist.size() > 0)
                        _ownerlist = _workin.judgeConditioner(_starterid, _ownerlinklist, _examplestepid);
                    if(_ownerlist.size() == 0)
                        _ownerlist = _workbase.getOwnerList(-100, _targetid);
                    List _addownerlist = ((List) (_addmap.get("stepownerlist") != null ? (List)_addmap.get("stepownerlist") : ((List) (new ArrayList()))));
                    int j;
                    for(j = 0; j < _addownerlist.size(); j++)
                    {
                        Map _addownermap = (Map)_addownerlist.get(j);
                        _addownermap.put("datefrom", "addmap");
                        _ownerlist.add(_addownermap);
                    }

                    List _spownerlist = _workbase.getOwnerList(-1, _targetid);
                    if(_spownerlist.size() > 0)
                    {
                        Map _spmap = (Map)_spownerlist.get(0);
                        int _spmark = NumberTool.convertMapKeyToInt(_spmap, "orgid", Integer.valueOf(0)).intValue();
                        if(_spmark > 0)
                        {
                            _paramap.put("stepownerlist", _ownerlist);
                            _paramap.put("processstepid", Integer.valueOf(_targetid));
                            Map _resultmap = _workin.judgeSpecialDealer(_spmark, _paramap);
                            int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
                            if(_exception > 0 || _exitcondition < exception.errorbase)
                            {
                                resultmap.put("exception", Integer.valueOf(_exception));
                                return resultmap;
                            }
                            _ownerlist = _resultmap.get("stepownerlist") != null && ((List)_resultmap.get("stepownerlist")).size() != 0 ? (List)_resultmap.get("stepownerlist") : _ownerlist;
                        }
                    }
                    StringBuffer _stepbeigin = new StringBuffer();
                    j = 0;
                    do
                    {
                        if(j >= _ownerlist.size())
                            break;
                        Map _ownermap = (Map)_ownerlist.get(j);
                        int _skipmark = NumberTool.convertMapKeyToInt(_ownermap, "mark", Integer.valueOf(1)).intValue();
                        if(_skipmark != 1)
                        {
                            _stepbeigin.append("skipmark ").append(_skipmark).append(" ");
                            break;
                        }
                        j++;
                    } while(true);
                    _targetmap.put("stepbeigin", _stepbeigin.toString());
                    _targetmap.put("ownerlist", _ownerlist);
                } else
                if(_targetstep.getStepTypeId().intValue() == 7)
                {
                    int _targetsteptypeads = NumberTool.convertMapKeyToInt(_targetmap, "steptypeads", Integer.valueOf(0)).intValue();
                    if(_targetsteptypeads == 2)
                    {
                        Map _ownermap = new HashMap();
                        int _ownerid = NumberTool.convertMapKeyToInt(_mainmap, "starterid", Integer.valueOf(0)).intValue();
                        _ownermap.put("orgid", Integer.valueOf(_ownerid));
                        _ownermap.put("orgtype", Integer.valueOf(8));
                        _ownermap.put("value", Integer.valueOf(1));
                        _ownerlist.add(_ownermap);
                    } else
                    if(_targetsteptypeads == 3)
                    {
                        List tempownerlist = getThisStepNextHistoryOwner(_exampleid, _targetid);
                        for(int ii = 0; ii < tempownerlist.size(); ii++)
                        {
                            Map _ownermap = (Map)tempownerlist.get(ii);
                            _ownerlist.add(_ownermap);
                        }

                    } else
                    if(_targetsteptypeads == 1)
                        _ownerlist = _workbase.getOwnerList(-100, _targetid);
                    List _addownerlist = ((List) (_addmap.get("stepownerlist") != null ? (List)_addmap.get("stepownerlist") : ((List) (new ArrayList()))));
                    for(int j = 0; j < _addownerlist.size(); j++)
                    {
                        Map _addownermap = (Map)_addownerlist.get(j);
                        _addownermap.put("datefrom", "addmap");
                        _ownerlist.add(_addownermap);
                    }

                    List _spownerlist = _workbase.getOwnerList(-1, _targetid);
                    if(_spownerlist.size() > 0)
                    {
                        Map _spmap = (Map)_spownerlist.get(0);
                        int _spmark = NumberTool.convertMapKeyToInt(_spmap, "orgid", Integer.valueOf(0)).intValue();
                        if(_spmark > 0)
                        {
                            _paramap.put("stepownerlist", _ownerlist);
                            _paramap.put("processstepid", Integer.valueOf(_targetid));
                            Map _resultmap = _workin.judgeSpecialDealer(_spmark, _paramap);
                            int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
                            if(_exception > 0 || _exitcondition < exception.errorbase)
                            {
                                resultmap.put("exception", Integer.valueOf(_exception));
                                return resultmap;
                            }
                            _ownerlist = _resultmap.get("stepownerlist") != null && ((List)_resultmap.get("stepownerlist")).size() != 0 ? (List)_resultmap.get("stepownerlist") : _ownerlist;
                        }
                    }
                    _targetmap.put("ownerlist", _ownerlist);
                }
                _targetlist.add(_targetmap);
            }

        }
        _stepmap.put("exampleid", Integer.valueOf(_exampleid));
        resultmap.put("stepmap", _stepmap);
        resultmap.put("targetlist", _targetlist);
        resultmap.put("targetsize", Integer.valueOf(_targetlist.size()));
        return resultmap;
    }

    private int judgeExitStep(Map stepmap, Map paramap)
    {
        int mark1;
        int mark2;
label0:
        {
            IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
            IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
            int _stepid = NumberTool.convertMapKeyToInt(stepmap, "stepid", Integer.valueOf(0)).intValue();
            IWorkflowProcessStep _step = _workbase.loadProcessStep(_stepid);
            int _typeid = NumberTool.safeToInteger(_step.getStepTypeId(), Integer.valueOf(0)).intValue();
            String _typeads = StringUtil.safeToString(_step.getStepTypeAds(), "");
            int spcondition = NumberTool.convertMapKeyToInt(stepmap, "condition", Integer.valueOf(0)).intValue();
            if(_typeid != WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue() && _typeid != WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
                return spcondition;
            List _ownerlist = ((List) (stepmap.get("ownerlist") != null ? (List)stepmap.get("ownerlist") : ((List) (new ArrayList()))));
            int totalren = 0;
            int totalpiao = 0;
            Map renmap = new HashMap();
            Map piaomap = new HashMap();
            for(int i = 0; i < _ownerlist.size(); i++)
            {
                Map _ownermap = (Map)_ownerlist.get(i);
                int ownerstatus = NumberTool.convertMapKeyToInt(_ownermap, "ownerstatus", Integer.valueOf(0)).intValue();
                int approvestatus = NumberTool.convertMapKeyToInt(_ownermap, "approvestatus", Integer.valueOf(0)).intValue();
                int value = NumberTool.convertMapKeyToInt(_ownermap, "value", Integer.valueOf(0)).intValue();
                if(ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue())
                {
                    if(approvestatus != 0)
                    {
                        int tempren = NumberTool.convertMapKeyToInt(renmap, (new StringBuilder()).append("").append(approvestatus).toString(), Integer.valueOf(0)).intValue();
                        int temppiao = NumberTool.convertMapKeyToInt(piaomap, (new StringBuilder()).append("").append(approvestatus).toString(), Integer.valueOf(0)).intValue();
                        renmap.put((new StringBuilder()).append("").append(approvestatus).toString(), Integer.valueOf(tempren + 1));
                        piaomap.put((new StringBuilder()).append("").append(approvestatus).toString(), Integer.valueOf(temppiao + value));
                    }
                    totalren++;
                    totalpiao += value;
                    continue;
                }
                if(ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_PEND.getEnumItemValue() || ownerstatus == WorkflowStepOwnerStatus.WF_OWNER_BEING.getEnumItemValue())
                {
                    totalren++;
                    totalpiao += value;
                }
            }

            mark1 = 0;
            mark2 = -1;
            if(_typeid == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
            {
                Iterator i$ = renmap.keySet().iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    Object key = i$.next();
                    int condition = NumberTool.safeToInteger(key, Integer.valueOf(0)).intValue();
                    if(condition <= 0)
                        continue;
                    int value = NumberTool.convertMapKeyToInt(renmap, (new StringBuilder()).append("").append(condition).toString(), Integer.valueOf(0)).intValue();
                    if(value <= 0)
                        continue;
                    mark2 = condition;
                    break;
                } while(true);
                return mark2;
            }
            String judgeArr[] = _typeads.split(" ");
            if(judgeArr.length == 1)
            {
                int judgetype = NumberTool.safeToInteger(judgeArr[0], Integer.valueOf(0)).intValue();
                if(judgetype == WorkflowStepJudgeType.WF_JUDGE_SEIZE.getEnumItemValue())
                {
                    Iterator i$ = renmap.keySet().iterator();
                    int condition;
                    int value;
                    do
                    {
                        do
                        {
                            if(!i$.hasNext())
                                break label0;
                            Object key = i$.next();
                            condition = NumberTool.safeToInteger(key, Integer.valueOf(0)).intValue();
                        } while(condition <= 0);
                        value = NumberTool.convertMapKeyToInt(renmap, (new StringBuilder()).append("").append(condition).toString(), Integer.valueOf(0)).intValue();
                    } while(value <= 0);
                    mark2 = condition;
                } else
                if(judgetype == WorkflowStepJudgeType.WF_JUDGE_SP.getEnumItemValue())
                    mark1 = 1;
            } else
            if(judgeArr.length == 2)
            {
                int judgetype = NumberTool.safeToInteger(judgeArr[0], Integer.valueOf(0)).intValue();
                int judgeads = NumberTool.safeToInteger(judgeArr[1], Integer.valueOf(0)).intValue();
                if(judgetype == WorkflowStepJudgeType.WF_JUDGE_OTHER.getEnumItemValue())
                {
                    Map _resultmap = _workin.judgeSpecialApproval(judgeads, paramap);
                    int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
                    if(_exception != 0)
                        stepmap.put("exception", Integer.valueOf(_exception));
                    int _condition = NumberTool.convertMapKeyToInt(_resultmap, "condition", Integer.valueOf(-1)).intValue();
                    if(_condition > -1)
                        mark2 = _condition;
                }
            } else
            if(judgeArr.length == 3)
            {
                int judgetype = NumberTool.safeToInteger(judgeArr[0], Integer.valueOf(0)).intValue();
                int judgeads = NumberTool.safeToInteger(judgeArr[1], Integer.valueOf(0)).intValue();
                double judgeadsads = NumberTool.safeToDouble(judgeArr[2], Double.valueOf(0.0D)).doubleValue();
                double pass = 0.0D;
                double unpass = 0.0D;
                if(judgetype == WorkflowStepJudgeType.WF_JUDGE_VOTE.getEnumItemValue() || judgetype == WorkflowStepJudgeType.WF_JUDGE_WAIT.getEnumItemValue())
                {
                    if(judgeads == 1)
                    {
                        pass = NumberTool.safeToDouble(renmap.get("1"), Double.valueOf(0.0D)).doubleValue();
                        unpass = NumberTool.safeToDouble(renmap.get("2"), Double.valueOf(0.0D)).doubleValue();
                        if((pass * 100D) / (double)totalren >= judgeadsads)
                            mark2 = 1;
                        else
                        if((unpass * 100D) / (double)totalren > 100D - judgeadsads)
                            mark2 = 2;
                    } else
                    if(judgeads == 2)
                    {
                        pass = NumberTool.safeToDouble(renmap.get("1"), Double.valueOf(0.0D)).doubleValue();
                        unpass = NumberTool.safeToDouble(renmap.get("2"), Double.valueOf(0.0D)).doubleValue();
                        if(pass >= judgeadsads)
                            mark2 = 1;
                        else
                        if(unpass > (double)totalren - judgeadsads)
                            mark2 = 2;
                    } else
                    if(judgeads == 3)
                    {
                        pass = NumberTool.safeToDouble(piaomap.get("1"), Double.valueOf(0.0D)).doubleValue();
                        unpass = NumberTool.safeToDouble(piaomap.get("2"), Double.valueOf(0.0D)).doubleValue();
                        if((pass * 100D) / (double)totalpiao >= judgeadsads)
                            mark2 = 1;
                        else
                        if((unpass * 100D) / (double)totalpiao > 100D - judgeadsads)
                            mark2 = 2;
                    } else
                    if(judgeads == 4)
                    {
                        pass = NumberTool.safeToDouble(piaomap.get("1"), Double.valueOf(0.0D)).doubleValue();
                        unpass = NumberTool.safeToDouble(piaomap.get("2"), Double.valueOf(0.0D)).doubleValue();
                        if(pass >= judgeadsads)
                            mark2 = 1;
                        else
                        if(unpass > (double)totalpiao - judgeadsads)
                            mark2 = 2;
                    }
                    if(judgetype == WorkflowStepJudgeType.WF_JUDGE_WAIT.getEnumItemValue())
                    {
                        pass = NumberTool.safeToDouble(renmap.get("1"), Double.valueOf(0.0D)).doubleValue();
                        unpass = NumberTool.safeToDouble(renmap.get("2"), Double.valueOf(0.0D)).doubleValue();
                        if(pass + unpass != (double)totalren)
                            mark2 = -1;
                    }
                }
            }
        }
        if(mark1 == 0)
            return mark2;
        else
            return mark2;
    }

    private boolean compare(double num1, double num2, int type)
    {
        boolean result = false;
        if(type == 1)
        {
            if(num1 == num2)
                result = true;
        } else
        if(type == 2)
        {
            if(num1 != num2)
                result = true;
        } else
        if(type == 3)
        {
            if(num1 > num2)
                result = true;
        } else
        if(type == 4)
        {
            if(num1 >= num2)
                result = true;
        } else
        if(type == 5)
        {
            if(num1 < num2)
                result = true;
        } else
        if(type == 6 && num1 <= num2)
            result = true;
        return result;
    }

    private int judgeExitStep2(Map stepmap, Map paramap)
    {
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        String _stepend = StringUtil.safeToString(stepmap.get("stepend"), "");
        int _condition = NumberTool.convertMapKeyToInt(stepmap, "condition", Integer.valueOf(0)).intValue();
        if(_stepend.length() == 0)
            return _condition;
        String _endArr[] = _stepend.split(";");
        if(_endArr.length == 0)
            return _condition;
        if(_endArr.length == 1)
            return _condition;
        if(_endArr.length == 2)
        {
            int _mark = NumberTool.safeToInteger(_endArr[0], Integer.valueOf(0)).intValue();
            int _type = NumberTool.safeToInteger(_endArr[1], Integer.valueOf(0)).intValue();
            Map _resultmap = _workin.judgeSpecialApproval(_type, paramap);
            int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
            _condition = NumberTool.convertMapKeyToInt(_resultmap, "condition", Integer.valueOf(-1)).intValue();
            if(_exception != 0)
                stepmap.put("exception", Integer.valueOf(_exception));
            return _condition;
        }
        if(_endArr.length == 3)
        {
            String _totalMsg[] = _endArr[0].split(" ");
            int totalRen = 0;
            int totalType = 0;
            double totalPiao = 0.0D;
            if(_totalMsg.length >= 1)
            {
                totalRen = NumberTool.safeToInteger(_totalMsg[0], Integer.valueOf(0)).intValue();
                if(_totalMsg.length >= 2)
                {
                    totalPiao = NumberTool.safeToDouble(_totalMsg[1], Double.valueOf(0.0D)).doubleValue();
                    if(_totalMsg.length >= 3)
                        totalType = NumberTool.safeToInteger(_totalMsg[2], Integer.valueOf(0)).intValue();
                }
            }
            String _baseMsg[] = _endArr[1].split(",");
            List _baselist = new ArrayList();
            for(int i = 0; i < _baseMsg.length; i++)
            {
                String _baseArr[] = _baseMsg[i].split(" ");
                Map _basemap = new HashMap();
                _basemap.put("basetype", "0");
                _basemap.put("baseren", "0");
                _basemap.put("basepiao", "0");
                if(_baseArr.length >= 1)
                {
                    _basemap.put("basetype", _baseArr[0]);
                    if(_baseArr.length >= 2)
                    {
                        _basemap.put("baseren", _baseArr[1]);
                        if(_baseArr.length >= 3)
                            _basemap.put("basepiao", _baseArr[2]);
                    }
                }
                _baselist.add(_basemap);
            }

            String _judgeMsg[] = _endArr[2].split(",");
            List _judgelist = new ArrayList();
            for(int i = 0; i < _judgeMsg.length; i++)
            {
                String _judgeArr[] = _judgeMsg[i].split(" ");
                Map _judgemap = new HashMap();
                _judgemap.put("judgeresult", "0");
                _judgemap.put("judgetype", "0");
                _judgemap.put("judgeads", "0");
                if(_judgeArr.length >= 1)
                {
                    _judgemap.put("judgeresult", _judgeArr[0]);
                    if(_judgeArr.length >= 2)
                    {
                        _judgemap.put("judgetype", _judgeArr[1]);
                        if(_judgeArr.length >= 3)
                            _judgemap.put("judgeads", _judgeArr[2]);
                    }
                }
                _judgelist.add(_judgemap);
            }

label0:
            for(int i = 0; i < _judgelist.size(); i++)
            {
                Map _judgemap = (Map)_judgelist.get(i);
                int _judgeresult = NumberTool.convertMapKeyToInt(_judgemap, "judgeresult", Integer.valueOf(0)).intValue();
                int _judgetype = NumberTool.convertMapKeyToInt(_judgemap, "judgetype", Integer.valueOf(0)).intValue();
                double _judgeads = NumberTool.safeToDouble(_judgemap.get("judgeads"), Double.valueOf(0.0D)).doubleValue();
                int j = 0;
                do
                {
                    if(j >= _baselist.size())
                        continue label0;
                    Map _basemap = (Map)_baselist.get(j);
                    int _basetype = NumberTool.convertMapKeyToInt(_basemap, "basetype", Integer.valueOf(0)).intValue();
                    if(_basetype == _judgeresult)
                    {
                        int _baseren = NumberTool.convertMapKeyToInt(_basemap, "baseren", Integer.valueOf(0)).intValue();
                        double _basepiao = NumberTool.safeToDouble(_basemap.get("basepiao"), Double.valueOf(0.0D)).doubleValue();
                        if(_judgetype == 1)
                        {
                            if((double)_baseren * 1.0D >= (_judgeads * (double)totalRen) / 100D)
                                return _judgeresult;
                            continue label0;
                        }
                        if(_judgetype == 2)
                        {
                            if((double)_baseren * 1.0D >= _judgeads)
                                return _judgeresult;
                            continue label0;
                        }
                        if(_judgetype == 3)
                        {
                            if(_basepiao * 1.0D >= (_judgeads * totalPiao) / 100D)
                                return _judgeresult;
                            continue label0;
                        }
                        if(_judgetype == 4 && _basepiao * 1.0D >= _judgeads)
                            return _judgeresult;
                        continue label0;
                    }
                    j++;
                } while(true);
            }

            return -1;
        } else
        {
            return -2;
        }
    }

    private boolean judgeEnterStep(Map stepmap)
    {
        String _stepbeigin = StringUtil.safeToString(stepmap.get("stepbeigin"), "");
        return true;
    }

    private int judgeLinkDo(Map linkdomap, Map paramap)
    {
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        int _linkdotype = NumberTool.convertMapKeyToInt(linkdomap, "dotype", Integer.valueOf(0)).intValue();
        if(_linkdotype != 0)
        {
            Map _resultmap = _workin.judgeSpecialTask(_linkdotype, paramap);
            int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
            return _exception;
        } else
        {
            return 0;
        }
    }

    private boolean judgeLinkIf(Map linkifmap, Map paramap)
    {
        IWorkflowInService _workin = WorkflowServiceFactory.getWorkflowInService();
        int _linkiftype = NumberTool.convertMapKeyToInt(linkifmap, "iftype", Integer.valueOf(0)).intValue();
        int _linkifand = NumberTool.convertMapKeyToInt(linkifmap, "ifand", Integer.valueOf(0)).intValue();
        String _linkifads = StringUtil.safeToString(linkifmap.get("ifads"), "");
        if(_linkiftype != 1)
            if(_linkiftype == 2)
            {
                int _linkifnow = NumberTool.convertMapKeyToInt(paramap, "condition", Integer.valueOf(0)).intValue();
                int _linkifbase = NumberTool.safeToInteger(_linkifads, Integer.valueOf(0)).intValue();
                if(_linkifand == 1)
                {
                    if(_linkifnow == _linkifbase)
                        return true;
                } else
                if(_linkifand == 2)
                {
                    if(_linkifnow != _linkifbase)
                        return true;
                } else
                if(_linkifand == 3)
                {
                    if(_linkifnow > _linkifbase)
                        return true;
                } else
                if(_linkifand == 4)
                {
                    if(_linkifnow >= _linkifbase)
                        return true;
                } else
                if(_linkifand == 5)
                {
                    if(_linkifnow < _linkifbase)
                        return true;
                } else
                if(_linkifand == 6 && _linkifnow <= _linkifbase)
                    return true;
            } else
            if(_linkiftype >= 0x186a0)
            {
                Map _resultmap = _workin.judgeSpecialCondition(_linkiftype, paramap);
                int _exception = NumberTool.convertMapKeyToInt(_resultmap, "exception", Integer.valueOf(0)).intValue();
                int _result = NumberTool.convertMapKeyToInt(_resultmap, "result", Integer.valueOf(-1)).intValue();
                linkifmap.put("excption", Integer.valueOf(_exception));
                int _judgeresult = NumberTool.safeToInteger(_linkifads, Integer.valueOf(-1)).intValue();
                return _result == _judgeresult;
            }
        return false;
    }

    private Map changeWorkflowProcessStep(IWorkflowProcessStep step)
    {
        Map _map = new HashMap();
        _map.put("stepid", Integer.valueOf(step.getId()));
        _map.put("processid", step.getProcessId());
        _map.put("stepname", step.getName());
        _map.put("steptypeid", step.getStepTypeId());
        _map.put("steptypeads", step.getStepTypeAds());
        _map.put("actionurl", step.getActionUrl());
        _map.put("showurl", step.getShowUrl());
        _map.put("condition", step.getCondition());
        return _map;
    }

    public Map getStartStep(int processid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select ps.objid ");
        _sql.append("from WF_PROCESSSTEP ps ");
        _sql.append("where ps.processid=").append(processid).append(" and ps.status=1 and steptypeid=1");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map getProcessByCodeAndVersion(String processcode, int processversion)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p.objid as processid,p.name as processname,p.status ");
        _sql.append("from WF_PROCESS p ");
        _sql.append("where p.processcode='").append(processcode).append("' and (p.status=2 or p.status=3) ");
        if(processversion > 0)
            _sql.append("and p.processversion=").append(processversion).append(" ");
        _sql.append("order by p.objid desc");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        if(_list.size() > 0)
            return (Map)_list.get(0);
        else
            return new HashMap();
    }

    public Map getProcessBySyscode(String syscode)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select p2.objid as processid,p2.name as processname,p2.status ");
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

    public IWorkflowProcess getProcessById(int id)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        return _workbase.loadProcess(id);
    }

    private List getThisStepNextHistoryOwner(int exampleid, int processstepid)
    {
        List list = getThisStepHistoryOwner(exampleid, processstepid);
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        int tempid = 0;
        for(int i = 0; i < list.size(); i++)
        {
            Map map = (Map)list.get(i);
            int examplestepid = NumberTool.safeToInteger(map.get("examplestepid"), Integer.valueOf(0)).intValue();
            if(tempid == 0)
                tempid = examplestepid;
            else
            if(tempid != examplestepid)
                break;
            list2.add(map);
        }

        for(int i = 0; i < list2.size(); i++)
        {
            Map map = (Map)list2.get(i);
            Map ownermap = new HashMap();
            ownermap.put("orgid", map.get("ownerid"));
            ownermap.put("orgtype", Integer.valueOf(8));
            ownermap.put("value", map.get("value"));
            list3.add(ownermap);
        }

        return list3;
    }

    private List getThisStepHistoryOwner(int exampleid, int processstepid)
    {
        StringBuffer _sql = new StringBuffer();
        _sql.append("select es.objid as examplestepid,eso.ownerid,eso.value \n");
        _sql.append("from WF_EXAMPLESTEP es \n");
        _sql.append("inner join WF_EXAMPLESTEPOWNER eso on eso.examplestepid=es.objid \n");
        _sql.append("where es.exampleid=").append(exampleid).append(" and es.processstepid=").append(processstepid).append(" \n");
        _sql.append("and eso.preownerid=0 \n");
        _sql.append("order by es.objid desc \n");
        IMetaDBQuery _dbQuery = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = _dbQuery.getResult();
        return _list;
    }
}
