// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallBackTaskInfo.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support.eoa:
//            CallBackTaskFile, CallBackTaskDetail

public class CallBackTaskInfo
    implements Serializable
{

    public CallBackTaskInfo()
    {
    }

    public static CallBackTaskInfo newInstanceByXml(String xml)
    {
        CallBackTaskInfo taskInfo = null;
        if(StringUtils.isNotEmpty(xml))
        {
            SAXReader saxReader = new SAXReader();
            try
            {
                Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
                Element root = document.getRootElement();
                if(root != null)
                {
                    taskInfo = new CallBackTaskInfo();
                    taskInfo.setSource(xml);
                    List entrys = root.selectNodes("entry");
                    Iterator i$ = entrys.iterator();
                    do
                    {
                        if(!i$.hasNext())
                            break;
                        Object obj = i$.next();
                        Element subEmt = (Element)obj;
                        Iterator it = subEmt.elementIterator();
                        Element keyEmt = (Element)it.next();
                        if(keyEmt != null)
                        {
                            String key = keyEmt.getStringValue();
                            if(key.equalsIgnoreCase("taskFile"))
                            {
                                if(it.hasNext())
                                {
                                    Element valueEmt = (Element)it.next();
                                    if(valueEmt.getName().equalsIgnoreCase("list"))
                                    {
                                        List files = new ArrayList();
                                        Iterator subIt = valueEmt.elementIterator();
                                        do
                                        {
                                            if(!subIt.hasNext())
                                                break;
                                            Element emt = (Element)subIt.next();
                                            if(emt != null)
                                            {
                                                CallBackTaskFile file = CallBackTaskFile.newInstanceByXml(emt);
                                                files.add(file);
                                            }
                                        } while(true);
                                        taskInfo.setTaskFile(files);
                                    }
                                }
                            } else
                            if(key.equalsIgnoreCase("taskDetail"))
                            {
                                if(it.hasNext())
                                {
                                    Element valueEmt = (Element)it.next();
                                    if(valueEmt.getName().equalsIgnoreCase("list"))
                                    {
                                        List details = new ArrayList();
                                        Iterator subIt = valueEmt.elementIterator();
                                        do
                                        {
                                            if(!subIt.hasNext())
                                                break;
                                            Element emt = (Element)subIt.next();
                                            if(emt != null)
                                            {
                                                CallBackTaskDetail detail = CallBackTaskDetail.newInstanceByXml(emt);
                                                details.add(detail);
                                            }
                                        } while(true);
                                        taskInfo.setTaskDetail(details);
                                    }
                                }
                            } else
                            if(!key.equalsIgnoreCase("cachetMap") && !key.equalsIgnoreCase("minuteMap"))
                                if(!key.equalsIgnoreCase("draftFile"));
                            String strValue = "";
                            if(it.hasNext())
                            {
                                Element valueEmt = (Element)it.next();
                                strValue = valueEmt.getStringValue();
                            }
                            if(key.equalsIgnoreCase("REQUESTSYSID"))
                                taskInfo.setRequestId(strValue);
                            else
                            if(key.equalsIgnoreCase("FA_NAME"))
                                taskInfo.setEmpName(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKOWNWE"))
                                taskInfo.setTaskOwner(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKENDDATE"))
                            {
                                if(StringUtils.isNotEmpty(strValue))
                                    taskInfo.setTaskEndDate(datefmt.parse(strValue));
                            } else
                            if(key.equalsIgnoreCase("TASKCOMP"))
                                taskInfo.setTaskComp(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKSTATUS"))
                                taskInfo.setTaskStatus(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKDESC"))
                                taskInfo.setTaskDesc(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKTYPEID"))
                                taskInfo.setTaskTypeId(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKID"))
                                taskInfo.setTaskId(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKDEPT"))
                                taskInfo.setTaskDept(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKSORTID"))
                                taskInfo.setTaskSortId(strValue);
                            else
                            if(key.equalsIgnoreCase("REQUESTSYSNAME"))
                                taskInfo.setSysName(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKSENDLEADER"))
                                taskInfo.setTaskSendLeader(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKDEPTNAME"))
                                taskInfo.setTaskDeptName(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKDATE"))
                            {
                                if(StringUtils.isNotEmpty(strValue))
                                    taskInfo.setTaskDate(datefmt.parse(strValue));
                            } else
                            if(key.equalsIgnoreCase("TASKNAME"))
                                taskInfo.setTaskName(strValue);
                            else
                            if(key.equalsIgnoreCase("TASKTRANSACTOR"))
                                taskInfo.setTaskTransActor(strValue);
                        }
                    } while(true);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return taskInfo;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public String getSysName()
    {
        return sysName;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public String getTaskTypeId()
    {
        return taskTypeId;
    }

    public String getTaskSortId()
    {
        return taskSortId;
    }

    public String getTaskDesc()
    {
        return taskDesc;
    }

    public Date getTaskDate()
    {
        return taskDate;
    }

    public Date getTaskEndDate()
    {
        return taskEndDate;
    }

    public String getTaskOwner()
    {
        return taskOwner;
    }

    public String getEmpName()
    {
        return empName;
    }

    public String getTaskTransActor()
    {
        return taskTransActor;
    }

    public String getTaskSendLeader()
    {
        return taskSendLeader;
    }

    public String getTaskDept()
    {
        return taskDept;
    }

    public String getTaskComp()
    {
        return taskComp;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }

    public List getTaskFile()
    {
        return taskFile;
    }

    public List getTaskDetail()
    {
        return taskDetail;
    }

    public Map getCachetMap()
    {
        return cachetMap;
    }

    public Map getDraftFile()
    {
        return draftFile;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public void setSysName(String sysName)
    {
        this.sysName = sysName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public void setTaskTypeId(String taskTypeId)
    {
        this.taskTypeId = taskTypeId;
    }

    public void setTaskSortId(String taskSortId)
    {
        this.taskSortId = taskSortId;
    }

    public void setTaskDesc(String taskDesc)
    {
        this.taskDesc = taskDesc;
    }

    public void setTaskDate(Date taskDate)
    {
        this.taskDate = taskDate;
    }

    public void setTaskEndDate(Date taskEndDate)
    {
        this.taskEndDate = taskEndDate;
    }

    public void setTaskOwner(String taskOwner)
    {
        this.taskOwner = taskOwner;
    }

    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public void setTaskTransActor(String taskTransActor)
    {
        this.taskTransActor = taskTransActor;
    }

    public void setTaskSendLeader(String taskSendLeader)
    {
        this.taskSendLeader = taskSendLeader;
    }

    public void setTaskDept(String taskDept)
    {
        this.taskDept = taskDept;
    }

    public void setTaskComp(String taskComp)
    {
        this.taskComp = taskComp;
    }

    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public void setTaskFile(List taskFile)
    {
        this.taskFile = taskFile;
    }

    public void setTaskDetail(List taskDetail)
    {
        this.taskDetail = taskDetail;
    }

    public void setCachetMap(Map cachetMap)
    {
        this.cachetMap = cachetMap;
    }

    public void setDraftFile(Map draftFile)
    {
        this.draftFile = draftFile;
    }

    public String getTaskDeptName()
    {
        return taskDeptName;
    }

    public void setTaskDeptName(String taskDeptName)
    {
        this.taskDeptName = taskDeptName;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public CallBackTaskInfo clone()
        throws CloneNotSupportedException
    {
        return (CallBackTaskInfo)super.clone();
    }

    public volatile Object clone()
        throws CloneNotSupportedException
    {
        return clone();
    }

    public static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd");
    public static final String STATUS_COMPLIED = "COMPLIED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_ABANDONED = "ABANDONED";
    private String taskId;
    private String requestId;
    private String sysName;
    private String taskName;
    private String taskTypeId;
    private String taskSortId;
    private String taskDesc;
    private Date taskDate;
    private Date taskEndDate;
    private String taskOwner;
    private String empName;
    private String taskTransActor;
    private String taskSendLeader;
    private String taskDept;
    private String taskDeptName;
    private String taskComp;
    private String taskStatus;
    private List taskFile;
    private List taskDetail;
    private Map cachetMap;
    private Map draftFile;
    private String source;

}
