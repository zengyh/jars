// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListenerManager.java

package com.sinitek.sirm.common.engine.event.utils;

import com.sinitek.base.metadb.IStreamValue;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.engine.event.entity.ITriggerAction;
import com.sinitek.sirm.common.engine.event.service.IEventManagerService;
import com.sinitek.sirm.common.utils.LogUtils;
import com.sinitek.sirm.common.utils.TimeUtil;
import java.io.*;
import java.util.*;
import javolution.util.FastList;
import javolution.util.FastMap;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            AbstractEventListener, SimpleEventObject, SimpleEventTester, AbstractEventObject

public class ListenerManager
{

    public ListenerManager()
    {
    }

    protected Map getListenerMap()
    {
        if(listenerMap == null)
            synchronized(lock)
            {
                if(listenerMap == null)
                    listenerMap = new FastMap();
            }
        return listenerMap;
    }

    public transient void addEventListener(AbstractEventListener listeners[])
    {
        AbstractEventListener arr$[] = listeners;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            AbstractEventListener listener = arr$[i$];
            Map map = getListenerMap();
            Map sourceMap = (Map)map.get(listener.getSource());
            if(sourceMap == null)
            {
                sourceMap = new FastMap();
                map.put(listener.getSource(), sourceMap);
            }
            List listenerList = (List)sourceMap.get(listener.getEventName());
            if(listenerList == null)
            {
                listenerList = new FastList();
                sourceMap.put(listener.getEventName(), listenerList);
            }
            listenerList.add(listener);
        }

    }

    public void putListenerManager(ListenerManager manager)
    {
        Map map = getListenerMap();
        map.putAll(manager.getListenerMap());
    }

    public transient void removeEventListener(AbstractEventListener listeners[])
    {
        AbstractEventListener arr$[] = listeners;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            AbstractEventListener listener = arr$[i$];
            Map sourceMap = (Map)getListenerMap().get(listener.getSource());
            if(sourceMap == null)
                continue;
            List listenerList = (List)sourceMap.get(listener.getEventName());
            if(listenerList != null)
                listenerList.remove(listener);
        }

    }

    public List getEventListeners(String name, Object source)
    {
        Map sourceMap = (Map)getListenerMap().get(source != null ? source : ((Object) (this)));
        List listenerList = new FastList();
        if(sourceMap != null)
        {
            List listeners = (List)sourceMap.get(name);
            if(listeners != null)
            {
                Iterator i$ = listeners.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    AbstractEventListener listener = (AbstractEventListener)i$.next();
                    if(listener != null)
                        listenerList.add(listener);
                } while(true);
            }
        }
        return listenerList;
    }

    public List dispatchEvent(String name, Object source)
    {
        return dispatchEvent(name, source, null);
    }

    public List dispatchEvent(String name, Object source, AbstractEventObject eventObject)
    {
        Map sourceMap = (Map)getListenerMap().get(source != null ? source : ((Object) (this)));
        List list = new FastList();
        if(sourceMap != null)
        {
            IEventManagerService eventManagerService = CommonServiceFactory.getEventManagerService();
            List listeners = (List)sourceMap.get(name);
            if(listeners != null)
            {
                if(eventObject == null)
                {
                    eventObject = new SimpleEventObject(source);
                    eventObject.setEventTester(new SimpleEventTester());
                }
                eventObject.setEventCode(name);
                list.add(eventObject);
                for(Iterator i$ = listeners.iterator(); i$.hasNext();)
                {
                    AbstractEventListener listener = (AbstractEventListener)i$.next();
                    if(listener.getSyncFlag() == 0)
                    {
                        ITriggerAction triggerAction = (ITriggerAction)(new MetaDBTemplate()).createNewMetaObject("TRIGGERACTION");
                        triggerAction.setEventCode(name);
                        triggerAction.setActionCode(listener.getActionCode());
                        triggerAction.setLocation(listener.getActionLocation());
                        triggerAction.setStatus(Integer.valueOf(0));
                        try
                        {
                            boolean isok = triggerAction != null && triggerAction.getActionParam() != null && triggerAction.getActionParam().getInputStream().available() > 0;
                            if(isok)
                                getActionParam(eventObject, triggerAction.getActionParam().getOutputStream());
                        }
                        catch(Exception e)
                        {
                            LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u83B7\u53D6\u52A8\u4F5C\u53C2\u6570\u5931\u8D25", e);
                        }
                        triggerAction.setDealtime(TimeUtil.getSysDateAsDate());
                        eventManagerService.saveTriggerAction(triggerAction);
                    } else
                    {
                        listener.dispatch(eventObject);
                    }
                }

            } else
            {
                LogUtils.info(com/sinitek/sirm/common/engine/event/utils/ListenerManager, (new StringBuilder()).append("\u4E8B\u4EF6\u89E6\u53D1\u5931\u8D25\uFF0C\u6570\u636E\u5E93\u672A\u914D\u7F6E\u4E8B\u4EF6\u4EE3\u7801\u4E3A\u3010").append(name).append("\u3011\u7684\u76F8\u5E94\u52A8\u4F5C").toString());
            }
        }
        return list;
    }

    public List dispatchEvent(String name, AbstractEventObject eventObject)
    {
        return dispatchEvent(name, null, eventObject);
    }

    protected List getAllListeners()
    {
        List result = new FastList();
        Map listenerMap;
        for(Iterator i$ = getListenerMap().values().iterator(); i$.hasNext(); result.addAll(listenerMap.values()))
            listenerMap = (Map)i$.next();

        return result;
    }

    public OutputStream getActionParam(AbstractEventObject eventObject, OutputStream out)
    {
        ObjectOutputStream oos = null;
        ObjectOutputStream objectoutputstream;
        oos = new ObjectOutputStream(out);
        oos.writeObject(eventObject);
        objectoutputstream = oos;
        if(oos != null)
            try
            {
                oos.close();
            }
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        if(out != null)
            try
            {
                out.flush();
                out.close();
            }
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        return objectoutputstream;
        IOException e;
        e;
        e.printStackTrace();
        if(oos != null)
            try
            {
                oos.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        if(out != null)
            try
            {
                out.flush();
                out.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_187;
        Exception exception;
        exception;
        if(oos != null)
            try
            {
                oos.close();
            }
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        if(out != null)
            try
            {
                out.flush();
                out.close();
            }
            catch(IOException e)
            {
                LogUtils.error(com/sinitek/sirm/common/engine/event/utils/ListenerManager, "\u5173\u95ED\u6D41\u5931\u8D25", e);
            }
        throw exception;
        return oos;
    }

    private static final byte lock[] = new byte[0];
    private Map listenerMap;

}
