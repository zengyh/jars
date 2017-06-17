// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynCenterCommonException.java

package com.sinitek.base.metadb.syncenter.common;


// Referenced classes of package com.sinitek.base.metadb.syncenter.common:
//            SynCenterException

public final class SynCenterCommonException extends SynCenterException
{

    public SynCenterCommonException(String errorCode)
    {
        super(errorCode);
    }

    public SynCenterCommonException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynCenterCommonException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public SynCenterCommonException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/metadb/syncenter/common/common_errorcode.properties";
    }

    public String getSubModuleCode()
    {
        return "05";
    }
}
