// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorDefaultImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.config.IDataSourceProvider;
import java.sql.*;
import java.util.Hashtable;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBCoreException, IIdGenerator, MetaDBException, IEntity, 
//            MetaDBLoggerFactory

public class IdGeneratorDefaultImpl
    implements IIdGenerator
{

    public IdGeneratorDefaultImpl()
    {
        lockMap = new Hashtable();
    }

    public int generateId(IEntity entity, IDataSourceProvider dataSourceProvider)
        throws MetaDBException
    {
        Connection conn = null;
        try
        {
            int i;
            try
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u4E3A\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u751F\u6210\u4E3B\u952E\u503C").toString());
                String sql = "select CURRENTVALUE, STEP from METADB_IDGENERATOR\nwhere ENTITYNAME=? for update";
                conn = dataSourceProvider.getDataSource().getConnection();
                conn.setAutoCommit(false);
                PreparedStatement queryStmt = conn.prepareStatement(sql);
                queryStmt.setString(1, entity.getEntityName().toUpperCase());
                ResultSet queryRs = queryStmt.executeQuery();
                String updateSql = "";
                int current;
                if(!queryRs.next())
                {
                    String currentSql = (new StringBuilder()).append("select max(objid) from ").append(entity.getTableName()).toString();
                    ResultSet rs = conn.createStatement().executeQuery(currentSql);
                    if(rs.next())
                    {
                        current = rs.getInt(1);
                        current = current != 0 ? current : 1;
                    } else
                    {
                        current = 1;
                    }
                    updateSql = "insert into METADB_IDGENERATOR(CURRENTVALUE,ENTITYNAME) values (?,?)";
                } else
                {
                    current = queryRs.getInt(1) + queryRs.getInt(2);
                    updateSql = "update METADB_IDGENERATOR set CURRENTVALUE=? where ENTITYNAME=?";
                }
                queryRs.close();
                queryStmt.close();
                PreparedStatement insertStmt = conn.prepareStatement(updateSql);
                insertStmt.setInt(1, current);
                insertStmt.setString(2, entity.getEntityName());
                insertStmt.execute();
                conn.commit();
                i = current;
            }
            catch(Exception ex)
            {
                if(conn != null)
                    try
                    {
                        conn.rollback();
                    }
                    catch(SQLException e)
                    {
                        throw new MetaDBCoreException("0001", ex, new Object[] {
                            entity.getEntityName()
                        });
                    }
                throw new MetaDBCoreException("0001", ex, new Object[] {
                    entity.getEntityName()
                });
            }
            return i;
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    LOGGER.warn("\u4E3B\u952E\u5206\u914D\u5668\u5728\u5173\u95ED\u6570\u636E\u5E93\u94FE\u63A5\u65F6\u5931\u8D25", e);
                }
        }
    }

    private synchronized void lockEntity(IEntity entity)
    {
        Boolean lock = (Boolean)lockMap.get(entity.getEntityName());
        if(lock == null)
        {
            lockMap.put(entity.getEntityName(), Boolean.TRUE);
        } else
        {
            for(; lock.booleanValue(); lock = (Boolean)lockMap.get(entity.getEntityName()))
                try
                {
                    wait();
                }
                catch(InterruptedException e)
                {
                    LOGGER.warn((new StringBuilder()).append("\u5BF9\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u65BD\u52A0\u7684\u4E3B\u952E\u751F\u6210\u5668\u540C\u6B65\u9501\u88AB\u5F3A\u5236\u6253\u65AD").toString(), e);
                }

            lockMap.put(entity.getEntityName(), Boolean.TRUE);
        }
    }

    private synchronized void unlockEntity(IEntity entity)
    {
        lockMap.put(entity.getEntityName(), Boolean.FALSE);
        notifyAll();
    }

    private static final Logger LOGGER;
    private Map lockMap;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
