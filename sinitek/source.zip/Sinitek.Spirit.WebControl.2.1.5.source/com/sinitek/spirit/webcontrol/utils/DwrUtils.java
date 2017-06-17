// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DwrUtils.java

package com.sinitek.spirit.webcontrol.utils;

import java.io.File;
import java.io.StringReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DwrUtils
{

    public DwrUtils()
    {
    }

    public static Document loadDwrConfig(HttpServletRequest request)
        throws Exception
    {
        String fileName = (new StringBuilder()).append(request.getSession().getServletContext().getRealPath("/")).append("WEB-INF").append(File.separator).append("dwr.xml").toString();
        File f = new File(fileName);
        SAXReader reader = new SAXReader(false);
        reader.setEntityResolver(new EntityResolver() {

            public InputSource resolveEntity(String publicId, String systemId)
            {
                return new InputSource(new StringReader(""));
            }

        }
);
        return reader.read(f);
    }
}
