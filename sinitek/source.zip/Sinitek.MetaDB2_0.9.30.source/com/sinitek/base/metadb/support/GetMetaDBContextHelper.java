// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetMetaDBContextHelper.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.util.Stack;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.support:
//            MetaDBContextSupportException, ContextInvocationInfo, ContextInvocationInfoContainer

public class GetMetaDBContextHelper
{

    public GetMetaDBContextHelper()
    {
    }

    public static IMetaDBContext getMetaDBContext()
    {
        ContextInvocationInfo his = ContextInvocationInfoContainer.getCurrentContext();
        if(his == null || his.getMethodStack().size() == 0)
            throw new MetaDBContextSupportException("0001");
        if(his.isCurrentMethodOpenContext())
            return (IMetaDBContext)his.getContextStack().peek();
        else
            throw new MetaDBContextSupportException("0019");
    }

    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
