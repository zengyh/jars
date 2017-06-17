// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBTableCreator.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

// Referenced classes of package com.sinitek.base.metadb.config.impl:
//            PropertyImpl, TableColsBean

public class MetaDBTableCreator
{
    private class TableColsRowMapper
        implements RowMapper
    {

        public Object mapRow(ResultSet rs, int index)
            throws SQLException
        {
            TableColsBean bean = new TableColsBean();
            bean.setColName(rs.getString("COLUMN_NAME"));
            bean.setDataLength(rs.getInt("DATA_LENGTH"));
            bean.setDataPrecision(rs.getInt("DATA_PRECISION"));
            bean.setDataScale(rs.getInt("DATA_SCALE"));
            bean.setDataType(rs.getString("DATA_TYPE"));
            bean.setNullable(rs.getString("NULLABLE").equalsIgnoreCase("N"));
            return bean;
        }

        final MetaDBTableCreator this$0;

        private TableColsRowMapper()
        {
            this$0 = MetaDBTableCreator.this;
            super();
        }

    }


    private MetaDBTableCreator()
    {
    }

    public static MetaDBTableCreator getInstance()
    {
        if(instance == null)
            instance = new MetaDBTableCreator();
        return instance;
    }

    public void createTable(DataSource ds, List entities)
    {
        Iterator iter = entities.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            IEntity entity = (IEntity)iter.next();
            createTable(ds, entity, 0);
            if(entity.isHistorySupport())
                createTable(ds, entity, 1);
            if(entity.isRemoveSupport())
                createTable(ds, entity, 2);
        } while(true);
    }

    public void createTable(DataSource ds, IEntity entity, int entityFlag)
    {
        String tableName = entity.getTableName();
        List uniqueProps = new ArrayList();
        List propertyies = entity.getProperties();
        if(entityFlag == 1)
            tableName = (new StringBuilder()).append(tableName).append("_HIS").toString();
        else
        if(entityFlag == 2)
            tableName = (new StringBuilder()).append(tableName).append("_BAK").toString();
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u5904\u7406\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u8868\u540D\u4E3A[").append(tableName).append("]").toString());
        if(tableExist(tableName, ds))
        {
            LOGGER.debug((new StringBuilder()).append("\u8868[").append(tableName).append("]\u5DF2\u7ECF\u5B58\u5728").toString());
            checkProperty(propertyies, tableName, ds, entityFlag);
            return;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE ");
        sql.append(tableName);
        sql.append("\n(\n");
        dealProperty(entity.getProperty("Id"), sql, entityFlag);
        List sysProps = Arrays.asList(IProperty.SYSTEM_PROPERTY_NAMES);
        Iterator iter = propertyies.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            IProperty prop = (IProperty)iter.next();
            String _propName = prop.getPropertyName();
            if(!sysProps.contains(_propName))
            {
                dealProperty(prop, sql, entityFlag);
                if(prop.isUnique() && entityFlag == 0)
                    uniqueProps.add(prop.getPropertyColumnName());
            }
        } while(true);
        dealProperty(entity.getProperty("CreateTimeStamp"), sql, entityFlag);
        dealProperty(entity.getProperty("UpdateTimeStamp"), sql, entityFlag);
        dealProperty(entity.getProperty("Version"), sql, entityFlag);
        dealProperty(entity.getProperty("EntityName"), sql, entityFlag);
        if(entityFlag == 2)
        {
            PropertyImpl prop = new PropertyImpl();
            prop.setEntity(entity);
            prop.setNotNull(false);
            prop.setPropertyColumnName("ORIGOBJID");
            prop.setPropertyName("OrigObjId");
            prop.setPropertyType(PropertyType.INTEGER);
            prop.setPropertyDBType(PropertyDBType.INTEGER);
            dealProperty(prop, sql, entityFlag);
            prop = new PropertyImpl();
            prop.setEntity(entity);
            prop.setNotNull(false);
            prop.setPropertyColumnName("BAKTIME");
            prop.setPropertyName("BakTime");
            prop.setPropertyType(PropertyType.DATE);
            prop.setPropertyDBType(PropertyDBType.DATE);
            dealProperty(prop, sql, entityFlag);
        }
        if(entity.isHistorySupport())
            dealProperty(entity.getProperty("LastId"), sql, entityFlag);
        sql.delete(sql.length() - 2, sql.length() - 1);
        sql.append(")");
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u5EFA\u8868:[").append(sql.toString()).append("]").toString());
        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.update(sql.toString());
        try
        {
            createComments(tableName, entity, ds);
        }
        catch(Exception e)
        {
            LOGGER.warn("\u521B\u5EFA\u8868\u4E0E\u5B57\u6BB5\u7684\u5907\u6CE8\u5931\u8D25", e);
        }
        LOGGER.debug("\u5F00\u59CB\u521B\u5EFA\u4E3B\u952E");
        createPrimeryKey(tableName, "objid", ds);
        LOGGER.debug("\u5F00\u59CB\u5BF9\u552F\u4E00\u5B57\u6BB5\u521B\u5EFA\u552F\u4E00\u7EA6\u675F");
        createUnique(tableName, uniqueProps, ds);
    }

    private void dealProperty(IProperty property, StringBuffer buffer, int entityFlag)
    {
        String propName = property.getPropertyName();
        LOGGER.debug((new StringBuilder()).append("\u8BFB\u5165\u5C5E\u6027[").append(propName).append("]").toString());
        buffer.append("\t").append(property.getPropertyColumnName()).append("\t");
        if(property.getPropertyDBType().equals(PropertyDBType.VARCHAR))
            buffer.append("varchar2");
        else
        if(property.getPropertyDBType().equals(PropertyDBType.DATE))
            buffer.append("timestamp");
        else
        if(property.getPropertyDBType().equals(PropertyDBType.DOUBLE))
            buffer.append("number(32,8)");
        else
            buffer.append(property.getPropertyDBType().getEnumItemName());
        if(property.getPropertyDBType().equals(PropertyDBType.VARCHAR) || property.getPropertyDBType().equals(PropertyDBType.CHAR))
            buffer.append("(").append(property.getPropertyLength()).append(")");
        if(property.isNotNull() && entityFlag == 0)
            buffer.append("\t").append("not null");
        buffer.append(",\n");
    }

    private void createPrimeryKey(String tableName, String columnName, DataSource ds)
    {
        String pkName = shortName((new StringBuilder()).append("pk_").append(tableName).toString());
        String sql = (new StringBuilder()).append("ALTER TABLE ").append(tableName).append(" ADD (").append(" CONSTRAINT ").append(pkName).append(" PRIMARY KEY (").append(columnName).append("))").toString();
        LOGGER.debug(sql);
        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.execute(sql);
    }

    private String shortName(String pkName)
    {
        if(pkName.length() > 30)
            pkName = (new StringBuilder()).append(pkName.substring(0, 26)).append("_").append((new StringBuilder()).append("").append(Math.random() * 1000D).toString().substring(0, 3)).toString();
        return pkName;
    }

    private void createComments(String tableName, IEntity entity, DataSource ds)
    {
        JdbcTemplate jt = new JdbcTemplate(ds);
        if(StringUtils.isNotBlank(entity.getEntityInfo()))
        {
            String sql = (new StringBuilder()).append("comment on table ").append(tableName).append(" is '").append(entity.getEntityInfo()).append("'").toString();
            LOGGER.debug(sql);
            jt.update(sql);
        }
        Iterator i$ = entity.getProperties().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IProperty property = (IProperty)i$.next();
            if(StringUtils.isNotBlank(property.getPropertyInfo()))
            {
                String sql = (new StringBuilder()).append("comment on column ").append(tableName).append(".").append(property.getPropertyColumnName()).append(" is '").append(property.getPropertyInfo()).append("'").toString();
                LOGGER.debug(sql);
                jt.update(sql);
            }
        } while(true);
    }

    private void createUnique(String tableName, List columns, DataSource ds)
    {
        Object column;
        for(Iterator i$ = columns.iterator(); i$.hasNext(); createUnique(tableName, (String)column, ds))
            column = i$.next();

    }

    private void createUnique(String tableName, String columnName, DataSource ds)
    {
        String uqName = shortName((new StringBuilder()).append("uq_").append(tableName).append("_").append(columnName).toString());
        String sql = (new StringBuilder()).append("ALTER TABLE ").append(tableName).append(" ADD (").append(" CONSTRAINT ").append(uqName).append(" UNIQUE (").append(columnName).append("))").toString();
        LOGGER.debug(sql);
        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.execute(sql);
    }

    private boolean tableExist(String tableName, DataSource ds)
    {
        String sql = (new StringBuilder()).append("select count(*) as num from user_all_tables where table_name = '").append(tableName.toUpperCase()).append("'").toString();
        JdbcTemplate jt = new JdbcTemplate(ds);
        List list = jt.queryForList(sql);
        return ((Number)((Map)list.get(0)).get("NUM")).intValue() != 0;
    }

    private Map getTableCols(String tableName, DataSource ds)
    {
        String sql = (new StringBuilder()).append("select * from user_tab_cols where table_name='").append(tableName.toUpperCase()).append("'").toString();
        JdbcTemplate jt = new JdbcTemplate(ds);
        List list = jt.query(sql, new TableColsRowMapper());
        Map map = new HashMap();
        TableColsBean bean;
        for(Iterator iter = list.iterator(); iter.hasNext(); map.put(bean.getColName(), bean))
            bean = (TableColsBean)iter.next();

        return map;
    }

    private void checkProperty(List properties, String tableName, DataSource ds, int entityFlag)
    {
        Map map = getTableCols(tableName, ds);
        List sysProps = Arrays.asList(IProperty.SYSTEM_PROPERTY_NAMES);
        for(Iterator iter = properties.iterator(); iter.hasNext();)
        {
            IProperty prop = (IProperty)iter.next();
            String _propName = prop.getPropertyName();
            String _colName = prop.getPropertyColumnName();
            if(!sysProps.contains(_propName))
            {
                if(map.containsKey(_colName.toUpperCase()))
                {
                    String dbType = ((TableColsBean)map.get(_colName.toUpperCase())).getDataType();
                    if(!isDBTypeEquals(dbType, prop.getPropertyDBType()))
                        throw new MetaDBConfigException("0048", new Object[] {
                            tableName, _colName, dbType, prop.getPropertyDBType().getEnumItemName()
                        });
                    map.remove(_colName.toUpperCase());
                } else
                {
                    addColumn(tableName, prop, ds, entityFlag);
                }
            } else
            {
                map.remove(_colName.toUpperCase());
            }
        }

    }

    private void addColumn(String tableName, IProperty property, DataSource ds, int entityFlag)
    {
        StringBuilder sql = new StringBuilder();
        boolean notNullFlag = false;
        String propName = property.getPropertyName();
        LOGGER.debug((new StringBuilder()).append("\u65B0\u589E\u5B57\u6BB5[").append(property.getPropertyColumnName()).append("]").toString());
        if(entityFlag == 1 && propName.equalsIgnoreCase("LastId") || property.isUnique())
            notNullFlag = true;
        sql.append("ALTER TABLE ").append(tableName).append(" ADD ");
        sql.append(property.getPropertyColumnName()).append(" ");
        if(property.getPropertyDBType().equals(PropertyDBType.VARCHAR))
            sql.append("varchar2");
        else
        if(property.getPropertyDBType().equals(PropertyDBType.DATE))
            sql.append("timestamp");
        else
        if(property.getPropertyDBType().equals(PropertyDBType.DOUBLE))
            sql.append("number( 32, 8 )");
        else
            sql.append(property.getPropertyDBType().getEnumItemName());
        if(property.getPropertyDBType().equals(PropertyDBType.VARCHAR) || property.getPropertyDBType().equals(PropertyDBType.CHAR))
            sql.append("(").append(property.getPropertyLength()).append(")");
        if((property.isNotNull() || notNullFlag) && entityFlag == 0)
            sql.append("\t").append("not null");
        JdbcTemplate jt = new JdbcTemplate(ds);
        LOGGER.debug(sql.toString());
        jt.execute(sql.toString());
        if(property.isUnique() && entityFlag == 0)
            createUnique(tableName, property.getPropertyColumnName(), ds);
    }

    private boolean isDBTypeEquals(String dbType, PropertyDBType type)
    {
        if(type.equals(PropertyDBType.VARCHAR))
            return dbType.equalsIgnoreCase("VARCHAR2");
        if(type.equals(PropertyDBType.FLOAT) || type.equals(PropertyDBType.DOUBLE))
            return dbType.equalsIgnoreCase("FLOAT") || dbType.equalsIgnoreCase("NUMBER") || dbType.equalsIgnoreCase("DOUBLE");
        if(type.equals(PropertyDBType.INTEGER))
            return dbType.equalsIgnoreCase("INTEGER") || dbType.equalsIgnoreCase("NUMBER");
        if(type.equals(PropertyDBType.DATE))
        {
            return dbType.equalsIgnoreCase("DATE") || dbType.toUpperCase().startsWith("TIMESTAMP");
        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u8BFB\u53D6\u5230\u7C7B\u578B[").append(type.getEnumItemName()).append("],\u539F\u7C7B\u578B\u4E3A[").append(dbType).append("]").toString());
            return dbType.equalsIgnoreCase(type.getEnumItemName());
        }
    }

    private static final Logger LOGGER;
    private static MetaDBTableCreator instance = null;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
