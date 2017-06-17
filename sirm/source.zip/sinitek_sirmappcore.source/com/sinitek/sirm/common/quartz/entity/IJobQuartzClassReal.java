// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzClassReal.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IJobQuartzClassReal
    extends IMetaObjectImpl
{

    public abstract Integer getTIMERID();

    public abstract void setTIMERID(Integer integer);

    public abstract String getCLASSNAME();

    public abstract void setCLASSNAME(String s);

    public abstract String getCLASSPATH();

    public abstract void setCLASSPATH(String s);

    public abstract String getMEMO();

    public abstract void setMEMO(String s);

    public abstract Integer getGROUPID();

    public abstract void setGROUPID(Integer integer);

    public static final String ENTITY_NAME = "JOBQUARTZCLASSREAL";
}
