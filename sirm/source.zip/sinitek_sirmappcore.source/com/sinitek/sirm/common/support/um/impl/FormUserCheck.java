// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FormUserCheck.java

package com.sinitek.sirm.common.support.um.impl;

import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.support.um.*;
import com.sinitek.sirm.common.web.UserCheckUtils;
import com.sinitek.spirit.um.client.*;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FormUserCheck
    implements IUserCheck
{

    public FormUserCheck()
    {
    }

    public UserCheckResult checkSession(UserCheckContext context)
    {
        UserCheckResult result = new UserCheckResult();
        HttpServletRequest request = context.getRequest();
        Principal principal = request.getUserPrincipal();
        boolean isok = principal != null && !StringUtils.isBlank(principal.getName());
        if(isok)
        {
            String username = principal.getName();
            UserSession umsession = UMFactory.getUMClient().getSession();
            if(umsession == null)
            {
                String userSession = (new StringBuilder()).append("_").append(username).append("_").toString();
                if(request.getSession().getAttribute(userSession) == null)
                {
                    String pwd = SettingUtils.getStringValue("ORG", "PASSWORD");
                    result = UserCheckUtils.logon(username, pwd);
                    request.getSession().setAttribute(userSession, Integer.valueOf(1));
                } else
                {
                    request.getSession().removeAttribute(userSession);
                    result.setResult(10);
                }
            } else
            {
                result.setUserSession(umsession);
                result.setResult(11);
                result.setSuccess(1);
            }
        } else
        {
            result.setResult(10);
        }
        return result;
    }

    public boolean afterCheckSession(UserCheckContext context)
    {
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        boolean result = true;
        String uri = request.getRequestURI();
        String logonurl = getLogonUrl(request);
        if(uri.equals(logonurl))
        {
            result = false;
            try
            {
                response.sendRedirect(getFirstUrl(request));
            }
            catch(IOException e)
            {
                LOGGER.error("afterchecksession", e);
            }
        }
        return result;
    }

    private static String getLogonUrl(HttpServletRequest request)
    {
        String logonurl = SettingUtils.getLocalSetting("app.logonurl");
        if(logonurl == null)
            logonurl = "/logon.jsp";
        return logonurl;
    }

    private static String getFirstUrl(HttpServletRequest request)
    {
        String logonurl = SettingUtils.getLocalSetting("app.firsturl");
        if(logonurl == null)
            logonurl = "/first.jsp";
        return logonurl;
    }

    public UserCheckResult logon(UserCheckContext context)
    {
        return UserCheckUtils.logonUser(context);
    }

    public boolean canChangePassword(UserCheckContext context)
    {
        return false;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/um/impl/FormUserCheck);

}
