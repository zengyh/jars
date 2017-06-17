// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgRelationService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.org.busin.entity.IOrgRelation;
import com.sinitek.sirm.org.busin.entity.IOrgRelationScheme;
import java.util.List;
import java.util.Map;

public interface IOrgRelationService
{

    public abstract IMetaDBQuery findAllRelationScheme();

    public abstract IOrgRelationScheme getRelationSchemeById(int i);

    public abstract boolean checkRelationSchemeByCodeAndName(String s, String s1, int i);

    public abstract void saveRelationScheme(IOrgRelationScheme iorgrelationscheme);

    public abstract void delRelationScheme(int i);

    public abstract List findAllRelation(int i);

    public abstract List getRelationByOrgId(String s);

    public abstract List getRelationByOrgId(String s, String s1);

    public abstract List getRelationByParentId(int i, int j);

    public abstract List findParentsByRelation(int i, int j);

    public abstract List findChildrenByRelation(int i, int j);

    public abstract IOrgRelation getRelationById(int i);

    public abstract void saveRelation(IOrgRelation iorgrelation);

    public abstract void delRelation(int i);

    public abstract void deleteOrgRelation(int i, int j);

    public abstract IMetaDBQuery findOrgRelationByParam(Map map);
}
