// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterContextListener.java

package com.sinitek.base.starter.support;

import com.sinitek.base.starter.StarterContext;
import com.sinitek.base.starter.StarterException;
import com.sinitek.base.starter.config.StarterConfigReader;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

public class StarterContextListener
    implements ServletContextListener
{

    public StarterContextListener()
    {
    }

    public void contextInitialized(ServletContextEvent event)
    {
        String configFile = event.getServletContext().getInitParameter("starterconfigfile");
        if(StringUtils.isEmpty(configFile))
            configFile = "/WEB-INF/starterconfig.xml";
        InputStream fin = event.getServletContext().getResourceAsStream(configFile);
        if(fin == null)
            throw new StarterException("0002", new Object[] {
                configFile
            });
        try
        {
            StarterContext.start(StarterConfigReader.readConfig(fin));
        }
        catch(IOException e)
        {
            LOGGER.error("load /WEB-INF/starterconfig.xml failed", e);
        }
        catch(DocumentException e)
        {
            LOGGER.error("load /WEB-INF/starterconfig.xml failed", e);
        }
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/starter/support/StarterContextListener);

}
