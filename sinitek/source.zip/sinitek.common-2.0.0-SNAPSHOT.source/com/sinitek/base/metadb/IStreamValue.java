// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IStreamValue.java

package com.sinitek.base.metadb;

import java.io.*;

public interface IStreamValue
    extends Serializable
{

    public abstract InputStream getInputStream()
        throws IOException;

    public abstract OutputStream getOutputStream()
        throws IOException;

    public abstract IStreamValue deepCopy();
}
