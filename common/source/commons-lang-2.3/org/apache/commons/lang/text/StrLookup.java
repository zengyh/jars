// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrLookup.java

package org.apache.commons.lang.text;

import java.util.Map;

public abstract class StrLookup
{
    static class MapStrLookup extends StrLookup
    {

        public String lookup(String key)
        {
            if(map == null)
                return null;
            Object obj = map.get(key);
            if(obj == null)
                return null;
            else
                return obj.toString();
        }

        private final Map map;

        MapStrLookup(Map map)
        {
            this.map = map;
        }
    }


    public static StrLookup noneLookup()
    {
        return NONE_LOOKUP;
    }

    public static StrLookup systemPropertiesLookup()
    {
        return SYSTEM_PROPERTIES_LOOKUP;
    }

    public static StrLookup mapLookup(Map map)
    {
        return new MapStrLookup(map);
    }

    protected StrLookup()
    {
    }

    public abstract String lookup(String s);

    private static final StrLookup NONE_LOOKUP;
    private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;

    static 
    {
        NONE_LOOKUP = new MapStrLookup(null);
        StrLookup lookup = null;
        try
        {
            lookup = new MapStrLookup(System.getProperties());
        }
        catch(SecurityException ex)
        {
            lookup = NONE_LOOKUP;
        }
        SYSTEM_PROPERTIES_LOOKUP = lookup;
    }
}
