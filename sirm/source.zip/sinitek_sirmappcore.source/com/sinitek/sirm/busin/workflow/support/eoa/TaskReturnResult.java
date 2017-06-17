// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskReturnResult.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class TaskReturnResult
    implements Serializable
{

    public TaskReturnResult()
    {
    }

    public static TaskReturnResult newInstanceByMap(Map input)
    {
        TaskReturnResult result = null;
        if(input != null)
        {
            result = new TaskReturnResult();
            String taskId = (String)input.get("TASKID");
            result.setTaskId(taskId);
            result.setRequestId((String)input.get("REQUESTSYSID"));
            result.setSuccess((Boolean)input.get("BOOL"));
            result.setMessage((String)input.get("EXCEPTIONSTR"));
        }
        return result;
    }

    public static TaskReturnResult newInstanceByXml(String xml)
    {
        TaskReturnResult result = new TaskReturnResult();
        result.setSuccess(Boolean.valueOf(false));
        if(StringUtils.isNotEmpty(xml))
        {
            SAXReader saxReader = new SAXReader();
            try
            {
                Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
                Element root = document.getRootElement();
                if(root != null)
                {
                    Node taskId = root.selectSingleNode("taskId");
                    if(taskId != null)
                        result.setTaskId(taskId.getStringValue());
                    Node bool = root.selectSingleNode("bool");
                    if(bool != null)
                        result.setSuccess(Boolean.valueOf(bool.getStringValue()));
                    Node exception = root.selectSingleNode("exceptionStr");
                    if(exception != null)
                        result.setMessage(exception.getStringValue());
                    Node reqId = root.selectSingleNode("requestSysId");
                    if(reqId != null)
                        result.setRequestId(reqId.getStringValue());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    private String taskId;
    private String requestId;
    private Boolean success;
    private String message;
}
