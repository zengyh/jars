// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClobType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.hibernate.proxy.ClobProxy;
import java.io.StringReader;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class ClobType extends AbstractUserType
{

    public ClobType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            2005
        });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/metadb/hibernate/proxy/ClobProxy;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        ClobProxy cp = new ClobProxy();
        java.sql.Clob clob = rs.getClob(names[0]);
        cp.setDBValue(clob);
        return cp;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        if(value == null)
        {
            st.setNull(index, 2005);
        } else
        {
            String str = null;
            if(value instanceof ClobProxy)
            {
                ClobProxy cp = (ClobProxy)value;
                str = (String)cp.getValue();
            } else
            {
                str = value.toString();
            }
            if(str != null)
                st.setCharacterStream(index, new StringReader(str), str.length());
            else
                st.setNull(index, 2005);
        }
    }

    public Object deepCopy(Object value)
        throws HibernateException
    {
        return value;
    }
}
