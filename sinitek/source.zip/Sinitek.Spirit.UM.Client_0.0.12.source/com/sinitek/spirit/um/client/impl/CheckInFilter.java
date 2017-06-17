// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckInFilter.java

package com.sinitek.spirit.um.client.impl;

import java.io.IOException;
import javax.servlet.*;

// Referenced classes of package com.sinitek.spirit.um.client.impl:
//            UMClientImpl, RequestListener

public class CheckInFilter
    implements Filter
{

    public CheckInFilter()
    {
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        requestListener = UMClientImpl.getInstance();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        requestListener.requestComingIn(servletRequest, servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
        requestListener.requestGoingOut(servletRequest, servletResponse);
        break MISSING_BLOCK_LABEL_49;
        Exception exception;
        exception;
        requestListener.requestGoingOut(servletRequest, servletResponse);
        throw exception;
    }

    public void destroy()
    {
    }

    private RequestListener requestListener;
}
