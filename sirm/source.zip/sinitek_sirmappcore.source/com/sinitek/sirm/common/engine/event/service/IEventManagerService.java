// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEventManagerService.java

package com.sinitek.sirm.common.engine.event.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.engine.event.entity.*;
import com.sinitek.sirm.common.engine.event.utils.ListenerManager;
import java.util.List;

public interface IEventManagerService
{

    public abstract void saveEventInfo(IEventInfo ieventinfo);

    public abstract void saveActionInfo(IActionInfo iactioninfo);

    public abstract void saveEventListener(IEventListener ieventlistener);

    public abstract void deleteEventInfo(IEventInfo ieventinfo);

    public abstract void deleteActionInfo(IActionInfo iactioninfo);

    public abstract void deleteEventListener(IEventListener ieventlistener);

    public abstract void deleteEventListener(int i);

    public abstract ListenerManager getListenerManager();

    public abstract List findAllEventListener();

    public abstract List findAllActionInfo();

    public abstract List findAllActionProperty();

    public abstract List findAllEventInfo();

    public abstract IMetaDBQuery findAllEvent();

    public abstract List findActionInfosByEventCode(String s);

    public abstract List findTriggerActionByStatus(int i);

    public abstract void saveTriggerAction(ITriggerAction itriggeraction);

    public abstract int forUpdateTriggerAction(int i, int j);
}
