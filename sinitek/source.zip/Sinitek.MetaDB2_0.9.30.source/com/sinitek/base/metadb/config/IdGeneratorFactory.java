// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorFactory.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

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
        if(StringUtils.isBlank(className) || "TABLE".equalsIgnoreCase(className))
            return DEFAULT_GENERATOR;
        if("SEQ".equalsIgnoreCase(className))
            return SEQ_GENERATOR;
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
        Object oGen;
        Class genClass = Class.forName(className);
        oGen = genClass.newInstance();
        if(oGen instanceof IIdGenerator)
            return (IIdGenerator)oGen;
        try
        {
            throw new MetaDBConfigException("0013", new Object[] {
                className
            });
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
    }

    private static Map generatorMap = new HashMap();
    private static final IIdGenerator DEFAULT_GENERATOR = new IdGeneratorDefaultImpl();
    private static final IIdGenerator SEQ_GENERATOR = new IdGeneratorSequenceImpl();

}
