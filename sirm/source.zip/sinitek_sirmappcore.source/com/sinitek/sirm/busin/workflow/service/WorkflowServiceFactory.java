// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowServiceFactory.java

package com.sinitek.sirm.busin.workflow.service;

import com.sinitek.base.metadb.support.MetaDBContextSupportFactory;
import com.sinitek.sirm.busin.workflow.service.claim.IWorkflowClaimDaoService;
import com.sinitek.sirm.busin.workflow.service.claim.IWorkflowClaimService;
import com.sinitek.sirm.busin.workflow.service.drawprocess.IWorkflowDrawService;
import com.sinitek.sirm.busin.workflow.service.feature.IWorkflowFeatureService;
import com.sinitek.sirm.busin.workflow.service.rule.IWorkflowRuleService;
import com.sinitek.sirm.common.metadb.MetaDBFactory;
import com.sinitek.sirm.tool.workflow.special.IWorkflowMobileService;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service:
//            IWorkflowBaseService, IWorkflowAdvService, IWorkflowEngineService, IWorkflowSupportService, 
//            IWorkflowAppService, IWorkflowWebService, IWorkflowInService, IWorkflowAchieveService, 
//            IWorkflowTemplateService, IWorkflowTranslateService, IWorkflowExportService, IWorkflowIostreamService, 
//            IWorkflowSpecialService, IWorkflowModelService

public class WorkflowServiceFactory
{

    public WorkflowServiceFactory()
    {
    }

    private static MetaDBContextSupportFactory getMetaDBContextFactory()
    {
        return MetaDBFactory.getFactory("sirmappcore-workflow.xml");
    }

    public static IWorkflowBaseService getWorkflowBaseService()
    {
        return (IWorkflowBaseService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowBaseService);
    }

    public static IWorkflowAdvService getWorkflowAdvService()
    {
        return (IWorkflowAdvService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowAdvService);
    }

    public static IWorkflowEngineService getWorkflowEngineService()
    {
        return (IWorkflowEngineService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowEngineService);
    }

    public static IWorkflowSupportService getWorkflowSupportService()
    {
        return (IWorkflowSupportService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowSupportService);
    }

    public static IWorkflowAppService getWorkflowAppService()
    {
        return (IWorkflowAppService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowAppService);
    }

    public static IWorkflowWebService getWorkflowWebService()
    {
        return (IWorkflowWebService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowWebService);
    }

    public static IWorkflowInService getWorkflowInService()
    {
        return (IWorkflowInService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowInService);
    }

    public static IWorkflowAchieveService getWorkflowAchieveService()
    {
        return (IWorkflowAchieveService)getMetaDBContextFactory().getContextSupport("sirmWorkflowAchieveService");
    }

    public static IWorkflowTemplateService getWorkflowTemplateService()
    {
        return (IWorkflowTemplateService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowTemplateService);
    }

    public static IWorkflowTranslateService getWorkflowTranslateService()
    {
        return (IWorkflowTranslateService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowTranslateService);
    }

    public static IWorkflowExportService getWorkflowExportService()
    {
        return (IWorkflowExportService)getMetaDBContextFactory().getContextSupport("sirmWorkflowExportService");
    }

    public static IWorkflowIostreamService getWorkflowIostreamService()
    {
        return (IWorkflowIostreamService)getMetaDBContextFactory().getContextSupport("sirmWorkflowIostreamService");
    }

    public static IWorkflowSpecialService getWorkflowSpecialService()
    {
        return (IWorkflowSpecialService)getMetaDBContextFactory().getContextSupport("sirmWorkflowSpecialService");
    }

    public static IWorkflowDrawService getWorkflowDrawService()
    {
        return (IWorkflowDrawService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/drawprocess/IWorkflowDrawService);
    }

    public static IWorkflowClaimService getWorkflowClaimService()
    {
        return (IWorkflowClaimService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/claim/IWorkflowClaimService);
    }

    public static IWorkflowFeatureService getWorkflowFeatureService()
    {
        return (IWorkflowFeatureService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/feature/IWorkflowFeatureService);
    }

    public static IWorkflowMobileService getWorkflowMobileService()
    {
        return (IWorkflowMobileService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/tool/workflow/special/IWorkflowMobileService);
    }

    public static IWorkflowClaimDaoService getWorkflowClaimDaoService()
    {
        return (IWorkflowClaimDaoService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/claim/IWorkflowClaimDaoService);
    }

    public static IWorkflowModelService getWorkflowModelService()
    {
        return (IWorkflowModelService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/IWorkflowModelService);
    }

    public static IWorkflowExportService getWorkflowExportService2()
    {
        return (IWorkflowExportService)getMetaDBContextFactory().getContextSupport("sirmWorkflowExportService2");
    }

    public static IWorkflowRuleService getWorkflowRuleService()
    {
        return (IWorkflowRuleService)getMetaDBContextFactory().getContextSupport(com/sinitek/sirm/busin/workflow/service/rule/IWorkflowRuleService);
    }
}
