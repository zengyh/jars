// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PathExpressionImpl.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            PredicateSetBuilder, PathExpression, PredicateFactory, PredicateSet

final class PathExpressionImpl
    implements PathExpression
{

    public PathExpressionImpl(AtomicQueryExpressionImpl expression)
    {
        if(QueryDir.UP == expression.getQueryDir())
            direction = "UP";
        else
            direction = "DOWN";
        PredicateSetBuilder builder = new PredicateSetBuilder();
        com.sinitek.spirit.org.server.service.QueryCondition queryCondition[] = expression.getQueryCondition();
        com.sinitek.spirit.org.server.service.OperationType operationType[] = expression.getOperationType();
        Object queryValue[] = expression.getQueryValue();
        boolean notFlag[] = expression.isNotFlag();
        for(int i = 0; i < queryCondition.length; i++)
        {
            Predicate predicate = PredicateFactory.newPredicate(queryCondition[i], notFlag[i], operationType[i], queryValue[i]);
            builder.add(predicate);
        }

        predicateSet = builder.build();
    }

    public String getDirection()
    {
        return direction;
    }

    public PredicateSet getPredicateSet()
    {
        return predicateSet;
    }

    public String toString()
    {
        return (new StringBuilder()).append(direction).append(predicateSet.toString()).toString();
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(obj instanceof PathExpression)
        {
            PathExpression expression = (PathExpression)obj;
            return toString().equals(expression.toString());
        } else
        {
            return false;
        }
    }

    public IAtomicQueryExpression getAtomicQueryExpression()
    {
        if(atomicQueryExpression == null)
            synchronized(this)
            {
                if(atomicQueryExpression == null)
                    atomicQueryExpression = AtomicQueryExpressionFactory.parseExpression(toString(), LOGGER);
            }
        return atomicQueryExpression;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/spirit/org/server/cache/PathExpressionImpl);
    private final String direction;
    private final PredicateSet predicateSet;
    private volatile IAtomicQueryExpression atomicQueryExpression;

}
