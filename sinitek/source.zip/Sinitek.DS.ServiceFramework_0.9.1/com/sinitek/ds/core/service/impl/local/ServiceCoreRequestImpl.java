// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceCoreRequestImpl.java

package com.sinitek.ds.core.service.impl.local;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.framework.dict.DictionaryContext;
import com.sinitek.base.framework.dict.IDictionaryItem;
import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.config.*;
import com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;

public class ServiceCoreRequestImpl
    implements IServiceCoreRequest
{

    public ServiceCoreRequestImpl(IServiceConfigBean configBean, IServiceRequest request)
    {
        this.request = request;
        IServiceParamConfig configs[] = configBean.getParamConfigs();
        paramMap = new CaseInsensitiveMap(configs.length);
        for(int i = 0; i < configs.length; i++)
            paramMap.put(configs[i].getParamName(), configs[i]);

    }

    public IEnumItem getEnumItem(String paramName)
        throws ServiceException
    {
        IServiceParamConfig config = (IServiceParamConfig)paramMap.get(paramName);
        if(config == null)
            return null;
        if(config.getParamType().equals(ServiceParamType.ENUM))
        {
            com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder.EnumParamConfig enumConfig = (com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder.EnumParamConfig)config;
            String value;
            if(enumConfig.isIntType())
            {
                value = getInteger(paramName);
                if(value == null)
                    return null;
                else
                    return EnumItemContext.getInstance().getEnumItem(enumConfig.getEnumName(), value.intValue());
            }
            value = getString(paramName);
            if(value == null)
                return null;
            else
                return EnumItemContext.getInstance().getEnumItem(enumConfig.getEnumName(), value);
        } else
        {
            throw new ServiceRuntimeException("0012", new Object[] {
                paramName
            });
        }
    }

    public IDictionaryItem getDictionaryItem(String paramName)
        throws ServiceException
    {
        IServiceParamConfig config = (IServiceParamConfig)paramMap.get(paramName);
        if(config == null)
            return null;
        if(config.getParamType().equals(ServiceParamType.DICT))
        {
            com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder.DictParamConfig dictConfig = (com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder.DictParamConfig)config;
            String value = getString(paramName);
            if(StringUtils.isEmpty(value))
                return null;
            else
                return DictionaryContext.getInstance().getDictionaryItem(dictConfig.getKey(), value);
        } else
        {
            throw new ServiceRuntimeException("0013", new Object[] {
                paramName
            });
        }
    }

    public String getFunctionCode()
    {
        return request.getFunctionCode();
    }

    public Object getObject(String paramName)
    {
        return request.getObject(paramName);
    }

    public String getString(String paramName)
        throws ServiceException
    {
        return request.getString(paramName);
    }

    public Integer getInteger(String paramName)
        throws ServiceException
    {
        return request.getInteger(paramName);
    }

    public Float getFloat(String paramName)
        throws ServiceException
    {
        return request.getFloat(paramName);
    }

    public Double getDouble(String paramName)
        throws ServiceException
    {
        return request.getDouble(paramName);
    }

    public Boolean getBoolean(String paramName)
        throws ServiceException
    {
        return request.getBoolean(paramName);
    }

    public boolean contains(String paramName)
    {
        return request.contains(paramName);
    }

    public Collection getParamNames()
    {
        return request.getParamNames();
    }

    public Map getValueMap()
    {
        return request.getValueMap();
    }

    private IServiceRequest request;
    private Map paramMap;
}
