// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingServiceImpl.java

package com.sinitek.sirm.common.setting.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.setting.entity.*;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.utils.EncryptUtil;
import java.util.*;

public class SettingServiceImpl extends MetaDBContextSupport
    implements ISettingService
{

    public SettingServiceImpl()
    {
    }

    public ISetting getSetting(String module, String name)
    {
        String _sql = "module=:module and name=:name";
        Map _map = new HashMap();
        _map.put("module", module);
        _map.put("name", name);
        List _settings = getMetaDBContext().query("SETTING", _sql, _map);
        ISetting setting = null;
        if(_settings.size() > 0)
        {
            setting = (ISetting)_settings.get(0);
            if(setting != null && setting.getEncryptionFlag() != null && setting.getEncryptionFlag().intValue() == 1)
                setting.setValue(EncryptUtil.decryptDES(setting.getValue(), "20121221"));
        }
        return setting;
    }

    public List findAllSettings()
    {
        Map _map = new HashMap();
        List _settings = getMetaDBContext().query("SETTING", "", _map);
        return _settings;
    }

    public List findSettingsByModule(String module)
    {
        String _sql = "module=:module";
        Map _map = new HashMap();
        _map.put("module", module);
        return getMetaDBContext().query("SETTING", _sql, _map);
    }

    public void saveSetting(ISetting setting)
    {
        super.save(setting);
    }

    public void deleteSetting(ISetting setting)
    {
        super.remove(setting);
    }

    public Integer saveSeting(ISetting seting)
    {
        if(!"".equals(Integer.valueOf(seting.getId())) && 0 != seting.getId())
        {
            ISetting obj = null;
            try
            {
                obj = (ISetting)getMetaDBContext().get("SETTING", seting.getId());
                if(obj != null)
                {
                    String encryString = ";PASSWORD;SWITCHINGPWD;MAIL_SMTPPWD;";
                    String value = seting.getValue();
                    int encryFlag = 0;
                    if(encryString.contains((new StringBuilder()).append(";").append(obj.getName()).append(";").toString()))
                    {
                        value = EncryptUtil.encryptDES(value, "20121221");
                        encryFlag = 1;
                    }
                    obj.setValue(value);
                    obj.setEncryptionFlag(Integer.valueOf(encryFlag));
                    obj.setModule(seting.getModule());
                    obj.setName(seting.getName());
                    obj.setBrief(seting.getBrief());
                    obj.setUpdateTimeStamp(new Date());
                    save(obj);
                }
            }
            catch(Exception e) { }
        } else
        {
            save(seting);
        }
        return Integer.valueOf(seting.getId());
    }

    public void delSettingByObjid(String objid)
    {
        ISetting obj = (ISetting)getMetaDBContext().get("SETTING", Integer.parseInt(objid));
        if(obj != null)
            remove(obj);
    }

    public List findSettingModule()
    {
        String sql = "select distinct module from sirm_setting order by module ";
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(sql);
        return _metaDBQuery.getResult();
    }

    public List findGroupSettingByCode(String catalogCode)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(catalogCode != null && !"".equals(catalogCode))
        {
            sql.append(" and catalogCode=:catalogCode");
            map.put("catalogCode", catalogCode);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("SIRMGROUPSETTING", sql.toString());
        iMetaDBQuery.setParameters(map);
        iMetaDBQuery.setDefaultOrderBy("sort asc");
        return iMetaDBQuery.getResult();
    }

    public List getGroupSettingList()
    {
        StringBuilder sql = new StringBuilder("1=1");
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("SIRMGROUPSETTING", sql.toString());
        iMetaDBQuery.setDefaultOrderBy("sort asc");
        return iMetaDBQuery.getResult();
    }

    public List findGroupSettings()
    {
        String sql = "select * from sirm_groupsetting  order by sort asc";
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql);
        return metaDBQuery.getResult();
    }

    public void updateGroupSettings(List groupSettings)
    {
        if(groupSettings == null || groupSettings.size() <= 0)
            return;
        String sql;
        for(Iterator i$ = groupSettings.iterator(); i$.hasNext(); getMetaDBContext().createSqlUpdater(sql).executeUpdate())
        {
            IGroupSetting groupSetting = (IGroupSetting)i$.next();
            int sort = groupSetting.getSort().intValue();
            int objid = groupSetting.getId();
            sql = (new StringBuilder()).append("update sirm_groupsetting s set s.sort = ").append(sort).append(" where s.objid = ").append(objid).toString();
        }

    }

    public void insertGroupSetting(IGroupSetting groupSetting)
    {
        groupSetting.save();
    }

    public void saveSetting(String module, String name, String value)
    {
        ISetting setting = getSetting(module, name);
        if(setting != null)
        {
            setting.setValue(value);
        } else
        {
            setting = new SettingImpl();
            setting.setModule(module);
            setting.setName(name);
            setting.setValue(value);
        }
        saveSetting(setting);
    }

    public ISetting getSettingById(int objid)
    {
        return (ISetting)getMetaDBContext().get("SETTING", objid);
    }
}
