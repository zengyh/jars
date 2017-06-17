// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyDemoConfig.java

package com.sinitek.sirm.web.routine.document;

import java.io.File;
import org.elfinder.servlets.config.AbstractConnectorConfig;
import org.elfinder.servlets.fs.DiskFsImpl;
import org.elfinder.servlets.fs.IFsImpl;

// Referenced classes of package com.sinitek.sirm.web.routine.document:
//            MyDemoServlet

public class MyDemoConfig extends AbstractConnectorConfig
{

    public MyDemoConfig()
    {
        fsImpl = new DiskFsImpl();
    }

    public IFsImpl getFs()
    {
        return fsImpl;
    }

    public String getRoot()
    {
        return MyDemoServlet.HOME_SHARED_DOCS;
    }

    public String getRootUrl()
    {
        return MyDemoServlet.REALOBJECTURL;
    }

    public String getFileUrl(File path)
    {
        return (new StringBuilder()).append(getRootUrl()).append("/").append(getRelativePath(path)).toString();
    }

    public String rootAliasOrBaseName()
    {
        return MyDemoServlet.SHARED_DOCS;
    }

    public String getThumbnailUrl(File path)
    {
        return (new StringBuilder()).append(MyDemoServlet.THUMBNAIL).append(getRelativePath(path)).toString();
    }

    public boolean hasThumbnail(File path)
    {
        String mimeType = getMime(path);
        return mimeType.contains("image");
    }

    private DiskFsImpl fsImpl;
}
