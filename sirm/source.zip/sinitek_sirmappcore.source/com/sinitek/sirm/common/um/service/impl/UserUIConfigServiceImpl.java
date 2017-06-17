// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUIConfigServiceImpl.java

package com.sinitek.sirm.common.um.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.IMetaDBContextSupport;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.um.entity.IUserUIConfig;
import com.sinitek.sirm.common.um.entity.UserUIConfigImpl;
import com.sinitek.sirm.common.um.enums.UserUIConfigCatalog;
import com.sinitek.sirm.common.um.service.IUserUIConfigService;
import java.util.*;

public class UserUIConfigServiceImpl extends MetaDBContextSupport
    implements IUserUIConfigService, IMetaDBContextSupport
{

    public UserUIConfigServiceImpl()
    {
    }

    public void save(IUserUIConfig cfg)
    {
        cfg.save();
    }

    public void save(String orgId, String name, String value, UserUIConfigCatalog catalog)
    {
        save(orgId, name, value, catalog, null);
    }

    public void save(String orgId, String name, String value, UserUIConfigCatalog catalog, Class clazz)
    {
        IUserUIConfig cfg = new UserUIConfigImpl();
        cfg.setOrgId(orgId);
        cfg.setName(name);
        cfg.setValue(value);
        cfg.setCatalog(catalog.name());
        if(clazz != null)
            cfg.setActionClass(clazz.getName());
        save(cfg);
    }

    private IMetaDBQuery query(String hql)
    {
        return getMetaDBContext().createQuery(com/sinitek/sirm/common/um/entity/IUserUIConfig, hql);
    }

    private IMetaDBQuery query(String hql, Map params)
    {
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/common/um/entity/UserUIConfigImpl, hql);
        query.setParameters(params);
        return query;
    }

    public IUserUIConfig get(String orgId, String name, UserUIConfigCatalog catalog)
    {
        List list = find(orgId, name, catalog);
        return list.size() <= 0 ? null : (IUserUIConfig)list.get(0);
    }

    public IUserUIConfig get(String orgId, String name, UserUIConfigCatalog catalog, Class clazz)
    {
        List list = find(orgId, name, catalog, clazz);
        return list.size() <= 0 ? null : (IUserUIConfig)list.get(0);
    }

    private Map getBaseParams(String orgId, String name, UserUIConfigCatalog catalog)
    {
        Map params = new HashMap();
        params.put("orgId", orgId);
        params.put("name", name);
        params.put("catalog", catalog.name());
        return params;
    }

    private String getBaseHql()
    {
        return "orgId=:orgId and catalog=:catalog and name=:name";
    }

    public List find(String orgId, String name, UserUIConfigCatalog catalog)
    {
        return query(getBaseHql(), getBaseParams(orgId, name, catalog)).getResult();
    }

    public List find(String orgId, String name, UserUIConfigCatalog catalog, Class clazz)
    {
        Map params = getBaseParams(orgId, name, catalog);
        params.put("clazz", clazz.getName());
        return query((new StringBuilder()).append(getBaseHql()).append(" and actionClass=:clazz").toString(), params).getResult();
    }
}
