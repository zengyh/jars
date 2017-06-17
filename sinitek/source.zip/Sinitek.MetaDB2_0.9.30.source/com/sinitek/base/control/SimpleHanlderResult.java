// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleHanlderResult.java

package com.sinitek.base.control;

import java.util.*;

// Referenced classes of package com.sinitek.base.control:
//            IHandleResult

public class SimpleHanlderResult
    implements IHandleResult
{

    public SimpleHanlderResult(boolean success)
    {
        returnMessage = "";
        paramMap = new HashMap();
        this.success = success;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public Object getReturnParameter(String name)
    {
        return paramMap.get(name);
    }

    public List getReturnParameterNames()
    {
        return new ArrayList(paramMap.keySet());
    }

    public boolean containsParameter(String name)
    {
        return paramMap.containsKey(name);
    }

    public String getReturnMessage()
    {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage)
    {
        this.returnMessage = returnMessage != null ? returnMessage : "";
    }

    public void addReturnParameter(String name, Object value)
    {
        paramMap.put(name, value);
    }

    protected boolean success;
    protected String returnMessage;
    protected Map paramMap;
}
