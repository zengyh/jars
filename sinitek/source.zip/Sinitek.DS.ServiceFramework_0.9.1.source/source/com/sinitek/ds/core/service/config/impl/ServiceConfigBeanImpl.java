// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceConfigBeanImpl.java

package com.sinitek.ds.core.service.config.impl;

import com.sinitek.ds.core.service.IServiceRequest;
import com.sinitek.ds.core.service.ServiceException;
import com.sinitek.ds.core.service.config.IServiceConfigBean;
import com.sinitek.ds.core.service.config.IServiceParamConfig;
import java.util.*;

public class ServiceConfigBeanImpl
    implements IServiceConfigBean
{

    public ServiceConfigBeanImpl()
    {
    }

    public Properties getInitParams()
    {
        return initParams;
    }

    public void setInitParams(Properties initParams)
    {
        this.initParams = initParams;
    }

    public String getServiceClass()
    {
        return serviceClass;
    }

    public String getFunctionCode()
    {
        return functionCode;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public void setFunctionCode(String functionCode)
    {
        this.functionCode = functionCode;
    }

    public void setServiceClass(String serviceClass)
    {
        this.serviceClass = serviceClass;
    }

    public IServiceParamConfig[] getParamConfigs()
    {
        return paramConfigs;
    }

    public void setParamConfigs(IServiceParamConfig paramConfigs[])
    {
        this.paramConfigs = paramConfigs;
    }

    public void checkRequest(IServiceRequest request)
        throws ServiceException
    {
        for(int i = 0; i < paramConfigs.length; i++)
        {
            IServiceParamConfig config = paramConfigs[i];
            config.checkParamValue(request.getObject(config.getParamName()));
        }

    }

    public List getKeyParams()
    {
        List ret = new ArrayList();
        for(int i = 0; i < paramConfigs.length; i++)
        {
            IServiceParamConfig config = paramConfigs[i];
            if(config.isKeyFlag())
                ret.add(config.getParamName());
        }

        return ret;
    }

    private String serviceClass;
    private String functionCode;
    private String functionName;
    private IServiceParamConfig paramConfigs[];
    private Properties initParams;
}
