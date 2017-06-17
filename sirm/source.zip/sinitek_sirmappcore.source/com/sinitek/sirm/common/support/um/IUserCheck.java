// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserCheck.java

package com.sinitek.sirm.common.support.um;


// Referenced classes of package com.sinitek.sirm.common.support.um:
//            UserCheckContext, UserCheckResult

public interface IUserCheck
{

    public abstract UserCheckResult checkSession(UserCheckContext usercheckcontext);

    public abstract boolean afterCheckSession(UserCheckContext usercheckcontext);

    public abstract UserCheckResult logon(UserCheckContext usercheckcontext);

    public abstract boolean canChangePassword(UserCheckContext usercheckcontext);
}
