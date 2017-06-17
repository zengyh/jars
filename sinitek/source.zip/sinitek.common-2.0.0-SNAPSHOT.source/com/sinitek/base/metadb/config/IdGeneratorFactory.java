// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorFactory.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.sinitek.base.metadb.config:
//            MetaDBConfigException

public class IdGeneratorFactory
{

    public IdGeneratorFactory()
    {
    }

    public static synchronized IIdGenerator createGenerator(String className)
        throws MetaDBException
    {
        if(className == null || className.length() == 0)
            return DEFAULT_GENERATOR;
        IIdGenerator gen = (IIdGenerator)generatorMap.get(className);
        if(gen != null)
        {
            return gen;
        } else
        {
            gen = createNewGenerator(className);
            generatorMap.put(className, gen);
            return gen;
        }
    }

    private static IIdGenerator createNewGenerator(String className)
        throws MetaDBException
    {
        try
        {
            Class genClass = Class.forName(className);
            Object oGen = genClass.newInstance();
            if(oGen instanceof IIdGenerator)
                return (IIdGenerator)oGen;
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception ex)
        {
            throw new MetaDBConfigException("0012", ex, new Object[] {
                className
            });
        }
        throw new MetaDBConfigException("0013", new Object[] {
            className
        });
    }

    private static Map generatorMap = new HashMap();
    private static final IIdGenerator DEFAULT_GENERATOR = new IdGeneratorDefaultImpl();

}
