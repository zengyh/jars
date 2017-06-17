// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOUtil.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            FileUtil, TimeUtil

public class IOUtil
{

    public IOUtil()
    {
    }

    public static synchronized File copyToTempDir(File file)
    {
        String _tempDir = SettingUtils.getStringValue("COMMON", "TEMPDIR");
        return copyToTempDir(_tempDir, file);
    }

    public static synchronized File copyToTempDir(InputStream fin)
    {
        String _tempDir = SettingUtils.getStringValue("COMMON", "TEMPDIR");
        return copyToTempDir(_tempDir, fin);
    }

    public static synchronized File copyToTempDir(String targetDir, File file)
    {
        try
        {
            return copyToTempDir(targetDir, ((InputStream) (new FileInputStream(file))));
        }
        catch(IOException ex)
        {
            LOGGER.warn("copy file failed!", ex);
        }
        return null;
    }

    public static synchronized File copyToTempDir(String targetDir, InputStream fin)
    {
        return FileUtil.copyToTempDir(targetDir, fin);
    }

    public static synchronized File copyFile(String targetFile, InputStream fin)
    {
        return FileUtil.copyFile(targetFile, fin);
    }

    public static void deleteFile(String fileName)
    {
        deleteFile(fileName, 100L);
    }

    public static void deleteFile(File file)
    {
        deleteFile(file, 100L);
    }

    public static void deleteFile(String fileName, long millis)
    {
        deleteFile(new File(fileName), millis);
    }

    public static void deleteFile(File file, long millis)
    {
        FileUtil.deleteFile(file, millis);
    }

    public static void copy(InputStream fin, OutputStream fout)
    {
        FileUtil.copy(fin, fout);
    }

    public static void closeInputStream(InputStream fin)
    {
        FileUtil.closeInputStream(fin);
    }

    public static void closeOutputStream(OutputStream fout)
    {
        FileUtil.closeOutputStream(fout);
    }

    public static synchronized File createTempDir()
    {
        String _tempDir = SettingUtils.getStringValue("COMMON", "TEMPDIR");
        int count = 2;
        String _prefix = (new StringBuilder()).append(_tempDir).append(File.separatorChar).append(TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss")).toString();
        File _dir;
        for(_dir = new File(_prefix); _dir.exists();)
        {
            String f = (new StringBuilder()).append(_prefix).append("_").append(count).toString();
            _dir = new File(f);
            count++;
        }

        _dir.mkdirs();
        try
        {
            Thread.sleep(100L);
        }
        catch(InterruptedException e)
        {
            LOGGER.warn("create tempdir failed!", e);
        }
        return _dir;
    }

    public static synchronized File getTempDir()
    {
        return new File(SettingUtils.getStringValue("COMMON", "TEMPDIR"));
    }

    public static synchronized File createTempFile()
    {
        return createTempFile("dat");
    }

    public static synchronized File createTempFile(String suffix)
    {
        return createTempFile(suffix, null);
    }

    public static synchronized File createTempFile(String suffix, String _tempDir)
    {
        if(_tempDir == null)
            _tempDir = SettingUtils.getStringValue("COMMON", "TEMPDIR");
        int count = 2;
        String _prefix = (new StringBuilder()).append(_tempDir).append(File.separatorChar).append(TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss")).toString();
        File _file;
        for(_file = new File((new StringBuilder()).append(_prefix).append(".").append(suffix).toString()); _file.exists();)
        {
            String f = (new StringBuilder()).append(_prefix).append("_").append(count).append(".").append(suffix).toString();
            _file = new File(f);
            count++;
        }

        try
        {
            _file.createNewFile();
        }
        catch(Exception ex)
        {
            LOGGER.error("create file failed!", ex);
            _file = null;
        }
        try
        {
            Thread.sleep(100L);
        }
        catch(InterruptedException e) { }
        return _file;
    }

    public static String combinePath(String paths[])
    {
        return FileUtil.combinePath(paths);
    }

    public static Charset getFileEncoding(File file)
    {
        return FileUtil.getFileEncoding(file);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/utils/IOUtil);

}
