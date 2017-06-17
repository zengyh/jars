// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractStreamUserType.java

package com.sinitek.base.metadb.hibernate.usertype;

import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractUserType

public abstract class AbstractStreamUserType extends AbstractUserType
    implements ParameterizedType
{

    public AbstractStreamUserType()
    {
    }

    public Object deepCopy(Object value)
        throws HibernateException
    {
        return value;
    }

    public void setParameterValues(Properties parameters)
    {
        entityName = parameters.getProperty("ENTITYNAME");
    }

    public static final String PROP_ENTITYNAME = "ENTITYNAME";
    protected String entityName;
}
