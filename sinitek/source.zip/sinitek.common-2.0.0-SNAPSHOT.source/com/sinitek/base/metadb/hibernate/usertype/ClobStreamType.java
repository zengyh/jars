// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClobStreamType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.hibernate.proxy.ClobStreamValue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractStreamUserType

public class ClobStreamType extends AbstractStreamUserType
{

    public ClobStreamType()
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
        return com/sinitek/base/metadb/hibernate/proxy/ClobStreamValue;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        java.sql.Clob clob = rs.getClob(names[0]);
        return new ClobStreamValue(clob, entityName);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        ClobStreamValue bsv = (ClobStreamValue)value;
        java.io.InputStream data = null;
        try
        {
            data = bsv.getData();
            if(data != null)
                st.setCharacterStream(index, new InputStreamReader(data), (int)bsv.getDataSize());
            else
                st.setNull(index, 2005);
        }
        catch(IOException e)
        {
            throw new HibernateException("\u5904\u7406CLOB\u6570\u636E\u5931\u8D25", e);
        }
    }
}
