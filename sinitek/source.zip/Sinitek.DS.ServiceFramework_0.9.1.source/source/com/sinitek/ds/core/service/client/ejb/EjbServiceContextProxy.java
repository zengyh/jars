// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EjbServiceContextProxy.java

package com.sinitek.ds.core.service.client.ejb;

import com.sinitek.base.common.BaseException;
import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceClientException;
import com.sinitek.ds.core.service.impl.ejb.ServiceContext;
import com.sinitek.ds.core.service.impl.ejb.ServiceContextHome;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class EjbServiceContextProxy
    implements IServiceContext
{

    public EjbServiceContextProxy(String jndi, String url, Properties jndiProperties, int checkTime, boolean checkFlag)
    {
        initFlag = false;
        this.checkTime = checkTime;
        this.jndi = jndi;
        this.jndiProperties = jndiProperties;
        this.url = url;
        this.checkFlag = checkFlag;
    }

    public IServiceResponse handleService(IServiceRequest request)
        throws ServiceException
    {
        synchronized(this)
        {
            if(!initFlag)
            {
                init();
                initFlag = true;
            }
        }
        if(serviceContext == null)
            reconn();
        try
        {
            return serviceContext.handleService(request);
        }
        catch(Exception ex)
        {
            throw new ServiceClientException("0002", ex, new Object[] {
                url, jndi
            });
        }
    }

    private void init()
        throws ServiceClientException
    {
        try
        {
            ServiceContext _serviceContext = createRemoteObject();
            _serviceContext.testCall();
            serviceContext = _serviceContext;
            Timer timer = new Timer(true);
            TimerTask task = new TimerTask() {

                public void run()
                {
                    if(serviceContext != null)
                        try
                        {
                            serviceContext.testCall();
                            EjbServiceContextProxy.LOGGER.debug("\u6D4B\u8BD5\u8C03\u7528\u8FDC\u7A0B\u5BF9\u8C61\u6210\u529F");
                        }
                        catch(Exception ex)
                        {
                            EjbServiceContextProxy.LOGGER.error("\u6D4B\u8BD5\u8C03\u7528\u8FDC\u7A0B\u5BF9\u8C61\u5931\u8D25", ex);
                            serviceContext = null;
                            reconn();
                        }
                    else
                        reconn();
                }

            
            {
                super();
            }
            }
;
            timer.schedule(task, checkTime, checkTime);
        }
        catch(BaseException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            throw new ServiceClientException("0003", ex, new Object[] {
                url, jndi
            });
        }
    }

    private void reconn()
    {
        LOGGER.debug("\u5C1D\u8BD5\u91CD\u65B0\u83B7\u53D6\u8FDC\u7A0B\u5BF9\u8C61");
        try
        {
            serviceContext = createRemoteObject();
        }
        catch(Exception e)
        {
            LOGGER.error("\u5C1D\u8BD5\u91CD\u65B0\u83B7\u53D6\u8FDC\u7A0B\u5BF9\u8C61\u5931\u8D25", e);
        }
    }

    private ServiceContext createRemoteObject()
    {
        try
        {
            Properties prop = new Properties();
            prop.putAll(jndiProperties);
            if(StringUtils.isNotEmpty(url))
                prop.setProperty("java.naming.provider.url", url);
            Context ctx = new InitialContext(prop);
            Object obj = ctx.lookup(jndi);
            ServiceContextHome home = (ServiceContextHome)PortableRemoteObject.narrow(obj, com.sinitek.ds.core.service.impl.ejb.ServiceContextHome.class);
            ServiceContext ret = home.create();
            LOGGER.info("\u83B7\u53D6\u8FDC\u7A0B\u5BF9\u8C61\u6210\u529F");
            return ret;
        }
        catch(Exception ex)
        {
            throw new ServiceClientException("0003", ex, new Object[] {
                url, jndi
            });
        }
    }

    private String url;
    private String jndi;
    private int checkTime;
    private boolean checkFlag;
    private boolean initFlag;
    private Properties jndiProperties;
    private ServiceContext serviceContext;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = Logger.getLogger(com.sinitek.ds.core.service.client.ejb.EjbServiceContextProxy.class);
    }




}
