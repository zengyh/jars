// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnitImpl.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.IUnit;

// Referenced classes of package com.sinitek.spirit.org.client:
//            AbstractOrgObject

public class UnitImpl extends AbstractOrgObject
    implements IUnit
{

    public UnitImpl()
    {
    }

    public String getUnitType()
    {
        return unitType;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof IUnit) && getId().equalsIgnoreCase(((IUnit)obj).getId());
    }

    public String toDataString()
    {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append(getId()).append("^");
        dataStr.append(getName()).append("^");
        dataStr.append(nullSaveStr(getDescription())).append("^");
        dataStr.append(unitType);
        return dataStr.toString();
    }

    private String unitType;
}
