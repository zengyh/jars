// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WordUtils.java

package com.sinitek.sirm.common.office;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.*;
import java.io.*;
import java.util.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.sinitek.sirm.common.office:
//            WordUpdateException

public class WordUtils
{

    public WordUtils()
    {
    }

    public static String filter(String str)
    {
        String result = str;
        if(result != null)
        {
            StringBuffer sb = new StringBuffer();
            boolean isok = false;
            char chars[] = str.toCharArray();
            for(int i = 0; i < chars.length; i++)
            {
                char c = chars[i];
                if(c == '\023' || c == '\001')
                {
                    isok = true;
                    continue;
                }
                if(c == '\007' || c == '\024' || c == '\025')
                {
                    isok = false;
                    continue;
                }
                if(!isok)
                    sb.append(c);
            }

            result = sb.toString();
        }
        return result;
    }

    public static String updateUsingExternalTool(String templatepath, Map data)
    {
        String toolpath = CommonServiceFactory.getSettingService().getSetting("COMMON", "WORDPARSERPATH").getValue();
        return update(toolpath, templatepath, data);
    }

    public static String updateUsingExternalTool(InputStream templatedata, Map data)
    {
        File file;
        FileOutputStream fout;
        file = IOUtil.createTempFile("doc");
        fout = null;
        fout = new FileOutputStream(file);
        IOUtils.copy(templatedata, fout);
        Exception e;
        if(fout != null)
            try
            {
                fout.flush();
                fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception e)
            {
                e.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_107;
        e;
        e.printStackTrace();
        if(fout != null)
            try
            {
                fout.flush();
                fout.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception e)
            {
                e.printStackTrace();
            }
        break MISSING_BLOCK_LABEL_107;
        Exception exception;
        exception;
        if(fout != null)
            try
            {
                fout.flush();
                fout.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        throw exception;
        return updateUsingExternalTool(file.getAbsolutePath(), data);
    }

    public static String update(String toolpath, String templatepath, Map data)
    {
        if(!(new File(toolpath)).exists())
            throw new WordUpdateException("SIRMERROR0001", "\u5916\u90E8\u5E94\u7528\u7A0B\u5E8F\u4E0D\u5B58\u5728");
        if(!(new File(templatepath)).exists())
            throw new WordUpdateException("SIRMERROR0002", "Word\u6A21\u677F\u539F\u6587\u4EF6\u4E0D\u5B58\u5728");
        String tempFile = null;
        try
        {
            tempFile = IOUtil.createTempFile("json").getAbsolutePath();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
            Writer jsonwriter = (new JSONObject(data)).write(writer);
            jsonwriter.flush();
            jsonwriter.close();
        }
        catch(Exception e)
        {
            LogUtils.error(com/sinitek/sirm/common/office/WordUtils, e);
            throw new WordUpdateException("SIRMERROR0003", "\u751F\u6210json\u6587\u4EF6\u9519\u8BEF");
        }
        SystemUtil.execCommand((new StringBuilder()).append(toolpath).append(" -t:update ").append(templatepath).append(" ").append(tempFile).toString(), true);
        String jsonfile = (new StringBuilder()).append(templatepath).append(".json").toString();
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(jsonfile)));
            while((line = br.readLine()) != null) 
                stringBuffer.append(line);
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            String code = ((JSONObject)(JSONObject)jsonObject.getJSONArray("error").get(0)).getString("code");
            String filepath = ((JSONObject)(JSONObject)jsonObject.getJSONArray("data").get(0)).getString("filepath");
            if(code.equals("SIRMERROR0005"))
                throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
            if(code.equals("SIRMERROR0004"))
                throw new WordUpdateException("SIRMERROR0004", "\u751F\u6210.update.doc\u6587\u4EF6\u9519\u8BEF");
            else
                return filepath;
        }
        catch(Exception e)
        {
            throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
        }
    }

    public static String merge(List filepaths)
    {
        String toolpath = CommonServiceFactory.getSettingService().getSetting("COMMON", "WORDPARSERPATH").getValue();
        if(!(new File(toolpath)).exists())
            throw new WordUpdateException("SIRMERROR0001", "\u5916\u90E8\u5E94\u7528\u7A0B\u5E8F\u4E0D\u5B58\u5728");
        String tempFile = null;
        String outPath = null;
        Map data = new HashMap();
        try
        {
            tempFile = IOUtil.createTempFile("json").getAbsolutePath();
            outPath = IOUtil.createTempFile("json").getAbsolutePath();
            data.put("filepaths", filepaths);
            data.put("outpath", outPath);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
            Writer jsonwriter = (new JSONObject(data)).write(writer);
            jsonwriter.flush();
            jsonwriter.close();
        }
        catch(Exception e)
        {
            LogUtils.error(com/sinitek/sirm/common/office/WordUtils, e);
            throw new WordUpdateException("SIRMERROR0003", "\u751F\u6210json\u6587\u4EF6\u9519\u8BEF");
        }
        SystemUtil.execCommand((new StringBuilder()).append(toolpath).append(" -t:merge ").append(tempFile).toString(), true);
        String jsonfile = outPath;
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(jsonfile)));
            while((line = br.readLine()) != null) 
                stringBuffer.append(line);
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            String code = ((JSONObject)(JSONObject)jsonObject.getJSONArray("error").get(0)).getString("code");
            String filepath = ((JSONObject)(JSONObject)jsonObject.getJSONArray("data").get(0)).getString("filepath");
            if(code.equals("SIRMERROR0005"))
                throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
            if(code.equals("SIRMERROR0006"))
                throw new WordUpdateException("SIRMERROR0006", "\u751F\u6210.update.doc\u6587\u4EF6\u9519\u8BEF");
            else
                return filepath;
        }
        catch(Exception e)
        {
            throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
        }
    }

    public static String merge2(List filepaths, int type)
    {
        String toolpath = CommonServiceFactory.getSettingService().getSetting("COMMON", "WORDPARSERPATH").getValue();
        if(!(new File(toolpath)).exists())
            throw new WordUpdateException("SIRMERROR0001", "\u5916\u90E8\u5E94\u7528\u7A0B\u5E8F\u4E0D\u5B58\u5728");
        String tempFile = null;
        String outPath = null;
        Map data = new HashMap();
        try
        {
            tempFile = IOUtil.createTempFile("json").getAbsolutePath();
            outPath = IOUtil.createTempFile("json").getAbsolutePath();
            data.put("filepaths", filepaths);
            data.put("page", Integer.valueOf(type));
            data.put("outpath", outPath);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
            Writer jsonwriter = (new JSONObject(data)).write(writer);
            jsonwriter.flush();
            jsonwriter.close();
        }
        catch(Exception e)
        {
            LogUtils.error(com/sinitek/sirm/common/office/WordUtils, e);
            throw new WordUpdateException("SIRMERROR0003", "\u751F\u6210json\u6587\u4EF6\u9519\u8BEF");
        }
        SystemUtil.execCommand((new StringBuilder()).append(toolpath).append(" -t:merge ").append(tempFile).toString(), true);
        String jsonfile = outPath;
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(jsonfile)));
            while((line = br.readLine()) != null) 
                stringBuffer.append(line);
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            String code = ((JSONObject)(JSONObject)jsonObject.getJSONArray("error").get(0)).getString("code");
            String filepath = ((JSONObject)(JSONObject)jsonObject.getJSONArray("data").get(0)).getString("filepath");
            if(code.equals("SIRMERROR0005"))
                throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
            if(code.equals("SIRMERROR0006"))
                throw new WordUpdateException("SIRMERROR0006", "\u751F\u6210.update.doc\u6587\u4EF6\u9519\u8BEF");
            else
                return filepath;
        }
        catch(Exception e)
        {
            throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
        }
    }

    public static String merge(List filepaths, String tempDir)
    {
        String jsonfile;
        StringBuffer stringBuffer;
        BufferedReader br;
        String toolpath = SettingUtils.getStringValue("COMMON", "WORDPARSERPATH");
        if(!(new File(toolpath)).exists())
            throw new WordUpdateException("SIRMERROR0001", (new StringBuilder()).append("\u5916\u90E8\u5E94\u7528\u7A0B\u5E8F\u4E0D\u5B58\u5728[WORDPARSERPATH:").append(toolpath).append("]").toString());
        String tempFile = null;
        String outPath = null;
        Map data = new HashMap();
        try
        {
            if(tempDir != null)
            {
                tempFile = IOUtil.createTempFile("json", tempDir).getAbsolutePath();
                outPath = IOUtil.createTempFile("json", tempDir).getAbsolutePath();
            } else
            {
                tempFile = IOUtil.createTempFile("json").getAbsolutePath();
                outPath = IOUtil.createTempFile("json").getAbsolutePath();
            }
            data.put("filepaths", filepaths);
            data.put("outpath", outPath);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
            Writer jsonwriter = (new JSONObject(data)).write(writer);
            jsonwriter.flush();
            jsonwriter.close();
        }
        catch(Exception e)
        {
            LogUtils.error(com/sinitek/sirm/common/office/WordUtils, e);
            throw new WordUpdateException("SIRMERROR0003", "\u751F\u6210json\u6587\u4EF6\u9519\u8BEF");
        }
        SystemUtil.execCommand((new StringBuilder()).append(toolpath).append(" -t:merge ").append(tempFile).toString(), true);
        jsonfile = outPath;
        stringBuffer = new StringBuffer();
        String line = null;
        br = null;
        String s;
        try
        {
            br = new BufferedReader(new FileReader(new File(jsonfile)));
            String line;
            while((line = br.readLine()) != null) 
                stringBuffer.append(line);
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            String code = ((JSONObject)(JSONObject)jsonObject.getJSONArray("error").get(0)).getString("code");
            String filepath = ((JSONObject)(JSONObject)jsonObject.getJSONArray("data").get(0)).getString("filepath");
            if(code.equals("SIRMERROR0005"))
                throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
            if(code.equals("SIRMERROR0006"))
                throw new WordUpdateException("SIRMERROR0006", "\u751F\u6210.update.doc\u6587\u4EF6\u9519\u8BEF");
            s = filepath;
        }
        catch(Exception e)
        {
            throw new WordUpdateException("SIRMERROR0005", (new StringBuilder()).append("\u8BFB\u53D6json\u6587\u4EF6\u9519\u8BEF[").append(jsonfile).append("]").toString());
        }
        if(br != null)
            try
            {
                br.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        return s;
        Exception exception;
        exception;
        if(br != null)
            try
            {
                br.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        throw exception;
    }
}
