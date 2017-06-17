// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritAutoCompleteAction.java

package com.sinitek.spirit.webcontrol.autocomplete;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.autocomplete:
//            IAutoCompleteAware

public class SpiritAutoCompleteAction
{

    public SpiritAutoCompleteAction()
    {
    }

    public List call(String inputValue, Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        String clazz = (String)options.get("clazz");
        try
        {
            IAutoCompleteAware action = (IAutoCompleteAware)Class.forName(clazz).newInstance();
            return action.match(inputValue, options, params, request);
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/autocomplete/SpiritAutoCompleteAction);

}
