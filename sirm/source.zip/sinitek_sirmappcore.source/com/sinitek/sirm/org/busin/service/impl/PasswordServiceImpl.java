// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PasswordServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.sirm.org.utils.OrgUtils;
import com.sinitek.spirit.um.client.*;
import java.util.*;
import org.apache.log4j.Logger;

public class PasswordServiceImpl extends MetaDBContextSupport
    implements IPasswordService
{

    public PasswordServiceImpl()
    {
        LOGGER = Logger.getLogger(getClass());
    }

    public void savePswHis(IOrgPswHis pswHis)
    {
        pswHis.save();
    }

    public void savePswLog(IOrgLogonLog logonLog)
    {
        logonLog.save();
    }

    public void savePswLog(String orgid)
    {
        List logs = getMetaDBContext().createQuery(com/sinitek/sirm/org/busin/entity/IOrgLogonLog, (new StringBuilder()).append(" orgid = ").append(orgid).toString()).getResult();
        IOrgLogonLog logonLog = new OrgLogonLogImpl();
        if(null != logs && logs.size() > 0)
            logonLog = (IOrgLogonLog)logs.get(0);
        logonLog.setLogDate(new Date());
        logonLog.setOrgid(orgid);
        logonLog.setFailedCount(Integer.valueOf(NumberTool.safeToInteger(logonLog.getFailedCount(), Integer.valueOf(0)).intValue() + 1));
        savePswLog(logonLog);
    }

    public IOrgLogonLog getLastPswLog(String orgid)
    {
        List logs = getMetaDBContext().createQuery(com/sinitek/sirm/org/busin/entity/IOrgLogonLog, (new StringBuilder()).append(" orgid = ").append(orgid).toString()).getResult();
        if(null != logs && logs.size() > 0)
            return (IOrgLogonLog)logs.get(0);
        else
            return null;
    }

    public int pswRepeatCheck(String orgid, String psw)
    {
        EncryptUtil encryptUtil = new EncryptUtil();
        int orgsirm003 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM003"), Integer.valueOf(0)).intValue();
        IMetaDBQuery metaDBQuery = getMetaDBContext().createQuery(com/sinitek/sirm/org/busin/entity/IOrgPswHis, "orgid = :orgid");
        metaDBQuery.setFirstResult(0);
        metaDBQuery.setMaxResult(orgsirm003);
        metaDBQuery.setParameter("orgid", orgid);
        metaDBQuery.setDefaultOrderBy("logdate desc");
        List pswHises = metaDBQuery.getResult();
        for(Iterator i$ = pswHises.iterator(); i$.hasNext();)
        {
            IOrgPswHis orgPswHis = (IOrgPswHis)i$.next();
            if(orgPswHis.getPassword().trim().equals(encryptUtil.encodeMD5(psw.trim())))
                return 1;
        }

        return 0;
    }

    public int pswErrorCount(String orgid)
    {
        IMetaDBQuery metaDBQuery = getMetaDBContext().createQuery(com/sinitek/sirm/org/busin/entity/IOrgLogonLog, "orgid = :orgid");
        metaDBQuery.setParameter("orgid", orgid);
        List logonLogs = metaDBQuery.getResult();
        if(logonLogs.size() > 0)
        {
            IOrgLogonLog logonLog = (IOrgLogonLog)logonLogs.get(0);
            return NumberTool.safeToInteger(logonLog.getFailedCount(), Integer.valueOf(0)).intValue();
        } else
        {
            return 0;
        }
    }

    public String lockUserCheck(String username)
    {
        UMClient client;
        IOrgService orgService;
        Employee employee;
        String orgid;
        int errorcount;
        Date logonLogDate;
        int orgsirm011;
        client = UMFactory.getUMClient();
        String userid = client.getUserByName(username);
        if(null == userid)
            return "\u5F53\u524D\u7528\u6237\u4E0D\u5B58\u5728\uFF01";
        orgService = OrgServiceFactory.getOrgService();
        employee = orgService.getEmployeeByUserId(userid);
        orgid = employee.getId();
        errorcount = pswErrorCount(orgid);
        logonLogDate = null;
        if(null != getLastPswLog(orgid))
            logonLogDate = getLastPswLog(orgid).getLogDate();
        savePswLog(orgid);
        orgsirm011 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM011"), Integer.valueOf(0)).intValue();
        boolean locked = client.isUserLocked(employee.getUserId());
        if(!locked || orgsirm011 <= 0)
            break MISSING_BLOCK_LABEL_435;
        LOGGER.info("\u5F53\u524D\u7528\u6237\u88AB\u9501\u5B9A");
        Date d1 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(TimeUtil.safeToDate(employee.getUserLockTime(), "yyyy-MM-dd HH:mm:ss", new Date()));
        cal.set(12, cal.get(12) + orgsirm011);
        Date d2 = cal.getTime();
        if(d1.before(d2))
        {
            Double time = Double.valueOf(NumberTool.safeToDouble(Long.valueOf(d2.getTime() - d1.getTime()), Double.valueOf(0.0D)).doubleValue() / 60000D);
            LOGGER.info((new StringBuilder()).append("\u8FD8\u6709").append(time).append("\u5206\u949F\u7528\u6237\u5373\u5C06\u89E3\u9501").toString());
            if(time.doubleValue() > 0.0D && time.doubleValue() < 1.0D)
                time = Double.valueOf(1.0D);
            return (new StringBuilder()).append("\u7528\u6237").append(username).append("\u5DF2\u9501\u5B9A\uFF0C").append((new Double(Math.ceil(time.doubleValue()))).intValue()).append("\u5206\u949F\u540E\u81EA\u52A8\u89E3\u9501\uFF0C\u60A8\u4E5F\u53EF\u4EE5\u8054\u7CFB\u7BA1\u7406\u5458\u624B\u5DE5\u89E3\u9501\u3002").toString();
        }
        try
        {
            UserSession _userSession = client.getSession();
            _userSession.unlockUser(employee.getUserId());
            _userSession.invalidate();
            IOrgLogonLog logonLog = getLastPswLog(orgid);
            logonLog.setFailedCount(Integer.valueOf(0));
            logonLog.setLogDate(new Date());
            savePswLog(logonLog);
            return "unlock";
        }
        catch(Exception e)
        {
            LOGGER.error(getClass(), e);
        }
        return "\u5F53\u524D\u7528\u6237\u5DF2\u88AB\u9501\u5B9A";
        int orgsirm010;
        orgsirm010 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM010"), Integer.valueOf(0)).intValue();
        if(orgsirm010 <= 0)
            break MISSING_BLOCK_LABEL_647;
        String mesg = recoverPasswordCheck(errorcount, orgsirm010, orgid, logonLogDate);
        if(!mesg.isEmpty())
            return mesg;
        if(++errorcount < orgsirm010)
            break MISSING_BLOCK_LABEL_618;
        OrgUtils.lockUser(username);
        employee.setUserLockTime(TimeUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try
        {
            orgService.updateEmployee(employee);
        }
        catch(Exception e)
        {
            LOGGER.error("\u7528\u6237\u9501\u5B9A\u5931\u8D25", e);
        }
        if(orgsirm011 > 0)
            return (new StringBuilder()).append("\u7528\u6237").append(username).append("\u5DF2\u9501\u5B9A\uFF0C").append(orgsirm011).append("\u5206\u949F\u540E\u81EA\u52A8\u89E3\u9501\uFF0C\u60A8\u4E5F\u53EF\u4EE5\u8054\u7CFB\u7BA1\u7406\u5458\u624B\u5DE5\u89E3\u9501\u3002").toString();
        try
        {
            return (new StringBuilder()).append("\u7528\u6237").append(username).append("\u5DF2\u9501\u5B9A").toString();
        }
        catch(Exception e)
        {
            LOGGER.error("\u5F53\u524D\u7528\u6237\u9501\u5B9A\u5931\u8D25", e);
        }
        return (new StringBuilder()).append("\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF\uFF0C\u518D\u6709").append(orgsirm010 - errorcount).append("\u6B21\u9519\u8BEF\u8F93\u5165\uFF0C\u8BE5\u7528\u6237\u5C06\u88AB\u9501\u5B9A").toString();
        return "";
    }

    public String recoverPasswordCheck(int errorcount, int orgsirm010, String orgid, Date logonLogDate)
    {
        int orgsirm012 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM012"), Integer.valueOf(0)).intValue();
        if(orgsirm012 > 0 && errorcount < orgsirm010)
        {
            Date d1 = new Date();
            IOrgLogonLog logonLog = getLastPswLog(orgid);
            Calendar cal = Calendar.getInstance();
            cal.setTime(TimeUtil.safeToDate(logonLogDate, "yyyy-MM-dd HH:mm:ss", new Date()));
            cal.set(12, cal.get(12) + orgsirm012);
            Date d2 = cal.getTime();
            if(d2.before(d1))
            {
                Double time = Double.valueOf(NumberTool.safeToDouble(Long.valueOf(d2.getTime() - d1.getTime()), Double.valueOf(0.0D)).doubleValue() / 60000D);
                LOGGER.info((new StringBuilder()).append(time).append("\u5206\u540E\u7528\u6237\u9519\u8BEF\u6B21\u6570\u5C06\u6E05\u9664").toString());
                if(time.doubleValue() <= 0.0D)
                {
                    logonLog.setFailedCount(Integer.valueOf(0));
                    logonLog.setLogDate(new Date());
                    savePswLog(logonLog);
                }
                int times = orgsirm010 - errorcount - 1;
                if(times == 0)
                {
                    logonLog.setFailedCount(Integer.valueOf(1));
                    logonLog.setLogDate(new Date());
                    savePswLog(logonLog);
                    times = 1;
                }
                return (new StringBuilder()).append("\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF\uFF0C\u518D\u6709").append(times).append("\u6B21\u9519\u8BEF\u8F93\u5165\uFF0C\u8BE5\u7528\u6237\u5C06\u88AB\u9501\u5B9A").toString();
            }
            int a = orgsirm010 - errorcount - 1;
            if(a != 0)
                return (new StringBuilder()).append("\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF\uFF0C\u518D\u6709").append(a).append("\u6B21\u9519\u8BEF\u8F93\u5165\uFF0C\u8BE5\u7528\u6237\u5C06\u88AB\u9501\u5B9A").toString();
        }
        return "";
    }

    public void clearLogonLog(String orgid)
    {
        IOrgLogonLog logonLog = getLastPswLog(orgid);
        if(null == logonLog)
            logonLog = new OrgLogonLogImpl();
        logonLog.setFailedCount(Integer.valueOf(0));
        logonLog.setLogDate(new Date());
        savePswLog(logonLog);
    }

    public String getUserPsw(String userid)
    {
        List list = getMetaDBContext().createSqlQuery((new StringBuilder()).append("select * from um_userinfo where userid ='").append(userid).append("'").toString()).getResult();
        if(list.size() > 0)
            return StringUtil.safeToString(((Map)list.get(0)).get("password"), "");
        else
            return "";
    }

    public int getPasswordPloy()
    {
        int orgsirm015 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM015"), Integer.valueOf(0)).intValue();
        return orgsirm015;
    }

    public void clearLogonFailedCount(String orgid)
    {
        LOGGER.info("\u6E05\u9664\u767B\u5F55\u9519\u8BEF\u5BC6\u7801\u8F93\u9519\u6B21\u6570!");
        IOrgLogonLog orgLogonLog = getLastPswLog(orgid);
        if(null != orgLogonLog)
        {
            orgLogonLog.setFailedCount(Integer.valueOf(0));
            orgLogonLog.setLogDate(new Date());
            savePswLog(orgLogonLog);
            LOGGER.info((new StringBuilder()).append("\u767B\u5F55\u9519\u8BEF\u5BC6\u7801\u8F93\u9519\u6B21\u6570\u4E3A").append(orgLogonLog.getFailedCount()).toString());
        }
    }

    public void enforceClearFailedCount(String orgid)
    {
        int orgsirm012 = NumberTool.safeToInteger(SettingUtils.getStringValue("ORG", "ORGSIRM012"), Integer.valueOf(0)).intValue();
        if(orgsirm012 > 0)
        {
            IOrgLogonLog logonLog = getLastPswLog(orgid);
            if(null == logonLog)
            {
                LOGGER.info("\u65E0\u767B\u9646\u9519\u8BEF\u8BB0\u5F55");
                return;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(TimeUtil.safeToDate(logonLog.getLogDate(), "yyyy-MM-dd HH:mm:ss", new Date()));
            cal.set(12, cal.get(12) + orgsirm012);
            Date d2 = cal.getTime();
            Date d1 = new Date();
            if(d2.before(d1))
            {
                Double time = Double.valueOf(NumberTool.safeToDouble(Long.valueOf(d2.getTime() - d1.getTime()), Double.valueOf(0.0D)).doubleValue() / 60000D);
                LOGGER.info((new StringBuilder()).append(time).append("\u5206\u540E\u7528\u6237\u9519\u8BEF\u6B21\u6570\u5C06\u6E05\u9664").toString());
                if(time.doubleValue() <= 0.0D)
                {
                    logonLog.setFailedCount(Integer.valueOf(0));
                    logonLog.setLogDate(new Date());
                    savePswLog(logonLog);
                }
            }
        }
    }

    public String getDefaultPswDate(String orgid)
    {
        List list = getMetaDBContext().createSqlQuery((new StringBuilder()).append("select * from sprt_orgobject where orgid ='").append(orgid).append("'").toString()).getResult();
        if(list.size() > 0)
            return StringUtil.safeToString(TimeUtil.formatDate(TimeUtil.safeToDate(((Map)list.get(0)).get("createtimestamp"), "yyyy-MM-dd HH:mm:ss", new Date()), "yyyy-MM-dd HH:mm:ss"), "");
        else
            return "";
    }

    private Logger LOGGER;
}
