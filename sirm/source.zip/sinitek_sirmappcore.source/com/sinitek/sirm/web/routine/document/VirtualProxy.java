// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VirtualProxy.java

package com.sinitek.sirm.web.routine.document;

import com.sinitek.sirm.common.web.SirmAction;
import java.io.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;

// Referenced classes of package com.sinitek.sirm.web.routine.document:
//            MyDemoServlet

public class VirtualProxy extends SirmAction
{

    public VirtualProxy()
    {
    }

    public String virtualproxy()
        throws IOException
    {
        String path = request.getParameter("p");
        path = FilenameUtils.normalize(path);
        File file = new File(MyDemoServlet.HOME_SHARED_DOCS, path);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        for(byte outputByte[] = new byte[4096]; fileIn.read(outputByte, 0, 4096) != -1; out.write(outputByte, 0, 4096));
        fileIn.close();
        out.flush();
        out.close();
        return null;
    }
}
