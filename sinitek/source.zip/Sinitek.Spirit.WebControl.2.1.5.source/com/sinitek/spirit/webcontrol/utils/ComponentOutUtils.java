// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComponentOutUtils.java

package com.sinitek.spirit.webcontrol.utils;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

class ComponentOutUtils
{

    ComponentOutUtils()
    {
    }

    public static void main(String s[])
        throws DocumentException, IOException
    {
        String path = "D:\\webcontrol-ui-tags.xml";
        String outPath = "D:\\componentOut\\";
        SAXReader reader = new SAXReader();
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        Document document = reader.read(f);
        List tags = document.selectNodes("/taglib/tag");
        FileOutputStream out;
        for(Iterator i$ = tags.iterator(); i$.hasNext(); out.close())
        {
            Element tag = (Element)i$.next();
            String file = "<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\" %>\n<%@ include file=\"../include/head.jsp\" %>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <%@ include file=\"../include/innerHead.jsp\" %>\n<%\n";
            String setParam = "";
            String setUI = "";
            String name = tag.selectSingleNode("name").getText();
            List attrs = tag.selectNodes("attribute");
            setUI = (new StringBuilder()).append(setUI).append("<ui:").append(name).toString();
            for(Iterator i$ = attrs.iterator(); i$.hasNext();)
            {
                Element a = (Element)i$.next();
                String attName = a.selectSingleNode("name").getText();
                setUI = (new StringBuilder()).append(setUI).append("\n ").append(attName).append("=\"${").append(attName).append("}\"").toString();
                setParam = (new StringBuilder()).append(setParam).append("pageContext.setAttribute(\"").append(attName).append("\",request.getParameter(\"").append(attName).append("\"));\n").toString();
            }

            setUI = (new StringBuilder()).append(setUI).append("></ui:").append(name).append(">\n").toString();
            file = (new StringBuilder()).append(file).append(setParam).append("%>\n").append("</head>\n").append("<body>\n").toString();
            file = (new StringBuilder()).append(file).append(setUI).toString();
            file = (new StringBuilder()).append(file).append("</body>\n</html>").toString();
            out = new FileOutputStream((new StringBuilder()).append(outPath).append(name).append(".jsp").toString());
            out.write(file.getBytes());
        }

    }
}
