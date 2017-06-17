// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageTemplateUtil.java

package com.sinitek.sirm.busin.routine.utils;

import com.sinitek.sirm.busin.routine.entity.IMessageTemplate;
import com.sinitek.sirm.busin.routine.entity.MessageTemplateImpl;
import com.sinitek.sirm.busin.routine.service.IMessageService;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.utils.*;
import java.io.*;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MessageTemplateUtil
{

    private MessageTemplateUtil()
    {
    }

    public static void export(List templates, String path)
        throws IOException
    {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("templates");
        rootElement.addAttribute("version", "1.0");
        rootElement.addAttribute("exporttime", TimeUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if(CollectionUtils.isNotEmpty(templates))
        {
            Iterator i$ = templates.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IMessageTemplate template = (IMessageTemplate)i$.next();
                if(template != null)
                    procTemplate(rootElement, template);
            } while(true);
        }
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path));
        xmlWriter.write(document);
        xmlWriter.flush();
        xmlWriter.close();
    }

    private static void procTemplate(Element rootElement, IMessageTemplate template)
    {
        Element templateElement = rootElement.addElement("template");
        templateElement.addAttribute("code", template.getCode());
        templateElement.addElement("name").setText(StringUtils.defaultString(template.getName(), ""));
        templateElement.addElement("title").setText(StringUtils.defaultString(template.getTitle(), ""));
        templateElement.addElement("forceflag").setText((new StringBuilder()).append("").append(NumberTool.safeToInteger(template.getForceflag(), Integer.valueOf(0))).toString());
        templateElement.addElement("sendmode").setText((new StringBuilder()).append("").append(NumberTool.safeToInteger(template.getSendMode(), Integer.valueOf(0))).toString());
        templateElement.addElement("remark").setText(StringUtils.defaultString(template.getRemark(), ""));
        templateElement.addElement("content").setText(StringUtils.defaultString(template.getContent(), ""));
        templateElement.addElement("smscontent").setText(StringUtils.defaultString(template.getSmsContent(), ""));
        templateElement.addElement("remindcontent").setText(StringUtils.defaultString(template.getRemindContent(), ""));
        templateElement.addElement("mobilecontent").setText(StringUtils.defaultString(template.getMobileContent(), ""));
    }

    public static String importMessageTemplate(InputStream inputStream, int onConflict)
        throws IOException, DocumentException
    {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        if(!StringUtils.equals(rootElement.getName(), "templates"))
            throw new RuntimeException("\u975E\u6D88\u606F\u6A21\u677F\u5BFC\u5165\u6587\u4EF6\uFF01");
        if(!ObjectUtils.contains(rootElement.attributeValue("version"), new String[] {
    "1.0"
}))
            throw new RuntimeException("\u65E0\u6CD5\u5BFC\u5165\u8BE5\u7248\u672C\u6587\u4EF6\uFF01");
        StringBuilder info = new StringBuilder();
        List templates = rootElement.elements("template");
        info.append("\u5171\u627E\u5230").append(templates.size()).append("\u4E2A\u6A21\u677F\n");
        int noCodeCount = 0;
        for(Iterator i$ = templates.iterator(); i$.hasNext();)
        {
            Object templateObj = i$.next();
            Element template = (Element)templateObj;
            String code = template.attributeValue("code");
            if(StringUtils.isBlank(code))
            {
                noCodeCount++;
            } else
            {
                IMessageTemplate messageTemplate = CommonServiceFactory.getMessageService().getMessageTemplateByCode(code);
                if(messageTemplate == null)
                    try
                    {
                        messageTemplate = new MessageTemplateImpl();
                        procEachImport(template, messageTemplate);
                        info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u5BFC\u5165\u6210\u529F\uFF01\n");
                    }
                    catch(Exception e)
                    {
                        LOGGER.error((new StringBuilder()).append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u5BFC\u5165\u65F6\u53D1\u751F\u9519\u8BEF\uFF01").toString(), e);
                        info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u5BFC\u5165\u65F6\u53D1\u751F\u9519\u8BEF\uFF01\n");
                    }
                else
                if(onConflict == 1)
                    try
                    {
                        procEachImport(template, messageTemplate);
                        info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u8986\u76D6\u5BFC\u5165\uFF01\n");
                    }
                    catch(Exception e)
                    {
                        LOGGER.error((new StringBuilder()).append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u5BFC\u5165\u65F6\u53D1\u751F\u9519\u8BEF\uFF01").toString(), e);
                        info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u8986\u76D6\u5BFC\u5165\u65F6\u53D1\u751F\u9519\u8BEF\uFF01\n");
                    }
                else
                if(onConflict == 2)
                    info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u5DF2\u8DF3\u8FC7\uFF01\n");
                else
                    info.append("\u6A21\u677F\u4EE3\u7801").append(code).append("\uFF1A\u672A\u77E5\u51B2\u7A81\u5904\u7406\u65B9\u5F0F(").append(onConflict).append(")\uFF0C\u672A\u5BFC\u5165\uFF01");
            }
        }

        if(noCodeCount > 0)
            info.append("\u5171\u6709").append(noCodeCount).append("\u4E2A\u6A21\u677F\u6CA1\u6709\u627E\u5230\u6A21\u677F\u4EE3\u7801\uFF0C\u65E0\u6CD5\u5BFC\u5165\u3002\n");
        return info.toString();
    }

    private static void procEachImport(Element template, IMessageTemplate messageTemplate)
    {
        messageTemplate.setCode(template.attributeValue("code", ""));
        messageTemplate.setName(getElementText(template, "name"));
        messageTemplate.setTitle(getElementText(template, "title"));
        messageTemplate.setForceflag(NumberTool.safeToInteger(getElementText(template, "forceflag"), Integer.valueOf(0)));
        messageTemplate.setSendMode(NumberTool.safeToInteger(getElementText(template, "sendmode"), Integer.valueOf(0)));
        messageTemplate.setRemark(getElementText(template, "remark"));
        messageTemplate.setContent(getElementText(template, "content"));
        messageTemplate.setSmsContent(getElementText(template, "smscontent"));
        messageTemplate.setRemindContent(getElementText(template, "remindcontent"));
        messageTemplate.setMobileContent(getElementText(template, "mobilecontent"));
        messageTemplate.setProcesstype(NumberTool.safeToInteger(getElementText(template, "processtype"), Integer.valueOf(0)));
        CommonServiceFactory.getMessageService().saveMessageTemplate(messageTemplate);
    }

    private static String getElementText(Element element, String name)
    {
        Element subElement = element.element(name);
        if(subElement != null)
            return subElement.getText();
        else
            return "";
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/utils/MessageTemplateUtil);
    public static final String VERSION1_0 = "1.0";
    public static final int CONFLICT_COVER = 1;
    public static final int CONFLICT_SKIP = 2;

}
