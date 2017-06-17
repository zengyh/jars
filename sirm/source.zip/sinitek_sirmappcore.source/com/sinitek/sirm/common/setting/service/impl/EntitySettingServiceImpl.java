// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntitySettingServiceImpl.java

package com.sinitek.sirm.common.setting.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.setting.entity.EntitySettingImpl;
import com.sinitek.sirm.common.setting.entity.IEntitySetting;
import com.sinitek.sirm.common.setting.service.IEntitySettingService;
import java.util.*;

public class EntitySettingServiceImpl extends AbstractMetaDBContextSupport
    implements IEntitySettingService
{

    public EntitySettingServiceImpl()
    {
    }

    public List findEntitySettingBySourceidSourceEntity(Integer sourceid, String sourceEntity)
    {
        Map _map = new HashMap();
        _map.put("sourceid", sourceid);
        _map.put("sourceentity", sourceEntity);
        IMetaDBQuery _metadbQuery = getMetaDBContext().createQuery("ENTITYSETTING", "sourceid=:sourceid and sourceentity=:sourceentity");
        _metadbQuery.setParameters(_map);
        return _metadbQuery.getResult();
    }

    public void saveEntitySetting(IEntitySetting entitySetting)
    {
        entitySetting.save();
    }

    public void saveEntitySetting(int sourceId, String sourceEntity, String name, String value)
    {
        IEntitySettingService _iess = CommonServiceFactory.getEntitySettingService();
        IEntitySetting _entitySetting = _iess.getEntitySetting(Integer.valueOf(sourceId), sourceEntity, name);
        if(null == _entitySetting)
            _entitySetting = new EntitySettingImpl();
        _entitySetting.setSourceid(Integer.valueOf(sourceId));
        _entitySetting.setSourceentity(sourceEntity);
        _entitySetting.setName(name);
        _entitySetting.setValue(value);
        _iess.saveEntitySetting(_entitySetting);
    }

    public void deleteEntitySetting(IEntitySetting entitySetting)
    {
        entitySetting.remove();
    }

    public IEntitySetting getEntitySetting(Integer sourceid, String sourceEntity, String name)
    {
        Map _map = new HashMap();
        _map.put("sourceid", sourceid);
        _map.put("sourceentity", sourceEntity);
        _map.put("name", name);
        List _list = getMetaDBContext().query("ENTITYSETTING", "sourceid=:sourceid and sourceentity=:sourceentity and name=:name", _map);
        if(_list != null && _list.size() > 0)
            return (IEntitySetting)_list.get(0);
        else
            return null;
    }

    public IEntitySetting getEntitySettingByValue(String value, String name, String sourceEntity)
    {
        Map _map = new HashMap();
        _map.put("value", value);
        _map.put("sourceentity", sourceEntity);
        _map.put("name", name);
        List _list = getMetaDBContext().query("ENTITYSETTING", "value=:value and sourceentity=:sourceentity and name=:name", _map);
        if(_list != null && _list.size() > 0)
            return (IEntitySetting)_list.get(0);
        else
            return null;
    }

    public void deleteAllReportTypeSetting(Integer typeId)
    {
        ISQLUpdater _isu = getMetaDBContext().createSqlUpdater("delete from sirm_entitysetting where sourceid=:typeid");
        _isu.setParameter("typeid", typeId);
        _isu.executeUpdate();
    }
}
