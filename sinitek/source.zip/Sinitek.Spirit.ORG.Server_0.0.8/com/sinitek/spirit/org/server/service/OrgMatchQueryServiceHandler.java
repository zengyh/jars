// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgMatchQueryServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException, IAtomicQueryExpression, AtomicQueryExpressionFactory

/**
 * @deprecated Class OrgMatchQueryServiceHandler is deprecated
 */

public class OrgMatchQueryServiceHandler extends AbstractServiceHandler
{

    public OrgMatchQueryServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        String fromorgid = request.getString("fromorgid");
        String targetOrgId = request.getString("targetorgid");
        IOrgObject fromOrg = (IOrgObject)getMetaDBContext().get("ORGOBJECT", "orgid", fromorgid);
        IOrgObject targetOrg = (IOrgObject)getMetaDBContext().get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", targetOrgId);
        if(fromOrg == null)
            throw new OrgServiceException("1001", new Object[] {
                fromorgid
            });
        if(targetOrgId == null)
            throw new OrgServiceException("1001", new Object[] {
                targetOrgId
            });
        String queryData = request.getString("querystr");
        List atomicQueryExpressions = AtomicQueryExpressionFactory.parseExpressions(queryData, logger);
        List inputList = new ArrayList();
        List outputList = new ArrayList();
        inputList.add(fromOrg);
        Iterator i$ = atomicQueryExpressions.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IAtomicQueryExpression expression = (IAtomicQueryExpression)i$.next();
            expression.query(inputList, outputList);
            inputList.clear();
            if(outputList.isEmpty())
                break;
            if(outputList.contains(targetOrg))
            {
                response.addParam("match", Boolean.TRUE);
                return response;
            }
            inputList.addAll(outputList);
            outputList.clear();
        } while(true);
        response.addParam("match", Boolean.FALSE);
        return response;
    }
}
