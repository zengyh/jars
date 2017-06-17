// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionInfoUtils.java

package com.sinitek.sirm.common.function.utils;

import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.sirm.common.function.entity.IFunctionGroup;
import com.sinitek.sirm.common.function.entity.IFunctionInfo;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.sirm.common.function.utils:
//            FunctionInfoMetaDBPlugin, FunctionGroupMetaDBPlugin

public class FunctionInfoUtils
{

    public FunctionInfoUtils()
    {
    }

    private static FunctionInfoMetaDBPlugin getFuncInfoMetaDBPlugin()
    {
        if(funcInfoMetaDBPlugin == null)
            synchronized(com/sinitek/sirm/common/function/utils/FunctionInfoUtils)
            {
                if(funcInfoMetaDBPlugin == null)
                {
                    FunctionInfoMetaDBPlugin plugin = new FunctionInfoMetaDBPlugin();
                    MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(plugin);
                    funcInfoMetaDBPlugin = plugin;
                }
            }
        return funcInfoMetaDBPlugin;
    }

    private static FunctionGroupMetaDBPlugin getFunctionGroupMetaDBPlugin()
    {
        if(funcGroupMetaDBPlugin == null)
            synchronized(com/sinitek/sirm/common/function/utils/FunctionInfoUtils)
            {
                if(funcGroupMetaDBPlugin == null)
                {
                    FunctionGroupMetaDBPlugin plugin = new FunctionGroupMetaDBPlugin();
                    MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(plugin);
                    funcGroupMetaDBPlugin = plugin;
                }
            }
        return funcGroupMetaDBPlugin;
    }

    public static IFunctionInfo getFunctionInfoByActionMethod(String action, String method)
    {
        return getFuncInfoMetaDBPlugin().getFunctionInfoByActionMethod(action, method);
    }

    public static IFunctionInfo getFunctionInfoByRequest(HttpServletRequest request)
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

        return getFunctionInfoByUrl(path, uri, params);
    }

    public static IFunctionInfo getFunctionInfoByUrl(String contextpath, String uri, Map params)
    {
        IFunctionInfo result = getFuncInfoMetaDBPlugin().getFunctionInfoByUrl(contextpath, uri, params);
        return result;
    }

    public static IFunctionInfo getFunctionInfoByCode(String code)
    {
        return getFuncInfoMetaDBPlugin().getFunctionInfoByCode(code);
    }

    public static IFunctionGroup getFunctionGroupById(int groupId)
    {
        return getFunctionGroupMetaDBPlugin().getFunctionGroup(groupId);
    }

    private static FunctionInfoMetaDBPlugin funcInfoMetaDBPlugin = null;
    private static FunctionGroupMetaDBPlugin funcGroupMetaDBPlugin = null;

}
