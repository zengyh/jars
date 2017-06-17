// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractUserType.java

package com.sinitek.base.metadb.hibernate.usertype;

import java.io.Serializable;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public abstract class AbstractUserType
    implements UserType
{

    public AbstractUserType()
    {
    }

    public int hashCode(Object x)
        throws HibernateException
    {
        return x.hashCode();
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object replace(Object original, Object target, Object owner)
        throws HibernateException
    {
        return original;
    }

    public Serializable disassemble(Object value)
        throws HibernateException
    {
        return (Serializable)value;
    }

    public Object assemble(Serializable cached, Object owner)
        throws HibernateException
    {
        return cached;
    }

    public boolean equals(Object x, Object y)
        throws HibernateException
    {
        return ObjectUtils.equals(x, y);
    }
}
