// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IHandleResult.java

package com.sinitek.base.control;

import java.util.List;

public interface IHandleResult
{

    public abstract boolean isSuccess();

    public abstract String getReturnMessage();

    public abstract Object getReturnParameter(String s);

    public abstract List getReturnParameterNames();

    public abstract boolean containsParameter(String s);
}
