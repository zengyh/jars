// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgQueryServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.entity.ObjectType;
import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException, IAtomicQueryExpression, AtomicQueryExpressionFactory

/**
 * @deprecated Class OrgQueryServiceHandler is deprecated
 */

public class OrgQueryServiceHandler extends AbstractServiceHandler
{

    public OrgQueryServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String fromorgid = request.getString("fromorgid");
        IOrgObject fromOrg = (IOrgObject)getMetaDBContext().get("ORGOBJECT", "orgid", fromorgid);
        if(fromOrg == null)
            throw new OrgServiceException("1001", new Object[] {
                fromorgid
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
            inputList.addAll(outputList);
            outputList.clear();
        } while(true);
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        for(Iterator i$ = inputList.iterator(); i$.hasNext();)
        {
            IOrgObject orgObject = (IOrgObject)i$.next();
            SimpleParamObject row = new SimpleParamObject();
            response.addRecord(row);
            row.addParam("orgid", orgObject.getOrgId());
            row.addParam("name", orgObject.getOrgName());
            row.addParam("description", orgObject.getDescription());
            row.addParam("versionid", Integer.valueOf(orgObject.getVersion()));
            row.addParam("orgtype", orgObject.getObjectType().getEnumItemName());
            if(ObjectType.EMPLOYEE == orgObject.getObjectType())
            {
                row.addParam("userid", orgObject.getUserId());
                row.addParam("inservice", orgObject.getInservice());
            } else
            {
                row.addParam("unittype", orgObject.getUnitType());
            }
        }

        return response;
    }
}
