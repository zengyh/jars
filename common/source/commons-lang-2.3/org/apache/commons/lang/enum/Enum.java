// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Enum.java

package org.apache.commons.lang.enum;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @deprecated Class Enum is deprecated
 */

public abstract class Enum
    implements Comparable, Serializable
{
    private static class Entry
    {

        final Map map = new HashMap();
        final Map unmodifiableMap;
        final List list = new ArrayList(25);
        final List unmodifiableList;

        protected Entry()
        {
            unmodifiableMap = Collections.unmodifiableMap(map);
            unmodifiableList = Collections.unmodifiableList(list);
        }
    }


    protected Enum(String name)
    {
        iToString = null;
        init(name);
        iName = name;
        iHashCode = 7 + getEnumClass().hashCode() + 3 * name.hashCode();
    }

    private void init(String name)
    {
        if(StringUtils.isEmpty(name))
            throw new IllegalArgumentException("The Enum name must not be empty or null");
        Class enumClass = getEnumClass();
        if(enumClass == null)
            throw new IllegalArgumentException("getEnumClass() must not be null");
        Class cls = getClass();
        boolean ok = false;
        for(; cls != null && cls != (org.apache.commons.lang.enum.Enum.class) && cls != (org.apache.commons.lang.enum.ValuedEnum.class); cls = cls.getSuperclass())
        {
            if(cls != enumClass)
                continue;
            ok = true;
            break;
        }

        if(!ok)
            throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
        Entry entry = (Entry)cEnumClasses.get(enumClass);
        if(entry == null)
        {
            entry = createEntry(enumClass);
            cEnumClasses.put(enumClass, entry);
        }
        if(entry.map.containsKey(name))
        {
            throw new IllegalArgumentException("The Enum name must be unique, '" + name + "' has already been added");
        } else
        {
            entry.map.put(name, this);
            entry.list.add(this);
            return;
        }
    }

    protected Object readResolve()
    {
        Entry entry = (Entry)cEnumClasses.get(getEnumClass());
        if(entry == null)
            return null;
        else
            return entry.map.get(getName());
    }

    protected static Enum getEnum(Class enumClass, String name)
    {
        Entry entry = getEntry(enumClass);
        if(entry == null)
            return null;
        else
            return (Enum)entry.map.get(name);
    }

    protected static Map getEnumMap(Class enumClass)
    {
        Entry entry = getEntry(enumClass);
        if(entry == null)
            return EMPTY_MAP;
        else
            return entry.unmodifiableMap;
    }

    protected static List getEnumList(Class enumClass)
    {
        Entry entry = getEntry(enumClass);
        if(entry == null)
            return Collections.EMPTY_LIST;
        else
            return entry.unmodifiableList;
    }

    protected static Iterator iterator(Class enumClass)
    {
        return getEnumList(enumClass).iterator();
    }

    private static Entry getEntry(Class enumClass)
    {
        if(enumClass == null)
            throw new IllegalArgumentException("The Enum Class must not be null");
        if(!(org.apache.commons.lang.enum.Enum.class).isAssignableFrom(enumClass))
        {
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        } else
        {
            Entry entry = (Entry)cEnumClasses.get(enumClass);
            return entry;
        }
    }

    private static Entry createEntry(Class enumClass)
    {
        Entry entry = new Entry();
        for(Class cls = enumClass.getSuperclass(); cls != null && cls != (org.apache.commons.lang.enum.Enum.class) && cls != (org.apache.commons.lang.enum.ValuedEnum.class); cls = cls.getSuperclass())
        {
            Entry loopEntry = (Entry)cEnumClasses.get(cls);
            if(loopEntry == null)
                continue;
            entry.list.addAll(loopEntry.list);
            entry.map.putAll(loopEntry.map);
            break;
        }

        return entry;
    }

    public final String getName()
    {
        return iName;
    }

    public Class getEnumClass()
    {
        return getClass();
    }

    public final boolean equals(Object other)
    {
        if(other == this)
            return true;
        if(other == null)
            return false;
        if(other.getClass() == getClass())
            return iName.equals(((Enum)other).iName);
        if(!other.getClass().getName().equals(getClass().getName()))
            return false;
        else
            return iName.equals(getNameInOtherClassLoader(other));
    }

    public final int hashCode()
    {
        return iHashCode;
    }

    public int compareTo(Object other)
    {
        if(other == this)
            return 0;
        if(other.getClass() != getClass() && other.getClass().getName().equals(getClass().getName()))
            return iName.compareTo(getNameInOtherClassLoader(other));
        else
            return iName.compareTo(((Enum)other).iName);
    }

    private String getNameInOtherClassLoader(Object other)
    {
        try
        {
            Method mth = other.getClass().getMethod("getName", null);
            String name = (String)mth.invoke(other, null);
            return name;
        }
        catch(NoSuchMethodException e) { }
        catch(IllegalAccessException e) { }
        catch(InvocationTargetException e) { }
        throw new IllegalStateException("This should not happen");
    }

    public String toString()
    {
        if(iToString == null)
        {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            iToString = shortName + "[" + getName() + "]";
        }
        return iToString;
    }

    private static final long serialVersionUID = 0xf93daa49b331567aL;
    private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
    private static final Map cEnumClasses = new WeakHashMap();
    private final String iName;
    private final transient int iHashCode;
    protected transient String iToString;

}
