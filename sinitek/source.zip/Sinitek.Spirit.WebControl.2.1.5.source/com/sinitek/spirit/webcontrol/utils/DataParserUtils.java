// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataParserUtils.java

package com.sinitek.spirit.webcontrol.utils;

import com.sinitek.base.enumsupport.AbstractEnumItem;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.utils:
//            SqlUtils

public class DataParserUtils
{

    public DataParserUtils()
    {
    }

    public static String formatNumber(Object data, String pointPlace, String isMoney)
    {
        String pattern = null;
        if("true".equals(isMoney))
        {
            pattern = "#,##0";
            if(StringUtils.isBlank(pointPlace))
                pointPlace = "2";
            int ipointPlace = (new Integer(pointPlace)).intValue();
            if(ipointPlace > 0)
                pattern = (new StringBuilder()).append(pattern).append(".").toString();
            for(int i = 0; i < ipointPlace; i++)
                pattern = (new StringBuilder()).append(pattern).append("0").toString();

        } else
        {
            Integer iPointPlace = new Integer(pointPlace);
            if(iPointPlace == null)
                iPointPlace = Integer.valueOf(0);
            if(iPointPlace.intValue() > 0)
                pattern = getPatternByPos(iPointPlace.intValue());
        }
        if(StringUtils.isNotBlank(pattern))
        {
            DecimalFormat formater = new DecimalFormat(pattern);
            formater.setRoundingMode(RoundingMode.HALF_UP);
            return formater.format(new Double(data.toString()));
        } else
        {
            return data.toString();
        }
    }

    public static String formatBoolean(Object data, String format)
    {
        Boolean b;
        if(data instanceof Boolean)
            b = (Boolean)data;
        else
        if(data instanceof BigDecimal)
        {
            int tmp = ((BigDecimal)data).intValue();
            b = Boolean.valueOf(tmp == 1);
        } else
        if(data instanceof Integer)
            b = Boolean.valueOf(((Integer)data).intValue() == 1);
        else
        if(data.equals("1") || data.equals("true"))
            b = Boolean.TRUE;
        else
            b = Boolean.FALSE;
        String tmp[];
        if(format.contains(":"))
            tmp = format.split(":");
        else
        if(format.contains("\uFF1A"))
            tmp = format.split("\uFF1A");
        else
            tmp = (new String[] {
                "\u662F", "\u5426"
            });
        if(b.booleanValue())
            return tmp[0];
        if(tmp.length < 2)
            return "";
        else
            return tmp[1];
    }

    public static String formatDate(Object data, String format)
    {
        if(StringUtils.isBlank(format))
            format = "yyyy-MM-dd";
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sfTime = new SimpleDateFormat("HHmmss");
        SimpleDateFormat sfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date;
        if(data instanceof String)
        {
            String stringDate = (String)data;
            int len = stringDate.length();
            try
            {
                if(StringUtils.isBlank(format))
                    switch(len)
                    {
                    case 8: // '\b'
                        date = sfDate.parse(stringDate);
                        format = "yyyy-MM-dd";
                        break;

                    case 6: // '\006'
                        date = sfTime.parse(stringDate);
                        format = "HH:mm:ss";
                        break;

                    case 14: // '\016'
                        date = sfDateTime.parse(stringDate);
                        format = "yyyy-MM-dd HH:mm:ss";
                        break;

                    default:
                        throw new RuntimeException((new StringBuilder()).append("\u5B57\u7B26\u4E32\uFF1A[").append(stringDate).append("]\u5C1D\u8BD5\u8F6C\u6362\u6210date\u5931\u8D25\uFF0C\u5B57\u7B26\u4E32\u957F\u5EA6\u4E0D\u4E3A6\u62168\u621614\uFF01").toString());
                    }
                else
                    date = (new SimpleDateFormat(format)).parse(stringDate);
            }
            catch(ParseException e)
            {
                throw new RuntimeException((new StringBuilder()).append("\u8F6C\u6362\u5B57\u7B26\u4E32\uFF1A[").append(stringDate).append("]\u4E3A\u65E5\u671F\u578B\u65F6\u51FA\u9519").toString(), e);
            }
        } else
        {
            date = (Date)data;
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String formatEnum(Object data, String enumClass, String format)
    {
        if(StringUtils.isBlank(format))
            format = "info";
        AbstractEnumItem enumObj;
        if(data instanceof AbstractEnumItem)
            enumObj = (AbstractEnumItem)data;
        else
        if(data instanceof Integer)
            enumObj = (AbstractEnumItem)EnumItemContext.getInstance().getEnumItem(enumClass, ((Integer)data).intValue());
        else
        if(data instanceof BigDecimal)
            enumObj = (AbstractEnumItem)EnumItemContext.getInstance().getEnumItem(enumClass, ((BigDecimal)data).intValue());
        else
            enumObj = (AbstractEnumItem)EnumItemContext.getInstance().getEnumItem(enumClass, data.toString());
        String result = format;
        result = result.replaceAll("name", enumObj.getEnumItemName());
        result = result.replaceAll("info", enumObj.getEnumItemInfo());
        result = result.replaceAll("value", (new StringBuilder()).append("").append(enumObj.getEnumItemValue()).toString());
        result = result.replaceAll("displayValue", enumObj.getEnumItemDisplayValue());
        return result;
    }

    public static String formatEntity(Object data, String entityName, String foreignProperty, String format)
    {
        if(StringUtils.isBlank(format))
            format = "{objid}";
        String orgValue = data.toString();
        String result = format;
        MetaDBContextFactory factory = MetaDBContextFactory.getInstance();
        MetaDBTemplate template = new MetaDBTemplate(MetaDBContextFactory.AUTO);
        IMetaObject metaObject;
        if("objid".equalsIgnoreCase(foreignProperty))
            metaObject = template.load(factory.getEntity(entityName), (new Integer(orgValue)).intValue());
        else
            metaObject = template.get(factory.getEntity(entityName), foreignProperty, orgValue);
        List list = new ArrayList();
        int x;
        int y;
        for(; isExpress(format, "{", "}"); format = (new StringBuilder()).append(format.substring(0, x)).append(format.substring(y + 1)).toString())
        {
            x = format.indexOf("{");
            y = format.indexOf("}", x);
            list.add(format.substring(x + 1, y));
        }

        for(Iterator i$ = list.iterator(); i$.hasNext();)
        {
            String value = (String)i$.next();
            if(value.equalsIgnoreCase("objid"))
                result = result.replaceAll((new StringBuilder()).append("\\{").append(value).append("\\}").toString(), (new StringBuilder()).append("").append(metaObject.getId()).toString());
            else
                result = result.replaceAll((new StringBuilder()).append("\\{").append(value).append("\\}").toString(), metaObject.get(value.toLowerCase()).toString());
        }

        return result;
    }

    public static String formatForeign(Object data, String tableName, String columnName, String format)
    {
        if(StringUtils.isBlank(format))
            format = "{objid}";
        String result = format;
        Map query = SqlUtils.queryForMap(tableName, columnName, data);
        if(query == null)
            return data.toString();
        List list = new ArrayList();
        int x;
        int y;
        for(; isExpress(format, "{", "}"); format = (new StringBuilder()).append(format.substring(0, x)).append(format.substring(y + 1)).toString())
        {
            x = format.indexOf("{");
            y = format.indexOf("}", x);
            list.add(format.substring(x + 1, y));
        }

        for(Iterator i$ = list.iterator(); i$.hasNext();)
        {
            String value = (String)i$.next();
            result = result.replaceAll((new StringBuilder()).append("\\{").append(value).append("\\}").toString(), (new StringBuilder()).append("").append(query.get(value)).toString());
        }

        return result;
    }

    public static String formatCust(Map data, String className, String methodName, HttpServletRequest request)
        throws Exception
    {
        Class argsClass[] = {
            java/util/Map
        };
        Class _class = Class.forName(className);
        Method method;
        try
        {
            method = _class.getMethod(methodName, argsClass);
        }
        catch(Exception e)
        {
            argsClass = (new Class[] {
                java/util/Map, javax/servlet/http/HttpServletRequest
            });
            method = _class.getMethod(methodName, argsClass);
        }
        if(Modifier.isStatic(method.getModifiers()))
            if(argsClass.length == 2)
                return (String)method.invoke(null, new Object[] {
                    data, request
                });
            else
                return (String)method.invoke(null, new Object[] {
                    data
                });
        if(argsClass.length == 2)
            return (String)method.invoke(_class.newInstance(), new Object[] {
                data, request
            });
        else
            return (String)method.invoke(_class.newInstance(), new Object[] {
                data
            });
    }

    private static String getPatternByPos(int pos)
    {
        String tmp = "0.";
        for(int i = 0; i < pos; i++)
            tmp = (new StringBuilder()).append(tmp).append("0").toString();

        return tmp;
    }

    private static boolean isExpress(String expr, String start, String end)
    {
        if(expr == null)
            return false;
        if(!expr.contains(start) || !expr.contains(end))
        {
            return false;
        } else
        {
            int x = expr.indexOf(start);
            int y = expr.indexOf(end, x);
            return x != -1 && y != -1;
        }
    }
}
