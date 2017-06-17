// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CurdEntityAction.java

package com.sinitek.spirit.webcontrol.autogen;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class CurdEntityAction
    implements ITableAware
{

    public CurdEntityAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        Map paras = new HashMap();
        StringBuilder querySql = new StringBuilder("select a.entityname as objid ,a.entityname, a.entityinfo, b.catalogname\n  from metadb_entity a, metadb_entitycatalog b\n where a.catalogkey = b.catalogkey\n");
        String entityName = (String)map.get("entityname");
        if(entityName != null)
            entityName = entityName.toUpperCase();
        QueryUtils.buildLike("a.entityinfo", (String)map.get("entityinfo"), querySql, paras);
        QueryUtils.buildLike("a.entityname", entityName, querySql, paras);
        IMetaDBQuery result = (new MetaDBTemplate()).createSqlQuery(querySql.toString());
        if(!paras.isEmpty())
            result.setParameters(paras);
        return result;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return null;
    }
}
