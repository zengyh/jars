// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoFileUploadAction.java

package com.sinitek.spirit.webcontrol.demo;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.io.FileTransfer;

public class DemoFileUploadAction
{

    public DemoFileUploadAction()
    {
    }

    public static String upload(Map param, Map fileMap, HttpServletRequest request)
    {
        List files = (List)fileMap.get("files");
        if(files != null && files.size() > 0)
        {
            FileTransfer file = (FileTransfer)files.get(0);
            return (new StringBuilder()).append("\u4E0A\u4F20\u6587\u4EF6:").append(files.size()).append("\u7B2C\u4E00\u4E2A\u6587\u4EF6\uFF1A").append(file.getFilename()).toString();
        } else
        {
            return "\u6CA1\u6709\u4E0A\u4F20";
        }
    }
}
