// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IStrongDataSource.java

package com.sinitek.base.datasource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;

// Referenced classes of package com.sinitek.base.datasource:
//            DataSourceException

public interface IStrongDataSource
{

    public abstract void createOriDataSource();

    public abstract String getDataSourceName();

    public abstract void startCheckoutConnection();

    public abstract void startReconnect();

    public abstract boolean isReconnectionStarted();

    public abstract void reconnectionOver();

    public abstract DataSource getOriDataSource();

    public abstract boolean getNeedBind();

    public abstract void bindDataSource(String s, DataSource datasource, boolean flag)
        throws DataSourceException, NamingException;

    public abstract Logger getParentLogger()
        throws SQLFeatureNotSupportedException;
}
