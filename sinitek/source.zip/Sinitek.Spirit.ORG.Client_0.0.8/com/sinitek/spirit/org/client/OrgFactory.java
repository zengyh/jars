// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgFactory.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.org.core.IOrgUpdater;

// Referenced classes of package com.sinitek.spirit.org.client:
//            OrgFinderImpl, OrgUpdaterImpl

public class OrgFactory
{

    public OrgFactory()
    {
    }

    public static IOrgFinder getOrgFinder()
    {
        return new OrgFinderImpl();
    }

    public static IOrgUpdater getOrgUpdater()
    {
        return new OrgUpdaterImpl();
    }
}
