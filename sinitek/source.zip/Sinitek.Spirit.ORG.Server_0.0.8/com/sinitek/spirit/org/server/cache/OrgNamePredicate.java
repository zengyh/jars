// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgNamePredicate.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.OperationType;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            Predicate

public final class OrgNamePredicate
    implements Predicate
{

    public OrgNamePredicate(boolean notFlag, OperationType operationType, Object valueObject)
    {
        String operator;
        if(operationType == OperationType.EQUALS)
            operator = notFlag ? "!=" : "=";
        else
            operator = notFlag ? "not like" : "like";
        stringValue = buildStringValue(operator, ((String)valueObject).trim());
    }

    public int compareTo(Predicate o)
    {
        return toString().compareTo(o.toString());
    }

    public String toString()
    {
        return stringValue;
    }

    private String buildStringValue(String operator, String value)
    {
        StringBuilder sb = (new StringBuilder("orgname")).append(" ").append(operator).append(" '").append(value).append("'");
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
        if(obj instanceof OrgNamePredicate)
        {
            OrgNamePredicate predicate = (OrgNamePredicate)obj;
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

    public static final String NAME = "orgname";
    private static final String OPERATOR_EQUAL = "=";
    private static final String OPERATOR_NOT_EQUAL = "!=";
    private static final String OPERATOR_LIKE = "like";
    private static final String OPERATOR_NOT_LIKE = "not like";
    private final String stringValue;
}
