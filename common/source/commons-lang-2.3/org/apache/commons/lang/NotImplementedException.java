// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotImplementedException.java

package org.apache.commons.lang;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;

public class NotImplementedException extends UnsupportedOperationException
    implements Nestable
{

    public NotImplementedException()
    {
        super("Code is not implemented");
        _flddelegate = new NestableDelegate(this);
    }

    public NotImplementedException(String msg)
    {
        super(msg != null ? msg : "Code is not implemented");
        _flddelegate = new NestableDelegate(this);
    }

    public NotImplementedException(Throwable cause)
    {
        super("Code is not implemented");
        _flddelegate = new NestableDelegate(this);
        this.cause = cause;
    }

    public NotImplementedException(String msg, Throwable cause)
    {
        super(msg != null ? msg : "Code is not implemented");
        _flddelegate = new NestableDelegate(this);
        this.cause = cause;
    }

    public NotImplementedException(Class clazz)
    {
        super(clazz != null ? "Code is not implemented in " + clazz : "Code is not implemented");
        _flddelegate = new NestableDelegate(this);
    }

    public Throwable getCause()
    {
        return cause;
    }

    public String getMessage()
    {
        if(super.getMessage() != null)
            return super.getMessage();
        if(cause != null)
            return cause.toString();
        else
            return null;
    }

    public String getMessage(int index)
    {
        if(index == 0)
            return super.getMessage();
        else
            return _flddelegate.getMessage(index);
    }

    public String[] getMessages()
    {
        return _flddelegate.getMessages();
    }

    public Throwable getThrowable(int index)
    {
        return _flddelegate.getThrowable(index);
    }

    public int getThrowableCount()
    {
        return _flddelegate.getThrowableCount();
    }

    public Throwable[] getThrowables()
    {
        return _flddelegate.getThrowables();
    }

    public int indexOfThrowable(Class type)
    {
        return _flddelegate.indexOfThrowable(type, 0);
    }

    public int indexOfThrowable(Class type, int fromIndex)
    {
        return _flddelegate.indexOfThrowable(type, fromIndex);
    }

    public void printStackTrace()
    {
        _flddelegate.printStackTrace();
    }

    public void printStackTrace(PrintStream out)
    {
        _flddelegate.printStackTrace(out);
    }

    public void printStackTrace(PrintWriter out)
    {
        _flddelegate.printStackTrace(out);
    }

    public final void printPartialStackTrace(PrintWriter out)
    {
        super.printStackTrace(out);
    }

    private static final String DEFAULT_MESSAGE = "Code is not implemented";
    private static final long serialVersionUID = 0xa053285329013bd8L;
    private NestableDelegate _flddelegate;
    private Throwable cause;
}
