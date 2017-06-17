// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SocketServiceContextProxy.java

package com.sinitek.ds.core.service.client.socket;

import com.sinitek.base.control.IHandleResult;
import com.sinitek.base.control.SimpleRequest;
import com.sinitek.base.control.client.ControlClient;
import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceClientException;
import com.sinitek.ds.core.service.impl.webservice.StringConverter;

public class SocketServiceContextProxy
    implements IServiceContext
{

    public SocketServiceContextProxy(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public IServiceResponse handleService(IServiceRequest request)
        throws ServiceException
    {
        ControlClient client = ControlClient.createClient(ip, port);
        SimpleRequest socketReq = new SimpleRequest();
        socketReq.addRequestParameter("reqstr", StringConverter.toString(request));
        IHandleResult result = client.sendControl("servicecall", socketReq);
        if(result.isSuccess())
        {
            String strResp = (String)result.getReturnParameter("respstr");
            return StringConverter.getResponseFromString(strResp);
        } else
        {
            throw new ServiceClientException("0008", new Object[] {
                result.getReturnMessage()
            });
        }
    }

    private String ip;
    private int port;
}
