// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonServiceFactory.java

package com.sinitek.sirm.common;

import com.sinitek.base.metadb.support.MetaDBContextSupportFactory;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.cache.service.ICacheInfoService;
import com.sinitek.sirm.common.engine.event.service.IEventManagerService;
import com.sinitek.sirm.common.function.service.IFunctionService;
import com.sinitek.sirm.common.homepagecfg.service.IHomePageCfgService;
import com.sinitek.sirm.common.loader.service.IAppLoaderService;
import com.sinitek.sirm.common.log.service.IBusinLogService;
import com.sinitek.sirm.common.maillogon.service.IEmailLogonService;
import com.sinitek.sirm.common.menu.serivce.IMenuFunctionService;
import com.sinitek.sirm.common.message.service.ISirmMessageService;
import com.sinitek.sirm.common.metadb.MetaDBFactory;
import com.sinitek.sirm.common.quartz.service.IJobQuartzRealService;
import com.sinitek.sirm.common.quartz.service.IJobQuartzService;
import com.sinitek.sirm.common.quartz.service.ISchedulerService;
import com.sinitek.sirm.common.quartz.service.ISirmJobExecuteLogService;
import com.sinitek.sirm.common.searchfilter.service.ISearchFilterService;
import com.sinitek.sirm.common.setting.service.IEntitySettingService;
import com.sinitek.sirm.common.setting.service.ISettingSearchService;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.sirmenum.service.ISirmEnumService;
import com.sinitek.sirm.common.supportcenter.service.ISupportCenterService;
import com.sinitek.sirm.common.sysmenu.service.ISysMenuService;
import com.sinitek.sirm.common.taginfo.service.ITagInfoService;
import com.sinitek.sirm.common.treemenu.service.ITreeMenuService;
import com.sinitek.sirm.common.um.service.IUserUIConfigService;

public class CommonServiceFactory
{

    public CommonServiceFactory()
    {
    }

    private static MetaDBContextSupportFactory getFactory()
    {
        return MetaDBFactory.getFactory("sirmappcore-common.xml");
    }

    public static IAttachmentService getAttachmentService()
    {
        return (IAttachmentService)getFactory().getContextSupport(com/sinitek/sirm/common/attachment/service/IAttachmentService);
    }

    public static ISettingService getSettingService()
    {
        return (ISettingService)getFactory().getContextSupport(com/sinitek/sirm/common/setting/service/ISettingService);
    }

    public static IEntitySettingService getEntitySettingService()
    {
        return (IEntitySettingService)getFactory().getContextSupport(com/sinitek/sirm/common/setting/service/IEntitySettingService);
    }

    public static ISearchFilterService getSearchFilterService()
    {
        return (ISearchFilterService)getFactory().getContextSupport(com/sinitek/sirm/common/searchfilter/service/ISearchFilterService);
    }

    public static ISirmEnumService getSirmEnumService()
    {
        return (ISirmEnumService)getFactory().getContextSupport(com/sinitek/sirm/common/sirmenum/service/ISirmEnumService);
    }

    public static ISysMenuService getSysMenuService()
    {
        return (ISysMenuService)getFactory().getContextSupport(com/sinitek/sirm/common/sysmenu/service/ISysMenuService);
    }

    public static ISchedulerService getSchedulerService()
    {
        return (ISchedulerService)getFactory().getContextSupport(com/sinitek/sirm/common/quartz/service/ISchedulerService);
    }

    public static IJobQuartzRealService getJobQuartzRealService()
    {
        return (IJobQuartzRealService)getFactory().getContextSupport(com/sinitek/sirm/common/quartz/service/IJobQuartzRealService);
    }

    public static IFunctionService getFunctionService()
    {
        return (IFunctionService)getFactory().getContextSupport(com/sinitek/sirm/common/function/service/IFunctionService);
    }

    public static IBusinLogService getBusinLogService()
    {
        return (IBusinLogService)getFactory().getContextSupport(com/sinitek/sirm/common/log/service/IBusinLogService);
    }

    public static IMenuFunctionService getMenuFunctionService()
    {
        return (IMenuFunctionService)getFactory().getContextSupport(com/sinitek/sirm/common/menu/serivce/IMenuFunctionService);
    }

    public static IJobQuartzService getJobQuartzService()
    {
        return (IJobQuartzService)getFactory().getContextSupport(com/sinitek/sirm/common/quartz/service/IJobQuartzService);
    }

    public static ICacheInfoService getCacheInfoService()
    {
        return (ICacheInfoService)getFactory().getContextSupport(com/sinitek/sirm/common/cache/service/ICacheInfoService);
    }

    public static IUserUIConfigService getUserUIConfigService()
    {
        return (IUserUIConfigService)getFactory().getContextSupport(com/sinitek/sirm/common/um/service/IUserUIConfigService);
    }

    public static IAppLoaderService getAppLoaderService()
    {
        return (IAppLoaderService)getFactory().getContextSupport(com/sinitek/sirm/common/loader/service/IAppLoaderService);
    }

    public static IEventManagerService getEventManagerService()
    {
        return (IEventManagerService)getFactory().getContextSupport(com/sinitek/sirm/common/engine/event/service/IEventManagerService);
    }

    public static IHomePageCfgService getHomePageCfgService()
    {
        return (IHomePageCfgService)getFactory().getContextSupport(com/sinitek/sirm/common/homepagecfg/service/IHomePageCfgService);
    }

    public static ISettingSearchService getSettingSearchService()
    {
        return (ISettingSearchService)getFactory().getContextSupport(com/sinitek/sirm/common/setting/service/ISettingSearchService);
    }

    public static ISupportCenterService getSupportCenterService()
    {
        return (ISupportCenterService)getFactory().getContextSupport(com/sinitek/sirm/common/supportcenter/service/ISupportCenterService);
    }

    public static ISirmMessageService getSirmMessageService()
    {
        return (ISirmMessageService)getFactory().getContextSupport(com/sinitek/sirm/common/message/service/ISirmMessageService);
    }

    public static ITagInfoService getTagInfoService()
    {
        return (ITagInfoService)getFactory().getContextSupport(com/sinitek/sirm/common/taginfo/service/ITagInfoService);
    }

    public static ISirmJobExecuteLogService getSirmJobExecuteLogService()
    {
        return (ISirmJobExecuteLogService)getFactory().getContextSupport(com/sinitek/sirm/common/quartz/service/ISirmJobExecuteLogService);
    }

    public static ITreeMenuService getTreeMenuService()
    {
        return (ITreeMenuService)getFactory().getContextSupport(com/sinitek/sirm/common/treemenu/service/ITreeMenuService);
    }

    public static IEmailLogonService getEmailLogonService()
    {
        return (IEmailLogonService)getFactory().getContextSupport(com/sinitek/sirm/common/maillogon/service/IEmailLogonService);
    }

    public static ICalendarService getCalendarService()
    {
        return (ICalendarService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/ICalendarService);
    }

    public static ISendMessageService getSendMessageService()
    {
        return (ISendMessageService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/ISendMessageService);
    }

    public static IMessageService getMessageService()
    {
        return (IMessageService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IMessageService);
    }

    public static IMessageEngineService getMessageEngineService()
    {
        return (IMessageEngineService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IMessageEngineService);
    }

    public static IReceiveMessageService getReceiveMessageService()
    {
        return (IReceiveMessageService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IReceiveMessageService);
    }

    public static ISendMessageContentService getSendMessageContentService()
    {
        return (ISendMessageContentService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/ISendMessageContentService);
    }

    public static ISendMessageDetailService getSendMessageDetailService()
    {
        return (ISendMessageDetailService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/ISendMessageDetailService);
    }

    public static IHolidaysService getHolidaysService()
    {
        return (IHolidaysService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IHolidaysService);
    }
}
