// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileUtil.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.common.exception.SirmAppException;
import info.monitorenter.cpdetector.io.*;
import java.io.*;
import java.net.URI;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            TimeUtil

public class FileUtil
{

    public FileUtil()
    {
    }

    public static synchronized File copyToTempDir(String targetDir, InputStream fin)
    {
        File _file;
        OutputStream _fout;
        File _dir = new File(targetDir);
        if(!_dir.exists())
        {
            _dir.mkdirs();
            try
            {
                Thread.sleep(100L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        String _prefix = (new StringBuilder()).append(targetDir).append(File.separatorChar).append(TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss")).toString();
        String _suffix = ".dat";
        String _fileName = (new StringBuilder()).append(_prefix).append(_suffix).toString();
        int _no = 2;
        for(_file = new File(_fileName); _file.exists();)
        {
            _fileName = (new StringBuilder()).append(_prefix).append("_").append(_no).append(_suffix).toString();
            _file = new File(_fileName);
            _no++;
        }

        _fout = null;
        _fout = new FileOutputStream(_file);
        IOUtils.copy(fin, _fout);
        Exception ex;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_297;
        ex;
        _file = null;
        LOGGER.warn("copy file failed!", ex);
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_297;
        Exception exception;
        exception;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            catch(Exception ex) { }
        throw exception;
        return _file;
    }

    public static synchronized File copyFile(String targetFile, InputStream fin)
    {
        File _file;
        OutputStream _fout;
        _file = new File(targetFile);
        File _dir = _file.getParentFile();
        if(!_dir.exists())
        {
            _dir.mkdirs();
            try
            {
                Thread.sleep(100L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        String _fileName = _file.getAbsolutePath();
        int p = _fileName.lastIndexOf(".");
        String _prefix = p >= 0 ? _fileName.substring(0, p) : _fileName;
        String _suffix = p >= 0 ? _fileName.substring(p) : "";
        for(int _no = 2; _file.exists(); _no++)
        {
            _fileName = (new StringBuilder()).append(_prefix).append("_").append(_no).append(_suffix).toString();
            _file = new File(_fileName);
        }

        _fout = null;
        _fout = new FileOutputStream(_file);
        IOUtils.copy(fin, _fout);
        Exception ex;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_283;
        ex;
        _file = null;
        LOGGER.warn("copy file failed!", ex);
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_283;
        Exception exception;
        exception;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            catch(Exception ex) { }
        throw exception;
        return _file;
    }

    public static synchronized File copyToTempDir(String filename, String suffix, String targetDir, InputStream fin)
    {
        File _file;
        OutputStream _fout;
        File _dir = new File(targetDir);
        if(!_dir.exists())
        {
            _dir.mkdirs();
            try
            {
                Thread.sleep(100L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        String _prefix = (new StringBuilder()).append(targetDir).append(File.separatorChar).append(filename).toString();
        String _suffix = (new StringBuilder()).append(".").append(suffix).toString();
        String _fileName = (new StringBuilder()).append(_prefix).append(_suffix).toString();
        int _no = 2;
        for(_file = new File(_fileName); _file.exists();)
        {
            _fileName = (new StringBuilder()).append(_prefix).append("_").append(_no).append(_suffix).toString();
            _file = new File(_fileName);
            _no++;
        }

        _fout = null;
        _fout = new FileOutputStream(_file);
        IOUtils.copy(fin, _fout);
        Exception ex;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_311;
        ex;
        _file = null;
        LOGGER.warn("copy file failed!", ex);
        if(_fout != null)
            try
            {
                _fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_311;
        Exception exception;
        exception;
        if(_fout != null)
            try
            {
                _fout.close();
            }
            catch(Exception ex) { }
        if(fin != null)
            try
            {
                fin.close();
            }
            catch(Exception ex) { }
        throw exception;
        return _file;
    }

    public static String formatFileTitle(String fileTitle)
    {
        String rs = fileTitle.replaceAll("\u2014", "-");
        rs = rs.replaceAll("\\?", "");
        rs = rs.replaceAll("\\\\", "");
        rs = rs.replaceAll("/", "");
        rs = rs.replaceAll("\\*", "");
        rs = rs.replaceAll("\\|", "");
        rs = rs.replaceAll("\\\\", "");
        if(rs.length() >= 128)
            rs = rs.substring(0, 128);
        return rs;
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
        file.delete();
        for(int i = 0; i < 3 && file.exists(); i++)
            try
            {
                Thread.sleep(100 * i);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

    }

    public static void copy(InputStream fin, OutputStream fout)
    {
        try
        {
            byte _buffer[] = new byte[1024];
            for(int _size = fin.read(_buffer); _size > 0; _size = fin.read(_buffer))
                fout.write(_buffer, 0, _size);

            fout.flush();
        }
        catch(IOException e)
        {
            LOGGER.warn("io copy error!", e);
            throw new SirmAppException("io copy error!", "0001");
        }
    }

    public static void closeInputStream(InputStream fin)
    {
        if(fin != null)
            try
            {
                fin.close();
            }
            catch(IOException ex)
            {
                String _msg = "close inputstream error!";
                LOGGER.warn(_msg, ex);
                throw new SirmAppException(_msg, "0001");
            }
    }

    public static void closeOutputStream(OutputStream fout)
    {
        if(fout != null)
            try
            {
                fout.close();
            }
            catch(IOException ex)
            {
                String _msg = "close outputstream error!";
                LOGGER.warn(_msg, ex);
                throw new SirmAppException(_msg, "0001");
            }
    }

    public static transient String combinePath(String paths[])
    {
        String result = null;
        if(paths.length > 0)
        {
            String part = paths[0];
            StringBuffer sb = new StringBuffer(part);
            for(int i = 1; i < paths.length; i++)
            {
                if(!part.endsWith(String.valueOf(File.separatorChar)))
                    sb.append(File.separatorChar);
                part = paths[i];
                sb.append(part);
            }

            result = sb.toString();
        }
        return result;
    }

    public static Charset getFileEncoding(File file)
    {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        try
        {
            charset = detector.detectCodepage(file.toURI().toURL());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return charset;
    }

    public static byte[] convertFileEncodingToSys(File inFile)
        throws IOException
    {
        return convertFileEncoding(inFile, (File)null, Charset.defaultCharset());
    }

    public static byte[] convertFileEncodingToSys(File inFile, File outFile)
        throws IOException
    {
        return convertFileEncoding(inFile, outFile, Charset.defaultCharset());
    }

    public static byte[] convertFileEncoding(File inFile, File outFile, Charset outCharset)
        throws IOException
    {
        return convertFileEncoding(inFile, getFileEncoding(inFile), outFile, outCharset);
    }

    public static byte[] convertFileEncoding(File inFile, Charset inCharset, File outFile, Charset outCharset)
        throws IOException
    {
        RandomAccessFile inRandom;
        FileChannel inChannel;
        CharsetEncoder outEncoder;
        CharBuffer cb;
        ByteBuffer outBuffer;
        inRandom = new RandomAccessFile(inFile, "r");
        inChannel = inRandom.getChannel();
        MappedByteBuffer byteMapper = inChannel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, (int)inFile.length());
        CharsetDecoder inDecoder = inCharset.newDecoder();
        outEncoder = outCharset.newEncoder();
        cb = inDecoder.decode(byteMapper);
        outBuffer = null;
        RandomAccessFile outRandom;
        FileChannel outChannel;
        outBuffer = outEncoder.encode(cb);
        outRandom = null;
        outChannel = null;
        if(outFile == null)
            break MISSING_BLOCK_LABEL_154;
        outRandom = new RandomAccessFile(outFile, "rw");
        outChannel = outRandom.getChannel();
        outChannel.write(outBuffer);
        if(outChannel != null)
            outChannel.close();
        if(outRandom != null)
            outRandom.close();
        break MISSING_BLOCK_LABEL_154;
        Exception exception;
        exception;
        if(outChannel != null)
            outChannel.close();
        if(outRandom != null)
            outRandom.close();
        throw exception;
        inChannel.close();
        inRandom.close();
        break MISSING_BLOCK_LABEL_182;
        Exception exception1;
        exception1;
        inChannel.close();
        inRandom.close();
        throw exception1;
        return outBuffer.array();
    }

    public static String getFilePrefix(String fileFullName)
    {
        int splitIndex = fileFullName.lastIndexOf(".");
        return fileFullName.substring(0, splitIndex);
    }

    public static String getFilePrefix(File file)
    {
        String fileFullName = file.getName();
        return getFilePrefix(fileFullName);
    }

    public static String getFileSuffix(String fileFullName)
    {
        int splitIndex = fileFullName.lastIndexOf(".");
        return fileFullName.substring(splitIndex + 1);
    }

    public static String getFileSuffix(File file)
    {
        String fileFullName = file.getName();
        return getFileSuffix(fileFullName);
    }

    public static byte[] getBytesFromFile(File f)
    {
        if(f == null)
            return null;
        try
        {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte b[] = new byte[1000];
            int n;
            while((n = stream.read(b)) != -1) 
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        }
        catch(IOException e)
        {
            return null;
        }
    }

    public static File getFileFromBytes(byte b[], String outputFile)
    {
        BufferedOutputStream stream;
        File file;
        stream = null;
        file = null;
        file = new File(outputFile);
        FileOutputStream fstream = new FileOutputStream(file);
        stream = new BufferedOutputStream(fstream);
        stream.write(b);
        if(stream != null)
            try
            {
                stream.close();
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_110;
        Exception e;
        e;
        e.printStackTrace();
        if(stream != null)
            try
            {
                stream.close();
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_110;
        Exception exception;
        exception;
        if(stream != null)
            try
            {
                stream.close();
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
        throw exception;
        return file;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/utils/FileUtil);

}
