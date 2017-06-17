// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserCheckResult.java

package com.sinitek.sirm.common.support.um;

import com.sinitek.spirit.um.client.UserSession;

public class UserCheckResult
{

    public UserCheckResult()
    {
        result = 0;
        success = 0;
        userSession = null;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public int getSuccess()
    {
        return success;
    }

    public void setSuccess(int success)
    {
        this.success = success;
    }

    public UserSession getUserSession()
    {
        return userSession;
    }

    public void setUserSession(UserSession userSession)
    {
        this.userSession = userSession;
    }

    public static final int CHECK_SESSION_SUCCESS = 11;
    public static final int CHECK_SESSION_FAILED = 10;
    public static final int CHECK_LOGON_INVALIDUSER = 20;
    public static final int CHECK_LOGON_LOCKUSER = 21;
    public static final int CHECK_LOGON_INSERVICE = 22;
    public static final int CHECK_LOGON_LDAPURL = 23;
    public static final int CHECK_LOGON_NOSUCHUSER = 24;
    public static final int CHECK_LDAP_PARAM = 26;
    private int result;
    private int success;
    private UserSession userSession;
}
