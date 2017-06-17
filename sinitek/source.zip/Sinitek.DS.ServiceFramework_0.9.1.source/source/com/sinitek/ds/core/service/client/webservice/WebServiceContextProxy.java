// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebServiceContextProxy.java

package com.sinitek.ds.core.service.client.webservice;

import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceClientException;
import com.sinitek.ds.core.service.impl.webservice.StringConverter;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class WebServiceContextProxy
    implements IServiceContext
{

    public WebServiceContextProxy(String url)
    {
        this.url = url;
    }

    public IServiceResponse handleService(IServiceRequest request)
        throws ServiceException
    {
        try
        {
            RPCServiceClient serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            options.setProperty("REUSE_HTTP_CLIENT", Boolean.TRUE);
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            options.setTimeOutInMilliSeconds(0x36ee80L);
            QName omNs = new QName("http://ws.apache.org/axis2", "call");
            Object args[] = {
                StringConverter.toString(request)
            };
            Object response[] = serviceClient.invokeBlocking(omNs, args, new Class[] {
                java/lang/String
            });
            serviceClient.cleanupTransport();
            serviceClient.cleanup();
            serviceClient = null;
            return StringConverter.getResponseFromString((String)response[0]);
        }
        catch(Throwable ex)
        {
            ex.printStackTrace();
            throw new ServiceClientException("0007", ex);
        }
    }

    private String url;
}
