// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDocumentAuthService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.spirit.right.core.IRightType;
import java.util.List;

public interface IRTDocumentAuthService
{

    public abstract List findAuthByDocumentId(int i);

    public abstract List findAuthByDiretoryId(int i);

    public abstract List getDocumentRightType(int i, String s, String s1);

    public abstract void saveDocumentAuth(IRTDocumentAuth irtdocumentauth);

    public abstract void saveCompanyDocumentAuth(IRTDocumentAuth irtdocumentauth);

    public abstract IMetaObject getDocumentAuth(int i);

    public abstract IRTDocumentAuth getDocumentAuthByAuthId(String s, int i);

    public abstract void delDocumentAuth(IRTDocumentAuth irtdocumentauth);

    public abstract List getSharingerDiretoryByOrgId(String s);

    public abstract List findDocumentsByOrgId(String s, String s1);

    public abstract void saveKeyReader(IRT_KeyReader irt_keyreader);

    public abstract IRT_KeyReader getKeyReaderByAuthIdAndReaderId(int i, String s);

    public abstract int deleteKeyReaderByDocumentId(int i);

    public abstract IMetaDBQuery findKeyReaderByDocumentId(int i);

    public abstract IRT_KeyReader getKeyReaderById(int i);

    public abstract void delDocumentKeyReader(IRT_KeyReader irt_keyreader);

    public abstract List getDirAuthBydiridAndorgid(int i, String s);

    public abstract List getDocAuthBydocidAndorgid(int i, String s);

    public abstract boolean checkAuth(int i, String s, String s1, String s2);

    public abstract IRTApplyDocAuth getApplyDocAuth(int i, int j, int k);

    public abstract IRTApplyDocAuth getApplyDocAuthById(int i);

    public abstract void removeApplyDocAuth(IRTApplyDocAuth irtapplydocauth);

    public abstract void saveApplyDocAuth(IRTApplyDocAuth irtapplydocauth);

    public abstract boolean checkAuth(String s, String s1, String s2);

    public abstract void copyToChildren(int i, IRightType airighttype[], List list);

    public abstract List getDocumentAuthsByFromEntityAndId(String s, int i);
}
