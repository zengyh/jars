// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckOrgObjectExistsServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;

public class CheckOrgObjectExistsServiceHandler extends AbstractServiceHandler
{

    public CheckOrgObjectExistsServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        response.addParam("exists", Boolean.valueOf(getMetaDBContext().get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", request.getString("orgid")) != null));
        return response;
    }
}
