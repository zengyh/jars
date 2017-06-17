// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ICacheManager.java

package com.sinitek.base.cache;

import java.util.List;

public interface ICacheManager
{

    public abstract boolean set(String s, Object obj);

    public abstract Object get(String s);

    public abstract boolean delete(String s);

    public abstract boolean delete(String as[]);

    public abstract List listKeys();
}
