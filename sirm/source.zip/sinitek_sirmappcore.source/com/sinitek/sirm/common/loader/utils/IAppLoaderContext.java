// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAppLoaderContext.java

package com.sinitek.sirm.common.loader.utils;

import java.io.InputStream;
import java.util.Map;

public interface IAppLoaderContext
{

    public abstract InputStream getResourceAsStream(String s);

    public abstract InputStream getClassResourceAsStream(String s);

    public abstract Map getParam();
}
