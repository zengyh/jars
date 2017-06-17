// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassLoaderEntityResolver.java

package com.sinitek.base.common.xml;

import java.io.IOException;
import java.net.URL;
import org.xml.sax.*;

public class ClassLoaderEntityResolver
    implements EntityResolver
{

    public ClassLoaderEntityResolver(String publicId, String resPath)
    {
        this.publicId = publicId;
        this.resPath = resPath;
    }

    public InputSource resolveEntity(String publicId, String systemId)
        throws SAXException, IOException
    {
        if(publicId.equals(this.publicId))
        {
            URL url = Thread.currentThread().getContextClassLoader().getResource(resPath);
            if(url == null)
                throw new SAXException((new StringBuilder()).append("publicid[").append(publicId).append("]\u6307\u5B9A\u7684classpath dtd\u6587\u4EF6[").append(resPath).append("]\u4E0D\u5B58\u5728").toString());
            else
                return new InputSource(url.openStream());
        } else
        {
            return null;
        }
    }

    private String publicId;
    private String resPath;
}
