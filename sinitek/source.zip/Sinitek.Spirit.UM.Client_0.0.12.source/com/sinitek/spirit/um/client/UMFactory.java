// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UMFactory.java

package com.sinitek.spirit.um.client;

import com.sinitek.spirit.um.client.impl.UMClientImpl;

// Referenced classes of package com.sinitek.spirit.um.client:
//            UMClient

public class UMFactory
{

    public UMFactory()
    {
    }

    public static UMClient getUMClient()
    {
        return UMClientImpl.getInstance();
    }
}
