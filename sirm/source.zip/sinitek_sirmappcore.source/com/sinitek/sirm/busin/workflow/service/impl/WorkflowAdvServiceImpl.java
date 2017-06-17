// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowAdvServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.entity.drawprocess.*;
import com.sinitek.sirm.busin.workflow.enumerate.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.service.drawprocess.IWorkflowDrawService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

public class WorkflowAdvServiceImpl extends MetaDBContextSupport
    implements IWorkflowAdvService
{

    public WorkflowAdvServiceImpl()
    {
    }

    public List getProcessStepList(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        Map _baseparamap = _workbase.findAllParaMap();
        List _steplist = new ArrayList();
        List _stepmainlist = _workbase.getProcessStepList(processid);
        if(_stepmainlist.size() == 0)
            return _steplist;
        for(int ii = 0; ii < _stepmainlist.size(); ii++)
        {
            Map _stepmap = (Map)_stepmainlist.get(ii);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            int _steptypeid = NumberTool.convertMapKeyToInt(_stepmap, "steptypeid", Integer.valueOf(0)).intValue();
            String _steptypeads = StringUtil.safeToString(_stepmap.get("steptypeads"), "");
            String _steptypeadsArr[] = _steptypeads.split(" ");
            int _steptypemark = 0;
            if(_steptypeadsArr.length > 0)
                _steptypemark = NumberTool.safeToInteger(_steptypeadsArr[0], Integer.valueOf(0)).intValue();
            String _acrosstype = "";
            if(_steptypeid == 3)
                _acrosstype = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("JudgeType").append(_steptypemark).toString()), "");
            else
            if(_steptypeid == 7)
                _acrosstype = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("SubmitOwner").append(_steptypemark).toString()), "");
            _stepmap.put("acrosstype", _acrosstype);
            List _ownerlist = _workbase.getOwnerList(0, _stepid);
            _stepmap.put("ownerlist", _ownerlist);
            List _ownerlinklist = new ArrayList();
            List _ownerlinkmainlist = _workbase.getOwnerLinkList(_stepid);
            for(int i = 0; i < _ownerlinkmainlist.size(); i++)
            {
                Map _ownerlinkmap = (Map)_ownerlinkmainlist.get(i);
                String _ownerstarter = StringUtil.safeToString(_ownerlinkmap.get("ownerstarter"), "");
                String _starterArr[] = _ownerstarter.split(",");
                String _ownerstartershow = "";
                for(int j = 0; j < _starterArr.length; j++)
                {
                    String _starterArrArr[] = _starterArr[j].split(":");
                    if(_starterArrArr.length == 3)
                        _ownerstartershow = (new StringBuilder()).append(_ownerstartershow).append(_starterArrArr[1]).append("\uFF0C").toString();
                }

                if(_ownerstartershow.length() > 0)
                    _ownerlinkmap.put("ownerstartershow", _ownerstartershow.substring(0, _ownerstartershow.length() - 1));
                else
                    _ownerlinkmap.put("ownerstartershow", _ownerstartershow);
                String _ownerender = StringUtil.safeToString(_ownerlinkmap.get("ownerender"), "");
                String _enderArr[] = _ownerender.split(",");
                String _ownerendershow = "";
                for(int j = 0; j < _enderArr.length; j++)
                {
                    String _enderArrArr[] = _enderArr[j].split(":");
                    if(_enderArrArr.length == 3)
                        _ownerendershow = (new StringBuilder()).append(_ownerendershow).append(_enderArrArr[1]).append("\uFF0C").toString();
                }

                if(_ownerendershow.length() > 0)
                    _ownerlinkmap.put("ownerendershow", _ownerendershow.substring(0, _ownerendershow.length() - 1));
                else
                    _ownerlinkmap.put("ownerendershow", _ownerendershow);
                _ownerlinklist.add(_ownerlinkmap);
            }

            _stepmap.put("ownerlinklistsize", Integer.valueOf(_ownerlinklist.size()));
            _stepmap.put("ownerlinklist", _ownerlinklist);
            List _linklist = new ArrayList();
            List _linkmainlist = _workbase.getStepLinkList(_stepid);
            for(int i = 0; i < _linkmainlist.size(); i++)
            {
                Map _linkmap = (Map)_linkmainlist.get(i);
                int _linkid = NumberTool.convertMapKeyToInt(_linkmap, "objid", Integer.valueOf(0)).intValue();
                List _linkiflist = new ArrayList();
                List _linkifmainlist = _workbase.getStepLinkIfList(_linkid);
                for(int j = 0; j < _linkifmainlist.size(); j++)
                {
                    Map _linkifmap = (Map)_linkifmainlist.get(j);
                    int _iftype = NumberTool.convertMapKeyToInt(_linkifmap, "iftype", Integer.valueOf(0)).intValue();
                    int _ifand = NumberTool.convertMapKeyToInt(_linkifmap, "ifand", Integer.valueOf(0)).intValue();
                    String _ifads = StringUtil.safeToString(_linkifmap.get("ifads"), "0");
                    String _iftypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("LinkIfType").append(_iftype).toString()), "");
                    if(_iftype >= 0x186a0)
                        _iftypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append(_process.getProcessType()).append("LinkIfType").append(_iftype).toString()), "");
                    String _ifandtext = "";
                    String _ifadstext = "";
                    if(_iftype == 2)
                    {
                        _ifandtext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("MathMark").append(_ifand).toString()), "");
                        _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("JudgeResult").append(_ifads).toString()), "");
                    } else
                    if(_iftype == 1)
                    {
                        _ifandtext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("SpecialMark").append(_ifand).toString()), "");
                        if(_process.getProcessType() != null)
                            _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append(_process.getProcessType()).append("SpecialResult").append(_ifads).toString()), "");
                    } else
                    if(_iftype >= 0x186a0)
                    {
                        _ifandtext = "";
                        _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("LinkIfResult").append(_ifads).toString()), "");
                    }
                    String _ifshowtext = (new StringBuilder()).append(_iftypetext).append(" ").append(_ifandtext).append(" ").append(_ifadstext).toString();
                    _linkifmap.put("ifshow", _ifshowtext);
                    _linkiflist.add(_linkifmap);
                }

                _linkmap.put("linkiflist", _linkiflist);
                _linkmap.put("linkiflistsize", Integer.valueOf(_linkiflist.size()));
                _linklist.add(_linkmap);
            }

            _stepmap.put("linklist", _linklist);
            double linkheight = 0.0D;
            double everyheight = 24D;
            double everynotopheight = everyheight - 1.0D;
            for(Iterator i$ = _linklist.iterator(); i$.hasNext();)
            {
                Map map = (Map)i$.next();
                List list = (List)map.get("linkiflist");
                linkheight += (double)list.size() * everynotopheight + 1.0D;
            }

            linkheight++;
            double ownheight = (double)_ownerlinklist.size() * everyheight + 1.0D;
            double finalheight;
            if(ownheight >= linkheight)
            {
                _stepmap.put("whichislower", "linkheight");
                finalheight = _linklist.size() != 0 ? (ownheight - linkheight) / (double)_linklist.size() + everynotopheight : 0.0D;
            } else
            {
                _stepmap.put("whichislower", "ownheight");
                finalheight = _ownerlinklist.size() != 0 ? (linkheight - ownheight) / (double)_ownerlinklist.size() + everynotopheight : 0.0D;
            }
            _stepmap.put("htmlheight", Double.valueOf(finalheight));
            _stepmap.put("linklistsize", Integer.valueOf(_linklist.size()));
            _steplist.add(_stepmap);
        }

        return _steplist;
    }

    public List getOrderStepList(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List ordersteplist = new ArrayList();
        List steplist = _workbase.getSingleStepList(processid);
        Map steptypemap = new HashMap();
        Map stepnamemap = new HashMap();
        Map steplinkmap = new HashMap();
        List linklist = _workbase.getProcessLinkList(processid);
        int beiginid = 0;
        for(int i = 0; i < steplist.size(); i++)
        {
            Map stepmap = (Map)steplist.get(i);
            int stepid = NumberTool.safeToInteger(stepmap.get("processstepid"), Integer.valueOf(0)).intValue();
            int typeid = NumberTool.safeToInteger(stepmap.get("steptypeid"), Integer.valueOf(0)).intValue();
            String name = StringUtil.safeToString(stepmap.get("name"), "");
            steptypemap.put((new StringBuilder()).append("").append(stepid).toString(), Integer.valueOf(typeid));
            stepnamemap.put((new StringBuilder()).append("").append(stepid).toString(), name);
            if(typeid == WorkflowStepType.WF_TYPE_START.getEnumItemValue())
                beiginid = stepid;
        }

        for(int i = 0; i < linklist.size(); i++)
        {
            Map linkmap = (Map)linklist.get(i);
            int sort = NumberTool.safeToInteger(linkmap.get("sort"), Integer.valueOf(0)).intValue();
            if(sort == 1)
            {
                int aftstepid = NumberTool.safeToInteger(linkmap.get("aftstepid"), Integer.valueOf(0)).intValue();
                int prestepid = NumberTool.safeToInteger(linkmap.get("prestepid"), Integer.valueOf(0)).intValue();
                steplinkmap.put((new StringBuilder()).append("").append(prestepid).toString(), Integer.valueOf(aftstepid));
            }
        }

        int tempid;
        for(int searchid = beiginid; searchid > 0; searchid = tempid)
        {
            int searchtype = NumberTool.safeToInteger(steptypemap.get((new StringBuilder()).append("").append(searchid).toString()), Integer.valueOf(0)).intValue();
            Map stepmap = new HashMap();
            stepmap.put("stepid", Integer.valueOf(searchid));
            stepmap.put("steptype", Integer.valueOf(searchtype));
            tempid = NumberTool.safeToInteger(steplinkmap.get((new StringBuilder()).append("").append(searchid).toString()), Integer.valueOf(0)).intValue();
            if(tempid >= 0)
            {
                String name = StringUtil.safeToString(stepnamemap.get((new StringBuilder()).append("").append(searchid).toString()), "");
                stepmap.put("name", name);
                ordersteplist.add(stepmap);
                steplinkmap.put((new StringBuilder()).append("").append(searchid).toString(), Integer.valueOf(-1));
            }
        }

        return ordersteplist;
    }

    public List getPhysicalStepList(int processid, int exampleid, int processstepid, int examplestepid, int type, int even)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List oldsteplist = getOrderStepList(processid);
        List hissteplist = _workapp.getExampleStepList(exampleid);
        List resultlist = new ArrayList();
        int mark = 0;
        Map sortmap = new HashMap();
        for(int i = 0; i < oldsteplist.size(); i++)
        {
            Map stepmap = (Map)oldsteplist.get(i);
            int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
            sortmap.put((new StringBuilder()).append("").append(stepid).toString(), Integer.valueOf(i));
        }

        Map evenmap = new HashMap();
        int evennum = 1;
        int lastmark = -1;
        int nextmarkArr[] = new int[oldsteplist.size()];
        int nextmarkNum = 0;
        for(int i = 0; i < hissteplist.size(); i++)
        {
            Map stepmap = (Map)hissteplist.get(i);
            int stepstatus = NumberTool.safeToInteger(stepmap.get("stepstatus"), Integer.valueOf(-1)).intValue();
            if(stepstatus != WorkflowStepStatus.WF_STEP_PROCESS.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_AUTO.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_BEING.getEnumItemValue())
                continue;
            int stepid = NumberTool.safeToInteger(stepmap.get("processstepid"), Integer.valueOf(0)).intValue();
            evenmap.put((new StringBuilder()).append(stepid).append("").toString(), Integer.valueOf(evennum));
            int tempmark = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(-1)).intValue();
            if(tempmark != -1)
            {
                if(tempmark < lastmark)
                {
                    nextmarkArr[nextmarkNum] = lastmark;
                    nextmarkNum++;
                } else
                {
                    int j;
                    for(j = nextmarkNum - 1; j >= 0 && tempmark >= nextmarkArr[j]; j--);
                    nextmarkNum = j;
                }
                lastmark = tempmark;
            } else
            {
                String stepname = StringUtil.safeToString(stepmap.get("processstepname"), "");
                LOGGER.warn((new StringBuilder()).append("\u6B65\u9AA4").append(stepname).append("\u4E0D\u5728\u6D41\u7A0B\u4E3B\u7EBF\u4E0A").toString());
            }
        }

        int nextmark = oldsteplist.size();
        if(nextmarkNum > 0)
            nextmark = nextmarkArr[nextmarkNum - 1];
        if(type == 1)
        {
            for(int i = 0; i < oldsteplist.size(); i++)
            {
                Map stepmap = (Map)oldsteplist.get(i);
                int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                if(stepid == processstepid)
                    mark = 1;
                if(mark == 1)
                    resultlist.add(stepmap);
            }

        } else
        if(type == 2 || type == 4)
        {
            int i = 0;
label0:
            do
            {
label1:
                {
                    if(i >= oldsteplist.size())
                        break label0;
                    Map stepmap = (Map)oldsteplist.get(i);
                    int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                    if(mark == 0)
                    {
                        if(even == 1)
                        {
                            int evenmark = NumberTool.safeToInteger(evenmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(0)).intValue();
                            if(evenmark == 0)
                                break label1;
                        }
                        resultlist.add(stepmap);
                    }
                    if(stepid == processstepid)
                        mark = 1;
                }
                i++;
            } while(true);
        } else
        if(type == 3)
        {
            if(nextmark == oldsteplist.size())
                even = 0;
            for(int i = 0; i < nextmark; i++)
            {
                Map stepmap = (Map)oldsteplist.get(i);
                int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                if(stepid == processstepid)
                    mark = 1;
                if(mark != 1)
                    continue;
                if(even == 1)
                {
                    int evenmark = NumberTool.safeToInteger(evenmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(0)).intValue();
                    if(evenmark == 0)
                        continue;
                }
                resultlist.add(stepmap);
            }

        }
        return resultlist;
    }

    public List getLogicStepList(int processid, int exampleid, int processstepid, int examplestepid, int type, int even)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        List oldsteplist = _workbase.getSingleStepList(processid);
        List hissteplist = _workapp.getExampleStepList(exampleid);
        List resultlist = new ArrayList();
        Map sortmap = new HashMap();
        int sortnum = 1;
        Map evenmap = new HashMap();
        int evennum = 1;
        int lastmark = -1;
        int nextmarkArr[] = new int[oldsteplist.size()];
        int nextmarkNum = 0;
        for(int i = 0; i < hissteplist.size(); i++)
        {
            Map stepmap = (Map)hissteplist.get(i);
            int stepstatus = NumberTool.safeToInteger(stepmap.get("stepstatus"), Integer.valueOf(-1)).intValue();
            if(stepstatus != WorkflowStepStatus.WF_STEP_PROCESS.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_AUTO.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_PEND.getEnumItemValue() && stepstatus != WorkflowStepStatus.WF_STEP_BEING.getEnumItemValue())
                continue;
            int stepid = NumberTool.safeToInteger(stepmap.get("processstepid"), Integer.valueOf(0)).intValue();
            evenmap.put((new StringBuilder()).append(stepid).append("").toString(), Integer.valueOf(evennum));
            int tempmark = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(-1)).intValue();
            if(tempmark == -1)
            {
                tempmark = sortnum;
                sortmap.put((new StringBuilder()).append("").append(stepid).toString(), Integer.valueOf(sortnum));
                sortnum++;
            }
            if(tempmark == -1)
                continue;
            if(tempmark < lastmark)
            {
                nextmarkArr[nextmarkNum] = lastmark;
                nextmarkNum++;
            } else
            {
                int j;
                for(j = nextmarkNum - 1; j >= 0 && tempmark >= nextmarkArr[j]; j--);
                nextmarkNum = j + 1;
            }
            lastmark = tempmark;
        }

        int nextmark = sortnum;
        if(nextmarkNum > 0)
            nextmark = nextmarkArr[nextmarkNum - 1];
        int basesort = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(processstepid).toString()), Integer.valueOf(sortnum)).intValue();
        if(type == 1)
        {
            for(int i = 0; i < oldsteplist.size(); i++)
            {
                Map stepmap = (Map)oldsteplist.get(i);
                int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                int tempsort = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(sortnum)).intValue();
                if(tempsort >= basesort)
                    resultlist.add(stepmap);
            }

        } else
        if(type == 2 || type == 4)
        {
            int i = 0;
label0:
            do
            {
label1:
                {
                    if(i >= oldsteplist.size())
                        break label0;
                    Map stepmap = (Map)oldsteplist.get(i);
                    int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                    int tempsort = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(sortnum)).intValue();
                    if(tempsort > basesort)
                        break label1;
                    if(even == 1)
                    {
                        int evenmark = NumberTool.safeToInteger(evenmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(0)).intValue();
                        if(evenmark == 0)
                            break label1;
                    }
                    resultlist.add(stepmap);
                }
                i++;
            } while(true);
        } else
        if(type == 3)
        {
            if(nextmark == sortnum)
                even = 0;
            for(int i = 0; i < oldsteplist.size(); i++)
            {
                Map stepmap = (Map)oldsteplist.get(i);
                int stepid = NumberTool.safeToInteger(stepmap.get("stepid"), Integer.valueOf(0)).intValue();
                int tempsort = NumberTool.safeToInteger(sortmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(sortnum)).intValue();
                if(tempsort < basesort || tempsort > nextmark)
                    continue;
                if(even == 1)
                {
                    int evenmark = NumberTool.safeToInteger(evenmap.get((new StringBuilder()).append("").append(stepid).toString()), Integer.valueOf(0)).intValue();
                    if(evenmark == 0)
                        continue;
                }
                resultlist.add(stepmap);
            }

        }
        return resultlist;
    }

    public Map getProcessStep(int stepid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcessStep step = _workbase.loadProcessStep(stepid);
        IWorkflowProcess _process = _workbase.loadProcess(step.getProcessId().intValue());
        Map _baseparamap = _workbase.findAllParaMap();
        Map _stepmap = new HashMap();
        Map _stepmainmap = _workbase.getProcessStepMain(stepid);
        _stepmap.put("stepmainmap", _stepmainmap);
        List _ownerlist = _workbase.getOwnerList(0, stepid);
        _stepmap.put("ownerlist", _ownerlist);
        List _candidatelist = _workbase.getOwnerListByGoto(0, stepid, -1);
        _stepmap.put("candidatelist", _candidatelist);
        List _noticerlist = _workbase.getOwnerListByGoto(0, stepid, -2);
        _stepmap.put("noticerlist", _noticerlist);
        List _ownerlinklist = new ArrayList();
        List _ownerlinkmainlist = _workbase.getOwnerLinkList(stepid);
        for(int i = 0; i < _ownerlinkmainlist.size(); i++)
        {
            Map _ownerlinkmap = (Map)_ownerlinkmainlist.get(i);
            String _ownerstarter = StringUtil.safeToString(_ownerlinkmap.get("ownerstarter"), "");
            String _starterArr[] = _ownerstarter.split(",");
            String _ownerstartershow = "";
            for(int j = 0; j < _starterArr.length; j++)
            {
                String _starterArrArr[] = _starterArr[j].split(":");
                if(_starterArrArr.length == 3)
                    _ownerstartershow = (new StringBuilder()).append(_ownerstartershow).append(_starterArrArr[1]).append("\uFF0C").toString();
            }

            if(_ownerstartershow.length() > 0)
                _ownerlinkmap.put("ownerstartershow", _ownerstartershow.substring(0, _ownerstartershow.length() - 1));
            else
                _ownerlinkmap.put("ownerstartershow", _ownerstartershow);
            String _ownerender = StringUtil.safeToString(_ownerlinkmap.get("ownerender"), "");
            String _enderArr[] = _ownerender.split(",");
            String _ownerendershow = "";
            for(int j = 0; j < _enderArr.length; j++)
            {
                String _enderArrArr[] = _enderArr[j].split(":");
                if(_enderArrArr.length == 3)
                    _ownerendershow = (new StringBuilder()).append(_ownerendershow).append(_enderArrArr[1]).append("\uFF0C").toString();
            }

            if(_ownerendershow.length() > 0)
                _ownerlinkmap.put("ownerendershow", _ownerendershow.substring(0, _ownerendershow.length() - 1));
            else
                _ownerlinkmap.put("ownerendershow", _ownerendershow);
            _ownerlinklist.add(_ownerlinkmap);
        }

        _stepmap.put("ownerlinklist", _ownerlinklist);
        List _linklist = new ArrayList();
        List _linkmainlist = _workbase.getStepLinkList(stepid);
        for(int i = 0; i < _linkmainlist.size(); i++)
        {
            Map _linkmap = (Map)_linkmainlist.get(i);
            int _linkid = NumberTool.convertMapKeyToInt(_linkmap, "objid", Integer.valueOf(0)).intValue();
            List _linkiflist = new ArrayList();
            String _linkifs = "";
            List _linkifmainlist = _workbase.getStepLinkIfList(_linkid);
            for(int j = 0; j < _linkifmainlist.size(); j++)
            {
                Map _linkifmap = (Map)_linkifmainlist.get(j);
                int _iftype = NumberTool.convertMapKeyToInt(_linkifmap, "iftype", Integer.valueOf(0)).intValue();
                int _ifand = NumberTool.convertMapKeyToInt(_linkifmap, "ifand", Integer.valueOf(0)).intValue();
                String _ifads = StringUtil.safeToString(_linkifmap.get("ifads"), "");
                if(j > 0)
                    _linkifs = (new StringBuilder()).append(_linkifs).append(",").toString();
                _linkifs = (new StringBuilder()).append(_linkifs).append(_iftype).append(" ").append(_ifand).append(" ").append(_ifads).toString();
                String _iftypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("LinkIfType").append(_iftype).toString()), "");
                if(_iftype >= 0x186a0)
                    _iftypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append(_process.getProcessType()).append("LinkIfType").append(_iftype).toString()), "");
                String _ifandtext = "";
                String _ifadstext = "";
                if(_iftype == 2)
                {
                    _ifandtext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("MathMark").append(_ifand).toString()), "");
                    _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("JudgeResult").append(_ifads).toString()), "");
                } else
                if(_iftype == 1)
                {
                    _ifandtext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("SpecialMark").append(_ifand).toString()), "");
                    if(_process.getProcessType() != null)
                        _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append(_process.getProcessType()).append("SpecialResult").append(_ifads).toString()), "");
                } else
                if(_iftype >= 0x186a0)
                {
                    _ifandtext = "";
                    _ifadstext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("LinkIfResult").append(_ifads).toString()), "");
                }
                String _ifshowtext = (new StringBuilder()).append(_iftypetext).append(" ").append(_ifandtext).append(" ").append(_ifadstext).toString();
                _linkifmap.put("ifshow", _ifshowtext);
                _linkiflist.add(_linkifmap);
            }

            _linkmap.put("linkifs", _linkifs);
            _linkmap.put("linkiflist", _linkiflist);
            List _linkdolist = new ArrayList();
            String _linkdos = "";
            List _linkdomainlist = _workbase.getStepLinkDoList(_linkid);
            for(int j = 0; j < _linkdomainlist.size(); j++)
            {
                Map _linkdomap = (Map)_linkdomainlist.get(j);
                int _dotype = NumberTool.convertMapKeyToInt(_linkdomap, "dotype", Integer.valueOf(0)).intValue();
                if(j > 0)
                    _linkdos = (new StringBuilder()).append(_linkdos).append(",").toString();
                _linkdos = (new StringBuilder()).append(_linkdos).append(_dotype).toString();
                String _dotypetext = "";
                if(_dotype == 0)
                    _dotypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append("SpecialTask").append(_dotype).toString()), "");
                else
                    _dotypetext = StringUtil.safeToString(_baseparamap.get((new StringBuilder()).append(_process.getProcessType()).append("SpecialTask").append(_dotype).toString()), "");
                _linkdomap.put("doshow", _dotypetext);
                _linkdolist.add(_linkdomap);
            }

            _linkmap.put("linkdos", _linkdos);
            _linkmap.put("linkdolist", _linkdolist);
            _linklist.add(_linkmap);
        }

        _stepmap.put("linklist", _linklist);
        return _stepmap;
    }

    public int deleteProcessStep(int stepid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List _ownerlinklist = _workbase.getOwnerLinkList(stepid);
        for(int i = 0; i < _ownerlinklist.size(); i++)
        {
            Map _ownerlinkmap = (Map)_ownerlinklist.get(i);
            int _ownerlinkid = NumberTool.convertMapKeyToInt(_ownerlinkmap, "objid", Integer.valueOf(0)).intValue();
            if(_ownerlinkid > 0)
            {
                IWorkflowProcessOwnerLink _ownerlink = _workbase.loadProcessOwnerLink(_ownerlinkid);
                _ownerlink.setStatus(Integer.valueOf(100));
                _workbase.saveProcessOwnerLink(_ownerlink);
            }
        }

        List _linklist = _workbase.getStepLinkList(stepid);
        for(int i = 0; i < _linklist.size(); i++)
        {
            Map _linkmap = (Map)_linklist.get(i);
            int _linkid = NumberTool.convertMapKeyToInt(_linkmap, "objid", Integer.valueOf(0)).intValue();
            if(_linkid <= 0)
                continue;
            IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(_linkid);
            _steplink.setStatus(Integer.valueOf(100));
            _workbase.saveProcessStepLink(_steplink);
            List _linkiflist = _workbase.getStepLinkIfList(_linkid);
            Iterator i$ = _linkiflist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _linkifmap = (Map)i$.next();
                int _linkifid = NumberTool.convertMapKeyToInt(_linkifmap, "objid", Integer.valueOf(0)).intValue();
                if(_linkifid > 0)
                {
                    IWorkflowProcessStepLinkIf _linkif = _workbase.loadProcessStepLinkIf(_linkifid);
                    _linkif.setStatus(Integer.valueOf(100));
                    _workbase.saveProcessStepLinkIf(_linkif);
                }
            } while(true);
            List _linkdolist = _workbase.getStepLinkDoList(_linkid);
            IWorkflowProcessStepLinkDo _linkdo;
            for(Iterator i$ = _linkdolist.iterator(); i$.hasNext(); _workbase.saveProcessStepLinkDo(_linkdo))
            {
                Map _linkdomap = (Map)i$.next();
                int _linkdoid = NumberTool.convertMapKeyToInt(_linkdomap, "objid", Integer.valueOf(0)).intValue();
                _linkdo = _workbase.loadProcessStepLinkDo(_linkdoid);
                _linkdo.setStatus(Integer.valueOf(100));
            }

        }

        List _ownerlist = _workbase.getOwnerListByStepid(stepid);
        for(int i = 0; i < _ownerlist.size(); i++)
        {
            Map _ownermap = (Map)_ownerlist.get(i);
            int _ownerid = NumberTool.convertMapKeyToInt(_ownermap, "objid", Integer.valueOf(0)).intValue();
            if(_ownerid > 0)
            {
                IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(_ownerid);
                _stepowner.setStatus(Integer.valueOf(100));
                _workbase.saveProcessOwner(_stepowner);
            }
        }

        return 0;
    }

    public int copyProcess(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        IWorkflowProcess _process2 = (IWorkflowProcess)_process.clone();
        int _sysversion = NumberTool.safeToInteger(_process.getSysVersion(), Integer.valueOf(0)).intValue();
        _process2.setSysVersion(Integer.valueOf(_sysversion + 1));
        _process2.setStatus(Integer.valueOf(1));
        int _processid2 = _workbase.saveProcess(_process2);
        Map _steptostepmap = new HashMap();
        List _steplist = _workbase.getProcessStepList(processid);
        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            IWorkflowProcessStep _step = _workbase.loadProcessStep(_stepid);
            IWorkflowProcessStep _step2 = (IWorkflowProcessStep)_step.clone();
            _step2.setProcessId(Integer.valueOf(_processid2));
            int _stepid2 = _workbase.saveProcessStep(_step2);
            _steptostepmap.put(StringUtil.safeToString(Integer.valueOf(_stepid), ""), Integer.valueOf(_stepid2));
            IWorkflowProcessStepHistory _stephistory = _workbase.loadProcessStepHistory(0);
            _stephistory.setPreProcessId(_step.getProcessId());
            _stephistory.setPreStepId(Integer.valueOf(_step.getId()));
            _stephistory.setNewProcessId(_step2.getProcessId());
            _stephistory.setNewStepId(Integer.valueOf(_step2.getId()));
            _workbase.saveProcessStepHistory(_stephistory);
        }

        for(int i = 0; i < _steplist.size(); i++)
        {
            Map _stepmap = (Map)_steplist.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            int _stepid2 = NumberTool.convertMapKeyToInt(_steptostepmap, StringUtil.safeToString(Integer.valueOf(_stepid), ""), Integer.valueOf(0)).intValue();
            List _linklist = _workbase.getStepLinkList(_stepid);
            for(int j = 0; j < _linklist.size(); j++)
            {
                Map _linkmap = (Map)_linklist.get(j);
                int _linkid = NumberTool.convertMapKeyToInt(_linkmap, "objid", Integer.valueOf(0)).intValue();
                IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(_linkid);
                IWorkflowProcessStepLink _steplink2 = (IWorkflowProcessStepLink)_steplink.clone();
                _steplink2.setProcessId(Integer.valueOf(_processid2));
                _steplink2.setPreStepId(Integer.valueOf(_stepid2));
                int _aftstepid = NumberTool.convertMapKeyToInt(_steptostepmap, StringUtil.safeToString(_steplink.getAftStepId(), ""), Integer.valueOf(0)).intValue();
                _steplink2.setAftStepId(Integer.valueOf(_aftstepid));
                int _linkid2 = _workbase.saveProcessStepLink(_steplink2);
                List _linkiflist = _workbase.getStepLinkIfList(_linkid);
                for(int k = 0; k < _linkiflist.size(); k++)
                {
                    Map _linkifmap = (Map)_linkiflist.get(k);
                    int _linkifid = NumberTool.convertMapKeyToInt(_linkifmap, "objid", Integer.valueOf(0)).intValue();
                    IWorkflowProcessStepLinkIf _linkif = _workbase.loadProcessStepLinkIf(_linkifid);
                    IWorkflowProcessStepLinkIf _linkif2 = (IWorkflowProcessStepLinkIf)_linkif.clone();
                    _linkif2.setProcessId(Integer.valueOf(_processid2));
                    _linkif2.setStepId(Integer.valueOf(_stepid2));
                    _linkif2.setLinkId(Integer.valueOf(_linkid2));
                    _workbase.saveProcessStepLinkIf(_linkif2);
                }

                List _linkdolist = _workbase.getStepLinkDoList(_linkid);
                for(int k = 0; k < _linkdolist.size(); k++)
                {
                    Map _linkdomap = (Map)_linkdolist.get(k);
                    int _linkdoid = NumberTool.convertMapKeyToInt(_linkdomap, "objid", Integer.valueOf(0)).intValue();
                    IWorkflowProcessStepLinkDo _linkdo = _workbase.loadProcessStepLinkDo(_linkdoid);
                    IWorkflowProcessStepLinkDo _linkdo2 = (IWorkflowProcessStepLinkDo)_linkdo.clone();
                    _linkdo2.setProcessId(Integer.valueOf(_processid2));
                    _linkdo2.setStepId(Integer.valueOf(_stepid2));
                    _linkdo2.setLinkId(Integer.valueOf(_linkid2));
                    _workbase.saveProcessStepLinkDo(_linkdo2);
                }

            }

            List _ownerlist = _workbase.getOwnerListByStepid(_stepid);
            for(int j = 0; j < _ownerlist.size(); j++)
            {
                Map _ownermap = (Map)_ownerlist.get(j);
                int _ownerid = NumberTool.convertMapKeyToInt(_ownermap, "objid", Integer.valueOf(0)).intValue();
                IWorkflowProcessOwner _owner = _workbase.loadProcessOwner(_ownerid);
                IWorkflowProcessOwner _owner2 = (IWorkflowProcessOwner)_owner.clone();
                _owner2.setProcessId(Integer.valueOf(_processid2));
                _owner2.setStepId(Integer.valueOf(_stepid2));
                _owner2.setOwnerGotoId(Integer.valueOf(_stepid2));
                _workbase.saveProcessOwner(_owner2);
            }

            List _ownerlinklist = _workbase.getOwnerLinkList(_stepid);
            for(int j = 0; j < _ownerlinklist.size(); j++)
            {
                Map _ownerlinkmap = (Map)_ownerlinklist.get(j);
                int _ownerlinkid = NumberTool.convertMapKeyToInt(_ownerlinkmap, "objid", Integer.valueOf(0)).intValue();
                IWorkflowProcessOwnerLink _ownerlink = _workbase.loadProcessOwnerLink(_ownerlinkid);
                IWorkflowProcessOwnerLink _ownerlink2 = (IWorkflowProcessOwnerLink)_ownerlink.clone();
                _ownerlink2.setProcessId(Integer.valueOf(_processid2));
                _ownerlink2.setStepId(Integer.valueOf(_stepid2));
                _workbase.saveProcessOwnerLink(_ownerlink2);
            }

        }

        return _processid2;
    }

    public int copyProcess_new(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        IWorkflowProcess _process2 = (IWorkflowProcess)_process.clone();
        int _sysversion = NumberTool.safeToInteger(_process.getSysVersion(), Integer.valueOf(0)).intValue();
        _process2.setSysVersion(Integer.valueOf(_sysversion + 1));
        _process2.setStatus(Integer.valueOf(1));
        int _processid2 = _workbase.saveProcess(_process2);
        Map _parammap = new HashMap();
        if(_workdraw.checkIsDrawFlow(processid).booleanValue())
        {
            if(processid > 0 && _processid2 > 0)
            {
                List _propslist = _workdraw.findFlowProps(_processid2, _process2.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_processid2));
                }

            }
            List _nodelist = _workdraw.findFlowNode(processid);
            for(int i = 0; i < _nodelist.size(); i++)
            {
                IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
                IWorkflowFlowNode _newnode = (IWorkflowFlowNode)_node.clone();
                _newnode.setProcessid(Integer.valueOf(_processid2));
                int _newnodeid = _workdraw.saveFlowNode(_newnode);
                _parammap.put((new StringBuilder()).append("node").append(_node.getId()).toString(), Integer.valueOf(_newnodeid));
                List _propslist = _workdraw.findFlowProps(_node.getId(), _node.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_newnodeid));
                }

            }

            List _pathlist = _workdraw.findFlowPaths(processid);
            for(Iterator i$ = _pathlist.iterator(); i$.hasNext();)
            {
                IWorkflowFlowPaths _paths = (IWorkflowFlowPaths)i$.next();
                IWorkflowFlowPaths _newpath = (IWorkflowFlowPaths)_paths.clone();
                int _fromid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append("node").append(_paths.getFromid()).toString(), 0);
                int _toid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append("node").append(_paths.getToid()).toString(), 0);
                _newpath.setProcessid(Integer.valueOf(_processid2));
                _newpath.setFromid(Integer.valueOf(_fromid));
                _newpath.setToid(Integer.valueOf(_toid));
                int _newpathid = _workdraw.saveFlowPaths(_newpath);
                List _propslist = _workdraw.findFlowProps(_paths.getId(), _paths.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_newpathid));
                }

                List _dotslist = _workdraw.findFlowDots(_paths.getId());
                Iterator i$ = _dotslist.iterator();
                while(i$.hasNext()) 
                {
                    IWorkflowFlowDots _dot = (IWorkflowFlowDots)i$.next();
                    IWorkflowFlowDots _newdot = (IWorkflowFlowDots)_dot.clone();
                    _newdot.setPathid(Integer.valueOf(_newpathid));
                    _workdraw.saveFlowDots(_newdot);
                }
            }

        } else
        {
            Map _flowdata = changeProcess(processid);
            try
            {
                _workdraw.saveFlowData(_processid2, _flowdata);
            }
            catch(JSONException e)
            {
                LOGGER.error("\u65E7\u6D41\u7A0B\u66F4\u65B0\u51FA\u73B0\u95EE\u9898", e);
            }
        }
        return _processid2;
    }

    public int cloneProcess(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = copyProcess(processid);
        IWorkflowProcess _process = _workbase.loadProcess(_processid);
        _process.setProcessCode("");
        _process.setSysCode(StringUtil.safeToString(Integer.valueOf(_processid), ""));
        _process.setSysVersion(Integer.valueOf(1));
        return _workbase.saveProcess(_process);
    }

    public int freshProcess(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _processid = copyProcess(processid);
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        if(_process.getStatus().intValue() == 2)
        {
            _process.setStatus(Integer.valueOf(3));
            _workbase.saveProcess(_process);
        }
        return _processid;
    }

    public int freshProcess_new(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int _newprocessid = copyProcess_new(processid);
        IWorkflowProcess _oldprocess = _workbase.loadProcess(processid);
        if(_oldprocess.getStatus().intValue() == 2)
        {
            _oldprocess.setStatus(Integer.valueOf(3));
            _workbase.saveProcess(_oldprocess);
        }
        return _newprocessid;
    }

    public int recoverProcess(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        String syscode = StringUtil.safeToString(_process.getSysCode(), "");
        Map nowprocessmap = _workbase.getProcessBySyscode(syscode);
        int _processid = NumberTool.convertMapKeyToInt(nowprocessmap, "processid", Integer.valueOf(0)).intValue();
        int _sysversion = 0;
        List _usedlist = _workbase.getProcessListBySyscode(syscode);
        if(_usedlist.size() > 0)
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
            _sysversion = NumberTool.safeToInteger(_used.getSysVersion(), Integer.valueOf(0)).intValue();
        }
        Map stephistorymap = _workbase.getStepHistoryMap(syscode);
        IWorkflowProcess _process2 = (IWorkflowProcess)_process.clone();
        _process2.setSysVersion(Integer.valueOf(_sysversion + 1));
        _process2.setStatus(Integer.valueOf(1));
        int _processid2 = _workbase.saveProcess(_process2);
        Map _parammap = new HashMap();
        if(_workdraw.checkIsDrawFlow(processid).booleanValue())
        {
            if(processid > 0 && _processid2 > 0)
            {
                List _propslist = _workdraw.findFlowProps(_processid2, _process2.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_processid2));
                }

            }
            List _nodelist = _workdraw.findFlowNode(processid);
            for(int i = 0; i < _nodelist.size(); i++)
            {
                IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
                IWorkflowFlowNode _newnode = (IWorkflowFlowNode)_node.clone();
                _newnode.setProcessid(Integer.valueOf(_processid2));
                int _newnodeid = _workdraw.saveFlowNode(_newnode);
                _parammap.put((new StringBuilder()).append("node").append(_node.getId()).toString(), Integer.valueOf(_newnodeid));
                List _propslist = _workdraw.findFlowProps(_node.getId(), _node.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_newnodeid));
                }

            }

            List _pathlist = _workdraw.findFlowPaths(processid);
            for(Iterator i$ = _pathlist.iterator(); i$.hasNext();)
            {
                IWorkflowFlowPaths _paths = (IWorkflowFlowPaths)i$.next();
                IWorkflowFlowPaths _newpath = (IWorkflowFlowPaths)_paths.clone();
                int _fromid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append("node").append(_paths.getFromid()).toString(), 0);
                int _toid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append("node").append(_paths.getToid()).toString(), 0);
                _newpath.setProcessid(Integer.valueOf(_processid2));
                _newpath.setFromid(Integer.valueOf(_fromid));
                _newpath.setToid(Integer.valueOf(_toid));
                int _newpathid = _workdraw.saveFlowPaths(_newpath);
                List _propslist = _workdraw.findFlowProps(_paths.getId(), _paths.getEntityName());
                IWorkflowFlowProps _newprop;
                for(Iterator i$ = _propslist.iterator(); i$.hasNext(); _workdraw.saveFlowProps(_newprop))
                {
                    IWorkflowFlowProps _prop = (IWorkflowFlowProps)i$.next();
                    _newprop = (IWorkflowFlowProps)_prop.clone();
                    _newprop.setSourceid(Integer.valueOf(_newpathid));
                }

                List _dotslist = _workdraw.findFlowDots(_paths.getId());
                Iterator i$ = _dotslist.iterator();
                while(i$.hasNext()) 
                {
                    IWorkflowFlowDots _dot = (IWorkflowFlowDots)i$.next();
                    IWorkflowFlowDots _newdot = (IWorkflowFlowDots)_dot.clone();
                    _newdot.setPathid(Integer.valueOf(_newpathid));
                    _workdraw.saveFlowDots(_newdot);
                }
            }

        } else
        {
            Map _flowdata = changeProcess(processid);
            try
            {
                _workdraw.saveFlowData(_processid2, _flowdata);
            }
            catch(JSONException e)
            {
                LOGGER.error("\u65E7\u6D41\u7A0B\u66F4\u65B0\u51FA\u73B0\u95EE\u9898", e);
            }
        }
        return _processid2;
    }

    private int searchOldstepToNew(Map stepmap, int oldstep)
    {
        int step1 = oldstep;
        int step2 = 0;
        for(; step1 != 0; step1 = NumberTool.convertMapKeyToInt(stepmap, (new StringBuilder()).append("").append(step2).toString(), Integer.valueOf(0)).intValue())
            step2 = step1;

        return step2;
    }

    public List getAgentsList(Map map)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List _list = _workbase.getAgentsList(map);
        return changeAgentsListToShow(_list);
    }

    public List getAllAgentsList(Map map)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        String _orgid = StringUtil.safeToString(map.get("orgid"), "");
        int _statustype = NumberTool.convertMapKeyToInt(map, "statustype", Integer.valueOf(0)).intValue();
        List _list = _workbase.getAgentsList(_orgid, _statustype);
        return changeAgentsListToShow(_list);
    }

    private List changeAgentsListToShow(List list)
    {
        List _list2 = new ArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            Map _map = (Map)list.get(i);
            String _starttime = StringUtil.safeToString(_map.get("starttime"), "");
            String _endtime = StringUtil.safeToString(_map.get("endtime"), "");
            String _starttime2 = "";
            if(_starttime.length() >= 10)
                _starttime2 = _starttime.substring(0, 10);
            String _endtime2 = "";
            if(_endtime.length() >= 10)
                _endtime2 = _endtime.substring(0, 10);
            _map.put("starttime2", _starttime2);
            _map.put("endtime2", _endtime2);
            int processtype = NumberTool.convertMapKeyToInt(_map, "processtype", Integer.valueOf(0)).intValue();
            if(processtype == 0)
                _map.put("processtypevalue", "\u6240\u6709\u6D41\u7A0B\u7C7B\u578B");
            int processid = NumberTool.convertMapKeyToInt(_map, "processid", Integer.valueOf(0)).intValue();
            if(processid == 0)
                _map.put("processname", "\u6240\u6709\u6B64\u7C7B\u6D41\u7A0B");
            int processstepid = NumberTool.convertMapKeyToInt(_map, "processstepid", Integer.valueOf(0)).intValue();
            if(processstepid == 0)
                _map.put("stepname", "\u6240\u6709\u6D41\u7A0B\u6B65\u9AA4");
            String agentsname = StringUtil.safeToString(_map.get("agentsname"), "");
            if(agentsname.length() == 0)
            {
                int agentsid = NumberTool.safeToInteger(_map.get("agentsid"), Integer.valueOf(0)).intValue();
                _map.put("agentsname", (new StringBuilder()).append("ID:").append(agentsid).toString());
            }
            String ownername = StringUtil.safeToString(_map.get("ownername"), "");
            if(ownername.length() == 0)
            {
                int ownerid = NumberTool.safeToInteger(_map.get("ownerid"), Integer.valueOf(0)).intValue();
                _map.put("ownername", (new StringBuilder()).append("ID:").append(ownerid).toString());
            }
            _list2.add(_map);
        }

        return _list2;
    }

    public int freshAgents(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        int num = 0;
        List _historylist = _workbase.getStepHistoryList(processid, 3);
        if(_historylist.size() == 0)
            return num;
        List _steplist = _workbase.getAgentStepList(processid);
        int _preprocessid = NumberTool.convertMapKeyToInt((Map)_historylist.get(0), "preprocessid", Integer.valueOf(0)).intValue();
        Map _stepmap = new HashMap();
        for(int i = 0; i < _historylist.size(); i++)
        {
            Map _historymap = (Map)_historylist.get(i);
            int _newstepid = NumberTool.convertMapKeyToInt(_historymap, "newstepid", Integer.valueOf(0)).intValue();
            int _prestepid = NumberTool.convertMapKeyToInt(_historymap, "prestepid", Integer.valueOf(0)).intValue();
            int j = 0;
            do
            {
                if(j >= _steplist.size())
                    break;
                Map _stepmap2 = (Map)_steplist.get(j);
                int _stepid2 = NumberTool.convertMapKeyToInt(_stepmap2, "stepid", Integer.valueOf(0)).intValue();
                if(_stepid2 == _newstepid)
                    break;
                j++;
            } while(true);
            if(j < _steplist.size())
                _stepmap.put(StringUtil.safeToString(Integer.valueOf(_prestepid), ""), Integer.valueOf(_newstepid));
        }

        Map _amap = new HashMap();
        IWorkflowProcess _preprocess = _workbase.loadProcess(_preprocessid);
        _amap.put("typeid", _preprocess.getSysCode());
        _amap.put("statustype", Integer.valueOf(-1));
        List _agentslist = _workbase.getAgentsList(_amap);
        for(int i = 0; i < _agentslist.size(); i++)
        {
            int _agentsid = NumberTool.convertMapKeyToInt((Map)_agentslist.get(i), "objid", Integer.valueOf(0)).intValue();
            if(_agentsid <= 0)
                continue;
            IWorkflowAgents _agents = _workbase.loadAgents(_agentsid);
            int _agentspreid = NumberTool.safeToInteger(_agents.getProcessStepId(), Integer.valueOf(0)).intValue();
            int _agentsnowid = NumberTool.convertMapKeyToInt(_stepmap, StringUtil.safeToString(Integer.valueOf(_agentspreid), ""), Integer.valueOf(0)).intValue();
            if(_agentsnowid != 0)
            {
                IWorkflowAgents _nowagents = (IWorkflowAgents)_agents.clone();
                _nowagents.setProcessStepId(Integer.valueOf(_agentsnowid));
                _nowagents.setProcessId(Integer.valueOf(processid));
                _workbase.saveAgents(_nowagents);
                num++;
            }
            _agents.setStatus(Integer.valueOf(_agents.getStatus().intValue() + 2));
            _workbase.saveAgents(_agents);
        }

        return num;
    }

    public int releaseProcess_new(int processid, Map returnmap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        if(_process == null || _process.getId() == 0)
            return -1;
        if(_process.getStatus().intValue() == 2)
            return releaseProcess(processid, returnmap);
        Map _map2 = detectProcess_new(processid);
        String _error = StringUtil.safeToString(_map2.get("error"), "");
        String _warn = StringUtil.safeToString(_map2.get("warn"), "");
        returnmap.put("error", _error);
        returnmap.put("warn", _warn);
        if(_error.length() > 0)
        {
            return -1;
        } else
        {
            achieveProcess(processid);
            _process.setStatus(Integer.valueOf(2));
            _workbase.saveProcess(_process);
            return 0;
        }
    }

    public int releaseProcess(int processid, Map returnmap)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        if(_process == null || _process.getId() == 0)
            return -1;
        Map _map2 = detectProcess(processid);
        String _error = StringUtil.safeToString(_map2.get("error"), "");
        String _warn = StringUtil.safeToString(_map2.get("warn"), "");
        returnmap.put("error", _error);
        returnmap.put("warn", _warn);
        if(_error.length() > 0)
            return -1;
        List _list = _workbase.getReleaseStepList(processid);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _stepid = NumberTool.convertMapKeyToInt(_map, "objid", Integer.valueOf(0)).intValue();
            String _actionurl = StringUtil.safeToString(_map.get("actionurl"), "");
            String _showurl = StringUtil.safeToString(_map.get("showurl"), "");
            IWorkflowProcessStep _step = _workbase.loadProcessStep(_stepid);
            _step.setActionUrl(_actionurl);
            _step.setShowUrl(_showurl);
            _workbase.saveProcessStep(_step);
        }

        _process.setStatus(Integer.valueOf(2));
        _workbase.saveProcess(_process);
        return 0;
    }

    public Map detectProcess(int processid)
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _returnmap = new HashMap();
        StringBuffer _errorbuffer = new StringBuffer();
        StringBuffer _warnbuffer = new StringBuffer();
        List _list = getProcessStepList(processid);
        if(_list.size() == 0)
            _errorbuffer.append("\u9519\u8BEF\uFF1A\u8BE5\u6D41\u7A0B\u4E3A\u7A7A<Br>");
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _steptypeid = NumberTool.convertMapKeyToInt(_map, "steptypeid", Integer.valueOf(0)).intValue();
            String _name = StringUtil.safeToString(_map.get("name"), "");
            List _linklist = ((List) (_map.get("linklist") != null ? (List)_map.get("linklist") : ((List) (new ArrayList()))));
            List _ownerlist = ((List) (_map.get("ownerlist") != null ? (List)_map.get("ownerlist") : ((List) (new ArrayList()))));
            if(_steptypeid != 2 && _linklist.size() == 0)
                _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u8BBE\u7F6E\u540E\u7EE7\u70B9<Br>");
            int mark = 0;
            int j = 0;
            do
            {
                if(j >= _ownerlist.size())
                    break;
                Map _ownermap = (Map)_ownerlist.get(j);
                int _orgtype = NumberTool.convertMapKeyToInt(_ownermap, "orgtype", Integer.valueOf(0)).intValue();
                if(_orgtype >= 0)
                {
                    mark = 1;
                    break;
                }
                j++;
            } while(true);
            if(_steptypeid == WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
            {
                String _steptypeads = StringUtil.safeToString(_map.get("steptypeads"), "");
                String _steptypeArr[] = _steptypeads.split(" ");
                if(_steptypeArr.length > 0)
                {
                    if(mark == 0)
                        _warnbuffer.append("\u8B66\u544A\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6E\u9ED8\u8BA4\u5904\u7406\u4EBA<Br>");
                } else
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u5904\u7406\u4EBA\u6570\u636E\u5F02\u5E38<Br>");
                }
            } else
            if(_steptypeid == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
            {
                String _steptypeads = StringUtil.safeToString(_map.get("steptypeads"), "");
                String _steptypeArr[] = _steptypeads.split(" ");
                if(_steptypeArr.length > 0)
                {
                    int acrosstype = NumberTool.safeToInteger(_steptypeArr[0], Integer.valueOf(0)).intValue();
                    if(mark == 0 && acrosstype == 1)
                        _warnbuffer.append("\u8B66\u544A\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6E\u9ED8\u8BA4\u5904\u7406\u4EBA<Br>");
                } else
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u5904\u7406\u4EBA\u6570\u636E\u5F02\u5E38<Br>");
                }
            }
            if(_steptypeid != WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue() && _steptypeid != WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
                continue;
            int _urlmark = NumberTool.convertMapKeyToInt(_map, "urlmark", Integer.valueOf(0)).intValue();
            if(_urlmark == 0)
            {
                _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6EURL<Br>");
                continue;
            }
            IWorkflowProcessUrl _purl = _workbase.loadProcessUrl(_urlmark);
            if(_purl == null)
            {
                _errorbuffer.append("\u9519\u8BEF\uFF1AURL(").append(_urlmark).append(")\u5DF2\u4E0D\u5B58\u5728<Br>");
                continue;
            }
            String _actionurl = StringUtil.safeToString(_purl.getActionUrl(), "");
            if(_actionurl.length() == 0)
                _errorbuffer.append("\u9519\u8BEF\uFF1A").append(StringUtil.safeToString(_purl.getName(), "")).append("\u6CA1\u6709\u914D\u7F6E\u5904\u7406url<Br>");
        }

        _returnmap.put("error", _errorbuffer.toString());
        _returnmap.put("warn", _warnbuffer.toString());
        return _returnmap;
    }

    public Map detectProcess_new(int processid)
    {
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _returnmap = new HashMap();
        StringBuffer _errorbuffer = new StringBuffer();
        StringBuffer _warnbuffer = new StringBuffer();
        int _hasstart = 0;
        int _hasend = 0;
        List _nodelist = _workdraw.findFlowNode(processid);
        for(int i = 0; i < _nodelist.size(); i++)
        {
            IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
            Map _props = _workdraw.getFlowPropsMap(_node.getId(), _node.getEntityName());
            List _pathlist;
            if("start".equals(_node.getType()))
            {
                _hasstart++;
                _pathlist = _workdraw.findFlowPaths(processid, _node.getId(), 0);
                if(_pathlist.size() == 0)
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(MapUtils.getString((Map)_props.get("stepname"), "value", "")).append("\u6CA1\u6709\u8BBE\u7F6E\u540E\u7EE7\u70B9<Br>");
                continue;
            }
            if("end".equals(_node.getType()))
            {
                _hasend++;
                continue;
            }
            if("submit".equals(_node.getType()))
            {
                String _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
                List _pathlist = _workdraw.findFlowPaths(processid, _node.getId(), 0);
                if(_pathlist.size() == 0)
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u8BBE\u7F6E\u540E\u7EE7\u70B9<Br>");
                int mark = 0;
                if(StringUtils.isNotBlank(MapUtils.getString((Map)_props.get("conditionowner"), "value", "")))
                    mark = 1;
                int acrosstype = MapUtils.getIntValue((Map)_props.get("submitowner"), "value", 0);
                if(mark == 0 && acrosstype == 1)
                    _warnbuffer.append("\u8B66\u544A\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6E\u9ED8\u8BA4\u5904\u7406\u4EBA<Br>");
                int _urlmark = MapUtils.getIntValue((Map)_props.get("processurl"), "value", 0);
                if(_urlmark == 0)
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6EURL<Br>");
                    continue;
                }
                IWorkflowProcessUrl _purl = _workbase.loadProcessUrl(_urlmark);
                if(_purl == null)
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1AURL(").append(_urlmark).append(")\u5DF2\u4E0D\u5B58\u5728<Br>");
                    continue;
                }
                String _actionurl = StringUtil.safeToString(_purl.getActionUrl(), "");
                if(_actionurl.length() == 0)
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(StringUtil.safeToString(_purl.getName(), "")).append("\u6CA1\u6709\u914D\u7F6E\u5904\u7406url<Br>");
                continue;
            }
            if("judge".equals(_node.getType()))
            {
                String _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
                List _pathlist = _workdraw.findFlowPaths(processid, _node.getId(), 0);
                if(_pathlist.size() == 0)
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u8BBE\u7F6E\u540E\u7EE7\u70B9<Br>");
                int mark = 0;
                if(StringUtils.isNotBlank(MapUtils.getString((Map)_props.get("conditionowner"), "value", "")))
                    mark = 1;
                String _steptypeads = MapUtils.getString((Map)_props.get("judgetype"), "value", "");
                String _steptypeArr[] = _steptypeads.split(":");
                if(_steptypeArr.length > 0)
                {
                    if(mark == 0)
                        _warnbuffer.append("\u8B66\u544A\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6E\u9ED8\u8BA4\u5904\u7406\u4EBA<Br>");
                } else
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u5904\u7406\u4EBA\u6570\u636E\u5F02\u5E38<Br>");
                }
                int _urlmark = MapUtils.getIntValue((Map)_props.get("processurl"), "value", 0);
                if(_urlmark == 0)
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_name).append("\u6CA1\u6709\u914D\u7F6EURL<Br>");
                    continue;
                }
                IWorkflowProcessUrl _purl = _workbase.loadProcessUrl(_urlmark);
                if(_purl == null)
                {
                    _errorbuffer.append("\u9519\u8BEF\uFF1AURL(").append(_urlmark).append(")\u5DF2\u4E0D\u5B58\u5728<Br>");
                    continue;
                }
                String _actionurl = StringUtil.safeToString(_purl.getActionUrl(), "");
                if(_actionurl.length() == 0)
                    _errorbuffer.append("\u9519\u8BEF\uFF1A").append(StringUtil.safeToString(_purl.getName(), "")).append("\u6CA1\u6709\u914D\u7F6E\u5904\u7406url<Br>");
                continue;
            }
            if(!"state".equals(_node.getType()))
                continue;
            _name = _workdraw.findFlowPaths(processid, _node.getId(), 0);
            int flag = 0;
            Iterator i$ = _name.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IWorkflowFlowPaths _path = (IWorkflowFlowPaths)i$.next();
                flag++;
                int _toid = _path.getToid().intValue();
                IWorkflowFlowNode _targer = _workdraw.getFlowNode(_toid);
                if("submit".equals(_targer.getType()) || "judge".equals(_targer.getType()) || "end".equals(_targer.getType()))
                    continue;
                flag = -1;
                break;
            } while(true);
            if(flag == 0)
            {
                _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_node.getText()).append("\u6CA1\u6709\u8BBE\u7F6E\u540E\u7EE7\u70B9<Br>");
                continue;
            }
            if(flag == -1)
                _errorbuffer.append("\u9519\u8BEF\uFF1A").append(_node.getText()).append("\u540E\u7EE7\u70B9\u9519\u8BEF<Br>");
        }

        if(_hasstart == 0)
            _errorbuffer.append("\u9519\u8BEF\uFF1A").append("\u6CA1\u6709\u8BBE\u7F6E\u5F00\u59CB\u8282\u70B9<Br>");
        else
        if(_hasstart > 1)
            _errorbuffer.append("\u9519\u8BEF\uFF1A").append("\u8BBE\u7F6E\u4E86\u591A\u4E2A\u5F00\u59CB\u8282\u70B9<Br>");
        if(_hasend == 0)
            _errorbuffer.append("\u9519\u8BEF\uFF1A").append("\u6CA1\u6709\u8BBE\u7F6E\u7ED3\u675F\u8282\u70B9<Br>");
        else
        if(_hasend > 1)
            _errorbuffer.append("\u9519\u8BEF\uFF1A").append("\u8BBE\u7F6E\u4E86\u591A\u4E2A\u7ED3\u675F\u8282\u70B9<Br>");
        _returnmap.put("error", _errorbuffer.toString());
        _returnmap.put("warn", _warnbuffer.toString());
        return _returnmap;
    }

    private void saveProcessStepHistory(int preprocessid, int newprocessid, int newstepid, int stepcode, Map usedprocessmap)
    {
        if(preprocessid > 0 && stepcode > 0)
        {
            int _usedstepid = MapUtils.getIntValue(usedprocessmap, (new StringBuilder()).append("stepcode_").append(stepcode).toString(), 0);
            if(_usedstepid > 0)
            {
                IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
                IWorkflowProcessStepHistory history = _workbase.loadProcessStepHistory(0);
                history.setPreProcessId(Integer.valueOf(preprocessid));
                history.setPreStepId(Integer.valueOf(_usedstepid));
                history.setNewProcessId(Integer.valueOf(newprocessid));
                history.setNewStepId(Integer.valueOf(newstepid));
                _workbase.saveProcessStepHistory(history);
            }
        }
    }

    private void saveFlowProp(String name, String key, String value, int sourceid, String sourcename)
    {
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowFlowProps _prop = _workdraw.findFlowProps(sourceid, sourcename, name, key);
        _prop.setName(name);
        _prop.setKey(key);
        _prop.setValue(value);
        _prop.setSort(Integer.valueOf(0));
        _prop.setStatus(Integer.valueOf(0));
        _prop.setSourceid(Integer.valueOf(sourceid));
        _prop.setSourcename(sourcename);
        _workdraw.saveFlowProps(_prop);
    }

    public void achieveProcess(int processid)
    {
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _relationMap = new HashMap();
        List _steplist = _workbase.getProcessStepList(processid);
        IWorkflowProcessStep _step;
        for(Iterator i$ = _steplist.iterator(); i$.hasNext(); _workbase.saveProcessStep(_step))
        {
            Map _stepmap = (Map)i$.next();
            int _stepid = NumberTool.convertMapKeyToInt(_stepmap, "objid", Integer.valueOf(0)).intValue();
            _step = _workbase.loadProcessStep(_stepid);
            deleteProcessStep(_stepid);
            _step.setStatus(Integer.valueOf(100));
        }

        IWorkflowProcess _process = _workbase.loadProcess(processid);
        Map _paramap = new HashMap();
        Map _usedprocessmap = new HashMap();
        int _usedprocessid2 = 0;
        List _usedlist = _workbase.getProcessByName(_process.getName(), processid);
        if(_usedlist.size() > 0)
        {
            Map _usedmap = (Map)_usedlist.get(0);
            int _usedprocessid = MapUtils.getIntValue(_usedmap, "objid", 0);
            IWorkflowProcess _usedprocess = _workbase.loadProcess(_usedprocessid);
            Map _usedmap2 = _workbase.getProcessBySyscode(_usedprocess.getSysCode());
            _usedprocessid2 = MapUtils.getIntValue(_usedmap2, "processid", 0);
            List _usedsteplist = _workbase.getProcessStepList(_usedprocessid2);
            for(int i = 0; i < _usedsteplist.size(); i++)
            {
                Map _usedstepmap = (Map)_usedsteplist.get(i);
                String _usedstepname = MapUtils.getString(_usedstepmap, "name", "");
                int _usedstepid = MapUtils.getIntValue(_usedstepmap, "objid", 0);
                int _usedstepcode = MapUtils.getIntValue(_usedstepmap, "stepcode", 0);
                _usedprocessmap.put((new StringBuilder()).append("stepcode_").append(_usedstepcode).toString(), Integer.valueOf(_usedstepid));
                _paramap.put((new StringBuilder()).append("stepcode_").append(_usedstepname).toString(), Integer.valueOf(_usedstepcode));
            }

        }
        List _nodelist = _workdraw.findFlowNode(processid);
label0:
        for(int i = 0; i < _nodelist.size(); i++)
        {
            IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
            Map _props = _workdraw.getFlowPropsMap(_node.getId(), _node.getEntityName());
            String _name;
            int _pointtypeid;
            if("start".equals(_node.getType()))
            {
                _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
                _pointtypeid = MapUtils.getIntValue((Map)_props.get("pointtypeid"), "value", 0);
                int _phoneshow = MapUtils.getIntValue((Map)_props.get("phoneshow"), "value", -1);
                StringBuffer _stepcondition = new StringBuffer();
                String _messageremind = MapUtils.getString(_props, "messageremind", "");
                String _messagenotice = MapUtils.getString(_props, "messagenotice", "");
                String _noticeowner = MapUtils.getString(_props, "noticeowner", "");
                String _specialnoticer = MapUtils.getString(_props, "specialnoticer", "");
                _stepcondition.append(_messageremind).append(";").append(";").append(";");
                _stepcondition.append(_messagenotice).append(";").append(";").append(_specialnoticer).append(";");
                int _stepid = _workbase.addProcessStep(_name, processid, 1);
                IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_stepid);
                _processstep.setName(_name);
                _processstep.setStepTypeAds("");
                _processstep.setPointTypeId(Integer.valueOf(_pointtypeid));
                _processstep.setCondition(_stepcondition.toString());
                _processstep.setUrlMark(Integer.valueOf(0));
                _processstep.setAgentType(Integer.valueOf(2));
                _processstep.setPhoneshow(Integer.valueOf(_phoneshow));
                _processstep.setStatus(Integer.valueOf(1));
                int _stepcode = MapUtils.getIntValue((Map)_props.get("stepcode"), "value", 0);
                if(_stepcode > 0)
                    _processstep.setStepCode(Integer.valueOf(_stepcode));
                _stepid = _workbase.saveProcessStep(_processstep);
                if(_stepcode == 0)
                {
                    _stepcode = _processstep.getStepCode().intValue();
                    saveFlowProp("stepcode", "value", StringUtil.safeToString(Integer.valueOf(_stepcode), "0"), _node.getId(), _node.getEntityName());
                }
                _relationMap.put((new StringBuilder()).append("nodeid").append(_node.getId()).toString(), Integer.valueOf(_stepid));
                saveProcessStepHistory(_usedprocessid2, processid, _stepid, _stepcode, _usedprocessmap);
                continue;
            }
            if("end".equals(_node.getType()))
            {
                _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
                _pointtypeid = MapUtils.getIntValue((Map)_props.get("pointtypeid"), "value", 0);
                int _phoneshow = MapUtils.getIntValue((Map)_props.get("phoneshow"), "value", -1);
                int _stepid = _workbase.addProcessStep(_name, processid, 2);
                IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_stepid);
                _processstep.setName(_name);
                _processstep.setAgentType(Integer.valueOf(2));
                _processstep.setPhoneshow(Integer.valueOf(_phoneshow));
                int _stepcode = MapUtils.getIntValue((Map)_props.get("stepcode"), "value", 0);
                if(_stepcode > 0)
                    _processstep.setStepCode(Integer.valueOf(_stepcode));
                _stepid = _workbase.saveProcessStep(_processstep);
                if(_stepcode == 0)
                {
                    _stepcode = _processstep.getStepCode().intValue();
                    saveFlowProp("stepcode", "value", StringUtil.safeToString(Integer.valueOf(_stepcode), "0"), _node.getId(), _node.getEntityName());
                }
                _relationMap.put((new StringBuilder()).append("nodeid").append(_node.getId()).toString(), Integer.valueOf(_stepid));
                saveProcessStepHistory(_usedprocessid2, processid, _stepid, _stepcode, _usedprocessmap);
                String _steppredotaskids = MapUtils.getString((Map)_props.get("steppredo"), "value", "");
                if(!StringUtil.isNotBlank(_steppredotaskids))
                    continue;
                String _steppredotaskidArr[] = _steppredotaskids.split(",");
                int num = 0;
                do
                {
                    if(num >= _steppredotaskidArr.length)
                        continue label0;
                    int _steppredotaskid = NumberTool.safeToInteger(_steppredotaskidArr[num], Integer.valueOf(0)).intValue();
                    if(_steppredotaskid > 0)
                    {
                        IWorkflowProcessStepDo _steppredo = _workbase.loadProcessStepDo(0);
                        _steppredo.setProcessid(Integer.valueOf(processid));
                        _steppredo.setProcessstepid(Integer.valueOf(_stepid));
                        _steppredo.setSort(Integer.valueOf(num));
                        _steppredo.setStatus(Integer.valueOf(1));
                        _steppredo.setType(Integer.valueOf(WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue()));
                        _steppredo.setTaskid(Integer.valueOf(_steppredotaskid));
                        _workbase.saveProcessStepDo(_steppredo);
                    }
                    num++;
                } while(true);
            }
            int _processurl;
            String _actionurl;
            String _showurl;
            IWorkflowProcessUrl _url;
            String _steptypeads;
            if("submit".equals(_node.getType()))
            {
                _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
                _pointtypeid = MapUtils.getIntValue((Map)_props.get("pointtypeid"), "value", 0);
                _processurl = MapUtils.getIntValue((Map)_props.get("processurl"), "value", 0);
                _actionurl = "";
                _showurl = "";
                _url = _workbase.loadProcessUrl(_processurl);
                if(!_url.isEmpty())
                {
                    _actionurl = _url.getActionUrl();
                    _showurl = _url.getShowUrl();
                }
                _steptypeads = "";
                int _judgetype = MapUtils.getIntValue((Map)_props.get("submitowner"), "value", 0);
                _steptypeads = (new StringBuilder()).append(_steptypeads).append(_judgetype).toString();
                StringBuffer _stepcondition = new StringBuffer();
                String _messageremind = MapUtils.getString((Map)_props.get("messageremind"), "value", "");
                String _messagenotice = MapUtils.getString((Map)_props.get("messagenotice"), "value", "");
                String _noticeowner = MapUtils.getString((Map)_props.get("messagenotice"), "noticeowners", "");
                String _specialnoticer = MapUtils.getString((Map)_props.get("messagenotice"), "specialnoticer", "");
                _stepcondition.append(_messageremind).append(";").append(";").append(";");
                _stepcondition.append(_messagenotice).append(";").append(";").append(_specialnoticer).append(";");
                int _agenttype = MapUtils.getIntValue((Map)_props.get("agenttype"), "value", 2);
                int _phoneshow = MapUtils.getIntValue((Map)_props.get("phoneshow"), "value", -1);
                int _stepid = _workbase.addProcessStep(_name, processid, 7);
                IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_stepid);
                _processstep.setName(_name);
                _processstep.setStepTypeAds(_steptypeads);
                _processstep.setPointTypeId(Integer.valueOf(_pointtypeid));
                _processstep.setAgentType(Integer.valueOf(_agenttype));
                _processstep.setPhoneshow(Integer.valueOf(_phoneshow));
                _processstep.setCondition(_stepcondition.toString());
                _processstep.setUrlMark(Integer.valueOf(_processurl));
                _processstep.setActionUrl(_actionurl);
                _processstep.setShowUrl(_showurl);
                _processstep.setStatus(Integer.valueOf(1));
                int _stepcode = MapUtils.getIntValue((Map)_props.get("stepcode"), "value", 0);
                if(_stepcode > 0)
                    _processstep.setStepCode(Integer.valueOf(_stepcode));
                _stepid = _workbase.saveProcessStep(_processstep);
                if(_stepcode == 0)
                {
                    _stepcode = _processstep.getStepCode().intValue();
                    saveFlowProp("stepcode", "value", StringUtil.safeToString(Integer.valueOf(_stepcode), "0"), _node.getId(), _node.getEntityName());
                }
                _relationMap.put((new StringBuilder()).append("nodeid").append(_node.getId()).toString(), Integer.valueOf(_stepid));
                saveProcessStepHistory(_usedprocessid2, processid, _stepid, _stepcode, _usedprocessmap);
                if(_noticeowner != null && _noticeowner.length() > 0)
                {
                    String _stepownerArr[] = _noticeowner.split(",");
                    for(int j = 0; j < _stepownerArr.length; j++)
                    {
                        String _stepownerArrArr[] = _stepownerArr[j].split(":");
                        IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                        _stepowner.setProcessId(Integer.valueOf(processid));
                        _stepowner.setStepId(Integer.valueOf(_stepid));
                        _stepowner.setOwnerGoto(Integer.valueOf(-2));
                        _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                        _stepowner.setOrgId(_stepownerArrArr[0]);
                        _stepowner.setOrgType(Integer.valueOf(Integer.parseInt(_stepownerArrArr[2])));
                        _stepowner.setValue(Integer.valueOf(1));
                        _stepowner.setStatus(Integer.valueOf(1));
                        _workbase.saveProcessOwner(_stepowner);
                    }

                }
                int _stepdealerid = MapUtils.getIntValue((Map)_props.get("stepdealer"), "value", 0);
                if(_stepdealerid != 0)
                {
                    IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                    _stepowner.setProcessId(Integer.valueOf(processid));
                    _stepowner.setStepId(Integer.valueOf(_stepid));
                    _stepowner.setOwnerGoto(Integer.valueOf(0));
                    _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                    _stepowner.setOrgId(Integer.toString(_stepdealerid));
                    _stepowner.setOrgType(Integer.valueOf(-1));
                    _stepowner.setValue(Integer.valueOf(1));
                    _stepowner.setStatus(Integer.valueOf(1));
                    _workbase.saveProcessOwner(_stepowner);
                }
                String _conditionowner = MapUtils.getString((Map)_props.get("conditionowner"), "value", "");
                if(_conditionowner != null && _conditionowner.length() > 0)
                {
                    String _stepownerArr[] = _conditionowner.split(",");
                    for(int j = 0; j < _stepownerArr.length; j++)
                    {
                        String _stepownerArrArr[] = _stepownerArr[j].split(":");
                        IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                        _stepowner.setProcessId(Integer.valueOf(processid));
                        _stepowner.setStepId(Integer.valueOf(_stepid));
                        _stepowner.setOwnerGoto(Integer.valueOf(0));
                        _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                        _stepowner.setOrgId(_stepownerArrArr[0]);
                        _stepowner.setOrgType(Integer.valueOf(Integer.parseInt(_stepownerArrArr[2])));
                        _stepowner.setValue(Integer.valueOf(1));
                        _stepowner.setStatus(Integer.valueOf(1));
                        _workbase.saveProcessOwner(_stepowner);
                    }

                }
                String _steppredotaskids = MapUtils.getString((Map)_props.get("steppredo"), "value", "");
                if(StringUtil.isNotBlank(_steppredotaskids))
                {
                    String _steppredotaskidArr[] = _steppredotaskids.split(",");
                    for(int num = 0; num < _steppredotaskidArr.length; num++)
                    {
                        int _steppredotaskid = NumberTool.safeToInteger(_steppredotaskidArr[num], Integer.valueOf(0)).intValue();
                        if(_steppredotaskid > 0)
                        {
                            IWorkflowProcessStepDo _steppredo = _workbase.loadProcessStepDo(0);
                            _steppredo.setProcessid(Integer.valueOf(processid));
                            _steppredo.setProcessstepid(Integer.valueOf(_stepid));
                            _steppredo.setSort(Integer.valueOf(num));
                            _steppredo.setStatus(Integer.valueOf(1));
                            _steppredo.setType(Integer.valueOf(WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue()));
                            _steppredo.setTaskid(Integer.valueOf(_steppredotaskid));
                            _workbase.saveProcessStepDo(_steppredo);
                        }
                    }

                }
                String _stepaftdotaskids = MapUtils.getString((Map)_props.get("stepaftdo"), "value", "");
                if(!StringUtil.isNotBlank(_stepaftdotaskids))
                    continue;
                String _stepaftdotaskidArr[] = _stepaftdotaskids.split(",");
                int num = 0;
                do
                {
                    if(num >= _stepaftdotaskidArr.length)
                        continue label0;
                    int _stepaftdotaskid = NumberTool.safeToInteger(_stepaftdotaskidArr[num], Integer.valueOf(0)).intValue();
                    if(_stepaftdotaskid > 0)
                    {
                        IWorkflowProcessStepDo _steppredo = _workbase.loadProcessStepDo(0);
                        _steppredo.setProcessid(Integer.valueOf(processid));
                        _steppredo.setProcessstepid(Integer.valueOf(_stepid));
                        _steppredo.setSort(Integer.valueOf(num));
                        _steppredo.setStatus(Integer.valueOf(1));
                        _steppredo.setType(Integer.valueOf(WorkflowStepDoType.WF_DOTYPE_AFTDO.getEnumItemValue()));
                        _steppredo.setTaskid(Integer.valueOf(_stepaftdotaskid));
                        _workbase.saveProcessStepDo(_steppredo);
                    }
                    num++;
                } while(true);
            }
            if(!"judge".equals(_node.getType()))
                continue;
            _name = MapUtils.getString((Map)_props.get("stepname"), "value", "");
            _pointtypeid = MapUtils.getIntValue((Map)_props.get("pointtypeid"), "value", 0);
            _processurl = MapUtils.getIntValue((Map)_props.get("processurl"), "value", 0);
            _actionurl = "";
            _showurl = "";
            _url = _workbase.loadProcessUrl(_processurl);
            if(!_url.isEmpty())
            {
                _actionurl = _url.getActionUrl();
                _showurl = _url.getShowUrl();
            }
            _steptypeads = "";
            String _judgetypearr[] = MapUtils.getString((Map)_props.get("judgetype"), "value", "").split(":");
            int _judgetype = NumberTool.safeToInteger(_judgetypearr[0], Integer.valueOf(0)).intValue();
            _steptypeads = (new StringBuilder()).append(_steptypeads).append(_judgetype).toString();
            if(_judgetype == 2 || _judgetype == 5)
            {
                int _coojudge = NumberTool.safeToInteger(_judgetypearr[1], Integer.valueOf(0)).intValue();
                double _coojudgeads = NumberTool.safeToDouble(_judgetypearr[2], Double.valueOf(0.0D)).doubleValue();
                _steptypeads = (new StringBuilder()).append(_steptypeads).append(" ").append(_coojudge).toString();
                _steptypeads = (new StringBuilder()).append(_steptypeads).append(" ").append(_coojudgeads).toString();
            } else
            if(_judgetype == 100)
            {
                int _resultdeal = NumberTool.safeToInteger(_judgetypearr[3], Integer.valueOf(0)).intValue();
                _steptypeads = (new StringBuilder()).append(_steptypeads).append(" ").append(_resultdeal).toString();
            }
            StringBuffer _stepcondition = new StringBuffer();
            String _messageremind = MapUtils.getString((Map)_props.get("messageremind"), "value", "");
            String _messagenotice = MapUtils.getString((Map)_props.get("messagenotice"), "value", "");
            String _noticeowner = MapUtils.getString((Map)_props.get("messagenotice"), "noticeowners", "");
            String _specialnoticer = MapUtils.getString((Map)_props.get("messagenotice"), "specialnoticer", "");
            _stepcondition.append(_messageremind).append(";").append(";").append(";");
            _stepcondition.append(_messagenotice).append(";").append(";").append(_specialnoticer).append(";");
            int _agenttype = MapUtils.getIntValue((Map)_props.get("agenttype"), "value", 2);
            int _phoneshow = MapUtils.getIntValue((Map)_props.get("phoneshow"), "value", -1);
            int _stepid = _workbase.addProcessStep(_name, processid, 3);
            IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_stepid);
            _processstep.setName(_name);
            _processstep.setStepTypeAds(_steptypeads);
            _processstep.setPointTypeId(Integer.valueOf(_pointtypeid));
            _processstep.setAgentType(Integer.valueOf(_agenttype));
            _processstep.setPhoneshow(Integer.valueOf(_phoneshow));
            _processstep.setCondition(_stepcondition.toString());
            _processstep.setUrlMark(Integer.valueOf(_processurl));
            _processstep.setActionUrl(_actionurl);
            _processstep.setShowUrl(_showurl);
            _processstep.setStatus(Integer.valueOf(1));
            int _stepcode = MapUtils.getIntValue((Map)_props.get("stepcode"), "value", 0);
            if(_stepcode > 0)
                _processstep.setStepCode(Integer.valueOf(_stepcode));
            _stepid = _workbase.saveProcessStep(_processstep);
            if(_stepcode == 0)
            {
                _stepcode = _processstep.getStepCode().intValue();
                saveFlowProp("stepcode", "value", StringUtil.safeToString(Integer.valueOf(_stepcode), "0"), _node.getId(), _node.getEntityName());
            }
            _relationMap.put((new StringBuilder()).append("nodeid").append(_node.getId()).toString(), Integer.valueOf(_stepid));
            saveProcessStepHistory(_usedprocessid2, processid, _stepid, _stepcode, _usedprocessmap);
            if(_noticeowner != null && _noticeowner.length() > 0)
            {
                String _stepownerArr[] = _noticeowner.split(",");
                for(int j = 0; j < _stepownerArr.length; j++)
                {
                    String _stepownerArrArr[] = _stepownerArr[j].split(":");
                    IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                    _stepowner.setProcessId(Integer.valueOf(processid));
                    _stepowner.setStepId(Integer.valueOf(_stepid));
                    _stepowner.setOwnerGoto(Integer.valueOf(-2));
                    _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                    _stepowner.setOrgId(_stepownerArrArr[0]);
                    _stepowner.setOrgType(Integer.valueOf(Integer.parseInt(_stepownerArrArr[2])));
                    _stepowner.setValue(Integer.valueOf(1));
                    _stepowner.setStatus(Integer.valueOf(1));
                    _workbase.saveProcessOwner(_stepowner);
                }

            }
            int _stepdealerid = MapUtils.getIntValue((Map)_props.get("stepdealer"), "value", 0);
            if(_stepdealerid != 0)
            {
                IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                _stepowner.setProcessId(Integer.valueOf(processid));
                _stepowner.setStepId(Integer.valueOf(_stepid));
                _stepowner.setOwnerGoto(Integer.valueOf(0));
                _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                _stepowner.setOrgId(Integer.toString(_stepdealerid));
                _stepowner.setOrgType(Integer.valueOf(-1));
                _stepowner.setValue(Integer.valueOf(1));
                _stepowner.setStatus(Integer.valueOf(1));
                _workbase.saveProcessOwner(_stepowner);
            }
            String _conditionowner = MapUtils.getString((Map)_props.get("conditionowner"), "value", "");
            if(_conditionowner != null && _conditionowner.length() > 0)
            {
                String _stepownerArr[] = _conditionowner.split(",");
                for(int j = 0; j < _stepownerArr.length; j++)
                {
                    String _stepownerArrArr[] = _stepownerArr[j].split(":");
                    IWorkflowProcessOwner _stepowner = _workbase.loadProcessOwner(0);
                    _stepowner.setProcessId(Integer.valueOf(processid));
                    _stepowner.setStepId(Integer.valueOf(_stepid));
                    _stepowner.setOwnerGoto(Integer.valueOf(0));
                    _stepowner.setOwnerGotoId(Integer.valueOf(_stepid));
                    _stepowner.setOrgId(_stepownerArrArr[0]);
                    _stepowner.setOrgType(Integer.valueOf(Integer.parseInt(_stepownerArrArr[2])));
                    _stepowner.setValue(Integer.valueOf(1));
                    _stepowner.setStatus(Integer.valueOf(1));
                    _workbase.saveProcessOwner(_stepowner);
                }

            }
            String _steppredotaskids = MapUtils.getString((Map)_props.get("steppredo"), "value", "");
            if(StringUtil.isNotBlank(_steppredotaskids))
            {
                String _steppredotaskidArr[] = _steppredotaskids.split(",");
                for(int num = 0; num < _steppredotaskidArr.length; num++)
                {
                    int _steppredotaskid = NumberTool.safeToInteger(_steppredotaskidArr[num], Integer.valueOf(0)).intValue();
                    if(_steppredotaskid > 0)
                    {
                        IWorkflowProcessStepDo _steppredo = _workbase.loadProcessStepDo(0);
                        _steppredo.setProcessid(Integer.valueOf(processid));
                        _steppredo.setProcessstepid(Integer.valueOf(_stepid));
                        _steppredo.setSort(Integer.valueOf(num));
                        _steppredo.setStatus(Integer.valueOf(1));
                        _steppredo.setType(Integer.valueOf(WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue()));
                        _steppredo.setTaskid(Integer.valueOf(_steppredotaskid));
                        _workbase.saveProcessStepDo(_steppredo);
                    }
                }

            }
            String _stepaftdotaskids = MapUtils.getString((Map)_props.get("stepaftdo"), "value", "");
            if(StringUtil.isNotBlank(_stepaftdotaskids))
            {
                String _stepaftdotaskidArr[] = _stepaftdotaskids.split(",");
                for(int num = 0; num < _stepaftdotaskidArr.length; num++)
                {
                    int _stepaftdotaskid = NumberTool.safeToInteger(_stepaftdotaskidArr[num], Integer.valueOf(0)).intValue();
                    if(_stepaftdotaskid > 0)
                    {
                        IWorkflowProcessStepDo _steppredo = _workbase.loadProcessStepDo(0);
                        _steppredo.setProcessid(Integer.valueOf(processid));
                        _steppredo.setProcessstepid(Integer.valueOf(_stepid));
                        _steppredo.setSort(Integer.valueOf(num));
                        _steppredo.setStatus(Integer.valueOf(1));
                        _steppredo.setType(Integer.valueOf(WorkflowStepDoType.WF_DOTYPE_AFTDO.getEnumItemValue()));
                        _steppredo.setTaskid(Integer.valueOf(_stepaftdotaskid));
                        _workbase.saveProcessStepDo(_steppredo);
                    }
                }

            }
            String _ownerlinkids = MapUtils.getString((Map)_props.get("ownerlink"), "value", "");
            String _ownertypes = MapUtils.getString((Map)_props.get("ownerlink"), "ownertype", "");
            String _ownerstarters = MapUtils.getString((Map)_props.get("ownerlink"), "ownerstarter", "");
            String _ownerenders = MapUtils.getString((Map)_props.get("ownerlink"), "ownerender", "");
            String _autoskips = MapUtils.getString((Map)_props.get("ownerlink"), "autoskip", "");
            if(!StringUtils.isNotBlank(_ownerlinkids))
                continue;
            String linkidsArr[] = _ownerlinkids.split(",", -1);
            String ownertypesArr[] = _ownertypes.split(",", -1);
            String ownerstartersArr[] = _ownerstarters.split(";", -1);
            String ownerendersArr[] = _ownerenders.split(";", -1);
            String autoskipsArr[] = _autoskips.split(",", -1);
            for(int j = 0; j < linkidsArr.length; j++)
            {
                int _ownertype = NumberTool.safeToInteger(ownertypesArr[j], Integer.valueOf(0)).intValue();
                String _ownerstarter = StringUtil.safeToString(ownerstartersArr[j], "");
                String _ownerender = StringUtil.safeToString(ownerendersArr[j], "");
                int _autoskip = NumberTool.safeToInteger(autoskipsArr[j], Integer.valueOf(1)).intValue();
                IWorkflowProcessOwnerLink _ownerlink = _workbase.loadProcessOwnerLink(0);
                _ownerlink.setOwnerEnder(_ownerender);
                _ownerlink.setOwnerStarter(_ownerstarter);
                _ownerlink.setLinkLeaf(Integer.valueOf(_ownertype));
                _ownerlink.setLinkRoot(Integer.valueOf(_autoskip));
                _ownerlink.setStatus(Integer.valueOf(1));
                _ownerlink.setSort(Integer.valueOf(j + 1));
                _ownerlink.setProcessId(Integer.valueOf(processid));
                _ownerlink.setStepId(Integer.valueOf(_stepid));
                _workbase.saveProcessOwnerLink(_ownerlink);
            }

        }

label1:
        for(int i = 0; i < _nodelist.size(); i++)
        {
            IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
            if("state".equals(_node.getType()) || "end".equals(_node.getType()))
                continue;
            int _stepid = MapUtils.getIntValue(_relationMap, (new StringBuilder()).append("nodeid").append(_node.getId()).toString(), 0);
            List _pathlist = _workdraw.findFlowPaths(processid, _node.getId(), 0);
            Iterator i$ = _pathlist.iterator();
            do
            {
                if(!i$.hasNext())
                    continue label1;
                IWorkflowFlowPaths _path = (IWorkflowFlowPaths)i$.next();
                Map _pathprops = _workdraw.getFlowPropsMap(_path.getId(), _path.getEntityName());
                int linksort = MapUtils.getIntValue((Map)_pathprops.get("sort"), "value", 0);
                int _toid = _path.getToid().intValue();
                IWorkflowFlowNode _targer = _workdraw.getFlowNode(_toid);
                if("state".equals(_targer.getType()))
                {
                    List _statuspathlist = _workdraw.findFlowPaths(processid, _targer.getId(), 0);
                    if(_statuspathlist.size() == 0)
                        continue label1;
                    IWorkflowFlowPaths _statuspath = (IWorkflowFlowPaths)_statuspathlist.get(0);
                    int _toid2 = _statuspath.getToid().intValue();
                    int _linktarget = MapUtils.getIntValue(_relationMap, (new StringBuilder()).append("nodeid").append(_toid2).toString(), 0);
                    IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(0);
                    _steplink.setProcessId(Integer.valueOf(processid));
                    _steplink.setPreStepId(Integer.valueOf(_stepid));
                    _steplink.setAftStepId(Integer.valueOf(_linktarget));
                    _steplink.setStatus(Integer.valueOf(1));
                    _steplink.setSort(Integer.valueOf(linksort));
                    int _linkid = _workbase.saveProcessStepLink(_steplink);
                    Map _statusprops = _workdraw.getFlowPropsMap(_targer.getId(), _targer.getEntityName());
                    String _linkif = MapUtils.getString((Map)_statusprops.get("stepcondition"), "value", "");
                    if(StringUtils.isNotBlank(_linkif))
                    {
                        String _linkifArr[] = _linkif.split(",");
                        for(int j = 0; j < _linkifArr.length; j++)
                        {
                            String _linkifArrArr[] = _linkifArr[j].split(":");
                            if(_linkifArrArr.length != 3)
                                continue;
                            IWorkflowProcessStepLinkIf _steplinkif = _workbase.loadProcessStepLinkIf(0);
                            _steplinkif.setProcessId(Integer.valueOf(processid));
                            _steplinkif.setStepId(Integer.valueOf(_stepid));
                            _steplinkif.setLinkId(Integer.valueOf(_linkid));
                            _steplinkif.setStatus(Integer.valueOf(1));
                            _steplinkif.setIfType(NumberTool.safeToInteger(_linkifArrArr[0], Integer.valueOf(0)));
                            if(_steplinkif.getIfType().intValue() >= 0x186a0)
                            {
                                _steplinkif.setIfAnd(Integer.valueOf(0));
                                _steplinkif.setIfAds(_linkifArrArr[2]);
                            } else
                            {
                                _steplinkif.setIfAnd(NumberTool.safeToInteger(_linkifArrArr[1], Integer.valueOf(0)));
                                _steplinkif.setIfAds(_linkifArrArr[2]);
                            }
                            _workbase.saveProcessStepLinkIf(_steplinkif);
                        }

                    }
                    String _linkdo = MapUtils.getString((Map)_statusprops.get("steplinkdo"), "value", "");
                    if(StringUtils.isNotBlank(_linkdo))
                    {
                        String _linkdoArr[] = _linkdo.split(",");
                        int j = 0;
                        while(j < _linkdoArr.length) 
                        {
                            if(_linkdoArr[j].length() > 0)
                            {
                                IWorkflowProcessStepLinkDo _steplinkdo = _workbase.loadProcessStepLinkDo(0);
                                _steplinkdo.setProcessId(Integer.valueOf(processid));
                                _steplinkdo.setStepId(Integer.valueOf(_stepid));
                                _steplinkdo.setLinkId(Integer.valueOf(_linkid));
                                _steplinkdo.setStatus(Integer.valueOf(1));
                                _steplinkdo.setDoType(NumberTool.safeToInteger(_linkdoArr[j], Integer.valueOf(0)));
                                _workbase.saveProcessStepLinkDo(_steplinkdo);
                            }
                            j++;
                        }
                    }
                } else
                {
                    int _linktarget = MapUtils.getIntValue(_relationMap, (new StringBuilder()).append("nodeid").append(_toid).toString(), 0);
                    IWorkflowProcessStepLink _steplink = _workbase.loadProcessStepLink(0);
                    _steplink.setProcessId(Integer.valueOf(processid));
                    _steplink.setPreStepId(Integer.valueOf(_stepid));
                    _steplink.setAftStepId(Integer.valueOf(_linktarget));
                    _steplink.setStatus(Integer.valueOf(1));
                    _steplink.setSort(Integer.valueOf(linksort));
                    int _linkid = _workbase.saveProcessStepLink(_steplink);
                }
            } while(true);
        }

    }

    public Map changeProcess(int processid)
    {
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        if(_process.isEmpty())
        {
            LOGGER.info((new StringBuilder()).append("ID:").append(processid).append("\u7684\u6D41\u7A0B\u4E0D\u5B58\u5728").toString());
            return null;
        }
        Map _myflow = new HashMap();
        int num = 1;
        Map _parammap = new HashMap();
        Map status = new HashMap();
        Map paths = new HashMap();
        int _posx = 100;
        int _posy = 50;
        List _steplist = _workbase.getSingleStepList(processid);
        Iterator i$ = _steplist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map _stepmap = (Map)i$.next();
            int _processstepid = MapUtils.getIntValue(_stepmap, "processstepid", 0);
            IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_processstepid);
            if(_processstep.getStepTypeId().intValue() == WorkflowStepType.WF_TYPE_START.getEnumItemValue())
            {
                Map rect = new HashMap();
                rect.put("type", "start");
                Map _tp = new HashMap();
                _tp.put("text", _processstep.getName());
                rect.put("text", _tp);
                _tp = new HashMap();
                _tp.put("height", Integer.valueOf(50));
                _tp.put("width", Integer.valueOf(50));
                _tp.put("y", Integer.valueOf(_posy));
                _tp.put("x", Integer.valueOf(_posx));
                rect.put("attr", _tp);
                _parammap.put((new StringBuilder()).append("_posx").append(_processstepid).toString(), Integer.valueOf(_posx));
                _parammap.put((new StringBuilder()).append("_posy").append(_processstepid).toString(), Integer.valueOf(_posy));
                _posy += 100;
                _posx += 200;
                Map props = new HashMap();
                _tp = new HashMap();
                _tp.put("value", "\u5F00\u59CB\u8282\u70B9");
                props.put("steptype", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getName());
                props.put("stepname", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getStepCode());
                props.put("stepcode", _tp);
                rect.put("props", props);
                status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                _parammap.put((new StringBuilder()).append("_rect").append(_processstepid).toString(), (new StringBuilder()).append("rect").append(num).toString());
                num++;
            } else
            if(_processstep.getStepTypeId().intValue() == WorkflowStepType.WF_TYPE_END.getEnumItemValue())
            {
                Map rect = new HashMap();
                rect.put("type", "end");
                Map _tp = new HashMap();
                _tp.put("text", _processstep.getName());
                rect.put("text", _tp);
                _tp = new HashMap();
                _tp.put("height", Integer.valueOf(50));
                _tp.put("width", Integer.valueOf(50));
                _tp.put("y", Integer.valueOf(_posy));
                _tp.put("x", Integer.valueOf(_posx));
                rect.put("attr", _tp);
                _parammap.put((new StringBuilder()).append("_posx").append(_processstepid).toString(), Integer.valueOf(_posx));
                _parammap.put((new StringBuilder()).append("_posy").append(_processstepid).toString(), Integer.valueOf(_posy));
                _posy += 100;
                _posx += 200;
                Map props = new HashMap();
                _tp = new HashMap();
                _tp.put("value", "\u7ED3\u675F\u8282\u70B9");
                props.put("steptype", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getName());
                props.put("stepname", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getStepCode());
                props.put("stepcode", _tp);
                String _stepdotaskid = "";
                List _stepdotasklist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue());
                if(_stepdotasklist.size() > 0)
                {
                    for(Iterator i$ = _stepdotasklist.iterator(); i$.hasNext();)
                    {
                        Map _stepdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                        if(_stepdotaskid.length() > 0)
                            _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(",").toString();
                        _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(_dotype).toString();
                    }

                }
                _tp = new HashMap();
                _tp.put("value", _stepdotaskid);
                props.put("steppredo", _tp);
                rect.put("props", props);
                status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                _parammap.put((new StringBuilder()).append("_rect").append(_processstepid).toString(), (new StringBuilder()).append("rect").append(num).toString());
                num++;
            } else
            if(_processstep.getStepTypeId().intValue() == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
            {
                Map rect = new HashMap();
                rect.put("type", "submit");
                Map _tp = new HashMap();
                _tp.put("text", _processstep.getName());
                rect.put("text", _tp);
                _tp = new HashMap();
                _tp.put("height", Integer.valueOf(50));
                _tp.put("width", Integer.valueOf(100));
                _tp.put("y", Integer.valueOf(_posy));
                _tp.put("x", Integer.valueOf(_posx));
                rect.put("attr", _tp);
                _parammap.put((new StringBuilder()).append("_posx").append(_processstepid).toString(), Integer.valueOf(_posx));
                _parammap.put((new StringBuilder()).append("_posy").append(_processstepid).toString(), Integer.valueOf(_posy));
                _posy += 100;
                _posx += 200;
                Map props = new HashMap();
                _tp = new HashMap();
                _tp.put("value", _processstep.getName());
                props.put("stepname", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getStepCode());
                props.put("stepcode", _tp);
                _tp = new HashMap();
                _tp.put("value", "\u63D0\u4EA4\u8282\u70B9");
                props.put("steptype", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_processstep.getPointTypeId()));
                props.put("pointtypeid", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_processstep.getUrlMark()));
                props.put("processurl", _tp);
                _tp = new HashMap();
                String _steptypeads = _processstep.getStepTypeAds();
                int _submitowner = NumberTool.safeToInteger(_steptypeads, Integer.valueOf(0)).intValue();
                _tp.put("value", String.valueOf(_submitowner));
                props.put("submitowner", _tp);
                List _ownerlist = _workbase.getOwnerList(0, _processstepid);
                String _conditionowner = "";
                int _stepdealer = 0;
                Iterator i$ = _ownerlist.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    Map _ownermap = (Map)i$.next();
                    int _orgid = MapUtils.getIntValue(_ownermap, "orgid", 0);
                    int _orgtype = MapUtils.getIntValue(_ownermap, "orgtype", 0);
                    String _orgname = MapUtils.getString(_ownermap, "orgname", "");
                    if(_orgtype == -1)
                        _stepdealer = _orgid;
                    else
                    if(_orgtype != -2)
                    {
                        if(_conditionowner.length() > 0)
                            _conditionowner = (new StringBuilder()).append(_conditionowner).append(",").toString();
                        _conditionowner = (new StringBuilder()).append(_conditionowner).append(_orgid).append(":").append(_orgname).append(":").append(_orgtype).toString();
                    }
                } while(true);
                _tp = new HashMap();
                _tp.put("value", _conditionowner);
                props.put("conditionowner", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_stepdealer));
                props.put("stepdealer", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(NumberTool.safeToInteger(_processstep.getAgentType(), Integer.valueOf(2))));
                props.put("agenttype", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(NumberTool.safeToInteger(_processstep.getPhoneshow(), Integer.valueOf(-1))));
                props.put("phoneshow", _tp);
                String _stepcondition = _processstep.getCondition();
                String _arr[] = _stepcondition.split(";");
                if(_arr.length >= 6)
                {
                    String _messageremind = _arr[0];
                    _tp = new HashMap();
                    _tp.put("value", _messageremind);
                    props.put("messageremind", _tp);
                    String _messagenotice = _arr[3];
                    String _specialnoticer = _arr[5];
                    String _noticeowner = "";
                    List _noticeownerlist = _workbase.getOwnerListByGoto(0, _processstepid, -2);
                    for(Iterator i$ = _noticeownerlist.iterator(); i$.hasNext();)
                    {
                        Map _ownermap = (Map)i$.next();
                        int _orgid = MapUtils.getIntValue(_ownermap, "orgid", 0);
                        int _orgtype = MapUtils.getIntValue(_ownermap, "orgtype", 0);
                        String _orgname = MapUtils.getString(_ownermap, "orgname", "");
                        if(_noticeowner.length() > 0)
                            _noticeowner = (new StringBuilder()).append(_noticeowner).append(",").toString();
                        _noticeowner = (new StringBuilder()).append(_noticeowner).append(_orgid).append(":").append(_orgname).append(":").append(_orgtype).toString();
                    }

                    _tp = new HashMap();
                    _tp.put("value", _messagenotice);
                    _tp.put("noticeowners", _noticeowner);
                    _tp.put("specialnoticer", _specialnoticer);
                    props.put("messagenotice", _tp);
                }
                String _stepdotaskid = "";
                List _stepdotasklist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue());
                if(_stepdotasklist.size() > 0)
                {
                    for(Iterator i$ = _stepdotasklist.iterator(); i$.hasNext();)
                    {
                        Map _stepdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                        if(_stepdotaskid.length() > 0)
                            _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(",").toString();
                        _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(_dotype).toString();
                    }

                }
                _tp = new HashMap();
                _tp.put("value", _stepdotaskid);
                props.put("steppredo", _tp);
                _stepdotaskid = "";
                _stepdotasklist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_AFTDO.getEnumItemValue());
                if(_stepdotasklist.size() > 0)
                {
                    for(Iterator i$ = _stepdotasklist.iterator(); i$.hasNext();)
                    {
                        Map _stepdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                        if(_stepdotaskid.length() > 0)
                            _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(",").toString();
                        _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(_dotype).toString();
                    }

                }
                _tp = new HashMap();
                _tp.put("value", _stepdotaskid);
                props.put("stepaftdo", _tp);
                rect.put("props", props);
                status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                _parammap.put((new StringBuilder()).append("_rect").append(_processstepid).toString(), (new StringBuilder()).append("rect").append(num).toString());
                num++;
            } else
            if(_processstep.getStepTypeId().intValue() == WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
            {
                Map rect = new HashMap();
                rect.put("type", "judge");
                Map _tp = new HashMap();
                _tp.put("text", _processstep.getName());
                rect.put("text", _tp);
                _tp = new HashMap();
                _tp.put("height", Integer.valueOf(50));
                _tp.put("width", Integer.valueOf(100));
                _tp.put("y", Integer.valueOf(_posy));
                _tp.put("x", Integer.valueOf(_posx));
                rect.put("attr", _tp);
                _parammap.put((new StringBuilder()).append("_posx").append(_processstepid).toString(), Integer.valueOf(_posx));
                _parammap.put((new StringBuilder()).append("_posy").append(_processstepid).toString(), Integer.valueOf(_posy));
                _posy += 100;
                _posx += 200;
                Map props = new HashMap();
                _tp = new HashMap();
                _tp.put("value", _processstep.getName());
                props.put("stepname", _tp);
                _tp = new HashMap();
                _tp.put("value", _processstep.getStepCode());
                props.put("stepcode", _tp);
                _tp = new HashMap();
                _tp.put("value", "\u5904\u7406\u8282\u70B9");
                props.put("steptype", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_processstep.getPointTypeId()));
                props.put("pointtypeid", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_processstep.getUrlMark()));
                props.put("processurl", _tp);
                _tp = new HashMap();
                String _steptypeads = _processstep.getStepTypeAds();
                String _judgetypearr[] = _steptypeads.split(" ");
                String _judgetype = _judgetypearr[0];
                String _coojudge = "";
                String _coojudgeads = "";
                String _resultdeal = "";
                if(NumberTool.safeToInteger(_judgetype, Integer.valueOf(0)).intValue() == 2 || NumberTool.safeToInteger(_judgetype, Integer.valueOf(0)).intValue() == 5)
                {
                    _coojudge = _judgetypearr[1];
                    _coojudgeads = _judgetypearr[2];
                } else
                if(NumberTool.safeToInteger(_judgetype, Integer.valueOf(0)).intValue() == 100)
                    _resultdeal = _judgetypearr[1];
                _tp.put("value", (new StringBuilder()).append(_judgetype).append(":").append(_coojudge).append(":").append(_coojudgeads).append(":").append(_resultdeal).toString());
                props.put("judgetype", _tp);
                List _ownerlist = _workbase.getOwnerList(0, _processstepid);
                String _conditionowner = "";
                int _stepdealer = 0;
                Iterator i$ = _ownerlist.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    Map _ownermap = (Map)i$.next();
                    int _orgid = MapUtils.getIntValue(_ownermap, "orgid", 0);
                    int _orgtype = MapUtils.getIntValue(_ownermap, "orgtype", 0);
                    String _orgname = MapUtils.getString(_ownermap, "orgname", "");
                    if(_orgtype == -1)
                        _stepdealer = _orgid;
                    else
                    if(_orgtype != -2)
                    {
                        if(_conditionowner.length() > 0)
                            _conditionowner = (new StringBuilder()).append(_conditionowner).append(",").toString();
                        _conditionowner = (new StringBuilder()).append(_conditionowner).append(_orgid).append(":").append(_orgname).append(":").append(_orgtype).toString();
                    }
                } while(true);
                _tp = new HashMap();
                _tp.put("value", _conditionowner);
                props.put("conditionowner", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_stepdealer));
                props.put("stepdealer", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(NumberTool.safeToInteger(_processstep.getAgentType(), Integer.valueOf(2))));
                props.put("agenttype", _tp);
                _tp = new HashMap();
                _tp.put("value", String.valueOf(NumberTool.safeToInteger(_processstep.getPhoneshow(), Integer.valueOf(-1))));
                props.put("phoneshow", _tp);
                String _stepcondition = _processstep.getCondition();
                String _arr[] = _stepcondition.split(";");
                if(_arr.length >= 6)
                {
                    String _messageremind = _arr[0];
                    _tp = new HashMap();
                    _tp.put("value", _messageremind);
                    props.put("messageremind", _tp);
                    String _messagenotice = _arr[3];
                    String _specialnoticer = _arr[5];
                    String _noticeowner = "";
                    List _noticeownerlist = _workbase.getOwnerListByGoto(0, _processstepid, -2);
                    for(Iterator i$ = _noticeownerlist.iterator(); i$.hasNext();)
                    {
                        Map _ownermap = (Map)i$.next();
                        int _orgid = MapUtils.getIntValue(_ownermap, "orgid", 0);
                        int _orgtype = MapUtils.getIntValue(_ownermap, "orgtype", 0);
                        String _orgname = MapUtils.getString(_ownermap, "orgname", "");
                        if(_noticeowner.length() > 0)
                            _noticeowner = (new StringBuilder()).append(_noticeowner).append(",").toString();
                        _noticeowner = (new StringBuilder()).append(_noticeowner).append(_orgid).append(":").append(_orgname).append(":").append(_orgtype).toString();
                    }

                    _tp = new HashMap();
                    _tp.put("value", _messagenotice);
                    _tp.put("noticeowners", _noticeowner);
                    _tp.put("specialnoticer", _specialnoticer);
                    props.put("messagenotice", _tp);
                }
                String _stepdotaskid = "";
                List _stepdotasklist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_PREDO.getEnumItemValue());
                if(_stepdotasklist.size() > 0)
                {
                    for(Iterator i$ = _stepdotasklist.iterator(); i$.hasNext();)
                    {
                        Map _stepdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                        if(_stepdotaskid.length() > 0)
                            _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(",").toString();
                        _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(_dotype).toString();
                    }

                }
                _tp = new HashMap();
                _tp.put("value", _stepdotaskid);
                props.put("steppredo", _tp);
                _stepdotaskid = "";
                _stepdotasklist = _workbase.getProcessStepDoList(_processstepid, WorkflowStepDoType.WF_DOTYPE_AFTDO.getEnumItemValue());
                if(_stepdotasklist.size() > 0)
                {
                    for(Iterator i$ = _stepdotasklist.iterator(); i$.hasNext();)
                    {
                        Map _stepdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_stepdomap, "taskid", 0);
                        if(_stepdotaskid.length() > 0)
                            _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(",").toString();
                        _stepdotaskid = (new StringBuilder()).append(_stepdotaskid).append(_dotype).toString();
                    }

                }
                _tp = new HashMap();
                _tp.put("value", _stepdotaskid);
                props.put("stepaftdo", _tp);
                List _ownerlinkmainlist = _workbase.getOwnerLinkList(_processstepid);
                String _ownerlinkids = "";
                String _ownertypes = "";
                String _ownerstarters = "";
                String _ownerenders = "";
                String _autoskips = "";
                for(int k = 0; k < _ownerlinkmainlist.size(); k++)
                {
                    Map _ownerlinkmap = (Map)_ownerlinkmainlist.get(k);
                    int _value = MapUtils.getIntValue(_ownerlinkmap, "objid", 0);
                    int _ownertype = MapUtils.getIntValue(_ownerlinkmap, "linkleaf", 0);
                    String _ownerstarter = StringUtil.safeToString(_ownerlinkmap.get("ownerstarter"), "");
                    String _ownerender = StringUtil.safeToString(_ownerlinkmap.get("ownerender"), "");
                    int _autoskip = MapUtils.getIntValue(_ownerlinkmap, "linkroot", 1);
                    _ownerlinkids = (new StringBuilder()).append(_ownerlinkids).append(k != 0 ? "," : "").append(_value).toString();
                    _ownertypes = (new StringBuilder()).append(_ownertypes).append(k != 0 ? "," : "").append(_ownertype).toString();
                    _ownerstarters = (new StringBuilder()).append(_ownerstarters).append(k != 0 ? ";" : "").append(_ownerstarter).toString();
                    _ownerenders = (new StringBuilder()).append(_ownerenders).append(k != 0 ? ";" : "").append(_ownerender).toString();
                    _autoskips = (new StringBuilder()).append(_autoskips).append(k != 0 ? "," : "").append(_autoskip).toString();
                }

                _tp = new HashMap();
                _tp.put("value", _ownerlinkids);
                _tp.put("ownertype", _ownertypes);
                _tp.put("ownerstarter", _ownerstarters);
                _tp.put("ownerender", _ownerenders);
                _tp.put("autoskip", _autoskips);
                props.put("ownerlink", _tp);
                rect.put("props", props);
                status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                _parammap.put((new StringBuilder()).append("_rect").append(_processstepid).toString(), (new StringBuilder()).append("rect").append(num).toString());
                num++;
            }
        } while(true);
        Map _statemap = new HashMap();
        for(Iterator i$ = _steplist.iterator(); i$.hasNext();)
        {
            Map _stepmap = (Map)i$.next();
            int _processstepid = MapUtils.getIntValue(_stepmap, "processstepid", 0);
            IWorkflowProcessStep _processstep = _workbase.loadProcessStep(_processstepid);
            List _linklist = _workbase.getStepLinkList(_processstepid);
            Iterator i$ = _linklist.iterator();
            while(i$.hasNext()) 
            {
                Map _linkmap = (Map)i$.next();
                int _linkid = MapUtils.getIntValue(_linkmap, "objid", 0);
                int _sort = MapUtils.getIntValue(_linkmap, "sort", 0);
                int _prestepid = MapUtils.getIntValue(_linkmap, "prestepid", 0);
                int _aftstepid = MapUtils.getIntValue(_linkmap, "aftstepid", 0);
                String _fromrect = MapUtils.getString(_parammap, (new StringBuilder()).append("_rect").append(_prestepid).toString(), "");
                String _torect = MapUtils.getString(_parammap, (new StringBuilder()).append("_rect").append(_aftstepid).toString(), "");
                String _stepcondition = "";
                String _steplinkdo = "";
                List _linkiflist = _workbase.getStepLinkIfList(_linkid);
                if(_linkiflist.size() > 0)
                {
                    for(Iterator i$ = _linkiflist.iterator(); i$.hasNext();)
                    {
                        Map _linkifmap = (Map)i$.next();
                        int _iftype = MapUtils.getIntValue(_linkifmap, "iftype", 0);
                        int _ifand = MapUtils.getIntValue(_linkifmap, "ifand", 0);
                        String _ifads = MapUtils.getString(_linkifmap, "ifads", "");
                        if(_stepcondition.length() > 0)
                            _stepcondition = (new StringBuilder()).append(_stepcondition).append(",").toString();
                        if(_iftype >= 0x186a0)
                            _stepcondition = (new StringBuilder()).append(_stepcondition).append(_iftype).append(":").append(0).append(":").append(_ifads).toString();
                        else
                            _stepcondition = (new StringBuilder()).append(_stepcondition).append(_iftype).append(":").append(_ifand).append(":").append(_ifads).toString();
                    }

                }
                List _linkdolist = _workbase.getStepLinkDoList(_linkid);
                if(_linkdolist.size() > 0)
                {
                    for(Iterator i$ = _linkdolist.iterator(); i$.hasNext();)
                    {
                        Map _linkdomap = (Map)i$.next();
                        int _dotype = MapUtils.getIntValue(_linkdomap, "dotype", 0);
                        if(_steplinkdo.length() > 0)
                            _steplinkdo = (new StringBuilder()).append(_steplinkdo).append(",").toString();
                        _steplinkdo = (new StringBuilder()).append(_steplinkdo).append(_dotype).toString();
                    }

                }
                Map _tp;
                if(StringUtils.isNotBlank(_stepcondition) || StringUtils.isNotBlank(_steplinkdo))
                {
                    int _preposx = MapUtils.getInteger(_parammap, (new StringBuilder()).append("_posx").append(_prestepid).toString(), Integer.valueOf(0)).intValue();
                    int _preposy = MapUtils.getInteger(_parammap, (new StringBuilder()).append("_posy").append(_prestepid).toString(), Integer.valueOf(0)).intValue();
                    int _aftposx = MapUtils.getInteger(_parammap, (new StringBuilder()).append("_posx").append(_aftstepid).toString(), Integer.valueOf(0)).intValue();
                    int _aftposy;
                    for(_aftposy = MapUtils.getInteger(_parammap, (new StringBuilder()).append("_posy").append(_aftstepid).toString(), Integer.valueOf(0)).intValue(); MapUtils.getInteger(_statemap, (new StringBuilder()).append("posx").append(_preposx).toString(), Integer.valueOf(0)).intValue() == 1 && MapUtils.getInteger(_statemap, (new StringBuilder()).append("posy").append(_aftposy).toString(), Integer.valueOf(0)).intValue() == 1; _preposx += 60);
                    Map rect = new HashMap();
                    rect.put("type", "state");
                    _tp = new HashMap();
                    _tp.put("text", "\u6761\u4EF6");
                    rect.put("text", _tp);
                    _tp = new HashMap();
                    _tp.put("height", Integer.valueOf(50));
                    _tp.put("width", Integer.valueOf(50));
                    _tp.put("y", Integer.valueOf(_aftposy));
                    _tp.put("x", Integer.valueOf(_preposx));
                    _statemap.put((new StringBuilder()).append("posx").append(_preposx).toString(), Integer.valueOf(1));
                    _statemap.put((new StringBuilder()).append("posy").append(_aftposy).toString(), Integer.valueOf(1));
                    rect.put("attr", _tp);
                    Map _stateprops = new HashMap();
                    _tp = new HashMap();
                    _tp.put("value", _stepcondition);
                    _stateprops.put("stepcondition", _tp);
                    _tp = new HashMap();
                    _tp.put("value", _steplinkdo);
                    _stateprops.put("steplinkdo", _tp);
                    rect.put("props", _stateprops);
                    status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                    String _staterect = (new StringBuilder()).append("rect").append(num).toString();
                    num++;
                    Map path = new HashMap();
                    path.put("type", "path");
                    path.put("from", _fromrect);
                    path.put("to", _staterect);
                    path.put("dots", new LinkedList());
                    _fromrect = _staterect;
                    Map pathprops = new HashMap();
                    _tp = new HashMap();
                    _tp.put("value", String.valueOf(_sort));
                    pathprops.put("sort", _tp);
                    path.put("props", pathprops);
                    _sort = 0;
                    paths.put((new StringBuilder()).append("path").append(num).toString(), path);
                    num++;
                }
                Map path = new HashMap();
                path.put("type", "path");
                path.put("from", _fromrect);
                path.put("to", _torect);
                path.put("dots", new LinkedList());
                Map props = new HashMap();
                _tp = new HashMap();
                _tp.put("value", String.valueOf(_sort));
                props.put("sort", _tp);
                path.put("props", props);
                paths.put((new StringBuilder()).append("path").append(num).toString(), path);
                num++;
            }
        }

        _myflow.put("states", status);
        _myflow.put("paths", paths);
        return _myflow;
    }

    public int translateExampleData(Map map)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample _example = _workapp.loadExample(0);
        try
        {
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
            Date _starttime;
            try
            {
                _starttime = map.get("starttime") != null ? sdf.parse((new StringBuilder()).append(map.get("starttime")).append("").toString().substring(0, 19)) : new Date();
            }
            catch(Exception e)
            {
                _starttime = new Date();
            }
            _example.setStartTime(_starttime);
            Date _endtime;
            try
            {
                _endtime = map.get("endtime") != null ? sdf.parse((new StringBuilder()).append(map.get("endtime")).append("").toString().substring(0, 19)) : new Date();
            }
            catch(Exception e)
            {
                _endtime = new Date();
            }
            _example.setEndTime(_endtime);
            String _starterads = StringUtil.safeToString(map.get("starterads"), "");
            _example.setStarterAds(_starterads);
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
                Date _changetime;
                try
                {
                    _changetime = _entrymap.get("changetime") != null ? sdf.parse((new StringBuilder()).append(_entrymap.get("changetime")).append("").toString().substring(0, 19)) : new Date();
                }
                catch(Exception e)
                {
                    _changetime = new Date();
                }
                _entry.setChangeTime(_changetime);
                _workapp.saveExampleEntry(_entry);
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
                Date _starttime2;
                try
                {
                    _starttime2 = _stepmap.get("starttime") != null ? sdf.parse((new StringBuilder()).append(_stepmap.get("starttime")).append("").toString().substring(0, 19)) : new Date();
                }
                catch(Exception e)
                {
                    _starttime2 = new Date();
                }
                _step.setStartTime(_starttime2);
                Date _endtime2;
                try
                {
                    _endtime2 = _stepmap.get("endtime") != null ? sdf.parse((new StringBuilder()).append(_stepmap.get("endtime")).append("").toString().substring(0, 19)) : new Date();
                }
                catch(Exception e)
                {
                    _endtime2 = new Date();
                }
                _step.setEndTime(_endtime2);
                List _ownerlist = ((List) (_stepmap.get("ownerlist") != null ? (List)_stepmap.get("ownerlist") : ((List) (new ArrayList()))));
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
                    Date _starttime3;
                    try
                    {
                        _starttime3 = _ownermap.get("starttime") != null ? sdf.parse((new StringBuilder()).append(_ownermap.get("starttime")).append("").toString().substring(0, 19)) : new Date();
                    }
                    catch(Exception e)
                    {
                        _starttime3 = new Date();
                    }
                    _owner.setStartTime(_starttime3);
                    Date _approvetime;
                    try
                    {
                        _approvetime = _ownermap.get("approvetime") != null ? sdf.parse((new StringBuilder()).append(_ownermap.get("approvetime")).append("").toString().substring(0, 19)) : new Date();
                    }
                    catch(Exception e)
                    {
                        _approvetime = new Date();
                    }
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

            }

            LOGGER.info((new StringBuilder()).append("\u5BFC\u5165\u6D41\u7A0BID\uFF1A").append(_exampleid).toString());
            return _exampleid;
        }
        catch(Exception e)
        {
            LOGGER.error("\u8F6C\u6362\u5F02\u5E38");
        }
        return 0;
    }

    public boolean canbeAgents(int processtype, int processcode, int stepcode, int ownerid, int agentid, Date starttime, Date endtime, 
            Map returnmap, int preobjid)
    {
        Map inmap = new HashMap();
        inmap.put("processtype", Integer.valueOf(processtype));
        inmap.put("processcode", Integer.valueOf(processcode));
        inmap.put("stepcode", Integer.valueOf(stepcode));
        inmap.put("objid", Integer.valueOf(preobjid));
        Map returnmap0 = new HashMap();
        boolean result0 = canbeAgents(0, ownerid, inmap, starttime, endtime, returnmap0);
        String message0 = StringUtil.safeToString(returnmap0.get("message"), "");
        Map returnmap1 = new HashMap();
        boolean result1 = canbeAgents(1, ownerid, inmap, starttime, endtime, returnmap1);
        String message1 = StringUtil.safeToString(returnmap1.get("message"), "");
        Map returnmap2 = new HashMap();
        boolean result2 = canbeAgents(2, agentid, inmap, starttime, endtime, returnmap2);
        String message2 = StringUtil.safeToString(returnmap2.get("message"), "");
        List messagelist = new ArrayList();
        messagelist.add(message0);
        messagelist.add(message1);
        messagelist.add(message2);
        returnmap.put("messagelist", messagelist);
        return result0 && result1 && result2;
    }

    private boolean canbeAgents(int type, int orgid, Map inmap, Date starttime, Date endtime, Map returnmap)
    {
        inmap.put("type", Integer.valueOf(type % 2));
        inmap.put("orgid", Integer.valueOf(orgid));
        List _list = WorkflowServiceFactory.getWorkflowBaseService().judgeAgentsList(inmap);
        long daysBetween = ((endtime.getTime() - starttime.getTime()) + 0xf4240L) / 0x5265c00L;
        int days = (int)daysBetween + 2;
        boolean markArr[] = new boolean[days];
        for(int i = 0; i < days; i++)
            markArr[i] = true;

        Date basedate = starttime;
        StringBuffer message = new StringBuffer();
label0:
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            Date tempstart = (Date)_map.get("starttime");
            Date tempend = (Date)_map.get("endtime");
            if(tempend.before(basedate))
                continue;
            int marknum;
            if(tempstart.before(basedate) || tempstart.equals(basedate))
            {
                marknum = -1;
                do
                {
                    if(!endtime.after(basedate) && !endtime.equals(basedate) || !tempend.after(basedate) && !tempend.equals(basedate))
                        continue label0;
                    if(marknum == -1)
                    {
                        marknum = (int)(((basedate.getTime() - starttime.getTime()) + 0xf4240L) / 0x5265c00L);
                        markArr[marknum] = false;
                    } else
                    {
                        marknum++;
                        markArr[marknum] = false;
                    }
                    basedate = TimeUtil.getPreOrNextDay(basedate, 1);
                } while(true);
            }
            if(!tempstart.before(endtime) && !tempstart.equals(endtime))
                continue;
            basedate = tempstart;
            marknum = -1;
            for(; (endtime.after(basedate) || endtime.equals(basedate)) && (tempend.after(basedate) || tempend.equals(basedate)); basedate = TimeUtil.getPreOrNextDay(basedate, 1))
                if(marknum == -1)
                {
                    marknum = (int)(((basedate.getTime() - starttime.getTime()) + 0xf4240L) / 0x5265c00L);
                    markArr[marknum] = false;
                } else
                {
                    marknum++;
                    markArr[marknum] = false;
                }

        }

        int markfont = -1;
        int marktype = 1;
        Calendar c = Calendar.getInstance();
        c.setTime(starttime);
        int day = c.get(5);
        for(int i = 0; i < days; i++)
        {
            if(!markArr[i])
            {
                if(marktype == 1)
                {
                    marktype = 0;
                    markfont = i;
                }
                continue;
            }
            if(!markArr[i] || marktype != 0)
                continue;
            marktype = 1;
            if(markfont == -1)
                continue;
            Date bdate;
            if(markfont == i - 1)
            {
                if(message.length() > 0)
                    message.append(",");
                c.setTime(starttime);
                c.set(5, day + markfont);
                bdate = c.getTime();
                message.append(TimeUtil.formatDate(bdate, "yyyy-MM-dd"));
                continue;
            }
            if(message.length() > 0)
                message.append(",");
            c.setTime(starttime);
            c.set(5, day + markfont);
            bdate = c.getTime();
            message.append(TimeUtil.formatDate(bdate, "yyyy-MM-dd"));
            c.setTime(starttime);
            c.set(5, (day + i) - 1);
            Date edate = c.getTime();
            message.append("\u2014").append(TimeUtil.formatDate(edate, "yyyy-MM-dd"));
        }

        if(message.length() > 0)
        {
            returnmap.put("message", message.toString());
            return false;
        } else
        {
            returnmap.put("message", message.toString());
            return true;
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowAdvServiceImpl);

}
