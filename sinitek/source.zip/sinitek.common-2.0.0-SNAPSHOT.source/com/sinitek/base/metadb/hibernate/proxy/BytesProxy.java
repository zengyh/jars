// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BytesProxy.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.MetaDBCoreException;
import java.io.*;
import java.sql.Blob;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractProxyValue

public class BytesProxy extends AbstractProxyValue
{

    public BytesProxy()
    {
    }

    public Object getValue()
    {
        ByteArrayOutputStream baos;
        InputStream dvIs;
        if(value != null)
            break MISSING_BLOCK_LABEL_147;
        if(dbValue == null)
            return null;
        baos = new ByteArrayOutputStream();
        dvIs = null;
        try
        {
            dvIs = dbValue.getBinaryStream();
            byte buffer[] = new byte[256];
            for(int length = dvIs.read(buffer); length > 0; length = dvIs.read(buffer))
                baos.write(buffer, 0, length);

            baos.flush();
            value = baos.toByteArray();
        }
        catch(Exception ex)
        {
            throw new MetaDBCoreException("0006");
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
        break MISSING_BLOCK_LABEL_147;
        Exception exception;
        exception;
        if(dvIs != null)
            try
            {
                dvIs.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95EDBLOB\u7684\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        throw exception;
        return value;
    }

    public void setValue(Object value)
    {
        this.value = (byte[])(byte[])value;
    }

    public Object getDBValue()
    {
        if(value != null)
            return Hibernate.createBlob(value);
        else
            return null;
    }

    public void setDBValue(Object value)
    {
        dbValue = (Blob)value;
    }

    public Class getValueType()
    {
        return [B;
    }

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        value = (byte[])(byte[])in.readObject();
    }

    private void writeObject(ObjectOutputStream out)
        throws IOException
    {
        out.writeObject(getValue());
    }

    private transient Blob dbValue;
    private byte value[];
}
