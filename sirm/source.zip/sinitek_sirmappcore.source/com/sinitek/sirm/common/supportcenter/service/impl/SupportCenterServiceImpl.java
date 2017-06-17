// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SupportCenterServiceImpl.java

package com.sinitek.sirm.common.supportcenter.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.supportcenter.entity.ISupportCenter;
import com.sinitek.sirm.common.supportcenter.service.ISupportCenterService;

public class SupportCenterServiceImpl extends AbstractMetaDBContextSupport
    implements ISupportCenterService
{

    public SupportCenterServiceImpl()
    {
    }

    public ISupportCenter getSupportCenterById(int id)
    {
        return (ISupportCenter)getMetaDBContext().get("SUPPORTCENTER", id);
    }

    public IMetaDBQuery findSupportCenter()
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("SUPPORTCENTER", " 1=1");
        iMetaDBQuery.setOrderBy(" sort asc");
        return iMetaDBQuery;
    }

    public void saveSupportCenter(ISupportCenter supportCenter)
    {
        supportCenter.save();
    }

    public void delSupportCenter(int id)
    {
        ISupportCenter supportCenter = getSupportCenterById(id);
        if(supportCenter != null)
            supportCenter.delete();
    }
}
