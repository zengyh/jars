// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskInfo.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support.eoa:
//            TaskFile, TaskDetail

public final class TaskInfo
    implements Serializable
{

    public TaskInfo()
    {
        sysName = "INVEST_IVS_IRP";
        taskSortId = "1";
        newFlow = "1";
    }

    public Map toMap()
    {
        Map result = new HashMap();
        result.put("REQUESTSYSID", requestId);
        result.put("REQUESTSYSNAME", sysName);
        result.put("TASKNAME", taskName);
        result.put("TASKSORTID", taskSortId);
        result.put("TASKDESC", taskDesc);
        result.put("TASKDATE", taskDate);
        if(StringUtils.isNotEmpty(taskOwner))
            result.put("TASKOWNER", taskOwner);
        if(StringUtils.isNotEmpty(empNo))
            result.put("FA_EMPNO", empNo);
        result.put("FA_NAME", empName);
        result.put("TASKTRANSACTOR", taskTransActor);
        result.put("IS_NEWFLOW", newFlow);
        if(StringUtils.isNotEmpty(taskSendLeader))
            result.put("TASKSENDLEADER", taskSendLeader);
        if(StringUtils.isNotEmpty(taskDept))
            result.put("TASKDEPT", taskDept);
        if(StringUtils.isNotEmpty(taskComp))
            result.put("TASKCOMP", taskComp);
        if(taskFile != null)
        {
            List files = new ArrayList();
            TaskFile file;
            for(Iterator i$ = taskFile.iterator(); i$.hasNext(); files.add(file.toMap()))
            {
                Object obj = i$.next();
                file = (TaskFile)obj;
            }

            result.put("taskFile", files);
        }
        if(taskDetail != null)
        {
            List files = new ArrayList();
            TaskDetail file;
            for(Iterator i$ = taskDetail.iterator(); i$.hasNext(); files.add(file.toMap()))
            {
                Object obj = i$.next();
                file = (TaskDetail)obj;
            }

            result.put("taskDetail", files);
        }
        if(otherTaskInfo != null)
            result.put("OTHERTASKINFO", otherTaskInfo);
        return result;
    }

    public String toXML()
    {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        xml.append("<").append("com.paic.eoa.biz.service.webservice.dto.TaskInfo").append(">");
        xml.append("<requestSysID>").append(requestId).append("</requestSysID>");
        xml.append("<taskName><![CDATA[").append(taskName).append("]]></taskName>");
        xml.append("<taskSortID>").append(taskSortId).append("</taskSortID>");
        xml.append("<taskDesc><![CDATA[").append(taskDesc).append("]]></taskDesc>");
        xml.append("<requestSysName>").append(sysName).append("</requestSysName>");
        xml.append("<taskDate>").append(datefmt.format(taskDate == null ? new Date() : taskDate)).append("</taskDate>");
        if(StringUtils.isNotEmpty(taskOwner))
            xml.append("<taskOwner>").append(taskOwner).append("</taskOwner>");
        if(StringUtils.isNotEmpty(empNo))
            xml.append("<faEmpNo>").append(empNo).append("</faEmpNo>");
        if(StringUtils.isNotEmpty(taskSendLeader))
            xml.append("<taskSendLeader>").append(taskSendLeader).append("</taskSendLeader>");
        if(StringUtils.isNotEmpty(taskDept))
            xml.append("<taskDept>").append(taskDept).append("</taskDept>");
        if(StringUtils.isNotEmpty(taskComp))
            xml.append("<taskComp>").append(taskComp).append("</taskComp>");
        xml.append("<taskTransactor>").append(taskTransActor).append("</taskTransactor>");
        xml.append("<isNewFlow>").append(newFlow).append("</isNewFlow>");
        xml.append("<taskDetail>");
        if(taskDetail != null && taskDetail.size() > 0)
        {
            TaskDetail detail;
            for(Iterator i$ = taskDetail.iterator(); i$.hasNext(); xml.append(detail.toXML()))
            {
                Object obj = i$.next();
                detail = (TaskDetail)obj;
            }

        }
        xml.append("</taskDetail>");
        if(taskFile != null && taskFile.size() > 0)
        {
            xml.append("<taskFile>");
            TaskFile file;
            for(Iterator i$ = taskFile.iterator(); i$.hasNext(); xml.append(file.toXML()))
            {
                Object obj = i$.next();
                file = (TaskFile)obj;
            }

            xml.append("</taskFile>");
        }
        if(otherTaskInfo != null)
        {
            xml.append("<otherTaskInfo>");
            Object obj;
            for(Iterator i$ = otherTaskInfo.keySet().iterator(); i$.hasNext(); xml.append("<").append(obj).append(">").append(otherTaskInfo.get(obj)).append("</").append(obj).append(">"))
                obj = i$.next();

            xml.append("</otherTaskInfo>");
        }
        xml.append("</").append("com.paic.eoa.biz.service.webservice.dto.TaskInfo").append(">");
        return xml.toString();
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public String getSysName()
    {
        return sysName;
    }

    public void setSysName(String sysName)
    {
        this.sysName = sysName;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskSortId()
    {
        return taskSortId;
    }

    public void setTaskSortId(String taskSortId)
    {
        this.taskSortId = taskSortId;
    }

    public String getTaskDesc()
    {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc)
    {
        this.taskDesc = taskDesc;
    }

    public Date getTaskDate()
    {
        return taskDate;
    }

    public void setTaskDate(Date taskDate)
    {
        this.taskDate = taskDate;
    }

    public String getTaskOwner()
    {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner)
    {
        this.taskOwner = taskOwner;
    }

    public String getEmpNo()
    {
        return empNo;
    }

    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpName()
    {
        return empName;
    }

    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getTaskRansActor()
    {
        return taskTransActor;
    }

    public void setTaskRansActor(String taskTransActor)
    {
        this.taskTransActor = taskTransActor;
    }

    public String getNewFlow()
    {
        return newFlow;
    }

    public void setNewFlow(String newFlow)
    {
        this.newFlow = newFlow;
    }

    public String getTaskSendLeader()
    {
        return taskSendLeader;
    }

    public void setTaskSendLeader(String taskSendLeader)
    {
        this.taskSendLeader = taskSendLeader;
    }

    public String getTaskDept()
    {
        return taskDept;
    }

    public void setTaskDept(String taskDept)
    {
        this.taskDept = taskDept;
    }

    public String getTaskComp()
    {
        return taskComp;
    }

    public void setTaskComp(String taskComp)
    {
        this.taskComp = taskComp;
    }

    public List getTaskFile()
    {
        return taskFile;
    }

    public void setTaskFile(List taskFile)
    {
        this.taskFile = taskFile;
    }

    public List getTaskDetail()
    {
        return taskDetail;
    }

    public void setTaskDetail(List taskDetail)
    {
        this.taskDetail = taskDetail;
    }

    public Map getOtherTaskInfo()
    {
        return otherTaskInfo;
    }

    public void setOtherTaskInfo(Map otherTaskInfo)
    {
        this.otherTaskInfo = otherTaskInfo;
    }

    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
    public static final String XML_NAME = "com.paic.eoa.biz.service.webservice.dto.TaskInfo";
    public static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String IS_NEW = "1";
    public static final String IS_NOT_NEW = "0";
    private String requestId;
    private String sysName;
    private String taskName;
    private String taskSortId;
    private String taskDesc;
    private Date taskDate;
    private String taskOwner;
    private String empNo;
    private String empName;
    private String taskTransActor;
    private String newFlow;
    private String taskSendLeader;
    private String taskDept;
    private String taskComp;
    private List taskFile;
    private List taskDetail;
    private Map otherTaskInfo;

}
