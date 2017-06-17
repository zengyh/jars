// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserSchemeService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.sirm.org.busin.entity.IUserScheme;
import com.sinitek.sirm.org.busin.entity.IUserSchemeRela;
import java.util.List;

public interface IUserSchemeService
{

    public abstract List findUserScheme(IUserScheme iuserscheme);

    public abstract IUserScheme getUserScheme(int i);

    public abstract void saveUserScheme(IUserScheme iuserscheme);

    public abstract void delUserScheme(int i);

    public abstract IUserScheme getUserSchemeByCode(String s);

    public abstract List findUserSchemeRelaBySchemeId(int i);

    public abstract List findUserSchemeRelaBySchemeCode(String s);

    public abstract List findUserSchemeRelaByPath(String s);

    public abstract void saveUserSchemeRela(IUserSchemeRela iuserschemerela);

    public abstract void delUserSchemeRela(IUserSchemeRela iuserschemerela);

    public abstract IUserSchemeRela getUserSchemeRelaById(int i);

    public abstract List findEmployeeBySchemeCode(String s);

    public abstract List findDepartmentsBySchemeCode(String s);
}
