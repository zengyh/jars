// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Predicate.java

package com.sinitek.spirit.org.server.cache;


public interface Predicate
    extends Comparable
{

    public abstract String toString();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}
