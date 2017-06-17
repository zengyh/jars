// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.PropertyDBType;
import java.sql.*;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public class EnumType extends AbstractUserType
    implements ParameterizedType
{

    public EnumType()
    {
    }

    public int[] sqlTypes()
    {
        if(dbType.equals(PropertyDBType.INTEGER))
            return (new int[] {
                4
            });
        else
            return (new int[] {
                1
            });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/enumsupport/IEnumItem;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        if(dbType.equals(PropertyDBType.INTEGER))
        {
            Number num = (Number)rs.getObject(names[0]);
            if(num == null)
            {
                return null;
            } else
            {
                int value = num.intValue();
                return EnumItemContext.getInstance().getEnumItem(enumName, value);
            }
        }
        String strValue = rs.getString(names[0]);
        if(strValue == null)
            return null;
        else
            return EnumItemContext.getInstance().getEnumItem(enumName, strValue);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        IEnumItem item = (IEnumItem)value;
        if(item == null)
        {
            if(dbType.equals(PropertyDBType.INTEGER))
                st.setNull(index, 4);
            else
                st.setNull(index, 1);
        } else
        if(dbType.equals(PropertyDBType.INTEGER))
            st.setInt(index, item.getEnumItemValue());
        else
            st.setString(index, item.getEnumItemName());
    }

    public Object deepCopy(Object value)
        throws HibernateException
    {
        return value;
    }

    public void setParameterValues(Properties parameters)
    {
        enumName = parameters.getProperty("ENUMNAME");
        dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), parameters.getProperty("DBTYPE"));
    }

    private String enumName;
    private PropertyDBType dbType;
}
