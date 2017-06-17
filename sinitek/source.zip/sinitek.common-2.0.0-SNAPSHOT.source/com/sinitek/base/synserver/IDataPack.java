// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataPack.java

package com.sinitek.base.synserver;


public interface IDataPack
{

    public abstract boolean isSystemPack();

    public abstract boolean isRequestPack();

    public abstract String getApplicationCode();

    public abstract String getPackCode();

    public abstract int getBodySize();

    public abstract byte[] getRemark();

    public abstract byte[] getBodyPack();

    public abstract byte[] getBytes();

    public static final String APP_CODE_READY = "READY";
    public static final String APP_CODE_CHECK = "CHECK";
    public static final String APP_CODE_EXIT = "EXIT";
    public static final String APP_CODE_GOODBYE = "GOODBYE";
}
