// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IExceptionHandler.java

package com.sinitek.base.metadb.support;


public interface IExceptionHandler
{

    public abstract void onException(Throwable throwable);
}
