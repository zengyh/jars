// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynHeader.java

package com.sinitek.sirm.common.support.datasyn;

import java.io.Serializable;

public class DataSynHeader
    implements Serializable
{

    public DataSynHeader()
    {
        datasrc = null;
        operartorId = null;
        serviceName = null;
        operation = null;
    }

    public String getDatasrc()
    {
        return datasrc;
    }

    public void setDatasrc(String datasrc)
    {
        this.datasrc = datasrc;
    }

    public String getOperartorId()
    {
        return operartorId;
    }

    public void setOperartorId(String operartorId)
    {
        this.operartorId = operartorId;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    private String datasrc;
    private String operartorId;
    private String serviceName;
    private String operation;
}
