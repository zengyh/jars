// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntityCatalog.java

package com.sinitek.base.metadb;

import java.io.Serializable;

public interface IEntityCatalog
    extends Serializable
{

    public abstract String getCatalogKey();

    public abstract String getCatalogName();

    public abstract String getCatalogInfo();

    public abstract String getCatalogPrefix();
}
