// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRemoteStrongDataSource.java

package com.sinitek.base.datasource;

import com.sinitek.base.datasource.config.IRemoteDataSourceConfig;

// Referenced classes of package com.sinitek.base.datasource:
//            IStrongDataSource

public interface IRemoteStrongDataSource
    extends IStrongDataSource
{

    public abstract IRemoteDataSourceConfig getRemoteDataSourceConfig();
}