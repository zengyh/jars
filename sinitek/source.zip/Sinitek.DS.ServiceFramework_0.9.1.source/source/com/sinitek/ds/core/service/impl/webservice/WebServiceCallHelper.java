// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebServiceCallHelper.java

package com.sinitek.ds.core.service.impl.webservice;

import com.sinitek.ds.core.service.IServiceContext;
import com.sinitek.ds.core.service.IServiceRequest;
import com.sinitek.ds.core.service.client.ServiceContextClientFactory;

// Referenced classes of package com.sinitek.ds.core.service.impl.webservice:
//            StringConverter

public class WebServiceCallHelper
{

    public WebServiceCallHelper()
    {
    }

    public static String callService(String request)
    {
        IServiceRequest req = StringConverter.getRequestFromString(request);
        String functionCode = req.getFunctionCode();
        com.sinitek.ds.core.service.IServiceResponse resp = ServiceContextClientFactory.getServiceContext().handleService(req);
        return StringConverter.toString(functionCode, resp);
    }
}
