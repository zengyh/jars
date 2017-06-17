// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtendDataLoader.java

package com.sinitek.sirm.common.support;

import com.sinitek.sirm.common.loader.utils.AppLoader;
import com.sinitek.sirm.common.loader.utils.IAppLoaderContext;
import com.sinitek.sirm.common.utils.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

// Referenced classes of package com.sinitek.sirm.common.support:
//            LocalSetting

public class ExtendDataLoader
    implements AppLoader
{

    public ExtendDataLoader()
    {
        _isInitialized = false;
    }

    public boolean isInitialized()
    {
        return _isInitialized;
    }

    public void initialize(IAppLoaderContext context)
    {
        LogUtils.debug(com/sinitek/sirm/common/support/ExtendDataLoader, "------------------\u5F00\u59CB\u52A0\u8F7D\u5916\u90E8\u6570\u636E\u914D\u7F6E\u7684XML\u6587\u4EF6-------------------");
        InputStream fin = context.getResourceAsStream("/WEB-INF/extendpage.xml");
        if(fin != null)
        {
            try
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(fin);
                NodeList nl = doc.getElementsByTagName("page");
                for(int i = 0; i < nl.getLength(); i++)
                {
                    String code = nl.item(i).getAttributes().getNamedItem("code").getNodeValue();
                    EXTENDDATA.add(code);
                }

            }
            catch(IOException ex)
            {
                LogUtils.warn(com/sinitek/sirm/common/support/LocalSetting, ex);
            }
            catch(ParserConfigurationException e)
            {
                LogUtils.warn(com/sinitek/sirm/common/support/LocalSetting, e);
            }
            catch(SAXException e)
            {
                LogUtils.warn(com/sinitek/sirm/common/support/LocalSetting, e);
            }
            LogUtils.debug(com/sinitek/sirm/common/support/ExtendDataLoader, (new StringBuilder()).append("------------------\u7ED3\u675F\u52A0\u8F7D\u5916\u90E8\u6570\u636E\u914D\u7F6E\u7684XML\u6587\u4EF6\uFF08").append(EXTENDDATA.size()).append("\uFF09---------------").toString());
        }
        _isInitialized = true;
    }

    public void destroy(IAppLoaderContext context)
    {
        EXTENDDATA.clear();
        _isInitialized = false;
    }

    public static boolean isConfigExtendCode(String code)
    {
        boolean isConfig;
label0:
        {
            isConfig = false;
            if(EXTENDDATA == null || EXTENDDATA.size() <= 0)
                break label0;
            Iterator i$ = EXTENDDATA.iterator();
            String temp;
            do
            {
                if(!i$.hasNext())
                    break label0;
                temp = (String)i$.next();
            } while(!code.equals(temp));
            isConfig = true;
        }
        return isConfig;
    }

    private static List EXTENDDATA = new ArrayList();
    private boolean _isInitialized;

}
