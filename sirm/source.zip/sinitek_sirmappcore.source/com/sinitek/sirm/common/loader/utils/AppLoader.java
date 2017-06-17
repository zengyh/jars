// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLoader.java

package com.sinitek.sirm.common.loader.utils;


// Referenced classes of package com.sinitek.sirm.common.loader.utils:
//            IAppLoaderContext

public interface AppLoader
{

    public abstract boolean isInitialized();

    public abstract void initialize(IAppLoaderContext iapploadercontext);

    public abstract void destroy(IAppLoaderContext iapploadercontext);
}
