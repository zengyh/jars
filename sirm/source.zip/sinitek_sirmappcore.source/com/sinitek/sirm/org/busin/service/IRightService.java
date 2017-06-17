// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRightService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.spirit.um.NoSuchUserException;
import java.util.List;

public interface IRightService
{

    public abstract boolean checkRight(int i, String s)
        throws NoSuchUserException;

    public abstract void addRightAuth(OrgObject orgobject, String s, boolean flag, String as[]);

    public abstract void addRightAuth(OrgObject orgobject, String s, String s1, boolean flag, String as[]);

    public abstract void addRightAuth(String s, String s1, String s2, boolean flag, String as[]);

    public abstract void deleteRightAuth(OrgObject orgobject, String s, String as[]);

    public abstract void deleteRightAuth(OrgObject orgobject, String s, String s1, String as[]);

    public abstract void deleteRightAuth(String s, String s1, String as[]);

    public abstract void deleteRightAuth(String s, String as[]);

    public abstract boolean checkRight(OrgObject orgobject, String s, String s1);

    public abstract boolean checkRight(String s, String s1, String s2);

    public abstract boolean checkOrgRight(OrgObject orgobject, String s, String s1, String s2);

    public abstract boolean checkOrgRight(String s, String s1, String s2, String s3);

    public abstract boolean checkRight(OrgObject orgobject, String s, String s1, String s2);

    public abstract boolean checkRight(String s, String s1, String s2, String s3);

    public abstract List findAuthOrgObjects(String s, String s1, String s2);

    public abstract List findEnableAuthOrgObjects(String s, String s1, String s2);

    public abstract List findAuthedObjects(String s, String s1, String s2);

    public abstract List findAuthedObjects(String s, String s1, String as[]);

    public abstract List findEnableAuthedObjects(String s, String s1, String s2, List list);

    public abstract void recoverOrgAuth1(String s, String s1, String s2);

    public abstract IMetaDBQuery findAuthOrg(String s, int i);

    public abstract IMetaDBQuery findAuthOrg(String s, int i, String s1);

    public abstract List findAllAuthOrgObjects(String s, String s1, String s2);

    public abstract IMetaDBQuery findAuthOrg(String s, String s1, String s2);
}
