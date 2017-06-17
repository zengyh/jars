// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IScheduleTask.java

package com.sinitek.sirm.common.support.schedule;


public interface IScheduleTask
{

    public abstract void init();

    public abstract void execute();

    public abstract void destroy();
}
