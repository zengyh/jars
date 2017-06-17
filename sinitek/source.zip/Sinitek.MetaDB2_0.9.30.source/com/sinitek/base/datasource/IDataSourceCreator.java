// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSourceCreator.java

package com.sinitek.base.datasource;

import com.sinitek.base.datasource.config.IDataSourceInfoConfig;
import com.sinitek.base.datasource.config.ILocalDataSourceConfig;
import com.sinitek.base.datasource.config.IRemoteDataSourceConfig;
import java.util.Map;
import javax.sql.DataSource;

// Referenced classes of package com.sinitek.base.datasource:
//            DataSourceException

public interface IDataSourceCreator
{

    public abstract DataSource createLocalDataSource(ILocalDataSourceConfig ilocaldatasourceconfig)
        throws DataSourceException;

    public abstract DataSource createRemoteDataSource(IRemoteDataSourceConfig iremotedatasourceconfig)
        throws DataSourceException;

    public abstract Map createLocalDataSource(Map map)
        throws DataSourceException;

    public abstract Map createRemoteDataSource(Map map)
        throws DataSourceException;

    public abstract Map createDataSource(IDataSourceInfoConfig idatasourceinfoconfig)
        throws DataSourceException;
}
