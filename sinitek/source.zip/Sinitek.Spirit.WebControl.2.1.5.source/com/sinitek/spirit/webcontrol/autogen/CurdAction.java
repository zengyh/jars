// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CurdAction.java

package com.sinitek.spirit.webcontrol.autogen;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.common.FrameworkConfig;
import com.sinitek.spirit.webcontrol.utils.FileUtils;
import freemarker.template.*;
import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class CurdAction
{

    public CurdAction()
    {
    }

    public void checkSql(Map params, HttpServletRequest request)
    {
        int count = (new MetaDBTemplate()).createSqlQuery((String)params.get("sql")).getResultCount();
        if(count == 0)
            throw new RuntimeException("\u7ED3\u679C\u4E2D\u81F3\u5C11\u6709\u4E00\u4E2A\u8BB0\u5F55\u624D\u80FD\u751F\u6210");
        else
            return;
    }

    public void gen(Map params, HttpServletRequest request)
    {
        String java = (String)params.get("java");
        params.put("action", java.substring(java.lastIndexOf(".") + 1));
        if(StringUtils.isNotBlank((String)params.get("jsp")))
            genJsp(params);
        if(StringUtils.isNotBlank((String)params.get("java")))
            genJava(params);
    }

    private void genJsp(Map params)
    {
        String filePath;
        String fileDir;
        Writer out;
        filePath = (String)params.get("jsp");
        fileDir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        out = null;
        try
        {
            String tmpName = "1".equals(params.get("onlyQuery")) ? "curd_onlyQuery_jsp.ftl" : "curd_jsp.ftl";
            Template temp = getFreeMarkConfig().getTemplate(tmpName);
            String jspPath = FrameworkConfig.getInstance().getParam("autogen.path.jsp");
            if(StringUtils.isBlank(jspPath))
                jspPath = (new StringBuilder()).append("c:").append(File.separator).append("autogen").append(File.separator).append("jsp").toString();
            String includeHead = FrameworkConfig.getInstance().getParam("autogen.include.head");
            if(StringUtils.isBlank(includeHead))
                includeHead = "../include/head.jsp";
            String includeInnerHead = FrameworkConfig.getInstance().getParam("autogen.include.innerHead");
            if(StringUtils.isBlank(includeInnerHead))
                includeInnerHead = "../include/innerHead.jsp";
            Map root = new HashMap();
            root.put("type", params.get("type"));
            root.put("target", params.get("target"));
            root.put("tableType", params.get("tableType"));
            JSONArray jsonMap = JSONArray.fromObject(params.get("properties"));
            root.put("properties", jsonMap);
            Map info = new HashMap();
            info.put("onlyQuery", Boolean.valueOf(((String)params.get("onlyQuery")).equals("1")));
            info.put("jsp", params.get("jsp"));
            info.put("java", params.get("java"));
            info.put("action", params.get("action"));
            info.put("includeHead", includeHead);
            info.put("includeInnerHead", includeInnerHead);
            root.put("info", info);
            createDirs((new StringBuilder()).append(jspPath).append(fileDir).toString());
            if("0".equals(params.get("conflict")))
                createBakFile((new StringBuilder()).append(jspPath).append(filePath).toString());
            out = writeToOutput((new StringBuilder()).append(jspPath).append(filePath).toString(), temp, root, "UTF-8");
        }
        catch(Exception e)
        {
            LOG.error("JSP\u6587\u4EF6\u751F\u6210\u9519\u8BEF", e);
            throw new RuntimeException("JSP\u6587\u4EF6\u751F\u6210\u9519\u8BEF", e);
        }
        try
        {
            if(out != null)
            {
                out.flush();
                out.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        break MISSING_BLOCK_LABEL_557;
        Exception exception;
        exception;
        try
        {
            if(out != null)
            {
                out.flush();
                out.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        throw exception;
    }

    private void createBakFile(String filePath)
        throws IOException
    {
        File oldFile = new File(filePath);
        if(oldFile.exists())
            FileUtils.copyFile(oldFile, new File((new StringBuilder()).append(filePath).append(".bak").toString()));
    }

    private void createDirs(String fileDir)
    {
        File dir = new File(fileDir);
        if(!dir.exists())
            dir.mkdirs();
    }

    private void genJava(Map params)
    {
        String filePath;
        String type;
        String target;
        String fileDir;
        Writer out;
        filePath = (String)params.get("java");
        filePath = (new StringBuilder()).append(filePath.replaceAll("\\.", "/")).append(".java").toString();
        type = (String)params.get("type");
        target = (String)params.get("target");
        fileDir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        out = null;
        try
        {
            Template temp = getFreeMarkConfig().getTemplate("curd_java.ftl");
            Map root = new HashMap();
            if("1".equals(type))
            {
                target = target.trim().replaceAll("\\n", "\\\\n\"+\n\t\t\t\"");
                root.put("target", target);
            } else
            {
                root.put("target", target.trim());
            }
            root.put("type", params.get("type"));
            JSONArray jsonMap = JSONArray.fromObject(params.get("properties"));
            root.put("properties", jsonMap);
            Map info = new HashMap();
            info.put("onlyQuery", Boolean.valueOf(((String)params.get("onlyQuery")).equals("1")));
            info.put("jsp", params.get("jsp"));
            info.put("java", params.get("java"));
            info.put("action", params.get("action"));
            root.put("info", info);
            root.put("package", fileDir.substring(0, fileDir.length() - 1).replaceAll("/", "."));
            Map msgMap = new HashMap();
            msgMap.put("serverError", "\u670D\u52A1\u5668\u5FD9\uFF0C\u8BF7\u7A0D\u5019\u518D\u8BD5");
            msgMap.put("dupCheck", "\u83B7\u5F97\u5BF9\u8C61\u68C0\u67E5\u91CD\u590D");
            msgMap.put("dupRewrite", "\u6BD4\u5982\uFF1A\u8BE5\u8D26\u53F7\u5DF2\u7ECF\u5B58\u5728\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165\uFF01");
            msgMap.put("addError", "\u6DFB\u52A0\u5931\u8D25");
            msgMap.put("updateError", "\u66F4\u65B0\u5931\u8D25");
            msgMap.put("delError", "\u5220\u9664\u5931\u8D25");
            root.put("msg", msgMap);
            if("0".equals(type))
                root.put("interface", getInterFace(target));
            String javaPath = FrameworkConfig.getInstance().getParam("autogen.path.java");
            if(StringUtils.isBlank(javaPath))
                javaPath = (new StringBuilder()).append("c:").append(File.separator).append("autogen").append(File.separator).append("java").toString();
            createDirs((new StringBuilder()).append(javaPath).append(File.separator).append(fileDir).toString());
            if("0".equals(params.get("conflict")))
                createBakFile((new StringBuilder()).append(javaPath).append(File.separator).append(filePath).toString());
            out = writeToOutput((new StringBuilder()).append(javaPath).append(File.separator).append(filePath).toString(), temp, root, "GB2312");
        }
        catch(Exception e)
        {
            LOG.error("JAVA\u6587\u4EF6\u751F\u6210\u9519\u8BEF", e);
            throw new RuntimeException("JAVA\u6587\u4EF6\u751F\u6210\u9519\u8BEF", e);
        }
        try
        {
            if(out != null)
            {
                out.flush();
                out.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        break MISSING_BLOCK_LABEL_701;
        Exception exception;
        exception;
        try
        {
            if(out != null)
            {
                out.flush();
                out.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        throw exception;
    }

    private Writer writeToOutput(String filePath, Template temp, Map root, String encoding)
        throws TemplateException, IOException
    {
        Writer out = new OutputStreamWriter(new FileOutputStream(filePath), encoding);
        temp.process(root, out);
        return out;
    }

    private Configuration getFreeMarkConfig()
        throws Exception
    {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(WebContextFactory.get().getSession().getServletContext().getRealPath("\\framework\\autogen")));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setEncoding(Locale.getDefault(), "UTF-8");
        return cfg;
    }

    private String getInterFace(String entityname)
    {
        IMetaDBQuery result = (new MetaDBTemplate()).createSqlQuery("select a.entityname, a.INTERFACENAME from metadb_entity a where a.entityname=:entityname");
        result.setParameter("entityname", entityname);
        String interfaceName = (String)((Map)result.getResult().get(0)).get("INTERFACENAME");
        if(StringUtils.isBlank(interfaceName))
            throw new NullPointerException((new StringBuilder()).append("\u5B9E\u4F53\u3010").append(entityname).append("\u3011\u63A5\u53E3\u540D\u4E3Anull").toString());
        else
            return interfaceName;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/autogen/CurdAction);

}
