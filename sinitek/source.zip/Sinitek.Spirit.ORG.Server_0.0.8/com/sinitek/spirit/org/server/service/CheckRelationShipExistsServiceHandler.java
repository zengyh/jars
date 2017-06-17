// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckRelationShipExistsServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class CheckRelationShipExistsServiceHandler extends AbstractServiceHandler
{

    public CheckRelationShipExistsServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String orgid = request.getString("orgid");
        String type = StringUtils.trimToNull(request.getString("relationtype"));
        if(StringUtils.isBlank(orgid))
        {
            String fromObjId = request.getString("fromorgid");
            String toObjId = request.getString("toorgid");
            SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
            Map queryParam = new HashMap();
            queryParam.put("fromobjectid", fromObjId);
            queryParam.put("toobjectid", toObjId);
            queryParam.put("relationtype", type);
            response.addParam("exists", Boolean.valueOf(getMetaDBContext().get("ORGRELATIONINFO", queryParam) != null));
            return response;
        }
        String sql = "select * from sprt_orgrela where (FromObjectId=:objid or TOOBJECTID=:objid)";
        Map param = new HashMap();
        if(StringUtils.isNotBlank(type))
        {
            sql = (new StringBuilder()).append(sql).append(" and RelationType=:type").toString();
            param.put("type", type);
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        param.put("objid", orgid);
        query.setParameters(param);
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        response.addParam("exists", Boolean.valueOf(query.getResultCount() > 0));
        return response;
    }
}
