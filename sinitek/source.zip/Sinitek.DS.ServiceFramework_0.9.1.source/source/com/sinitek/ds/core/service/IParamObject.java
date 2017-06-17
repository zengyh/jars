// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IParamObject.java

package com.sinitek.ds.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

// Referenced classes of package com.sinitek.ds.core.service:
//            ServiceException

public interface IParamObject
    extends Serializable
{

    public abstract Object getObject(String s);

    public abstract String getString(String s)
        throws ServiceException;

    public abstract Integer getInteger(String s)
        throws ServiceException;

    public abstract Float getFloat(String s)
        throws ServiceException;

    public abstract Double getDouble(String s)
        throws ServiceException;

    public abstract Boolean getBoolean(String s)
        throws ServiceException;

    public abstract boolean contains(String s);

    public abstract Collection getParamNames();

    public abstract Map getValueMap();
}
