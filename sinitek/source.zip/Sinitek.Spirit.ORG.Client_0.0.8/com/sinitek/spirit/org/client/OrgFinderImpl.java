// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgFinderImpl.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.*;
import java.util.List;

// Referenced classes of package com.sinitek.spirit.org.client:
//            OrgClientException, OrgObjectService

public class OrgFinderImpl
    implements IOrgFinder
{

    public OrgFinderImpl()
    {
    }

    public IOrgObject getOrgObject(String orgObjId)
    {
        return OrgObjectService.getOrgObjectById(orgObjId);
    }

    public List findOrgObjectsByName(String orgObjName, boolean fuzzy)
    {
        return OrgObjectService.getOrgObjectsByName(orgObjName, fuzzy);
    }

    public List findOrgObjectsByName(String orgObjName)
    {
        return findOrgObjectsByName(orgObjName, false);
    }

    public IUnit getRoot()
    {
        IOrgObject root = getOrgObject("99999");
        if(root == null)
            throw new OrgClientException("0031", new Object[] {
                "99999"
            });
        else
            return (IUnit)root;
    }

    public IOrgObject findOrgObjectByPath(IOrgObject from, String findPathExp)
        throws OrgException
    {
        List result = findOrgObjectsByPath(from, findPathExp);
        return result.isEmpty() ? null : (IOrgObject)result.get(0);
    }

    public List findOrgObjectsByPath(IOrgObject from, String findPathExp)
        throws OrgException
    {
        return OrgObjectService.queryData(from.getId(), findPathExp);
    }

    public boolean isMatch(IOrgObject from, String findPathExp, IOrgObject targetOrgObject)
        throws OrgException
    {
        return OrgObjectService.checkMatch(from.getId(), findPathExp, targetOrgObject.getId());
    }

    public IEmployee getEmployeeByUserId(String userId)
    {
        return OrgObjectService.getEmployeeByUserId(userId);
    }

    public List findEmployeesByName(String employeeName, boolean fuzzy)
    {
        return OrgObjectService.getEmployeesByName(employeeName, fuzzy);
    }

    public List findEmployeesByName(String employeeName)
    {
        return findEmployeesByName(employeeName, false);
    }

    public List getUnassignedEmployees()
    {
        return OrgObjectService.getUnassignedEmployees();
    }

    public List findUnits(String unitType, String unitName, boolean fuzzy)
    {
        return OrgObjectService.getUnits(unitType, unitName, fuzzy);
    }

    public List findUnits(String unitType, String unitName)
    {
        return findUnits(unitType, unitName, false);
    }
}
