// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IQualifyInfoService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.org.busin.entity.IQualifyInfo;
import java.util.Map;

public interface IQualifyInfoService
{

    public abstract IMetaDBQuery findAllQualifyInfo(Map map);

    public abstract IQualifyInfo getQualifyInfoById(int i);

    public abstract IMetaDBQuery findQualifyInfoByOrgId(String s);

    public abstract void saveQualifyInfo(IQualifyInfo iqualifyinfo);

    public abstract void deleteQualifyInfo(int i);

    public abstract void deleteQualifyInfoByOrgId(String s);
}
