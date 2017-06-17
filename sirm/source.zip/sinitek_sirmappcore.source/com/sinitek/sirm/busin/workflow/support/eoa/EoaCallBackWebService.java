// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EoaCallBackWebService.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.util.Properties;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support.eoa:
//            CallBackTaskInfo

public class EoaCallBackWebService
{

    public EoaCallBackWebService()
    {
    }

    public boolean backTaskResult(String taskInfo, String username, String password)
    {
        LOGGER.debug("EOA\u63A5\u53D7\u8FD4\u56DE\u8BF7\u6C42\uFF01");
        LOGGER.debug((new StringBuilder()).append("taskInfo:\n").append(taskInfo).toString());
        LOGGER.debug((new StringBuilder()).append("username:").append(username).toString());
        boolean checked = checkUM(username, password);
        LOGGER.debug((new StringBuilder()).append("EOA\u9A8C\u8BC1\u6210\u529F?[").append(checked).append("]").toString());
        if(!checked)
            return false;
        CallBackTaskInfo info = null;
        if(taskInfo != null && !taskInfo.equals(""))
            info = CallBackTaskInfo.newInstanceByXml(taskInfo);
        boolean result = false;
        LOGGER.debug((new StringBuilder()).append("callback task info is ok?[").append(info != null).append("]").toString());
        if(info != null)
        {
            String wfIdStr = info.getRequestId();
            LOGGER.debug((new StringBuilder()).append("requestId:").append(wfIdStr).append(",taskId:").append(info.getTaskId()).toString());
            String ids[] = wfIdStr.split("_");
            info.setRequestId(ids[0]);
            result = true;
        } else
        {
            result = false;
        }
        LOGGER.debug("EOA\u5904\u7406\u8BF7\u6C42\u5B8C\u6BD5\uFF01");
        return result;
    }

    private boolean checkUM(String username, String password)
    {
        LOGGER.debug("\u5F00\u59CBEOA\u7528\u6237\u9A8C\u8BC1......");
        ISetting setting = SettingUtils.getSetting("WORKFLOW", "EOACHECKUMURL");
        LOGGER.debug((new StringBuilder()).append("1.........").append(setting).toString());
        String providerURL = setting.getValue();
        LOGGER.debug((new StringBuilder()).append("2.........").append(providerURL).toString());
        try
        {
            javax.naming.Context ctx = null;
            Properties prop = new Properties();
            LOGGER.debug("3.........");
            prop.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            LOGGER.debug("4.........");
            prop.put("java.naming.provider.url", providerURL);
            LOGGER.debug((new StringBuilder()).append("5.........").append(username).toString());
            prop.put("java.naming.security.principal", username);
            LOGGER.debug((new StringBuilder()).append("6.........").append(password).toString());
            prop.put("java.naming.security.credentials", password);
            LOGGER.debug("7.........");
            LOGGER.debug("....\u8C03\u7528\u9A8C\u8BC1......");
            ctx = new InitialContext(prop);
            LOGGER.debug("EOA\u7528\u6237\u9A8C\u8BC1\u901A\u8FC7\uFF01");
            return true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            LOGGER.error((new StringBuilder()).append("checkUM failed! EOA\u7528\u6237\u9A8C\u8BC1\u5931\u8D25! ").append(ex.getMessage()).toString());
            return false;
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/support/eoa/EoaCallBackWebService);

}
