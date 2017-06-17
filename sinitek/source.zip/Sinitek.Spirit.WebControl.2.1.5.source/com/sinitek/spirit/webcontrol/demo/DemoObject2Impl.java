// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoObject2Impl.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.spirit.webcontrol.demo:
//            IDemoObject2

public class DemoObject2Impl extends MetaObjectImpl
    implements IDemoObject2
{

    public DemoObject2Impl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("DEMOOBJECT2"));
    }

    private DemoObject2Impl(IEntity entity)
    {
        super(entity);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }
}
