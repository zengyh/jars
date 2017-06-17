// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskGroupException.java

package com.sinitek.base.taskgroup;

import com.sinitek.base.common.PropertiesBasedException;

public class TaskGroupException extends PropertiesBasedException
{

    public TaskGroupException(String errorCode)
    {
        super(errorCode);
    }

    public TaskGroupException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public TaskGroupException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public TaskGroupException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/taskgroup/taskgroup_exceptioncode.properties";
    }

    public String getModuleCode()
    {
        return "00";
    }

    public String getSubModuleCode()
    {
        return "05";
    }
}
