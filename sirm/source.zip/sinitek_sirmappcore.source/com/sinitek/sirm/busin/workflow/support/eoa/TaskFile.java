// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskFile.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public final class TaskFile
    implements Serializable
{

    public TaskFile()
    {
    }

    public Map toMap()
    {
        Map result = new HashMap();
        result.put("REQUESTSYSID", requestId);
        result.put("REQUESTSYSNAME", sysName);
        if(StringUtils.isNotEmpty(eoaNfsUrl))
            result.put("EOANFSURL", eoaNfsUrl);
        if(StringUtils.isNotEmpty(fullUrl))
            result.put("DOCNAME", fullUrl);
        result.put("DOCOLDNAME", docName);
        if(StringUtils.isNotEmpty(operator))
            result.put("OPERATOR", operator);
        if(operateTime != null)
            result.put("OPERATETIME", operateTime);
        result.put("DOCCLASS", docClass);
        result.put("FILESIZE", Double.valueOf(fileSize));
        if(content != null)
            result.put("CONTENT", content);
        if(StringUtils.isNotEmpty(contentType))
            result.put("CONTENTTYPE", contentType);
        if(cachetNum != null)
            result.put("CACHET_NUM", cachetNum);
        if(StringUtils.isNotEmpty(cachetDesc))
            result.put("CACHET_DESC", cachetDesc);
        return result;
    }

    public String toXML()
    {
        StringBuffer xml = new StringBuffer();
        xml.append("<").append("com.paic.eoa.biz.service.webservice.dto.TaskFile").append(">");
        xml.append("<requestSysID>").append(requestId).append("</requestSysID>");
        if(StringUtils.isNotEmpty(eoaNfsUrl))
            xml.append("<eoaNfsUrl>").append(eoaNfsUrl).append("</eoaNfsUrl>");
        if(StringUtils.isNotEmpty(fullUrl))
            xml.append("<docName><![CDATA[").append(fullUrl).append("]]></docName>");
        xml.append("<docOldName><![CDATA[").append(docName).append("]]></docOldName>");
        xml.append("<fileSize>").append(fileSize).append("</fileSize>");
        xml.append("<docClass>").append(docClass).append("</docClass>");
        xml.append("<requestSysName>").append(sysName).append("</requestSysName>");
        xml.append("<operateTime>").append(datefmt.format(operateTime == null ? new Date() : operateTime)).append("</operateTime>");
        xml.append("<operator>").append(operator).append("</operator>");
        xml.append("<contentType>").append(contentType == null ? "" : contentType).append("</contentType>");
        if(content != null)
            xml.append("<content>").append(content).append("</content>");
        xml.append("</").append("com.paic.eoa.biz.service.webservice.dto.TaskFile").append(">");
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

    public String getEoaNfsUrl()
    {
        return eoaNfsUrl;
    }

    public void setEoaNfsUrl(String eoaNfsUrl)
    {
        this.eoaNfsUrl = eoaNfsUrl;
    }

    public String getFullUrl()
    {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl)
    {
        this.fullUrl = fullUrl;
    }

    public String getDocName()
    {
        return docName;
    }

    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }

    public String getDocClass()
    {
        return docClass;
    }

    public void setDocClass(String docClass)
    {
        this.docClass = docClass;
    }

    public double getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(double fileSize)
    {
        this.fileSize = fileSize;
    }

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte content[])
    {
        this.content = content;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public Integer getCachetNum()
    {
        return cachetNum;
    }

    public void setCachetNum(Integer cachetNum)
    {
        this.cachetNum = cachetNum;
    }

    public String getCachetDesc()
    {
        return cachetDesc;
    }

    public void setCachetDesc(String cachetDesc)
    {
        this.cachetDesc = cachetDesc;
    }

    public static final String XML_NAME = "com.paic.eoa.biz.service.webservice.dto.TaskFile";
    public static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String requestId;
    private String sysName;
    private String eoaNfsUrl;
    private String fullUrl;
    private String docName;
    private String operator;
    private Date operateTime;
    private String docClass;
    private double fileSize;
    private byte content[];
    private String contentType;
    private Integer cachetNum;
    private String cachetDesc;

}
