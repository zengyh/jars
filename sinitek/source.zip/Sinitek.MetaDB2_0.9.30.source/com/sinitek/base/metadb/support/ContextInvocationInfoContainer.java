// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContextInvocationInfoContainer.java

package com.sinitek.base.metadb.support;


// Referenced classes of package com.sinitek.base.metadb.support:
//            ContextInvocationInfo

public class ContextInvocationInfoContainer
{

    public ContextInvocationInfoContainer()
    {
    }

    public static ContextInvocationInfo getCurrentContext()
    {
        return (ContextInvocationInfo)container.get();
    }

    public static ContextInvocationInfo createContext()
    {
        ContextInvocationInfo ret = new ContextInvocationInfo();
        container.set(ret);
        return ret;
    }

    public static void clear()
    {
        container.set(null);
    }

    public static boolean isInProxyMode()
    {
        return container.get() != null;
    }

    private static final ThreadLocal container = new ThreadLocal();

}
