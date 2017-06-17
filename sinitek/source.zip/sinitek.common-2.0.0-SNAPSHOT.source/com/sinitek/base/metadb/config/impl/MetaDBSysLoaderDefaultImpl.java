// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBSysLoaderDefaultImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.*;
import com.sinitek.base.metadb.hibernate.SessionFactoryBuilder;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

// Referenced classes of package com.sinitek.base.metadb.config.impl:
//            EntityImpl, PropertyImpl, MetaDBTableCreator

public class MetaDBSysLoaderDefaultImpl
    implements IMetaDBSysLoader
{

    public MetaDBSysLoaderDefaultImpl()
    {
        entityCatalogMap = new HashMap();
        entityMap = new HashMap();
        lock = Boolean.FALSE;
        startFlag = false;
    }

    public void initData(GlobalConfig globalConfig)
        throws MetaDBException
    {
        String mode = globalConfig.getMetaDBProperties().getProperty("metadb.startmode", "full").trim().toLowerCase();
        MetaDBStartMode startMode;
        try
        {
            startMode = (MetaDBStartMode)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/config/MetaDBStartMode, mode);
        }
        catch(Exception ex)
        {
            throw new MetaDBConfigException("0054", new Object[] {
                mode
            });
        }
        dataSource = globalConfig.getDataSourceProvider().getDataSource();
        String filterStr = "";
        if(startMode == MetaDBStartMode.CATALOG)
        {
            filterStr = globalConfig.getMetaDBProperties().getProperty("metadb.startmode.catalog");
            if(StringUtils.isBlank(filterStr))
                throw new MetaDBConfigException("0055");
        }
        if(startMode == MetaDBStartMode.ENTITY)
        {
            filterStr = globalConfig.getMetaDBProperties().getProperty("metadb.startmode.entity");
            if(StringUtils.isBlank(filterStr))
                throw new MetaDBConfigException("0056");
        }
        String filter[] = filterStr.toLowerCase().trim().split("[,]");
        LOGGER.debug((new StringBuilder()).append("MetaDB\u6309[").append(startMode.getEnumItemInfo()).append("]\u6A21\u5F0F\u542F\u52A8").toString());
        if(startMode != MetaDBStartMode.FULL)
            LOGGER.debug((new StringBuilder()).append("\u88C5\u8F7D\u76EE\u6807\u5185\u5BB9\u4E3A[").append(filterStr).append("]").toString());
        LOGGER.debug("\u5F00\u59CB\u88C5\u8F7D\u5B9E\u4F53\u76EE\u5F55\u5B9A\u4E49");
        List entityCatalogs = startMode != MetaDBStartMode.CATALOG ? (new EntityCatalogLoader()).getAllEntityCatalog(globalConfig.getDataSourceProvider()) : (new EntityCatalogLoader()).getEntityCatalog(globalConfig.getDataSourceProvider(), filter);
        IEntityCatalog entityCatalog;
        for(Iterator iter = entityCatalogs.iterator(); iter.hasNext(); entityCatalogMap.put(entityCatalog.getCatalogKey(), entityCatalog))
            entityCatalog = (IEntityCatalog)iter.next();

        LOGGER.debug((new StringBuilder()).append("\u5B9E\u4F53\u76EE\u5F55\u5B9A\u4E49\u88C5\u8F7D\u5B8C\u6210\uFF0C\u83B7\u5F97[").append(entityCatalogs.size()).append("]\u4E2A\u5B9E\u4F53\u76EE\u5F55\u5B9A\u4E49").toString());
        LOGGER.debug("\u5F00\u59CB\u88C5\u8F7D\u5B9E\u4F53\u57FA\u672C\u5B9A\u4E49");
        List entities = (new EntityBaseInfoLoader()).loadAllEntityBasicInfo(globalConfig, startMode, filter);
        LOGGER.debug((new StringBuilder()).append("\u5B9E\u4F53\u57FA\u672C\u5B9A\u4E49\u88C5\u8F7D\u5B8C\u6BD5\uFF0C\u83B7\u5F97[").append(entities.size()).append("]\u4E2A\u5B9E\u4F53\u5B9A\u4E49").toString());
        LOGGER.debug("\u5F00\u59CB\u9488\u5BF9\u6BCF\u4E2A\u5B9E\u4F53\u88C5\u8F7D\u5C5E\u6027");
        List relaProperties = new ArrayList();
        EntityImpl entity;
        for(Iterator iter = entities.iterator(); iter.hasNext(); LOGGER.debug((new StringBuilder()).append("\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684\u5C5E\u6027\u88C5\u8F7D\u5B8C\u6BD5").toString()))
        {
            entity = (EntityImpl)iter.next();
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u88C5\u8F7D\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684\u5C5E\u6027").toString());
            IEntityCatalog catalog = (IEntityCatalog)entityCatalogMap.get(entity.getEntityCatalogKey());
            if(catalog == null)
                throw new MetaDBConfigException("0014", new Object[] {
                    entity.getEntityName(), entity.getEntityCatalogKey()
                });
            entity.setEntityCatalog(catalog);
            String tableName = entity.getTableName();
            if(tableName == null || tableName.trim().length() == 0)
            {
                tableName = (new StringBuilder()).append(catalog.getCatalogPrefix().toUpperCase()).append(entity.getEntityName().toUpperCase()).toString();
                entity.setTableName(tableName);
            } else
            {
                entity.setTableName(tableName.trim());
            }
            List props = (new PropertyInfoLoader()).loadProperties(entity, globalConfig.getDataSourceProvider());
            PropertyImpl prop;
            for(Iterator propIter = props.iterator(); propIter.hasNext(); entity.addProperty(prop))
            {
                prop = (PropertyImpl)propIter.next();
                if(prop.getPropertyType().equals(PropertyType.ENTITY))
                {
                    relaProperties.add(prop);
                    entity.setHasRelaProperty(true);
                }
                if(prop.getPropertyType().equals(PropertyType.STREAM))
                    entity.setHasStreamProperty(true);
            }

            LOGGER.debug((new StringBuilder()).append("\u88C5\u8F7D\u5230\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684[").append(props.size()).append("]\u4E2A\u81EA\u5B9A\u4E49\u5C5E\u6027").toString());
            entity.addProperty(PropertyImpl.createIdProperty(entity));
            entity.addProperty(PropertyImpl.createCreateTimeStampProperty(entity));
            entity.addProperty(PropertyImpl.createEntityNameProperty(entity));
            entity.addProperty(PropertyImpl.createUpdateTimeStampProperty(entity));
            entity.addProperty(PropertyImpl.createVersionProperty(entity));
            if(entity.isHistorySupport())
                entity.addProperty(PropertyImpl.createLastIdProperty(entity));
            entityMap.put(entity.getEntityName(), entity);
        }

        LOGGER.debug("\u6240\u6709\u5B9E\u4F53\u5C5E\u6027\u88C5\u8F7D\u5B8C\u6BD5");
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u5904\u7406\u6240\u6709\u5B9E\u4F53\u95F4\u7684\u5173\u8054\u5C5E\u6027\uFF0C\u5171[").append(relaProperties.size()).append("]\u4E2A\u5173\u8054\u5C5E\u6027").toString());
        PropertyImpl prop;
        EntityImpl entity;
        for(Iterator iter = relaProperties.iterator(); iter.hasNext(); entity.registRelaEntity(prop))
        {
            prop = (PropertyImpl)iter.next();
            entity = (EntityImpl)entityMap.get(prop.getRelaEntityName());
            if(entity == null)
                throw new MetaDBConfigException("0025", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName(), prop.getRelaEntityName()
                });
            if(prop.getRelaPropertyName().length() == 0)
            {
                prop.setRelaProperty(entity.getProperty("Id"));
                prop.setPropertyDBType(PropertyDBType.INTEGER);
                continue;
            }
            IProperty relaProp;
            try
            {
                relaProp = entity.getProperty(prop.getRelaPropertyName());
            }
            catch(Exception e)
            {
                throw new MetaDBConfigException("0029", e, new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName(), prop.getRelaEntityName(), prop.getRelaPropertyName()
                });
            }
            if(!relaProp.isUnique())
                throw new MetaDBConfigException("0030", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName(), prop.getRelaEntityName(), prop.getRelaPropertyName()
                });
            prop.setRelaProperty(relaProp);
            prop.setPropertyDBType(relaProp.getPropertyDBType());
            prop.setPropertyLength(relaProp.getPropertyLength());
        }

        LOGGER.debug("\u6240\u6709\u5B9E\u4F53\u95F4\u7684\u5173\u8054\u5C5E\u6027\u5904\u7406\u5B8C\u6BD5");
        LOGGER.debug("\u5F00\u59CB\u542F\u52A8Hibernate\u6846\u67B6");
        sessionFactory = SessionFactoryBuilder.buildSessionFactory(globalConfig, new ArrayList(entityMap.values()));
        LOGGER.debug("\u542F\u52A8Hibernate\u6846\u67B6\u5B8C\u6210");
        if("true".equalsIgnoreCase(globalConfig.getMetaDBProperties().getProperty("metadb.autocreatetable")))
            MetaDBTableCreator.getInstance().createTable(globalConfig.getDataSourceProvider().getDataSource(), new ArrayList(entityMap.values()));
        startFlag = true;
        LOGGER.debug("MetaDB\u6838\u5FC3\u521D\u59CB\u5316\u5B8C\u6BD5");
    }

    public IEntity getEntity(String entityName)
        throws MetaDBException
    {
        checkStatus();
        lock();
        IEntity entity = (IEntity)entityMap.get(entityName.toUpperCase());
        unlock();
        if(entity == null)
            throw new MetaDBCoreException("0003", new Object[] {
                entityName
            });
        else
            return entity;
    }

    public boolean isEntityExists(String entityName)
    {
        return entityMap.containsKey(entityName.toUpperCase());
    }

    public void reloadConfig(GlobalConfig config)
        throws MetaDBException
    {
        lock();
        LOGGER.debug("\u5F00\u59CB\u91CD\u65B0\u521D\u59CB\u5316MetaDB\u6838\u5FC3");
        startFlag = false;
        entityCatalogMap = new HashMap();
        entityMap = new HashMap();
        sessionFactory = null;
        initData(config);
        LOGGER.debug("\u91CD\u65B0\u521D\u59CB\u5316MetaDB\u6838\u5FC3\u5B8C\u6210");
        unlock();
    }

    public SessionFactory getSessionFactory()
        throws MetaDBException
    {
        checkStatus();
        return sessionFactory;
    }

    public DataSource getDataSource()
        throws MetaDBException
    {
        checkStatus();
        return dataSource;
    }

    private synchronized void lock()
    {
        while(lock.booleanValue()) 
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                LOGGER.warn("\u540C\u6B65MetaDB\u5B9E\u4F53\u5B9A\u4E49\u9501\u65F6\u88AB\u4E2D\u65AD", e);
            }
        lock = Boolean.TRUE;
    }

    private synchronized void unlock()
    {
        lock = Boolean.FALSE;
        notifyAll();
    }

    private void checkStatus()
    {
        if(!startFlag)
            throw new MetaDBCoreException("0004");
        else
            return;
    }

    private Map entityCatalogMap;
    private Map entityMap;
    private Boolean lock;
    private static final Logger LOGGER;
    private boolean startFlag;
    private SessionFactory sessionFactory;
    private DataSource dataSource;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
