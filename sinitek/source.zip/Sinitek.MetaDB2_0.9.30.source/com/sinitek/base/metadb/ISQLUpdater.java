// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISQLUpdater.java

package com.sinitek.base.metadb;

import java.util.Map;

public interface ISQLUpdater
{

    public abstract void setParameter(String s, Object obj);

    public abstract void setParameters(Map map);

    public abstract int executeUpdate();
}
