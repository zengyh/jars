// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileUtils.java

package com.sinitek.spirit.webcontrol.utils;

import java.io.*;

public class FileUtils
{

    public FileUtils()
    {
    }

    private static void deleteFile(File file)
    {
        if(file.exists())
        {
            if(file.isFile())
                file.delete();
            else
            if(file.isDirectory())
            {
                File files[] = file.listFiles();
                File arr$[] = files;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    File file1 = arr$[i$];
                    deleteFile(file1);
                }

            }
            file.delete();
        }
    }

    public static void copyFile(File oldfile, File newFile)
        throws IOException
    {
        if(oldfile.exists())
        {
            InputStream inStream = new FileInputStream(oldfile);
            FileOutputStream fs = new FileOutputStream(newFile);
            byte buffer[] = new byte[1444];
            int byteread;
            while((byteread = inStream.read(buffer)) != -1) 
                fs.write(buffer, 0, byteread);
            inStream.close();
            fs.close();
        }
    }

    private static void removeEmptyDir(File dir)
    {
        if(dir.isDirectory())
        {
            File fs[] = dir.listFiles();
            if(fs != null && fs.length > 0)
            {
                File arr$[] = fs;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    File tmpFile = arr$[i$];
                    if(tmpFile.isDirectory())
                        removeEmptyDir(tmpFile);
                    if(tmpFile.isDirectory() && tmpFile.listFiles().length <= 0)
                        tmpFile.delete();
                }

            }
            if(dir.isDirectory() && dir.listFiles().length == 0)
                dir.delete();
        }
    }
}
