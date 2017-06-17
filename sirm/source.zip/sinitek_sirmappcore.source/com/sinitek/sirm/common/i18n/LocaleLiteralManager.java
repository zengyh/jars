// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocaleLiteralManager.java

package com.sinitek.sirm.common.i18n;

import javolution.util.FastMap;

// Referenced classes of package com.sinitek.sirm.common.i18n:
//            LocaleLiteral

public class LocaleLiteralManager
{

    public LocaleLiteralManager()
    {
        literalMap = new FastMap();
    }

    public void putLiteralRelation(String key, String value)
    {
        literalMap.put(key, new LocaleLiteral(key, value));
    }

    public void putLiteralRelation(String key, String value, int level)
    {
        literalMap.put(key, new LocaleLiteral(key, value, level));
    }

    public void putAllLiteralRelation(FastMap map)
    {
        literalMap.putAll(map);
    }

    public String getLiteralRelationValue(String key)
    {
        LocaleLiteral locale = (LocaleLiteral)literalMap.get(key);
        return locale != null ? locale.getValue() : null;
    }

    public LocaleLiteral getLiteralRelation(String key)
    {
        return (LocaleLiteral)literalMap.get(key);
    }

    public String getLiteralRelationValue(String key, String defaultValue)
    {
        String value = getLiteralRelationValue(key);
        return value == null ? defaultValue : value;
    }

    public boolean containsKey(String key)
    {
        return literalMap.containsKey(key);
    }

    protected FastMap literalMap;
}
