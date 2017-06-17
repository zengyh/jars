// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowClaimServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepType;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import org.apache.commons.collections.MapUtils;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ClaimSubject, ClaimStep, ClaimOwner, ExampleTaskQuery, 
//            AskForSubmit, AskForMain, IWorkflowClaimService, AskForStart, 
//            IWorkflowClaimDaoService, ExampleAskForQuery

public class WorkflowClaimServiceImpl extends MetaDBContextSupport
    implements IWorkflowClaimService
{

    public WorkflowClaimServiceImpl()
    {
    }

    public ClaimSubject getClaimSubject(int objid)
    {
        IWorkflowAdvService advService = WorkflowServiceFactory.getWorkflowAdvService();
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        int processId = objid;
        List stepList = advService.getProcessStepList(processId);
        IWorkflowProcess process = baseService.loadProcess(processId);
        ClaimSubject claimSubject = new ClaimSubject(processId);
        int startId = 0;
        int editUrl = 0;
        Map memoryMap = new HashMap();
        for(int i = 0; i < stepList.size(); i++)
        {
            Map stepMap = (Map)stepList.get(i);
            int stepId = NumberTool.safeToInteger(stepMap.get("objid"), Integer.valueOf(0)).intValue();
            memoryMap.put(Integer.valueOf(stepId), stepMap);
            int stepTypeId;
            if(startId == 0)
            {
                stepTypeId = NumberTool.safeToInteger(stepMap.get("steptypeid"), Integer.valueOf(0)).intValue();
                if(stepTypeId == WorkflowStepType.WF_TYPE_START.getEnumItemValue())
                    startId = stepId;
                continue;
            }
            if(editUrl != 0)
                continue;
            stepTypeId = NumberTool.safeToInteger(stepMap.get("steptypeid"), Integer.valueOf(0)).intValue();
            if(stepTypeId == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
                editUrl = NumberTool.safeToInteger(stepMap.get("urlmark"), Integer.valueOf(0)).intValue();
        }

        List stepList2 = new ArrayList();
        for(int tempId = startId; tempId > 0;)
        {
            Map stepMap = (Map)memoryMap.get(Integer.valueOf(tempId));
            int stepTypeId = NumberTool.safeToInteger(stepMap.get("steptypeid"), Integer.valueOf(0)).intValue();
            if(stepTypeId == WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
                stepList2.add(stepMap);
            List linkList = ((List) (stepMap.get("linklist") != null ? (List)stepMap.get("linklist") : ((List) (new ArrayList()))));
            if(linkList.size() > 0)
            {
                Map linkMap = (Map)linkList.get(0);
                tempId = NumberTool.safeToInteger(linkMap.get("aftstepid"), Integer.valueOf(0)).intValue();
            } else
            {
                tempId = 0;
            }
        }

        for(int i = 0; i < stepList2.size(); i++)
        {
            Map stepMap = (Map)stepList2.get(i);
            int stepId = NumberTool.safeToInteger(stepMap.get("objid"), Integer.valueOf(0)).intValue();
            ClaimStep claimStep = new ClaimStep(stepId);
            List ownerList = ((List) (stepMap.get("ownerlist") != null ? (List)stepMap.get("ownerlist") : ((List) (new ArrayList()))));
            for(int j = 0; j < ownerList.size(); j++)
            {
                Map ownerMap = (Map)ownerList.get(j);
                int orgType = NumberTool.safeToInteger(ownerMap.get("orgtype"), Integer.valueOf(0)).intValue();
                if(orgType > 0)
                {
                    String orgName = StringUtil.safeToString(ownerMap.get("orgname"), "");
                    String orgId = StringUtil.safeToString(ownerMap.get("orgid"), "");
                    ClaimOwner owner = new ClaimOwner(orgId, orgType, orgName);
                    claimStep.addOwner(owner);
                }
                String name = StringUtil.safeToString(stepMap.get("name"), "");
                claimStep.setName(name);
                String judgeType = StringUtil.safeToString(stepMap.get("steptypeads"), "");
                claimStep.setJudgeType(judgeType);
                int judgeUrl = NumberTool.safeToInteger(stepMap.get("urlmark"), Integer.valueOf(0)).intValue();
                claimStep.setJudgeUrl(judgeUrl);
            }

            claimSubject.addStep(claimStep);
        }

        String processName = process.getName();
        claimSubject.setName(processName);
        String processBrief = process.getProcessBrief();
        if(processBrief == null)
            processBrief = "";
        claimSubject.setBrief(processBrief);
        int processType = process.getProcessType().intValue();
        claimSubject.setProcessType(processType);
        claimSubject.setEditUrl(editUrl);
        return claimSubject;
    }

    public ClaimSubject getClaimSubjectBySyscode(String syscode)
    {
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        Map processMap = baseService.getProcessBySyscode(syscode);
        int processId = NumberTool.safeToInteger(processMap.get("processid"), Integer.valueOf(0)).intValue();
        return getClaimSubject(processId);
    }

    public ClaimSubject getClaimSubjectByExampleId(int exampleId)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExample example = appService.loadExample(exampleId);
        return getClaimSubject(example.getProcessId().intValue());
    }

    public ClaimSubject getClaimSubjectByExampleOwnerId(int exampleOwnerId)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStepOwner exampleOwner = appService.loadExampleStepOwner(exampleOwnerId);
        return getClaimSubjectByExampleId(exampleOwner.getExampleId().intValue());
    }

    public void saveClaimSubject(ClaimSubject claimSubject)
    {
        IWorkflowAdvService advService = WorkflowServiceFactory.getWorkflowAdvService();
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        int processId = claimSubject.getProcessId();
        List stepList = advService.getProcessStepList(processId);
        IWorkflowProcess process = baseService.loadProcess(processId);
        process.setName(claimSubject.getName());
        process.setProcessBrief(claimSubject.getBrief());
        baseService.saveProcess(process);
        int startId = 0;
        int endId = 0;
        int submitId = 0;
        for(int i = 0; i < stepList.size(); i++)
        {
            Map stepMap = (Map)stepList.get(i);
            int stepId = NumberTool.safeToInteger(stepMap.get("objid"), Integer.valueOf(0)).intValue();
            advService.deleteProcessStep(stepId);
            IWorkflowProcessStep processStep = baseService.loadProcessStep(stepId);
            int stepTypeId = processStep.getStepTypeId().intValue();
            if(stepTypeId == WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue())
            {
                processStep.setStatus(Integer.valueOf(100));
                baseService.saveProcessStep(processStep);
                continue;
            }
            if(stepTypeId == WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue())
            {
                processStep.setUrlMark(Integer.valueOf(claimSubject.getEditUrl()));
                processStep.setStatus(Integer.valueOf(100));
                baseService.saveProcessStep(processStep);
                submitId = stepId;
                continue;
            }
            if(stepTypeId == WorkflowStepType.WF_TYPE_START.getEnumItemValue())
            {
                startId = stepId;
                continue;
            }
            if(stepTypeId == WorkflowStepType.WF_TYPE_END.getEnumItemValue())
                endId = stepId;
        }

        IWorkflowProcessStep processStep;
        if(startId == 0)
        {
            processStep = baseService.loadProcessStep(0);
            processStep.setName("\u5F00\u59CB\u6B65\u9AA4");
            processStep.setStatus(Integer.valueOf(1));
            processStep.setCondition(";;;;;0;");
            processStep.setSort(Integer.valueOf(1));
            processStep.setProcessId(Integer.valueOf(processId));
            processStep.setStepTypeId(Integer.valueOf(WorkflowStepType.WF_TYPE_START.getEnumItemValue()));
            startId = baseService.saveProcessStep(processStep);
        }
        if(endId == 0)
        {
            processStep = baseService.loadProcessStep(0);
            processStep.setName("\u7ED3\u675F\u6B65\u9AA4");
            processStep.setStatus(Integer.valueOf(1));
            processStep.setSort(Integer.valueOf(10000));
            processStep.setProcessId(Integer.valueOf(processId));
            processStep.setStepTypeId(Integer.valueOf(WorkflowStepType.WF_TYPE_END.getEnumItemValue()));
            endId = baseService.saveProcessStep(processStep);
        }
        if(submitId == 0)
        {
            processStep = baseService.loadProcessStep(0);
            processStep.setName("\u7F16\u8F91\u6B65\u9AA4");
            processStep.setStatus(Integer.valueOf(1));
            processStep.setCondition(";;;;;0;");
            processStep.setSort(Integer.valueOf(2));
            processStep.setProcessId(Integer.valueOf(processId));
            processStep.setStepTypeId(Integer.valueOf(WorkflowStepType.WF_TYPE_SUBMIT.getEnumItemValue()));
            processStep.setStepTypeAds("2");
            processStep.setUrlMark(Integer.valueOf(claimSubject.getEditUrl()));
            submitId = baseService.saveProcessStep(processStep);
        }
        processStep = baseService.loadProcessStep(submitId);
        processStep.setStatus(Integer.valueOf(1));
        baseService.saveProcessStep(processStep);
        IWorkflowProcessOwner owner = baseService.loadProcessOwner(0);
        owner.setValue(Integer.valueOf(1));
        owner.setOwnerGoto(Integer.valueOf(0));
        owner.setOwnerGotoId(Integer.valueOf(submitId));
        owner.setProcessId(Integer.valueOf(processId));
        owner.setStepId(Integer.valueOf(submitId));
        owner.setOrgId("2");
        owner.setOrgType(Integer.valueOf(-2));
        owner.setStatus(Integer.valueOf(1));
        baseService.saveProcessOwner(owner);
        List claimSteps = claimSubject.getSteps();
        for(int i = 0; i < claimSteps.size(); i++)
        {
            ClaimStep claimStep = (ClaimStep)claimSteps.get(i);
            IWorkflowProcessStep processStep = baseService.loadProcessStep(claimStep.getStepId());
            processStep.setName(claimStep.getName());
            processStep.setStatus(Integer.valueOf(1));
            processStep.setCondition(";;;;;0;");
            processStep.setSort(Integer.valueOf(3 + i));
            processStep.setProcessId(Integer.valueOf(processId));
            processStep.setStepTypeId(Integer.valueOf(WorkflowStepType.WF_TYPE_DEAL.getEnumItemValue()));
            processStep.setStepTypeAds(claimStep.getJudgeType());
            processStep.setUrlMark(Integer.valueOf(claimStep.getJudgeUrl()));
            int stepId = baseService.saveProcessStep(processStep);
            claimStep.setStepId(stepId);
            List claimOwners = claimStep.getOwners();
            for(int j = 0; j < claimOwners.size(); j++)
            {
                ClaimOwner claimOwner = (ClaimOwner)claimOwners.get(j);
                IWorkflowProcessOwner processOwner = baseService.loadProcessOwner(0);
                processOwner.setValue(Integer.valueOf(1));
                processOwner.setOwnerGoto(Integer.valueOf(0));
                processOwner.setOwnerGotoId(Integer.valueOf(stepId));
                processOwner.setProcessId(Integer.valueOf(processId));
                processOwner.setStepId(Integer.valueOf(stepId));
                processOwner.setOrgId(claimOwner.getOrgId());
                processOwner.setOrgType(Integer.valueOf(claimOwner.getOrgType()));
                processOwner.setStatus(Integer.valueOf(1));
                baseService.saveProcessOwner(processOwner);
            }

            IWorkflowProcessOwner owner = baseService.loadProcessOwner(0);
            owner.setValue(Integer.valueOf(1));
            owner.setOwnerGoto(Integer.valueOf(0));
            owner.setOwnerGotoId(Integer.valueOf(stepId));
            owner.setProcessId(Integer.valueOf(processId));
            owner.setStepId(Integer.valueOf(stepId));
            owner.setOrgId("2");
            owner.setOrgType(Integer.valueOf(-2));
            owner.setStatus(Integer.valueOf(1));
            baseService.saveProcessOwner(owner);
        }

        IWorkflowProcessStepLink stepLink1 = baseService.loadProcessStepLink(0);
        stepLink1.setAftStepId(Integer.valueOf(submitId));
        stepLink1.setProcessId(Integer.valueOf(processId));
        stepLink1.setSort(Integer.valueOf(1));
        stepLink1.setPreStepId(Integer.valueOf(startId));
        stepLink1.setStatus(Integer.valueOf(1));
        baseService.saveProcessStepLink(stepLink1);
        if(claimSteps.size() > 0)
        {
            IWorkflowProcessStepLink stepLink2 = baseService.loadProcessStepLink(0);
            stepLink2.setAftStepId(Integer.valueOf(((ClaimStep)claimSteps.get(0)).getStepId()));
            stepLink2.setProcessId(Integer.valueOf(processId));
            stepLink2.setSort(Integer.valueOf(1));
            stepLink2.setPreStepId(Integer.valueOf(submitId));
            stepLink2.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLink(stepLink2);
            for(int i = 0; i < claimSteps.size() - 1; i++)
            {
                ClaimStep claimStep1 = (ClaimStep)claimSteps.get(i);
                ClaimStep claimStep2 = (ClaimStep)claimSteps.get(i + 1);
                IWorkflowProcessStepLink stepLink3 = baseService.loadProcessStepLink(0);
                stepLink3.setAftStepId(Integer.valueOf(claimStep2.getStepId()));
                stepLink3.setProcessId(Integer.valueOf(processId));
                stepLink3.setSort(Integer.valueOf(1));
                stepLink3.setPreStepId(Integer.valueOf(claimStep1.getStepId()));
                stepLink3.setStatus(Integer.valueOf(1));
                baseService.saveProcessStepLink(stepLink3);
                IWorkflowProcessStepLink stepLink4 = baseService.loadProcessStepLink(0);
                stepLink4.setAftStepId(Integer.valueOf(submitId));
                stepLink4.setProcessId(Integer.valueOf(processId));
                stepLink4.setSort(Integer.valueOf(2));
                stepLink4.setPreStepId(Integer.valueOf(claimStep1.getStepId()));
                stepLink4.setStatus(Integer.valueOf(1));
                baseService.saveProcessStepLink(stepLink4);
                IWorkflowProcessStepLinkIf stepLinkIf1 = baseService.loadProcessStepLinkIf(0);
                stepLinkIf1.setLinkId(Integer.valueOf(stepLink3.getId()));
                stepLinkIf1.setProcessId(Integer.valueOf(processId));
                stepLinkIf1.setIfType(Integer.valueOf(2));
                stepLinkIf1.setStepId(Integer.valueOf(claimStep1.getStepId()));
                stepLinkIf1.setIfAnd(Integer.valueOf(1));
                stepLinkIf1.setIfAds("1");
                stepLinkIf1.setStatus(Integer.valueOf(1));
                baseService.saveProcessStepLinkIf(stepLinkIf1);
                IWorkflowProcessStepLinkIf stepLinkIf2 = baseService.loadProcessStepLinkIf(0);
                stepLinkIf2.setLinkId(Integer.valueOf(stepLink4.getId()));
                stepLinkIf2.setProcessId(Integer.valueOf(processId));
                stepLinkIf2.setIfType(Integer.valueOf(2));
                stepLinkIf2.setStepId(Integer.valueOf(claimStep1.getStepId()));
                stepLinkIf2.setIfAnd(Integer.valueOf(1));
                stepLinkIf2.setIfAds("2");
                stepLinkIf2.setStatus(Integer.valueOf(1));
                baseService.saveProcessStepLinkIf(stepLinkIf2);
            }

            ClaimStep claimStep3 = (ClaimStep)claimSteps.get(claimSteps.size() - 1);
            IWorkflowProcessStepLink stepLink5 = baseService.loadProcessStepLink(0);
            stepLink5.setAftStepId(Integer.valueOf(endId));
            stepLink5.setProcessId(Integer.valueOf(processId));
            stepLink5.setSort(Integer.valueOf(1));
            stepLink5.setPreStepId(Integer.valueOf(claimStep3.getStepId()));
            stepLink5.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLink(stepLink5);
            IWorkflowProcessStepLink stepLink6 = baseService.loadProcessStepLink(0);
            stepLink6.setAftStepId(Integer.valueOf(submitId));
            stepLink6.setProcessId(Integer.valueOf(processId));
            stepLink6.setSort(Integer.valueOf(2));
            stepLink6.setPreStepId(Integer.valueOf(claimStep3.getStepId()));
            stepLink6.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLink(stepLink6);
            IWorkflowProcessStepLinkIf stepLinkIf3 = baseService.loadProcessStepLinkIf(0);
            stepLinkIf3.setLinkId(Integer.valueOf(stepLink5.getId()));
            stepLinkIf3.setProcessId(Integer.valueOf(processId));
            stepLinkIf3.setIfType(Integer.valueOf(2));
            stepLinkIf3.setStepId(Integer.valueOf(claimStep3.getStepId()));
            stepLinkIf3.setIfAnd(Integer.valueOf(1));
            stepLinkIf3.setIfAds("1");
            stepLinkIf3.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLinkIf(stepLinkIf3);
            IWorkflowProcessStepLinkIf stepLinkIf4 = baseService.loadProcessStepLinkIf(0);
            stepLinkIf4.setLinkId(Integer.valueOf(stepLink6.getId()));
            stepLinkIf4.setProcessId(Integer.valueOf(processId));
            stepLinkIf4.setIfType(Integer.valueOf(2));
            stepLinkIf4.setStepId(Integer.valueOf(claimStep3.getStepId()));
            stepLinkIf4.setIfAnd(Integer.valueOf(1));
            stepLinkIf4.setIfAds("2");
            stepLinkIf4.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLinkIf(stepLinkIf4);
        } else
        {
            IWorkflowProcessStepLink stepLink2 = baseService.loadProcessStepLink(0);
            stepLink2.setAftStepId(Integer.valueOf(endId));
            stepLink2.setProcessId(Integer.valueOf(processId));
            stepLink2.setSort(Integer.valueOf(1));
            stepLink2.setPreStepId(Integer.valueOf(submitId));
            stepLink2.setStatus(Integer.valueOf(1));
            baseService.saveProcessStepLink(stepLink2);
        }
    }

    public IMetaDBQuery findExampleAskFors(ExampleAskForQuery query)
    {
        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append("select eaf.objid as askForId, eaf.startTime, e.processName, es.processStepName as stepName,\r\n");
        sb.append("so1.orgName as ownerName, so2.orgName as startName, so3.orgName as askerName, eaf.startTime, e.brief \r\n");
        sb.append("from WF_ExampleAskFor eaf \r\n");
        sb.append("left join WF_ExampleStepOwner eso on eaf.exampleOwnerId = eso.objid \r\n");
        sb.append("left join WF_Example e on eso.exampleid = e.objid \r\n");
        sb.append("left join WF_ExampleStep es on eso.examplestepid = es.objid \r\n");
        sb.append("left join sprt_orgobject so1 on so1.orgId = to_char(eaf.ownerId) \r\n");
        sb.append("left join sprt_orgobject so2 on so2.orgId = to_char(e.starterId) \r\n");
        sb.append("left join sprt_orgobject so3 on so3.orgId = to_char(eaf.askerId) \r\n");
        sb.append("where 1 = 1 \r\n");
        if(query.getOwnerId() != -1)
        {
            sb.append("and eaf.ownerId = :ownerId \r\n");
            params.put("ownerId", Integer.valueOf(query.getOwnerId()));
        }
        if(query.getStartId() != -1)
        {
            sb.append("and e.starterId = :startId \r\n");
            params.put("startId", Integer.valueOf(query.getStartId()));
        }
        if(query.getAskerId() != -1)
        {
            sb.append("and eaf.askerId = :askerId \r\n");
            params.put("askerId", Integer.valueOf(query.getAskerId()));
        }
        if(query.getProcessName() != null)
        {
            sb.append("and e.processName like :processName \r\n");
            params.put("processName", (new StringBuilder()).append("%").append(query.getProcessName()).append("%").toString());
        }
        if(query.getStepName() != null)
        {
            sb.append("and es.processStepName like :stepName \r\n");
            params.put("stepName", (new StringBuilder()).append("%").append(query.getStepName()).append("%").toString());
        }
        if(query.getBrief() != null)
        {
            sb.append("and e.brief like :brief \r\n");
            params.put("brief", (new StringBuilder()).append("%").append(query.getBrief()).append("%").toString());
        }
        if(query.getStatus() != -1)
        {
            sb.append("and eaf.status = :status \r\n");
            params.put("status", Integer.valueOf(query.getStatus()));
        }
        if(query.getStartTime1() != null)
        {
            sb.append("and eaf.startTime >= :startTime1 \r\n");
            params.put("startTime1", query.getStartTime1());
        }
        if(query.getStartTime2() != null)
        {
            sb.append("and eaf.startTime <= :startTime2 \r\n");
            params.put("startTime2", query.getStartTime2());
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sb.toString());
        iMetaDBQuery.setParameters(params);
        iMetaDBQuery.setOrderBy("eaf.startTime desc");
        return iMetaDBQuery;
    }

    public IMetaDBQuery findExampleTasks(ExampleTaskQuery query)
    {
        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append("select et.*, \r\n");
        sb.append("so1.orgName as orginerName, so2.orgName as dealerName \r\n");
        sb.append("from Wf_ExampleTask et \r\n");
        sb.append("left join sprt_orgobject so1 on so1.orgId = to_char(et.orginerId) \r\n");
        sb.append("left join sprt_orgobject so2 on so2.orgId = to_char(et.dealerId) \r\n");
        sb.append("where 1 = 1 \r\n");
        if(query.getSourceEntity() != null)
        {
            sb.append("and et.sourceEntity = :sourceEntity \r\n");
            params.put("sourceEntity", query.getSourceEntity());
        }
        if(query.getSourceId() != -1)
        {
            sb.append("and et.sourceId = :sourceId \r\n");
            params.put("sourceId", Integer.valueOf(query.getSourceId()));
        }
        if(query.getDealerId() != -1)
        {
            sb.append("and et.dealerId = :dealerId \r\n");
            params.put("dealerId", Integer.valueOf(query.getDealerId()));
        }
        if(query.getOrginerId() != -1)
        {
            sb.append("and et.orginerId = :orginerId \r\n");
            params.put("orginerId", Integer.valueOf(query.getOrginerId()));
        }
        if(query.getStatus() != -1)
            if(query.getStatus() == 0)
            {
                sb.append("and (et.status = 0 or et.status = 1) \r\n");
            } else
            {
                sb.append("and et.status = :status \r\n");
                params.put("status", Integer.valueOf(query.getStatus()));
            }
        if(query.getDescription() != null)
        {
            sb.append("and et.description like :description \r\n");
            params.put("description", (new StringBuilder()).append("%").append(query.getDescription()).append("%").toString());
        }
        if(query.getRemarks() != null)
        {
            sb.append("and et.remarks like :remarks \r\n");
            params.put("remarks", (new StringBuilder()).append("%").append(query.getRemarks()).append("%").toString());
        }
        if(query.getStartTime1() != null)
        {
            sb.append("and et.startTime >= :startTime1 \r\n");
            params.put("startTime1", query.getStartTime1());
        }
        if(query.getStartTime2() != null)
        {
            sb.append("and et.startTime <= :startTime2 \r\n");
            params.put("startTime2", query.getStartTime2());
        }
        if(query.getEndTime1() != null)
        {
            sb.append("and et.endTime >= :endTime1 \r\n");
            params.put("endTime1", query.getEndTime1());
        }
        if(query.getEndTime2() != null)
        {
            sb.append("and et.endTime >= :endTime2 \r\n");
            params.put("endTime2", query.getEndTime2());
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sb.toString());
        iMetaDBQuery.setParameters(params);
        if(query.getOrderBy() == null)
            iMetaDBQuery.setOrderBy("et.startTime desc");
        else
            iMetaDBQuery.setOrderBy(query.getOrderBy());
        return iMetaDBQuery;
    }

    public int getExampleTaskCount(int dealerid)
    {
        ExampleTaskQuery query = new ExampleTaskQuery();
        query.setDealerId(dealerid);
        query.setStatus(0);
        return WorkflowServiceFactory.getWorkflowClaimService().findExampleTasks(query).getResultCount();
    }

    public List getHomePageTasks(int dealerid)
    {
        ExampleTaskQuery query = new ExampleTaskQuery();
        query.setDealerId(dealerid);
        query.setStatus(0);
        List _tasklist = WorkflowServiceFactory.getWorkflowClaimService().findExampleTasks(query).getResult();
        List tasklist = new ArrayList();
        for(int i = 0; i < 6 && i < _tasklist.size(); i++)
        {
            Map _taskmap = (Map)_tasklist.get(i);
            Map taskmap = new HashMap();
            taskmap.put("taskid", Integer.valueOf(MapUtils.getIntValue(_taskmap, "objid", 0)));
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
        String _processstepname = StringUtil.safeToString(map.get("remarks"), "");
        _main.append("[").append(_processstepname).append("] ");
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
        String _processbrief = StringUtil.safeToString(map.get("description"), "");
        _main.append((new StringBuilder()).append("   ").append(_processbrief).toString());
        return _main.toString();
    }

    public void startAskFor(AskForStart forStart)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowClaimDaoService daoService = WorkflowServiceFactory.getWorkflowClaimDaoService();
        int exampleOwnerId = forStart.getExampleOwnerId();
        int askerId = 0;
        if(exampleOwnerId == 0)
        {
            IWorkflowExampleAskFor askFor = daoService.loadExampleAskFor(forStart.getAskForId());
            exampleOwnerId = askFor.getExampleOwnerId().intValue();
            askerId = askFor.getOwnerId().intValue();
        }
        IWorkflowExampleStepOwner exampleOwner = appService.loadExampleStepOwner(exampleOwnerId);
        if(askerId == 0)
            askerId = exampleOwner.getOwnerId().intValue();
        List ownerList = forStart.getOwnerList();
        for(int i = 0; i < ownerList.size(); i++)
        {
            int ownerId = NumberTool.safeToInteger(ownerList.get(i), Integer.valueOf(0)).intValue();
            IWorkflowExampleAskFor askFor = daoService.loadExampleAskFor(0);
            askFor.setExampleOwnerId(Integer.valueOf(exampleOwnerId));
            askFor.setOwnerId(Integer.valueOf(ownerId));
            askFor.setStatus(Integer.valueOf(0));
            askFor.setAskerId(Integer.valueOf(askerId));
            askFor.setStartTime(new Date());
            askFor.setParentId(Integer.valueOf(forStart.getAskForId()));
            daoService.saveExampleAskFor(askFor);
        }

    }

    public void submitAskFor(AskForSubmit forSubmit)
    {
        IWorkflowClaimDaoService daoService = WorkflowServiceFactory.getWorkflowClaimDaoService();
        IWorkflowExampleAskFor askFor = daoService.loadExampleAskFor(forSubmit.getAskForId());
        askFor.setStatus(Integer.valueOf(8));
        askFor.setApproveStatus(Integer.valueOf(forSubmit.getAskForStatus()));
        askFor.setOpinion(forSubmit.getOpinion());
        askFor.setBrief(forSubmit.getBrief());
        askFor.setApproveTime(new Date());
        daoService.saveExampleAskFor(askFor);
    }

    public AskForSubmit loadAskForSubmit(int askForId)
    {
        IWorkflowClaimDaoService daoService = WorkflowServiceFactory.getWorkflowClaimDaoService();
        IWorkflowExampleAskFor askFor = daoService.loadExampleAskFor(askForId);
        AskForSubmit forSubmit = new AskForSubmit(askForId);
        forSubmit.setAskForStatus(askFor.getApproveStatus());
        forSubmit.setOpinion(askFor.getOpinion());
        forSubmit.setBrief(askFor.getBrief());
        forSubmit.setApproveTime(askFor.getApproveTime());
        return forSubmit;
    }

    public List findAskForSubmits(int exampleOwnerId)
    {
        IOrgService orgService = OrgServiceFactory.getOrgService();
        String _hql = "exampleOwnerId=:exampleOwnerId order by objid";
        Map _map = new HashMap();
        _map.put("exampleOwnerId", Integer.valueOf(exampleOwnerId));
        List askForList = getMetaDBContext().query("WFEXAMPLEASKFOR", _hql, _map);
        List forSubmitList = new ArrayList();
        for(int i = 0; i < askForList.size(); i++)
        {
            IWorkflowExampleAskFor askFor = (IWorkflowExampleAskFor)askForList.get(i);
            AskForSubmit forSubmit = new AskForSubmit(askFor.getId());
            forSubmit.setAskForStatus(askFor.getApproveStatus());
            forSubmit.setOpinion(askFor.getOpinion());
            forSubmit.setBrief(askFor.getBrief());
            forSubmit.setApproveTime(askFor.getApproveTime());
            List employeeList = orgService.findEmployeesByOrgId((new StringBuilder()).append(askFor.getOwnerId()).append("").toString());
            if(employeeList.size() > 0)
                forSubmit.setOwnerName(((Employee)employeeList.get(0)).getEmpName());
            forSubmitList.add(forSubmit);
        }

        return forSubmitList;
    }

    public AskForMain loadAskForMain(int askForId)
    {
        IWorkflowClaimDaoService daoService = WorkflowServiceFactory.getWorkflowClaimDaoService();
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowExampleAskFor askFor = daoService.loadExampleAskFor(askForId);
        IWorkflowExampleStepOwner exampleOwner = appService.loadExampleStepOwner(askFor.getExampleOwnerId().intValue());
        IWorkflowExampleStep exampleStep = appService.loadExampleStep(exampleOwner.getExampleStepId().intValue());
        IWorkflowProcessStep processStep = baseService.loadProcessStep(exampleStep.getProcessStepId().intValue());
        List entryList = appService.getExampleEntrys(exampleOwner.getExampleId().intValue());
        StringBuilder nameSb = new StringBuilder();
        StringBuilder idSb = new StringBuilder();
        for(int i = 0; i < entryList.size(); i++)
        {
            if(nameSb.length() > 0)
            {
                nameSb.append(",");
                idSb.append(",");
            }
            Map entryMap = (Map)entryList.get(i);
            String sourceName = StringUtil.safeToString(entryMap.get("sourcename"), "");
            String sourceId = StringUtil.safeToString(entryMap.get("sourceid"), "");
            nameSb.append(sourceName);
            idSb.append(sourceId);
        }

        IWorkflowProcessUrl processUrl = baseService.loadProcessUrl(processStep.getUrlMark().intValue());
        String showUrl = processUrl.getViewUrl();
        if(showUrl == null || showUrl.length() == 0)
        {
            showUrl = processUrl.getShowUrl();
            if(showUrl == null || showUrl.length() == 0)
                showUrl = processUrl.getActionUrl();
        }
        StringBuilder urlSb = new StringBuilder();
        urlSb.append("../../..");
        if(!showUrl.startsWith("/"))
            urlSb.append("/");
        int mark = showUrl.indexOf("?");
        if(mark != -1)
            urlSb.append(showUrl.substring(0, mark - 1));
        else
            urlSb.append(showUrl);
        AskForMain forMain = new AskForMain(askForId);
        forMain.setSourceName(nameSb.toString());
        forMain.setSourceId(idSb.toString());
        forMain.setShowUrl(urlSb.toString());
        forMain.setExampleId(exampleOwner.getExampleId().intValue());
        forMain.setExampleStepId(exampleOwner.getExampleStepId().intValue());
        forMain.setExampleOwnerId(exampleOwner.getId());
        return forMain;
    }
}
