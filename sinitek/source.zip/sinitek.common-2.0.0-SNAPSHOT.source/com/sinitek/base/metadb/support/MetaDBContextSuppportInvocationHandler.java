// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextSuppportInvocationHandler.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.metadb.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IMethodInvocationInfo, GetMetaDBContextHelper, ContextInvocationInfoContainer, ContextInvocationInfo, 
//            TransactionType, IExceptionTranslator, IExceptionHandler, IMetaDBContextSupport

public class MetaDBContextSuppportInvocationHandler
    implements InvocationHandler
{

    public MetaDBContextSuppportInvocationHandler(List invocationInfos, IMetaDBContextSupport supportImpl, Class mainInterface)
    {
        invocationInfoMap = new Hashtable();
        this.invocationInfos = invocationInfos;
        this.supportImpl = supportImpl;
        mainMethodNames = new HashMap();
        Method methods[] = mainInterface.getMethods();
        for(int i = 0; i < methods.length; i++)
            mainMethodNames.put(methods[i].getName(), methods[i]);

    }

    public Object invoke(Object proxy, Method method, Object args[])
        throws Throwable
    {
        if(method.getName().equals("getMetaDBContext") && (args == null || args.length == 0))
            return GetMetaDBContextHelper.getMetaDBContext();
        if(!isMethodInMainInterface(method))
            return method.invoke(supportImpl, args);
        IMethodInvocationInfo invocationInfo = findInvocationInfo(method);
        ContextInvocationInfo his = ContextInvocationInfoContainer.getCurrentContext();
        if(his == null)
            his = ContextInvocationInfoContainer.createContext();
        his.getMethodStack().push(method);
        his.getCurrentInvocationInfoStack().push(invocationInfo);
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u5B9E\u73B0\u7C7B[").append(supportImpl.getClass().getName()).append("]\u7684[").append(method.getName()).append("]\u65B9\u6CD5\u5C06\u6309[").append(invocationInfo.getTransactionType().getEnumItemName()).append("]\u6A21\u5F0F\u5904\u7406\u4E8B\u52A1").toString());
        if(invocationInfo.getTransactionType() == TransactionType.TRANSACTION_NEW)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("\u72EC\u7ACB\u4E8B\u52A1\u6A21\u5F0F");
            IMetaDBContext context = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.CREATE_ONLY);
            his.markCurrentMethodOpenContext();
            his.getContextStack().push(context);
        } else
        if(invocationInfo.getTransactionType() == TransactionType.TRANSACTION_SUPPORT)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("\u975E\u72EC\u7ACB\u4E8B\u52A1\u6A21\u5F0F");
            IMetaDBContext context = MetaDBContextFactory.getInstance().createContext();
            his.markCurrentMethodOpenContext();
            his.getContextStack().push(context);
        } else
        if(invocationInfo.getTransactionType() == TransactionType.TRANSACTION_REQUIRED)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("\u5F3A\u5236\u975E\u72EC\u7ACB\u4E8B\u52A1\u6A21\u5F0F");
            IMetaDBContext context = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.CURRENT_ONLY);
            his.markCurrentMethodOpenContext();
            his.getContextStack().push(context);
        } else
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("\u65E0\u4E8B\u52A1\u6A21\u5F0F");
        try
        {
            Object obj;
            try
            {
                Object ret = method.invoke(supportImpl, args);
                if(his.getContextStack().size() > 0 && his.isCurrentMethodOpenContext())
                {
                    boolean isNeedCommit = false;
                    IMetaDBContext context = (IMetaDBContext)his.getContextStack().peek();
                    if(invocationInfo.getTransactionType() == TransactionType.TRANSACTION_NEW)
                    {
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug("\u72EC\u7ACB\u4E8B\u52A1\u6A21\u5F0F\uFF0C\u51C6\u5907\u63D0\u4EA4\u4E8B\u52A1");
                        isNeedCommit = true;
                    } else
                    if(his.getContextStack().size() == 1)
                    {
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug("\u9876\u5C42\u8C03\u7528\u51FD\u6570\u4E14\u5DF2\u7ECF\u6253\u5F00MetaDBContext\uFF0C\u53EF\u80FD\u9700\u8981\u63D0\u4EA4\u4E8B\u52A1");
                        isNeedCommit = true;
                    }
                    if(isNeedCommit)
                        context.commit();
                }
                obj = ret;
            }
            catch(Exception ex)
            {
                LOGGER.warn((new StringBuilder()).append("\u5B9E\u73B0\u7C7B[").append(supportImpl.getClass().getName()).append("]\u7684[").append(method.getName()).append("]\u65B9\u6CD5\u629B\u51FA\u5F02\u5E38").toString(), ex);
                Throwable origEx = ex;
                if(ex instanceof InvocationTargetException)
                    origEx = ((InvocationTargetException)ex).getTargetException();
                if(his.getContextStack().size() > 0 && his.isCurrentMethodOpenContext())
                {
                    IMetaDBContext context = (IMetaDBContext)his.getContextStack().peek();
                    if(invocationInfo.getTransactionType() == TransactionType.TRANSACTION_NEW)
                    {
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug("\u72EC\u7ACB\u4E8B\u52A1\u6A21\u5F0F\uFF0C\u51C6\u5907\u56DE\u6EDA\u4E8B\u52A1");
                        context.rollback();
                    } else
                    if(his.getContextStack().size() == 1)
                    {
                        callExceptionHandler(invocationInfo.getExceptionHandlers(), origEx);
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug("\u9876\u5C42\u8C03\u7528\u51FD\u6570\u4E14\u5DF2\u7ECF\u6253\u5F00MetaDBContext\uFF0C\u51C6\u5907\u56DE\u6EDA\u4E8B\u52A1");
                        context.rollback();
                    }
                }
                throw invocationInfo.getExceptionTranslator().translate(method, args, origEx);
            }
            return obj;
        }
        finally
        {
            boolean isNeedCloss = his.isCurrentMethodOpenContext();
            his.getMethodStack().pop();
            his.getCurrentInvocationInfoStack().pop();
            if(isNeedCloss && his.getContextStack().size() > 0)
            {
                IMetaDBContext context = (IMetaDBContext)his.getContextStack().pop();
                context.close();
            }
            if(his.getMethodStack().isEmpty())
                ContextInvocationInfoContainer.clear();
        }
    }

    private void callExceptionHandler(IExceptionHandler handlerList[], Throwable ex)
    {
        for(int i = 0; i < handlerList.length; i++)
        {
            IExceptionHandler handler = handlerList[i];
            try
            {
                handler.onException(ex);
            }
            catch(Exception e)
            {
                LOGGER.warn((new StringBuilder()).append("\u8C03\u7528\u5F02\u5E38\u5904\u7406\u5668[").append(handlerList[i].getClass().getName()).append("]\u53D1\u751F\u5F02\u5E38").toString(), e);
            }
        }

    }

    private boolean isMethodInMainInterface(Method method)
    {
        return mainMethodNames.containsKey(method.getName());
    }

    private IMethodInvocationInfo findInvocationInfo(Method method)
    {
        IMethodInvocationInfo info;
label0:
        {
            info = (IMethodInvocationInfo)invocationInfoMap.get(method.getName());
            if(info != null)
                break label0;
            Iterator iter = invocationInfos.iterator();
            IMethodInvocationInfo _info;
            String pattern;
            do
            {
                if(!iter.hasNext())
                    break label0;
                _info = (IMethodInvocationInfo)iter.next();
                pattern = _info.getMethodNamePattern();
                pattern = pattern.replaceAll("[?]", ".");
                pattern = pattern.replaceAll("[*]", ".*");
            } while(!Pattern.matches(pattern, method.getName()));
            info = _info;
            invocationInfoMap.put(method.getName(), info);
        }
        return info;
    }

    private List invocationInfos;
    private IMetaDBContextSupport supportImpl;
    private Map mainMethodNames;
    private static final Logger LOGGER;
    private Map invocationInfoMap;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
