// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBQuery.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.IEntity;
import java.util.*;

// Referenced classes of package com.sinitek.base.metadb.query:
//            IMetaDBGroupByQuery

public interface IMetaDBQuery
{

    public abstract IEntity getEntity();

    public abstract String getQueryString();

    public abstract void setFirstResult(int i);

    public abstract void setMaxResult(int i);

    public abstract void setCacheUse(boolean flag);

    public abstract boolean isCacheUse();

    public abstract void setParameter(String s, Object obj);

    public abstract void setParameters(Map map);

    public abstract Map getParameters();

    public abstract int getResultCount();

    public abstract List getResult();

    public abstract void setOrderBy(String s);

    public abstract void setDefaultOrderBy(String s);

    public abstract Iterator getIterator(int i);

    public abstract Iterator getIterator();

    public abstract IMetaDBGroupByQuery getGroupByQuery();
}
