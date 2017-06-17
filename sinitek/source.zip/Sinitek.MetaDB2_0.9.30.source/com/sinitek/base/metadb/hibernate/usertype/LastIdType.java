// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastIdType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.sql.*;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class LastIdType extends AbstractUserType
{

    public LastIdType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            4
        });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/metadb/hibernate/proxy/LastIdProxy;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        Number lastId = (Number)rs.getObject(names[0]);
        IEntity entity = ((IMetaObject)owner).getEntity();
        LastIdProxy proxy = new LastIdProxy(entity);
        if(lastId == null)
            proxy.setDBValue(null);
        else
            proxy.setDBValue(new Integer(lastId.intValue()));
        return proxy;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        Integer lastId = (Integer)value;
        if(lastId == null)
            st.setNull(index, 4);
        else
            st.setInt(index, lastId.intValue());
    }

    public Object deepCopy(Object value)
        throws HibernateException
    {
        return value;
    }
}
