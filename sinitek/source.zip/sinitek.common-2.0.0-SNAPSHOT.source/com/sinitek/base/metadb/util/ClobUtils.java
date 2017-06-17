// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClobUtils.java

package com.sinitek.base.metadb.util;

import com.sinitek.base.metadb.MetaDBContextFactory;
import java.io.*;
import java.sql.*;
import javax.sql.DataSource;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import org.apache.commons.dbcp.DelegatingResultSet;
import org.apache.log4j.Logger;

public class ClobUtils
{

    public ClobUtils()
    {
        logger = Logger.getLogger(getClass());
        dataSource = MetaDBContextFactory.getInstance().getDataSource();
    }

    public OracleResultSet getOracleResultSet(ResultSet rs)
    {
        if(rs instanceof OracleResultSet)
            return (OracleResultSet)rs;
        if(rs instanceof DelegatingResultSet)
        {
            ResultSet _rs = ((DelegatingResultSet)rs).getDelegate();
            if(_rs != null)
            {
                if(_rs instanceof OracleResultSet)
                    return (OracleResultSet)_rs;
                else
                    throw new RuntimeException((new StringBuilder()).append("ResultSet\u7684\u7C7B\uFF1A").append(_rs.getClass()).toString());
            } else
            {
                throw new RuntimeException("apapche\u7684DelegatingResultSet\u7684getDelegate\u8FD4\u56DEnull");
            }
        } else
        {
            throw new RuntimeException((new StringBuilder()).append("\u6CA1\u6709\u4F7F\u7528OracleDataSource\u4E5F\u4E0D\u662Ftomcat").append(rs.getClass()).toString());
        }
    }

    public void saveClobContent(int id, String content, String table, String clobColumnName, String keyColumnName)
        throws Exception
    {
        Connection conn;
        DataSource ods = dataSource;
        conn = ods.getConnection();
        Statement stmt;
        ResultSet rset;
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        String sql = (new StringBuilder()).append("UPDATE ").append(table).append(" SET ").append(clobColumnName).append("=EMPTY_CLOB() WHERE ").append(keyColumnName).append("=").append(id).toString();
        stmt.execute(sql);
        logger.debug("Content Cleared!");
        sql = (new StringBuilder()).append("SELECT ").append(clobColumnName).append(" FROM ").append(table).append(" WHERE ").append(keyColumnName).append("=").append(id).toString();
        rset = stmt.executeQuery(sql);
        logger.debug(sql);
_L5:
        CLOB clob;
        if(!rset.next())
            break; /* Loop/switch isn't completed */
        logger.debug("Found Row!");
        clob = getOracleResultSet(rset).getCLOB(1);
        if(clob == null) goto _L2; else goto _L1
_L1:
        logger.debug((new StringBuilder()).append("Found clob!").append(clob.length()).append("\t").append(clob.getLength()).append("\t").append(clob.getChunkSize()).toString());
        clob.open(1);
        fillClob(clob, content);
        clob.close();
          goto _L3
        Exception e;
        e;
        logger.error(e.getMessage());
        e.printStackTrace();
        clob.close();
          goto _L3
        Exception exception;
        exception;
        clob.close();
        throw exception;
_L3:
        logger.debug("Content saved!");
        continue; /* Loop/switch isn't completed */
_L2:
        logger.error("Clob IS NUll!");
        if(true) goto _L5; else goto _L4
_L4:
        conn.commit();
        rset.close();
        stmt.close();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_451;
        SQLException e;
        e;
        e.printStackTrace();
        logger.error(e.getMessage());
        e.printStackTrace();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_451;
        Exception exception1;
        exception1;
        conn.close();
        logger.debug("Connection closed!");
        throw exception1;
    }

    public String getClobContent(int id, String table, String clobColumnName, String keyColumnName)
        throws Exception
    {
        Connection conn;
        String content;
        logger.debug((new StringBuilder()).append("getClobContent for:").append(id).toString());
        DataSource ods = dataSource;
        conn = ods.getConnection();
        logger.debug((new StringBuilder()).append("Connection open!").append(conn.getMetaData().getDriverName()).append("\t").append(conn.getMetaData().getDriverVersion()).append("\t").append(conn.getMetaData().getURL()).append("\t").append(conn.getMetaData().getUserName()).toString());
        content = null;
        Statement stmt = conn.createStatement();
        String sql = (new StringBuilder()).append("SELECT ").append(clobColumnName).append(" FROM ").append(table).append(" WHERE ").append(keyColumnName).append("=").append(id).toString();
        ResultSet rset = stmt.executeQuery(sql);
        logger.debug(sql);
        while(rset.next()) 
        {
            logger.debug("Found row!");
            CLOB clob = getOracleResultSet(rset).getCLOB(1);
            if(clob != null)
            {
                logger.debug((new StringBuilder()).append("Find clob!").append(clob.length()).append("\t").append(clob.getLength()).append("\t").append(clob.getChunkSize()).toString());
                clob.open(0);
                content = dumpClob(clob);
                clob.close();
            } else
            {
                logger.info("Content in CLOB IS NUll!");
            }
        }
        rset.close();
        stmt.close();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_427;
        SQLException e;
        e;
        e.printStackTrace();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_427;
        Exception exception;
        exception;
        conn.close();
        logger.debug("Connection closed!");
        throw exception;
        return content;
    }

    public void saveBlobContent(int id, InputStream is, String table, String blobColumnName, String keyColumnName)
        throws Exception
    {
        Connection conn;
        logger.debug((new StringBuilder()).append("saveBlobContent for:").append(id).toString());
        DataSource ods = dataSource;
        conn = ods.getConnection();
        logger.debug((new StringBuilder()).append("Connection open!").append(conn.getMetaData().getDriverName()).append("\t").append(conn.getMetaData().getDriverVersion()).append("\t").append(conn.getMetaData().getURL()).append("\t").append(conn.getMetaData().getUserName()).toString());
        Statement stmt;
        ResultSet rset;
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        String sql = (new StringBuilder()).append("UPDATE ").append(table).append(" SET ").append(blobColumnName).append("=EMPTY_BLOB() WHERE ").append(keyColumnName).append("=").append(id).toString();
        stmt.execute(sql);
        logger.debug((new StringBuilder()).append(sql).append("\tcontent Cleared!").toString());
        sql = (new StringBuilder()).append("SELECT ").append(blobColumnName).append(" FROM ").append(table).append(" WHERE ").append(keyColumnName).append("=").append(id).toString();
        rset = stmt.executeQuery(sql);
        logger.debug(sql);
_L5:
        BLOB blob;
        if(!rset.next())
            break; /* Loop/switch isn't completed */
        logger.debug("Found Row!");
        blob = getOracleResultSet(rset).getBLOB(1);
        if(blob == null) goto _L2; else goto _L1
_L1:
        logger.debug((new StringBuilder()).append("Found blob!").append(blob.length()).append("\t").append(blob.getLength()).append("\t").append(blob.getChunkSize()).toString());
        blob.open(1);
        fillBlob(blob, is);
        blob.close();
          goto _L3
        Exception e;
        e;
        logger.error(e.getMessage());
        e.printStackTrace();
        blob.close();
          goto _L3
        Exception exception;
        exception;
        blob.close();
        throw exception;
_L3:
        logger.debug("CONTENT saved!");
        continue; /* Loop/switch isn't completed */
_L2:
        logger.error("CONTENT IS NUll!");
        if(true) goto _L5; else goto _L4
_L4:
        conn.commit();
        rset.close();
        stmt.close();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_575;
        SQLException e;
        e;
        e.printStackTrace();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_575;
        Exception exception1;
        exception1;
        conn.close();
        logger.debug("Connection closed!");
        throw exception1;
    }

    public InputStream getBlobContent(int id, String table, String blobColumnName, String keyColumnName)
        throws Exception
    {
        Connection conn;
        InputStream is;
        logger.debug((new StringBuilder()).append("getBlobContent for id:").append(id).toString());
        DataSource ods = dataSource;
        conn = ods.getConnection();
        logger.debug((new StringBuilder()).append("Connection open!").append(conn.getMetaData().getDriverName()).append("\t").append(conn.getMetaData().getDriverVersion()).append("\t").append(conn.getMetaData().getURL()).append("\t").append(conn.getMetaData().getUserName()).toString());
        is = null;
        Statement stmt = conn.createStatement();
        String sql = (new StringBuilder()).append("SELECT ").append(blobColumnName).append(" FROM ").append(table).append(" WHERE ").append(keyColumnName).append("=").append(id).toString();
        ResultSet rset = stmt.executeQuery(sql);
        logger.debug(sql);
        while(rset.next()) 
        {
            logger.debug("Found row!");
            BLOB blob = getOracleResultSet(rset).getBLOB(1);
            logger.debug("blob got!");
            if(blob != null)
            {
                logger.debug((new StringBuilder()).append("Find blob!").append(blob.length()).append("\t").append(blob.getLength()).append("\t").append(blob.getChunkSize()).toString());
                blob.open(0);
                is = dumpBlob(blob);
                blob.close();
                logger.debug("Content retrieved!");
            } else
            {
                logger.info("Content in BLOB IS NUll!");
            }
        }
        rset.close();
        stmt.close();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_457;
        SQLException e;
        e;
        logger.error(e.getMessage());
        e.printStackTrace();
        conn.close();
        logger.debug("Connection closed!");
        break MISSING_BLOCK_LABEL_457;
        Exception exception;
        exception;
        conn.close();
        logger.debug("Connection closed!");
        throw exception;
        return is;
    }

    protected String dumpClob(CLOB clob)
        throws Exception
    {
        System.out.println((new StringBuilder()).append("length:").append(clob.length()).toString());
        Reader instream = clob.getCharacterStream();
        char buffer[] = new char[100];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while((length = instream.read(buffer)) != -1) 
            sb.append(buffer, 0, length);
        instream.close();
        logger.debug((new StringBuilder()).append("Content length:").append(sb.length()).toString());
        return sb.toString();
    }

    protected void fillClob(CLOB clob, String str)
        throws Exception
    {
        Writer outstream = clob.getCharacterOutputStream();
        int i = 0;
        int chunk = 100;
        if(str.length() - i < chunk)
            chunk = str.length() - i;
        do
        {
            if(i >= str.length())
                break;
            outstream.write(str, i, chunk);
            i += chunk;
            if(str.length() - i < chunk)
                chunk = str.length() - i;
        } while(true);
        outstream.close();
    }

    protected InputStream dumpBlob(BLOB blob)
        throws Exception
    {
        InputStream instream = blob.getBinaryStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        for(int length = 0; (length = instream.read(buffer)) != -1;)
            os.write(buffer, 0, length);

        os.close();
        instream.close();
        logger.debug((new StringBuilder()).append("Content length:").append(os.size()).toString());
        return new ByteArrayInputStream(os.toByteArray());
    }

    protected void fillBlob(BLOB blob, InputStream is)
        throws Exception
    {
        OutputStream outstream = blob.getBinaryOutputStream();
        int i = 0;
        byte buffer[] = new byte[100];
        while((i = is.read(buffer)) != -1) 
            outstream.write(buffer, 0, i);
        outstream.close();
    }

    protected Logger logger;
    private DataSource dataSource;
}
