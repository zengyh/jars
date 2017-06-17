// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nestable.java

package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface Nestable
{

    public abstract Throwable getCause();

    public abstract String getMessage();

    public abstract String getMessage(int i);

    public abstract String[] getMessages();

    public abstract Throwable getThrowable(int i);

    public abstract int getThrowableCount();

    public abstract Throwable[] getThrowables();

    public abstract int indexOfThrowable(Class class1);

    public abstract int indexOfThrowable(Class class1, int i);

    public abstract void printStackTrace(PrintWriter printwriter);

    public abstract void printStackTrace(PrintStream printstream);

    public abstract void printPartialStackTrace(PrintWriter printwriter);
}
