// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityCatalogImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.IEntityCatalog;

public class EntityCatalogImpl
    implements IEntityCatalog
{

    public EntityCatalogImpl()
    {
    }

    public String getCatalogInfo()
    {
        return catalogInfo;
    }

    public void setCatalogInfo(String catalogInfo)
    {
        this.catalogInfo = catalogInfo;
    }

    public String getCatalogKey()
    {
        return catalogKey;
    }

    public void setCatalogKey(String catalogKey)
    {
        this.catalogKey = catalogKey;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    public String getCatalogPrefix()
    {
        return catalogPrefix;
    }

    public void setCatalogPrefix(String catalogPrefix)
    {
        this.catalogPrefix = catalogPrefix;
    }

    private String catalogKey;
    private String catalogName;
    private String catalogInfo;
    private String catalogPrefix;
}
