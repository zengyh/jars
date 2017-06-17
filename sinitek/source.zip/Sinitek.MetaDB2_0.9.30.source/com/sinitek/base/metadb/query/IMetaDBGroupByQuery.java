// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBGroupByQuery.java

package com.sinitek.base.metadb.query;


// Referenced classes of package com.sinitek.base.metadb.query:
//            IMetaDBQuery

public interface IMetaDBGroupByQuery
    extends IMetaDBQuery
{

    public abstract void setGroupBy(String s);

    public abstract void setGroupBy(String as[]);

    public abstract void addCountResult(String s, String s1);

    public abstract void addSumResult(String s, String s1);

    public abstract void addAvgResult(String s, String s1);
}
