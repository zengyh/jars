// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XNMySQL5Dialect.java

package com.sinitek.base.metadb.hibernate;

import org.hibernate.dialect.MySQL5Dialect;

public class XNMySQL5Dialect extends MySQL5Dialect
{

    public XNMySQL5Dialect()
    {
        registerHibernateType(12, "text");
        registerHibernateType(-1, "longtext");
    }
}
