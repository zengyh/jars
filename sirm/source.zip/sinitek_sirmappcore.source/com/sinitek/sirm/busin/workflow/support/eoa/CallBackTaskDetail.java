// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallBackTaskDetail.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public class CallBackTaskDetail
    implements Serializable
{

    public CallBackTaskDetail()
    {
    }

    public static CallBackTaskDetail newInstanceByMap(Map input)
    {
        CallBackTaskDetail detail = null;
        if(input != null)
        {
            detail = new CallBackTaskDetail();
            String temp = (String)input.get("FLOWDESC");
            if(StringUtils.isNotEmpty(temp))
                detail.flowDesc = temp;
            temp = (String)input.get("FLOWOWNER");
            if(StringUtils.isNotEmpty(temp))
                detail.flowOwner = temp;
            temp = (String)input.get("FLOWOWNERMAIL");
            if(StringUtils.isNotEmpty(temp))
                detail.flowOwnerMail = temp;
            temp = (String)input.get("FLOWREALOWNER");
            if(StringUtils.isNotEmpty(temp))
                detail.flowRealOwner = temp;
            temp = (String)input.get("FLOWREALMAIL");
            if(StringUtils.isNotEmpty(temp))
                detail.flowRealOwnerMail = temp;
            temp = (String)input.get("FLOWOWNERDEPT");
            if(StringUtils.isNotEmpty(temp))
                detail.flowOwnerDept = temp;
            temp = (String)input.get("FLOWRESULT");
            if(StringUtils.isNotEmpty(temp))
                detail.flowResult = temp;
            temp = (String)input.get("FLOWSTATE");
            if(StringUtils.isNotEmpty(temp))
                detail.flowState = temp;
            Date time = (Date)input.get("FLOWDONEDATE");
            if(time != null)
                detail.flowDoneDate = time;
            time = (Date)input.get("flowEndDate");
            if(time != null)
                detail.flowEndDate = time;
        }
        return detail;
    }

    public static CallBackTaskDetail newInstanceByXml(Element xml)
    {
        CallBackTaskDetail detail = null;
        if(xml != null)
        {
            detail = new CallBackTaskDetail();
            List entrys = xml.selectNodes("entry");
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
                    String strValue = "";
                    do
                    {
                        if(!it.hasNext())
                            break;
                        Element valueEmt = (Element)it.next();
                        if(valueEmt != null)
                            strValue = valueEmt.getStringValue();
                    } while(true);
                    if(key.equalsIgnoreCase("FLOWREALOWNER"))
                        detail.setFlowRealOwner(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWREALOWNERNAME"))
                        detail.setFlowRealOwnerName(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWDESC"))
                        detail.setFlowDesc(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWOWNER"))
                        detail.setFlowOwner(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWOWNERNAME"))
                        detail.setFlowOwnerName(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWENDDATE"))
                    {
                        if(StringUtils.isNotEmpty(strValue))
                            try
                            {
                                detail.setFlowEndDate(datefmt.parse(strValue));
                            }
                            catch(ParseException e)
                            {
                                e.printStackTrace();
                            }
                    } else
                    if(key.equalsIgnoreCase("FLOWOWNERMAIL"))
                        detail.setFlowOwnerMail(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWREALMAIL"))
                        detail.setFlowRealOwnerMail(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWOWNERDEPT"))
                        detail.setFlowOwnerDept(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWRESULT"))
                        detail.setFlowResult(strValue);
                    else
                    if(key.equalsIgnoreCase("FLOWDONEDATE"))
                    {
                        if(StringUtils.isNotEmpty(strValue))
                            try
                            {
                                detail.setFlowDoneDate(datefmt.parse(strValue));
                            }
                            catch(ParseException e)
                            {
                                e.printStackTrace();
                            }
                    } else
                    if(key.equalsIgnoreCase("SORTCODE"))
                        detail.setSortCode(strValue);
                }
            } while(true);
        }
        return detail;
    }

    public String getFlowDesc()
    {
        return flowDesc;
    }

    public String getFlowOwner()
    {
        return flowOwner;
    }

    public String getFlowOwnerMail()
    {
        return flowOwnerMail;
    }

    public String getFlowRealOwner()
    {
        return flowRealOwner;
    }

    public String getFlowRealOwnerMail()
    {
        return flowRealOwnerMail;
    }

    public String getFlowOwnerDept()
    {
        return flowOwnerDept;
    }

    public String getFlowResult()
    {
        return flowResult;
    }

    public Date getFlowDoneDate()
    {
        return flowDoneDate;
    }

    public Date getFlowEndDate()
    {
        return flowEndDate;
    }

    public String getFlowState()
    {
        return flowState;
    }

    public void setFlowDesc(String flowDesc)
    {
        this.flowDesc = flowDesc;
    }

    public void setFlowOwner(String flowOwner)
    {
        this.flowOwner = flowOwner;
    }

    public void setFlowOwnerMail(String flowOwnerMail)
    {
        this.flowOwnerMail = flowOwnerMail;
    }

    public void setFlowRealOwner(String flowRealOwner)
    {
        this.flowRealOwner = flowRealOwner;
    }

    public void setFlowRealOwnerMail(String flowRealOwnerMail)
    {
        this.flowRealOwnerMail = flowRealOwnerMail;
    }

    public void setFlowOwnerDept(String flowOwnerDept)
    {
        this.flowOwnerDept = flowOwnerDept;
    }

    public void setFlowResult(String flowResult)
    {
        this.flowResult = flowResult;
    }

    public void setFlowDoneDate(Date flowDoneDate)
    {
        this.flowDoneDate = flowDoneDate;
    }

    public void setFlowEndDate(Date flowEndDate)
    {
        this.flowEndDate = flowEndDate;
    }

    public void setFlowState(String flowState)
    {
        this.flowState = flowState;
    }

    public String getFlowOwnerName()
    {
        return flowOwnerName;
    }

    public void setFlowOwnerName(String flowOwnerName)
    {
        this.flowOwnerName = flowOwnerName;
    }

    public String getFlowRealOwnerName()
    {
        return flowRealOwnerName;
    }

    public void setFlowRealOwnerName(String flowRealOwnerName)
    {
        this.flowRealOwnerName = flowRealOwnerName;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd");
    private String flowDesc;
    private String flowOwner;
    private String flowOwnerName;
    private String flowOwnerMail;
    private String flowRealOwner;
    private String flowRealOwnerName;
    private String flowRealOwnerMail;
    private String flowOwnerDept;
    private String flowResult;
    private Date flowDoneDate;
    private Date flowEndDate;
    private String flowState;
    private String sortCode;

}
