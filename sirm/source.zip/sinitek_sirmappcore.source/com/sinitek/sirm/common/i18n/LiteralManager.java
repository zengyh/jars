// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LiteralManager.java

package com.sinitek.sirm.common.i18n;


// Referenced classes of package com.sinitek.sirm.common.i18n:
//            I18nManager

public class LiteralManager
{

    public LiteralManager()
    {
    }

    public static String getLiteral(String locale, String key)
    {
        return I18nManager.getLiteralRelationValue(locale, key);
    }

    public static String getLiteral(String locale, String key, Object objects[])
    {
        return I18nManager.getLiteralRelationValue(locale, key, objects);
    }
}
