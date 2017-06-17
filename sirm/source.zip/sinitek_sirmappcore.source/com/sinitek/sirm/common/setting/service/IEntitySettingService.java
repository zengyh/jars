// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntitySettingService.java

package com.sinitek.sirm.common.setting.service;

import com.sinitek.sirm.common.setting.entity.IEntitySetting;
import java.util.List;

public interface IEntitySettingService
{

    public abstract List findEntitySettingBySourceidSourceEntity(Integer integer, String s);

    public abstract void saveEntitySetting(IEntitySetting ientitysetting);

    public abstract void saveEntitySetting(int i, String s, String s1, String s2);

    public abstract void deleteEntitySetting(IEntitySetting ientitysetting);

    public abstract IEntitySetting getEntitySetting(Integer integer, String s, String s1);

    public abstract IEntitySetting getEntitySettingByValue(String s, String s1, String s2);

    public abstract void deleteAllReportTypeSetting(Integer integer);
}
