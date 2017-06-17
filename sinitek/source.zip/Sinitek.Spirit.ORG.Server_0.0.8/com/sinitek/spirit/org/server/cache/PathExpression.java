// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PathExpression.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.IAtomicQueryExpression;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            PredicateSet

public interface PathExpression
{

    public abstract String getDirection();

    public abstract PredicateSet getPredicateSet();

    public abstract String toString();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    public abstract IAtomicQueryExpression getAtomicQueryExpression();

    public static final String DIRECTION_UP = "UP";
    public static final String DIRECTION_DOWN = "DOWN";
}
