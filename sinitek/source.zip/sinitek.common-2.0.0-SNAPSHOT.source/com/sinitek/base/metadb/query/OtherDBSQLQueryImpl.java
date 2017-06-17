// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OtherDBSQLQueryImpl.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package com.sinitek.base.metadb.query:
//            QueryBatchIterator, IMetaDBQuery, IMetaDBGroupByQuery

public class OtherDBSQLQueryImpl
    implements IMetaDBQuery
{

    public OtherDBSQLQueryImpl()
    {
        firstResult = 0;
        cacheUse = true;
        paramsMap = new HashMap();
        hasParser = false;
    }

    public OtherDBSQLQueryImpl(DataSource ds, String queryStr)
    {
        firstResult = 0;
        cacheUse = true;
        paramsMap = new HashMap();
        hasParser = false;
        this.queryStr = queryStr;
        paramsList = new ArrayList();
        if(ds != null)
            template = new JdbcTemplate(ds);
    }

    protected String genOrderByStr()
    {
        if(StringUtils.isBlank(orderBy))
        {
            if(StringUtils.isBlank(defaultOrderBy))
                return "";
            else
                return (new StringBuilder()).append(" order by ").append(defaultOrderBy).toString();
        } else
        {
            return (new StringBuilder()).append(" order by ").append(orderBy).toString();
        }
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public String getQueryString()
    {
        return queryStr;
    }

    public void setFirstResult(int firstResult)
    {
        this.firstResult = firstResult;
    }

    public void setMaxResult(int maxResult)
    {
        this.maxResult = maxResult;
    }

    public boolean isCacheUse()
    {
        return cacheUse;
    }

    public void setCacheUse(boolean cacheUse)
    {
        this.cacheUse = cacheUse;
    }

    public void setParameter(String parameter, Object value)
    {
        paramsMap.put(parameter, value);
    }

    public void setParameters(Map parameters)
    {
        paramsMap.putAll(parameters);
    }

    public Map getParameters()
    {
        return paramsMap;
    }

    public void setDefaultOrderBy(String defaultOrderBy)
    {
        this.defaultOrderBy = defaultOrderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public int getResultCount()
    {
        String sql;
        try
        {
            parseExpress();
            sql = (new StringBuilder()).append("select count(*) from (").append(queryStr).append(")").toString();
            if(paramsList.isEmpty())
                return template.queryForInt(sql);
        }
        catch(Exception e)
        {
            throw new MetaDBCoreException("0033", e, new Object[] {
                entity != null ? entity.getEntityName() : "SQL", queryStr
            });
        }
        return template.queryForInt(sql, paramsList.toArray());
    }

    public List getResult()
    {
        String sql;
        try
        {
            parseExpress();
            if(maxResult > 0)
            {
                int endResults = firstResult + maxResult;
                sql = (new StringBuilder()).append("select * from (select t_table.*,rownum as t_rownum from (").append(queryStr).append(genOrderByStr()).append(") t_table where rownum <= ").append(endResults).append(") where t_rownum > ").append(firstResult).toString();
            } else
            {
                sql = (new StringBuilder()).append(queryStr).append(genOrderByStr()).toString();
            }
            if(paramsList.isEmpty())
                return template.queryForList(sql);
        }
        catch(Exception e)
        {
            throw new MetaDBCoreException("0034", e, new Object[] {
                entity != null ? entity.getEntityName() : "SQL", queryStr
            });
        }
        return template.queryForList(sql, paramsList.toArray());
    }

    public Iterator getIterator(int fatchSize)
    {
        return new QueryBatchIterator(fatchSize, cacheUse, entity, firstResult, maxResult, queryStr, paramsMap);
    }

    public void parseExpress()
        throws Exception
    {
        hasParser = true;
        String expression;
        int x;
        String tmp;
        int y;
        for(expression = queryStr; expression.indexOf(":") > -1; expression = (new StringBuilder()).append(expression.substring(0, x)).append("?").append(expression.substring(y + tmp.length())).toString())
        {
            x = expression.indexOf(":");
            Pattern pattern = Pattern.compile(":\\S+");
            Matcher mt = pattern.matcher(expression);
            tmp = "";
            y = -1;
            if(!mt.find())
                continue;
            tmp = mt.group(0);
            y = expression.indexOf(tmp, x);
            String key = tmp.substring(1);
            if(paramsMap.containsKey(key))
                paramsList.add(paramsMap.get(key));
        }

        queryStr = expression;
    }

    public Iterator getIterator()
    {
        return getIterator(defaultIterateSize);
    }

    public IMetaDBGroupByQuery getGroupByQuery()
    {
        return null;
    }

    public static void main(String s[])
    {
        (new MetaDBTemplate()).createSqlQuery(null, "");
    }

    protected JdbcTemplate template;
    protected int defaultIterateSize;
    protected int firstResult;
    protected int maxResult;
    protected boolean cacheUse;
    protected String queryStr;
    protected IEntity entity;
    protected String orderBy;
    protected String defaultOrderBy;
    protected Map paramsMap;
    private boolean hasParser;
    protected List paramsList;
}
