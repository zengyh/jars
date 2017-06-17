// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BytesType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.hibernate.proxy.BytesProxy;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class BytesType extends AbstractUserType
{

    public BytesType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            2004
        });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/metadb/hibernate/proxy/BytesProxy;
    }

    public Object nullSafeGet(ResultSet resultSet, String strings[], Object o)
        throws HibernateException, SQLException
    {
        Blob blob = resultSet.getBlob(strings[0]);
        BytesProxy proxy = new BytesProxy();
        proxy.setDBValue(blob);
        return proxy;
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i)
        throws HibernateException, SQLException
    {
        if(o == null)
        {
            preparedStatement.setNull(i, 2004);
        } else
        {
            BytesProxy bp = (BytesProxy)o;
            Blob blob = (Blob)bp.getDBValue();
            preparedStatement.setBinaryStream(i, blob.getBinaryStream(), (int)blob.length());
        }
    }

    public Object deepCopy(Object o)
        throws HibernateException
    {
        BytesProxy orig = (BytesProxy)o;
        BytesProxy clone = new BytesProxy();
        clone.setDBValue(orig.getDBValue());
        return clone;
    }
}
