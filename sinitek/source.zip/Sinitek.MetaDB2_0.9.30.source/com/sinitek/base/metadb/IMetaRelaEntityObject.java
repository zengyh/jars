// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaRelaEntityObject.java

package com.sinitek.base.metadb;


public interface IMetaRelaEntityObject
{

    public abstract Object getEntityOrigValue();

    public abstract boolean isValidRela();

    public abstract void resetRelaObject();
}
