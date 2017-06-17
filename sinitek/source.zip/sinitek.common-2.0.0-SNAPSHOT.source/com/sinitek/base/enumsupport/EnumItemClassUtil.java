// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumItemClassUtil.java

package com.sinitek.base.enumsupport;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

// Referenced classes of package com.sinitek.base.enumsupport:
//            IEnumItem, EnumException, EnumBean

public class EnumItemClassUtil
{

    public EnumItemClassUtil()
    {
    }

    public static EnumBean parseEnumItemClass(Class cls)
        throws EnumException
    {
        if(!com/sinitek/base/enumsupport/IEnumItem.isAssignableFrom(cls))
            throw new EnumException("0012", new Object[] {
                cls.getName()
            });
        EnumBean ret = new EnumBean();
        ret.setEnumName(cls.getName());
        Field fields[] = cls.getFields();
        for(int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            int fieldModifier = field.getModifiers();
            if(!Modifier.isPublic(fieldModifier) || !Modifier.isStatic(fieldModifier) || !Modifier.isFinal(fieldModifier))
                continue;
            Class fieldClz = field.getType();
            if(!com/sinitek/base/enumsupport/IEnumItem.isAssignableFrom(fieldClz))
                continue;
            try
            {
                ret.getEnumItemList().add(field.get(null));
            }
            catch(IllegalAccessException e)
            {
                throw new EnumException("0013", e, new Object[] {
                    cls.getName(), field.getName()
                });
            }
        }

        return ret;
    }
}
