// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MethodInvocationInfoImpl.java

package com.sinitek.base.metadb.support;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IExceptionHandler, IMethodInvocationInfo, IExceptionTranslator, TransactionType

public class MethodInvocationInfoImpl
    implements IMethodInvocationInfo
{

    public MethodInvocationInfoImpl()
    {
        exceptionHandlers = new ArrayList();
    }

    public TransactionType getTransactionType()
    {
        return transactionType;
    }

    public IExceptionHandler[] getExceptionHandlers()
    {
        return (IExceptionHandler[])(IExceptionHandler[])exceptionHandlers.toArray(new IExceptionHandler[exceptionHandlers.size()]);
    }

    public IExceptionTranslator getExceptionTranslator()
    {
        return exceptionTranslate;
    }

    public Class getInterfaceName()
    {
        return interfaceName;
    }

    public Class getImplementsClassName()
    {
        return implementsClassName;
    }

    public String getMethodNamePattern()
    {
        return methodNamePattern;
    }

    public void setExceptionTranslate(IExceptionTranslator exceptionTranslate)
    {
        this.exceptionTranslate = exceptionTranslate;
    }

    public void setImplementsClassName(Class implementsClassName)
    {
        this.implementsClassName = implementsClassName;
    }

    public void setInterfaceName(Class interfaceName)
    {
        this.interfaceName = interfaceName;
    }

    public void setMethodNamePattern(String methodNamePattern)
    {
        this.methodNamePattern = methodNamePattern;
    }

    public void setTransactionType(TransactionType transactionType)
    {
        this.transactionType = transactionType;
    }

    public void addExceptionHandler(IExceptionHandler handler)
    {
        exceptionHandlers.add(handler);
    }

    private Class interfaceName;
    private Class implementsClassName;
    private IExceptionTranslator exceptionTranslate;
    private TransactionType transactionType;
    private List exceptionHandlers;
    private String methodNamePattern;
}
