// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyDemoServlet.java

package com.sinitek.sirm.web.routine.document;

import com.sinitek.sirm.common.utils.HttpUtils;
import javax.servlet.http.HttpServletRequest;
import org.elfinder.servlets.config.AbstractConnectorConfig;

// Referenced classes of package com.sinitek.sirm.web.routine.document:
//            AbstractConnectorServlet, MyDemoConfig

public class MyDemoServlet extends AbstractConnectorServlet
{

    public MyDemoServlet()
    {
    }

    protected AbstractConnectorConfig prepareConfig(HttpServletRequest request)
    {
        return new MyDemoConfig();
    }

    public static String SHARED_DOCS = "Shared docs";
    public static String THUMBNAIL = HttpUtils.getJsUrl("/routine/document/thumbnailer.action?p=");
    public static String HOME_SHARED_DOCS = "/sirmapp/tmp/shared_docs";
    public static String REALOBJECTURL = HttpUtils.getJsUrl("/routine/document/virtualproxy.action");

}
