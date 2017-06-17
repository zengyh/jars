// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetRelationshipServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class GetRelationshipServiceHandler extends AbstractServiceHandler
{

    public GetRelationshipServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        Map param = new HashMap();
        StringBuffer sql = new StringBuffer("select rela.RELATIONTYPE, rela.version as relaversion, rela.objid as metadbobjid,\n       fromobj.ORGID as fromobjid, fromobj.DESCRIPTION as fromdesc, fromobj.ORGNAME as fromname, \n       fromobj.UNITTYPE as fromunittype, fromobj.INSERVICE as frominservice, fromobj.OBJECTTYPE as fromobjecttype, \n       fromobj.USERID as fromuserid, fromobj.version as fromversion,\n       toobj.ORGID as toobjid, toobj.DESCRIPTION as todesc, toobj.ORGNAME as toname, \n       toobj.UNITTYPE as tounittype, toobj.INSERVICE as toinservice, toobj.OBJECTTYPE as toobjecttype, \n       toobj.USERID as touserid, toobj.version as toversion\nfrom sprt_orgrela rela, sprt_orgobject fromobj, sprt_orgobject toobj\nwhere rela.FROMOBJECTID = fromobj.ORGID\n      and rela.TOOBJECTID = toobj.orgid \n");
        String orgid = request.getString("orgid");
        if(StringUtils.isNotBlank(orgid))
        {
            sql.append("      and (fromobj.orgid=:orgid or toobj.orgid= :orgid )");
            param.put("orgid", orgid);
        } else
        {
            String fromOrgId = request.getString("fromorgid");
            String toOrgId = request.getString("toorgid");
            String type = request.getString("relationtype");
            if(StringUtils.isBlank(fromOrgId) && StringUtils.isBlank(toOrgId) && StringUtils.isBlank(type))
                return SimpleServiceResponse.createFailResponse("00000001", "\u4F7F\u7528\u6A21\u7CCA\u67E5\u8BE2\u65F6\uFF0Cfromobj\uFF0Ctoobj\uFF0Ctype\u4E0D\u80FD\u540C\u65F6\u4E3A\u7A7A");
            if(StringUtils.isNotBlank(fromOrgId))
            {
                sql.append("      and fromobj.orgid=:fromobjid");
                param.put("fromobjid", fromOrgId);
            }
            if(StringUtils.isNotBlank(toOrgId))
            {
                sql.append("      and toobj.orgid=:toobjid");
                param.put("toobjid", toOrgId);
            }
            if(StringUtils.isNotBlank(type))
            {
                sql.append("      and rela.RELATIONTYPE=:type");
                param.put("type", type);
            }
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        List result = query.getResult();
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        SimpleParamObject data;
        for(Iterator i$ = result.iterator(); i$.hasNext(); response.addRecord(data))
        {
            Map row = (Map)i$.next();
            data = new SimpleParamObject();
            data.addParam("relationtype", row.get("RELATIONTYPE"));
            data.addParam("relaversion", Integer.valueOf(((Number)row.get("relaversion")).intValue()));
            data.addParam("metadbobjid", Integer.valueOf(((Number)row.get("metadbobjid")).intValue()));
            data.addParam("fromobjid", row.get("fromobjid"));
            data.addParam("fromdesc", row.get("fromdesc"));
            data.addParam("fromname", row.get("fromname"));
            data.addParam("fromunittype", row.get("fromunittype"));
            data.addParam("frominservice", row.get("frominservice") != null ? ((Object) (Boolean.valueOf("1".equals(row.get("frominservice"))))) : null);
            data.addParam("fromobjecttype", Integer.valueOf(((Number)row.get("fromobjecttype")).intValue()));
            data.addParam("fromversion", Integer.valueOf(((Number)row.get("fromversion")).intValue()));
            data.addParam("fromuserid", row.get("fromuserid"));
            data.addParam("toobjid", row.get("toobjid"));
            data.addParam("todesc", row.get("todesc"));
            data.addParam("toname", row.get("toname"));
            data.addParam("tounittype", row.get("tounittype"));
            data.addParam("toinservice", row.get("toinservice") != null ? ((Object) (Boolean.valueOf("1".equals(row.get("toinservice"))))) : null);
            data.addParam("toobjecttype", Integer.valueOf(((Number)row.get("toobjecttype")).intValue()));
            data.addParam("toversion", Integer.valueOf(((Number)row.get("toversion")).intValue()));
            data.addParam("touserid", row.get("touserid"));
        }

        return response;
    }
}
