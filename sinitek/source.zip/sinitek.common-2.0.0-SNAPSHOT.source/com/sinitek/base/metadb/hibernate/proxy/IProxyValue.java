// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IProxyValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import java.io.Serializable;

public interface IProxyValue
    extends Serializable
{

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    public abstract Object getDBValue();

    public abstract void setDBValue(Object obj);

    public abstract Class getValueType();
}
