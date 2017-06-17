// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandleWrapException.java

package com.sinitek.base.control.client;

import com.sinitek.base.control.HandleException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class HandleWrapException extends HandleException
{

    public HandleWrapException(String code, String errorMsg, String stackTrace)
    {
        super(code.substring(4));
        this.code = code;
        this.errorMsg = errorMsg;
        this.stackTrace = stackTrace;
    }

    public String getPropertieFile()
    {
        return null;
    }

    public String getModuleCode()
    {
        return code.substring(0, 2);
    }

    public String getSubModuleCode()
    {
        return code.substring(2, 4);
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void printStackTrace()
    {
        printStackTrace(System.out);
    }

    public void printStackTrace(PrintStream s)
    {
        PrintWriter writer = new PrintWriter(s);
        super.printStackTrace(writer);
        writer.flush();
    }

    public void printStackTrace(PrintWriter s)
    {
        s.println(stackTrace);
        s.flush();
    }

    private String code;
    private String errorMsg;
    private String stackTrace;
}
