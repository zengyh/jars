// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaObjectImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.util.Date;

// Referenced classes of package com.sinitek.base.metadb:
//            IMetaObject, IProperty

public interface IMetaObjectImpl
    extends IMetaObject
{

    public abstract Object putValue(Object obj, Object obj1);

    public abstract Object getValue(Object obj);

    public abstract boolean isDirtyProperty(String s);

    public abstract int getDirtyPropertyCount();

    public abstract boolean isPersistStatus();

    public abstract void setPersistStatus(boolean flag);

    public abstract Object getOrigValue(IProperty iproperty);

    public abstract void setEnumValue(String s, Object obj);

    public abstract void setEntityValue(String s, Object obj);

    public abstract void setEntityOrigDBValue(String s, Object obj);

    public abstract void setCreateTimeStamp(Date date);

    public abstract void setId(int i);

    public abstract void setUpdateTimeStamp(Date date);

    public abstract void setLastProxy(LastIdProxy lastidproxy);

    public abstract void setVersion(int i);
}
