// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmployeeImpl.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.IEmployee;

// Referenced classes of package com.sinitek.spirit.org.client:
//            AbstractOrgObject

public class EmployeeImpl extends AbstractOrgObject
    implements IEmployee
{

    public EmployeeImpl()
    {
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Boolean isInservice()
    {
        return inservice;
    }

    public void setInservice(Boolean inservice)
    {
        this.inservice = inservice;
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof IEmployee) && getId().equalsIgnoreCase(((IEmployee)obj).getId());
    }

    public String toDataString()
    {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append(getId()).append("^");
        dataStr.append(getName()).append("^");
        dataStr.append(nullSaveStr(getDescription())).append("^");
        dataStr.append(getUserId()).append("^");
        dataStr.append(inservice != null ? inservice.toString() : "@NULL");
        return dataStr.toString();
    }

    private String userId;
    private Boolean inservice;
}
