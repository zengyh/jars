// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IFunctionService.java

package com.sinitek.sirm.common.function.service;

import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.function.entity.IFunctionGroup;
import com.sinitek.sirm.common.function.entity.IFunctionInfo;
import java.util.List;

public interface IFunctionService
{

    public abstract IMetaDBQuery findAllFunctionInfo(int i);

    public abstract int getMaxInfoSort(int i);

    public abstract boolean checkFuntion(int i, String s, String s1, int j);

    public abstract void saveFunctionInfo(IFunctionInfo ifunctioninfo);

    public abstract void deleteFunctionInfo(int i);

    public abstract List findAllFunctionGroup(int i);

    public abstract boolean checkFuntionGroup(int i, String s, int j);

    public abstract void saveFunctionGroup(IFunctionGroup ifunctiongroup);

    public abstract void deleteFunctionGroup(int i);

    public abstract List findFunctionGroupByName(String s);

    public abstract IMetaObject getGroupById(int i);

    public abstract IMetaObject getFunctionById(int i);

    public abstract int getMaxGroupSort(int i);

    public abstract List findAllFunction();

    public abstract IFunctionInfo getFunctionByCode(String s);

    public abstract boolean checkFunctionByCodeAndOrgId(String s, String s1);

    public abstract List getAllFunctionGroup();

    public abstract List findAllFunctionInfos(int i);
}
