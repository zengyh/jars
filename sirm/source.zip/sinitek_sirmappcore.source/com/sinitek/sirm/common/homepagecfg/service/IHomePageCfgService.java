// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IHomePageCfgService.java

package com.sinitek.sirm.common.homepagecfg.service;

import com.sinitek.sirm.common.homepagecfg.entity.IHomePageCfg;
import java.util.List;

public interface IHomePageCfgService
{

    public abstract void saveHomePageCfg(IHomePageCfg ihomepagecfg);

    public abstract void deleteHomePageCfg(IHomePageCfg ihomepagecfg);

    public abstract List findHomePageCfg(String s, int i, int j);

    public abstract List findHomePageCfgByOrgId(String s);

    public abstract IHomePageCfg getHomePageCfg(String s, int i);

    public abstract List findOldHomePageCfg();
}
