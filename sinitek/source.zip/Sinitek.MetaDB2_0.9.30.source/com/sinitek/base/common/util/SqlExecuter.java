// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SqlExecuter.java

package com.sinitek.base.common.util;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class SqlExecuter
{

    public SqlExecuter()
    {
    }

    public static void excute(String filePath, DataSource ds)
        throws Exception
    {
        Connection conn;
        Exception exception;
        List sqls = getSqls(filePath);
        conn = null;
        try
        {
            conn = ds.getConnection();
            String sql;
            Statement stmt;
            for(Iterator iter = sqls.iterator(); iter.hasNext(); stmt.execute(sql))
            {
                sql = (String)iter.next();
                stmt = conn.createStatement();
            }

            try
            {
                conn.commit();
            }
            catch(SQLException se)
            {
                if(!"Can't call commit when autocommit=true".equals(se.getMessage()))
                    throw se;
            }
        }
        catch(Exception ex)
        {
            if(conn != null)
                conn.rollback();
            throw ex;
        }
        finally
        {
            if(conn == null) goto _L0; else goto _L0
        }
        if(conn != null)
            conn.close();
        break MISSING_BLOCK_LABEL_135;
        conn.close();
        throw exception;
    }

    private static List getSqls(String filePath)
        throws Exception
    {
        InputStream is;
        List sqls;
        Exception exception;
        is = null;
        sqls = new ArrayList();
        try
        {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            BufferedReader bif = new BufferedReader(new InputStreamReader(is));
            StringBuffer sql = new StringBuffer();
            for(String _sql = bif.readLine(); _sql != null; _sql = bif.readLine())
            {
                _sql = _sql.trim();
                if(_sql.length() <= 0 || _sql.startsWith("#"))
                    continue;
                if(_sql.endsWith(";"))
                {
                    sql.append(_sql.substring(0, _sql.length() - 1));
                    sqls.add(sql.toString());
                    sql = new StringBuffer();
                } else
                {
                    sql.append(_sql);
                    sql.append('\n');
                }
            }

            bif.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        finally
        {
            if(is == null) goto _L0; else goto _L0
        }
        if(is != null)
            is.close();
        break MISSING_BLOCK_LABEL_195;
        is.close();
        throw exception;
        return sqls;
    }
}
