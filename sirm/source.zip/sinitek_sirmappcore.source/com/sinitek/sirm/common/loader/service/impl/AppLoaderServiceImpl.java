// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLoaderServiceImpl.java

package com.sinitek.sirm.common.loader.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.loader.service.IAppLoaderService;
import java.util.HashMap;
import java.util.List;

public class AppLoaderServiceImpl extends MetaDBContextSupport
    implements IAppLoaderService
{

    public AppLoaderServiceImpl()
    {
    }

    public List getAllAppLoaders()
    {
        return getMetaDBContext().query("APPLOADER", "enabled=1 order by priority,objid", new HashMap());
    }
}
