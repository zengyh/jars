// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlobStreamValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.IStreamValue;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractStreamValue

public class BlobStreamValue extends AbstractStreamValue
{

    public BlobStreamValue(Blob blob, String entityName)
    {
        super(entityName);
        this.blob = blob;
    }

    public InputStream getOrigInputStream()
        throws IOException, SQLException
    {
        return blob != null ? blob.getBinaryStream() : null;
    }

    public IStreamValue deepCopy()
    {
        return new BlobStreamValue(blob, entityName);
    }

    protected void resetStatus()
    {
        super.resetStatus();
    }

    private transient Blob blob;
}
