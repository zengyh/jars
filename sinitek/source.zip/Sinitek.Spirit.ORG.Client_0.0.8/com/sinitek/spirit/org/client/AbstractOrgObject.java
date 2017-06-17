// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractOrgObject.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.IOrgObject;

public abstract class AbstractOrgObject
    implements IOrgObject
{

    public AbstractOrgObject()
    {
        versionId = -1;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getVersionId()
    {
        return versionId;
    }

    public void setVersionId(int versionId)
    {
        this.versionId = versionId;
    }

    protected String nullSaveStr(String input)
    {
        return input != null ? input : "@NULL";
    }

    public abstract String toDataString();

    private String id;
    private String name;
    private String description;
    private int versionId;
}
