// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISerializableObject.java

package com.sinitek.base.control;


public interface ISerializableObject
{

    public abstract byte[] toBytes();

    public abstract void fromBytes(byte abyte0[]);
}