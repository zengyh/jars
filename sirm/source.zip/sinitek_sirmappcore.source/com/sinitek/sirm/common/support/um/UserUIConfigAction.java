// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUIConfigAction.java

package com.sinitek.sirm.common.support.um;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.um.entity.IUserUIConfig;
import com.sinitek.sirm.common.um.enums.UserUIConfigCatalog;
import com.sinitek.sirm.common.um.service.IUserUIConfigService;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.spirit.webcontrol.userconfig.ISpiritUserConfigAware;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UserUIConfigAction
    implements ISpiritUserConfigAware
{

    public UserUIConfigAction()
    {
    }

    public Integer loadPageSize(String tableId, String className, HttpServletRequest request)
    {
        if(StringUtils.isBlank(tableId) || StringUtils.isBlank(className))
            return Integer.valueOf(15);
        IUserUIConfigService service = CommonServiceFactory.getUserUIConfigService();
        try
        {
            IUserUIConfig config = null;
            if(RequestContext.getCurrentUser() != null)
            {
                config = service.get(RequestContext.getCurrentUser().getOrgId(), tableId, UserUIConfigCatalog.TABLE_PAGE_SIZE, Class.forName(className));
                request.getSession().setAttribute((new StringBuilder()).append(className).append(">").append(tableId).toString(), config);
            }
            return Integer.valueOf(config == null ? 15 : Integer.parseInt(config.getValue()));
        }
        catch(ClassNotFoundException e)
        {
            LOGGER.error("loadpagesize failed.", e);
        }
        return Integer.valueOf(15);
    }

    public void savePageSize(String tableId, String className, Integer pageSize, HttpServletRequest request)
    {
        if(pageSize == null || StringUtils.isBlank(tableId) || StringUtils.isBlank(className))
            return;
        IUserUIConfigService service = CommonServiceFactory.getUserUIConfigService();
        try
        {
            IUserUIConfig config = (IUserUIConfig)request.getSession().getAttribute((new StringBuilder()).append(className).append(">").append(tableId).toString());
            if(config == null)
            {
                if(RequestContext.getCurrentUser() != null)
                    service.save(RequestContext.getCurrentUser().getOrgId(), tableId, pageSize.toString(), UserUIConfigCatalog.TABLE_PAGE_SIZE, Class.forName(className));
            } else
            {
                config.setValue(pageSize.toString());
                service.save(config);
            }
        }
        catch(ClassNotFoundException e)
        {
            LOGGER.error("savepagesize failed.", e);
        }
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/um/UserUIConfigAction);

}
