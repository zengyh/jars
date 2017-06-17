// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AutoLogonUtils.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.maillogon.entity.IEmailLogon;
import com.sinitek.sirm.common.maillogon.service.IEmailLogonService;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            EncryptUtil, HttpUtils, NumberTool, TimeUtil

public class AutoLogonUtils
{

    public AutoLogonUtils()
    {
    }

    public static String createEmailLogonUrl(String orgid, String openlink, String linkname)
    {
        StringBuilder _res = new StringBuilder();
        Employee employee = OrgServiceFactory.getOrgService().getEmployeeById(orgid);
        String _uuid = UUID.randomUUID().toString();
        LOGGER.info((new StringBuilder()).append("UUID=").append(_uuid).toString());
        String _username = EncryptUtil.encryptDES((new StringBuilder()).append("username=").append(employee.getUserName()).append("&uuid=").append(_uuid).toString(), "SIRM2012");
        _res.append((new StringBuilder()).append("<a href='").append(SettingUtils.getStringValue("COMMON", "HOST_ADDRESS")).append(HttpUtils.getJsUrl("/service/logon.action?url=")).append(openlink.replaceAll("&", "%26")).append("&parameter=").append(_username).append("'>").append(linkname).append("</a>").toString());
        IEmailLogonService _serv = CommonServiceFactory.getEmailLogonService();
        IEmailLogon _emaillogon = _serv.getEmailLogon(0);
        _emaillogon.setUserName(employee.getUserName());
        _emaillogon.setUUID(_uuid);
        _emaillogon.setStartDate(new Date());
        _emaillogon.setUsedTime(Integer.valueOf(0));
        _serv.saveEmailLogon(_emaillogon);
        return _res.toString();
    }

    public static boolean checkEmailLogonOfSIRM0001(String username, String uuid)
    {
        boolean _res = true;
        IEmailLogon _elogon = CommonServiceFactory.getEmailLogonService().getEmailLogon(uuid, username);
        if(_elogon != null)
        {
            ISetting _SIRM0001 = CommonServiceFactory.getSettingService().getSetting("COMMON", "MAIL_SIRM0001");
            if(_SIRM0001 != null)
            {
                int _sirm0001 = NumberTool.safeToInteger(_SIRM0001.getValue(), Integer.valueOf(0)).intValue();
                if(_sirm0001 > 0)
                {
                    Calendar _cal = Calendar.getInstance();
                    _cal.setTime(_elogon.getStartDate());
                    _cal.add(6, _sirm0001);
                    Date _finaldate = _cal.getTime();
                    Date _currentdate = new Date();
                    LOGGER.info((new StringBuilder()).append("\u94FE\u63A5\u751F\u6210\u65F6\u95F4\uFF1A").append(TimeUtil.formatDate(_elogon.getStartDate(), "yyyy-MM-dd HH:mm:ss")).toString());
                    LOGGER.info((new StringBuilder()).append("\u914D\u7F6E\u7684\u5929\u6570\uFF1A").append(_sirm0001).toString());
                    LOGGER.info((new StringBuilder()).append("\u94FE\u63A5\u5931\u6548\u65F6\u95F4\uFF1A").append(TimeUtil.formatDate(_finaldate, "yyyy-MM-dd HH:mm:ss")).toString());
                    LOGGER.info((new StringBuilder()).append("\u5F53\u524D\u65F6\u95F4\uFF1A").append(TimeUtil.formatDate(_currentdate, "yyyy-MM-dd HH:mm:ss")).toString());
                    if(_finaldate.compareTo(_currentdate) < 0)
                        _res = false;
                }
            }
        }
        return _res;
    }

    public static boolean checkEmailLogonOfSIRM0002(String username, String uuid)
    {
        boolean _res = true;
        IEmailLogonService _serv = CommonServiceFactory.getEmailLogonService();
        IEmailLogon _elogon = _serv.getEmailLogon(uuid, username);
        if(_elogon != null)
        {
            int _usedtime = _elogon.getUsedTime().intValue() + 1;
            _elogon.setUsedTime(Integer.valueOf(_usedtime));
            _serv.saveEmailLogon(_elogon);
            ISetting _SIRM0002 = CommonServiceFactory.getSettingService().getSetting("COMMON", "MAIL_SIRM0002");
            if(_SIRM0002 != null)
            {
                int _sirm0002 = NumberTool.safeToInteger(_SIRM0002.getValue(), Integer.valueOf(0)).intValue();
                if(_sirm0002 > 0)
                {
                    LOGGER.info((new StringBuilder()).append("\u5F53\u524D\u94FE\u63A5\u5DF2\u767B\u9646\u6B21\u6570\uFF1A").append(_usedtime).toString());
                    LOGGER.info((new StringBuilder()).append("\u914D\u7F6E\u7684\u6B21\u6570\uFF1A").append(_sirm0002).toString());
                    if(_sirm0002 < _usedtime)
                        _res = false;
                }
            }
        }
        return _res;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/utils/AutoLogonUtils);

}
