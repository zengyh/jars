// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgUpdater.java

package com.sinitek.spirit.org.core;


// Referenced classes of package com.sinitek.spirit.org.core:
//            OrgException, IUnit, IEmployee, IOrgObject, 
//            IRelationship

public interface IOrgUpdater
{

    public abstract IUnit addUnit(String s, String s1, String s2);

    public abstract IUnit updateUnit(IUnit iunit, String s, String s1, String s2)
        throws OrgException;

    public abstract IEmployee addEmployee(String s, String s1, String s2, Boolean boolean1);

    public abstract IEmployee updateEmployee(IEmployee iemployee, String s, String s1, String s2, Boolean boolean1)
        throws OrgException;

    public abstract IRelationship addRelationShip(IUnit iunit, IOrgObject iorgobject, String s)
        throws OrgException;

    public abstract IRelationship addRelationShip(IUnit iunit, IOrgObject iorgobject, String s, IOrgObject iorgobject1, boolean flag)
        throws OrgException;

    public abstract IRelationship changeRelationShipOrder(IUnit iunit, IOrgObject iorgobject, String s, IOrgObject iorgobject1, boolean flag)
        throws OrgException;

    public abstract void deleteObject(IOrgObject iorgobject, boolean flag)
        throws OrgException;

    public abstract int deleteRelationship(IOrgObject iorgobject, IOrgObject iorgobject1, String s)
        throws OrgException;

    public abstract void flush()
        throws OrgException;
}
