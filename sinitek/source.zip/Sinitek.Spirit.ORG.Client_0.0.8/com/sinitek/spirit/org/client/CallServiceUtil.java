// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallServiceUtil.java

package com.sinitek.spirit.org.client;

import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceContextClientFactory;
import com.sinitek.spirit.org.core.OrgException;

// Referenced classes of package com.sinitek.spirit.org.client:
//            OrgClientException

public class CallServiceUtil
{

    public CallServiceUtil()
    {
    }

    public static IServiceResponse callService(IServiceRequest request)
        throws OrgException
    {
        IServiceResponse response = ServiceContextClientFactory.getServiceContext().handleService(request);
        if(!response.isSuccess())
            throw new OrgClientException("9999", new Object[] {
                response.getReturnMessage(), response.getReturnCode()
            });
        else
            return response;
    }
}
