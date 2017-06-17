// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceSocketHandler.java

package com.sinitek.ds.core.service.impl.socket;

import com.sinitek.base.control.*;
import com.sinitek.base.starter.StarterContext;
import com.sinitek.base.starter.config.StarterConfigReader;
import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceContextClientFactory;
import com.sinitek.ds.core.service.impl.webservice.StringConverter;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ServiceSocketHandler extends AbstractHandler
{

    public ServiceSocketHandler()
    {
    }

    protected void initParam(Properties properties)
        throws HandleException
    {
        java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("starterconfigfile.xml");
        if(url != null)
        {
            logger.info("\u627E\u5230\u542F\u52A8\u5668\u914D\u7F6E\u6587\u4EF6\uFF0C\u8FDB\u884C\u521D\u59CB\u5316\u64CD\u4F5C");
            StarterContext.start(StarterConfigReader.readConfig(url));
        }
    }

    public IHandleResult handle(IRequest request)
        throws HandleException
    {
        String strReq = (String)request.getRequestParameter("reqstr");
        IServiceRequest req = StringConverter.getRequestFromString(strReq);
        String functionCode = req.getFunctionCode();
        try
        {
            com.sinitek.ds.core.service.IServiceResponse resp = ServiceContextClientFactory.getServiceContext().handleService(req);
            String strResp = StringConverter.toString(functionCode, resp);
            SimpleHanlderResult result = new SimpleHanlderResult(true);
            result.addReturnParameter("respstr", strResp);
            return result;
        }
        catch(ServiceException e)
        {
            logger.error("\u8C03\u7528Service\u6846\u67B6\u5931\u8D25", e);
            SimpleHanlderResult result = new SimpleHanlderResult(false);
            result.setReturnMessage(e.toString());
            return result;
        }
    }
}
