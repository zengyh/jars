// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoObjectImpl.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.spirit.webcontrol.demo:
//            IDemoObject2, IDemoObject

public class DemoObjectImpl extends MetaObjectImpl
    implements IDemoObject
{

    public DemoObjectImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("DEMOOBJECT"));
    }

    private DemoObjectImpl(IEntity entity)
    {
        super(entity);
    }

    public Date getDemoDate()
    {
        return (Date)get("DemoDate");
    }

    public void setDemoDate(Date value)
    {
        put("DemoDate", value);
    }

    public IEnumItem getDemoEnum()
    {
        return (IEnumItem)get("DemoEnum");
    }

    public void setDemoEnum(IEnumItem value)
    {
        put("DemoEnum", value);
    }

    public void setDemoEnumEnumValue(Integer value)
    {
        setEnumValue("DemoEnum", value);
    }

    public IDemoObject2 getDemoForeign()
    {
        return (IDemoObject2)get("DemoForeign");
    }

    public void setDemoForeign(IDemoObject2 value)
    {
        put("DemoForeign", value);
    }

    public void setDemoForeignEntityValue(Object value)
    {
        setEntityValue("DemoForeign", value);
    }

    public String getDemoString()
    {
        return (String)get("DemoString");
    }

    public void setDemoString(String value)
    {
        put("DemoString", value);
    }

    public Integer getDemoInteger()
    {
        return (Integer)get("DemoInteger");
    }

    public void setDemoInteger(Integer value)
    {
        put("DemoInteger", value);
    }
}
