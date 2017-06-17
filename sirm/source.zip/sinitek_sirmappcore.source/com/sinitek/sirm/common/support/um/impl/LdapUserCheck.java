// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LdapUserCheck.java

package com.sinitek.sirm.common.support.um.impl;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.support.um.*;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.common.web.UserCheckUtils;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.*;
import com.sinitek.spirit.um.UserNameConflictException;
import com.sinitek.spirit.um.client.*;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LdapUserCheck extends BaseUserCheck
{

    public LdapUserCheck()
    {
    }

    public boolean afterCheckSession(UserCheckContext context)
    {
        return true;
    }

    public UserCheckResult logon(UserCheckContext context)
    {
        UserCheckResult _result = new UserCheckResult();
        String loginname = context.getUsername();
        String userpwd = context.getPassword();
        if(null != loginname && !"".equals(loginname) && null != userpwd && !"".equals(userpwd))
        {
            Map properties = new HashMap();
            properties.put("username", loginname);
            Properties env = new Properties();
            String principal = SettingUtils.getLocalSetting("ldap.principal");
            String username = principal == null ? "" : StringUtil.replaceVariables(principal, properties);
            String host = SettingUtils.getLocalSetting("ldap.host");
            String port = SettingUtils.getLocalSetting("ldap.port");
            String basedn = SettingUtils.getLocalSetting("ldap.basedn");
            StringBuilder ldapURL = new StringBuilder();
            if(host != null && basedn != null)
                ldapURL.append("ldap://").append(host).append(":").append(port != null ? port : "389").append("/").append(basedn);
            if(!"".equals(username) && !"".equals(ldapURL.toString()))
            {
                env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
                env.put("java.naming.security.authentication", "simple");
                env.put("java.naming.security.principal", username);
                env.put("java.naming.security.credentials", userpwd);
                env.put("java.naming.provider.url", ldapURL.toString());
                try
                {
                    Object displayname = null;
                    LdapContext ctx = new InitialLdapContext(env, null);
                    LOGGER.info((new StringBuilder()).append("\u767B\u5F55\u540D\u3010").append(loginname).append("\u3011\u7684\u7528\u6237LDAP\u767B\u9646\u6210\u529F").toString());
                    String filter = SettingUtils.getLocalSetting("ldap.filter");
                    String filters = filter == null ? "" : StringUtil.replaceVariables(filter, properties);
                    String displaynameattr = SettingUtils.getLocalSetting("ldap.displaynameattr");
                    if(StringUtils.isNotBlank(displaynameattr))
                    {
                        String returnedAtts[] = {
                            displaynameattr
                        };
                        if(!"".equals(filters) && returnedAtts.length > 0)
                        {
                            SearchControls constraints = new SearchControls();
                            constraints.setSearchScope(2);
                            if(returnedAtts != null && returnedAtts.length > 0)
                                constraints.setReturningAttributes(returnedAtts);
                            String searchbasedn = SettingUtils.getLocalSetting("ldap.searchbasedn");
                            if(StringUtils.isNotBlank(searchbasedn))
                            {
                                for(NamingEnumeration en = ctx.search(searchbasedn != null ? searchbasedn : "", filters, constraints); en.hasMore();)
                                {
                                    SearchResult sr = (SearchResult)en.next();
                                    displayname = sr.getAttributes().get(returnedAtts[0]).get();
                                }

                            }
                        } else
                        {
                            LOGGER.info("application.properties\u4E2D\u4FDD\u5B58\u7684\u3010ldap.filter\u3011LDAP\u53C2\u6570\u4E0D\u5B58\u5728\u6216\u672A\u914D\u7F6E");
                        }
                    } else
                    {
                        LOGGER.info("application.properties\u4E2D\u4FDD\u5B58\u7684\u3010ldap.displaynameattr\u3011LDAP\u53C2\u6570\u4E0D\u5B58\u5728\u6216\u672A\u914D\u7F6E");
                    }
                    UMClient client = UMFactory.getUMClient();
                    String userid = client.getUserByName(context.getUsername());
                    ISetting passwordSetting = CommonServiceFactory.getSettingService().getSetting("ORG", "PASSWORD");
                    if(passwordSetting != null && !"".equals(passwordSetting.getValue()))
                        context.setPassword(passwordSetting.getValue());
                    else
                        LOGGER.info("\u6A21\u5757\u4E3A\u3010ORG\u3011,\u53C2\u6570\u540D\u4E3A\u3010PASSWORD\u3011\u7684\u53C2\u6570\u4E0D\u5B58\u5728");
                    if(userid == null || "".equals(userid))
                    {
                        UserSession _userSession = client.getSession();
                        if(displayname != null)
                        {
                            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
                            IOrgFinder _finder = OrgFactory.getOrgFinder();
                            IEmployee employee = _updater.addEmployee(String.valueOf(displayname), "000", "", Boolean.valueOf(true));
                            try
                            {
                                Map map = new HashMap();
                                map.put("orgid", employee.getId());
                                userid = client.createUser(context.getUsername(), context.getPassword(), map, null);
                            }
                            catch(UserNameConflictException e)
                            {
                                _result.setResult(20);
                            }
                            if(!"".equals(userid) && userid != null)
                            {
                                _updater.updateEmployee(employee, String.valueOf(displayname), userid, "", Boolean.valueOf(true));
                                _updater.flush();
                            }
                            ISetting setting = CommonServiceFactory.getSettingService().getSetting("COMMON", "ORGDEFAULTPOSITION");
                            if(setting != null)
                            {
                                IUnit unit = (IUnit)_finder.getOrgObject(setting.getValue());
                                com.sinitek.spirit.org.core.IOrgObject orgObject = _finder.getOrgObject(employee.getId());
                                _updater.addRelationShip(unit, orgObject, com.sinitek.spirit.org.core.IRelationship.RelationshipType.SUPERVISION.toString());
                                _updater.flush();
                            } else
                            {
                                LOGGER.info("\u6A21\u5757\u540D\u3010COMMON\u3011\uFF0C\u53C2\u6570\u540D\u4E3A\u3010ORGDEFAULTPOSITION\u3011\u7684\u53C2\u6570\u4E0D\u5B58\u5728");
                            }
                            _result.setUserSession(_userSession);
                            _result.setSuccess(1);
                        } else
                        {
                            LOGGER.info("LDAP\u8FD4\u56DE\u7684displayname\u4E3A\u7A7A");
                            _result.setResult(10);
                        }
                    }
                    _result = UserCheckUtils.logonUser(context);
                    ctx.close();
                }
                catch(NamingException e)
                {
                    if(e instanceof CommunicationException)
                        _result.setResult(23);
                    else
                        _result.setResult(20);
                    LOGGER.info("ldap logon failed.", e);
                }
            } else
            {
                LOGGER.info("application.properties\u4E2D\u4FDD\u5B58\u7684\u3010ldap.principal\u3011\u6216\u3010ldap.host, ldap.port, ldap.basedn\u3011LDAP\u53C2\u6570\u4E0D\u5B58\u5728\u6216\u672A\u914D\u7F6E");
            }
        } else
        {
            _result.setResult(20);
        }
        return _result;
    }

    public boolean canChangePassword(UserCheckContext context)
    {
        return false;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/um/impl/LdapUserCheck);

}
