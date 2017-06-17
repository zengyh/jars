// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectUpdateBean.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.IMetaObjectImpl;

// Referenced classes of package com.sinitek.base.metadb.cache:
//            ObjectOperateType

public class MetaObjectUpdateBean
{

    public MetaObjectUpdateBean(IMetaObjectImpl updateObject, ObjectOperateType updateType, IEntity entity, int objId)
    {
        this.updateObject = updateObject;
        this.updateType = updateType;
        this.entity = entity;
        this.objId = objId;
    }

    public IMetaObjectImpl getUpdateObject()
    {
        return updateObject;
    }

    public ObjectOperateType getUpdateType()
    {
        return updateType;
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public int getObjId()
    {
        return objId;
    }

    public void setObjId(int objId)
    {
        this.objId = objId;
    }

    private IMetaObjectImpl updateObject;
    private ObjectOperateType updateType;
    private IEntity entity;
    private int objId;
}
