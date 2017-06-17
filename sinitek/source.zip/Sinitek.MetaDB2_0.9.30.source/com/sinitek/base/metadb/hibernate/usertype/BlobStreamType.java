// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlobStreamType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.hibernate.proxy.BlobStreamValue;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractStreamUserType

public class BlobStreamType extends AbstractStreamUserType
{

    public BlobStreamType()
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
        return com/sinitek/base/metadb/hibernate/proxy/BlobStreamValue;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        java.sql.Blob blob = rs.getBlob(names[0]);
        return new BlobStreamValue(blob, entityName);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        BlobStreamValue bsv = (BlobStreamValue)value;
        InputStream data = null;
        try
        {
            data = bsv.getData();
            if(data == null)
                data = bsv.getOrigInputStream();
            if(data != null)
                st.setBinaryStream(index, data, (int)bsv.getDataSize());
            else
                st.setNull(index, 2004);
        }
        catch(IOException e)
        {
            throw new HibernateException("\u5904\u7406BLOB\u6570\u636E\u5931\u8D25", e);
        }
    }
}
