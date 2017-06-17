// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBEntityPersister.java

package com.sinitek.base.metadb.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.cache.CacheConcurrencyStrategy;
import org.hibernate.cfg.Settings;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.*;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.jdbc.*;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.pretty.MessageHelper;
import org.hibernate.sql.Insert;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.Type;

public class MetaDBEntityPersister extends SingleTableEntityPersister
{

    public MetaDBEntityPersister(PersistentClass persistentClass, CacheConcurrencyStrategy cache, SessionFactoryImplementor factory, Mapping mapping)
        throws HibernateException
    {
        super(persistentClass, cache, factory, mapping);
    }

    protected String generateInsertString(boolean identityInsert, boolean includeProperty[], int j)
    {
        Insert insert = (new Insert(getFactory().getDialect())).setTableName(getTableName(j));
        if(j == 0 && identityInsert)
            insert.addIdentityColumn(getKeyColumns(0)[0]);
        else
            insert.addColumns(getKeyColumns(j));
        for(int i = 0; i < getEntityMetamodel().getPropertySpan(); i++)
            if(includeProperty[i] && isPropertyOfTable(i, j))
                insert.addColumns(getPropertyColumnNames(i), new boolean[] {
                    true
                });

        if(j == 0)
            addDiscriminatorToInsert(insert);
        if(getFactory().getSettings().isCommentsEnabled())
            insert.setComment((new StringBuilder()).append("insert ").append(getEntityName()).toString());
        String result = insert.toStatementString();
        if(j == 0 && identityInsert && useInsertSelectIdentity())
            result = getFactory().getDialect().appendIdentitySelectToInsert(result);
        return result;
    }

    protected void insert(Serializable id, Object fields[], boolean notNull[], int j, String sql, Object object, SessionImplementor session)
        throws HibernateException
    {
        Expectation expectation;
        boolean callable;
        boolean useBatch;
        if(isInverseTable(j))
            return;
        if(isNullableTable(j) && isAllNull(fields, j))
            return;
        expectation = Expectations.appropriateExpectation(insertResultCheckStyles[j]);
        callable = isInsertCallable(j);
        useBatch = j == 0 && expectation.canBeBatched();
        PreparedStatement insert;
        if(useBatch)
        {
            if(callable)
                insert = session.getBatcher().prepareBatchCallableStatement(sql);
            else
                insert = session.getBatcher().prepareBatchStatement(sql);
        } else
        if(callable)
            insert = session.getBatcher().prepareCallableStatement(sql);
        else
            insert = session.getBatcher().prepareStatement(sql);
        try
        {
            int index = 1;
            index += expectation.prepare(insert);
            boolean insertable[][] = new boolean[getPropertyInsertability().length][1];
            for(int i = 0; i < getPropertyInsertability().length; i++)
                insertable[i][0] = getPropertyInsertability()[i];

            dehydrateInsert(id, fields, null, notNull, insertable, j, insert, session, index);
            if(useBatch)
                session.getBatcher().addToBatch(expectation);
            else
                expectation.verifyOutcome(insert.executeUpdate(), insert, -1);
        }
        catch(SQLException sqle)
        {
            if(useBatch)
                session.getBatcher().abortBatch(sqle);
            throw sqle;
        }
        if(!useBatch)
            session.getBatcher().closeStatement(insert);
        break MISSING_BLOCK_LABEL_394;
        Exception exception;
        exception;
        if(!useBatch)
            session.getBatcher().closeStatement(insert);
        throw exception;
        SQLException sqle;
        sqle;
        throw JDBCExceptionHelper.convert(getFactory().getSQLExceptionConverter(), sqle, (new StringBuilder()).append("could not insert: ").append(MessageHelper.infoString(this)).toString(), sql);
    }

    private int dehydrateInsert(Serializable id, Object fields[], Object rowId, boolean includeProperty[], boolean includeColumns[][], int j, PreparedStatement ps, 
            SessionImplementor session, int index)
        throws SQLException, HibernateException
    {
        for(int i = 0; i < getEntityMetamodel().getPropertySpan(); i++)
            if(includeProperty[i] && isPropertyOfTable(i, j))
            {
                getPropertyTypes()[i].nullSafeSet(ps, fields[i], index + 1, includeColumns[i], session);
                index += getPropertyColumnSpan(i);
            }

        if(rowId != null)
        {
            ps.setObject(index, rowId);
            index++;
        } else
        if(id != null)
        {
            getIdentifierType().nullSafeSet(ps, id, 1, session);
            index += getIdentifierColumnSpan();
        }
        return index;
    }

    private boolean isAllNull(Object array[], int tableNumber)
    {
        for(int i = 0; i < array.length; i++)
            if(isPropertyOfTable(i, tableNumber) && array[i] != null)
                return false;

        return true;
    }
}
