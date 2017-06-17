// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SqlExecuter.java

package com.sinitek.base.common.util;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
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
        List sqls = getSqls(filePath);
        Connection conn = null;
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

            conn.commit();
        }
        catch(Exception ex)
        {
            if(conn != null)
                conn.rollback();
            throw ex;
        }
        finally
        {
            if(conn != null)
                conn.close();
        }
    }

    private static List getSqls(String filePath)
        throws Exception
    {
        InputStream is = null;
        List sqls = new ArrayList();
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
            if(is != null)
                is.close();
        }
        return sqls;
    }
}
