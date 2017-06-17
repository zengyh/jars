// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterContext.java

package com.sinitek.base.starter;

import com.sinitek.base.starter.config.StarterConfigInfo;
import com.sinitek.base.starter.config.StarterConfigReader;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.starter:
//            StarterException, IStarter

public class StarterContext
{

    public StarterContext()
    {
    }

    public static void start()
    {
        String file = System.getProperty("com.sinitek.base.starter.StarterContext.ConfigFile", "starterconfig.xml");
        start(StarterConfigReader.readConfig(file));
    }

    public static void start(List starterInfos)
    {
        for(Iterator iter = starterInfos.iterator(); iter.hasNext(); callStarter((StarterConfigInfo)iter.next()));
    }

    private static void callStarter(StarterConfigInfo info)
    {
        String className = info.getStarterClass();
        LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u542F\u52A8[").append(className).append("]").toString());
        Object _starter = null;
        try
        {
            _starter = Class.forName(className).newInstance();
        }
        catch(Exception ex)
        {
            dealException(new StarterException("0003", ex, new Object[] {
                className
            }), info);
        }
        if(_starter != null)
            if(_starter instanceof IStarter)
            {
                IStarter starter = (IStarter)_starter;
                try
                {
                    starter.start(info.getProps());
                    LOGGER.debug((new StringBuilder()).append("\u542F\u52A8[").append(className).append("]\u6210\u529F").toString());
                }
                catch(Exception ex)
                {
                    dealException(new StarterException("0005", ex, new Object[] {
                        className
                    }), info);
                }
            } else
            {
                dealException(new StarterException("0004", new Object[] {
                    className
                }), info);
            }
    }

    private static void dealException(StarterException ex, StarterConfigInfo info)
    {
        if(info.isIgnoreOnError())
            LOGGER.warn((new StringBuilder()).append("\u542F\u52A8\u5668[").append(info.getStarterClass()).append("]\u542F\u52A8\u5931\u8D25").toString(), ex);
        else
            throw ex;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/starter/StarterContext);
    public static final String STARTERCONFIGPARAM = "com.sinitek.base.starter.StarterContext.ConfigFile";
    public static final String DEFAULTCONFIGFILE = "starterconfig.xml";

}
