// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleParamObject.java

package com.sinitek.ds.core.service;

import java.util.*;
import org.apache.commons.collections.map.CaseInsensitiveMap;

// Referenced classes of package com.sinitek.ds.core.service:
//            IParamObject, ServiceException

public class SimpleParamObject
    implements IParamObject
{

    public SimpleParamObject()
    {
        paramMap = new CaseInsensitiveMap();
    }

    public Object getObject(String paramName)
    {
        return paramMap.get(paramName);
    }

    public String getString(String paramName)
        throws ServiceException
    {
        return (String)paramMap.get(paramName);
    }

    public Integer getInteger(String paramName)
        throws ServiceException
    {
        return (Integer)paramMap.get(paramName);
    }

    public Float getFloat(String paramName)
        throws ServiceException
    {
        return (Float)paramMap.get(paramName);
    }

    public Double getDouble(String paramName)
        throws ServiceException
    {
        return (Double)paramMap.get(paramName);
    }

    public Boolean getBoolean(String paramName)
        throws ServiceException
    {
        return (Boolean)paramMap.get(paramName);
    }

    public boolean contains(String paramName)
    {
        return paramMap.containsKey(paramName);
    }

    public Collection getParamNames()
    {
        return paramMap.keySet();
    }

    public void addParam(String name, Object value)
    {
        paramMap.put(name, value);
    }

    public Map getValueMap()
    {
        return paramMap;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof IParamObject)
        {
            IParamObject req = (IParamObject)obj;
            return mapEquals(getValueMap(), req.getValueMap());
        } else
        {
            return false;
        }
    }

    private boolean mapEquals(Map map1, Map map2)
    {
        boolean equals = map1.size() == map2.size();
        for(Iterator iter = map1.entrySet().iterator(); iter.hasNext() && equals;)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            Object value = entry.getValue();
            if(value == null)
                equals = map2.containsKey(entry.getKey()) && map2.get(entry.getKey()) == null;
            else
                value.equals(map2.get(entry.getKey()));
        }

        return equals;
    }

    protected Map paramMap;
}
