// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowSpecialServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.special.IWorkflowSync;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class WorkflowSpecialServiceImpl extends MetaDBContextSupport
    implements IWorkflowSpecialService
{

    public WorkflowSpecialServiceImpl()
    {
    }

    public int doSpecialOwner(int ownerid)
    {
        int specialownermark = NumberTool.convertMapKeyToInt(SpecialMap, "1", Integer.valueOf(-1)).intValue();
        if(specialownermark == -1)
        {
            List showlist = WorkflowServiceFactory.getWorkflowBaseService().findParaListByNameAndType("WorkflowSpecial", 0);
            for(int i = 0; i < showlist.size(); i++)
            {
                Map _showmap = (Map)showlist.get(i);
                String _key = StringUtil.safeToString(_showmap.get("key"), "-1");
                String _value = StringUtil.safeToString(_showmap.get("value"), "");
                SpecialMap.put(_key, _value);
            }

            specialownermark = NumberTool.convertMapKeyToInt(SpecialMap, "1", Integer.valueOf(-1)).intValue();
            LOGGER.info((new StringBuilder()).append("\u5F53\u524D\u540C\u6B65\u6807\u8BB0\uFF1A").append(specialownermark).toString());
            if(specialownermark == -1)
            {
                SpecialMap.put("0", Integer.valueOf(0));
                specialownermark = 0;
            }
        }
        if(specialownermark == 1)
        {
            String _localhost = StringUtil.safeToString(SpecialMap.get("2"), "");
            String _webaddress = StringUtil.safeToString(SpecialMap.get("1001"), "");
            String _specialclass = StringUtil.safeToString(SpecialMap.get("100"), "");
            try
            {
                IWorkflowSync _worksync = (IWorkflowSync)Class.forName(_specialclass).newInstance();
                Map map = new HashMap();
                map.put("ownerids", Integer.valueOf(ownerid));
                map.put("localhost", _localhost);
                map.put("webaddress", _webaddress);
                Map remap = _worksync.syncTask(map);
            }
            catch(Exception e)
            {
                LOGGER.warn((new StringBuilder()).append(_specialclass).append("\u53CD\u5C04\u5931\u8D25").toString());
                return -1;
            }
        }
        return 0;
    }

    public String addStepCode()
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        long startTime = System.nanoTime();
        Map historystep = _workbase.getHistoryStepMap(-1, 0, 0);
        List steplist = _workbase.getStepNoCode();
        LOGGER.info((new StringBuilder()).append("\u9700\u8981\u8865\u5145\u7684\u6B65\u9AA4\u6570\u636E\u4E00\u5171\u6709").append(steplist.size()).append("\u6761").toString());
        for(int i = 0; i < steplist.size(); i++)
        {
            Map stepmap = (Map)steplist.get(i);
            int stepid = NumberTool.convertMapKeyToInt(stepmap, "objid", Integer.valueOf(0)).intValue();
            if(stepid != 0)
            {
                IWorkflowProcessStep step = _workbase.loadProcessStep(stepid);
                if(step == null || step.size() == 0)
                    continue;
                int stepcode1 = stepid;
                int stepcode2 = 0;
                do
                {
                    stepcode2 = NumberTool.convertMapKeyToInt(historystep, (new StringBuilder()).append("").append(stepcode1).toString(), Integer.valueOf(0)).intValue();
                    if(stepcode2 == 0)
                        break;
                    stepcode1 = stepcode2;
                } while(true);
                step.setStepCode(Integer.valueOf(stepcode1));
                _workbase.saveProcessStep(step);
            }
            if(i % 100 == 99)
                LOGGER.info((new StringBuilder()).append("\u8865\u5145\u6B65\u9AA4\u6570\u636E\u5DF2\u5B8C\u6210").append(i + 1).append("\u6761").toString());
        }

        LOGGER.info("\u8865\u5145\u6B65\u9AA4\u6570\u636E\u5168\u90E8\u5B8C\u6210");
        long endTime = System.nanoTime();
        return (new StringBuilder()).append("\u8865\u5145\u6B65\u9AA4\u6570\u636E\u5168\u90E8\u5B8C\u6210\uFF0C\u8017\u65F6\uFF1A").append(TimeUnit.NANOSECONDS.toMinutes(endTime - startTime)).append("\u5206").toString();
    }

    public void changeAgents()
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        List list = _workbase.getAgentsList("", 1);
        LOGGER.info((new StringBuilder()).append("\u9700\u8981\u8C03\u6574\u7684\u4EE3\u7406\u6570\u636E\u4E00\u5171\u6709").append(list.size()).append("\u6761").toString());
        for(int i = 0; i < list.size(); i++)
        {
            int savemark = 0;
            Map map = (Map)list.get(i);
            int agentsid = NumberTool.convertMapKeyToInt(map, "objid", Integer.valueOf(0)).intValue();
            IWorkflowAgents agents = _workbase.loadAgents(agentsid);
            int processid = agents.getProcessId().intValue();
            if(processid != 0)
            {
                IWorkflowProcess process = _workbase.loadProcess(processid);
                int syscode = NumberTool.safeToInteger(process.getSysCode(), Integer.valueOf(0)).intValue();
                if(syscode != 0 && syscode != processid)
                {
                    agents.setProcessId(Integer.valueOf(syscode));
                    savemark++;
                }
            }
            int stepid = agents.getProcessStepId().intValue();
            if(stepid != 0)
            {
                IWorkflowProcessStep step = _workbase.loadProcessStep(stepid);
                int stepcode = NumberTool.safeToInteger(step.getStepCode(), Integer.valueOf(0)).intValue();
                if(stepcode != 0 && stepcode != stepid)
                {
                    agents.setProcessStepId(Integer.valueOf(stepcode));
                    savemark++;
                }
            }
            if(savemark > 0)
                _workbase.saveAgents(agents);
            if(i % 100 == 99)
                LOGGER.info((new StringBuilder()).append("\u8C03\u6574\u4EE3\u7406\u6570\u636E\u5DF2\u5B8C\u6210").append(i + 1).append("\u6761").toString());
        }

        LOGGER.info("\u8C03\u6574\u7684\u4EE3\u7406\u6570\u636E\u5B8C\u6210");
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/IWorkflowSpecialService);
    private static Map SpecialMap = new HashMap();

}
