// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleRequest.java

package com.sinitek.base.control;

import java.util.*;

// Referenced classes of package com.sinitek.base.control:
//            IRequest

public class SimpleRequest
    implements IRequest
{

    public SimpleRequest()
    {
        paramsMap = new HashMap();
    }

    public List getRequestParameterNames()
    {
        return new ArrayList(paramsMap.keySet());
    }

    public Object getRequestParameter(String name)
    {
        return paramsMap.get(name);
    }

    public boolean containsParameter(String name)
    {
        return paramsMap.containsKey(name);
    }

    public void addRequestParameter(String name, Object value)
    {
        paramsMap.put(name, value);
    }

    protected Map paramsMap;
}
