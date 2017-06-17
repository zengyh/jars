// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoUserConfig.java

package com.sinitek.spirit.webcontrol.userconfig;

import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.spirit.webcontrol.userconfig:
//            ISpiritUserConfigAware

public class DemoUserConfig
    implements ISpiritUserConfigAware
{

    public DemoUserConfig()
    {
    }

    public Integer loadPageSize(String tableId, String className, HttpServletRequest request)
    {
        return null;
    }

    public void savePageSize(String s, String s1, Integer integer, HttpServletRequest httpservletrequest)
    {
    }
}
