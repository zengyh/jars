// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAtomicQueryExpression.java

package com.sinitek.spirit.org.server.service;

import java.util.List;

public interface IAtomicQueryExpression
{

    public abstract String getExpression();

    public abstract void query(List list, List list1);
}
