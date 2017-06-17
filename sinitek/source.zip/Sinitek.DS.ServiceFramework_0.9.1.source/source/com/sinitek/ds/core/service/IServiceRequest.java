// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceRequest.java

package com.sinitek.ds.core.service;

import java.io.Serializable;

// Referenced classes of package com.sinitek.ds.core.service:
//            IParamObject

public interface IServiceRequest
    extends IParamObject, Serializable
{

    public abstract String getFunctionCode();
}
