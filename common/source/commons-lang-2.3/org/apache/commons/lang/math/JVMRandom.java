// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JVMRandom.java

package org.apache.commons.lang.math;

import java.util.Random;

public final class JVMRandom extends Random
{

    public JVMRandom()
    {
        constructed = false;
        constructed = true;
    }

    public synchronized void setSeed(long seed)
    {
        if(constructed)
            throw new UnsupportedOperationException();
        else
            return;
    }

    public synchronized double nextGaussian()
    {
        throw new UnsupportedOperationException();
    }

    public void nextBytes(byte byteArray[])
    {
        throw new UnsupportedOperationException();
    }

    public int nextInt()
    {
        return nextInt(0x7fffffff);
    }

    public int nextInt(int n)
    {
        if(n <= 0)
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        else
            return (int)(Math.random() * (double)n);
    }

    public long nextLong()
    {
        return nextLong(0x7fffffffffffffffL);
    }

    public static long nextLong(long n)
    {
        if(n <= 0L)
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        else
            return (long)(Math.random() * (double)n);
    }

    public boolean nextBoolean()
    {
        return Math.random() > 0.5D;
    }

    public float nextFloat()
    {
        return (float)Math.random();
    }

    public double nextDouble()
    {
        return Math.random();
    }

    private static final long serialVersionUID = 1L;
    private boolean constructed;
}
