// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritUserConfigFacory.java

package com.sinitek.spirit.webcontrol.userconfig;

import com.sinitek.spirit.webcontrol.utils.DwrUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

public class SpiritUserConfigFacory
{

    private SpiritUserConfigFacory(HttpServletRequest request)
    {
        init(request);
    }

    public static synchronized SpiritUserConfigFacory getInstance(HttpServletRequest request)
    {
        if(instance == null)
            instance = new SpiritUserConfigFacory(request);
        return instance;
    }

    void init(HttpServletRequest request)
    {
        try
        {
            Document document = DwrUtils.loadDwrConfig(request);
            Element e = (Element)document.selectSingleNode("/dwr/allow/create[@javascript='UserConfigAware']/param");
            className = e.attributeValue("value");
        }
        catch(Exception e)
        {
            LOG.error("\u8BFB\u53D6dwr.xml\u5931\u8D25", e);
            throw new RuntimeException("\u8BFB\u53D6dwr.xml\u5931\u8D25", e);
        }
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/userconfig/SpiritUserConfigFacory);
    private static SpiritUserConfigFacory instance;
    private String className;

}
