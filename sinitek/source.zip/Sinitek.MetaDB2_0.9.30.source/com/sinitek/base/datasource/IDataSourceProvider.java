// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSourceProvider.java

package com.sinitek.base.datasource;

import com.sinitek.base.datasource.config.IDataSourceInfoConfig;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import java.util.Map;
import javax.sql.DataSource;

// Referenced classes of package com.sinitek.base.datasource:
//            DataSourceException

public interface IDataSourceProvider
{

    public abstract void addDataSourceAndSetStrategy(IDataSourceInfoConfig idatasourceinfoconfig)
        throws DataSourceException;

    public abstract DataSource getDataSource(String s)
        throws DataSourceException;

    public abstract Map getDataSource()
        throws DataSourceException;

    public abstract IDataSourceStrategyConfig getDataSourceStrategy();
}
