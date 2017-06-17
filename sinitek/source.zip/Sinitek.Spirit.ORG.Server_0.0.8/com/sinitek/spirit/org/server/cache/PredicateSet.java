// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PredicateSet.java

package com.sinitek.spirit.org.server.cache;

import java.util.Set;

public interface PredicateSet
{

    public abstract Set getPredicates();

    public abstract String toString();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}
