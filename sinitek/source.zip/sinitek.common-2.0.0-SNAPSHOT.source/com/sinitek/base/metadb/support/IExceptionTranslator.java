// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IExceptionTranslator.java

package com.sinitek.base.metadb.support;

import java.lang.reflect.Method;

public interface IExceptionTranslator
{

    public abstract Throwable translate(Method method, Object aobj[], Throwable throwable);

    public static final IExceptionTranslator DUMMY_TRANSLATOR = new IExceptionTranslator() {

        public Throwable translate(Method method, Object args[], Throwable input)
        {
            return input;
        }

    }
;

}
