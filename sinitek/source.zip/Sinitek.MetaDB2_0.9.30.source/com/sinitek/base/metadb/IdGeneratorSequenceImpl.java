// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorSequenceImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.config.IDataSourceProvider;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBCoreException, IIdGenerator, MetaDBException, IEntity, 
//            MetaDBLoggerFactory

public class IdGeneratorSequenceImpl
    implements IIdGenerator
{

    public IdGeneratorSequenceImpl()
    {
    }

    public int generateId(IEntity entity, IDataSourceProvider dataSourceProvider)
        throws MetaDBException
    {
        String sql;
        JdbcTemplate template;
        String seqName = (new StringBuilder()).append("METADB_").append(entity.getEntityName().toUpperCase()).append("_SEQ").toString();
        sql = (new StringBuilder()).append("select ").append(seqName).append(".NEXTVAL from dual").toString();
        template = new JdbcTemplate(dataSourceProvider.getDataSource());
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u4E3A\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u751F\u6210\u4E3B\u952E\u503C").toString());
        return template.queryForInt(sql);
        Exception ex;
        ex;
        throw new MetaDBCoreException("0001", ex, new Object[] {
            entity.getEntityName()
        });
    }

    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
