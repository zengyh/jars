// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterConfigReader.java

package com.sinitek.base.starter.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.common.xml.ClassLoaderEntityResolver;
import com.sinitek.base.starter.StarterException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

// Referenced classes of package com.sinitek.base.starter.config:
//            StarterConfigInfo

public class StarterConfigReader
{

    public StarterConfigReader()
    {
    }

    public static List readConfig(InputStream is)
        throws IOException, DocumentException
    {
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new ClassLoaderEntityResolver("-//SINITEK//STARTER//STARTERCONFIG//1_0", "com/sinitek/base/starter/config/starterconfig_1_0.dtd"));
        Document doc = reader.read(is);
        Element root = doc.getRootElement();
        List starters = root.elements("starter");
        List ret = new ArrayList(starters.size());
        StarterConfigInfo info;
        for(Iterator iter = starters.iterator(); iter.hasNext(); ret.add(info))
        {
            Element starterEle = (Element)iter.next();
            info = new StarterConfigInfo();
            info.setStarterClass(starterEle.attributeValue("class").trim());
            info.setIgnoreOnError("Y".equalsIgnoreCase(starterEle.attributeValue("ignoreonerror")));
            List props = starterEle.elements("prop");
            Properties prop = new Properties();
            Element propEle;
            for(Iterator propIter = props.iterator(); propIter.hasNext(); prop.setProperty(propEle.attributeValue("name"), propEle.getTextTrim()))
                propEle = (Element)propIter.next();

            info.setProps(prop);
        }

        return ret;
    }

    public static List readConfig(URL url)
        throws StarterException
    {
        InputStream is;
        Exception exception;
        if(!$assertionsDisabled && url == null)
            throw new AssertionError();
        is = null;
        List list;
        try
        {
            is = url.openStream();
            list = readConfig(is);
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception ex)
        {
            throw new StarterException("0001", ex, new Object[] {
                url.toExternalForm()
            });
        }
        finally
        {
            if(is == null) goto _L0; else goto _L0
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95ED\u542F\u52A8\u5668\u914D\u7F6E\u6587\u4EF6\u5931\u8D25", e);
            }
        return list;
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95ED\u542F\u52A8\u5668\u914D\u7F6E\u6587\u4EF6\u5931\u8D25", e);
        }
        throw exception;
    }

    public static List readConfig(String path)
        throws StarterException
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if(url == null)
        {
            throw new StarterException("0002", new Object[] {
                path
            });
        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u8BFB\u53D6\u542F\u52A8\u5668\u914D\u7F6E\u6587\u4EF6[").append(path).append("]").toString());
            return readConfig(url);
        }
    }

    public static List readConfig(File file)
        throws StarterException
    {
        if(!$assertionsDisabled && file == null)
            throw new AssertionError();
        return readConfig(file.toURL());
        MalformedURLException e;
        e;
        throw new StarterException("0001", e, new Object[] {
            file.toString()
        });
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/starter/config/StarterConfigReader);
    private static final String PUBLIC_ID_1_0 = "-//SINITEK//STARTER//STARTERCONFIG//1_0";
    static final boolean $assertionsDisabled = !com/sinitek/base/starter/config/StarterConfigReader.desiredAssertionStatus();

}
