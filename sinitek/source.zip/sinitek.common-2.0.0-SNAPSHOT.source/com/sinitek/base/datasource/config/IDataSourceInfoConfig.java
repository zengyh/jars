// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSourceInfoConfig.java

package com.sinitek.base.datasource.config;

import java.util.Map;

// Referenced classes of package com.sinitek.base.datasource.config:
//            IDataSourceStrategyConfig

public interface IDataSourceInfoConfig
{

    public abstract Map getLocalDataSourceInfo();

    public abstract Map getRemoteDataSourceInfo();

    public abstract IDataSourceStrategyConfig getDataSourceStrategyConfig();
}
