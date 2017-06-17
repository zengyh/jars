// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
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
        JdbcTemplate template;
        javax.sql.DataSource ds = dataSourceProvider.getDataSource();
        template = new JdbcTemplate(ds);
        return template.query("select * from METADB_ENTITYCATALOG", new EntityCatalogRowMapper());
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new MetaDBConfigException("0011", e);
    }

    public List getEntityCatalog(IDataSourceProvider dataSourceProvider, String targetCatalogs[])
        throws MetaDBException
    {
        NamedParameterJdbcTemplate template;
        MapSqlParameterSource param;
        javax.sql.DataSource ds = dataSourceProvider.getDataSource();
        template = new NamedParameterJdbcTemplate(ds);
        param = new MapSqlParameterSource();
        param.addValue("catalogs", Arrays.asList(targetCatalogs));
        return template.query("select * from METADB_ENTITYCATALOG where lower(CATALOGKEY) in (:catalogs)", param, new EntityCatalogRowMapper());
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new MetaDBConfigException("0011", e);
    }
}
