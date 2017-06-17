// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BooleanType.java

package com.sinitek.base.metadb.hibernate.usertype;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class BooleanType extends AbstractUserType
    implements Serializable
{

    public BooleanType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            1
        });
    }

    public Class returnedClass()
    {
        return java/lang/Boolean;
    }

    public Object nullSafeGet(ResultSet resultSet, String strings[], Object o)
        throws HibernateException, SQLException
    {
        String booleanStr = resultSet.getString(strings[0]);
        return StringUtils.isBlank(booleanStr) ? null : Boolean.valueOf("1".equalsIgnoreCase(booleanStr));
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i)
        throws HibernateException, SQLException
    {
        Boolean bValue = (Boolean)o;
        if(o != null)
            preparedStatement.setString(i, bValue.booleanValue() ? "1" : "0");
        else
            preparedStatement.setNull(i, 1);
    }

    public Object deepCopy(Object o)
        throws HibernateException
    {
        return o;
    }
}
