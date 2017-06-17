// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassProxy.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.MetaDBCoreException;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.io.*;
import java.sql.Blob;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractProxyValue

public class ClassProxy extends AbstractProxyValue
{

    public ClassProxy()
    {
    }

    public Object getValue()
    {
        InputStream dvIs;
        ObjectInputStream ois;
        Exception exception;
        if(value == null)
        {
            if(dbValue == null)
                return null;
            dvIs = null;
            ois = null;
            try
            {
                dvIs = dbValue.getBinaryStream();
                ois = new ObjectInputStream(dvIs);
                value = (Serializable)ois.readObject();
            }
            catch(Exception ex)
            {
                throw new MetaDBCoreException("0006");
            }
            finally
            {
                if(dvIs == null) goto _L0; else goto _L0
            }
            if(dvIs != null)
                try
                {
                    dvIs.close();
                }
                catch(IOException e)
                {
                    LOGGER.warn("\u5173\u95EDBLOB\u7684\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
            if(ois != null)
                try
                {
                    ois.close();
                }
                catch(IOException e)
                {
                    LOGGER.warn("\u5173\u95ED\u5BF9\u8C61\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
        }
        break MISSING_BLOCK_LABEL_157;
        try
        {
            dvIs.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95EDBLOB\u7684\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        if(ois != null)
            try
            {
                ois.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95ED\u5BF9\u8C61\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        throw exception;
        return value;
    }

    public void setValue(Object value)
    {
        this.value = (Serializable)value;
    }

    public Object getDBValue()
    {
        ByteArrayOutputStream baos;
        if(value == null)
            break MISSING_BLOCK_LABEL_40;
        baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(value);
        return Hibernate.createBlob(baos.toByteArray());
        return null;
        IOException e;
        e;
        throw new MetaDBCoreException("0009", e);
    }

    public void setDBValue(Object value)
    {
        dbValue = (Blob)value;
    }

    public Class getValueType()
    {
        return java/io/Serializable;
    }

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        value = (Serializable)in.readObject();
    }

    private void writeObject(ObjectOutputStream out)
        throws ClassNotFoundException, IOException
    {
        out.writeObject(getValue());
    }

    private transient Blob dbValue;
    private Serializable value;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
