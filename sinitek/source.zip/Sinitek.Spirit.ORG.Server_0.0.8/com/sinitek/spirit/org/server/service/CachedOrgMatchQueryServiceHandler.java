// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CachedOrgMatchQueryServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.cache.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException, IAtomicQueryExpression, AtomicQueryExpressionImpl, AtomicQueryExpressionFactory, 
//            QueryCondition

public class CachedOrgMatchQueryServiceHandler extends AbstractServiceHandler
{

    public CachedOrgMatchQueryServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        String fromOrgId = request.getString("fromorgid");
        String targetOrgId = request.getString("targetorgid");
        IOrgObject fromOrg = (IOrgObject)getMetaDBContext().get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", fromOrgId);
        IOrgObject targetOrg = (IOrgObject)getMetaDBContext().get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", targetOrgId);
        if(fromOrg == null)
            throw new OrgServiceException("1001", new Object[] {
                fromOrgId
            });
        if(targetOrg == null)
            throw new OrgServiceException("1001", new Object[] {
                targetOrgId
            });
        String queryData = request.getString("querystr");
        List atomicQueryExpressions = AtomicQueryExpressionFactory.parseExpressions(queryData, logger);
        Map result = new HashMap();
        result.put(fromOrg.getOrgId(), fromOrg);
        Iterator i$ = atomicQueryExpressions.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IAtomicQueryExpression iExpression = (IAtomicQueryExpression)i$.next();
            AtomicQueryExpressionImpl expression = (AtomicQueryExpressionImpl)iExpression;
            int level = 1;
            QueryCondition queryConditions[] = expression.getQueryCondition();
            int i = 0;
            do
            {
                if(i >= queryConditions.length)
                    break;
                if(queryConditions[i] == QueryCondition.LEVEL)
                {
                    Object value = expression.getQueryValue()[i];
                    level = ((Integer)value).intValue();
                    break;
                }
                i++;
            } while(true);
            result = getResultRecursively(result.values(), expression, level);
            if(result.isEmpty())
                break;
            if(result.containsKey(targetOrg.getOrgId()))
            {
                response.addParam("match", Boolean.TRUE);
                return response;
            }
        } while(true);
        response.addParam("match", Boolean.FALSE);
        return response;
    }

    private Map getResultRecursively(Collection inputList, AtomicQueryExpressionImpl expression, int level)
    {
        Map resultMap = new HashMap();
        for(Iterator i$ = inputList.iterator(); i$.hasNext();)
        {
            IOrgObject orgObject = (IOrgObject)i$.next();
            com.sinitek.spirit.org.server.cache.OrgQuery orgQuery = new OrgQueryImpl(orgObject, expression);
            List result = OrgCacheManager.getInstance().getCache(orgQuery).getResult();
            Iterator i$ = result.iterator();
            while(i$.hasNext()) 
            {
                IOrgObject object = (IOrgObject)i$.next();
                resultMap.put(object.getOrgId(), object);
            }
        }

        level--;
        if(!resultMap.isEmpty() && level > 0)
            resultMap.putAll(getResultRecursively(resultMap.values(), expression, level));
        return resultMap;
    }
}
