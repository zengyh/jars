// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityBaseInfoLoader.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.impl.EntityImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

// Referenced classes of package com.sinitek.base.metadb.config:
//            MetaDBConfigException, GlobalConfig, MetaDBStartMode, IDataSourceProvider, 
//            IdGeneratorFactory

public class EntityBaseInfoLoader
{
    protected static class SimpleEntityRowMapper
        implements RowMapper
    {

        public Object mapRow(ResultSet rs, int i)
            throws SQLException
        {
            EntityImpl entity = new EntityImpl();
            String entityName = rs.getString("ENTITYNAME").trim().toUpperCase();
            if(ignoreMap.containsKey(entityName))
            {
                EntityBaseInfoLoader.LOGGER.info((new StringBuilder()).append("\u5B9E\u4F53[").append(entityName).append("]\u88AB\u914D\u7F6E\u5FFD\u7565").toString());
                return null;
            }
            String catalogKey = rs.getString("CATALOGKEY").trim().toUpperCase();
            String entityInfo = rs.getString("ENTITYINFO").trim();
            String entityTable = rs.getString("ENTITYTABLE");
            String idGenerator = rs.getString("IDGENERATOR");
            idGenerator = idGenerator != null ? idGenerator.trim() : "";
            IIdGenerator gen = IdGeneratorFactory.createGenerator(idGenerator);
            boolean hisFlag = "1".equalsIgnoreCase(rs.getString("HISTORYFLAG"));
            boolean remFlag = "1".equalsIgnoreCase(rs.getString("REMOVEFLAG"));
            boolean idCacheFlag = "1".equalsIgnoreCase(rs.getString("IDCACHEFLAG"));
            int idCacheSize = rs.getInt("IDCACHESIZE");
            String interfaceClassName = rs.getString("INTERFACENAME");
            if(StringUtils.isNotEmpty(interfaceClassName))
            {
                interfaceClassName = interfaceClassName.trim();
                try
                {
                    Class interfaceClass = Class.forName(interfaceClassName);
                    if(!interfaceClass.isInterface())
                        throw new MetaDBConfigException("0045", new Object[] {
                            entityName, interfaceClassName
                        });
                    if(!com/sinitek/base/metadb/IMetaObjectImpl.isAssignableFrom(interfaceClass))
                        throw new MetaDBConfigException("0046", new Object[] {
                            entityName, interfaceClassName
                        });
                    entity.setInterface(interfaceClass);
                }
                catch(ClassNotFoundException e)
                {
                    EntityBaseInfoLoader.LOGGER.warn((new StringBuilder()).append("\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684\u63A5\u53E3\u7C7B[").append(interfaceClassName.trim()).append("]\u4E0D\u5B58\u5728\uFF0C\u5FFD\u7565\u8BE5\u5B9E\u4F53").toString());
                    return null;
                }
                String implClassName = rs.getString("CLASSNAME");
                if(StringUtils.isEmpty(implClassName))
                {
                    int pos = interfaceClassName.lastIndexOf('.');
                    String className = interfaceClassName;
                    String packageName = "";
                    if(pos > 0)
                    {
                        className = interfaceClassName.substring(pos + 1);
                        packageName = interfaceClassName.substring(0, pos + 1);
                    }
                    if(className.startsWith("I"))
                        className = className.substring(1);
                    className = StringUtils.capitalize(className);
                    className = (new StringBuilder()).append(className).append("Impl").toString();
                    className = (new StringBuilder()).append(packageName).append(className).toString();
                    implClassName = className;
                }
                try
                {
                    Class implClass = Class.forName(implClassName.trim());
                    if(!entity.getInterface().isAssignableFrom(implClass))
                        throw new MetaDBConfigException("0044", new Object[] {
                            entityName, implClassName, interfaceClassName
                        });
                    entity.setImplClass(implClass);
                }
                catch(ClassNotFoundException e)
                {
                    EntityBaseInfoLoader.LOGGER.info((new StringBuilder()).append("\u5B9E\u4F53[").append(entityName).append("]\u6240\u914D\u7F6E\u7684\u5B9E\u73B0\u7C7B[").append(implClassName).append("]\u4E0D\u5B58\u5728\uFF0C\u5FFD\u7565\u8BE5\u5B9E\u4F53").toString());
                    return null;
                }
            }
            if(idCacheFlag && idCacheSize <= 0)
            {
                throw new MetaDBConfigException("0053", new Object[] {
                    entityName
                });
            } else
            {
                entity.setEntityName(entityName);
                entity.setEntityCatalogKey(catalogKey);
                entity.setTableName(entityTable);
                entity.setEntityInfo(entityInfo);
                entity.setIdGenerator(gen);
                entity.setHistorySupport(hisFlag);
                entity.setRemoveSupport(remFlag);
                entity.setIdCacheSupport(idCacheFlag);
                entity.setIdCacheSize(idCacheFlag ? idCacheSize : -1);
                return entity;
            }
        }

        private Map ignoreMap;

        public SimpleEntityRowMapper(Map ignoreMap)
        {
            this.ignoreMap = ignoreMap;
        }
    }


    public EntityBaseInfoLoader()
    {
    }

    public List loadAllEntityBasicInfo(GlobalConfig config, MetaDBStartMode mode, String filter[])
        throws MetaDBException
    {
        ArrayList ret;
        String querySql;
        if(mode == MetaDBStartMode.FULL)
            querySql = "select * from metadb_entity where 1=1";
        else
        if(mode == MetaDBStartMode.CATALOG)
            querySql = "select * from metadb_entity where lower(CATALOGKEY) in (:filter)";
        else
            querySql = "select * from metadb_entity where lower(ENTITYNAME) in (:filter)";
        if("true".equalsIgnoreCase(config.getMetaDBProperties().getProperty("metadb.enableIgnoreFlag", "true")))
            querySql = (new StringBuilder()).append(querySql).append(" and enableflag='1'").toString();
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(config.getDataSourceProvider().getDataSource());
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("filter", Arrays.asList(filter));
        String ignoreEntitys = config.getMetaDBProperties().getProperty("metadb.disableEntitys", "").trim();
        String ignores[] = ignoreEntitys.split(",");
        Map ignoreMap = new HashMap(ignores.length);
        for(int i = 0; i < ignores.length; i++)
        {
            String tmp = ignores[i].trim().toUpperCase();
            if(tmp.length() > 0)
                ignoreMap.put(tmp, tmp);
        }

        List retList = template.query(querySql, param, new SimpleEntityRowMapper(ignoreMap));
        ret = new ArrayList();
        Iterator iter = retList.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            Object _obj = iter.next();
            if(_obj != null)
                ret.add(_obj);
        } while(true);
        return ret;
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new MetaDBConfigException("0010", e);
    }

    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }

}
