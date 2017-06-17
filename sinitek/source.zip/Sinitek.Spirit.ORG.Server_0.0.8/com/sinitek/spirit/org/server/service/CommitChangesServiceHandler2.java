// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommitChangesServiceHandler2.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.cache.OrgCacheManager;
import com.sinitek.spirit.org.server.entity.*;
import java.math.BigDecimal;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException

public class CommitChangesServiceHandler2 extends AbstractServiceHandler
{

    public CommitChangesServiceHandler2()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        boolean dirty = false;
        String dataStr = request.getString("data");
        String dataRows[] = dataStr.split(";");
        logger.debug((new StringBuilder()).append("\u4F30\u8BA1\u6709[").append(dataRows.length).append("]\u884C\u6570\u636E\u9700\u63D0\u4EA4\u6570\u636E\u5E93").toString());
        String arr$[] = dataRows;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String dataRow = arr$[i$];
            logger.debug((new StringBuilder()).append("\u5F00\u59CB\u5904\u7406\u6570\u636E[").append(dataRow).append("]").toString());
            String details[] = dataRow.split("\\|");
            if(details.length != 5)
                throw new OrgServiceException("1002", new Object[] {
                    dataRow
                });
            if("update".equalsIgnoreCase(details[0]))
            {
                if("OBJ".equals(details[1]))
                    updateOrgObject(getMetaDBContext(), details);
                else
                if("RELA".equals(details[1]))
                    dirty = updateOrgRelation(getMetaDBContext(), details);
                else
                    throw new OrgServiceException("1004", new Object[] {
                        Arrays.toString(details), details[1]
                    });
            } else
            if("insert".equalsIgnoreCase(details[0]))
            {
                if("OBJ".equals(details[1]))
                    insertOrgObject(getMetaDBContext(), details);
                else
                if("RELA".equals(details[1]))
                    dirty = insertOrgRelation(getMetaDBContext(), details);
                else
                    throw new OrgServiceException("1004", new Object[] {
                        Arrays.toString(details), details[1]
                    });
            } else
            if("delete".equalsIgnoreCase(details[0]))
            {
                if("OBJ".equals(details[1]))
                    deleteOrgObject(getMetaDBContext(), details);
                else
                if("RELA".equals(details[1]))
                    deleteOrgRelation(getMetaDBContext(), details);
                else
                    throw new OrgServiceException("1004", new Object[] {
                        Arrays.toString(details), details[1]
                    });
            } else
            {
                throw new OrgServiceException("1003", new Object[] {
                    Arrays.toString(details), details[0]
                });
            }
            logger.debug((new StringBuilder()).append("\u5904\u7406\u6570\u636E[").append(dataRow).append("]\u5B8C\u6210").toString());
        }

        if(dirty)
            logger.debug("\u9700\u8981\u5237\u65B0relation\u7F13\u5B58");
        return SimpleServiceResponse.createSuccessResponse("\u4FDD\u5B58\u6210\u529F");
    }

    private void updateOrgObject(IMetaDBContext context, String details[])
    {
        IOrgObject orgObject = (IOrgObject)loadMoByDetails(context, details);
        checkVersion(orgObject, details);
        setOrgObjectByDetails(orgObject, details);
        logger.debug((new StringBuilder()).append("\u51C6\u5907\u66F4\u65B0\u5B9E\u4F53\u5BF9\u8C61[").append(orgObject.getEntityName()).append("],ID[").append(orgObject.getId()).append("]").toString());
        orgObject.save();
    }

    private void setOrgObjectByDetails(IOrgObject orgObject, String details[])
    {
        String data[] = details[3].split("\\^");
        if(StringUtils.isBlank(orgObject.getOrgId()))
            orgObject.setOrgId(data[0]);
        orgObject.setOrgName(data[1]);
        orgObject.setDescription("@NULL".equalsIgnoreCase(data[2]) ? null : data[2]);
        if(data.length == 5)
        {
            orgObject.setUserId(data[3]);
            orgObject.setObjectType(ObjectType.EMPLOYEE);
            orgObject.setInservice("@NULL".equalsIgnoreCase(data[4]) ? null : Boolean.valueOf(data[4]));
        } else
        if(data.length == 4)
        {
            orgObject.setObjectType(ObjectType.UNIT);
            orgObject.setUnitType(data[3]);
        } else
        {
            throw new OrgServiceException("1008", new Object[] {
                Arrays.toString(details), details[2]
            });
        }
    }

    private void insertOrgObject(IMetaDBContext context, String details[])
    {
        IOrgObject orgObject = (IOrgObject)context.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgObject);
        setOrgObjectByDetails(orgObject, details);
        logger.debug((new StringBuilder()).append("\u51C6\u5907\u4FDD\u5B58\u65B0\u5B9E\u4F53\u5BF9\u8C61[").append(orgObject.getEntityName()).append("],ID[").append(orgObject.getId()).append("]").toString());
        orgObject.save();
    }

    private boolean updateOrgRelation(IMetaDBContext context, String details[])
    {
        deleteOrgRelation(context, details);
        return insertOrgRelation(context, details);
    }

    private boolean insertOrgRelation(IMetaDBContext context, String details[])
    {
        IOrgRelationInfo relationInfo = (IOrgRelationInfo)context.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgRelationInfo);
        String data[] = details[3].split("\\^");
        if(data.length == 5)
        {
            String fromObjectId = data[0];
            String toObjectId = data[1];
            String relationType = data[2].toUpperCase();
            relationInfo.setFromObjectId(fromObjectId);
            relationInfo.setToObjectId(toObjectId);
            relationInfo.setRelationType(relationType);
            String referenceObjectId = "@NULL".equalsIgnoreCase(data[3]) ? null : data[3];
            boolean prior = Boolean.parseBoolean(data[4]);
            boolean dirty = false;
            Map params = new HashMap();
            params.put("fromobjid", fromObjectId);
            params.put("relationtype", relationType);
            int orderCode;
            if(referenceObjectId == null)
            {
                if(prior)
                {
                    IMetaDBQuery query = context.createSqlQuery("select min(ordercode) as minordercode from sprt_orgrela where fromobjectid = :fromobjid and relationtype = :relationtype");
                    query.setParameters(params);
                    List result = query.getResult();
                    Object tempObj = ((Map)result.get(0)).get("minordercode");
                    if(tempObj == null)
                    {
                        orderCode = 0;
                    } else
                    {
                        int minOrderCode = ((BigDecimal)tempObj).intValue();
                        if(minOrderCode == 0)
                        {
                            ISQLUpdater updater = context.createSqlUpdater("update sprt_orgrela set ordercode = ordercode + 1 where fromobjectid = :fromobjid and relationtype = :relationtype");
                            updater.setParameters(params);
                            updater.executeUpdate();
                            dirty = true;
                            orderCode = 0;
                        } else
                        {
                            orderCode = minOrderCode - 1;
                        }
                    }
                } else
                {
                    IMetaDBQuery query = context.createSqlQuery("select max(ordercode) as maxordercode from sprt_orgrela where fromobjectid = :fromobjid and relationtype = :relationtype");
                    query.setParameters(params);
                    List result = query.getResult();
                    Object tempObj = ((Map)result.get(0)).get("maxordercode");
                    if(tempObj == null)
                    {
                        orderCode = 0;
                    } else
                    {
                        int maxOrderCode = ((BigDecimal)tempObj).intValue();
                        orderCode = maxOrderCode + 1;
                    }
                }
            } else
            {
                params.put("toobjid", referenceObjectId);
                IMetaDBQuery query0 = context.createSqlQuery("select ordercode as referenceordercode from sprt_orgrela where fromobjectid = :fromobjid and toobjectid = :toobjid and relationtype = :relationtype");
                query0.setParameters(params);
                List result0 = query0.getResult();
                if(result0.isEmpty())
                    throw new OrgServiceException("1026", new Object[] {
                        fromObjectId, referenceObjectId, relationType
                    });
                int referenceOrderCode = ((BigDecimal)((Map)result0.get(0)).get("referenceordercode")).intValue();
                params.put("ordercode", Integer.valueOf(referenceOrderCode));
                if(prior)
                {
                    IMetaDBQuery query = context.createSqlQuery("select max(ordercode) as maxordercode from sprt_orgrela where fromobjectid = :fromobjid and relationtype = :relationtype and ordercode < :ordercode");
                    query.setParameters(params);
                    List result = query.getResult();
                    Object tempObj = ((Map)result.get(0)).get("maxordercode");
                    if(tempObj == null)
                    {
                        if(referenceOrderCode == 0)
                        {
                            ISQLUpdater updater = context.createSqlUpdater("update sprt_orgrela set ordercode = ordercode + 1 where fromobjectid = :fromobjid and relationtype = :relationtype");
                            updater.setParameters(params);
                            updater.executeUpdate();
                            dirty = true;
                            orderCode = 0;
                        } else
                        {
                            orderCode = referenceOrderCode - 1;
                        }
                    } else
                    {
                        int maxOrderCode = ((BigDecimal)tempObj).intValue();
                        if(referenceOrderCode - maxOrderCode > 1)
                        {
                            orderCode = referenceOrderCode - 1;
                        } else
                        {
                            ISQLUpdater updater = context.createSqlUpdater("update sprt_orgrela set ordercode = ordercode + 1 where fromobjectid = :fromobjid and relationtype = :relationtype and ordercode >= :ordercode");
                            updater.setParameters(params);
                            updater.executeUpdate();
                            dirty = true;
                            orderCode = referenceOrderCode;
                        }
                    }
                } else
                {
                    IMetaDBQuery query = context.createSqlQuery("select min(ordercode) as minordercode from sprt_orgrela where fromobjectid = :fromobjid and relationtype = :relationtype and ordercode > :ordercode");
                    query.setParameters(params);
                    List result = query.getResult();
                    Object tempObj = ((Map)result.get(0)).get("minordercode");
                    if(tempObj == null)
                    {
                        orderCode = referenceOrderCode + 1;
                    } else
                    {
                        int minOrderCode = ((BigDecimal)tempObj).intValue();
                        if(minOrderCode - referenceOrderCode > 1)
                        {
                            orderCode = referenceOrderCode + 1;
                        } else
                        {
                            ISQLUpdater updater = context.createSqlUpdater("update sprt_orgrela set ordercode = ordercode + 1 where fromobjectid = :fromobjid and relationtype = :relationtype and ordercode > :ordercode");
                            updater.setParameters(params);
                            updater.executeUpdate();
                            dirty = true;
                            orderCode = referenceOrderCode + 1;
                        }
                    }
                }
            }
            relationInfo.setOrderCode(Integer.valueOf(orderCode));
            logger.debug((new StringBuilder()).append("\u51C6\u5907\u4FDD\u5B58\u65B0\u5B9E\u4F53\u5BF9\u8C61[").append(relationInfo.getEntityName()).append("],ID[").append(relationInfo.getId()).append("]").toString());
            relationInfo.save();
            return dirty;
        } else
        {
            throw new OrgServiceException("1009", new Object[] {
                Arrays.toString(details), details[2]
            });
        }
    }

    private void deleteOrgObject(IMetaDBContext context, String details[])
    {
        IOrgObject orgObject = (IOrgObject)loadMoByDetails(context, details);
        checkVersion(orgObject, details);
        String sql = "select * from SPRT_ORGRELA rela\nwhere (rela.FROMOBJECTID = :orgid or rela.TOOBJECTID = :orgid)\nand exists(select * from sprt_orgobject where orgid = rela.fromobjectid)\nand exists(select * from sprt_orgobject where orgid = rela.toobjectid)";
        IMetaDBQuery query = context.createSqlQuery(sql);
        String orgid = orgObject.getOrgId();
        query.setParameter("orgid", orgid);
        if(query.getResultCount() > 0)
        {
            throw new OrgServiceException("1010", new Object[] {
                orgid
            });
        } else
        {
            logger.debug((new StringBuilder()).append("\u51C6\u5907\u5220\u9664\u5B9E\u4F53[").append(orgObject.getEntityName()).append("],ID[").append(orgObject.getId()).append("]").toString());
            orgObject.remove();
            return;
        }
    }

    private void deleteOrgRelation(IMetaDBContext context, String details[])
    {
        IOrgRelationInfo relationInfo = (IOrgRelationInfo)loadMoByDetails(context, details);
        checkVersion(relationInfo, details);
        logger.debug((new StringBuilder()).append("\u51C6\u5907\u5220\u9664\u5B9E\u4F53[").append(relationInfo.getEntityName()).append("],ID[").append(relationInfo.getId()).append("]").toString());
        relationInfo.remove();
    }

    private void checkVersion(IMetaObject mo, String details[])
    {
        int versionId = Integer.parseInt(details[4]);
        if(versionId >= 0 && versionId != mo.getVersion())
            throw new OrgServiceException("1007", new Object[] {
                Arrays.toString(details), mo.getEntityName(), Integer.valueOf(mo.getId()), Integer.valueOf(mo.getVersion()), Integer.valueOf(versionId)
            });
        else
            return;
    }

    private IMetaObject loadMoByDetails(IMetaDBContext context, String details[])
    {
        if("OBJ".equals(details[1]))
        {
            String orgId = details[2];
            IMetaObject mo = context.get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", orgId);
            if(mo == null)
                throw new OrgServiceException("1005", new Object[] {
                    Arrays.toString(details), orgId
                });
            else
                return mo;
        }
        if("RELA".equals(details[1]))
        {
            int objId = Integer.parseInt(details[2]);
            IMetaObject mo = context.get(com/sinitek/spirit/org/server/entity/IOrgRelationInfo, objId);
            if(mo == null)
                throw new OrgServiceException("1006", new Object[] {
                    Arrays.toString(details), Integer.valueOf(objId)
                });
            else
                return mo;
        } else
        {
            throw new OrgServiceException("1004", new Object[] {
                Arrays.toString(details), details[1]
            });
        }
    }

    static 
    {
        OrgCacheManager.getInstance();
    }
}
