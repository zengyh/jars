// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XNOracle9iDialect.java

package com.sinitek.base.metadb.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Oracle9Dialect;

public class XNOracle9iDialect extends Oracle9Dialect
{

    public XNOracle9iDialect()
    {
    }

    public String getHibernateTypeName(int code, int length, int precision, int scale)
        throws HibernateException
    {
        if(code == 1)
            return "string";
        else
            return super.getHibernateTypeName(code, length, precision, scale);
    }
}
