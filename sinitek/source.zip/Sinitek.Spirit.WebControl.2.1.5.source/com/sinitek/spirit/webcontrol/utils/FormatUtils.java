// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FormatUtils.java

package com.sinitek.spirit.webcontrol.utils;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.enumsupport.IEnumItem;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.BeanMap;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;

public class FormatUtils
{

    public FormatUtils()
    {
    }

    public static Date stringToDate(String s, String format)
        throws Exception
    {
        if(StringUtils.isBlank(s))
        {
            return null;
        } else
        {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.parse(s);
        }
    }

    public static Integer stringToInteger(String s)
    {
        if(StringUtils.isBlank(s))
            return null;
        else
            return new Integer(s);
    }

    public static IEnumItem stringToEnum(String s, String clazz)
    {
        if(StringUtils.isBlank(s))
            return null;
        else
            return EnumItemContext.getInstance().getEnumItem(clazz, s);
    }

    /**
     * @deprecated Method stringToEnum is deprecated
     */

    public static IEnumItem stringToEnum(Integer s, String clazz)
    {
        if(s == null)
            return null;
        else
            return EnumItemContext.getInstance().getEnumItem(clazz, s.intValue());
    }

    public static IEnumItem integerToEnum(Integer s, String clazz)
    {
        if(s == null)
            return null;
        else
            return EnumItemContext.getInstance().getEnumItem(clazz, s.intValue());
    }

    public static String dateToString(Date d, String format)
    {
        if(d == null)
        {
            return null;
        } else
        {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.format(d);
        }
    }

    public static String arrayToString(String s[])
    {
        return arrayToString(s, null);
    }

    private static String arrayToString(String s[], String symbol)
    {
        if(StringUtils.isBlank(symbol))
            symbol = ",";
        if(s != null && s.length > 0)
        {
            String result = "";
            for(int i = 0; i < s.length; i++)
            {
                result = (new StringBuilder()).append(result).append(s[i]).toString();
                if(s.length != i + 1)
                    result = (new StringBuilder()).append(result).append(symbol).toString();
            }

            return result;
        } else
        {
            return null;
        }
    }

    private static Object mapToBean(Map map, Object oc)
    {
        try
        {
            Method methods[] = oc.getClass().getMethods();
            Map methodMap = new CaseInsensitiveMap();
            Method arr$[] = methods;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                Method m = arr$[i$];
                methodMap.put(m.getName(), m.getReturnType());
            }

            BeanMap tmp = new BeanMap(oc);
            Iterator it = tmp.keyIterator();
            do
            {
                if(!it.hasNext())
                    break;
                String key = (String)it.next();
                if(map.containsKey(key) && !"".equals(map.get(key)))
                {
                    Class type = (Class)methodMap.get((new StringBuilder()).append("get").append(key.toLowerCase()).toString());
                    String returnType = type.getName();
                    Object value = map.get(key);
                    boolean isSuccess = true;
                    if("java.lang.String".equals(returnType) || "java.sql.Clob".equals(returnType) || "java.sql.Blob".equals(returnType))
                        value = value.toString();
                    else
                    if("java.lang.Integer".equals(returnType))
                    {
                        if(value instanceof String)
                            value = Integer.valueOf((String)value);
                    } else
                    if("java.lang.Double".equals(returnType))
                    {
                        if(value instanceof String)
                            value = Double.valueOf((String)value);
                    } else
                    if("java.lang.Float".equals(returnType))
                    {
                        if(value instanceof String)
                            value = Float.valueOf((String)value);
                    } else
                    if("java.lang.Boolean".equals(returnType))
                    {
                        if(value instanceof String)
                            value = Boolean.valueOf("true".equals(value) || "1".equals(value));
                    } else
                    if("java.util.Date".equals(returnType))
                    {
                        if(value instanceof String)
                        {
                            String pattern = "";
                            value = ((String)value).replaceAll("-", "");
                            value = ((String)value).replaceAll(":", "");
                            value = ((String)value).replaceAll(" ", "");
                            if(((String)value).length() == 8)
                                pattern = "yyyyMMdd";
                            else
                            if(((String)value).length() == 12)
                                pattern = "yyyyMMddHHmm";
                            else
                            if(((String)value).length() == 14)
                                pattern = "yyyyMMddHHmmss";
                            if(StringUtils.isNotBlank(pattern))
                                value = stringToDate((String)value, pattern);
                        }
                    } else
                    {
                        isSuccess = false;
                    }
                    if(isSuccess)
                        tmp.put(key, value);
                }
            } while(true);
            return tmp.getBean();
        }
        catch(Exception e)
        {
            throw new RuntimeException("\u5BF9\u8C61\u8F6C\u6362\u9519\u8BEF", e);
        }
    }

    public static Object mapToBean(Map map, Class c)
    {
        try
        {
            Object oc = c.newInstance();
            return mapToBean(map, oc);
        }
        catch(Exception e)
        {
            throw new RuntimeException("\u5BF9\u8C61\u8F6C\u6362\u9519\u8BEF", e);
        }
    }

    public static Map beanToMap(Object bean)
    {
        Map result = new HashMap();
        BeanMap tmp = new BeanMap(bean);
        Method methods[] = bean.getClass().getMethods();
        Method arr$[] = methods;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            Method m = arr$[i$];
            String methodName = m.getName();
            String key;
            if(methodName.length() > 3 && methodName.startsWith("get") && isUpCase(methodName.substring(3, 4)))
            {
                key = (new StringBuilder()).append(methodName.substring(3, 4).toLowerCase()).append(methodName.substring(4)).toString();
                result.put(key, tmp.get(key));
                continue;
            }
            if(methodName.length() <= 2 || !methodName.startsWith("is") || !isUpCase(methodName.substring(2, 3)))
                continue;
            key = (new StringBuilder()).append(methodName.substring(2, 3).toLowerCase()).append(methodName.substring(3)).toString();
            try
            {
                result.put(key, bean.getClass().getMethod(methodName, new Class[0]).invoke(bean, new Object[0]));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String htmlToText(String inputString)
    {
        String htmlStr = inputString;
        String textStr = "";
        try
        {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile(regEx_script, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        }
        catch(Exception e)
        {
            System.err.println((new StringBuilder()).append("Html2Text: ").append(e.getMessage()).toString());
        }
        return textStr;
    }

    public static String toSafeHtml(String value)
    {
        if(StringUtils.isBlank(value))
        {
            return value;
        } else
        {
            value = value.replaceAll("< ", "<");
            value = value.replaceAll(" >", ">");
            value = value.replaceAll(">", " >");
            value = value.replaceAll("<", "< ");
            return value;
        }
    }

    private static boolean isUpCase(String s)
    {
        return !s.equals(s.toLowerCase());
    }
}
