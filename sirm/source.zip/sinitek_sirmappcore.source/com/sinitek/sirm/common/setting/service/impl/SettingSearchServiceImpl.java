// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingSearchServiceImpl.java

package com.sinitek.sirm.common.setting.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.setting.service.ISettingSearchService;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.HashMap;
import java.util.Map;

public class SettingSearchServiceImpl extends AbstractMetaDBContextSupport
    implements ISettingSearchService
{

    public SettingSearchServiceImpl()
    {
    }

    public IMetaDBQuery searchSettings(Map params)
    {
        String module = StringUtil.safeToString(params.get("module"), null);
        String name = StringUtil.safeToString(params.get("name"), null);
        Map sqlparams = new HashMap();
        IMetaDBQuery _metaDBQuery = null;
        StringBuilder _buffer = new StringBuilder();
        _buffer.append("select * from sirm_setting  t where 1=1  ");
        if(!"".equals(module) && null != module)
        {
            _buffer.append(" and t.module = :module");
            sqlparams.put("module", module);
        }
        if(!"".equals(name) && null != name)
        {
            _buffer.append(" and upper(t.name) like :name");
            sqlparams.put("name", (new StringBuilder()).append("%").append(name.toUpperCase()).append("%").toString());
        }
        _metaDBQuery = getMetaDBContext().createSqlQuery(_buffer.toString());
        _metaDBQuery.setParameters(sqlparams);
        return _metaDBQuery;
    }
}
