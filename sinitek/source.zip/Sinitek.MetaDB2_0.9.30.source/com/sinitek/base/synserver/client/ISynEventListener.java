// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISynEventListener.java

package com.sinitek.base.synserver.client;

import com.sinitek.base.synserver.IDataPack;

public interface ISynEventListener
{

    public abstract void onInit();

    public abstract void onRecieveData(IDataPack idatapack);
}
