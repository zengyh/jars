// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmAction.java

package com.sinitek.sirm.common.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sinitek.sirm.common.utils.HttpUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.*;

public class SirmAction extends ActionSupport
    implements SessionAware, ServletRequestAware, ServletResponseAware, Preparable
{

    public void setSession(Map session)
    {
        this.session = session;
    }

    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public void prepare()
        throws Exception
    {
    }

    protected String getParameter(String key)
    {
        return request.getParameter(key);
    }

    public String execute()
        throws Exception
    {
        return super.execute();
    }

    public SirmAction()
    {
    }

    protected void removeCookiesWhenLogout()
    {
        String cookies[] = {
            "jc_autologon", "jc_userid", "jc_username", "username", "lastchecktime", "sso_username", "sso_logontime"
        };
        HttpUtils.removeCookies(response, cookies);
    }

    private static final long serialVersionUID = 1000L;
    protected Map session;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
}
