// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TagInfoServiceImpl.java

package com.sinitek.sirm.common.taginfo.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.taginfo.entity.ISirmTagInfo;
import com.sinitek.sirm.common.taginfo.entity.ISirmTagRela;
import com.sinitek.sirm.common.taginfo.service.ITagInfoService;
import java.util.*;

public class TagInfoServiceImpl extends MetaDBContextSupport
    implements ITagInfoService
{

    public TagInfoServiceImpl()
    {
    }

    public void saveTagInfo(ISirmTagInfo sirmTagInfo)
    {
        sirmTagInfo.save();
    }

    public void deleteTagInfo(ISirmTagInfo sirmTagInfo)
    {
        sirmTagInfo.delete();
    }

    public ISirmTagInfo getTagInfo(Integer tagid)
    {
        return (ISirmTagInfo)getMetaDBContext().get(com/sinitek/sirm/common/taginfo/entity/ISirmTagInfo, tagid.intValue());
    }

    public void saveTagRela(ISirmTagRela sirmTagRela)
    {
        sirmTagRela.save();
    }

    public void deleteTagRela(ISirmTagRela sirmTagRela)
    {
        sirmTagRela.delete();
    }

    public ISirmTagRela getTagRela(Integer tagRelaId)
    {
        return (ISirmTagRela)getMetaDBContext().get(com/sinitek/sirm/common/taginfo/entity/ISirmTagRela, tagRelaId.intValue());
    }

    public ISirmTagRela getTagRelaByTagId(Integer tagId)
    {
        String sql = " tagid = :tagid";
        Map paramMap = new HashMap();
        paramMap.put("tagid", tagId);
        List tagRelaList = getMetaDBContext().query(com/sinitek/sirm/common/taginfo/entity/ISirmTagRela, sql, paramMap);
        if(tagRelaList.size() > 0)
            return (ISirmTagRela)tagRelaList.get(0);
        else
            return null;
    }

    public List findTaginfoList(Integer sourceid, Integer tagtype, String sourceEntity, Integer type)
    {
        StringBuffer sql = new StringBuffer("select t.*,a.objid as tagrelaid from sirm_taginfo t left join sirm_tagrela a on a.tagid=t.objid");
        sql.append(" where 1=1");
        sql.append(" and a.sourceentity = '").append(sourceEntity).append("'");
        sql.append(" and t.type=").append(type);
        sql.append(" and a.sourceid=").append(sourceid);
        sql.append(" and t.tagtype= ").append(tagtype);
        sql.append(" order by a.objid");
        return getMetaDBContext().createSqlQuery(sql.toString()).getResult();
    }

    public IMetaDBQuery findTagList(String tagname)
    {
        return getMetaDBContext().createSqlQuery((new StringBuilder()).append("select t.* from sirm_taginfo t where t.tagname like '%").append(tagname).append("%' or t.pinyin like '%").append(tagname).append("%'").toString());
    }

    public ISirmTagInfo findTagByTagName(String tagname)
    {
        String sql = " tagname=:tagname";
        Map paramMap = new HashMap();
        paramMap.put("tagname", tagname);
        List list = getMetaDBContext().query(com/sinitek/sirm/common/taginfo/entity/ISirmTagInfo, sql, paramMap);
        if(list.size() > 0)
            return (ISirmTagInfo)list.get(0);
        else
            return null;
    }

    public List findRagRelaList(Integer tagid, Integer sourceid, String sourceEntity)
    {
        String sql = " tagid = :tagid and sourceid = :sourceid and sourceentity = :sourceentity";
        Map paramMap = new HashMap();
        paramMap.put("tagid", tagid);
        paramMap.put("sourceid", sourceid);
        paramMap.put("sourceentity", sourceEntity);
        List tagRelaList = getMetaDBContext().query(com/sinitek/sirm/common/taginfo/entity/ISirmTagRela, sql, paramMap);
        return tagRelaList;
    }
}
