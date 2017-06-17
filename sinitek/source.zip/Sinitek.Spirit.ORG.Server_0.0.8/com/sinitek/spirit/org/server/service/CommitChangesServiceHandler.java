// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommitChangesServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.cache.OrgCacheManager;
import com.sinitek.spirit.org.server.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException

public class CommitChangesServiceHandler extends AbstractServiceHandler
{

    public CommitChangesServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        OrgCacheManager.getInstance();
        String dataStr = request.getString("data");
        String dataRows[] = StringUtils.split(dataStr, ';');
        logger.debug((new StringBuilder()).append("\u4F30\u8BA1\u6709[").append(dataRows.length).append("]\u884C\u6570\u636E\u9700\u63D0\u4EA4\u6570\u636E\u5E93").toString());
        String arr$[] = dataRows;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String dataRow = arr$[i$];
            logger.debug((new StringBuilder()).append("\u5F00\u59CB\u5904\u7406\u6570\u636E[").append(dataRow).append("]").toString());
            String details[] = StringUtils.split(dataRow, "|");
            if(details.length != 5)
                throw new OrgServiceException("1002", new Object[] {
                    dataRow
                });
            if("update".equalsIgnoreCase(details[0]))
            {
                IMetaObject mo = loadMoByDetail(dataRow, details);
                checkVersion(mo, dataRow, details);
                setMoProperites(mo, dataRow, details);
                logger.debug((new StringBuilder()).append("\u51C6\u5907\u66F4\u65B0\u5B9E\u4F53\u5BF9\u8C61[").append(mo.getEntityName()).append("],ID[").append(mo.getId()).append("]").toString());
                mo.save();
            } else
            if("insert".equalsIgnoreCase(details[0]))
            {
                IMetaObject mo = createEmptyMo(dataRow, details);
                setMoProperites(mo, dataRow, details);
                logger.debug((new StringBuilder()).append("\u51C6\u5907\u4FDD\u5B58\u65B0\u5B9E\u4F53\u5BF9\u8C61[").append(mo.getEntityName()).append("],ID[").append(mo.getId()).append("]").toString());
                mo.save();
            } else
            if("delete".equalsIgnoreCase(details[0]))
            {
                IMetaObject mo = loadMoByDetail(dataRow, details);
                checkVersion(mo, dataRow, details);
                if("ORGOBJECT".equalsIgnoreCase(mo.getEntityName()))
                {
                    String sql = "select * from SPRT_ORGRELA rela\nwhere (rela.FROMOBJECTID = :orgid or rela.TOOBJECTID=:orgid)\n\t  and exists ( select * from sprt_orgobject where orgid=rela.fromobjectid)\n\t  and exists ( select * from sprt_orgobject where orgid=rela.toobjectid)";
                    IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
                    Object orgid = mo.get("orgid");
                    query.setParameter("orgid", orgid);
                    if(query.getResultCount() > 0)
                        throw new OrgServiceException("1010", new Object[] {
                            orgid
                        });
                }
                logger.debug((new StringBuilder()).append("\u51C6\u5907\u5220\u9664\u5B9E\u4F53[").append(mo.getEntityName()).append("],ID[").append(mo.getId()).append("]").toString());
                mo.remove();
            } else
            {
                throw new OrgServiceException("1003", new Object[] {
                    dataRow, details[0]
                });
            }
            logger.debug((new StringBuilder()).append("\u5904\u7406\u6570\u636E[").append(dataRow).append("]\u5B8C\u6210").toString());
        }

        return SimpleServiceResponse.createSuccessResponse("\u4FDD\u5B58\u6210\u529F");
    }

    private void checkVersion(IMetaObject mo, String dataRow, String details[])
    {
        int versionId = Integer.parseInt(details[4]);
        if(versionId >= 0 && versionId != mo.getVersion())
            throw new OrgServiceException("1007", new Object[] {
                dataRow, mo.getEntityName(), Integer.valueOf(mo.getId()), Integer.valueOf(mo.getVersion()), Integer.valueOf(versionId)
            });
        else
            return;
    }

    private void setMoProperites(IMetaObject mo, String dataRow, String details[])
    {
        String moData[] = details[3].split("[\\^]");
        if("OBJ".equals(details[1]))
        {
            IOrgObject orgObject = (IOrgObject)mo;
            if(StringUtils.isBlank(orgObject.getOrgId()))
                orgObject.setOrgId(moData[0]);
            orgObject.setOrgName(moData[1]);
            orgObject.setDescription("@NULL".equalsIgnoreCase(moData[2]) ? null : moData[2]);
            if(moData.length == 5)
            {
                orgObject.setUserId(moData[3]);
                orgObject.setObjectType(ObjectType.EMPLOYEE);
                orgObject.setInservice("@NULL".equalsIgnoreCase(moData[4]) ? null : Boolean.valueOf(moData[4]));
            } else
            if(moData.length == 4)
            {
                orgObject.setObjectType(ObjectType.UNIT);
                orgObject.setUnitType(moData[3]);
            } else
            {
                throw new OrgServiceException("1008", new Object[] {
                    dataRow, details[2]
                });
            }
        } else
        if("RELA".equals(details[1]))
        {
            if(moData.length == 3)
            {
                IOrgRelationInfo relationInfo = (IOrgRelationInfo)mo;
                relationInfo.setFromObjectId(moData[0]);
                relationInfo.setToObjectId(moData[1]);
                relationInfo.setRelationType(moData[2].toUpperCase());
            } else
            {
                throw new OrgServiceException("1009", new Object[] {
                    dataRow, details[2]
                });
            }
        } else
        {
            throw new OrgServiceException("1004", new Object[] {
                dataRow, details[0]
            });
        }
    }

    private IMetaObject loadMoByDetail(String dataRow, String details[])
    {
        if("OBJ".equals(details[1]))
        {
            String orgId = details[2];
            IMetaObject mo = getMetaDBContext().get("ORGOBJECT", "orgid", orgId);
            if(mo == null)
                throw new OrgServiceException("1005", new Object[] {
                    dataRow, orgId
                });
            else
                return mo;
        }
        if("RELA".equals(details[1]))
        {
            int objId = Integer.parseInt(details[2]);
            IMetaObject mo = getMetaDBContext().get("ORGRELATIONINFO", objId);
            if(mo == null)
                throw new OrgServiceException("1006", new Object[] {
                    dataRow, Integer.valueOf(objId)
                });
            else
                return mo;
        } else
        {
            throw new OrgServiceException("1004", new Object[] {
                dataRow, details[0]
            });
        }
    }

    private IMetaObject createEmptyMo(String dataRow, String details[])
    {
        if("OBJ".equals(details[1]))
            return new OrgObjectImpl();
        if("RELA".equals(details[1]))
            return new OrgRelationInfoImpl();
        else
            throw new OrgServiceException("1004", new Object[] {
                dataRow, details[0]
            });
    }
}
