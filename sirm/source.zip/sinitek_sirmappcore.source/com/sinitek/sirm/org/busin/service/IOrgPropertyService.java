// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgPropertyService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.sirm.org.busin.entity.IOrgProperty;

public interface IOrgPropertyService
{

    public abstract void saveOrgProperty(IOrgProperty iorgproperty);

    public abstract IOrgProperty getOrgProperty(int i);

    public abstract IOrgProperty getOrgPropertyByOrgId(String s);

    public abstract void deleteOrgProperty(int i);

    public abstract void deleteOrgPropertyByOrgId(String s);
}
