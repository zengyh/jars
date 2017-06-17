// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.hibernate.proxy.ClassProxy;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class ClassType extends AbstractUserType
{

    public ClassType()
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
        return com/sinitek/base/metadb/hibernate/proxy/ClassProxy;
    }

    public Object nullSafeGet(ResultSet resultSet, String strings[], Object o)
        throws HibernateException, SQLException
    {
        Blob blob = resultSet.getBlob(strings[0]);
        ClassProxy proxy = new ClassProxy();
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
            ClassProxy bp = (ClassProxy)o;
            Blob blob = (Blob)bp.getDBValue();
            if(blob == null)
                preparedStatement.setNull(i, 2004);
            else
                preparedStatement.setBinaryStream(i, blob.getBinaryStream(), (int)blob.length());
        }
    }

    public Object deepCopy(Object o)
        throws HibernateException
    {
        ClassProxy orig = (ClassProxy)o;
        ClassProxy clone = new ClassProxy();
        clone.setDBValue(orig.getDBValue());
        return clone;
    }
}
