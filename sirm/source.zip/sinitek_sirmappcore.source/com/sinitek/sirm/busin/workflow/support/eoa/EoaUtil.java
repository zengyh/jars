// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EoaUtil.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExample;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support.eoa:
//            TaskInfo, TaskDetail, TaskReturnResult

public class EoaUtil
{

    public EoaUtil()
    {
    }

    public static void startEoaWorkflow(IWorkflowExample workflow)
    {
        TaskInfo taskInfo = newTask(workflow);
        startEoaWorkflow(taskInfo, "WORKFLOW");
    }

    private static TaskInfo newTask(IWorkflowExample workflow)
    {
        TaskInfo taskInfo = new TaskInfo();
        List taskDetailList = new ArrayList();
        int count = 1;
        List eoasteps = new ArrayList();
        for(Iterator i$ = eoasteps.iterator(); i$.hasNext();)
        {
            Object eoastep = i$.next();
            TaskDetail taskDetail = new TaskDetail();
            String approveUserName = "";
            LOGGER.debug((new StringBuilder()).append("\u5BA1\u6279\u4EBA: ").append(approveUserName).toString());
            taskDetail.setFlowOwner(approveUserName);
            taskDetail.setSeq(String.valueOf(count));
            taskDetail.setHandType("120");
            taskDetail.setNodeDesc("");
            taskDetailList.add(taskDetail);
            count++;
        }

        taskInfo.setTaskDetail(taskDetailList);
        taskInfo.setTaskName(workflow.getBrief());
        taskInfo.setTaskDate(new Date());
        taskInfo.setRequestId(String.valueOf(workflow.getId()));
        String starterId = String.valueOf(workflow.getStarterId());
        Employee starter = OrgServiceFactory.getOrgService().getEmployeeById(starterId);
        if(starter != null)
        {
            taskInfo.setTaskOwner((new StringBuilder()).append("PAICDOM\\").append(starter.getUserName()).toString());
            String phone = starter.getTel();
            phone = phone != null && !"null".equals(phone) ? phone : "";
            String orgName = starter.getEmpName();
            orgName = orgName != null && !"null".equals(orgName) ? orgName : "";
            taskInfo.setTaskRansActor((new StringBuilder()).append(orgName).append(" ").append(phone).toString());
        }
        String sysName = SettingUtils.getStringValue("WORKFLOW", "EOASYSNAME", "INVEST_IVS_AMIS");
        taskInfo.setSysName(sysName);
        taskInfo.setTaskDesc("");
        return taskInfo;
    }

    private static int startEoaWorkflow(TaskInfo info, String modelName)
    {
        if(info == null || !StringUtils.isNotEmpty(modelName))
            break MISSING_BLOCK_LABEL_389;
        String wfIdStr = info.getRequestId();
        info.setNewFlow("1");
        String result;
        TaskReturnResult taskResult;
        try
        {
            String eoaisuser = SettingUtils.getStringValue("WORKFLOW", "EOAISUSER");
            String eoaWebServiceUrl = SettingUtils.getStringValue("WORKFLOW", "EOAWEBSERVICEURL");
            Service service = new Service();
            Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(eoaWebServiceUrl);
            if("1".equals(eoaisuser))
            {
                String _eoaUsername = SettingUtils.getStringValue("WORKFLOW", "EOAUSERNAME");
                String _cerdFilePath = SettingUtils.getStringValue("WORKFLOW", "CERDFILEPATH");
                String _cyberSafe = SettingUtils.getStringValue("WORKFLOW", "CYBERSAFE");
                String _cyberFoler = SettingUtils.getStringValue("WORKFLOW", "CYBERFOLER");
                call.setUsername(_eoaUsername);
                LOGGER.info("SyBrak \u5F00\u59CB\u53D6\u5BC6\u7801");
            } else
            {
                String _eoaUsername = SettingUtils.getStringValue("WORKFLOW", "EOAUSERNAME");
                String _eoaPassword = SettingUtils.getStringValue("WORKFLOW", "EOAPASSWORD");
                LOGGER.info((new StringBuilder()).append("\u4F7F\u7528\u9ED8\u8BA4\u5BC6\u7801\u8BBF\u95EEEOA\uFF08").append(_eoaPassword).append(")").toString());
                call.setUsername(_eoaUsername);
                call.setPassword(_eoaPassword);
            }
            result = null;
            try
            {
                LOGGER.info("\u5F00\u59CB\u8BF7\u6C42EOA");
                String data = info.toXML();
                LOGGER.info((new StringBuilder()).append("\u8BF7\u6C42EOA\u5185\u5BB9: ").append(data).toString());
                call.setOperationName(new QName("http://eoa2.paic.com.cn", "importTaskInfoXml"));
                result = (String)call.invoke(new Object[] {
                    data
                });
                LOGGER.info((new StringBuilder()).append("\u7ED3\u675F\u8BF7\u6C42EOA\uFF08").append(result).append(")").toString());
            }
            catch(Exception ex)
            {
                LOGGER.error("EOA:\u8C03\u7528\u670D\u52A1\u53D1\u9001\u9519\u8BEF ", ex);
                return -1;
            }
        }
        catch(Exception ex)
        {
            LOGGER.info("EOA:\u8C03\u7528\u670D\u52A1\u53D1\u9001\u9519\u8BEF ", ex);
            return -1;
        }
        taskResult = TaskReturnResult.newInstanceByXml(result);
        if(taskResult.getSuccess().booleanValue())
            return 0;
        LOGGER.info((new StringBuilder()).append(taskResult.getRequestId()).append(":").append(taskResult.getMessage()).toString());
        return -1;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/support/eoa/EoaUtil);

}
