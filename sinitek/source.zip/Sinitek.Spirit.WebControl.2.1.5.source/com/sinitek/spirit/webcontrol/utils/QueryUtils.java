// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueryUtils.java

package com.sinitek.spirit.webcontrol.utils;

import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class QueryUtils
{

    public QueryUtils()
    {
    }

    public static void buildLike(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).append("%").toString());
            sb.append(getExpress("like", name, realName, null));
        }
    }

    public static void buildLikeStart(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).toString());
            sb.append(getExpress("like", name, realName, null));
        }
    }

    public static void buildLikeEnd(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append(value).append("%").toString());
            sb.append(getExpress("like", name, realName, null));
        }
    }

    public static void buildLikeAllowNull(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).append("%").toString());
            sb.append(getAllowNullExpress("like", name, realName, null));
        }
    }

    public static void buildLikeStartAllowNull(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).toString());
            sb.append(getAllowNullExpress("like", name, realName, null));
        }
    }

    public static void buildLikeEndAllowNull(String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append(value).append("%").toString());
            sb.append(getAllowNullExpress("like", name, realName, null));
        }
    }

    public static void buildLike(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).append("%").toString());
            sb.append(getExpress("like", name, realName, type));
        }
    }

    public static void buildLikeStart(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).toString());
            sb.append(getExpress("like", name, realName, type));
        }
    }

    public static void buildLikeEnd(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append(value).append("%").toString());
            sb.append(getExpress("like", name, realName, type));
        }
    }

    public static void buildLikeAllowNull(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).append("%").toString());
            sb.append(getAllowNullExpress("like", name, realName, type));
        }
    }

    public static void buildLikeStartAllowNull(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append("%").append(value).toString());
            sb.append(getAllowNullExpress("like", name, realName, type));
        }
    }

    public static void buildLikeEndAllowNull(String type, String name, String value, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && !value.equals("[]"))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, (new StringBuilder()).append(value).append("%").toString());
            sb.append(getAllowNullExpress("like", name, realName, type));
        }
    }

    public static void buildEqual(String name, Object value, StringBuilder sb, Map paras)
    {
        if(!(value instanceof String) || !StringUtils.isBlank((String)value))
            build("=", name, value, sb, paras);
    }

    public static void buildEqual(String type, String name, Object value, StringBuilder sb, Map paras)
    {
        if(!(value instanceof String) || !StringUtils.isBlank((String)value))
            build(type, "=", name, value, sb, paras);
    }

    public static void buildEqualAllowNull(String name, Object value, StringBuilder sb, Map paras)
    {
        if(!(value instanceof String) || !StringUtils.isBlank((String)value))
            buildAllowNull("=", name, value, sb, paras);
    }

    public static void build(String sign, String name, Object value, StringBuilder sb, Map paras)
    {
        if(value != null && !value.equals("[]") && StringUtils.isNotBlank(value.toString()))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, value);
            sb.append(getExpress(sign, name, realName, null));
        }
    }

    public static void buildAllowNull(String sign, String name, Object value, StringBuilder sb, Map paras)
    {
        if(value != null && !value.equals("[]") && StringUtils.isNotBlank(value.toString()))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, value);
            sb.append(getAllowNullExpress(sign, name, realName, null));
        }
    }

    public static void build(String type, String sign, String name, Object value, StringBuilder sb, Map paras)
    {
        if(value != null && !value.equals("[]") && StringUtils.isNotBlank(value.toString()))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, value);
            sb.append(getExpress(sign, name, realName, type));
        }
    }

    public static void buildAllowNull(String type, String sign, String name, Object value, StringBuilder sb, Map paras)
    {
        if(value != null && !value.equals("[]") && StringUtils.isNotBlank(value.toString()))
        {
            String realName = getRealName(name, paras);
            paras.put(realName, value);
            sb.append(getAllowNullExpress(sign, name, realName, type));
        }
    }

    public static void buildIn(String name, Object value[], StringBuilder sb, Map paras)
    {
        if(value != null && value.length > 0)
        {
            String realName = getRealName(name, paras);
            paras.put(realName, ((Object) (value)));
            sb.append(getExpressArray("in", name, realName));
        }
    }

    public static void buildIn(String name, String value, String separator, StringBuilder sb, Map paras)
    {
        if(StringUtils.isNotBlank(value) && StringUtils.isNotBlank(separator))
            buildIn(name, ((Object []) (StringUtils.split(value, separator))), sb, paras);
    }

    public static String getExpressArray(String sign, String name, String realName)
    {
        return (new StringBuilder()).append(" and ").append(name).append(" ").append(sign).append(" (:").append(realName).append(")").toString();
    }

    public static String getAllowNullExpressArray(String sign, String name, String realName)
    {
        return (new StringBuilder()).append(" and (").append(name).append(" ").append(sign).append(" (:").append(realName).append(") or ").append(name).append("is null)").toString();
    }

    public static String getExpress(String sign, String name, String realName, String type)
    {
        if(StringUtils.isBlank(type))
            type = "and";
        return (new StringBuilder()).append(" ").append(type).append(" ").append(name).append(" ").append(sign).append(" :").append(realName).toString();
    }

    public static String getAllowNullExpress(String sign, String name, String realName, String type)
    {
        if(StringUtils.isBlank(type))
            type = "and";
        return (new StringBuilder()).append(" ").append(type).append(" (").append(name).append(" ").append(sign).append(" :").append(realName).append(" or ").append(name).append(" is null)").toString();
    }

    public static String getRealName(String name, Map params)
    {
        String tmp;
        for(tmp = name.replaceAll("\\.", "_"); params.containsKey(tmp); tmp = (new StringBuilder()).append(tmp).append("_").toString());
        return tmp;
    }
}
