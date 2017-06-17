// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectClassExporter.java

package com.sinitek.base.metadb.util;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.PropertyDBType;
import com.sinitek.base.metadb.PropertyType;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MetaObjectClassExporter
{
    private static class PropertyBean
    {

        public static List getProperties(String entityName, DataSource ds)
        {
            String sql = "SELECT propertyname,propertytype,propertyinfo,relaentityname,PROPERTYDBTYPE,RELAPROPERTYNAME FROM METADB_PROPERTY\nWHERE LOWER(entityname)=?";
            JdbcTemplate template = new JdbcTemplate(ds);
            RowMapper mapper = new RowMapper() {

                public Object mapRow(ResultSet resultSet, int i)
                    throws SQLException
                {
                    PropertyBean bean = new PropertyBean();
                    bean.propInfo = resultSet.getString("propertyinfo");
                    bean.propName = resultSet.getString("propertyname");
                    bean.propType = (PropertyType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyType.getName(), resultSet.getString("propertytype").toUpperCase());
                    bean.relaEntityName = resultSet.getString("relaentityname");
                    bean.propertyDBType = resultSet.getString("PROPERTYDBTYPE");
                    bean.relaPropName = resultSet.getString("RELAPROPERTYNAME");
                    return bean;
                }

            }
;
            return template.query(sql, new Object[] {
                entityName.toLowerCase().trim()
            }, mapper);
        }

        public String getPropInfo()
        {
            return propInfo;
        }

        public String getPropName()
        {
            return propName;
        }

        public PropertyType getPropType()
        {
            return propType;
        }

        public String getRelaEntityName()
        {
            return relaEntityName;
        }

        public String getPropertyDBType()
        {
            return propertyDBType;
        }

        public void setPropertyDBType(String propertyDBType)
        {
            this.propertyDBType = propertyDBType;
        }

        public String getRelaPropName()
        {
            return relaPropName;
        }

        public void setRelaPropName(String relaPropName)
        {
            this.relaPropName = relaPropName;
        }

        private String propName;
        private String propInfo;
        private PropertyType propType;
        private String relaEntityName;
        private String propertyDBType;
        private String relaPropName;







        private PropertyBean()
        {
        }

    }

    private static class EntityBean
    {

        public String getCatalogInfo()
        {
            return catalogInfo;
        }

        public String getClassName()
        {
            return className;
        }

        public String getEntityInfo()
        {
            return entityInfo;
        }

        public String getCatalogName()
        {
            return catalogName;
        }

        public String getEntityName()
        {
            return entityName;
        }

        public String getInterfaceName()
        {
            return interfaceName;
        }

        private String entityName;
        private String catalogInfo;
        private String entityInfo;
        private String className;
        private String catalogName;
        private String interfaceName;








        public EntityBean(String entityName, DataSource ds)
        {
            this.entityName = entityName;
            String sql = "SELECT entityname, entityinfo, catalogname, cataloginfo,interfacename, classname\nFROM METADB_ENTITY, METADB_ENTITYCATALOG\nWHERE LOWER(METADB_ENTITY.catalogkey)=LOWER(METADB_ENTITYCATALOG.catalogkey)\n\t  AND LOWER(METADB_ENTITY.entityname)=?";
            JdbcTemplate template = new JdbcTemplate(ds);
            final EntityBean ret = this;
            RowMapper mapper = new RowMapper() {

                public Object mapRow(ResultSet rs, int i)
                    throws SQLException
                {
                    catalogInfo = rs.getString("cataloginfo");
                    catalogName = rs.getString("catalogname");
                    interfaceName = rs.getString("interfacename");
                    className = rs.getString("classname");
                    if(StringUtils.isBlank(className))
                    {
                        int pos = interfaceName.lastIndexOf('.');
                        String _className = interfaceName;
                        String packageName = "";
                        if(pos > 0)
                        {
                            _className = interfaceName.substring(pos + 1);
                            packageName = interfaceName.substring(0, pos + 1);
                        }
                        if(_className.startsWith("I"))
                            _className = _className.substring(1);
                        _className = StringUtils.capitalize(_className);
                        _className = (new StringBuilder()).append(_className).append("Impl").toString();
                        _className = (new StringBuilder()).append(packageName).append(_className).toString();
                        className = _className;
                    }
                    entityInfo = rs.getString("entityinfo");
                    return ret;
                }

                final EntityBean val$ret;
                final EntityBean this$0;

                
                    throws SQLException
                {
                    this$0 = EntityBean.this;
                    ret = entitybean1;
                    super();
                }
            }
;
            template.query(sql, new Object[] {
                entityName.toLowerCase()
            }, mapper);
        }
    }


    public MetaObjectClassExporter()
    {
    }

    public static void exportInterface(String entityName, boolean exportSGMethod, OutputStream os, DataSource ds)
        throws IOException
    {
        exportInterface(entityName, exportSGMethod, os, ds, null);
    }

    public static void exportInterface(String entityName, boolean exportSGMethod, OutputStream os, DataSource ds, String encoding)
        throws IOException
    {
        String template = getTemplateContext("com/sinitek/base/metadb/util/interface.template");
        EntityBean bean = new EntityBean(entityName, ds);
        int pos = bean.getInterfaceName().lastIndexOf('.');
        String interfaceName = bean.getInterfaceName().substring(pos + 1);
        String packageName = bean.getInterfaceName().substring(0, pos);
        template = template.replaceAll("\\$\\{cataloginfo\\}", bean.getCatalogInfo());
        template = template.replaceAll("\\$\\{entityinfo\\}", bean.getEntityInfo());
        template = template.replaceAll("\\$\\{interfacename\\}", interfaceName);
        entityName = entityName.trim().toUpperCase();
        template = template.replaceAll("\\$\\{entityname\\}", entityName);
        template = template.replaceAll("\\$\\{date\\}", getCurretDate());
        template = template.replaceAll("\\$\\{packagename\\}", packageName);
        List otherImports = new ArrayList();
        if(exportSGMethod)
        {
            StringBuffer sb = new StringBuffer();
            Iterator iter = PropertyBean.getProperties(entityName, ds).iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                PropertyBean pBean = (PropertyBean)iter.next();
                String returnClass = getPropertyClassName(packageName, pBean, otherImports, ds);
                sb.append("    /**\n");
                sb.append("     * ").append("\u83B7\u5F97").append(pBean.getPropInfo()).append("\u5C5E\u6027\u503C\n");
                sb.append("     *\n");
                sb.append("     * @return ").append(pBean.getPropInfo()).append("\u5C5E\u6027\u503C\n");
                sb.append("     */\n");
                sb.append("    public ").append(returnClass).append(" get").append(StringUtils.capitalize(pBean.getPropName())).append("();\n\n");
                sb.append("    /**\n");
                sb.append("     * ").append("\u8BBE\u7F6E").append(pBean.getPropInfo()).append("\u5C5E\u6027\u503C\n");
                sb.append("     *\n");
                sb.append("     * @param value ").append(pBean.getPropInfo()).append("\u5C5E\u6027\u503C\n");
                sb.append("     */\n");
                sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("( ").append(returnClass).append(" value );\n\n");
                if(pBean.getPropType().equals(PropertyType.ENUM))
                {
                    PropertyDBType dbType = PropertyDBType.INTEGER;
                    if(StringUtils.isNotEmpty(pBean.getPropertyDBType()))
                        dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), pBean.getPropertyDBType());
                    sb.append("    /**\n");
                    sb.append("     * ").append("\u8BBE\u7F6E").append(pBean.getPropInfo()).append("\u679A\u4E3E\u5C5E\u6027\u503C\n");
                    sb.append("     *\n");
                    sb.append("     * @param value ").append(pBean.getPropInfo()).append("\u679A\u4E3E\u5C5E\u6027\u503C\uFF0C\u4F7F\u7528").append(dbType.equals(PropertyDBType.INTEGER) ? "\u679A\u4E3E\u9879\u503C" : "\u679A\u4E3E\u9879\u540D\u79F0").append("\n");
                    sb.append("     */\n");
                    sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("EnumValue( ").append(dbType.equals(PropertyDBType.INTEGER) ? "Integer" : "String").append(" value );\n\n");
                }
                if(pBean.getPropType().equals(PropertyType.ENTITY))
                {
                    sb.append("    /**\n");
                    sb.append("     * ").append("\u8BBE\u7F6E").append(pBean.getPropInfo()).append("\u5173\u8054\u5B9E\u4F53\u5C5E\u6027\u503C\n");
                    sb.append("     *\n");
                    sb.append("     * @param value ").append(pBean.getPropInfo()).append("\u5C5E\u6027\u5173\u8054\u503C\n");
                    sb.append("     */\n");
                    sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("EntityValue( Object value );\n\n");
                }
            } while(true);
            template = template.replaceAll("\\$\\{sg\\}", sb.toString());
        } else
        {
            template = template.replaceAll("\\$\\{sg\\}", "");
        }
        if(otherImports.size() > 0)
        {
            StringBuffer sb = new StringBuffer();
            for(Iterator iter = otherImports.iterator(); iter.hasNext(); sb.append(iter.next()))
                sb.append("\nimport ");

            template = template.replaceAll("\\$\\{otherimport\\}", sb.toString());
        } else
        {
            template = template.replaceAll("\\$\\{otherimport\\}", "");
        }
        if(StringUtils.isBlank(encoding))
            os.write(template.getBytes());
        else
            os.write(template.getBytes(encoding));
    }

    public static void exportImplClass(String entityName, boolean exportSGMethod, OutputStream os, DataSource ds)
        throws IOException
    {
        exportImplClass(entityName, exportSGMethod, os, ds, null);
    }

    public static void exportImplClass(String entityName, boolean exportSGMethod, OutputStream os, DataSource ds, String encoding)
        throws IOException
    {
        String template = getTemplateContext("com/sinitek/base/metadb/util/impl.template");
        EntityBean bean = new EntityBean(entityName, ds);
        int pos = bean.getInterfaceName().lastIndexOf('.');
        String interfaceName = bean.getInterfaceName().substring(pos + 1);
        String interfacePackage = bean.getInterfaceName().substring(0, pos);
        pos = bean.getClassName().lastIndexOf('.');
        String packageName = bean.getClassName().substring(0, pos);
        String implName = bean.getClassName().substring(pos + 1);
        template = template.replaceAll("\\$\\{cataloginfo\\}", bean.getCatalogInfo());
        template = template.replaceAll("\\$\\{entityinfo\\}", bean.getEntityInfo());
        template = template.replaceAll("\\$\\{interfacename\\}", interfaceName);
        entityName = entityName.trim().toUpperCase();
        template = template.replaceAll("\\$\\{entityname\\}", entityName);
        template = template.replaceAll("\\$\\{date\\}", getCurretDate());
        template = template.replaceAll("\\$\\{packagename\\}", packageName);
        template = template.replaceAll("\\$\\{implname\\}", implName);
        if(interfacePackage.equals(packageName))
            template = template.replaceAll("\\$\\{interfaceimport\\}", "");
        else
            template = template.replaceAll("\\$\\{interfaceimport\\}", (new StringBuilder()).append("import ").append(interfacePackage).append(".*;\n").toString());
        List otherImports = new ArrayList();
        if(exportSGMethod)
        {
            StringBuffer sb = new StringBuffer();
            Iterator iter = PropertyBean.getProperties(entityName, ds).iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                PropertyBean pBean = (PropertyBean)iter.next();
                String returnClass = getPropertyClassName(packageName, pBean, otherImports, ds);
                sb.append("    public ").append(returnClass).append(" get").append(StringUtils.capitalize(pBean.getPropName())).append("()\n");
                sb.append("    {\n");
                sb.append("        return ( ").append(returnClass).append(" ) get( \"").append(pBean.getPropName()).append("\" );\n");
                sb.append("    }\n");
                sb.append("\n\n");
                sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("( ").append(returnClass).append(" value )\n");
                sb.append("    {\n");
                sb.append("        put( \"").append(pBean.getPropName()).append("\", value );\n");
                sb.append("    }\n");
                sb.append("\n\n");
                if(pBean.getPropType().equals(PropertyType.ENUM))
                {
                    PropertyDBType dbType = PropertyDBType.INTEGER;
                    if(StringUtils.isNotEmpty(pBean.getPropertyDBType()))
                        dbType = (PropertyDBType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/PropertyDBType.getName(), pBean.getPropertyDBType());
                    sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("EnumValue( ").append(dbType.equals(PropertyDBType.INTEGER) ? "Integer" : "String").append(" value )\n");
                    sb.append("    {\n");
                    sb.append("        setEnumValue( \"").append(pBean.getPropName()).append("\", value );\n");
                    sb.append("    }\n");
                    sb.append("\n\n");
                }
                if(pBean.getPropType().equals(PropertyType.ENTITY))
                {
                    sb.append("    public void set").append(StringUtils.capitalize(pBean.getPropName())).append("EntityValue( Object value )\n");
                    sb.append("    {\n");
                    sb.append("        setEntityValue( \"").append(pBean.getPropName()).append("\", value );\n");
                    sb.append("    }\n");
                    sb.append("\n\n");
                }
            } while(true);
            template = template.replaceAll("\\$\\{sgimpl\\}", sb.toString());
        } else
        {
            template = template.replaceAll("\\$\\{sgimpl\\}", "");
        }
        if(otherImports.size() > 0)
        {
            StringBuffer sb = new StringBuffer();
            for(Iterator iter = otherImports.iterator(); iter.hasNext(); sb.append(iter.next()))
                sb.append("\nimport ");

            template = template.replaceAll("\\$\\{otherimport\\}", sb.toString());
        } else
        {
            template = template.replaceAll("\\$\\{otherimport\\}", "");
        }
        if(StringUtils.isBlank(encoding))
            os.write(template.getBytes());
        else
            os.write(template.getBytes(encoding));
    }

    private static String getTemplateContext(String templateFile)
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateFile);
        String s;
        try
        {
            byte buffer[] = new byte[512];
            int count = is.read(buffer);
            StringBuffer sb = new StringBuffer();
            for(; count > 0; count = is.read(buffer))
                sb.append(new String(buffer, 0, count));

            s = sb.toString();
        }
        catch(Exception ex)
        {
            throw new MetaDBConfigException("0047");
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        return s;
        Exception exception;
        exception;
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        throw exception;
    }

    private static String getPropertyClassName(String packageName, PropertyBean bean, List otherImports, DataSource ds)
    {
        if(bean.getPropType().equals(PropertyType.BOOLEAN))
            return "Boolean";
        if(bean.getPropType().equals(PropertyType.BYTES))
            return "byte[]";
        if(bean.getPropType().equals(PropertyType.CLASS))
        {
            String imports = "java.io.*;";
            if(!otherImports.contains(imports))
                otherImports.add(imports);
            return "Serializable";
        }
        if(bean.getPropType().equals(PropertyType.CLOB))
            return "String";
        if(bean.getPropType().equals(PropertyType.DATE))
        {
            String imports = "java.util.*;";
            if(!otherImports.contains(imports))
                otherImports.add(imports);
            return "Date";
        }
        if(bean.getPropType().equals(PropertyType.DOUBLE))
            return "Double";
        if(bean.getPropType().equals(PropertyType.ENTITY))
        {
            EntityBean entityBean = new EntityBean(bean.getRelaEntityName(), ds);
            if(StringUtils.isEmpty(entityBean.getInterfaceName()))
                return "IMetaObject";
            int pos = entityBean.getInterfaceName().lastIndexOf(".");
            String targetPackageName = entityBean.getInterfaceName().substring(0, pos);
            String targetInterfaceName = entityBean.getInterfaceName().substring(pos + 1);
            if(!packageName.equalsIgnoreCase(targetPackageName))
            {
                String imports = (new StringBuilder()).append(targetPackageName).append(".*;").toString();
                if(!otherImports.contains(imports))
                    otherImports.add(imports);
            }
            return targetInterfaceName;
        }
        if(bean.getPropType().equals(PropertyType.ENUM))
        {
            String imports = "com.sinitek.base.enumsupport.*;";
            if(!otherImports.contains(imports))
                otherImports.add(imports);
            return "IEnumItem";
        }
        if(bean.getPropType().equals(PropertyType.FLOAT))
            return "Float";
        if(bean.getPropType().equals(PropertyType.INTEGER))
            return "Integer";
        if(bean.getPropType().equals(PropertyType.STREAM))
            return "IStreamValue";
        if(bean.getPropType().equals(PropertyType.STRING))
            return "String";
        if(bean.getPropType().equals(PropertyType.UID))
            return "String";
        else
            return "Object";
    }

    private static String getCurretDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
