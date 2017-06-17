// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XmlConfigFileReader.java

package com.sinitek.base.enumsupport.config;

import com.sinitek.base.enumsupport.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.*;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlConfigFileReader
{
    private static class EnumItemBeanImpl
        implements IEnumItem, Serializable
    {

        public String getEnumItemDisplayValue()
        {
            return enumItemDisplayValue;
        }

        public String getEnumItemInfo()
        {
            return enumItemInfo;
        }

        public String getEnumItemName()
        {
            return enumItemName;
        }

        public int getEnumItemValue()
        {
            return enumItemValue;
        }

        public String getEnumName()
        {
            return enumName;
        }

        public boolean equals(Object obj)
        {
            if(obj instanceof IEnumItem)
            {
                IEnumItem item = (IEnumItem)obj;
                if(getEnumName().equalsIgnoreCase(item.getEnumName()))
                    return item.getEnumItemValue() == enumItemValue;
            }
            return false;
        }

        public String toString()
        {
            return (new StringBuilder()).append("[").append(enumItemName).append("]-[").append(enumItemValue).append("]").toString();
        }

        private String enumName;
        private String enumItemName;
        private String enumItemInfo;
        private int enumItemValue;
        private String enumItemDisplayValue;







        private EnumItemBeanImpl()
        {
        }

    }

    private static class EnumDtdEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if("-//SINITEK//ENUM//ENUMCONFIG//1_0".equalsIgnoreCase(publicId))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/base/enumsupport/config/enumconfig_1_0.dtd"));
            else
                return null;
        }

        private static final String DTD_PATH = "com/sinitek/base/enumsupport/config/enumconfig_1_0.dtd";

        private EnumDtdEntityResolver()
        {
        }

    }


    public XmlConfigFileReader()
    {
    }

    public static List readCofigFile(URL url)
    {
        InputStream xmlIs;
        Exception exception;
        List ret = new ArrayList();
        Map checkMap = new HashMap();
        xmlIs = null;
        List list;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new EnumDtdEntityResolver());
            Document doc = db.parse(url.openStream());
            DocumentType docType = doc.getDoctype();
            if(!"-//SINITEK//ENUM//ENUMCONFIG//1_0".equalsIgnoreCase(docType.getPublicId()))
                throw new EnumException("0003", new Object[] {
                    url.getFile(), docType.getPublicId()
                });
            Element root = doc.getDocumentElement();
            NodeList enumNodes = root.getElementsByTagName("enum");
            for(int i = 0; i < enumNodes.getLength(); i++)
            {
                Element enumElement = (Element)enumNodes.item(i);
                String enumName = enumElement.getAttribute("name");
                if(checkMap.containsKey(enumName))
                    throw new EnumException("0004", new Object[] {
                        url.getFile(), enumName
                    });
                EnumBean bean = new EnumBean();
                Map itemCheckMap = new HashMap();
                Map itemValueCheckMap = new HashMap();
                bean.setEnumName(enumName);
                NodeList enumItemList = enumElement.getElementsByTagName("enumitem");
                for(int j = 0; j < enumItemList.getLength(); j++)
                {
                    Element enumItemElement = (Element)enumItemList.item(j);
                    String enumItemName = readSubElementContent(enumItemElement, "itemname");
                    String szEnumItemValue = readSubElementContent(enumItemElement, "itemvalue");
                    String enumItemInfo = readSubElementContent(enumItemElement, "iteminfo");
                    String enumItemDislpay = readSubElementContent(enumItemElement, "displayvalue");
                    if(itemCheckMap.containsKey(enumItemName))
                        throw new EnumException("0006", new Object[] {
                            url.getFile(), enumName, enumItemName
                        });
                    EnumItemBeanImpl enumItem = new EnumItemBeanImpl();
                    enumItem.enumItemName = enumItemName;
                    enumItem.enumItemInfo = enumItemInfo;
                    enumItem.enumName = enumName;
                    try
                    {
                        enumItem.enumItemValue = Integer.parseInt(szEnumItemValue);
                    }
                    catch(NumberFormatException e)
                    {
                        throw new EnumException("0005", new Object[] {
                            url.getFile(), enumName, enumItemName, szEnumItemValue
                        });
                    }
                    Integer enumItemValue = new Integer(enumItem.enumItemValue);
                    if(itemValueCheckMap.containsKey(enumItemValue))
                        throw new EnumException("0007", new Object[] {
                            url.getFile(), enumName, enumItemValue
                        });
                    String displayValue = enumItemDislpay;
                    displayValue = displayValue.replaceAll("\\$\\{itemName\\}", enumItemName);
                    displayValue = displayValue.replaceAll("\\$\\{itemValue\\}", szEnumItemValue);
                    displayValue = displayValue.replaceAll("\\$\\{itemInfo\\}", enumItemInfo);
                    enumItem.enumItemDisplayValue = displayValue;
                    bean.getEnumItemList().add(enumItem);
                    itemCheckMap.put(enumItemName, enumItem);
                    itemValueCheckMap.put(enumItemValue, enumItem);
                }

                ret.add(bean);
                checkMap.put(enumName, bean);
            }

            list = ret;
        }
        catch(IOException e)
        {
            throw new EnumException("0002", e, new Object[] {
                url.getFile()
            });
        }
        catch(SAXException e)
        {
            throw new EnumException("0002", e, new Object[] {
                url.getFile()
            });
        }
        catch(ParserConfigurationException e)
        {
            throw new EnumException("0002", e, new Object[] {
                url.getFile()
            });
        }
        finally
        {
            if(xmlIs == null) goto _L0; else goto _L0
        }
        if(xmlIs != null)
            try
            {
                xmlIs.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        return list;
        try
        {
            xmlIs.close();
        }
        catch(IOException e)
        {
            LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        throw exception;
    }

    private static String readSubElementContent(Element element, String subElementName)
        throws EnumException
    {
        NodeList nodes = element.getElementsByTagName(subElementName);
        if(nodes == null || nodes.getLength() == 0)
            return "";
        Node targetNode = nodes.item(0);
        NodeList contentList = targetNode.getChildNodes();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < contentList.getLength(); i++)
        {
            Node subNode = contentList.item(i);
            if(subNode.getNodeType() == 3)
                sb.append(subNode.getNodeValue().trim());
        }

        return sb.toString();
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/enumsupport/config/XmlConfigFileReader);
    public static final String PUBLIC_ID_1_0 = "-//SINITEK//ENUM//ENUMCONFIG//1_0";

}
