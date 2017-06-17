// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowInServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepOwnerStatus;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import org.apache.log4j.Logger;

public class WorkflowInServiceImpl extends MetaDBContextSupport
    implements IWorkflowInService
{

    public WorkflowInServiceImpl()
    {
    }

    public List judgeConditioner(int starterid, int nowerid, List list)
    {
        List _list = new ArrayList();
        List _starterlist = OrgServiceFactory.getOrgService().findParentOrgById(Integer.toString(starterid));
        List _nowerlist = OrgServiceFactory.getOrgService().findParentOrgById(Integer.toString(nowerid));
        for(int i = 0; i < list.size(); i++)
        {
            Map _map = (Map)list.get(i);
            int _linkleaf = NumberTool.convertMapKeyToInt(_map, "linkleaf", Integer.valueOf(0)).intValue();
            int _linkroot = NumberTool.convertMapKeyToInt(_map, "linkroot", Integer.valueOf(1)).intValue();
            String _ownerstarter = StringUtil.safeToString(_map.get("ownerstarter"), "");
            String _ownerender = StringUtil.safeToString(_map.get("ownerender"), "");
            List _ownerlist = changeOwnerToList(_ownerstarter);
            if(_linkleaf == 1)
            {
                if(isOrgidIn(_ownerlist, _starterlist))
                {
                    _list = changeOwnerToList(_ownerender, _linkroot);
                    return _list;
                }
                continue;
            }
            if(_linkleaf == 2 && isOrgidIn(_ownerlist, _nowerlist))
            {
                _list = changeOwnerToList(_ownerender, _linkroot);
                return _list;
            }
        }

        return _list;
    }

    public List judgeConditioner(int starterid, List list, int stepid)
    {
        List _list = new ArrayList();
        List _starterlist = new ArrayList();
        try
        {
            _starterlist = OrgServiceFactory.getOrgService().findParentOrgById(Integer.toString(starterid));
        }
        catch(Exception e)
        {
            _starterlist = new ArrayList();
        }
        List _nowerlist = new ArrayList();
        LOGGER.info((new StringBuilder()).append("stepid=").append(stepid).append(",start").toString());
        int i;
        for(i = 0; i < list.size(); i++)
        {
            Map _map = (Map)list.get(i);
            int _linkleaf = NumberTool.convertMapKeyToInt(_map, "linkleaf", Integer.valueOf(0)).intValue();
            int _linkroot = NumberTool.convertMapKeyToInt(_map, "linkroot", Integer.valueOf(1)).intValue();
            String _ownerstarter = StringUtil.safeToString(_map.get("ownerstarter"), "");
            String _ownerender = StringUtil.safeToString(_map.get("ownerender"), "");
            List _ownerlist = changeOwnerToList(_ownerstarter);
            if(_linkleaf == 1)
            {
                LOGGER.info((new StringBuilder()).append("stepid=").append(stepid).append(",starterid=").append(starterid).append(";size=").append(_starterlist.size()).toString());
                if(isOrgidIn(_ownerlist, _starterlist))
                    _list.addAll(changeOwnerToList(_ownerender, _linkroot));
                continue;
            }
            if(_linkleaf != 2)
                continue;
            if(_nowerlist.size() == 0)
            {
                Map _ownermap = getFontOwner(0, stepid);
                int _nowerid = NumberTool.convertMapKeyToInt(_ownermap, "ownerid", Integer.valueOf(0)).intValue();
                _nowerlist = OrgServiceFactory.getOrgService().findParentOrgById(Integer.toString(_nowerid));
                if(_nowerlist == null || _nowerlist.size() == 0)
                    _nowerlist = _starterlist;
            }
            int tempid = NumberTool.safeToInteger(((OrgObject)_nowerlist.get(0)).getOrgId(), Integer.valueOf(0)).intValue();
            LOGGER.info((new StringBuilder()).append("stepid=").append(stepid).append(",nowerid=").append(tempid).append(";size=").append(_nowerlist.size()).toString());
            if(isOrgidIn(_ownerlist, _nowerlist))
                _list.addAll(changeOwnerToList(_ownerender, _linkroot));
        }

        LOGGER.info((new StringBuilder()).append("stepid=").append(stepid).append(",end i=").append(i).toString());
        return removeDuplicate(_list);
    }

    private List removeDuplicate(List list)
    {
        List lists = new ArrayList();
        Map map = new HashMap();
        for(Iterator i$ = list.iterator(); i$.hasNext(); lists.add(map))
        {
            Map obj = (Map)i$.next();
            map = new HashMap();
            map.put("orgid", obj.get("orgid"));
            map.put("orgtype", obj.get("orgtype"));
            map.put("value", obj.get("value"));
            map.put("mark", obj.get("mark"));
        }

        HashSet h = new HashSet(lists);
        lists.clear();
        lists.addAll(h);
        Map mr = new HashMap();
        list.clear();
        for(Iterator i$ = lists.iterator(); i$.hasNext(); list.add(mr))
        {
            Map obj2 = (Map)i$.next();
            mr = new HashMap();
            mr.put("orgid", (new StringBuilder()).append(obj2.get("orgid")).append("").toString());
            mr.put("orgtype", (new StringBuilder()).append(obj2.get("orgtype")).append("").toString());
            mr.put("value", (new StringBuilder()).append(obj2.get("value")).append("").toString());
            mr.put("mark", (new StringBuilder()).append(obj2.get("mark")).append("").toString());
        }

        return list;
    }

    private boolean isOrgidIn(List list, List orglist)
    {
        if(list.size() == 0)
            return true;
        for(int i = 0; i < orglist.size(); i++)
        {
            OrgObject _org = (OrgObject)orglist.get(i);
            for(int j = 0; j < list.size(); j++)
            {
                Map _map = (Map)list.get(j);
                String _orgid = StringUtil.safeToString(_map.get("orgid"), "");
                int _orgtype = NumberTool.convertMapKeyToInt(_map, "orgtype", Integer.valueOf(0)).intValue();
                if(_orgid.equals(_org.getOrgId()))
                    return true;
            }

        }

        return false;
    }

    private List changeOwnerToList(String owner)
    {
        List _list = new ArrayList();
        String _ownerArr[] = owner.split(",");
        for(int i = 0; i < _ownerArr.length; i++)
        {
            if(_ownerArr[i].length() <= 0)
                continue;
            String _ownerArrArr[] = _ownerArr[i].split(":");
            if(_ownerArrArr.length >= 3)
            {
                Map _map = new HashMap();
                _map.put("orgid", _ownerArrArr[0]);
                _map.put("orgtype", _ownerArrArr[2]);
                _map.put("value", Integer.valueOf(1));
                _list.add(_map);
            }
        }

        return _list;
    }

    private List changeOwnerToList(String owner, int mark)
    {
        List _list = new ArrayList();
        String _ownerArr[] = owner.split(",");
        for(int i = 0; i < _ownerArr.length; i++)
        {
            if(_ownerArr[i].length() <= 0)
                continue;
            String _ownerArrArr[] = _ownerArr[i].split(":");
            if(_ownerArrArr.length >= 3)
            {
                Map _map = new HashMap();
                _map.put("orgid", _ownerArrArr[0]);
                _map.put("orgtype", _ownerArrArr[2]);
                _map.put("value", Integer.valueOf(1));
                _map.put("mark", Integer.valueOf(mark));
                _list.add(_map);
            }
        }

        if(_list.size() == 0 && mark == 2)
        {
            Map _map = new HashMap();
            _map.put("orgid", Integer.valueOf(0));
            _map.put("orgtype", Integer.valueOf(8));
            _map.put("value", Integer.valueOf(1));
            _map.put("mark", Integer.valueOf(mark));
            _list.add(_map);
        }
        return _list;
    }

    public Map judgeSpecialCondition(int type, Map map)
    {
        WorkflowException _exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _returnmap = new HashMap();
        Map _mainmap = _workbase.findParaByEndNameAndKey("LinkIfType", type);
        String _valueads = StringUtil.safeToString(_mainmap.get("valueads"), "");
        try
        {
            IWorkflowCondition _workcondition = (IWorkflowCondition)Class.forName(_valueads).newInstance();
            try
            {
                boolean _result = _workcondition.condition(map, _returnmap);
                if(_result)
                    _returnmap.put("result", Integer.valueOf(0));
                else
                    _returnmap.put("result", Integer.valueOf(1));
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append(_valueads).append(".action\u6267\u884C\u5931\u8D25").toString(), e);
                _returnmap.put("exception", Integer.valueOf(_exception.actionfaild));
            }
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append(_valueads).append("\u53CD\u5C04\u5931\u8D25").toString(), e);
            _returnmap.put("exception", Integer.valueOf(_exception.classfaild));
        }
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        Object _key;
        Object _value;
        for(Iterator i$ = _returnmap.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
        {
            _key = i$.next();
            _value = _returnmap.get(_key);
        }

        _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap);
        return _returnmap;
    }

    public Map judgeSpecialTask(int type, Map map)
    {
        WorkflowException _exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        Map _returnmap = new HashMap();
        Map _mainmap = _workbase.findParaByEndNameAndKey("SpecialTask", type);
        String _valueads = StringUtil.safeToString(_mainmap.get("valueads"), "");
        try
        {
            IWorkflowAction _workaction = (IWorkflowAction)Class.forName(_valueads).newInstance();
            try
            {
                _workaction.action(map, _returnmap);
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append(_valueads).append(".action\u6267\u884C\u5931\u8D25").toString(), e);
                _returnmap.put("exception", Integer.valueOf(_exception.actionfaild));
            }
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append(_valueads).append("\u53CD\u5C04\u5931\u8D25").toString(), e);
            _returnmap.put("exception", Integer.valueOf(_exception.classfaild));
        }
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        Object _key;
        Object _value;
        for(Iterator i$ = _returnmap.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
        {
            _key = i$.next();
            _value = _returnmap.get(_key);
        }

        _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap);
        return _returnmap;
    }

    public Map judgeSpecialDealer(int type, Map map)
    {
        WorkflowException _exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _returnmap = new HashMap();
        Map _mainmap = _workbase.findParaByEndNameAndKey("StepDealer", type);
        String _valueads = StringUtil.safeToString(_mainmap.get("valueads"), "");
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        try
        {
            IWorkflowDealer _workdealer = (IWorkflowDealer)Class.forName(_valueads).newInstance();
            try
            {
                List _stepownerlist = _workdealer.dealer(map, _returnmap);
                Object _key;
                Object _value;
                for(Iterator i$ = _returnmap.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
                {
                    _key = i$.next();
                    _value = _returnmap.get(_key);
                }

                _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap);
                _returnmap.put("stepownerlist", _stepownerlist);
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append(_valueads).append(".action\u6267\u884C\u5931\u8D25").toString(), e);
                _returnmap.put("exception", Integer.valueOf(_exception.actionfaild));
            }
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append(_valueads).append("\u53CD\u5C04\u5931\u8D25").toString(), e);
            _returnmap.put("exception", Integer.valueOf(_exception.classfaild));
        }
        return _returnmap;
    }

    public Map judgeSpecialApproval(int type, Map map)
    {
        WorkflowException _exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _returnmap = new HashMap();
        Map _mainmap = _workbase.findParaByEndNameAndKey("ResultDeal", type);
        String _valueads = StringUtil.safeToString(_mainmap.get("valueads"), "");
        int _exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int _examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        try
        {
            IWorkflowConditionAndResult _workresult = (IWorkflowConditionAndResult)Class.forName(_valueads).newInstance();
            try
            {
                Map _returnmap2 = new HashMap();
                if(_workresult.condition(map, _returnmap2))
                {
                    _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap2);
                    Object _key;
                    Object _value;
                    for(Iterator i$ = _returnmap2.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
                    {
                        _key = i$.next();
                        _value = _returnmap2.get(_key);
                    }

                    int _result = _workresult.result(map, _returnmap);
                    Object _key;
                    Object _value;
                    for(Iterator i$ = _returnmap.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
                    {
                        _key = i$.next();
                        _value = _returnmap.get(_key);
                    }

                    _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap);
                    _returnmap.put("condition", Integer.valueOf(_result));
                } else
                {
                    Object _key;
                    Object _value;
                    for(Iterator i$ = _returnmap2.keySet().iterator(); i$.hasNext(); map.put(_key, _value))
                    {
                        _key = i$.next();
                        _value = _returnmap2.get(_key);
                    }

                    _workapp.saveParaMap(_exampleid, _examplestepid, 0, _returnmap2);
                    _returnmap.put("condition", Integer.valueOf(-1));
                }
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append(_valueads).append(".action\u6267\u884C\u5931\u8D25").toString(), e);
                _returnmap.put("exception", Integer.valueOf(_exception.actionfaild));
            }
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append(_valueads).append("\u53CD\u5C04\u5931\u8D25").toString(), e);
            _returnmap.put("exception", Integer.valueOf(_exception.classfaild));
        }
        return _returnmap;
    }

    private Map getFontOwner(int type, int examplestepid)
    {
        WorkflowException _exception = new WorkflowException();
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        Map _returnmap = new HashMap();
        List _resultlist = new ArrayList();
        List _list = _workapp.getExampleOwnerListByStepid(examplestepid, -1);
        for(int i = 0; i < _list.size(); i++)
        {
            Map _map = (Map)_list.get(i);
            int _status = NumberTool.convertMapKeyToInt(_map, "status", Integer.valueOf(0)).intValue();
            if(_status != WorkflowStepOwnerStatus.WF_OWNER_PROCESS.getEnumItemValue())
                continue;
            int _ownerid = NumberTool.convertMapKeyToInt(_map, "ownerid", Integer.valueOf(0)).intValue();
            if(_ownerid != 0)
                _resultlist.add(_map);
        }

        if(_resultlist.size() > 0)
        {
            Map _map = (Map)_resultlist.get(0);
            int _ownerid = NumberTool.convertMapKeyToInt(_map, "ownerid", Integer.valueOf(0)).intValue();
            int _preownerid = NumberTool.convertMapKeyToInt(_map, "preownerid", Integer.valueOf(0)).intValue();
            if(_preownerid != 0)
                _returnmap.put("ownerid", Integer.valueOf(_preownerid));
            else
                _returnmap.put("ownerid", Integer.valueOf(_ownerid));
            return _returnmap;
        }
        List _list2 = _workapp.getFrontExampleStepList(examplestepid);
        for(int i = 0; i < _list2.size(); i++)
        {
            Map _map = (Map)_list2.get(i);
            int _examplestepid = NumberTool.convertMapKeyToInt(_map, "prestepid", Integer.valueOf(0)).intValue();
            if(_examplestepid <= 0)
                continue;
            Map _map2 = getFontOwner(type, _examplestepid);
            int _ownerid = NumberTool.convertMapKeyToInt(_map2, "ownerid", Integer.valueOf(0)).intValue();
            if(_ownerid > 0)
            {
                _returnmap.put("ownerid", Integer.valueOf(_ownerid));
                return _returnmap;
            }
        }

        return _returnmap;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowInServiceImpl);

}
