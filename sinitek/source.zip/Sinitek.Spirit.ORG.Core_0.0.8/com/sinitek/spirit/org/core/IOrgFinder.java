// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgFinder.java

package com.sinitek.spirit.org.core;

import java.util.List;

// Referenced classes of package com.sinitek.spirit.org.core:
//            OrgException, IOrgObject, IUnit, IEmployee

public interface IOrgFinder
{

    public abstract IOrgObject getOrgObject(String s);

    /**
     * @deprecated Method findOrgObjectsByName is deprecated
     */

    public abstract List findOrgObjectsByName(String s, boolean flag);

    /**
     * @deprecated Method findOrgObjectsByName is deprecated
     */

    public abstract List findOrgObjectsByName(String s);

    public abstract List findEmployeesByName(String s, boolean flag);

    public abstract List findEmployeesByName(String s);

    public abstract List findUnits(String s, String s1, boolean flag);

    public abstract List findUnits(String s, String s1);

    public abstract IUnit getRoot();

    public abstract IOrgObject findOrgObjectByPath(IOrgObject iorgobject, String s)
        throws OrgException;

    public abstract List findOrgObjectsByPath(IOrgObject iorgobject, String s)
        throws OrgException;

    public abstract boolean isMatch(IOrgObject iorgobject, String s, IOrgObject iorgobject1)
        throws OrgException;

    public abstract IEmployee getEmployeeByUserId(String s);

    public abstract List getUnassignedEmployees();

    public static final String ROOT_OBJECT_KEY = "99999";
}
