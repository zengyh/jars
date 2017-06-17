// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoHandler.java

package com.sinitek.ds.core.service.webtest.demoservice;

import com.sinitek.base.common.BaseException;
import com.sinitek.ds.core.service.*;

public class DemoHandler extends AbstractServiceHandler
{

    public DemoHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u6210\u529F");
        String _temp = request.getString("String");
        response.addParam("input", _temp != null ? ((Object) (_temp)) : "null");
        response.addParam("show", "\u4E2D\u6587\u8FD4\u56DE\u503C");
        return response;
    }
}
