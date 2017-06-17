// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalUserCheck.java

package com.sinitek.sirm.common.support.um.impl;

import com.sinitek.sirm.common.support.um.*;
import com.sinitek.sirm.common.web.UserCheckUtils;

public class LocalUserCheck extends BaseUserCheck
{

    public LocalUserCheck()
    {
    }

    public boolean afterCheckSession(UserCheckContext context)
    {
        return true;
    }

    public UserCheckResult logon(UserCheckContext context)
    {
        return UserCheckUtils.logonUser(context);
    }

    public boolean canChangePassword(UserCheckContext context)
    {
        return true;
    }
}
