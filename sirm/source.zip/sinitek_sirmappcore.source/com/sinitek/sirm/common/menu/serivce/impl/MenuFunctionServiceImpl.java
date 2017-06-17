// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MenuFunctionServiceImpl.java

package com.sinitek.sirm.common.menu.serivce.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.menu.entity.IMenuFunctionRela;
import com.sinitek.sirm.common.menu.serivce.IMenuFunctionService;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import com.sinitek.spirit.web.sysmenu.SysMenuContext;
import java.util.*;

public class MenuFunctionServiceImpl extends MetaDBContextSupport
    implements IMenuFunctionService
{

    public MenuFunctionServiceImpl()
    {
    }

    public void saveMenuFunction(IMenuFunctionRela menuFunctionRela)
    {
        save(menuFunctionRela);
    }

    public IMenuFunctionRela getMenuFunctionByMenuId(int menuId)
    {
        String sql = "MenuId=:MenuId";
        Map param = new HashMap();
        param.put("MenuId", Integer.valueOf(menuId));
        List list = getMetaDBContext().query("MENUFUNCTIONRELA", sql, param);
        return list.size() <= 0 ? null : (IMenuFunctionRela)list.get(0);
    }

    public List findMenu(int menuid)
    {
        return getMetaDBContext().query(com/sinitek/spirit/web/sysmenu/ISysMenu, (new StringBuilder()).append("parentid = '").append(menuid).append("' order by ord").toString(), Collections.emptyMap());
    }

    public List findAllMenu(int menuid)
    {
        List menus = new LinkedList();
        List subMenus = SysMenuContext.getInstance().getChildren(menuid);
        if(subMenus == null)
            return new LinkedList();
        menus.addAll(subMenus);
        ISysMenu menu;
        for(Iterator i$ = subMenus.iterator(); i$.hasNext(); menus.addAll(findAllMenu(menu.getId())))
            menu = (ISysMenu)i$.next();

        return menus;
    }

    public List findAllParentMenu(int menuid)
    {
        List menus = new LinkedList();
        ISysMenu parentroot = SysMenuContext.getInstance().getParent(menuid);
        if(parentroot == null)
        {
            return new LinkedList();
        } else
        {
            menus.add(parentroot);
            menus.addAll(findAllParentMenu(parentroot.getId()));
            return menus;
        }
    }

    public ISysMenu getSysMenuById(int menuId)
    {
        return SysMenuContext.getInstance().getMenuItem(menuId);
    }
}
