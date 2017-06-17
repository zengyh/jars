// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgPropertyServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.org.busin.entity.IOrgProperty;
import com.sinitek.sirm.org.busin.service.IOrgPropertyService;
import java.util.List;

public class OrgPropertyServiceImpl extends AbstractMetaDBContextSupport
    implements IOrgPropertyService
{

    public OrgPropertyServiceImpl()
    {
    }

    public void saveOrgProperty(IOrgProperty orgProperty)
    {
        orgProperty.save();
    }

    public IOrgProperty getOrgProperty(int objid)
    {
        IOrgProperty orgProperty = null;
        if(objid != 0)
            orgProperty = (IOrgProperty)getMetaDBContext().get("ORGPROPERTY", objid);
        return orgProperty;
    }

    public IOrgProperty getOrgPropertyByOrgId(String orgId)
    {
        IOrgProperty orgProperty = null;
        if(orgId != null && !"".equals(orgId))
        {
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGPROPERTY", " orgId=:orgId");
            iMetaDBQuery.setParameter("orgId", orgId);
            List orgPropertyList = iMetaDBQuery.getResult();
            if(orgPropertyList.size() > 0)
                orgProperty = (IOrgProperty)orgPropertyList.get(0);
        }
        return orgProperty;
    }

    public void deleteOrgProperty(int objid)
    {
        IOrgProperty orgProperty = getOrgProperty(objid);
        if(orgProperty != null)
            orgProperty.delete();
    }

    public void deleteOrgPropertyByOrgId(String orgId)
    {
        IOrgProperty orgProperty = getOrgPropertyByOrgId(orgId);
        if(orgProperty != null)
            orgProperty.delete();
    }
}
