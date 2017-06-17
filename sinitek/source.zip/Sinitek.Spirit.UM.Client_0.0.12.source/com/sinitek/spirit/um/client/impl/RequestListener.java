// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestListener.java

package com.sinitek.spirit.um.client.impl;

import java.io.IOException;
import javax.servlet.*;

interface RequestListener
{

    public abstract void requestComingIn(ServletRequest servletrequest, ServletResponse servletresponse)
        throws ServletException, IOException;

    public abstract void requestGoingOut(ServletRequest servletrequest, ServletResponse servletresponse)
        throws ServletException, IOException;
}
