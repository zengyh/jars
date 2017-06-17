// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITreeMenuService.java

package com.sinitek.sirm.common.treemenu.service;

import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.List;

public interface ITreeMenuService
{

    public abstract List findNodeListByParentId(int i);

    public abstract List findNodeListByName(String s);

    public abstract List findParentNodeById(int i);

    public abstract ISysMenu getSysMenu(int i);

    public abstract void saveSysMenu(ISysMenu isysmenu);

    public abstract void deleteSysMenu(int i);
}
