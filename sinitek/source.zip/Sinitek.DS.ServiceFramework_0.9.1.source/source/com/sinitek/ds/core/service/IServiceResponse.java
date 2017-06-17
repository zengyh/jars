// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceResponse.java

package com.sinitek.ds.core.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

// Referenced classes of package com.sinitek.ds.core.service:
//            IParamObject

public interface IServiceResponse
    extends IParamObject, Serializable, Iterator
{

    public abstract boolean isSuccess();

    public abstract String getReturnCode();

    public abstract String getReturnMessage();

    public abstract boolean hasNext();

    public abstract Object next();

    public abstract int getRecordCount();

    public abstract void resetIterator();

    public abstract Date getCallStartTime();

    public abstract Date getCallEndTime();

    public abstract void setCallStartTime(Date date);

    public abstract void setCallEndTime(Date date);

    public static final String CALL_START_PARAM_NAME = "_call_start_time";
    public static final String CALL_END_PARAM_NAME = "_call_end_time";
}
