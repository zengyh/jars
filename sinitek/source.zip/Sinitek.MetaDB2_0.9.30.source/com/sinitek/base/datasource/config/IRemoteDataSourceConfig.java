// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRemoteDataSourceConfig.java

package com.sinitek.base.datasource.config;

import java.util.Map;

public interface IRemoteDataSourceConfig
{

    public abstract String getDataSourceName();

    public abstract String getJndi();

    public abstract Map getJndiProp();

    public abstract boolean isNeedBind();

    public abstract String getUserName();

    public abstract String getPassword();
}
