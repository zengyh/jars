// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceConfigBean.java

package com.sinitek.ds.core.service.config;

import com.sinitek.ds.core.service.IServiceRequest;
import com.sinitek.ds.core.service.ServiceException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

// Referenced classes of package com.sinitek.ds.core.service.config:
//            IServiceParamConfig

public interface IServiceConfigBean
    extends Serializable
{

    public abstract String getServiceClass();

    public abstract String getFunctionCode();

    public abstract String getFunctionName();

    public abstract IServiceParamConfig[] getParamConfigs();

    public abstract Properties getInitParams();

    public abstract void checkRequest(IServiceRequest iservicerequest)
        throws ServiceException;

    public abstract List getKeyParams();
}
