// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumPredicate.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.OperationType;
import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            Predicate

public abstract class EnumPredicate
    implements Predicate
{

    public EnumPredicate(boolean notFlag, OperationType operationType, Object valueObject, boolean lowerCase)
    {
        if(operationType == OperationType.EQUALS)
        {
            String operator = notFlag ? "!=" : "=";
            String value = ((String)valueObject).trim();
            value = lowerCase ? value.toLowerCase() : value.toUpperCase();
            stringValue = buildStringValue(operator, value);
        } else
        {
            String operator = notFlag ? "not in" : "in";
            TreeSet values = new TreeSet();
            Object arr$[] = (Object[])(Object[])valueObject;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                Object obj = arr$[i$];
                String value = ((String)obj).trim();
                value = lowerCase ? value.toLowerCase() : value.toUpperCase();
                values.add(value);
            }

            stringValue = buildStringValue(operator, values);
        }
    }

    protected abstract String getName();

    public String toString()
    {
        return stringValue;
    }

    public int compareTo(Predicate o)
    {
        return toString().compareTo(o.toString());
    }

    private String buildStringValue(String operator, String value)
    {
        StringBuilder sb = (new StringBuilder(getName())).append(" ").append(operator).append(" '").append(value).append("'");
        return sb.toString();
    }

    private String buildStringValue(String operator, Set values)
    {
        StringBuilder sb = (new StringBuilder(getName())).append(" ").append(operator).append(" ").append("[");
        boolean first = true;
        for(Iterator i$ = values.iterator(); i$.hasNext();)
        {
            String value = (String)i$.next();
            if(first)
            {
                sb.append("'").append(value).append("'");
                first = false;
            } else
            {
                sb.append(", '").append(value).append("'");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public int hashCode()
    {
        return stringValue.hashCode();
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(obj instanceof EnumPredicate)
        {
            EnumPredicate predicate = (EnumPredicate)obj;
            return stringValue.equals(predicate.stringValue);
        } else
        {
            return false;
        }
    }

    public volatile int compareTo(Object x0)
    {
        return compareTo((Predicate)x0);
    }

    private static final String OPERATOR_EQUAL = "=";
    private static final String OPERATOR_NOT_EQUAL = "!=";
    private static final String OPERATOR_IN = "in";
    private static final String OPERATOR_NOT_IN = "not in";
    private final String stringValue;
}
