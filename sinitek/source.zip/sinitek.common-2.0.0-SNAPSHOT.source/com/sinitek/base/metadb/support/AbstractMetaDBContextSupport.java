// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractMetaDBContextSupport.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.metadb.IMetaDBContext;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IMetaDBContextSupport, GetMetaDBContextHelper

public class AbstractMetaDBContextSupport
    implements IMetaDBContextSupport
{

    public AbstractMetaDBContextSupport()
    {
    }

    protected final IMetaDBContext getMetaDBContext()
    {
        return GetMetaDBContextHelper.getMetaDBContext();
    }
}
