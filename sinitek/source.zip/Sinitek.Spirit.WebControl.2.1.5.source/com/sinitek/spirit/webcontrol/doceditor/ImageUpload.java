// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageUpload.java

package com.sinitek.spirit.webcontrol.doceditor;

import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class ImageUpload extends HttpServlet
{

    public ImageUpload()
    {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        try
        {
            String errorMsg = "";
            String id = "";
            String imgTitle = "";
            String imgWidth = "";
            String imgHeight = "";
            String imgBorder = "";
            long maxSize = 0x5f5e100L;
            String types[] = {
                "gif", "jpg", "jpeg", "png", "bmp"
            };
            String savePath = (new StringBuilder()).append(request.getSession().getServletContext().getRealPath("/")).append("editorImg/").toString();
            File savePathFolder = new File(savePath);
            if(!savePathFolder.exists() || !savePathFolder.isDirectory())
                savePathFolder.mkdirs();
            String saveUrl = (new StringBuilder()).append(request.getContextPath()).append("/editorImg/").toString();
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if(isMultipart)
            {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator i$ = items.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    Object item1 = i$.next();
                    FileItem item = (FileItem)item1;
                    String fileName = item.getName();
                    if(item.getFieldName().equals("id"))
                        id = item.getString();
                    if(item.getFieldName().equals("imgTitle"))
                        imgTitle = item.getString();
                    if(item.getFieldName().equals("imgWidth"))
                        imgWidth = item.getString();
                    if(item.getFieldName().equals("imgHeight"))
                        imgHeight = item.getString();
                    if(item.getFieldName().equals("imgBorder"))
                        imgBorder = item.getString();
                    if(item.isFormField())
                        continue;
                    if(item.getName().equals("") || item.getString() == null)
                    {
                        errorMsg = "\u8BF7\u9009\u62E9\u6587\u4EF6\u3002";
                        LOG.info(errorMsg);
                        break;
                    }
                    File uploadDir = new File(savePath);
                    if(!uploadDir.isDirectory())
                    {
                        errorMsg = "\u4E0A\u4F20\u76EE\u5F55\u4E0D\u5B58\u5728\u3002";
                        LOG.info(errorMsg);
                        break;
                    }
                    if(!uploadDir.canWrite())
                    {
                        errorMsg = "\u4E0A\u4F20\u76EE\u5F55\u6CA1\u6709\u5199\u6743\u9650\u3002";
                        LOG.info(errorMsg);
                        break;
                    }
                    if(item.getSize() > maxSize)
                    {
                        errorMsg = "\u4E0A\u4F20\u6587\u4EF6\u5927\u5C0F\u8D85\u8FC7\u9650\u5236\u3002";
                        LOG.info(errorMsg);
                    }
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if(!Arrays.asList(types).contains(fileExt.toLowerCase()))
                    {
                        errorMsg = "\u4E0A\u4F20\u6587\u4EF6\u6269\u5C55\u540D\u662F\u4E0D\u5141\u8BB8\u7684\u6269\u5C55\u540D\u3002";
                        LOG.info(errorMsg);
                    }
                    String tempName = (new StringBuilder()).append(UUID.randomUUID().toString()).append(".").append(fileExt).toString();
                    File file = new File((new StringBuilder()).append(savePath).append("\\").append(tempName).toString());
                    if(file.exists())
                    {
                        errorMsg = "\u4E34\u65F6\u6587\u4EF6\u53EF\u80FD\u4E0D\u662F\u4E0A\u4F20\u6587\u4EF6\u3002";
                        LOG.info(errorMsg);
                    }
                    try
                    {
                        File uploadedFile = new File(savePath, tempName);
                        item.write(uploadedFile);
                    }
                    catch(Exception e)
                    {
                        errorMsg = "\u4E0A\u4F20\u6587\u4EF6\u5931\u8D25\u3002";
                        LOG.error(errorMsg, e);
                        throw e;
                    }
                    saveUrl = (new StringBuilder()).append(saveUrl).append(tempName).toString();
                } while(true);
                PrintWriter out = response.getWriter();
                if(errorMsg.equals(""))
                {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Insert Image</title>");
                    out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
                    out.println("</head>");
                    out.println("<body>");
                    out.println((new StringBuilder()).append("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\"").append(id).append("\",\"").append(saveUrl).append("\",\"").append(imgTitle).append("\",\"").append(imgWidth).append("\",\"").append(imgHeight).append("\",\"").append(imgBorder).append("\");</script>").toString());
                    System.out.println((new StringBuilder()).append("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\"").append(id).append("\",\"").append(saveUrl).append("\",\"").append(imgTitle).append("\",\"").append(imgWidth).append("\",\"").append(imgHeight).append("\",\"").append(imgBorder).append("\");</script>").toString());
                    out.println("</body>");
                    out.println("</html>");
                } else
                {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>error</title>");
                    out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
                    out.println("</head>");
                    out.println("<body>");
                    out.println((new StringBuilder()).append("<script type=\"text/javascript\">alert(\"").append(errorMsg).append("\");history.back();</script>").toString());
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }
        catch(Exception e)
        {
            LOG.error("\u4E0A\u4F20\u56FE\u7247\u9519\u8BEF", e);
            LOG.info("\u4E0A\u4F20\u56FE\u7247\u9519\u8BEF", e);
        }
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/doceditor/ImageUpload);

}
