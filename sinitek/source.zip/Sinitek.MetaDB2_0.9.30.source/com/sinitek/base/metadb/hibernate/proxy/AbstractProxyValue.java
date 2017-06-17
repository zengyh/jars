// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractProxyValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            IProxyValue

public abstract class AbstractProxyValue
    implements IProxyValue
{

    public AbstractProxyValue()
    {
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj.getClass().equals(getClass()))
        {
            IProxyValue bp = (IProxyValue)obj;
            return ObjectUtils.equals(getValue(), bp.getValue());
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return getValue().hashCode();
    }

    protected static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
