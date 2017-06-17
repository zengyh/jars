// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ILocalStrongDataSource.java

package com.sinitek.base.datasource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

// Referenced classes of package com.sinitek.base.datasource:
//            IStrongDataSource

public interface ILocalStrongDataSource
    extends IStrongDataSource
{

    public abstract Logger getParentLogger()
        throws SQLFeatureNotSupportedException;
}
