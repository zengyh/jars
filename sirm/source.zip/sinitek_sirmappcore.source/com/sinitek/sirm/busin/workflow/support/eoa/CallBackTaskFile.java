// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallBackTaskFile.java

package com.sinitek.sirm.busin.workflow.support.eoa;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public class CallBackTaskFile
    implements Serializable
{

    public CallBackTaskFile()
    {
    }

    public static CallBackTaskFile newInstanceByMap(Map input)
    {
        CallBackTaskFile file = null;
        if(input != null)
        {
            file = new CallBackTaskFile();
            String temp = (String)input.get("DOCNAME");
            if(StringUtils.isNotEmpty(temp))
                file.eoaNfsUrl = temp;
            temp = (String)input.get("DOCOLDNAME");
            if(StringUtils.isNotEmpty(temp))
                file.docName = temp;
            temp = (String)input.get("OPERATOR");
            if(StringUtils.isNotEmpty(temp))
                file.operator = temp;
            Date time = (Date)input.get("OPERATETIME");
            if(time != null)
                file.operateTime = time;
            temp = (String)input.get("DOCCLASS");
            if(StringUtils.isNotEmpty(temp))
                file.docClass = temp;
            Double len = (Double)input.get("FILESIZE");
            if(len != null)
                file.fileSize = len.doubleValue();
        }
        return file;
    }

    public static CallBackTaskFile newInstanceByXml(Element xml)
    {
        CallBackTaskFile file = null;
        if(xml != null)
        {
            file = new CallBackTaskFile();
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
                    if(key.equalsIgnoreCase("DOCNAME"))
                        file.setEoaNfsUrl(strValue);
                    else
                    if(key.equalsIgnoreCase("DOCOLDNAME"))
                        file.setDocName(strValue);
                    else
                    if(key.equalsIgnoreCase("OPERATOR"))
                        file.setOperator(strValue);
                    else
                    if(key.equalsIgnoreCase("OPERATETIME"))
                    {
                        if(StringUtils.isNotEmpty(strValue))
                            try
                            {
                                file.setOperateTime(datefmt.parse(strValue));
                            }
                            catch(ParseException e)
                            {
                                e.printStackTrace();
                            }
                    } else
                    if(key.equalsIgnoreCase("DOCCLASS"))
                        file.setDocClass(strValue);
                    else
                    if(key.equalsIgnoreCase("fileSize") && StringUtils.isNotEmpty(strValue))
                        file.setFileSize(Double.valueOf(strValue).doubleValue());
                }
            } while(true);
        }
        return file;
    }

    public String getEoaNfsUrl()
    {
        return eoaNfsUrl;
    }

    public String getDocName()
    {
        return docName;
    }

    public String getOperator()
    {
        return operator;
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public String getDocClass()
    {
        return docClass;
    }

    public double getFileSize()
    {
        return fileSize;
    }

    public void setEoaNfsUrl(String eoaNfsUrl)
    {
        this.eoaNfsUrl = eoaNfsUrl;
    }

    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }

    public void setDocClass(String docClass)
    {
        this.docClass = docClass;
    }

    public void setFileSize(double fileSize)
    {
        this.fileSize = fileSize;
    }

    public static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd");
    private String eoaNfsUrl;
    private String docName;
    private String operator;
    private Date operateTime;
    private String docClass;
    private double fileSize;

}
