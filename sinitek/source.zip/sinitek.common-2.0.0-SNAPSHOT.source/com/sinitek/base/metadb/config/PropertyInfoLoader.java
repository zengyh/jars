// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyInfoLoader.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.impl.PropertyImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package com.sinitek.base.metadb.config:
//            MetaDBConfigException, IDataSourceProvider

public class PropertyInfoLoader
{

    public PropertyInfoLoader()
    {
    }

    public List loadProperties(IEntity entity, IDataSourceProvider dataSourceProvider, boolean isCheck)
        throws MetaDBException
    {
        List sysPropNames = Arrays.asList(IProperty.SYSTEM_PROPERTY_NAMES);
        try
        {
            String sql = "select * from METADB_PROPERTY where upper(ENTITYNAME)=?";
            JdbcTemplate template = new JdbcTemplate(dataSourceProvider.getDataSource());
            List temp = template.queryForList(sql, new Object[] {
                entity.getEntityName()
            });
            List ret = new ArrayList(temp.size());
            Map row;
            for(Iterator iter = temp.iterator(); iter.hasNext(); ret.add(createProperty(entity, sysPropNames, row, isCheck)))
                row = (Map)iter.next();

            return ret;
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBConfigException("0015", e, new Object[] {
                entity.getEntityName()
            });
        }
    }

    public List loadProperties(IEntity entity, IDataSourceProvider dataSourceProvider)
        throws MetaDBException
    {
        return loadProperties(entity, dataSourceProvider, true);
    }

    public static PropertyImpl createProperty(IEntity entity, List sysPropNames, Map row, boolean isCheck)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        String propName = row.get("PROPERTYNAME").toString().trim();
        checkPropertyName(sysPropNames, entity.getEntityName(), propName);
        prop.setPropertyName(propName);
        prop.setPropertyInfo(row.get("PROPERTYINFO").toString().trim());
        String columnName = (String)row.get("PROPERTYCOLUMNNAME");
        columnName = columnName != null ? columnName.trim() : "";
        if(columnName.length() == 0)
            columnName = propName.toUpperCase();
        prop.setPropertyColumnName(columnName);
        String szPropType = row.get("PROPERTYTYPE").toString().trim().toUpperCase();
        PropertyType propType = (PropertyType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyType.getName(), szPropType);
        prop.setPropertyType(propType);
        prop.setNotNull("1".equalsIgnoreCase((String)row.get("PROPERTYISNOTNULL")));
        prop.setPropertyLength(0);
        prop.setUnique("1".equals(row.get("PROPERTYISUNIQUE")));
        String defaultValue = (String)row.get("PROPERTYDEFAULTVALUE");
        Number propLength = (Number)row.get("PROPERTYLENGTH");
        dealPropertyType(prop, defaultValue, propLength, propType, row, isCheck);
        return prop;
    }

    private static void dealPropertyType(PropertyImpl prop, String defaultValue, Number propLength, PropertyType type, Map row, boolean isCheck)
        throws MetaDBException
    {
        if(type.equals(PropertyType.STRING))
        {
            prop.setDefaultValue(defaultValue);
            if(propLength == null)
                throw new MetaDBConfigException("0016", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName()
                });
            prop.setPropertyLength(propLength.intValue());
            String dbType = (String)row.get("PROPERTYDBTYPE");
            dbType = dbType != null ? dbType.trim().toUpperCase() : "";
            if(dbType.length() == 0)
            {
                prop.setPropertyDBType(PropertyDBType.VARCHAR);
            } else
            {
                PropertyDBType _dbType;
                try
                {
                    _dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), dbType);
                }
                catch(Exception e)
                {
                    throw new MetaDBConfigException("0027", e, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                    });
                }
                if(!_dbType.equals(PropertyDBType.VARCHAR) && !_dbType.equals(PropertyDBType.CHAR))
                    throw new MetaDBConfigException("0027", new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                    });
                prop.setPropertyDBType(_dbType);
            }
        } else
        if(type.equals(PropertyType.BOOLEAN))
        {
            if(StringUtils.isNotBlank(defaultValue))
                prop.setDefaultValue(Boolean.valueOf("1".equals(defaultValue)));
            prop.setPropertyDBType(PropertyDBType.CHAR);
            prop.setPropertyLength(1);
        } else
        if(type.equals(PropertyType.BYTES))
        {
            if(defaultValue != null)
                prop.setDefaultValue(defaultValue.getBytes());
            prop.setPropertyDBType(PropertyDBType.BLOB);
        } else
        if(type.equals(PropertyType.CLASS))
            prop.setPropertyDBType(PropertyDBType.BLOB);
        else
        if(type.equals(PropertyType.CLOB))
            prop.setPropertyDBType(PropertyDBType.CLOB);
        else
        if(type.equals(PropertyType.DOUBLE))
        {
            if(defaultValue != null && defaultValue.trim().length() > 0)
                try
                {
                    prop.setDefaultValue(Double.valueOf(defaultValue.trim()));
                }
                catch(NumberFormatException e)
                {
                    throw new MetaDBConfigException("0017", e, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                    });
                }
            prop.setPropertyDBType(PropertyDBType.DOUBLE);
        } else
        if(type.equals(PropertyType.ENTITY))
        {
            String relaEntityName = (String)row.get("RELAENTITYNAME");
            relaEntityName = relaEntityName != null ? relaEntityName.trim().toUpperCase() : "";
            if(relaEntityName.length() == 0)
                throw new MetaDBConfigException("0023", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName()
                });
            String relaPropName = (String)row.get("RELAPROPERTYNAME");
            relaPropName = relaPropName != null ? relaPropName.trim() : "";
            prop.setRelaEntityName(relaEntityName);
            prop.setRelaPropertyName(relaPropName);
        } else
        if(type.equals(PropertyType.ENUM))
        {
            String dbType = (String)row.get("PROPERTYDBTYPE");
            dbType = dbType != null ? dbType.trim().toUpperCase() : "";
            PropertyDBType _dbType = PropertyDBType.INTEGER;
            try
            {
                if(StringUtils.isNotEmpty(dbType))
                    _dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), dbType);
            }
            catch(Exception e)
            {
                throw new MetaDBConfigException("0049", e, new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                });
            }
            if(!_dbType.equals(PropertyDBType.INTEGER) && !_dbType.equals(PropertyDBType.CHAR))
                throw new MetaDBConfigException("0049", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                });
            prop.setPropertyDBType(_dbType);
            if(prop.getPropertyDBType().equals(PropertyDBType.CHAR))
            {
                if(propLength == null)
                    throw new MetaDBConfigException("0050", new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                    });
                prop.setPropertyLength(propLength.intValue());
            }
            String enumName = (String)row.get("ENUMNAME");
            enumName = enumName != null ? enumName.trim() : "";
            if(enumName.length() == 0)
                throw new MetaDBConfigException("0020", new Object[] {
                    prop.getEntity().getEntityName(), prop.getPropertyName()
                });
            if(isCheck)
                try
                {
                    EnumItemContext.getInstance().getEnumItems(enumName);
                }
                catch(Exception ex)
                {
                    throw new MetaDBConfigException("0021", ex, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), enumName
                    });
                }
            prop.setEnumName(enumName);
        } else
        if(type.equals(PropertyType.FLOAT))
        {
            prop.setPropertyDBType(PropertyDBType.FLOAT);
            if(StringUtils.isNotBlank(defaultValue))
                try
                {
                    prop.setDefaultValue(Float.valueOf(defaultValue));
                }
                catch(NumberFormatException e)
                {
                    throw new MetaDBConfigException("0018", e, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                    });
                }
        } else
        if(type.equals(PropertyType.INTEGER))
        {
            prop.setPropertyDBType(PropertyDBType.INTEGER);
            if(defaultValue != null && defaultValue.trim().length() > 0)
                try
                {
                    prop.setDefaultValue(Integer.valueOf(defaultValue.trim()));
                }
                catch(NumberFormatException e)
                {
                    throw new MetaDBConfigException("0019", e, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                    });
                }
        } else
        if(type.equals(PropertyType.STREAM))
        {
            String dbType = (String)row.get("PROPERTYDBTYPE");
            dbType = dbType != null ? dbType.trim().toUpperCase() : "";
            if(dbType.length() == 0)
            {
                prop.setPropertyDBType(PropertyDBType.BLOB);
            } else
            {
                PropertyDBType _dbType;
                try
                {
                    _dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), dbType);
                }
                catch(Exception e)
                {
                    throw new MetaDBConfigException("0022", e, new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                    });
                }
                if(!_dbType.equals(PropertyDBType.BLOB) && !_dbType.equals(PropertyDBType.CLOB) && !_dbType.equals(PropertyDBType.FILE))
                    throw new MetaDBConfigException("0022", new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), dbType
                    });
                prop.setPropertyDBType(_dbType);
            }
        } else
        if(type.equals(PropertyType.UID))
        {
            prop.setDefaultValue(defaultValue);
            prop.setPropertyDBType(PropertyDBType.VARCHAR);
            prop.setPropertyLength(120);
        } else
        if(type.equals(PropertyType.DATE))
        {
            prop.setPropertyDBType(PropertyDBType.DATE);
            defaultValue = defaultValue != null ? defaultValue.trim() : "";
            if(defaultValue.length() > 0)
            {
                defaultValue = defaultValue.trim();
                String shortFmt = "yyyy-MM-dd";
                String longFmt = "yyyy-MM-dd HH:mm:ss";
                if(defaultValue.length() == shortFmt.length())
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(shortFmt);
                    try
                    {
                        prop.setDefaultValue(sdf.parse(defaultValue));
                    }
                    catch(ParseException e)
                    {
                        throw new MetaDBConfigException("0026", new Object[] {
                            prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                        });
                    }
                } else
                if(defaultValue.length() == longFmt.length())
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(longFmt);
                    try
                    {
                        prop.setDefaultValue(sdf.parse(defaultValue));
                    }
                    catch(ParseException e)
                    {
                        throw new MetaDBConfigException("0026", new Object[] {
                            prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                        });
                    }
                } else
                {
                    throw new MetaDBConfigException("0026", new Object[] {
                        prop.getEntity().getEntityName(), prop.getPropertyName(), defaultValue
                    });
                }
            }
        }
    }

    private static void checkPropertyName(List sysPropNames, String entityName, String propName)
    {
        if(sysPropNames.contains(propName))
            throw new MetaDBConfigException("0028", new Object[] {
                entityName, propName
            });
        else
            return;
    }
}
