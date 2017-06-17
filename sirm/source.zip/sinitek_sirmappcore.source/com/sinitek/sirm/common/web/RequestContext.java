// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestContext.java

package com.sinitek.sirm.common.web;

import com.sinitek.base.metadb.*;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.function.entity.IFunctionGroup;
import com.sinitek.sirm.common.function.entity.IFunctionInfo;
import com.sinitek.sirm.common.function.utils.FunctionInfoUtils;
import com.sinitek.sirm.common.menu.utils.SysMenuUtils;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.support.thread.ThreadPoolManager;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.um.entity.IUserUIConfig;
import com.sinitek.sirm.common.um.enums.UserUIConfigCatalog;
import com.sinitek.sirm.common.um.service.IUserUIConfigService;
import com.sinitek.sirm.common.utils.LogOperateType;
import com.sinitek.spirit.businlogger.BusinLoggerFactory;
import com.sinitek.spirit.businlogger.IBusinLogger;
import com.sinitek.spirit.um.InvalidSessionException;
import com.sinitek.spirit.um.NoSuchUserException;
import com.sinitek.spirit.um.client.*;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.*;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.web:
//            RequestMonitor

public class RequestContext
{
    private static class BusinLoggerSaver
        implements Runnable
    {

        public void run()
        {
            (new MetaDBTemplate()).callExecutor(new IMetaDBTemplateExecutor() {

                public void doInTransaction(IMetaDBContext ctx)
                {
                    IBusinLogger logger;
                    for(Iterator i$ = loggers.iterator(); i$.hasNext(); logger.save())
                    {
                        logger = (IBusinLogger)i$.next();
                        if(logger.getEndTime() == null)
                            logger.setEndTime(new Date());
                        int len = StringUtils.isNotBlank(logger.getDescription()) ? logger.getDescription().length() : 0;
                        if(len >= 1300)
                            logger.setDescription((new StringBuilder()).append(logger.getDescription().substring(0, 1300)).append("...").toString());
                    }

                    loggers.clear();
                    loggers = null;
                }

                final BusinLoggerSaver this$0;

                
                {
                    this$0 = BusinLoggerSaver.this;
                    super();
                }
            }
);
        }

        private List loggers;



        public BusinLoggerSaver(List loggers)
        {
            this.loggers = new ArrayList();
            this.loggers = loggers;
        }
    }


    private RequestContext()
    {
        userSession = null;
        currentUser = null;
        request = null;
        logbuffer = new ArrayList();
    }

    public static void begin()
    {
        requestContext.set(new RequestContext());
    }

    public static void end()
    {
        if(requestContext != null)
            requestContext.remove();
        RequestMonitor.removeRequest();
    }

    public static RequestUser getCurrentUser()
    {
        RequestUser result = null;
        if(requestContext != null)
        {
            RequestContext rc = (RequestContext)requestContext.get();
            if(rc != null)
            {
                if(rc.currentUser == null && rc.userSession != null)
                {
                    UMClient _client = UMFactory.getUMClient();
                    try
                    {
                        String _userId = rc.userSession.getUserId();
                        String _username = _client.getUserName(_userId);
                        Map _props = _client.getUserProperties(_userId);
                        rc.currentUser = new RequestUser();
                        rc.currentUser.setUserId(_userId);
                        rc.currentUser.setName(_username);
                        rc.currentUser.setUserProperties(_props);
                    }
                    catch(InvalidSessionException e)
                    {
                        LOGGER.warn("getCurrentUser failed!", e);
                    }
                    catch(NoSuchUserException e)
                    {
                        LOGGER.warn("getCurrentUser failed!", e);
                    }
                }
                result = rc.currentUser;
                return result;
            }
        }
        return result;
    }

    public static UserSession getUserSession()
    {
        UserSession result = null;
        if(requestContext != null)
        {
            RequestContext rc = (RequestContext)requestContext.get();
            result = rc.userSession;
        }
        return result;
    }

    public static void setUserSession(UserSession userSession)
    {
        if(requestContext != null)
        {
            RequestContext rc = (RequestContext)requestContext.get();
            rc.userSession = userSession;
            UMClient _client = UMFactory.getUMClient();
            try
            {
                String _userId = rc.userSession.getUserId();
                String _username = _client.getUserName(_userId);
                Map _props = _client.getUserProperties(_userId);
                rc.currentUser = new RequestUser();
                rc.currentUser.setUserId(_userId);
                rc.currentUser.setName(_username);
                rc.currentUser.setUserProperties(_props);
            }
            catch(InvalidSessionException e)
            {
                LOGGER.warn("getCurrentUser failed!", e);
            }
            catch(NoSuchUserException e)
            {
                LOGGER.warn("getCurrentUser failed!", e);
            }
        }
    }

    public static void setRequest(HttpServletRequest request)
    {
        if(requestContext != null)
        {
            RequestContext rc = (RequestContext)requestContext.get();
            rc.request = request;
        }
        Map map = new HashMap();
        map.put("starttime", new Date());
        map.put("url", request.getRequestURL());
        RequestMonitor.putRequest(map);
    }

    public static HttpServletRequest getRequest()
    {
        RequestContext rc = (RequestContext)requestContext.get();
        return rc != null ? rc.request : null;
    }

    public static String getRequestLocale()
    {
        return getRequest().getLocale().toString();
    }

    public static String getUserLocale()
    {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession(true);
        String locale = getRequest().getParameter("locale");
        if(locale == null && StringUtils.isNotBlank(locale))
        {
            session.setAttribute("locale", locale);
            return locale;
        }
        locale = (String)session.getAttribute("locale");
        if(locale == null)
        {
            try
            {
                String orgId = getCurrentUser().getOrgId();
                if(orgId != null)
                {
                    IUserUIConfig userUIConfig = configService.get(orgId, "LANGUAGE", UserUIConfigCatalog.I18N);
                    if(userUIConfig != null)
                    {
                        locale = userUIConfig.getValue();
                        if(locale != null)
                        {
                            session.setAttribute("locale", locale);
                            return locale;
                        }
                    }
                }
            }
            catch(Exception e) { }
            if(locale == null)
            {
                locale = getRequestLocale();
                return locale.toString();
            }
        }
        return locale;
    }

    public static void setUserLocale(String locale)
    {
        try
        {
            String orgId = getCurrentUser().getOrgId();
            IUserUIConfig userUIConfig = configService.get(orgId, "LANGUAGE", UserUIConfigCatalog.I18N);
            if(userUIConfig != null)
            {
                userUIConfig.setValue(locale);
                configService.save(userUIConfig);
            } else
            {
                configService.save(orgId, "LANGUAGE", locale, UserUIConfigCatalog.I18N, null);
            }
        }
        catch(Exception e) { }
        getRequest().getSession(true).setAttribute("locale", locale);
    }

    public static String getUserTheme()
    {
        String theme = SettingUtils.getLocalSetting("defaulttheme");
        theme = theme != null ? theme : "default";
        if(getCurrentUser() == null)
            return theme;
        HttpSession session = getRequest().getSession(true);
        Object sessionTheme = session.getAttribute("userTheme");
        if(sessionTheme == null || StringUtils.isNotBlank(sessionTheme.toString()))
        {
            IUserUIConfig config = getUserThemeConfig();
            if(config != null && StringUtils.isNotBlank(config.getValue()))
                theme = config.getValue();
            getRequest().getSession(true).setAttribute("userTheme", theme);
        } else
        {
            theme = sessionTheme.toString();
        }
        return theme;
    }

    public static IUserUIConfig getUserThemeConfig()
    {
        try
        {
            String orgId = getCurrentUser().getOrgId();
            return configService.get(orgId, "THEME", UserUIConfigCatalog.THEME);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static void setTheme(String theme)
    {
        if(StringUtils.isNotBlank(theme))
        {
            String orgId = getCurrentUser().getOrgId();
            IUserUIConfig userUIConfig = getUserThemeConfig();
            if(userUIConfig != null)
            {
                userUIConfig.setValue(theme);
                configService.save(userUIConfig);
            } else
            {
                configService.save(orgId, "THEME", theme, UserUIConfigCatalog.THEME, null);
            }
            getRequest().getSession(true).setAttribute("userTheme", theme);
        }
    }

    public static IBusinLogger log(HttpServletRequest request)
    {
        IBusinLogger logger = null;
        RequestContext rc = (RequestContext)requestContext.get();
        if(rc != null)
        {
            UserSession us = rc.userSession;
            if(us != null)
            {
                String userId = null;
                try
                {
                    userId = us.getUserId();
                }
                catch(Exception ex)
                {
                    LOGGER.error("getUserId failed.", ex);
                }
                if(userId != null)
                {
                    ISysMenu sysMenu = SysMenuUtils.getSysMenuByRequest(request);
                    if(sysMenu != null)
                    {
                        BusinLoggerFactory factory = BusinLoggerFactory.getLogger(userId, rc.request);
                        logger = factory.getBusinLogger();
                        logger.setStartTime(new Date());
                        logger.setModuleName("\u83DC\u5355");
                        logger.setDescription((new StringBuilder()).append("\u8BBF\u95EE[").append(sysMenu.getName()).append("]!").toString());
                        logger.setOperateType(Integer.valueOf(6));
                        rc.logbuffer.add(logger);
                    } else
                    {
                        IFunctionInfo funcInfo = FunctionInfoUtils.getFunctionInfoByRequest(request);
                        if(funcInfo != null)
                        {
                            IFunctionGroup functionGroup = FunctionInfoUtils.getFunctionGroupById(funcInfo.getGroupId().intValue());
                            BusinLoggerFactory factory = BusinLoggerFactory.getLogger(userId, rc.request);
                            logger = factory.getBusinLogger();
                            logger.setStartTime(new Date());
                            logger.setModuleName(functionGroup.getName());
                            logger.setDescription((new StringBuilder()).append("\u8BBF\u95EE[").append(funcInfo.getName()).append("]\u529F\u80FD!").toString());
                            logger.setOperateType(Integer.valueOf(0));
                            rc.logbuffer.add(logger);
                        }
                    }
                }
            }
        }
        return logger;
    }

    public static IBusinLogger logAction(String action, String method)
    {
        IBusinLogger logger = null;
        RequestContext rc = (RequestContext)requestContext.get();
        if(rc != null)
        {
            UserSession us = rc.userSession;
            if(us != null)
            {
                IFunctionInfo funcInfo = FunctionInfoUtils.getFunctionInfoByActionMethod(action, method);
                if(funcInfo != null)
                {
                    IFunctionGroup functionGroup = FunctionInfoUtils.getFunctionGroupById(funcInfo.getGroupId().intValue());
                    String userId = null;
                    try
                    {
                        userId = us.getUserId();
                    }
                    catch(Exception ex)
                    {
                        LOGGER.error("getUserId failed.", ex);
                    }
                    if(userId != null)
                    {
                        BusinLoggerFactory factory = BusinLoggerFactory.getLogger(userId, rc.request);
                        logger = factory.getBusinLogger();
                        logger.setStartTime(new Date());
                        logger.setModuleName(functionGroup.getName());
                        logger.setDescription((new StringBuilder()).append("\u6267\u884C[").append(funcInfo.getName()).append("]\u64CD\u4F5C!").toString());
                        logger.setOperateType(Integer.valueOf(0));
                        rc.logbuffer.add(logger);
                    }
                }
            }
        }
        return logger;
    }

    public static IBusinLogger log(String modulename, int operateType, String desc)
    {
        IBusinLogger logger = null;
        RequestContext rc = (RequestContext)requestContext.get();
        if(rc != null)
        {
            String userId = null;
            try
            {
                userId = rc.userSession == null ? null : rc.userSession.getUserId();
            }
            catch(Exception ex)
            {
                LOGGER.error("getUserId failed.", ex);
            }
            if(userId != null)
            {
                BusinLoggerFactory factory = BusinLoggerFactory.getLogger(userId, rc.request);
                logger = factory.getBusinLogger();
                logger.setModuleName(modulename);
                logger.setDescription(desc);
                logger.setOperateType(Integer.valueOf(operateType));
                logger.setStartTime(new Date());
                rc.logbuffer.add(logger);
            }
        }
        return logger;
    }

    public static IBusinLogger log(String userId, String modulename, int operateType, String desc)
    {
        IBusinLogger logger = null;
        RequestContext rc = (RequestContext)requestContext.get();
        if(rc != null && userId != null)
        {
            BusinLoggerFactory factory = BusinLoggerFactory.getLogger(userId, rc.request);
            logger = factory.getBusinLogger();
            logger.setModuleName(modulename);
            logger.setDescription(desc);
            logger.setOperateType(Integer.valueOf(operateType));
            logger.setStartTime(new Date());
            rc.logbuffer.add(logger);
        }
        return logger;
    }

    public static IBusinLogger logInsert(Object object)
    {
        IBusinLogger logger = null;
        if(object != null && (object instanceof IMetaObject))
        {
            IMetaObject mo = (IMetaObject)object;
            IEntity e = mo.getEntity();
            String modulename = mo.getEntityName();
            int op = LogOperateType.INSERT;
            StringBuffer desc = new StringBuffer();
            desc.append((new StringBuilder()).append("\u65B0\u5EFA[").append(e.getEntityName()).append("][").append(e.getEntityInfo()).append("],\u8BB0\u5F55ID:").append(mo.getId()).toString()).append("\n");
            desc.append(getMetaObjectString(mo));
            logger = log(modulename, op, desc.toString());
        }
        return logger;
    }

    public static IBusinLogger logUpdate(Object object)
    {
        IBusinLogger logger = null;
        if(object != null && (object instanceof IMetaObject))
        {
            IMetaObject mo = (IMetaObject)object;
            IEntity e = mo.getEntity();
            String modulename = mo.getEntity().getEntityInfo();
            int op = LogOperateType.UPDATE;
            StringBuffer desc = new StringBuffer();
            desc.append((new StringBuilder()).append("\u66F4\u65B0[").append(e.getEntityName()).append("][").append(e.getEntityInfo()).append("],\u8BB0\u5F55ID:").append(mo.getId()).toString()).append("\n");
            desc.append(getUpdateMetaObjectString(mo));
            logger = log(modulename, op, desc.toString());
        }
        return logger;
    }

    public static IBusinLogger logDelete(Object object)
    {
        IBusinLogger logger = null;
        if(object != null && (object instanceof IMetaObject))
        {
            IMetaObject mo = (IMetaObject)object;
            IEntity e = mo.getEntity();
            String modulename = mo.getEntityName();
            int op = LogOperateType.DELETE;
            StringBuffer desc = new StringBuffer();
            desc.append((new StringBuilder()).append("\u5220\u9664[").append(e.getEntityName()).append("][").append(e.getEntityInfo()).append("],\u8BB0\u5F55ID:").append(mo.getId()).toString()).append("\n");
            desc.append(getMetaObjectString(mo));
            logger = log(modulename, op, desc.toString());
        }
        return logger;
    }

    public static void savelogs()
    {
        List buffer = requestContext.get() == null ? null : ((RequestContext)requestContext.get()).logbuffer;
        List logs = buffer != null ? ((List) (new ArrayList(buffer))) : ((List) (new ArrayList()));
        BusinLoggerSaver saver = new BusinLoggerSaver(logs);
        if(buffer != null)
            buffer.clear();
        ThreadPoolManager.getExecutorService("businloggersaver").submit(saver);
    }

    private static String getUpdateMetaObjectString(IMetaObject mo)
    {
        StringBuffer sb = new StringBuffer();
        IMetaObjectPropertyUpdateInfo updateInfo = mo.getUpdateInfo();
        IProperty properties[] = updateInfo.getUpdatedProperties();
        IProperty arr$[] = properties;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            IProperty prop = arr$[i$];
            boolean _ignore = ignoreMetaObjectProperty(prop);
            com.sinitek.base.metadb.IMetaObjectPropertyUpdateInfo.IOrigValueBean valueBean = updateInfo.getOrigValueBean(prop);
            sb.append("[");
            sb.append((new StringBuilder()).append(prop.getPropertyName()).append(":").toString());
            if(!_ignore)
            {
                Object v1 = getMetaObjectPropValue(prop, valueBean.getOrigValue());
                Object v2 = getMetaObjectPropValue(prop, valueBean.getCurrentValue());
                sb.append((new StringBuilder()).append(v1).append("->").append(v2).toString());
            } else
            {
                sb.append("blob\u6216clob\u5FFD\u7565\u68C0\u67E5");
            }
            sb.append("]");
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String getMetaObjectString(IMetaObject mo)
    {
        StringBuffer sb = new StringBuffer();
        List properties = mo.getEntity().getProperties();
        Iterator i$ = properties.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IProperty prop = (IProperty)i$.next();
            String propName = prop.getPropertyName();
            boolean _ignore = ignoreMetaObjectProperty(prop);
            if(!_ignore)
            {
                boolean _meta = StringUtils.equalsIgnoreCase(propName, "ID") || StringUtils.equalsIgnoreCase(propName, "CREATETIMESTAMP") || StringUtils.equalsIgnoreCase(propName, "ENTITYNAME") || StringUtils.equalsIgnoreCase(propName, "UPDATETIMESTAMP") || StringUtils.equalsIgnoreCase(propName, "VERSION");
                if(!_meta)
                {
                    sb.append("[").append((new StringBuilder()).append(propName).append(":").toString());
                    Object v = getMetaObjectPropValue(prop, mo.get(prop.getPropertyName()));
                    sb.append(v);
                    sb.append("]\n");
                }
            } else
            {
                sb.append("[").append((new StringBuilder()).append(propName).append(":").toString());
                sb.append("blob\u6216clob\u5FFD\u7565\u68C0\u67E5");
                sb.append("]\n");
            }
        } while(true);
        return sb.toString();
    }

    private static boolean ignoreMetaObjectProperty(IProperty prop)
    {
        String type = prop.getPropertyType().getEnumItemName();
        boolean _isok = StringUtils.equalsIgnoreCase(type, "stream") || StringUtils.equalsIgnoreCase(type, "clob");
        return _isok;
    }

    private static Object getMetaObjectPropValue(IProperty prop, Object value)
    {
        Object _result = null;
        if(value != null)
        {
            String type = prop.getPropertyType().getEnumItemName();
            boolean _isok = StringUtils.equalsIgnoreCase(type, "string");
            if(_isok)
            {
                String str = String.valueOf(value);
                _result = str.length() <= 100 ? ((Object) (str)) : ((Object) ((new StringBuilder()).append(str.substring(0, 100)).append("...(\u622A\u65AD\u5904\u7406)").toString()));
            } else
            {
                _result = value;
            }
        }
        return _result;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/web/RequestContext);
    private static ThreadLocal requestContext = new ThreadLocal();
    private static final IUserUIConfigService configService = CommonServiceFactory.getUserUIConfigService();
    private UserSession userSession;
    private RequestUser currentUser;
    private HttpServletRequest request;
    private List logbuffer;

}
