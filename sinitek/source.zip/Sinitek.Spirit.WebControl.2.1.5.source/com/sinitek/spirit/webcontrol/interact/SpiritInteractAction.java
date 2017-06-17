// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritInteractAction.java

package com.sinitek.spirit.webcontrol.interact;

import com.sinitek.spirit.webcontrol.selector.KeyValueBean;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.interact:
//            InteractiveSelectAware, InteractiveMultiSelectAware

public class SpiritInteractAction
{

    public SpiritInteractAction()
    {
    }

    public List getSelectData(String className, String key, HttpServletRequest request)
        throws Exception
    {
        List list = new ArrayList();
        try
        {
            InteractiveSelectAware action = (InteractiveSelectAware)Class.forName(className).newInstance();
            Map result = action.getData(key, request);
            KeyValueBean bean;
            for(Iterator i$ = result.entrySet().iterator(); i$.hasNext(); list.add(bean))
            {
                Object o = i$.next();
                java.util.Map.Entry e = (java.util.Map.Entry)o;
                bean = new KeyValueBean();
                bean.setKey((String)e.getKey());
                bean.setValue((String)e.getValue());
            }

        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
        return list;
    }

    public List getMultiSelectData(String className, String keys[], HttpServletRequest request)
        throws Exception
    {
        List list = new ArrayList();
        try
        {
            InteractiveMultiSelectAware action = (InteractiveMultiSelectAware)Class.forName(className).newInstance();
            Map result = action.getData(keys, request);
            KeyValueBean bean;
            for(Iterator i$ = result.entrySet().iterator(); i$.hasNext(); list.add(bean))
            {
                Object o = i$.next();
                java.util.Map.Entry e = (java.util.Map.Entry)o;
                bean = new KeyValueBean();
                bean.setKey((String)e.getKey());
                bean.setValue((String)e.getValue());
            }

        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
        return list;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/interact/SpiritInteractAction);

}
