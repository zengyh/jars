// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMenuFunctionService.java

package com.sinitek.sirm.common.menu.serivce;

import com.sinitek.sirm.common.menu.entity.IMenuFunctionRela;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.List;

public interface IMenuFunctionService
{

    public abstract void saveMenuFunction(IMenuFunctionRela imenufunctionrela);

    public abstract IMenuFunctionRela getMenuFunctionByMenuId(int i);

    public abstract List findMenu(int i);

    public abstract List findAllMenu(int i);

    public abstract List findAllParentMenu(int i);

    public abstract ISysMenu getSysMenuById(int i);
}
