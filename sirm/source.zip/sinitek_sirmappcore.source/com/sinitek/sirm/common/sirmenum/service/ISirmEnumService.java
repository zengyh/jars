// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmEnumService.java

package com.sinitek.sirm.common.sirmenum.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.sirmenum.entity.ISirmEnum;
import java.util.List;
import java.util.Map;

public interface ISirmEnumService
{

    public abstract Map findSirmEnumByCataLogAndType(String s, String s1);

    public abstract ISirmEnum getSirmEnumName(String s, String s1, Integer integer);

    public abstract List findSirmEnum(Map map);

    public abstract IMetaDBQuery findSirmEnum(String s, String s1, String s2);

    public abstract Integer saveSirmEnum(ISirmEnum isirmenum);

    public abstract void delSirmEnumById(String s);

    public abstract List findSirmEnum(String s, String s1);

    public abstract ISirmEnum getSirmEnumById(int i);
}
