// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpUtils.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.common.exception.SirmAppException;
import com.sinitek.sirm.common.web.RequestContext;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            IOUtil, StringUtil

public class HttpUtils
{
    private static class DownloadFile
    {

        public InputStream getInputStream()
        {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream)
        {
            this.inputStream = inputStream;
        }

        public String getFileName()
        {
            return fileName;
        }

        public void setFileName(String fileName)
        {
            this.fileName = fileName;
        }

        protected InputStream inputStream;
        protected String fileName;

        private DownloadFile()
        {
        }

    }


    private HttpUtils()
    {
    }

    public static void download(HttpServletResponse response, File file)
    {
        download(response, createDownloadFile(file), false);
    }

    public static void download(HttpServletResponse response, String fileName, InputStream data)
    {
        download(response, createDownloadFile(fileName, data), false);
    }

    public static void open(HttpServletResponse response, File file)
    {
        download(response, createDownloadFile(file), true);
    }

    public static void open(HttpServletResponse response, String fileName, InputStream data)
    {
        download(response, createDownloadFile(fileName, data), true);
    }

    private static void download(HttpServletResponse response, DownloadFile file, boolean open)
    {
        String _fileName = file.getFileName();
        response.setContentType(getContentType(_fileName));
        response.setHeader("Content-Disposition", getDisposition(_fileName, open));
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Expires", "0");
        response.setHeader("Content-Transfer-Encoding", "binary");
        if(open)
        {
            File temp = IOUtil.copyToTempDir(file.getInputStream());
            try
            {
                file.setInputStream(new FileInputStream(temp));
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            Charset fileCharset = IOUtil.getFileEncoding(temp);
            if(fileCharset != null)
                response.setCharacterEncoding(fileCharset.toString());
            else
                response.setCharacterEncoding("UTF-8");
        }
        output(response, file);
    }

    private static String encodeFileName(String fileName)
    {
        String _returnFileName = null;
        try
        {
            _returnFileName = fileName;
            _returnFileName = URLEncoder.encode(fileName, "UTF-8");
            _returnFileName = StringUtils.replace(_returnFileName, "+", "%20");
            _returnFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            _returnFileName = StringUtils.replace(_returnFileName, " ", "%20");
        }
        catch(Exception e)
        {
            return fileName;
        }
        return _returnFileName;
    }

    public static String getDisposition(String fileName, boolean open)
    {
        return open ? (new StringBuilder()).append("filename=\"").append(encodeFileName(fileName)).append("\"").toString() : (new StringBuilder()).append("attachment; filename=\"").append(encodeFileName(fileName)).append("\"").toString();
    }

    public static String getContentType(String fileName)
    {
        String result = null;
        int i = fileName != null ? fileName.lastIndexOf('.') : -1;
        if(i >= 0)
        {
            String _key = StringUtils.lowerCase(fileName.substring(i + 1));
            Map contentTypeMap = new HashMap();
            contentTypeMap.put("txt", "text/plain");
            contentTypeMap.put("htm", "text/html");
            contentTypeMap.put("html", "text/html");
            contentTypeMap.put("xml", "text/xml");
            contentTypeMap.put("rtf", "text/richtext");
            contentTypeMap.put("doc", "application/msword");
            contentTypeMap.put("xls", "application/x-msexcel");
            contentTypeMap.put("ppt", "application/x-mspowerpoint");
            contentTypeMap.put("pdf", "application/pdf");
            contentTypeMap.put("bmp", "image/bmp");
            contentTypeMap.put("jpg", "image/jpeg");
            contentTypeMap.put("gif", "image/gif");
            contentTypeMap.put("exe", "application/octet-stream");
            result = (String)contentTypeMap.get(_key);
        }
        return result != null ? result : "application/unknown";
    }

    private static DownloadFile createDownloadFile(String fileName, InputStream fin)
    {
        DownloadFile file = new DownloadFile();
        file.setInputStream(fin);
        file.setFileName(fileName);
        return file;
    }

    private static DownloadFile createDownloadFile(File file)
    {
        DownloadFile _result = new DownloadFile();
        FileInputStream _fin = null;
        try
        {
            _fin = new FileInputStream(file);
        }
        catch(Exception ex)
        {
            log.error("createDownloadFile failed!", ex);
            throw new SirmAppException((new StringBuilder()).append("\u521B\u5EFADownloadFile\u51FA\u9519![").append(file.getAbsolutePath()).append("]").toString(), "0001");
        }
        _result.setInputStream(_fin);
        _result.setFileName(file.getName());
        return _result;
    }

    private static void output(HttpServletResponse response, DownloadFile file)
    {
        try
        {
            java.io.OutputStream _fout = response.getOutputStream();
            if(file != null)
            {
                InputStream _fin = file.getInputStream();
                IOUtils.copy(_fin, _fout);
                _fin.close();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static InputStream requestData(String location, Map data)
    {
        InputStream result = null;
        try
        {
            URL url = new URL(location);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = new StringBuffer();
            Iterator it = data.keySet().iterator();
            do
            {
                if(!it.hasNext())
                    break;
                Object k = it.next();
                String v = StringUtil.safeToString(data.get(k), null);
                if(v != null)
                    sb.append("&").append(k).append("=").append(URLEncoder.encode(v, "UTF-8"));
            } while(true);
            String content = sb.length() <= 0 ? "" : sb.substring(1);
            log.debug((new StringBuilder()).append("[content][").append(content).append("]").toString());
            out.writeBytes(content);
            out.flush();
            out.close();
            result = conn.getInputStream();
        }
        catch(IOException ex)
        {
            String msg = (new StringBuilder()).append("request failed![").append(location).append("]").toString();
            log.error(msg, ex);
            throw new SirmAppException(msg, "0001");
        }
        return result;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int age)
    {
        Cookie c = new Cookie(name, value);
        c.setValue(value);
        if(age > 0)
            c.setMaxAge(age);
        response.addCookie(c);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int age, String path)
    {
        Cookie c = new Cookie(name, value);
        c.setValue(value);
        if(age > 0)
            c.setMaxAge(age);
        c.setPath(path);
        response.addCookie(c);
    }

    public static void removeCookies(HttpServletResponse response, HttpServletRequest request)
    {
        Cookie cookies[] = request.getCookies();
        if(cookies != null && cookies.length > 0)
        {
            Cookie arr$[] = cookies;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                Cookie c = arr$[i$];
                c.setMaxAge(0);
                response.addCookie(c);
            }

        }
    }

    public static void removeCookies(HttpServletResponse response, String names[])
    {
        if(names != null && names.length > 0)
        {
            String arr$[] = names;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String n = arr$[i$];
                addCookie(response, n, "", 0);
            }

        }
    }

    public static String getCookie(HttpServletRequest request, String name)
    {
        String _result = null;
        Cookie cookies[] = request.getCookies();
        if(cookies != null && cookies.length > 0)
        {
            Cookie arr$[] = cookies;
            int len$ = arr$.length;
            int i$ = 0;
            do
            {
                if(i$ >= len$)
                    break;
                Cookie c = arr$[i$];
                if(c.getName().equals(name))
                {
                    _result = c.getValue();
                    break;
                }
                i$++;
            } while(true);
        }
        return _result;
    }

    public static boolean existResource(HttpServletRequest request, String resource)
    {
        boolean result = false;
        try
        {
            Object o = StringUtils.isBlank(resource) ? null : ((Object) (request.getSession().getServletContext().getResource(resource)));
            result = o != null;
        }
        catch(Exception ex) { }
        return result;
    }

    public static String getJsUrl(String url)
    {
        String result = url;
        if(url != null && url.indexOf("://") < 0 && url.startsWith("/"))
        {
            HttpServletRequest _request = RequestContext.getRequest();
            String contextpath = _request.getContextPath();
            if(StringUtils.isNotBlank(contextpath) && !url.startsWith(contextpath))
                result = (new StringBuilder()).append(contextpath).append(url).toString();
            Pattern pattern = Pattern.compile("_ssid_=([^\\&]*)");
            boolean isok = pattern.matcher(url).find();
            if(!isok)
            {
                String ssid = StringUtil.safeToString(_request.getSession().getAttribute("_ssid_"), "");
                if(StringUtils.isNotBlank(ssid))
                {
                    int p = url.indexOf("?");
                    if(p >= 0)
                        result = (new StringBuilder()).append(result).append("&_ssid_=").append(ssid).toString();
                    else
                        result = (new StringBuilder()).append(result).append("?_ssid_=").append(ssid).toString();
                }
            }
        }
        return result;
    }

    private static final Logger log = Logger.getLogger(com/sinitek/sirm/common/utils/HttpUtils);

}
