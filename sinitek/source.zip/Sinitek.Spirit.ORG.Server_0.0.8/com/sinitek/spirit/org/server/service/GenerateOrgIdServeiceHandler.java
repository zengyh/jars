// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenerateOrgIdServeiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class GenerateOrgIdServeiceHandler extends AbstractServiceHandler
{

    public GenerateOrgIdServeiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        IMetaDBQuery query = getMetaDBContext().createSqlQuery("select seq_orgid.nextval as newid from dual");
        int nextId = ((Number)((Map)query.getResult().get(0)).get("newid")).intValue();
        DecimalFormat format = new DecimalFormat("000000");
        StringBuffer id = new StringBuffer("999");
        id.append(format.format(nextId));
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u751F\u6210\u6210\u529F");
        response.addParam("orgid", id.toString());
        return response;
    }
}
