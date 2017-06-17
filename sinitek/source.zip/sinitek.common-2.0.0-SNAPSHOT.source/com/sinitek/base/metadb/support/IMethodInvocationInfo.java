// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMethodInvocationInfo.java

package com.sinitek.base.metadb.support;


// Referenced classes of package com.sinitek.base.metadb.support:
//            TransactionType, IExceptionHandler, IExceptionTranslator

public interface IMethodInvocationInfo
{

    public abstract TransactionType getTransactionType();

    public abstract IExceptionHandler[] getExceptionHandlers();

    public abstract IExceptionTranslator getExceptionTranslator();

    public abstract Class getInterfaceName();

    public abstract Class getImplementsClassName();

    public abstract String getMethodNamePattern();
}
