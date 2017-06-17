// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDemoObject.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

// Referenced classes of package com.sinitek.spirit.webcontrol.demo:
//            IDemoObject2

interface IDemoObject
    extends IMetaObjectImpl
{

    public abstract Date getDemoDate();

    public abstract void setDemoDate(Date date);

    public abstract IEnumItem getDemoEnum();

    public abstract void setDemoEnum(IEnumItem ienumitem);

    public abstract void setDemoEnumEnumValue(Integer integer);

    public abstract IDemoObject2 getDemoForeign();

    public abstract void setDemoForeign(IDemoObject2 idemoobject2);

    public abstract void setDemoForeignEntityValue(Object obj);

    public abstract String getDemoString();

    public abstract void setDemoString(String s);

    public abstract Integer getDemoInteger();

    public abstract void setDemoInteger(Integer integer);

    public static final String ENTITY_NAME = "DEMOOBJECT";
}
