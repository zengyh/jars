// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DummyHandler.java

package com.sinitek.ds.core.service;


// Referenced classes of package com.sinitek.ds.core.service:
//            AbstractServiceHandler, SimpleServiceResponse, IServiceCoreRequest, IServiceResponse

public class DummyHandler extends AbstractServiceHandler
{

    public DummyHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
    {
        return SimpleServiceResponse.createSuccessResponse("\u6210\u529F");
    }
}
