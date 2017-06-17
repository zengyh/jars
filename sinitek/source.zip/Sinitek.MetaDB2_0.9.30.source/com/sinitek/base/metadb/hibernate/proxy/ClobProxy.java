// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClobProxy.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.MetaDBCoreException;
import java.io.*;
import java.sql.Clob;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractProxyValue

public class ClobProxy extends AbstractProxyValue
{

    public ClobProxy()
    {
    }

    public Object getValue()
    {
        Reader reader;
        Exception exception;
        if(value == null)
        {
            if(dbValue == null)
                return null;
            reader = null;
            try
            {
                StringBuffer sb = new StringBuffer();
                reader = dbValue.getCharacterStream();
                char buffer[] = new char[256];
                for(int length = reader.read(buffer); length > 0; length = reader.read(buffer))
                    sb.append(buffer, 0, length);

                value = sb.toString();
            }
            catch(Exception e)
            {
                throw new MetaDBCoreException("0007", e);
            }
            finally
            {
                if(reader == null) goto _L0; else goto _L0
            }
            if(reader != null)
                try
                {
                    reader.close();
                }
                catch(Exception e)
                {
                    LOGGER.warn("\u5173\u95EDCLOB\u7684\u6570\u636E\u6D41\u5931\u8D25", e);
                }
        }
        break MISSING_BLOCK_LABEL_145;
        try
        {
            reader.close();
        }
        catch(Exception e)
        {
            LOGGER.warn("\u5173\u95EDCLOB\u7684\u6570\u636E\u6D41\u5931\u8D25", e);
        }
        throw exception;
        return value;
    }

    public void setValue(Object value)
    {
        this.value = (String)value;
        if(value == null)
            dbValue = null;
    }

    public Object getDBValue()
    {
        if(value != null)
            return Hibernate.createClob(value);
        else
            return null;
    }

    public void setDBValue(Object value)
    {
        dbValue = (Clob)value;
    }

    public Class getValueType()
    {
        return java/lang/String;
    }

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        value = (String)in.readObject();
    }

    private void writeObject(ObjectOutputStream out)
        throws IOException
    {
        out.writeObject(getValue());
    }

    private transient Clob dbValue;
    private String value;
}
