// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableColsBean.java

package com.sinitek.base.metadb.config.impl;


public class TableColsBean
{

    public TableColsBean()
    {
    }

    public String getColName()
    {
        return colName;
    }

    public void setColName(String colName)
    {
        this.colName = colName;
    }

    public int getDataLength()
    {
        return dataLength;
    }

    public void setDataLength(int dataLength)
    {
        this.dataLength = dataLength;
    }

    public int getDataPrecision()
    {
        return dataPrecision;
    }

    public void setDataPrecision(int dataPrecision)
    {
        this.dataPrecision = dataPrecision;
    }

    public int getDataScale()
    {
        return dataScale;
    }

    public void setDataScale(int dataScale)
    {
        this.dataScale = dataScale;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    private String colName;
    private String dataType;
    private int dataLength;
    private int dataPrecision;
    private int dataScale;
    private boolean nullable;
}
