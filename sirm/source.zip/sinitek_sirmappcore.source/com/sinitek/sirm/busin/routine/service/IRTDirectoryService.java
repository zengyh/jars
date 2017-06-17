// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDirectoryService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.IRTDirectory;
import com.sinitek.sirm.busin.routine.entity.IRTDirectoryAppend;
import java.util.List;
import java.util.Map;

public interface IRTDirectoryService
{

    public abstract List findAllDirectory(int i, int j);

    public abstract List findAllDirectoryByParentId(int i, int j, int k, String s);

    public abstract List findAllParentDirectory(int i);

    public abstract List findAllChildrenDirectory(int i);

    public abstract IRTDirectory getDirectoryById(int i);

    public abstract IRTDirectoryAppend getDirectoryAppendById(int i);

    public abstract IRTDirectoryAppend getDirectoryAppendByDirectoryId(int i);

    public abstract void deleteDirectoryAppend(IRTDirectoryAppend irtdirectoryappend);

    public abstract void saveDirectory(IRTDirectory irtdirectory);

    public abstract void saveDirectoryAppend(IRTDirectoryAppend irtdirectoryappend);

    public abstract List getDirectoryByOrgid(String s);

    public abstract List getDirectoryDetailById(int i);

    public abstract List searchCompanyDirByParentId(int i);

    public abstract IMetaDBQuery searchCompanyDoc(Map map);

    public abstract List getAllParentDirByDocid(int i);

    public abstract IMetaDBQuery searchAllCompanyDirAuthEmpByDirId(int i);

    public abstract IMetaDBQuery searchAllCompanyDirDocAuthByDirId(int i);

    public abstract IMetaDBQuery searchCompanyDocAuthByDocid(int i);

    public abstract IRTDirectory getDirectoryByNamePId(String s, int i);
}
