// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MenuFunctionRelaImpl.java

package com.sinitek.sirm.common.menu.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.menu.entity:
//            IMenuFunctionRela

public class MenuFunctionRelaImpl extends MetaObjectImpl
    implements IMenuFunctionRela
{

    public MenuFunctionRelaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("MENUFUNCTIONRELA"));
    }

    public MenuFunctionRelaImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getFunctionId()
    {
        return (Integer)get("FunctionId");
    }

    public void setFunctionId(Integer value)
    {
        put("FunctionId", value);
    }

    public Integer getMenuId()
    {
        return (Integer)get("MenuId");
    }

    public void setMenuId(Integer value)
    {
        put("MenuId", value);
    }
}
