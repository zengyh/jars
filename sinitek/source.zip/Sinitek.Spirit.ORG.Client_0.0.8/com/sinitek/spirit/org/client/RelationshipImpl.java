// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelationshipImpl.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.IOrgObject;
import com.sinitek.spirit.org.core.IRelationship;

public class RelationshipImpl
    implements IRelationship
{

    public RelationshipImpl()
    {
    }

    public IOrgObject getFromObject()
    {
        return fromObject;
    }

    public void setFromObject(IOrgObject fromObject)
    {
        this.fromObject = fromObject;
    }

    public IOrgObject getToObject()
    {
        return toObject;
    }

    public void setToObject(IOrgObject toObject)
    {
        this.toObject = toObject;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getVersionId()
    {
        return versionId;
    }

    public void setVersionId(int versionId)
    {
        this.versionId = versionId;
    }

    public int getObjId()
    {
        return objId;
    }

    public void setObjId(int objId)
    {
        this.objId = objId;
    }

    public IOrgObject getReferenceObject()
    {
        return referenceObject;
    }

    public void setReferenceObject(IOrgObject referenceObject)
    {
        this.referenceObject = referenceObject;
    }

    public boolean isPrior()
    {
        return prior;
    }

    public void setPrior(boolean prior)
    {
        this.prior = prior;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof IRelationship)
        {
            IRelationship other = (IRelationship)obj;
            return other.getFromObject().equals(fromObject) && other.getToObject().equals(toObject) && other.getType().equalsIgnoreCase(getType());
        } else
        {
            return false;
        }
    }

    public String toDataString()
    {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append(fromObject.getId()).append("^");
        dataStr.append(toObject.getId()).append("^");
        dataStr.append(type).append("^");
        dataStr.append(referenceObject != null ? referenceObject.getId() : "@NULL").append("^");
        dataStr.append(prior);
        return dataStr.toString();
    }

    private IOrgObject fromObject;
    private IOrgObject toObject;
    private String type;
    private int versionId;
    private int objId;
    private IOrgObject referenceObject;
    private boolean prior;
}
