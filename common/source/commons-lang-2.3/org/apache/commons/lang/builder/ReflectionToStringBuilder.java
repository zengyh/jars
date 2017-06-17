// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReflectionToStringBuilder.java

package org.apache.commons.lang.builder;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang.ArrayUtils;

// Referenced classes of package org.apache.commons.lang.builder:
//            ToStringBuilder, ToStringStyle

public class ReflectionToStringBuilder extends ToStringBuilder
{

    public static String toString(Object object)
    {
        return toString(object, null, false, false, null);
    }

    public static String toString(Object object, ToStringStyle style)
    {
        return toString(object, style, false, false, null);
    }

    public static String toString(Object object, ToStringStyle style, boolean outputTransients)
    {
        return toString(object, style, outputTransients, false, null);
    }

    public static String toString(Object object, ToStringStyle style, boolean outputTransients, boolean outputStatics)
    {
        return toString(object, style, outputTransients, outputStatics, null);
    }

    public static String toString(Object object, ToStringStyle style, boolean outputTransients, boolean outputStatics, Class reflectUpToClass)
    {
        return (new ReflectionToStringBuilder(object, style, null, reflectUpToClass, outputTransients, outputStatics)).toString();
    }

    /**
     * @deprecated Method toString is deprecated
     */

    public static String toString(Object object, ToStringStyle style, boolean outputTransients, Class reflectUpToClass)
    {
        return (new ReflectionToStringBuilder(object, style, null, reflectUpToClass, outputTransients)).toString();
    }

    public static String toStringExclude(Object object, String excludeFieldName)
    {
        return toStringExclude(object, new String[] {
            excludeFieldName
        });
    }

    public static String toStringExclude(Object object, Collection excludeFieldNames)
    {
        return toStringExclude(object, toNoNullStringArray(excludeFieldNames));
    }

    static String[] toNoNullStringArray(Collection collection)
    {
        if(collection == null)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        else
            return toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object array[])
    {
        ArrayList list = new ArrayList(array.length);
        for(int i = 0; i < array.length; i++)
        {
            Object e = array[i];
            if(e != null)
                list.add(e.toString());
        }

        return (String[])list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static String toStringExclude(Object object, String excludeFieldNames[])
    {
        return (new ReflectionToStringBuilder(object)).setExcludeFieldNames(excludeFieldNames).toString();
    }

    public ReflectionToStringBuilder(Object object)
    {
        super(object);
        appendStatics = false;
        appendTransients = false;
        upToClass = null;
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style)
    {
        super(object, style);
        appendStatics = false;
        appendTransients = false;
        upToClass = null;
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer)
    {
        super(object, style, buffer);
        appendStatics = false;
        appendTransients = false;
        upToClass = null;
    }

    /**
     * @deprecated Method ReflectionToStringBuilder is deprecated
     */

    public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class reflectUpToClass, boolean outputTransients)
    {
        super(object, style, buffer);
        appendStatics = false;
        appendTransients = false;
        upToClass = null;
        setUpToClass(reflectUpToClass);
        setAppendTransients(outputTransients);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class reflectUpToClass, boolean outputTransients, boolean outputStatics)
    {
        super(object, style, buffer);
        appendStatics = false;
        appendTransients = false;
        upToClass = null;
        setUpToClass(reflectUpToClass);
        setAppendTransients(outputTransients);
        setAppendStatics(outputStatics);
    }

    protected boolean accept(Field field)
    {
        if(field.getName().indexOf('$') != -1)
            return false;
        if(Modifier.isTransient(field.getModifiers()) && !isAppendTransients())
            return false;
        if(Modifier.isStatic(field.getModifiers()) && !isAppendStatics())
            return false;
        return getExcludeFieldNames() == null || Arrays.binarySearch(getExcludeFieldNames(), field.getName()) < 0;
    }

    protected void appendFieldsIn(Class clazz)
    {
        if(clazz.isArray())
        {
            reflectionAppendArray(getObject());
            return;
        }
        Field fields[] = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for(int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            String fieldName = field.getName();
            if(accept(field))
                try
                {
                    Object fieldValue = getValue(field);
                    append(fieldName, fieldValue);
                }
                catch(IllegalAccessException ex)
                {
                    throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
                }
        }

    }

    public String[] getExcludeFieldNames()
    {
        return excludeFieldNames;
    }

    public Class getUpToClass()
    {
        return upToClass;
    }

    protected Object getValue(Field field)
        throws IllegalArgumentException, IllegalAccessException
    {
        return field.get(getObject());
    }

    public boolean isAppendStatics()
    {
        return appendStatics;
    }

    public boolean isAppendTransients()
    {
        return appendTransients;
    }

    public ToStringBuilder reflectionAppendArray(Object array)
    {
        getStyle().reflectionAppendArrayDetail(getStringBuffer(), null, array);
        return this;
    }

    public void setAppendStatics(boolean appendStatics)
    {
        this.appendStatics = appendStatics;
    }

    public void setAppendTransients(boolean appendTransients)
    {
        this.appendTransients = appendTransients;
    }

    public ReflectionToStringBuilder setExcludeFieldNames(String excludeFieldNamesParam[])
    {
        if(excludeFieldNamesParam == null)
        {
            excludeFieldNames = null;
        } else
        {
            excludeFieldNames = toNoNullStringArray(excludeFieldNamesParam);
            Arrays.sort(excludeFieldNames);
        }
        return this;
    }

    public void setUpToClass(Class clazz)
    {
        upToClass = clazz;
    }

    public String toString()
    {
        if(getObject() == null)
            return getStyle().getNullText();
        Class clazz = getObject().getClass();
        appendFieldsIn(clazz);
        for(; clazz.getSuperclass() != null && clazz != getUpToClass(); appendFieldsIn(clazz))
            clazz = clazz.getSuperclass();

        return super.toString();
    }

    private boolean appendStatics;
    private boolean appendTransients;
    private String excludeFieldNames[];
    private Class upToClass;
}
