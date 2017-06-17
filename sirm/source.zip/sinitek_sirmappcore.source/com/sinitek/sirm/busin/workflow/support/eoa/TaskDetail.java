// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskDetail.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public final class TaskDetail
    implements Serializable
{

    public TaskDetail()
    {
    }

    public Map toMap()
    {
        Map result = new HashMap();
        result.put("FLOWOWNER", flowOwner);
        result.put("SEQ", seq);
        result.put("HANDTYPE", handType);
        if(StringUtils.isNotEmpty(nodeDesc))
            result.put("NODEDESC", nodeDesc);
        return result;
    }

    public String toXML()
    {
        StringBuffer xml = new StringBuffer();
        xml.append("<").append("com.paic.eoa.biz.service.webservice.dto.TaskDetail").append(">");
        xml.append("<flowOwner>").append(flowOwner).append("</flowOwner>");
        xml.append("<seq>").append(seq).append("</seq>");
        xml.append("<handType>").append(handType).append("</handType>");
        xml.append("<nodeDesc><![CDATA[").append(nodeDesc).append("]]></nodeDesc>");
        xml.append("</").append("com.paic.eoa.biz.service.webservice.dto.TaskDetail").append(">");
        return xml.toString();
    }

    public String getFlowOwner()
    {
        return flowOwner;
    }

    public void setFlowOwner(String flowOwner)
    {
        this.flowOwner = flowOwner;
    }

    public String getSeq()
    {
        return seq;
    }

    public void setSeq(String seq)
    {
        this.seq = seq;
    }

    public String getHandType()
    {
        return handType;
    }

    public void setHandType(String handType)
    {
        this.handType = handType;
    }

    public String getNodeDesc()
    {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc)
    {
        this.nodeDesc = nodeDesc;
    }

    public static final String XML_NAME = "com.paic.eoa.biz.service.webservice.dto.TaskDetail";
    public static final String HANDTYPE_ONESTEP_ONEMAN = "30";
    public static final String HANDTYPE_ONESTEP_MANYMAN = "30";
    public static final String HANDTYPE_SYNERGY = "120";
    public static final String HANDTYPE_READ = "1110";
    private String flowOwner;
    private String seq;
    private String handType;
    private String nodeDesc;
}
