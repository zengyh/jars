// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RightSearchServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.org.busin.service.IRightSearchService;
import java.util.List;

public class RightSearchServiceImpl extends AbstractMetaDBContextSupport
    implements IRightSearchService
{

    public RightSearchServiceImpl()
    {
    }

    public List searchAllAuthedObjects()
    {
        IMetaDBQuery _query = getMetaDBContext().createQuery("RIGHTAUTH", "1=1");
        return _query.getResult();
    }
}
