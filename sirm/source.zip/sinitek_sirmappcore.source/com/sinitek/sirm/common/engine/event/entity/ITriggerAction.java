// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITriggerAction.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.IStreamValue;
import java.util.Date;

public interface ITriggerAction
    extends IMetaObjectImpl
{

    public abstract String getEventCode();

    public abstract void setEventCode(String s);

    public abstract String getActionCode();

    public abstract void setActionCode(String s);

    public abstract String getLocation();

    public abstract void setLocation(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getDealtime();

    public abstract void setDealtime(Date date);

    public abstract IStreamValue getActionParam();

    public abstract void setActionParam(IStreamValue istreamvalue);

    public static final String ENTITY_NAME = "TRIGGERACTION";
}
