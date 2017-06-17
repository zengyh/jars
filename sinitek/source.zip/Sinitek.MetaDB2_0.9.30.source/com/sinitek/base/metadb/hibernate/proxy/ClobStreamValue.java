// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClobStreamValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.IStreamValue;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.SQLException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractStreamValue

public class ClobStreamValue extends AbstractStreamValue
{

    public ClobStreamValue(Clob clob, String entityName)
    {
        super(entityName);
        this.clob = clob;
    }

    public InputStream getOrigInputStream()
        throws IOException, SQLException
    {
        return clob != null ? clob.getAsciiStream() : null;
    }

    public IStreamValue deepCopy()
    {
        return new ClobStreamValue(clob, entityName);
    }

    private transient Clob clob;
}
