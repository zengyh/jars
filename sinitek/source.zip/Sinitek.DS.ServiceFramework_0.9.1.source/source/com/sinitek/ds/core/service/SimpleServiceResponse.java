// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleServiceResponse.java

package com.sinitek.ds.core.service;

import java.util.*;

// Referenced classes of package com.sinitek.ds.core.service:
//            SimpleParamObject, IServiceResponse, IParamObject

public class SimpleServiceResponse extends SimpleParamObject
    implements IServiceResponse
{

    protected SimpleServiceResponse(boolean success, String returnCode, String returnMessage)
    {
        recordList = new ArrayList();
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.success = success;
    }

    public static SimpleServiceResponse createSuccessResponse(String returnMessage)
    {
        return new SimpleServiceResponse(true, "00000000", returnMessage);
    }

    public static SimpleServiceResponse createFailResponse(String returnCode, String returnMessage)
    {
        return new SimpleServiceResponse(false, returnCode, returnMessage);
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getReturnCode()
    {
        return returnCode;
    }

    public String getReturnMessage()
    {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage)
    {
        this.returnMessage = returnMessage;
    }

    public int getRecordCount()
    {
        return recordList.size();
    }

    public boolean hasNext()
    {
        return initIterator().hasNext();
    }

    public Object next()
    {
        return initIterator().next();
    }

    public void remove()
    {
        throw new UnsupportedOperationException("\u4E0D\u80FD\u5BF9\u8FD4\u56DE\u7ED3\u679C\u8FDB\u884C\u4FEE\u6539");
    }

    public void resetIterator()
    {
        iterator = null;
    }

    public void addRecord(IParamObject record)
    {
        recordList.add(record);
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof IServiceResponse)
        {
            IServiceResponse resp = (IServiceResponse)obj;
            return returnCode.equals(resp.getReturnCode()) && returnMessage.equals(resp.getReturnMessage()) && super.equals(resp) && contentEquals(resp);
        } else
        {
            return false;
        }
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

    private boolean contentEquals(IServiceResponse resp)
    {
        boolean equals;
        for(equals = getRecordCount() == resp.getRecordCount(); resp.hasNext() && hasNext() && equals; equals = next().equals(resp.next()));
        resp.resetIterator();
        resetIterator();
        return equals;
    }

    private Iterator initIterator()
    {
        if(iterator == null)
            iterator = recordList.iterator();
        return iterator;
    }

    private boolean success;
    private String returnCode;
    private String returnMessage;
    private List recordList;
    private Iterator iterator;
}
