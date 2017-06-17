// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityProxyType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType, EntityProxyInvocationHandler

public class EntityProxyType extends AbstractUserType
    implements ParameterizedType
{

    public EntityProxyType()
    {
    }

    public int[] sqlTypes()
    {
        if(relaDBType.equals(PropertyDBType.INTEGER))
            return (new int[] {
                4
            });
        if(relaDBType.equals(PropertyDBType.CHAR))
            return (new int[] {
                1
            });
        if(relaDBType.equals(PropertyDBType.VARCHAR))
            return (new int[] {
                12
            });
        else
            throw new IllegalArgumentException("\u4E0D\u652F\u6301\u7684\u5C5E\u6027\u6570\u636E\u5E93\u7C7B\u578B\uFF0C\u53EA\u652F\u6301Int\uFF0CChar\u548CVarchar");
    }

    public Class returnedClass()
    {
        try
        {
            return Class.forName(clzName);
        }
        catch(ClassNotFoundException e)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("\u975E\u6CD5\u7684\u7C7B\u540D[").append(clzName).append("]").toString());
        }
    }

    public Object nullSafeGet(ResultSet resultSet, String strings[], Object o)
        throws HibernateException, SQLException
    {
        IMetaObjectImpl impl = (IMetaObjectImpl)o;
        if(getProperty().getPropertyDBType().equals(PropertyDBType.INTEGER))
        {
            BigDecimal intValue = resultSet.getBigDecimal(strings[0]);
            if(intValue == null)
            {
                return null;
            } else
            {
                Integer value = Integer.valueOf(intValue.intValue());
                impl.setEntityOrigDBValue(properName, intValue);
                return EntityProxyInvocationHandler.createEntityProxy(getProperty(), value, returnedClass());
            }
        }
        String strValue = resultSet.getString(strings[0]);
        if(strValue == null)
        {
            return null;
        } else
        {
            impl.setEntityOrigDBValue(properName, strValue);
            return EntityProxyInvocationHandler.createEntityProxy(getProperty(), strValue, returnedClass());
        }
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i)
        throws HibernateException, SQLException
    {
        if(o == null)
        {
            preparedStatement.setNull(i, sqlTypes()[0]);
        } else
        {
            IMetaObject mo = (IMetaObject)o;
            if(getProperty().getPropertyDBType().equals(PropertyDBType.INTEGER))
                preparedStatement.setInt(i, ((Integer)mo.get(getProperty().getPropertyName())).intValue());
            else
                preparedStatement.setString(i, (String)mo.get(getProperty()));
        }
    }

    public Object deepCopy(Object o)
        throws HibernateException
    {
        return o;
    }

    public void setParameterValues(Properties properties)
    {
        targetEntityName = properties.getProperty("TARGETENTITYNAME");
        targetPropertyName = properties.getProperty("TARGETPROPERTYNAME");
        relaDBType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), properties.getProperty("TARGETPROPERTYDBTYPE"));
        clzName = properties.getProperty("TARGETENTITYINTERFACE");
        properName = properties.getProperty("PROPERTYNAME");
    }

    private IProperty getProperty()
    {
        if(property == null)
            property = MetaDBContextFactory.getInstance().getEntity(targetEntityName).getProperty(targetPropertyName);
        return property;
    }

    private IProperty property;
    private String targetEntityName;
    private String targetPropertyName;
    private String properName;
    private PropertyDBType relaDBType;
    private String clzName;
}
