// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ErrorResponse.java

package com.sinitek.ds.core.service.impl.local;

import com.sinitek.ds.core.service.*;
import java.util.*;

public class ErrorResponse extends SimpleParamObject
    implements IServiceResponse
{

    public ErrorResponse(String errorCode, String errorMsg, Date startDate)
    {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        setCallStartTime(startDate);
        setCallEndTime(new Date());
    }

    public boolean isSuccess()
    {
        return false;
    }

    public String getReturnCode()
    {
        return errorCode;
    }

    public String getReturnMessage()
    {
        return errorMsg;
    }

    public int getRecordCount()
    {
        return 0;
    }

    public void resetIterator()
    {
    }

    public Date getCallEndTime()
    {
        return (Date)super.getObject("_call_end_time");
    }

    public Date getCallStartTime()
    {
        return (Date)super.getObject("_call_start_time");
    }

    public void setCallEndTime(Date endDate)
    {
        super.addParam("_call_end_time", endDate);
    }

    public void setCallStartTime(Date startDate)
    {
        super.addParam("_call_start_time", startDate);
    }

    public boolean hasNext()
    {
        return false;
    }

    public Object next()
    {
        throw new ServiceRuntimeException("0014");
    }

    public void remove()
    {
        throw new UnsupportedOperationException("\u4E0D\u80FD\u5BF9\u8FD4\u56DE\u7ED3\u679C\u8FDB\u884C\u4FEE\u6539");
    }

    public Map getValueMap()
    {
        return Collections.EMPTY_MAP;
    }

    private String errorCode;
    private String errorMsg;
}
