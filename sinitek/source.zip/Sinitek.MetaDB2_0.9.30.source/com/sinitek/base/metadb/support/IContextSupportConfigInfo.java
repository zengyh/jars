// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IContextSupportConfigInfo.java

package com.sinitek.base.metadb.support;

import java.util.List;
import java.util.Properties;

public interface IContextSupportConfigInfo
{

    public abstract String getName();

    public abstract Class getInterface();

    public abstract Class getImplClass();

    public abstract String[] getInterfaces();

    public abstract Properties getInitProperties();

    public abstract List getMethodInvocationInfos();
}
