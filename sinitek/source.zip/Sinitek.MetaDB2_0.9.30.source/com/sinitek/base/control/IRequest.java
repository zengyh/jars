// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRequest.java

package com.sinitek.base.control;

import java.util.List;

public interface IRequest
{

    public abstract List getRequestParameterNames();

    public abstract Object getRequestParameter(String s);

    public abstract boolean containsParameter(String s);
}
