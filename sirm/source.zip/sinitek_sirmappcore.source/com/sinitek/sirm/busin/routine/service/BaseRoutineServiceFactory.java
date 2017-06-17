// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseRoutineServiceFactory.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.support.MetaDBContextSupportFactory;
import com.sinitek.sirm.common.metadb.MetaDBFactory;

// Referenced classes of package com.sinitek.sirm.busin.routine.service:
//            IRTDocumentsService, IRTDirectoryService, IRTDocumentAuthService

public class BaseRoutineServiceFactory
{

    public BaseRoutineServiceFactory()
    {
    }

    private static MetaDBContextSupportFactory getFactory()
    {
        return MetaDBFactory.getFactory("baseroutine.xml");
    }

    public static IRTDocumentsService getDocumentsService()
    {
        return (IRTDocumentsService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IRTDocumentsService);
    }

    public static IRTDirectoryService getDirectoryService()
    {
        return (IRTDirectoryService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IRTDirectoryService);
    }

    public static IRTDocumentAuthService getDocumentAuthService()
    {
        return (IRTDocumentAuthService)getFactory().getContextSupport(com/sinitek/sirm/busin/routine/service/IRTDocumentAuthService);
    }
}
