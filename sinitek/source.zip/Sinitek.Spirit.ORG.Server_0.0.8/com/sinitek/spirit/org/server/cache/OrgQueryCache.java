// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgQueryCache.java

package com.sinitek.spirit.org.server.cache;

import java.util.List;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            OrgQuery

public interface OrgQueryCache
{

    public abstract void markDirty();

    public abstract OrgQuery getOrgQuery();

    public abstract List getResult();
}
