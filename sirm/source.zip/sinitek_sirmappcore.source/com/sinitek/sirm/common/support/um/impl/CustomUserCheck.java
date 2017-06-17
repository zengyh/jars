// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomUserCheck.java

package com.sinitek.sirm.common.support.um.impl;

import com.sinitek.sirm.common.support.um.*;

// Referenced classes of package com.sinitek.sirm.common.support.um.impl:
//            LocalUserCheck, LdapUserCheck

public class CustomUserCheck extends BaseUserCheck
{

    public CustomUserCheck()
    {
    }

    public boolean afterCheckSession(UserCheckContext context)
    {
        return true;
    }

    public UserCheckResult logon(UserCheckContext context)
    {
        IUserCheck check = new LocalUserCheck();
        String username = context.getUsername();
        if("admin".equals(username))
            check = new LocalUserCheck();
        else
            check = new LdapUserCheck();
        UserCheckResult ucr = check.logon(context);
        return ucr;
    }

    public boolean canChangePassword(UserCheckContext context)
    {
        return false;
    }
}
