// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceParamConfig.java

package com.sinitek.ds.core.service.config;

import com.sinitek.ds.core.service.ServiceException;
import java.io.Serializable;

// Referenced classes of package com.sinitek.ds.core.service.config:
//            ServiceParamType

public interface IServiceParamConfig
    extends Serializable
{

    public abstract boolean isNotNull();

    public abstract String getParamName();

    public abstract ServiceParamType getParamType();

    public abstract String getInfo();

    public abstract String getComment();

    public abstract void checkParamValue(Object obj)
        throws ServiceException;

    public abstract boolean isKeyFlag();
}
