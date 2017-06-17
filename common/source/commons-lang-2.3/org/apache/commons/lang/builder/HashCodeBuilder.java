// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HashCodeBuilder.java

package org.apache.commons.lang.builder;

import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package org.apache.commons.lang.builder:
//            ReflectionToStringBuilder

public class HashCodeBuilder
{

    static Set getRegistry()
    {
        return (Set)registry.get();
    }

    static boolean isRegistered(Object value)
    {
        return getRegistry().contains(toIdentityHashCodeInteger(value));
    }

    private static void reflectionAppend(Object object, Class clazz, HashCodeBuilder builder, boolean useTransients, String excludeFields[])
    {
        if(isRegistered(object))
            return;
        try
        {
            register(object);
            Field fields[] = clazz.getDeclaredFields();
            List excludedFieldList = excludeFields == null ? Collections.EMPTY_LIST : Arrays.asList(excludeFields);
            AccessibleObject.setAccessible(fields, true);
            for(int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                if(!excludedFieldList.contains(field.getName()) && field.getName().indexOf('$') == -1 && (useTransients || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))
                    try
                    {
                        Object fieldValue = field.get(object);
                        builder.append(fieldValue);
                    }
                    catch(IllegalAccessException e)
                    {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
            }

        }
        finally
        {
            unregister(object);
        }
    }

    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object)
    {
        return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null, null);
    }

    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients)
    {
        return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null, null);
    }

    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients, Class reflectUpToClass)
    {
        return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, reflectUpToClass, null);
    }

    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients, Class reflectUpToClass, String excludeFields[])
    {
        if(object == null)
            throw new IllegalArgumentException("The object to build a hash code for must not be null");
        HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
        Class clazz = object.getClass();
        reflectionAppend(object, clazz, builder, testTransients, excludeFields);
        for(; clazz.getSuperclass() != null && clazz != reflectUpToClass; reflectionAppend(object, clazz, builder, testTransients, excludeFields))
            clazz = clazz.getSuperclass();

        return builder.toHashCode();
    }

    public static int reflectionHashCode(Object object)
    {
        return reflectionHashCode(17, 37, object, false, null, null);
    }

    public static int reflectionHashCode(Object object, boolean testTransients)
    {
        return reflectionHashCode(17, 37, object, testTransients, null, null);
    }

    public static int reflectionHashCode(Object object, Collection excludeFields)
    {
        return reflectionHashCode(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
    }

    public static int reflectionHashCode(Object object, String excludeFields[])
    {
        return reflectionHashCode(17, 37, object, false, null, excludeFields);
    }

    static void register(Object value)
    {
        getRegistry().add(toIdentityHashCodeInteger(value));
    }

    private static Integer toIdentityHashCodeInteger(Object value)
    {
        return new Integer(System.identityHashCode(value));
    }

    static void unregister(Object value)
    {
        getRegistry().remove(toIdentityHashCodeInteger(value));
    }

    public HashCodeBuilder()
    {
        iTotal = 0;
        iConstant = 37;
        iTotal = 17;
    }

    public HashCodeBuilder(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber)
    {
        iTotal = 0;
        if(initialNonZeroOddNumber == 0)
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
        if(initialNonZeroOddNumber % 2 == 0)
            throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
        if(multiplierNonZeroOddNumber == 0)
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
        if(multiplierNonZeroOddNumber % 2 == 0)
        {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
        } else
        {
            iConstant = multiplierNonZeroOddNumber;
            iTotal = initialNonZeroOddNumber;
            return;
        }
    }

    public HashCodeBuilder append(boolean value)
    {
        iTotal = iTotal * iConstant + (value ? 0 : 1);
        return this;
    }

    public HashCodeBuilder append(boolean array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(byte value)
    {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    public HashCodeBuilder append(byte array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(char value)
    {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    public HashCodeBuilder append(char array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(double value)
    {
        return append(Double.doubleToLongBits(value));
    }

    public HashCodeBuilder append(double array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(float value)
    {
        iTotal = iTotal * iConstant + Float.floatToIntBits(value);
        return this;
    }

    public HashCodeBuilder append(float array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(int value)
    {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    public HashCodeBuilder append(int array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(long value)
    {
        iTotal = iTotal * iConstant + (int)(value ^ value >> 32);
        return this;
    }

    public HashCodeBuilder append(long array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(Object object)
    {
        if(object == null)
            iTotal = iTotal * iConstant;
        else
        if(!object.getClass().isArray())
            iTotal = iTotal * iConstant + object.hashCode();
        else
        if(object instanceof long[])
            append((long[])object);
        else
        if(object instanceof int[])
            append((int[])object);
        else
        if(object instanceof short[])
            append((short[])object);
        else
        if(object instanceof char[])
            append((char[])object);
        else
        if(object instanceof byte[])
            append((byte[])object);
        else
        if(object instanceof double[])
            append((double[])object);
        else
        if(object instanceof float[])
            append((float[])object);
        else
        if(object instanceof boolean[])
            append((boolean[])object);
        else
            append((Object[])object);
        return this;
    }

    public HashCodeBuilder append(Object array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder append(short value)
    {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    public HashCodeBuilder append(short array[])
    {
        if(array == null)
        {
            iTotal = iTotal * iConstant;
        } else
        {
            for(int i = 0; i < array.length; i++)
                append(array[i]);

        }
        return this;
    }

    public HashCodeBuilder appendSuper(int superHashCode)
    {
        iTotal = iTotal * iConstant + superHashCode;
        return this;
    }

    public int toHashCode()
    {
        return iTotal;
    }

    private static ThreadLocal registry = new ThreadLocal() {

        protected synchronized Object initialValue()
        {
            return new HashSet();
        }

    };
    private final int iConstant;
    private int iTotal;

}
