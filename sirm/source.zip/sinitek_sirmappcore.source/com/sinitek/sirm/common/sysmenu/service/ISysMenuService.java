// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISysMenuService.java

package com.sinitek.sirm.common.sysmenu.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.List;

public interface ISysMenuService
{

    public abstract void saveSysMenu(ISysMenu isysmenu);

    public abstract void delSysMenusById(int i);

    public abstract void delAllSysMenus();

    public abstract void updateSysMenuByOrd(int i, int j);

    public abstract boolean findSysMenuParenById(Integer integer);

    public abstract ISysMenu getSysMenuById(int i);

    public abstract ISysMenu getMaxSysMenu(int i);

    public abstract List findSysMenuByParentId(int i);

    public abstract List findSysMenuByParentIdAndOrd(int i, int j, int k);

    public abstract List findUrlIsNotNullSysMenu();

    public abstract IMetaDBQuery findMenuAuthAllOrg(int i);

    public abstract ISysMenu findMenuByUrl(String s);
}
