// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IClientConfigInfo.java

package com.sinitek.ds.core.service.client;

import com.sinitek.ds.core.service.IServiceContext;

// Referenced classes of package com.sinitek.ds.core.service.client:
//            ServiceClientException

public interface IClientConfigInfo
{

    public abstract IServiceContext findContext(String s)
        throws ServiceClientException;
}
