// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReloadTimerTask.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.ILocalReloadEventListener;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import java.util.TimerTask;
import org.apache.log4j.Logger;

public class ReloadTimerTask extends TimerTask
{

    public ReloadTimerTask(ILocalReloadEventListener listener, String targetEntitys[])
    {
        this.listener = listener;
        this.targetEntitys = targetEntitys;
    }

    public void run()
    {
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8C03\u7528\u672C\u5730\u7F13\u5B58\u5237\u65B0\u5668[").append(listener.getClass().getName()).append("]").toString());
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5237\u65B0\u672C\u5730\u5B9E\u4F53\u4E3B\u952E\u7F13\u5B58\uFF0C\u5171\u6D89\u53CA[").append(targetEntitys.length).append("]\u4E2A\u5B9E\u4F53").toString());
        }
        for(int i = 0; i < targetEntitys.length; i++)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5237\u65B0\u672C\u5730\u5B9E\u4F53[").append(targetEntitys[i]).append("]\u7684\u7F13\u5B58").toString());
            IEntity entity = MetaDBContextFactory.getInstance().getEntity(targetEntitys[i]);
            if(entity.isIdCacheSupport())
                MetaDBContextFactory.getInstance().getCacheContext().reloadEntityCache(entity);
            else
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u672C\u5730\u5B9E\u4F53[").append(targetEntitys[i]).append("]\u672A\u5F00\u542F\u7F13\u5B58\uFF0C\u65E0\u9700\u5237\u65B0").toString());
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u5237\u65B0\u672C\u5730\u5B9E\u4F53[").append(targetEntitys[i]).append("]\u7684\u7F13\u5B58\u5B8C\u6210").toString());
        }

        try
        {
            listener.reload();
            LOGGER.debug((new StringBuilder()).append("\u8C03\u7528\u672C\u5730\u7F13\u5B58\u5237\u65B0\u5668[").append(listener.getClass().getName()).append("]\u6210\u529F").toString());
        }
        catch(Exception ex)
        {
            LOGGER.error((new StringBuilder()).append("\u8C03\u7528\u672C\u5730\u7F13\u5B58\u5237\u65B0\u5668[").append(listener.getClass().getName()).append("]\u5931\u8D25").toString(), ex);
        }
    }

    private static final Logger LOGGER;
    private ILocalReloadEventListener listener;
    private String targetEntitys[];

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }
}
