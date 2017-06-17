// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServerConfig.java

package com.sinitek.base.control.server;

import java.util.List;

public interface IServerConfig
{

    public abstract List getHandlerConfigs();

    public abstract int getPort();

    public abstract String getLogPath();

    public abstract int getThreadCount();
}
