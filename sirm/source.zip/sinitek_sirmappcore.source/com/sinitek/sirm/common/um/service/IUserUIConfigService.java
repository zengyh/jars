// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserUIConfigService.java

package com.sinitek.sirm.common.um.service;

import com.sinitek.sirm.common.um.entity.IUserUIConfig;
import com.sinitek.sirm.common.um.enums.UserUIConfigCatalog;
import java.util.List;

public interface IUserUIConfigService
{

    public abstract void save(IUserUIConfig iuseruiconfig);

    public abstract void save(String s, String s1, String s2, UserUIConfigCatalog useruiconfigcatalog);

    public abstract void save(String s, String s1, String s2, UserUIConfigCatalog useruiconfigcatalog, Class class1);

    public abstract IUserUIConfig get(String s, String s1, UserUIConfigCatalog useruiconfigcatalog);

    public abstract IUserUIConfig get(String s, String s1, UserUIConfigCatalog useruiconfigcatalog, Class class1);

    public abstract List find(String s, String s1, UserUIConfigCatalog useruiconfigcatalog);

    public abstract List find(String s, String s1, UserUIConfigCatalog useruiconfigcatalog, Class class1);
}
