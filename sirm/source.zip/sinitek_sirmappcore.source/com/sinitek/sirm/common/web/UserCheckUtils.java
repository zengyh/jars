// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserCheckUtils.java

package com.sinitek.sirm.common.web;

import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.support.um.*;
import com.sinitek.sirm.common.support.um.impl.*;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.ObjectUtils;
import com.sinitek.sirm.org.utils.OrgUtils;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.IEmployee;
import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.um.*;
import com.sinitek.spirit.um.client.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.web:
//            RequestContext

public class UserCheckUtils
{

    public UserCheckUtils()
    {
    }

    public static UserCheckResult checkSession()
    {
        UserCheckResult _result = new UserCheckResult();
        UserSession _session = UMFactory.getUMClient().getSession();
        if(_session == null)
        {
            _result.setResult(10);
        } else
        {
            _result.setUserSession(_session);
            _result.setResult(11);
            _result.setSuccess(1);
        }
        return _result;
    }

    public static IUserCheck userCheck(String username)
    {
        if("$admin$".equals(username))
            return new LocalUserCheck();
        IUserCheck checker = null;
        String str = SettingUtils.getLocalSetting("usercheckmode");
        int mode = NumberTool.safeToInteger(str, Integer.valueOf(0)).intValue();
        if(mode == 1)
            checker = new FormUserCheck();
        else
        if(mode == 2)
            checker = new LdapUserCheck();
        else
        if(mode == 3)
        {
            String location = SettingUtils.getLocalSetting("usercheckclassname");
            if(location != null && !"".equals(location))
                checker = (IUserCheck)ObjectUtils.newInstance(location);
        } else
        {
            checker = new LocalUserCheck();
        }
        return checker;
    }

    public static UserCheckResult checkSession(UserCheckContext context)
    {
        IUserCheck checker = userCheck(context.getUsername());
        UserCheckResult result = checker.checkSession(context);
        if(result != null && result.getSuccess() == 1)
        {
            UserSession session = result.getUserSession();
            if(session != null)
                try
                {
                    int interval = NumberTool.safeToInteger(SettingUtils.getLocalSetting("userchecktimeout"), Integer.valueOf(30)).intValue() * 60;
                    session.setMaxInactiveInterval(interval);
                }
                catch(InvalidSessionException e)
                {
                    LOGGER.warn("setMaxInactiveInterval failed.", e);
                }
        }
        return result;
    }

    public static boolean afterCheckSession(UserCheckContext context)
    {
        IUserCheck checker = userCheck(context.getUsername());
        boolean result = checker.afterCheckSession(context);
        return result;
    }

    private static UserCheckResult invalidateUser(String username, String pwd)
    {
        UserCheckResult _result = new UserCheckResult();
        try
        {
            UserCheckContext context = new UserCheckContext();
            context.setUsername(username);
            context.setPassword(pwd);
            IUserCheck checker = userCheck(username);
            _result = checker.logon(context);
            if(_result != null && _result.getUserSession() != null && _result.getSuccess() == 1)
            {
                UMClient client = UMFactory.getUMClient();
                String userid = client.getUserByName(username);
                if(userid != null && !"".equals(userid))
                    _result.getUserSession().invalidateUserSessions(userid);
            }
        }
        catch(InvalidSessionException e)
        {
            LOGGER.error("invalidateUser failed", e);
            _result.setResult(10);
        }
        catch(InsufficientPrivilegesException e)
        {
            LOGGER.error("invalidateUser failed", e);
            _result.setResult(10);
        }
        catch(NoSuchUserException e)
        {
            LOGGER.error("invalidateUser failed", e);
            _result.setResult(10);
        }
        return _result;
    }

    public static UserCheckResult logon(String username, String userpwd)
    {
        UserCheckResult _result = new UserCheckResult();
        if(username != null && userpwd != null)
        {
            Map map = new HashMap();
            map.put("username", username);
            map.put("userpwd", userpwd);
            map.put("kickmode", "1");
            _result = logon(map);
        } else
        {
            _result.setResult(20);
        }
        return _result;
    }

    public static UserCheckResult logon(Map map)
    {
        UserCheckResult _result = new UserCheckResult();
        if(map != null)
        {
            if(map.get("username") != null && map.get("userpwd") != null)
            {
                String username = String.valueOf(map.get("username"));
                String userpwd = String.valueOf(map.get("userpwd"));
                String kick = String.valueOf(map.get("kickmode"));
                if(map.get("kickmode") != null && "1".equals(kick))
                {
                    String kickMode = SettingUtils.getStringValue("COMMON", "KICKMODE");
                    if(kickMode != null && "1".equals(kickMode))
                    {
                        _result = invalidateUser(username, userpwd);
                        if(_result.getSuccess() != 1)
                            return _result;
                    } else
                    {
                        LOGGER.info("\u5982\u679C\u9700\u8981\u5355\u70B9\u6A21\u5F0F\u767B\u9646\u8BF7\u914D\u7F6E\u6A21\u5757\u540D\u4E3A\u3010COMMON\u3011\u53C2\u6570\u540D\u4E3A\u3010KICKMODE\u3011\u7684\u53C2\u6570\u503C\u4E3A\u30101\u3011");
                    }
                }
                UserCheckContext context = new UserCheckContext();
                context.setUsername(username);
                context.setPassword(userpwd);
                IUserCheck checker = userCheck(username);
                _result = checker.logon(context);
                if(!"$admin$".equalsIgnoreCase(username) && _result != null && _result.getUserSession() != null && _result.getSuccess() == 1)
                {
                    UMClient client = UMFactory.getUMClient();
                    String userid = client.getUserByName(username);
                    if(!"".equals(userid) && userid != null)
                    {
                        IEmployee employee = OrgFactory.getOrgFinder().getEmployeeByUserId(userid);
                        if(employee != null)
                        {
                            if(!employee.isInservice().booleanValue())
                            {
                                _result.setSuccess(0);
                                _result.setResult(22);
                                client.getSession().invalidate();
                            }
                        } else
                        {
                            _result.setSuccess(0);
                            _result.setResult(10);
                            client.getSession().invalidate();
                        }
                    }
                }
            } else
            {
                _result.setResult(20);
            }
        } else
        {
            _result.setResult(20);
        }
        return _result;
    }

    public static UserCheckResult logonUser(UserCheckContext context)
    {
        UserCheckResult _result = new UserCheckResult();
        UMClient client = UMFactory.getUMClient();
        try
        {
            UserSession umsession = client.openSession(context.getUsername(), context.getPassword());
            _result.setUserSession(umsession);
            _result.setSuccess(1);
            try
            {
                RequestContext.log(umsession.getUserId(), "\u7528\u6237\u767B\u5F55", 4, "\u767B\u5F55\u7CFB\u7EDF");
            }
            catch(Exception ex)
            {
                LOGGER.error(com/sinitek/sirm/common/web/UserCheckUtils, ex);
            }
        }
        catch(NoSuchUserException e)
        {
            _result.setResult(20);
        }
        catch(LockedUserException e)
        {
            _result.setResult(21);
        }
        catch(AuthenticationFailedException e)
        {
            _result.setResult(20);
        }
        return _result;
    }

    public static void lockUser(String username)
        throws NoSuchUserException
    {
        OrgUtils.lockUser(username);
    }

    public static boolean canChangePassword(UserCheckContext context)
    {
        IUserCheck checker = userCheck(context.getUsername());
        boolean result = checker.canChangePassword(context);
        return result;
    }

    static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/web/UserCheckUtils);

}
