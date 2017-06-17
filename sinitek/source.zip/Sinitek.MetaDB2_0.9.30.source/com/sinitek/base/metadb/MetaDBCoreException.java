// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCoreException.java

package com.sinitek.base.metadb;


// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException

public class MetaDBCoreException extends MetaDBException
{

    public MetaDBCoreException(String errorCode)
    {
        super(errorCode);
    }

    public MetaDBCoreException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public MetaDBCoreException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public MetaDBCoreException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "01";
    }
}
