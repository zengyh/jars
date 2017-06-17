// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseUserCheck.java

package com.sinitek.sirm.common.support.um;

import com.sinitek.sirm.common.support.LocalSetting;
import com.sinitek.sirm.common.utils.DateUtil;
import com.sinitek.sirm.common.utils.HttpUtils;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.common.web.UserCheckUtils;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.um.client.UMClient;
import com.sinitek.spirit.um.client.UMFactory;
import com.sinitek.spirit.um.client.UserSession;
import com.sinitek.util.ThreadLocalRegister;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.support.um:
//            UserCheckResult, IUserCheck, UserCheckContext

public abstract class BaseUserCheck
    implements IUserCheck
{

    public BaseUserCheck()
    {
    }

    public UserCheckResult checkSession(UserCheckContext context)
    {
        boolean ssoEnabled = LocalSetting.getSSOEnabled();
        boolean ssoMainhost = LocalSetting.getSSOMainhost();
        String ssoUsername = HttpUtils.getCookie(context.getRequest(), "sso_username");
        String ssoLogontime = HttpUtils.getCookie(context.getRequest(), "sso_logontime");
        Date logontime = null;
        if(StringUtils.isNotBlank(ssoLogontime))
            logontime = TimeUtil.formatDate(ssoLogontime, "yyyyMMddHHmmss");
        if(logontime == null)
            logontime = new Date();
        Date checktime = (Date)ThreadLocalRegister.getAttribute("usersessionchecktime");
        if(checktime == null)
        {
            int userchecktimeout = LocalSetting.getUserchecktimeout();
            checktime = DateUtil.addMinutes(logontime, userchecktimeout);
            ThreadLocalRegister.setAttribute("usersessionchecktime", checktime);
        }
        boolean isok = false;
        UserCheckResult _result = new UserCheckResult();
        UserSession _session = UMFactory.getUMClient().getSession();
        if(_session == null)
        {
            LOGGER.debug((new StringBuilder()).append("getsession failed.ssoEnabled:").append(ssoEnabled).append(";ssoMainhost:").append(ssoMainhost).append(";tid:").append(Thread.currentThread().getId()).toString());
            if(ssoEnabled && !ssoMainhost)
            {
                _session = UMFactory.getUMClient().getSession();
                if(_session == null)
                {
                    if(checktime.compareTo(new Date()) >= 0 && org.apache.commons.lang.StringUtils.isNotBlank(ssoUsername))
                    {
                        Employee employee = OrgServiceFactory.getOrgService().getEmployeeByUserName(ssoUsername);
                        if(employee != null)
                        {
                            LOGGER.debug((new StringBuilder()).append("ssoUsername.userid:").append(employee.getUserId()).toString());
                            UserCheckResult ucr = UserCheckUtils.logon("$admin$", "111111");
                            try
                            {
                                UserSession usersession = ucr.getUserSession();
                                _session = usersession.switchUser(employee.getUserId());
                                isok = _session != null;
                                LOGGER.debug((new StringBuilder()).append("switch user isok:").append(isok).toString());
                                if(isok)
                                    LOGGER.debug((new StringBuilder()).append("switched userid:").append(_session.getUserId()).toString());
                            }
                            catch(Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else
                {
                    isok = true;
                }
            }
        } else
        {
            isok = true;
        }
        if(isok)
        {
            _result.setUserSession(_session);
            _result.setResult(11);
            _result.setSuccess(1);
            if(ssoEnabled)
                HttpUtils.addCookie(context.getResponse(), "sso_logontime", TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss"), -1);
        } else
        {
            _result.setResult(10);
        }
        return _result;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/um/BaseUserCheck);

}
