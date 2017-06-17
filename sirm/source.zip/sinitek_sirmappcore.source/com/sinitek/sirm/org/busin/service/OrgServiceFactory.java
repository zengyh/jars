// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgServiceFactory.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.base.metadb.support.MetaDBContextSupportFactory;
import com.sinitek.sirm.common.metadb.MetaDBFactory;

// Referenced classes of package com.sinitek.sirm.org.busin.service:
//            IOrgService, IRightService, IRightSearchService, IUserSynchronousService, 
//            IUserSchemeService, IOrgRelationService, IQualifyInfoService, IOrgPropertyService, 
//            IImportEmpService, IPasswordService, IStatisticsService, IOrgMobileService

public class OrgServiceFactory
{

    public OrgServiceFactory()
    {
    }

    private static MetaDBContextSupportFactory getFactory()
    {
        return MetaDBFactory.getFactory("sirmappcore-org.xml");
    }

    public static IOrgService getOrgService()
    {
        return (IOrgService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IOrgService);
    }

    public static IRightService getRightService()
    {
        return (IRightService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IRightService);
    }

    public static IRightSearchService getRightSearchService()
    {
        return (IRightSearchService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IRightSearchService);
    }

    public static IUserSynchronousService getUserSynchronousService()
    {
        return (IUserSynchronousService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IUserSynchronousService);
    }

    public static IUserSchemeService getUserSehemeService()
    {
        return (IUserSchemeService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IUserSchemeService);
    }

    public static IOrgRelationService getOrgRelationService()
    {
        return (IOrgRelationService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IOrgRelationService);
    }

    public static IQualifyInfoService getQualifyInfoService()
    {
        return (IQualifyInfoService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IQualifyInfoService);
    }

    public static IOrgPropertyService getOrgPropertyService()
    {
        return (IOrgPropertyService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IOrgPropertyService);
    }

    public static IImportEmpService getImportEmpService()
    {
        return (IImportEmpService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IImportEmpService);
    }

    public static IPasswordService getPasswordService()
    {
        return (IPasswordService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IPasswordService);
    }

    public static IStatisticsService getStatisticsService()
    {
        return (IStatisticsService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IStatisticsService);
    }

    public static IOrgMobileService getOrgMobileService()
    {
        return (IOrgMobileService)getFactory().getContextSupport(com/sinitek/sirm/org/busin/service/IOrgMobileService);
    }
}
