// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgObjectService.java

package com.sinitek.spirit.org.client;

import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceContextClientFactory;
import com.sinitek.spirit.org.core.IOrgObject;
import com.sinitek.spirit.org.core.OrgException;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.org.client:
//            OrgClientException, UnitImpl, EmployeeImpl, RelationshipImpl, 
//            CallServiceUtil, AbstractOrgObject

public class OrgObjectService
{

    public OrgObjectService()
    {
    }

    static synchronized String getOrgId(String typeStr)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_01");
        request.addParam("orgtype", typeStr);
        IServiceResponse response = CallServiceUtil.callService(request);
        return response.getString("orgid");
    }

    static UnitImpl getUnitById(String id)
        throws OrgException
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_02");
        request.addParam("orgid", id);
        IServiceResponse response = CallServiceUtil.callService(request);
        String orgtype = response.getString("orgtype");
        if(!"unit".equalsIgnoreCase(orgtype))
        {
            throw new OrgClientException("0003", new Object[] {
                id
            });
        } else
        {
            UnitImpl ret = new UnitImpl();
            ret.setUnitType(response.getString("unittype"));
            ret.setName(response.getString("name"));
            ret.setId(id);
            ret.setDescription(response.getString("description"));
            ret.setVersionId(response.getInteger("versionid").intValue());
            return ret;
        }
    }

    static EmployeeImpl getEmployeeById(String id)
        throws OrgException
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_02");
        request.addParam("orgid", id);
        IServiceResponse response = CallServiceUtil.callService(request);
        String orgtype = response.getString("orgtype");
        if(!"emp".equalsIgnoreCase(orgtype))
        {
            throw new OrgClientException("0006", new Object[] {
                id
            });
        } else
        {
            EmployeeImpl ret = new EmployeeImpl();
            ret.setName(response.getString("name"));
            ret.setId(id);
            ret.setUserId(response.getString("userid"));
            ret.setDescription(response.getString("description"));
            ret.setVersionId(response.getInteger("versionid").intValue());
            ret.setInservice(response.getBoolean("inservice"));
            return ret;
        }
    }

    static AbstractOrgObject getOrgObjectById(String id)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_02");
        request.addParam("orgid", id);
        IServiceResponse response = ServiceContextClientFactory.getServiceContext().handleService(request);
        if(response.isSuccess())
            return getOrgObjectFromResponse(response);
        if("SPOG1001".equalsIgnoreCase(response.getReturnCode()))
            return null;
        else
            throw new OrgClientException("9999", new Object[] {
                response.getReturnMessage(), response.getReturnCode()
            });
    }

    static List getOrgObjectsByName(String name, boolean fuzzy)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_10");
        request.addParam("orgname", name);
        request.addParam("fuzzy", Boolean.valueOf(fuzzy));
        IServiceResponse response = CallServiceUtil.callService(request);
        List ret = new LinkedList();
        IParamObject row;
        for(; response.hasNext(); ret.add(getOrgObjectFromResponse(row)))
            row = (IParamObject)response.next();

        return ret;
    }

    static List getEmployeesByName(String name, boolean fuzzy)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_11");
        request.addParam("employeename", name);
        request.addParam("fuzzy", Boolean.valueOf(fuzzy));
        IServiceResponse response = CallServiceUtil.callService(request);
        List ret = new LinkedList();
        IParamObject row;
        for(; response.hasNext(); ret.add((EmployeeImpl)getOrgObjectFromResponse(row)))
            row = (IParamObject)response.next();

        return ret;
    }

    static List getUnits(String unitType, String unitName, boolean fuzzy)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_12");
        request.addParam("unittype", unitType);
        request.addParam("unitname", unitName);
        request.addParam("fuzzy", Boolean.valueOf(fuzzy));
        IServiceResponse response = CallServiceUtil.callService(request);
        List ret = new LinkedList();
        IParamObject row;
        for(; response.hasNext(); ret.add((UnitImpl)getOrgObjectFromResponse(row)))
            row = (IParamObject)response.next();

        return ret;
    }

    static EmployeeImpl getEmployeeByUserId(String userId)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_09");
        request.addParam("userid", userId);
        IServiceResponse response = CallServiceUtil.callService(request);
        String orgId = response.getString("orgid");
        if(StringUtils.isNotBlank(orgId))
        {
            EmployeeImpl employee = new EmployeeImpl();
            employee.setVersionId(response.getInteger("versionid").intValue());
            employee.setId(orgId);
            employee.setName(response.getString("name"));
            employee.setDescription(response.getString("description"));
            employee.setUserId(userId);
            employee.setInservice(response.getBoolean("inservice"));
            return employee;
        } else
        {
            return null;
        }
    }

    static List queryData(String fromId, String queryStr)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_07");
        request.addParam("fromorgid", fromId);
        request.addParam("querystr", queryStr);
        IServiceResponse response = CallServiceUtil.callService(request);
        List ret = new ArrayList();
        IParamObject row;
        for(; response.hasNext(); ret.add(getOrgObjectFromResponse(row)))
            row = (IParamObject)response.next();

        return ret;
    }

    static boolean checkMatch(String fromId, String queryStr, String targetId)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_08");
        request.addParam("fromorgid", fromId);
        request.addParam("targetorgid", targetId);
        request.addParam("querystr", queryStr);
        IServiceResponse response = CallServiceUtil.callService(request);
        return response.getBoolean("match").booleanValue();
    }

    static boolean checkOrgObjectExists(String id)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_03");
        request.addParam("orgid", id);
        IServiceResponse response = CallServiceUtil.callService(request);
        return response.getBoolean("exists").booleanValue();
    }

    static boolean checkOrgRelationshipExists(String fromId, String toId, String type)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_04");
        request.addParam("fromorgid", fromId);
        request.addParam("toorgid", toId);
        request.addParam("relationtype", type);
        return CallServiceUtil.callService(request).getBoolean("exists").booleanValue();
    }

    static boolean checkOrgRelationshipExists(String orgId, String type)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_04");
        request.addParam("orgid", orgId);
        if(StringUtils.isNotBlank(type))
            request.addParam("relationtype", type);
        return CallServiceUtil.callService(request).getBoolean("exists").booleanValue();
    }

    static List getRelationshipsByOrgId(String orgId)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_05");
        request.addParam("orgid", orgId);
        IServiceResponse response = CallServiceUtil.callService(request);
        return createRelationshipImplFromResp(response);
    }

    static List queryRelations(String fromObjId, String toObjId, String type)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_05");
        request.addParam("fromorgid", fromObjId);
        request.addParam("toorgid", toObjId);
        request.addParam("relationtype", type);
        IServiceResponse response = CallServiceUtil.callService(request);
        return createRelationshipImplFromResp(response);
    }

    static List getUnassignedEmployees()
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_13");
        IServiceResponse response = CallServiceUtil.callService(request);
        List ret = new LinkedList();
        IParamObject row;
        for(; response.hasNext(); ret.add((EmployeeImpl)getOrgObjectFromResponse(row)))
            row = (IParamObject)response.next();

        return ret;
    }

    static void commitChanges(String data)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_06");
        request.addParam("data", data);
        CallServiceUtil.callService(request);
    }

    private static IOrgObject createOrgObject(String objId, String name, String desc, int version, String unitType, int objectType, String userId, Boolean inservice)
    {
        if(objectType == 1)
        {
            EmployeeImpl employee = new EmployeeImpl();
            employee.setInservice(inservice);
            employee.setUserId(userId);
            employee.setName(name);
            employee.setDescription(desc);
            employee.setId(objId);
            employee.setVersionId(version);
            return employee;
        } else
        {
            UnitImpl unit = new UnitImpl();
            unit.setDescription(desc);
            unit.setUnitType(unitType);
            unit.setVersionId(version);
            unit.setId(objId);
            unit.setName(name);
            return unit;
        }
    }

    private static List createRelationshipImplFromResp(IServiceResponse response)
    {
        List relationshipList = new ArrayList();
        RelationshipImpl relationship;
        for(; response.hasNext(); relationshipList.add(relationship))
        {
            IParamObject row = (IParamObject)response.next();
            IOrgObject fromObj = createOrgObject(row.getString("fromobjid"), row.getString("fromname"), row.getString("fromdesc"), row.getInteger("fromversion").intValue(), row.getString("fromunittype"), row.getInteger("fromobjecttype").intValue(), row.getString("fromuserid"), row.getBoolean("frominservice"));
            IOrgObject toObj = createOrgObject(row.getString("toobjid"), row.getString("toname"), row.getString("todesc"), row.getInteger("toversion").intValue(), row.getString("tounittype"), row.getInteger("toobjecttype").intValue(), row.getString("touserid"), row.getBoolean("toinservice"));
            String type = row.getString("relationtype");
            relationship = new RelationshipImpl();
            relationship.setFromObject(fromObj);
            relationship.setToObject(toObj);
            relationship.setType(type);
            relationship.setVersionId(row.getInteger("relaversion").intValue());
            relationship.setObjId(row.getInteger("metadbobjid").intValue());
        }

        return relationshipList;
    }

    private static AbstractOrgObject getOrgObjectFromResponse(IParamObject param)
    {
        String orgtype = param.getString("orgtype");
        String orgid = param.getString("orgid");
        if("emp".equalsIgnoreCase(orgtype))
        {
            EmployeeImpl ret = new EmployeeImpl();
            ret.setName(param.getString("name"));
            ret.setId(orgid);
            ret.setUserId(param.getString("userid"));
            ret.setDescription(param.getString("description"));
            ret.setVersionId(param.getInteger("versionid").intValue());
            ret.setInservice(param.getBoolean("inservice"));
            return ret;
        }
        if("unit".equalsIgnoreCase(orgtype))
        {
            UnitImpl ret = new UnitImpl();
            ret.setUnitType(param.getString("unittype"));
            ret.setName(param.getString("name"));
            ret.setId(orgid);
            ret.setDescription(param.getString("description"));
            ret.setVersionId(param.getInteger("versionid").intValue());
            return ret;
        } else
        {
            throw new OrgClientException("0032", new Object[] {
                orgid, orgtype
            });
        }
    }
}
