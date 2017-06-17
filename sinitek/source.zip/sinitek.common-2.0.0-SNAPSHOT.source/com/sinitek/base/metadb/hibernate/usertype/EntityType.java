// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.PropertyDBType;
import com.sinitek.base.metadb.hibernate.proxy.EntityProxy;
import java.sql.*;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class EntityType extends AbstractUserType
    implements ParameterizedType
{

    public EntityType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            sqlType
        });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/metadb/hibernate/proxy/EntityProxy;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        EntityProxy proxy = new EntityProxy(targetEntityName, targetPropertyName);
        Object dbValue = rs.getObject(names[0]);
        proxy.setDBValue(dbValue);
        return proxy;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        EntityProxy proxy = (EntityProxy)value;
        if(proxy == null)
        {
            st.setNull(index, sqlType);
            return;
        }
        Object dbValue = proxy.getDBValue();
        if(dbValue != null)
            st.setObject(index, dbValue);
        else
            st.setNull(index, sqlType);
    }

    public Object deepCopy(Object value)
        throws HibernateException
    {
        return value;
    }

    public void setParameterValues(Properties parameters)
    {
        targetEntityName = parameters.getProperty("TARGETENTITYNAME");
        targetPropertyName = parameters.getProperty("TARGETPROPERTYNAME");
        String type = parameters.getProperty("TARGETPROPERTYDBTYPE");
        PropertyDBType dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), type);
        if(dbType.equals(PropertyDBType.INTEGER))
            sqlType = 4;
        else
        if(dbType.equals(PropertyDBType.CHAR))
            sqlType = 1;
        else
        if(dbType.equals(PropertyDBType.DATE))
            sqlType = 91;
        else
        if(dbType.equals(PropertyDBType.DOUBLE))
            sqlType = 8;
        else
        if(dbType.equals(PropertyDBType.FLOAT))
            sqlType = 6;
        else
        if(dbType.equals(PropertyDBType.VARCHAR))
            sqlType = 12;
    }

    private String targetEntityName;
    private String targetPropertyName;
    private int sqlType;
}
