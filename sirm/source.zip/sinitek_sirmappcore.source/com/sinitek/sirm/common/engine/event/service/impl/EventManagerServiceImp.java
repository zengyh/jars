// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventManagerServiceImp.java

package com.sinitek.sirm.common.engine.event.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.engine.event.DBMixedEventManager;
import com.sinitek.sirm.common.engine.event.entity.*;
import com.sinitek.sirm.common.engine.event.service.IEventManagerService;
import com.sinitek.sirm.common.engine.event.utils.*;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.ObjectUtils;
import com.sinitek.spirit.logger.LoggerFactory;
import java.util.*;
import javolution.util.FastMap;
import org.apache.commons.logging.Log;

public class EventManagerServiceImp extends MetaDBContextSupport
    implements IEventManagerService
{

    public EventManagerServiceImp()
    {
    }

    public void saveEventInfo(IEventInfo info)
    {
        info.save();
    }

    public void saveActionInfo(IActionInfo action)
    {
        action.save();
    }

    public void saveEventListener(IEventListener listener)
    {
        listener.save();
    }

    public void deleteEventInfo(IEventInfo info)
    {
        info.delete();
    }

    public void deleteActionInfo(IActionInfo action)
    {
        action.delete();
    }

    public void deleteEventListener(IEventListener listener)
    {
        listener.delete();
    }

    public void deleteEventListener(int objId)
    {
        IMetaDBContext context = getMetaDBContext();
        IEventListener el = (IEventListener)context.get(com/sinitek/sirm/common/engine/event/entity/IEventListener, objId);
        if(el != null)
            el.remove();
    }

    public List findAllEventListener()
    {
        return getMetaDBContext().query(com/sinitek/sirm/common/engine/event/entity/IEventListener, "1=1", new HashMap());
    }

    public List findAllActionInfo()
    {
        return getMetaDBContext().query(com/sinitek/sirm/common/engine/event/entity/IActionInfo, "1=1", new HashMap());
    }

    public List findAllActionProperty()
    {
        return getMetaDBContext().query(com/sinitek/sirm/common/engine/event/entity/IEventActionProperty, "1=1", new HashMap());
    }

    public List findAllEventInfo()
    {
        return getMetaDBContext().query(com/sinitek/sirm/common/engine/event/entity/IEventInfo, "1=1", new HashMap());
    }

    public IMetaDBQuery findAllEvent()
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select listener.objid, event.code eventcode,event.name eventname,action.code actioncode, action.name actionname ");
        sql.append("from sirm_eventinfo event,sirm_eventlistener listener,sirm_actioninfo action ");
        sql.append("where event.objid=listener.eventinfoid and action.objid=listener.actioninfoid");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        return query;
    }

    public ListenerManager getListenerManager()
    {
        List eventListenerList = findAllEventListener();
        Map eventListenersMap = ObjectUtils.groupList("eventInfoId", eventListenerList);
        List actionInfoList = findAllActionInfo();
        Map processMap = new FastMap();
        List eventInfoList = findAllEventInfo();
        ListenerManager manager = new ListenerManager();
        Map actionInfoMap = new HashMap();
        IActionInfo actionInfo;
        for(Iterator i$ = actionInfoList.iterator(); i$.hasNext(); actionInfoMap.put(Integer.valueOf(actionInfo.getId()), actionInfo))
        {
            actionInfo = (IActionInfo)i$.next();
            IEventProcess process = getEventProcess(actionInfo.getLocation());
            if(process != null)
                processMap.put(Integer.valueOf(actionInfo.getId()), process);
        }

        for(Iterator i$ = eventInfoList.iterator(); i$.hasNext();)
        {
            IEventInfo info = (IEventInfo)i$.next();
            List listeners = (List)eventListenersMap.get(Integer.valueOf(info.getId()));
            if(listeners != null)
            {
                Iterator i$ = listeners.iterator();
                while(i$.hasNext()) 
                {
                    IEventListener eventListener = (IEventListener)i$.next();
                    IEventProcess process = (IEventProcess)processMap.get(eventListener.getActionInfoId());
                    IActionInfo actionInfo = (IActionInfo)actionInfoMap.get(eventListener.getActionInfoId());
                    if(process != null && actionInfo != null)
                    {
                        SimpleEventListener listener = new SimpleEventListener(info.getCode(), DBMixedEventManager.getInstance());
                        listener.setActionCode(info.getCode());
                        listener.setActionLocation(actionInfo.getLocation());
                        listener.setSyncFlag(actionInfo.getSyncflag().intValue());
                        listener.setEventProcess(process);
                        manager.addEventListener(new AbstractEventListener[] {
                            listener
                        });
                    }
                }
            }
        }

        return manager;
    }

    private static IEventProcess getEventProcess(String location)
    {
        IEventProcess _result = null;
        if(location.startsWith("classpath:"))
        {
            String clazz = location.substring("classpath:".length());
            logger.debug((new StringBuilder()).append("classpath:").append(clazz).toString());
            try
            {
                _result = (IEventProcess)ObjectUtils.newInstance(clazz);
            }
            catch(Exception e) { }
        } else
        if(location.startsWith("http:"))
        {
            _result = new HttpEventProcess(location);
            logger.debug((new StringBuilder()).append("http:").append(location).toString());
        }
        logger.debug((new StringBuilder()).append("[result:][").append(_result == null).append("]").toString());
        return _result;
    }

    public List findActionInfosByEventCode(String eventCode)
    {
        List result = new ArrayList();
        if(eventCode != null && !"".equals(eventCode))
        {
            String sql = "select * from sirm_actioninfo where objid in (select actioninfoid from sirm_eventlistener where eventinfoid in (select objid from sirm_eventinfo where code =:eventCode))";
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql);
            iMetaDBQuery.setParameter("eventCode", eventCode);
            List list = iMetaDBQuery.getResult();
            IActionInfo actionInfo;
            for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(actionInfo))
            {
                Map map = (Map)i$.next();
                actionInfo = new ActionInfoImpl();
                actionInfo.setCode(String.valueOf(map.get("Code")));
                actionInfo.setBrief(String.valueOf(map.get("Brief")));
                actionInfo.setLocation(String.valueOf(map.get("Location")));
                actionInfo.setName(String.valueOf(map.get("Name")));
                actionInfo.setSyncflag(NumberTool.convertMapKeyToInt(map, "Syncflag"));
            }

        }
        return result;
    }

    public List findTriggerActionByStatus(int status)
    {
        StringBuilder sql = new StringBuilder(" 1=1 ");
        Map map = new HashMap();
        if(status != -1)
        {
            sql.append(" and status=:status");
            map.put("status", Integer.valueOf(status));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("TRIGGERACTION", sql.toString());
        iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public void saveTriggerAction(ITriggerAction triggerAction)
    {
        triggerAction.save();
    }

    public int forUpdateTriggerAction(int objid, int status)
    {
        int result = -1;
        StringBuilder sql = new StringBuilder("select * from sirm_triggeraction where 1=1");
        if(objid != 0 && status != -1)
        {
            sql.append(" and objid=:objid and status=:status for update");
            ISQLUpdater sqlUpdater = getMetaDBContext().createSqlUpdater(sql.toString());
            sqlUpdater.setParameter("objid", Integer.valueOf(objid));
            sqlUpdater.setParameter("status", Integer.valueOf(status));
            result = sqlUpdater.executeUpdate();
        }
        return result;
    }

    private static Log logger = LoggerFactory.getLogger(com/sinitek/sirm/common/engine/event/service/impl/EventManagerServiceImp);

}
