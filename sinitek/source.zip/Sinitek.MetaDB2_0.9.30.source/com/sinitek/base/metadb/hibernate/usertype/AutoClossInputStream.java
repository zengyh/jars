// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AutoClossInputStream.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

public class AutoClossInputStream extends InputStream
{

    public AutoClossInputStream(InputStream origInputStream)
    {
        this.origInputStream = origInputStream;
    }

    public int read()
        throws IOException
    {
        int ret = origInputStream.read();
        if(ret <= 0)
        {
            MetaDBLoggerFactory.LOGGER_CORE.debug("\u8F93\u5165\u6D41\u8BFB\u53D6\u5B8C\u6BD5\uFF0C\u81EA\u52A8\u5173\u95ED");
            origInputStream.close();
        }
        return ret;
    }

    public int available()
        throws IOException
    {
        return origInputStream.available();
    }

    public void close()
        throws IOException
    {
        origInputStream.close();
    }

    public synchronized void mark(int readlimit)
    {
        origInputStream.mark(readlimit);
    }

    public boolean markSupported()
    {
        return origInputStream.markSupported();
    }

    public int read(byte b[])
        throws IOException
    {
        int ret = origInputStream.read(b);
        if(ret <= 0)
        {
            MetaDBLoggerFactory.LOGGER_CORE.debug("\u8F93\u5165\u6D41\u8BFB\u53D6\u5B8C\u6BD5\uFF0C\u81EA\u52A8\u5173\u95ED");
            origInputStream.close();
        }
        return ret;
    }

    public int read(byte b[], int off, int len)
        throws IOException
    {
        int ret = origInputStream.read(b, off, len);
        if(ret <= 0)
        {
            MetaDBLoggerFactory.LOGGER_CORE.debug("\u8F93\u5165\u6D41\u8BFB\u53D6\u5B8C\u6BD5\uFF0C\u81EA\u52A8\u5173\u95ED");
            origInputStream.close();
        }
        return ret;
    }

    public synchronized void reset()
        throws IOException
    {
        origInputStream.reset();
    }

    public long skip(long n)
        throws IOException
    {
        long ret = origInputStream.skip(n);
        if(ret <= 0L)
        {
            MetaDBLoggerFactory.LOGGER_CORE.debug("\u8F93\u5165\u6D41\u8BFB\u53D6\u5B8C\u6BD5\uFF0C\u81EA\u52A8\u5173\u95ED");
            origInputStream.close();
        }
        return ret;
    }

    private InputStream origInputStream;
}
