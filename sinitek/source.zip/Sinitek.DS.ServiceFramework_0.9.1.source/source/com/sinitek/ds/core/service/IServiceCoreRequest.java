// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceCoreRequest.java

package com.sinitek.ds.core.service;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.framework.dict.IDictionaryItem;

// Referenced classes of package com.sinitek.ds.core.service:
//            IServiceRequest, ServiceException

public interface IServiceCoreRequest
    extends IServiceRequest
{

    public abstract IEnumItem getEnumItem(String s)
        throws ServiceException;

    public abstract IDictionaryItem getDictionaryItem(String s)
        throws ServiceException;
}
