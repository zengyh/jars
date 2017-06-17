// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysMenuServiceImpl.java

package com.sinitek.sirm.common.sysmenu.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.sysmenu.service.ISysMenuService;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import com.sinitek.spirit.web.sysmenu.SysMenuContext;
import java.util.*;

public class SysMenuServiceImpl extends AbstractMetaDBContextSupport
    implements ISysMenuService
{

    public SysMenuServiceImpl()
    {
    }

    public void saveSysMenu(ISysMenu sysMenu)
    {
        ISysMenu upsysMenu = null;
        if(sysMenu.getId() != 0)
            try
            {
                upsysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", sysMenu.getId());
                if(upsysMenu != null)
                {
                    upsysMenu.setDescription(sysMenu.getDescription());
                    upsysMenu.setUrl(sysMenu.getUrl());
                    upsysMenu.setName(sysMenu.getName());
                    upsysMenu.setOrd(sysMenu.getOrd());
                    upsysMenu.setParentId(sysMenu.getParentId());
                    upsysMenu.save();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        else
            sysMenu.save();
    }

    public void delSysMenusById(int id)
    {
        ISysMenu sysMenu = null;
        try
        {
            sysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", id);
            if(sysMenu != null)
            {
                OrgServiceFactory.getRightService().deleteRightAuth(String.valueOf(sysMenu.getId()), "SPRT_SYSMENU", new String[] {
                    "ACCESS"
                });
                sysMenu.remove();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void delAllSysMenus()
    {
        ISQLUpdater sqlupdate = getMetaDBContext().createSqlUpdater("delete sprt_sysmenu");
        sqlupdate.executeUpdate();
        OrgServiceFactory.getRightService().deleteRightAuth("SPRT_SYSMENU", new String[] {
            "ACCESS"
        });
    }

    public void updateSysMenuByOrd(int ord, int objid)
    {
        ISysMenu upsysMenu = null;
        try
        {
            upsysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", objid);
            if(upsysMenu != null)
            {
                upsysMenu.setOrd(Integer.valueOf(ord));
                upsysMenu.save();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean findSysMenuParenById(Integer objid)
    {
        boolean result = false;
        ISysMenu sysMenu = null;
        try
        {
            sysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", objid.intValue());
            if(sysMenu != null)
            {
                sysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", sysMenu.getParentId().intValue());
                if(sysMenu != null)
                {
                    sysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", sysMenu.getParentId().intValue());
                    if(sysMenu != null)
                    {
                        sysMenu = (ISysMenu)getMetaDBContext().get("SPRT_SYSMENU", sysMenu.getParentId().intValue());
                        if(sysMenu != null)
                            result = true;
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public ISysMenu getSysMenuById(int menuId)
    {
        return SysMenuContext.getInstance().getMenuItem(menuId);
    }

    public ISysMenu getMaxSysMenu(int parentId)
    {
        ISysMenu sysMenu = null;
        List menuList = new ArrayList();
        if(parentId == 0)
            menuList = SysMenuContext.getInstance().getTopMenus("DEFAULT");
        else
            menuList = findSysMenuByParentId(parentId);
        int order = 0;
        Iterator i$ = menuList.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            ISysMenu menu = (ISysMenu)i$.next();
            if(menu.getOrd().intValue() > order)
            {
                order = menu.getOrd().intValue();
                sysMenu = menu;
            }
        } while(true);
        return sysMenu;
    }

    public List findSysMenuByParentId(int parentId)
    {
        List list = SysMenuContext.getInstance().getChildren(parentId);
        return ((List) (list != null ? list : new ArrayList()));
    }

    public List findSysMenuByParentIdAndOrd(int parentId, int oldOrd, int newOrd)
    {
        String sql = "";
        Map map = new HashMap();
        map.put("parentid", Integer.valueOf(parentId));
        if(oldOrd < newOrd)
        {
            sql = "parentid = :parentid and ord > :smallord and ord <= :bigord";
            map.put("smallord", Integer.valueOf(oldOrd));
            map.put("bigord", Integer.valueOf(newOrd));
        } else
        {
            sql = "parentid = :parentid and ord >= :smallord and ord < :bigord";
            map.put("smallord", Integer.valueOf(newOrd));
            map.put("bigord", Integer.valueOf(oldOrd));
        }
        List list = getMetaDBContext().query("SPRT_SYSMENU", sql, map);
        return ((List) (list != null ? list : new ArrayList()));
    }

    public List findUrlIsNotNullSysMenu()
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery("select s.*,(select name from sprt_sysmenu  where objid=s.parentid) as parentname from sprt_sysmenu s where s.url is not null  order by parentid");
        return iMetaDBQuery.getResult();
    }

    public IMetaDBQuery findMenuAuthAllOrg(int menuId)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, o.orgname, o.unittype from (\n");
        sql.append("  select distinct a.toobjectid orgid from sprt_orgrela a start with a.fromobjectid in (select t.authorgid from SPRT_RIGHTAUTH t where t.rightdefinekey = 'SPRT_SYSMENU' and t.rejectflag = 0 and t.objectkey = :menuid)\n");
        sql.append("  connect by prior a.toobjectid=a.fromobjectid  and a.toobjectid not in (select t.authorgid from SPRT_RIGHTAUTH t where t.rightdefinekey = 'SPRT_SYSMENU' and t.rejectflag = 1 and t.objectkey = :menuid)\n");
        sql.append(") a\n");
        sql.append("  left join SPRT_ORGOBJECT o on a.orgid = o.orgid\n");
        IMetaDBQuery mq = getMetaDBContext().createSqlQuery(sql.toString());
        mq.setParameter("menuid", Integer.valueOf(menuId));
        return mq;
    }

    public ISysMenu findMenuByUrl(String url)
    {
        IMetaDBQuery metaDBQuery = getMetaDBContext().createQuery("SPRT_SYSMENU", "url=:url");
        metaDBQuery.setParameter("url", url);
        List lists = metaDBQuery.getResult();
        if(lists.size() > 0)
            return (ISysMenu)lists.get(0);
        else
            return null;
    }
}
