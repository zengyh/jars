// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityCatalogLoader.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.MetaDBException;
import com.sinitek.base.metadb.config.impl.EntityCatalogImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

// Referenced classes of package com.sinitek.base.metadb.config:
//            MetaDBConfigException, IDataSourceProvider

public class EntityCatalogLoader
{
    private static class EntityCatalogRowMapper
        implements RowMapper
    {

        public Object mapRow(ResultSet resultSet, int i)
            throws SQLException
        {
            EntityCatalogImpl catalog = new EntityCatalogImpl();
            String catalogKey = resultSet.getString("CATALOGKEY");
            String catalogName = resultSet.getString("CATALOGNAME");
            String catalogInfo = resultSet.getString("CATALOGINFO");
            String prefix = resultSet.getString("TABLENAMEPREFIX");
            catalogKey = catalogKey.trim().toUpperCase();
            catalogName = catalogName.trim();
            catalogInfo = catalogInfo.trim();
            if(prefix == null)
                prefix = "";
            catalog.setCatalogInfo(catalogInfo);
            catalog.setCatalogKey(catalogKey);
            catalog.setCatalogName(catalogName);
            catalog.setCatalogPrefix(prefix);
            return catalog;
        }

        private EntityCatalogRowMapper()
        {
        }

    }


    public EntityCatalogLoader()
    {
    }

    public List getAllEntityCatalog(IDataSourceProvider dataSourceProvider)
        throws MetaDBException
    {
        try
        {
            javax.sql.DataSource ds = dataSourceProvider.getDataSource();
            JdbcTemplate template = new JdbcTemplate(ds);
            return template.query("select * from METADB_ENTITYCATALOG", new EntityCatalogRowMapper());
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBConfigException("0011", e);
        }
    }

    public List getEntityCatalog(IDataSourceProvider dataSourceProvider, String targetCatalogs[])
        throws MetaDBException
    {
        try
        {
            javax.sql.DataSource ds = dataSourceProvider.getDataSource();
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("catalogs", Arrays.asList(targetCatalogs));
            return template.query("select * from METADB_ENTITYCATALOG where lower(CATALOGKEY) in (:catalogs)", param, new EntityCatalogRowMapper());
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBConfigException("0011", e);
        }
    }
}
