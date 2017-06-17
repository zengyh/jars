// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileStreamValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.IStreamValue;
import java.io.*;
import java.sql.SQLException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractStreamValue

public class FileStreamValue extends AbstractStreamValue
{

    public FileStreamValue(File origFile, String entityName)
    {
        super(entityName);
        this.origFile = origFile;
    }

    public InputStream getOrigInputStream()
        throws IOException, SQLException
    {
        return origFile != null ? new FileInputStream(origFile) : null;
    }

    public IStreamValue deepCopy()
    {
        return new FileStreamValue(origFile, entityName);
    }

    private transient File origFile;
}
