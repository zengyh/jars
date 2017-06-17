// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContext.java

package com.sinitek.base.metadb;

import java.io.File;
import java.io.RandomAccessFile;

public class MetaDBContext
{

    public MetaDBContext()
    {
    }

    public static void outTest(String path, String mark)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(mark).append(": ").append(System.currentTimeMillis()).append("ms\r\n");
        String context = sb.toString();
        try
        {
            File file = new File(path);
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(!file.exists())
                file.createNewFile();
            RandomAccessFile rf = new RandomAccessFile(path, "rw");
            rf.seek(rf.length());
            rf.write(context.getBytes("utf-8"));
            rf.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int TYPE = 0;
    public static final int TYPE_ORACLE = 0;
    public static final int TYPE_MYSQL = 1;

}
