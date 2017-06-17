// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleServiceRequest.java

package com.sinitek.ds.core.service;


// Referenced classes of package com.sinitek.ds.core.service:
//            SimpleParamObject, IServiceRequest

public class SimpleServiceRequest extends SimpleParamObject
    implements IServiceRequest
{

    public SimpleServiceRequest(String functionCode)
    {
        this.functionCode = functionCode;
    }

    public String getFunctionCode()
    {
        return functionCode;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof IServiceRequest)
        {
            IServiceRequest req = (IServiceRequest)obj;
            return functionCode.equals(req.getFunctionCode()) && super.equals(req);
        } else
        {
            return false;
        }
    }

    private String functionCode;
}
