// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingImpl.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.setting.entity:
//            ISetting

public class SettingImpl extends MetaObjectImpl
    implements ISetting
{

    public SettingImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SETTING"));
    }

    public SettingImpl(IEntity entity)
    {
        super(entity);
    }

    public String getModule()
    {
        return (String)get("Module");
    }

    public void setModule(String value)
    {
        put("Module", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getValue()
    {
        return (String)get("Value");
    }

    public void setValue(String value)
    {
        put("Value", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public Integer getEncryptionFlag()
    {
        return (Integer)get("EncryptionFlag");
    }

    public void setEncryptionFlag(Integer value)
    {
        put("EncryptionFlag", value);
    }
}
