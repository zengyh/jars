// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextInfoHandler.java

package com.sinitek.base.metadb.control;

import com.sinitek.base.control.*;
import com.sinitek.base.metadb.MetaDBContextFactory;
import java.util.Properties;

public class MetaDBContextInfoHandler extends AbstractHandler
{

    public MetaDBContextInfoHandler()
    {
    }

    protected void initParam(Properties properties1)
        throws HandleException
    {
    }

    public IHandleResult handle(IRequest request)
        throws HandleException
    {
        SimpleHanlderResult result = new SimpleHanlderResult(true);
        result.setReturnMessage("\u6210\u529F");
        result.addReturnParameter("info", MetaDBContextFactory.getInstance().getContextInfo());
        return result;
    }
}
