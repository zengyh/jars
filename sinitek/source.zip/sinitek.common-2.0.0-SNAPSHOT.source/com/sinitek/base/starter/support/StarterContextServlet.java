// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterContextServlet.java

package com.sinitek.base.starter.support;

import com.sinitek.base.starter.StarterContext;
import com.sinitek.base.starter.config.StarterConfigReader;
import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.commons.lang.StringUtils;

public class StarterContextServlet extends HttpServlet
{

    public StarterContextServlet()
    {
    }

    public void init()
        throws ServletException
    {
        super.init();
        String configFile = getServletContext().getInitParameter("starterconfigfile");
        if(StringUtils.isEmpty(configFile))
            configFile = "/WEB-INF/starterconfig.xml";
        File file = new File(getServletContext().getRealPath(configFile));
        if(!file.exists() || file.isDirectory())
        {
            throw new ServletException((new StringBuilder()).append("\u542F\u52A8\u5668\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u627E\u4E0D\u5230").toString());
        } else
        {
            StarterContext.start(StarterConfigReader.readConfig(file));
            return;
        }
    }
}
