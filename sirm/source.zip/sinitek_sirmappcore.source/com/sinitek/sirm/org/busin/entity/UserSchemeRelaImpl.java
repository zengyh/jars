// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSchemeRelaImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IUserSchemeRela

public class UserSchemeRelaImpl extends MetaObjectImpl
    implements IUserSchemeRela
{

    public UserSchemeRelaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("USERSCHEMERELA"));
    }

    public UserSchemeRelaImpl(IEntity entity)
    {
        super(entity);
    }

    public String getPath()
    {
        return (String)get("Path");
    }

    public void setPath(String value)
    {
        put("Path", value);
    }

    public Integer getSchemeId()
    {
        return (Integer)get("SchemeId");
    }

    public void setSchemeId(Integer value)
    {
        put("SchemeId", value);
    }
}
