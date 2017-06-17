// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SessionFactoryBuilder.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.GlobalConfig;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import com.sinitek.base.metadb.config.impl.ConnectionProviderImpl;
import com.sinitek.base.metadb.config.impl.PropertyImpl;
import com.sinitek.base.metadb.hibernate.usertype.BlobStreamType;
import com.sinitek.base.metadb.hibernate.usertype.BooleanType;
import com.sinitek.base.metadb.hibernate.usertype.BytesType;
import com.sinitek.base.metadb.hibernate.usertype.ClassType;
import com.sinitek.base.metadb.hibernate.usertype.ClobStreamType;
import com.sinitek.base.metadb.hibernate.usertype.ClobType;
import com.sinitek.base.metadb.hibernate.usertype.EntityProxyType;
import com.sinitek.base.metadb.hibernate.usertype.EnumType;
import com.sinitek.base.metadb.hibernate.usertype.FileStreamType;
import com.sinitek.base.metadb.hibernate.usertype.LastIdType;
import java.util.*;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;
import org.hibernate.mapping.*;

// Referenced classes of package com.sinitek.base.metadb.hibernate:
//            MetaObjectPersistentClass, MetaObjectTuplizer

public class SessionFactoryBuilder
{

    public SessionFactoryBuilder()
    {
    }

    public static SessionFactory buildSessionFactory(GlobalConfig globalConfig, List entitys)
        throws MetaDBException
    {
        Configuration config = new Configuration();
        Properties configProp = new Properties();
        ConnectionProviderImpl.dsProvider = globalConfig.getDataSourceProvider();
        configProp.setProperty("hibernate.dialect", "com.sinitek.base.metadb.hibernate.XNOracle9iDialect");
        configProp.setProperty("hibernate.connection.provider_class", com/sinitek/base/metadb/config/impl/ConnectionProviderImpl.getName());
        configProp.setProperty("hibernate.default_entity_mode", "dynamic-map");
        config.setNamingStrategy(new DefaultNamingStrategy());
        configProp.putAll(globalConfig.getHibernateProperties());
        config = config.addProperties(configProp);
        Mappings mappings = config.createMappings();
        Iterator iter = entitys.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            IEntity _entity = (IEntity)iter.next();
            mappings.addClass(buildClass(_entity, globalConfig, 0));
            if(_entity.isHistorySupport())
                mappings.addClass(buildClass(_entity, globalConfig, 1));
            if(_entity.isRemoveSupport())
                mappings.addClass(buildClass(_entity, globalConfig, 2));
        } while(true);
        return config.buildSessionFactory();
    }

    private static PersistentClass buildClass(IEntity entity, GlobalConfig config, int entityFlag)
        throws MetaDBException
    {
        MetaObjectPersistentClass cls = new MetaObjectPersistentClass();
        Table table = new Table();
        if(entityFlag == 0)
        {
            cls.setEntityName(entity.getEntityName());
            table.setName(entity.getTableName());
        } else
        if(entityFlag == 1)
        {
            cls.setEntityName((new StringBuilder()).append(entity.getEntityName()).append("_his").toString());
            table.setName((new StringBuilder()).append(entity.getTableName()).append("_HIS").toString());
            PropertyImpl prop = new PropertyImpl();
            prop.setEntity(entity);
            prop.setNotNull(false);
            prop.setPropertyColumnName("LASTOBJID");
            prop.setPropertyName("LastId");
            prop.setPropertyType(PropertyType.LASTID);
            prop.setPropertyDBType(PropertyDBType.INTEGER);
            createProperty(prop, table, cls, config);
        } else
        if(entityFlag == 2)
        {
            cls.setEntityName((new StringBuilder()).append(entity.getEntityName()).append("_bak").toString());
            table.setName((new StringBuilder()).append(entity.getTableName()).append("_BAK").toString());
            PropertyImpl prop = new PropertyImpl();
            prop.setEntity(entity);
            prop.setNotNull(false);
            prop.setPropertyColumnName("ORIGOBJID");
            prop.setPropertyName("OrigObjId");
            prop.setPropertyType(PropertyType.INTEGER);
            prop.setPropertyDBType(PropertyDBType.INTEGER);
            createProperty(prop, table, cls, config);
            prop = new PropertyImpl();
            prop.setEntity(entity);
            prop.setNotNull(false);
            prop.setPropertyColumnName("BAKTIME");
            prop.setPropertyName("BakTime");
            prop.setPropertyType(PropertyType.DATE);
            prop.setPropertyDBType(PropertyDBType.DATE);
            createProperty(prop, table, cls, config);
        }
        cls.setTable(table);
        cls.setEntity(entity);
        cls.setDynamicUpdate(true);
        cls.setDynamicInsert(false);
        cls.setLazy(false);
        cls.setMutable(true);
        cls.setSelectBeforeUpdate(false);
        cls.addTuplizer(EntityMode.MAP, com/sinitek/base/metadb/hibernate/MetaObjectTuplizer.getName());
        IProperty idProp = entity.getProperty("Id");
        PrimaryKey pk = new PrimaryKey();
        Property pkProp = createProperty(idProp, table, cls, config);
        Column column = (Column)pkProp.getColumnIterator().next();
        pk.addColumn(column);
        pk.setTable(table);
        pk.setName(idProp.getPropertyName());
        table.setPrimaryKey(pk);
        cls.setIdentifier((KeyValue)column.getValue());
        cls.setIdentifierProperty(pkProp);
        createProperty(entity.getProperty("CreateTimeStamp"), table, cls, config);
        createProperty(entity.getProperty("UpdateTimeStamp"), table, cls, config);
        createProperty(entity.getProperty("Version"), table, cls, config);
        createProperty(entity.getProperty("EntityName"), table, cls, config);
        if(entity.isHistorySupport() && entityFlag == 0)
            createProperty(entity.getProperty("LastId"), table, cls, config);
        List sysProps = Arrays.asList(IProperty.SYSTEM_PROPERTY_NAMES);
        Iterator iter = entity.getProperties().iterator();
        do
        {
            if(!iter.hasNext())
                break;
            IProperty prop = (IProperty)iter.next();
            if(!sysProps.contains(prop.getPropertyName()))
                createProperty(prop, table, cls, config);
        } while(true);
        return cls;
    }

    private static Column createColumn(IProperty prop, Table table, GlobalConfig config)
    {
        Column column = new Column();
        column.setName(prop.getPropertyColumnName());
        column.setNullable(table.getName().endsWith("_HIS") || table.getName().endsWith("_BAK") || !prop.isNotNull());
        column.setUnique(!table.getName().endsWith("_HIS") && !table.getName().endsWith("_BAK") && prop.isUnique());
        SimpleValue value = new SimpleValue();
        value.addColumn(column);
        value.setTable(table);
        PropertyType pt = prop.getPropertyType();
        value.setIdentifierGeneratorStrategy("assigned");
        if(pt.equals(PropertyType.ID))
        {
            value.setTypeName(java/lang/Integer.getName());
            value.setIdentifierGeneratorProperties(new Properties());
            value.setIdentifierGeneratorStrategy("com.sinitek.base.metadb.hibernate.IdGeneratorWrapper");
        } else
        if(pt.equals(PropertyType.CREATETIMESTAMP))
            value.setTypeName(java/util/Date.getName());
        else
        if(pt.equals(PropertyType.UPDATETIMESTAMP))
            value.setTypeName(java/util/Date.getName());
        else
        if(pt.equals(PropertyType.ENTITYNAME))
            value.setTypeName(java/lang/String.getName());
        else
        if(pt.equals(PropertyType.VERSION))
            value.setTypeName(java/lang/Integer.getName());
        else
        if(pt.equals(PropertyType.LASTID))
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/LastIdType.getName());
        else
        if(pt.equals(PropertyType.BOOLEAN))
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/BooleanType.getName());
        else
        if(pt.equals(PropertyType.BYTES))
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/BytesType.getName());
        else
        if(pt.equals(PropertyType.CLASS))
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/ClassType.getName());
        else
        if(pt.equals(PropertyType.CLOB))
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/ClobType.getName());
        else
        if(pt.equals(PropertyType.CREATETIMESTAMP))
            value.setTypeName(java/util/Date.getName());
        else
        if(pt.equals(PropertyType.DATE))
            value.setTypeName(java/util/Date.getName());
        else
        if(pt.equals(PropertyType.DOUBLE))
            value.setTypeName(java/lang/Double.getName());
        else
        if(pt.equals(PropertyType.ENTITY))
        {
            Properties typeProp = new Properties(config.getMetaDBProperties());
            typeProp.setProperty("TARGETPROPERTYNAME", prop.getRelaProperty().getPropertyName());
            typeProp.setProperty("TARGETPROPERTYDBTYPE", prop.getRelaProperty().getPropertyDBType().getEnumItemName());
            typeProp.setProperty("TARGETENTITYNAME", prop.getRelaProperty().getEntity().getEntityName());
            typeProp.setProperty("PROPERTYNAME", prop.getPropertyName());
            Class interfaceClz = prop.getRelaProperty().getEntity().getInterface();
            typeProp.setProperty("TARGETENTITYINTERFACE", interfaceClz != null ? interfaceClz.getName() : com/sinitek/base/metadb/IMetaObjectImpl.getName());
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/EntityProxyType.getName());
            value.setTypeParameters(typeProp);
        } else
        if(pt.equals(PropertyType.ENUM))
        {
            Properties typeProp = new Properties(config.getMetaDBProperties());
            typeProp.setProperty("ENUMNAME", prop.getEnumName());
            typeProp.setProperty("DBTYPE", prop.getPropertyDBType().getEnumItemName());
            value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/EnumType.getName());
            value.setTypeParameters(typeProp);
        } else
        if(pt.equals(PropertyType.FLOAT))
            value.setTypeName(java/lang/Float.getName());
        else
        if(pt.equals(PropertyType.INTEGER))
            value.setTypeName(java/lang/Integer.getName());
        else
        if(pt.equals(PropertyType.STREAM))
        {
            Properties typeProp = new Properties(config.getMetaDBProperties());
            typeProp.setProperty("ENTITYNAME", prop.getEntity().getEntityName());
            if(prop.getPropertyDBType().equals(PropertyDBType.BLOB))
                value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/BlobStreamType.getName());
            else
            if(prop.getPropertyDBType().equals(PropertyDBType.FILE))
                value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/FileStreamType.getName());
            else
                value.setTypeName(com/sinitek/base/metadb/hibernate/usertype/ClobStreamType.getName());
            value.setTypeParameters(typeProp);
        } else
        if(pt.equals(PropertyType.STRING))
            value.setTypeName(java/lang/String.getName());
        else
        if(pt.equals(PropertyType.UID))
            value.setTypeName(java/lang/String.getName());
        else
            throw new MetaDBConfigException("0033", new Object[] {
                prop.getEntity().getEntityName(), prop.getPropertyName(), prop.getPropertyType().toString()
            });
        column.setValue(value);
        return column;
    }

    private static Property createProperty(IProperty prop, Table table, RootClass clz, GlobalConfig config)
    {
        Property ret = new Property();
        ret.setName(prop.getPropertyName());
        ret.setSelectable(true);
        if(!prop.getPropertyType().equals(PropertyType.ID))
        {
            ret.setUpdateable(true);
            ret.setInsertable(true);
        } else
        {
            ret.setUpdateable(false);
            ret.setInsertable(false);
        }
        ret.setOptional(!prop.isNotNull());
        Column column = createColumn(prop, table, config);
        ret.setValue(column.getValue());
        table.addColumn(column);
        clz.addProperty(ret);
        if(prop.getPropertyType().equals(PropertyType.VERSION))
        {
            clz.setVersion(ret);
            ret.setOptimisticLocked(true);
            ret.setGeneration(PropertyGeneration.NEVER);
        } else
        {
            ret.setOptimisticLocked(false);
        }
        return ret;
    }
}
