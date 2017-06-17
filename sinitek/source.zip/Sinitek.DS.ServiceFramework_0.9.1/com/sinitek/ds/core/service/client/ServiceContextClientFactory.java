// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContextClientFactory.java

package com.sinitek.ds.core.service.client;

import com.sinitek.ds.core.service.*;

// Referenced classes of package com.sinitek.ds.core.service.client:
//            ServiceClientException, ClientConfigReader, IClientConfigInfo

public class ServiceContextClientFactory
{
    private static class ServiceContextProxy
        implements IServiceContext
    {

        public IServiceResponse handleService(IServiceRequest request)
            throws ServiceException
        {
            return clientInfo.findContext(request.getFunctionCode()).handleService(request);
        }

        private IClientConfigInfo clientInfo;

        private ServiceContextProxy(IClientConfigInfo clientInfo)
        {
            this.clientInfo = clientInfo;
        }

    }


    public ServiceContextClientFactory()
    {
    }

    public static synchronized IServiceContext getServiceContext()
    {
        init();
        return context;
    }

    private static void init()
    {
        if(context == null)
        {
            java.net.URL configUrl = Thread.currentThread().getContextClassLoader().getResource("serviceclient.xml");
            if(configUrl == null)
                throw new ServiceClientException("0005");
            IClientConfigInfo clientInfo = ClientConfigReader.readConfig(configUrl);
            context = new ServiceContextProxy(clientInfo);
        }
    }

    private static IServiceContext context;
}
