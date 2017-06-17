// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISynClientListener.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.syncenter.common.IDataPack;

public interface ISynClientListener
{

    public abstract void onRecieveData(IDataPack idatapack);
}
