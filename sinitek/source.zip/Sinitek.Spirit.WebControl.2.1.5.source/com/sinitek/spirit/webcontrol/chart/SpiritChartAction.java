// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritChartAction.java

package com.sinitek.spirit.webcontrol.chart;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.chart:
//            IChartAware

public class SpiritChartAction
{

    public SpiritChartAction()
    {
    }

    public Object getChartResult(Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        String clazz = (String)options.get("clazz");
        try
        {
            IChartAware action = (IChartAware)Class.forName(clazz).newInstance();
            return action.getChartResult(options, params, request);
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/chart/SpiritChartAction);

}
