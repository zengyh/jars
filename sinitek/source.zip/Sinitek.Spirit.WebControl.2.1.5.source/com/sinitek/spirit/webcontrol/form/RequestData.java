// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestData.java

package com.sinitek.spirit.webcontrol.form;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.enumsupport.IEnumItem;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class RequestData
{

    public RequestData()
    {
        paramters = new HashMap();
    }

    public String get(String s)
    {
        return getParamter(s);
    }

    public Integer getInteger(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return new Integer(data.trim());
        else
            return null;
    }

    public Double getDouble(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return new Double(data.trim());
        else
            return null;
    }

    public Float getFloat(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return new Float(data.trim());
        else
            return null;
    }

    public Boolean getBoolean(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data) && (data.equalsIgnoreCase("true") || data.equals("1") || data.equals("\u662F")))
            return Boolean.valueOf(true);
        else
            return Boolean.valueOf(false);
    }

    public Date getDate(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
        {
            data = data.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
            int len = data.length();
            String format;
            switch(len)
            {
            case 8: // '\b'
                format = "yyyyMMdd";
                break;

            case 6: // '\006'
                format = "HHmmss";
                break;

            case 10: // '\n'
                format = "yyyyMMddHH";
                break;

            case 12: // '\f'
                format = "yyyyMMddHHmm";
                break;

            case 14: // '\016'
                format = "yyyyMMddHHmmss";
                break;

            case 7: // '\007'
            case 9: // '\t'
            case 11: // '\013'
            case 13: // '\r'
            default:
                throw new RuntimeException((new StringBuilder()).append("\u5B57\u7B26\u4E32\uFF1A[").append(s).append("]\u5C1D\u8BD5\u8F6C\u6362\u6210date\u5931\u8D25").toString());
            }
            SimpleDateFormat sfDate = new SimpleDateFormat(format);
            try
            {
                return sfDate.parse(data);
            }
            catch(ParseException e)
            {
                throw new RuntimeException("\u65E5\u671F\u8F6C\u6362\u5931\u8D25", e);
            }
        } else
        {
            return null;
        }
    }

    public IEnumItem getEnumItem(String s, String className)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return EnumItemContext.getInstance().getEnumItem(className, (new Integer(data)).intValue());
        else
            return null;
    }

    public IEnumItem getEnumItem(String s, Class clazz)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return EnumItemContext.getInstance().getEnumItem(clazz, (new Integer(data)).intValue());
        else
            return null;
    }

    public IEnumItem getEnumItemByName(String s, String className)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return EnumItemContext.getInstance().getEnumItem(className, data);
        else
            return null;
    }

    public IEnumItem getEnumItemByName(String s, Class clazz)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
            return EnumItemContext.getInstance().getEnumItem(clazz, data);
        else
            return null;
    }

    public Date getDate(String s, String format)
    {
        SimpleDateFormat sfDate = new SimpleDateFormat(format);
        String data = getParamter(s);
        if(data != null)
            try
            {
                return sfDate.parse(data);
            }
            catch(ParseException e)
            {
                throw new RuntimeException("\u65E5\u671F\u8F6C\u6362\u5931\u8D25", e);
            }
        else
            return null;
    }

    public List getList(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
        {
            Gson gson = new Gson();
            Type type = (new TypeToken() {

                final RequestData this$0;

            
            {
                this$0 = RequestData.this;
                super();
            }
            }
).getType();
            try
            {
                return (List)gson.fromJson(data, type);
            }
            catch(Exception e)
            {
                throw new RuntimeException("\u8F6C\u6362list\u5931\u8D25\uFF0C\u5185\u5BB9\u5FC5\u987B\u4E3A\u7C7B\u4F3C[1,\"aaa\"] \u683C\u5F0F", e);
            }
        } else
        {
            return Collections.EMPTY_LIST;
        }
    }

    public Map getMap(String s)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
        {
            Gson gson = new Gson();
            Type type = (new TypeToken() {

                final RequestData this$0;

            
            {
                this$0 = RequestData.this;
                super();
            }
            }
).getType();
            try
            {
                return (Map)gson.fromJson(data, type);
            }
            catch(Exception e)
            {
                throw new RuntimeException("\u8F6C\u6362list\u5931\u8D25\uFF0C\u5185\u5BB9\u5FC5\u987B\u4E3A\u7C7B\u4F3C{a:1, b:\"sss\"} \u683C\u5F0F", e);
            }
        } else
        {
            return Collections.EMPTY_MAP;
        }
    }

    public Object getBean(String s, Class clazz)
    {
        String data = getParamter(s);
        if(StringUtils.isNotBlank(data))
        {
            Gson gson = new Gson();
            return gson.fromJson(data, clazz);
        }
        try
        {
            return clazz.newInstance();
        }
        catch(Exception e)
        {
            throw new RuntimeException("\u5B9E\u4F8B\u5316bean\u5931\u8D25\uFF0C\u5185\u5BB9\u5FC5\u987B\u4E3A\u7C7B\u4F3C{a:1, b:\"sss\"} \u683C\u5F0F", e);
        }
    }

    public String getParamter(String s)
    {
        String value[] = (String[])paramters.get(s);
        if(value != null)
        {
            String tmp = "";
            int i = 0;
            for(int valueLength = value.length; i < valueLength; i++)
            {
                String e = value[i];
                if(StringUtils.isNotBlank(e))
                    tmp = (new StringBuilder()).append(tmp).append(e).toString();
                if(i < valueLength - 1)
                    tmp = (new StringBuilder()).append(tmp).append(",").toString();
            }

            return tmp;
        } else
        {
            return null;
        }
    }

    public String[] getParamterValues(String s)
    {
        return (String[])paramters.get(s);
    }

    void setParam(Map map)
    {
        paramters = new HashMap();
        Map mapClone = new HashMap();
        mapClone.putAll(map);
        Iterator i$ = mapClone.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            String key = (String)i$.next();
            String value = (String)mapClone.get(key);
            if(StringUtils.isNotBlank(value))
                if(value.contains("@split@"))
                {
                    if(!value.equals("@split@"))
                    {
                        paramters.put(key, value.split("@split@"));
                        map.put(key, value.replaceAll("@split@", ","));
                    } else
                    {
                        map.remove(key);
                    }
                } else
                {
                    paramters.put(key, new String[] {
                        value
                    });
                }
        } while(true);
    }

    private Map paramters;
}
