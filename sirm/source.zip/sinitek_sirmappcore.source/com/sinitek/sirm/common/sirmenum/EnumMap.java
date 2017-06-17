// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumMap.java

package com.sinitek.sirm.common.sirmenum;

import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class EnumMap
    implements Map
{

    public EnumMap(Map map)
    {
        _enummap = map;
    }

    public int size()
    {
        return _enummap.size();
    }

    public boolean isEmpty()
    {
        return _enummap.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return _enummap.containsKey(StringUtil.safeToString(key, ""));
    }

    public boolean containsValue(Object value)
    {
        return _enummap.containsValue(value);
    }

    public Object get(Object key)
    {
        return _enummap.get(StringUtil.safeToString(key, ""));
    }

    public Object put(Object key, Object value)
    {
        return _enummap.put(StringUtil.safeToString(key, ""), StringUtil.safeToString(value, ""));
    }

    public Object remove(Object key)
    {
        return _enummap.remove(StringUtil.safeToString(key, ""));
    }

    public void putAll(Map m)
    {
        _enummap.putAll(m);
    }

    public void clear()
    {
        _enummap.clear();
    }

    public Set keySet()
    {
        return _enummap.keySet();
    }

    public Collection values()
    {
        return _enummap.values();
    }

    public Set entrySet()
    {
        return _enummap.entrySet();
    }

    public boolean equals(Object o)
    {
        return _enummap.equals(o);
    }

    public int hashCode()
    {
        return _enummap.hashCode();
    }

    Map _enummap;
}
