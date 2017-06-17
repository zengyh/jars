// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEventTester.java

package com.sinitek.sirm.common.engine.event.utils;

import java.util.List;

public interface IEventTester
{

    public abstract void writeMessage(Object obj);

    public abstract List getAllMessage();

    public abstract void setWriteLog(boolean flag);

    public abstract void setLogClass(Class class1);
}
