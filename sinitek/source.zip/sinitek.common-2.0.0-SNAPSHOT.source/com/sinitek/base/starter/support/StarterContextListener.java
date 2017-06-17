// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterContextListener.java

package com.sinitek.base.starter.support;

import com.sinitek.base.starter.StarterContext;
import com.sinitek.base.starter.StarterException;
import com.sinitek.base.starter.config.StarterConfigReader;
import java.io.File;
import javax.servlet.*;
import org.apache.commons.lang.StringUtils;

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
        File file = new File(event.getServletContext().getRealPath(configFile));
        if(!file.exists() || file.isDirectory())
        {
            throw new StarterException("0002", new Object[] {
                configFile
            });
        } else
        {
            StarterContext.start(StarterConfigReader.readConfig(file));
            return;
        }
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }
}
