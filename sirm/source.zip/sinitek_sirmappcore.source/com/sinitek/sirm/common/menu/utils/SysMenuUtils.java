// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysMenuUtils.java

package com.sinitek.sirm.common.menu.utils;

import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.sirm.common.menu.utils:
//            SysMenuMetaDBPlugin

public class SysMenuUtils
{

    public SysMenuUtils()
    {
    }

    private static SysMenuMetaDBPlugin getSysMenuMetaDBPlugin()
    {
        if(sysMenuMetaDBPlugin == null)
            synchronized(com/sinitek/sirm/common/menu/utils/SysMenuUtils)
            {
                if(sysMenuMetaDBPlugin == null)
                {
                    SysMenuMetaDBPlugin plugin = new SysMenuMetaDBPlugin();
                    MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(plugin);
                    sysMenuMetaDBPlugin = plugin;
                }
            }
        return sysMenuMetaDBPlugin;
    }

    public static ISysMenu getSysMenuByRequest(HttpServletRequest request)
    {
        List sysMenus = findSysMenusByRequest(request);
        return sysMenus.size() <= 0 ? null : (ISysMenu)sysMenus.get(0);
    }

    public static List findSysMenusByRequest(HttpServletRequest request)
    {
        Map params = new HashMap();
        String path = request.getContextPath();
        String uri = request.getRequestURI();
        String pn;
        String pv;
        for(Enumeration e = request.getParameterNames(); e.hasMoreElements(); params.put(pn, pv))
        {
            pn = String.valueOf(e.nextElement());
            pv = request.getParameter(pn);
        }

        return findSysMenusByUrl(path, uri, params);
    }

    public static ISysMenu getSysMenuByUrl(String contextpath, String uri, Map params)
    {
        List result = findSysMenusByUrl(contextpath, uri, params);
        return result.size() <= 0 ? null : (ISysMenu)result.get(0);
    }

    public static List findSysMenusByUrl(String contextpath, String uri, Map params)
    {
        return getSysMenuMetaDBPlugin().findSysMenusByUrl(contextpath, uri, params);
    }

    private static SysMenuMetaDBPlugin sysMenuMetaDBPlugin = null;

}
