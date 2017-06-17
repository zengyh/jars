// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISQLUpdaterImpl.java

package com.sinitek.base.metadb;

import java.util.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBContextImpl, MetaDBCoreException, ISQLUpdater, MetaDBContextFactory

public class ISQLUpdaterImpl
    implements ISQLUpdater
{

    public ISQLUpdaterImpl(String updateSql)
    {
        paramsMap = new HashMap();
        this.updateSql = updateSql;
    }

    public void setParameter(String parameter, Object value)
    {
        paramsMap.put(parameter, value);
    }

    public void setParameters(Map parameters)
    {
        paramsMap.putAll(parameters);
    }

    public int executeUpdate()
    {
        MetaDBContextImpl ctx;
        ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        ctx.begin();
        int i;
        try
        {
            Session session = ctx.getSession();
            SQLQuery query = session.createSQLQuery(updateSql);
            Map _temp = new HashMap();
            for(Iterator iter = paramsMap.entrySet().iterator(); iter.hasNext();)
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
                if(entry.getValue() == null)
                    query.setParameter((String)entry.getKey(), entry.getValue());
                else
                    _temp.put(entry.getKey(), entry.getValue());
            }

            query.setProperties(_temp);
            int ret = query.executeUpdate();
            if(ctx.isFlushOnOperation())
                ctx.getSession().flush();
            i = ret;
        }
        catch(Exception ex)
        {
            throw new MetaDBCoreException("0039", ex);
        }
        ctx.close();
        return i;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    protected Map paramsMap;
    protected String updateSql;
}
