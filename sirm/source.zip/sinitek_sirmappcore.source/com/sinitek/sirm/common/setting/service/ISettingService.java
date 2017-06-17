// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISettingService.java

package com.sinitek.sirm.common.setting.service;

import com.sinitek.sirm.common.setting.entity.IGroupSetting;
import com.sinitek.sirm.common.setting.entity.ISetting;
import java.util.List;

public interface ISettingService
{

    public abstract ISetting getSetting(String s, String s1);

    public abstract List findAllSettings();

    public abstract List findSettingsByModule(String s);

    public abstract void saveSetting(ISetting isetting);

    public abstract void deleteSetting(ISetting isetting);

    public abstract Integer saveSeting(ISetting isetting);

    public abstract void delSettingByObjid(String s);

    public abstract List findSettingModule();

    public abstract List findGroupSettingByCode(String s);

    public abstract List getGroupSettingList();

    public abstract List findGroupSettings();

    public abstract void updateGroupSettings(List list);

    public abstract void insertGroupSetting(IGroupSetting igroupsetting);

    public abstract void saveSetting(String s, String s1, String s2);

    public abstract ISetting getSettingById(int i);
}
