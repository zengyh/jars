// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITagInfoService.java

package com.sinitek.sirm.common.taginfo.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.taginfo.entity.ISirmTagInfo;
import com.sinitek.sirm.common.taginfo.entity.ISirmTagRela;
import java.util.List;

public interface ITagInfoService
{

    public abstract void saveTagInfo(ISirmTagInfo isirmtaginfo);

    public abstract void deleteTagInfo(ISirmTagInfo isirmtaginfo);

    public abstract ISirmTagInfo getTagInfo(Integer integer);

    public abstract void saveTagRela(ISirmTagRela isirmtagrela);

    public abstract void deleteTagRela(ISirmTagRela isirmtagrela);

    public abstract ISirmTagRela getTagRela(Integer integer);

    public abstract ISirmTagRela getTagRelaByTagId(Integer integer);

    public abstract List findTaginfoList(Integer integer, Integer integer1, String s, Integer integer2);

    public abstract IMetaDBQuery findTagList(String s);

    public abstract ISirmTagInfo findTagByTagName(String s);

    public abstract List findRagRelaList(Integer integer, Integer integer1, String s);
}
