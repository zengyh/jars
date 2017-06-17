// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBStarter.java

package com.sinitek.base.metadb.starter;

import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.starter.IStarter;
import java.util.Properties;

public class MetaDBStarter
    implements IStarter
{

    public MetaDBStarter()
    {
    }

    public void start(Properties prop)
    {
        MetaDBContextFactory.getInstance();
    }
}
