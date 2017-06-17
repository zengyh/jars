// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBLoggerFactory.java

package com.sinitek.base.metadb;

import org.apache.log4j.Logger;

public final class MetaDBLoggerFactory
{

    public MetaDBLoggerFactory()
    {
    }

    public static final Logger LOGGER_CONFIG = Logger.getLogger("METADB.CONFIG");
    public static final Logger LOGGER_CACHE = Logger.getLogger("METADB.CACHE");
    public static final Logger LOGGER_CORE = Logger.getLogger("METADB.CORE");
    public static final Logger LOGGER_SYN = Logger.getLogger("METADB.SYN");

}
