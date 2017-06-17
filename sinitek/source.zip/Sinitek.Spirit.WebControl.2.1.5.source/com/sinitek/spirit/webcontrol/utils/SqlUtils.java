// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SqlUtils.java

package com.sinitek.spirit.webcontrol.utils;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.math.BigDecimal;
import java.util.*;

public class SqlUtils
{

    public SqlUtils()
    {
    }

    private static int insertIntoDB(IMetaDBContext ctx, String tableName, Map params)
    {
        StringBuilder sql = new StringBuilder("INSERT INTO\n");
        sql.append(tableName).append("\n");
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        String columnName;
        for(Iterator i$ = params.keySet().iterator(); i$.hasNext(); values.append(":").append(columnName))
        {
            Object o = i$.next();
            columnName = (String)o;
            if(columns.length() != 0)
                columns.append(",");
            if(values.length() != 0)
                values.append(",");
            columns.append(columnName);
        }

        sql.append("(").append(columns).append(")\nVALUES (").append(values).append(")");
        ISQLUpdater updater = ctx.createSqlUpdater(sql.toString());
        updater.setParameters(params);
        return updater.executeUpdate();
    }

    private static List queryFromDB(IMetaDBContext ctx, String tableName, Map params)
    {
        StringBuilder sql = new StringBuilder("SELECT * FROM\n");
        sql.append(tableName).append("\n");
        if(!params.isEmpty())
        {
            sql.append("WHERE\n");
            Iterator iter = params.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(" and ");
            } while(true);
        }
        IMetaDBQuery updater = ctx.createSqlQuery(sql.toString());
        updater.setParameters(params);
        return updater.getResult();
    }

    private static List queryFromDB(String tableName, Map params)
    {
        StringBuilder sql = new StringBuilder("SELECT * FROM\n");
        sql.append(tableName).append("\n");
        if(!params.isEmpty())
        {
            sql.append("WHERE\n");
            Iterator iter = params.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(" and ");
            } while(true);
        }
        IMetaDBQuery updater = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        updater.setParameters(params);
        return updater.getResult();
    }

    public static List queryFromDB(String tableName, Map params, String orderBy)
    {
        StringBuilder sql = new StringBuilder("SELECT * FROM\n");
        sql.append(tableName).append("\n");
        if(!params.isEmpty())
        {
            sql.append("WHERE\n");
            Iterator iter = params.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(" and ");
            } while(true);
        }
        sql.append(" order by ");
        sql.append(orderBy);
        IMetaDBQuery updater = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        updater.setParameters(params);
        return updater.getResult();
    }

    public static List queryFromDB(IMetaDBContext ctx, String tableName, Map params, String orderBy)
    {
        StringBuilder sql = new StringBuilder("SELECT * FROM\n");
        sql.append(tableName).append("\n");
        if(!params.isEmpty())
        {
            sql.append("WHERE\n");
            Iterator iter = params.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(" and ");
            } while(true);
        }
        sql.append(" order by ");
        sql.append(orderBy);
        IMetaDBQuery updater = ctx.createSqlQuery(sql.toString());
        updater.setParameters(params);
        return updater.getResult();
    }

    public static int deleteFromDB(IMetaDBContext ctx, String tableName, Map params)
    {
        StringBuilder sql = new StringBuilder("DELETE FROM\n");
        sql.append(tableName).append("\n");
        if(!params.isEmpty())
        {
            sql.append("WHERE\n");
            Iterator iter = params.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(" and ");
            } while(true);
        }
        ISQLUpdater updater = ctx.createSqlUpdater(sql.toString());
        updater.setParameters(params);
        return updater.executeUpdate();
    }

    private static int updateTable(IMetaDBContext ctx, String tableName, Map queryParams, Map updateParams)
    {
        StringBuilder sql = new StringBuilder("UPDATE\n");
        sql.append(tableName).append("\n");
        Map params = new HashMap();
        if(!updateParams.isEmpty())
        {
            sql.append("SET");
            Iterator iter = updateParams.keySet().iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                String columnName = (String)iter.next();
                sql.append("\n").append(columnName).append("=:").append(columnName);
                if(iter.hasNext())
                    sql.append(",");
            } while(true);
            params.putAll(updateParams);
        }
        if(!queryParams.isEmpty())
        {
            sql.append("\nWHERE\n");
            String columnName;
            for(Iterator iter = queryParams.keySet().iterator(); iter.hasNext(); params.put((new StringBuilder()).append("_").append(columnName).toString(), queryParams.get(columnName)))
            {
                columnName = (String)iter.next();
                sql.append(columnName).append("=:_").append(columnName).append("\n");
                if(iter.hasNext())
                    sql.append(" and ");
            }

        }
        ISQLUpdater updater = ctx.createSqlUpdater(sql.toString());
        updater.setParameters(params);
        return updater.executeUpdate();
    }

    public static int insertOrUpdate(IMetaDBContext ctx, String tableName, Map queryParams, Map updateParams)
    {
        if(queryFromDB(ctx, tableName, queryParams).size() > 0)
            return updateTable(ctx, tableName, queryParams, updateParams);
        else
            return insertIntoDB(ctx, tableName, updateParams);
    }

    public static int getMaxTableId(String table, String idName)
    {
        if(queryFromDB(table, Collections.EMPTY_MAP).size() == 0)
        {
            return 1;
        } else
        {
            IMetaDBQuery updater = (new MetaDBTemplate()).createSqlQuery((new StringBuilder()).append("select max(").append(idName).append(")+1 as value from ").append(table).toString());
            BigDecimal value = (BigDecimal)((Map)updater.getResult().get(0)).get("value");
            return value.intValue();
        }
    }

    public static int getSequenceNo(String sqName)
    {
        return ((BigDecimal)((Map)(new MetaDBTemplate()).createSqlQuery((new StringBuilder()).append("SELECT ").append(sqName).append(".NEXTVAL FROM USER_SEQUENCES").toString()).getResult().get(0)).get("NEXTVAL")).intValue();
    }

    public static Map queryForMap(String tableName, String column, Object value)
    {
        IMetaDBQuery query = (new MetaDBTemplate()).createSqlQuery((new StringBuilder()).append("SELECT * from ").append(tableName).append(" where ").append(column).append("=:value").toString());
        query.setParameter("value", value);
        List list = query.getResult();
        if(list.size() > 0)
            return (Map)list.get(0);
        else
            return null;
    }
}
