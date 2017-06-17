// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowRuleServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.rule;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.rule:
//            ProcessRuleQuery, IWorkflowRuleService, ProcessRuleInfoQuery

public class WorkflowRuleServiceImpl extends MetaDBContextSupport
    implements IWorkflowRuleService
{

    public WorkflowRuleServiceImpl()
    {
    }

    public IMetaDBQuery findProcessRule(ProcessRuleQuery query)
    {
        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append("select pr.objid, pr.targetType, pr.targetId, pr.targetAdd, pr.status, pr.brief, \r\n");
        sb.append("pr.ruleId, pri.ruleName, pri.info \r\n");
        sb.append("from WF_ProcessRule pr \r\n");
        sb.append("left join WF_ProcessRuleInfo pri on pr.ruleId = pri.objid \r\n");
        sb.append("where pr.status != 100 \r\n");
        if(query.getTargetType() != null)
        {
            sb.append("and pr.targetType = :targetType \r\n");
            params.put("targetType", query.getTargetType());
        }
        if(query.getTargetId() != null)
        {
            sb.append("and pr.targetId = :targetId \r\n");
            params.put("targetId", query.getTargetId());
        }
        if(query.getRuleId() != null)
        {
            sb.append("and pr.ruleId = :ruleId \r\n");
            params.put("ruleId", query.getRuleId());
        }
        if(query.getStatus() != null)
        {
            sb.append("and pr.status = :status \r\n");
            params.put("status", query.getStatus());
        }
        if(query.getBrief() != null)
        {
            sb.append("and pr.brief like :brief \r\n");
            params.put("brief", (new StringBuilder()).append("%").append(query.getBrief()).append("%").toString());
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sb.toString());
        iMetaDBQuery.setParameters(params);
        iMetaDBQuery.setOrderBy("pr.targetType, pr.targetId");
        return iMetaDBQuery;
    }

    public IMetaDBQuery findProcessRuleInfo(ProcessRuleInfoQuery query)
    {
        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append("select pri.objid, pri.ruleName, pri.ruleType, pri.info, pri.status, pri.brief \r\n");
        sb.append("from WF_ProcessRuleInfo pri \r\n");
        sb.append("where pri.status != 100 \r\n");
        if(query.getRuleName() != null)
        {
            sb.append("and pri.ruleName like :ruleName \r\n");
            params.put("ruleName", (new StringBuilder()).append("%").append(query.getRuleName()).append("%").toString());
        }
        if(query.getRuleType() != null)
        {
            sb.append("and pri.ruleType = :ruleType \r\n");
            params.put("ruleType", query.getRuleType());
        }
        if(query.getInfo() != null)
        {
            sb.append("and pri.info like :info \r\n");
            params.put("info", (new StringBuilder()).append("%").append(query.getInfo()).append("%").toString());
        }
        if(query.getStatus() != null)
        {
            sb.append("and pri.status = :status \r\n");
            params.put("status", query.getStatus());
        }
        if(query.getBrief() != null)
        {
            sb.append("and pri.brief like :brief \r\n");
            params.put("brief", (new StringBuilder()).append("%").append(query.getBrief()).append("%").toString());
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sb.toString());
        iMetaDBQuery.setParameters(params);
        iMetaDBQuery.setOrderBy("pri.ruleName");
        return iMetaDBQuery;
    }

    public IWorkflowProcessRule loadProcessRule(int objid)
    {
        if(objid <= 0)
            return new WorkflowProcessRuleImpl();
        else
            return (IWorkflowProcessRule)getMetaDBContext().get("WFPROCESSRULE", objid);
    }

    public void saveProcessRule(IWorkflowProcessRule object)
    {
        object.save();
    }

    public IWorkflowProcessRuleInfo loadProcessRuleInfo(int objid)
    {
        if(objid <= 0)
            return new WorkflowProcessRuleInfoImpl();
        else
            return (IWorkflowProcessRuleInfo)getMetaDBContext().get("WFPROCESSRULEINFO", objid);
    }

    public void saveProcessRuleInfo(IWorkflowProcessRuleInfo object)
    {
        object.save();
    }

    public Map pretreatedSubProcessStep(Map map)
    {
        int exampleStepId = NumberTool.safeToInteger(map.get("examplestepid"), Integer.valueOf(0)).intValue();
        if(exampleStepId == 0)
            return map;
        Map ownerMap = (Map)map.get("ownermap");
        if(ownerMap == null)
            return map;
        int condition = NumberTool.convertMapKeyToInt(ownerMap, "condition", Integer.valueOf(0)).intValue();
        if(condition != 1)
            return map;
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowExampleStep exampleStep = appService.loadExampleStep(exampleStepId);
        IWorkflowProcessStep processStep = baseService.loadProcessStep(exampleStep.getProcessStepId().intValue());
        IWorkflowProcess process = baseService.loadProcess(exampleStep.getProcessId().intValue());
        ProcessRuleQuery query = new ProcessRuleQuery();
        query.setTargetType("ProcessStep");
        query.setTargetId(processStep.getStepCode());
        query.setStatus(Integer.valueOf(0));
        List list = findProcessRule(query).getResult();
        if(list.size() == 0)
        {
            query.setTargetType("ProcessMain");
            query.setTargetId(NumberTool.safeToInteger(process.getSysCode(), Integer.valueOf(0)));
            list = findProcessRule(query).getResult();
            if(list.size() == 0)
            {
                query.setTargetType("ProcessType");
                query.setTargetId(process.getProcessType());
                list = findProcessRule(query).getResult();
                if(list.size() == 0)
                {
                    query.setTargetId(Integer.valueOf(0));
                    list = findProcessRule(query).getResult();
                    if(list.size() == 0)
                        return map;
                }
            }
        }
        Map ruleMap = (Map)list.get(0);
        String suffixOpinion = StringUtil.safeToString(ruleMap.get("info"), "");
        String approveOpinion = StringUtil.safeToString(ownerMap.get("approveopinion"), "");
        ownerMap.put("approveopinion", (new StringBuilder()).append(approveOpinion).append(suffixOpinion).toString());
        return map;
    }
}
