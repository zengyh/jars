// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeMenuServiceImpl.java

package com.sinitek.sirm.common.treemenu.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.treemenu.service.ITreeMenuService;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class TreeMenuServiceImpl extends MetaDBContextSupport
    implements ITreeMenuService
{

    public TreeMenuServiceImpl()
    {
    }

    public List findNodeListByParentId(int parentId)
    {
        StringBuffer _sql = new StringBuffer();
        Map _param = new HashMap();
        _sql.append("Select objid, ord sort, parentid, name \n");
        _sql.append("   From SPRT_SYSMENU \n");
        _sql.append("Where 1=1 and parentid=:parentid\n");
        _param.put("parentid", Integer.valueOf(parentId));
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public List findNodeListByName(String name)
    {
        StringBuffer _sql = new StringBuffer();
        Map _param = new HashMap();
        _sql.append("Select objid, ord sort, parentid, name \n");
        _sql.append("   From SPRT_SYSMENU \n");
        _sql.append("Where 1=1 \n");
        if(StringUtils.isNotBlank(name))
        {
            _sql.append("   and ( upper(name) like :name \n");
            _sql.append("       or objid in (select objid from SPRT_SYSMENU where F_TRANS_PINYIN_CAPITAL(name) like :name) \n");
            _sql.append("       ) \n");
            _param.put("name", (new StringBuilder()).append("%").append(name.toUpperCase()).append("%").toString());
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public List findParentNodeById(int id)
    {
        StringBuffer _sql = new StringBuffer();
        Map _param = new HashMap();
        _sql.append("Select objid, ord sort, parentid, name \n");
        _sql.append("   From SPRT_SYSMENU \n");
        _sql.append("Where 1=1 and objid in ( \n");
        _sql.append("     Select parentid \n");
        _sql.append("       From SPRT_SYSMENU \n");
        _sql.append("     Where 1=1 \n");
        if(id > 0)
        {
            _sql.append("       and objid = :objid \n");
            _param.put("objid", Integer.valueOf(id));
        }
        _sql.append(") \n");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public ISysMenu getSysMenu(int id)
    {
        return (ISysMenu)getMetaDBContext().get(com/sinitek/spirit/web/sysmenu/ISysMenu, id);
    }

    public void saveSysMenu(ISysMenu node)
    {
        super.save(node);
    }

    public void deleteSysMenu(int id)
    {
        ISysMenu node = getSysMenu(id);
        super.remove(node);
    }
}
