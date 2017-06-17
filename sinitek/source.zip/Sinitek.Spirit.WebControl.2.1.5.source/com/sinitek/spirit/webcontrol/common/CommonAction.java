// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonAction.java

package com.sinitek.spirit.webcontrol.common;

import com.sinitek.spirit.webcontrol.utils.FormatUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.common:
//            FrameworkConfig

public class CommonAction
{

    public CommonAction()
    {
    }

    public Date getNowDate()
    {
        return new Date();
    }

    public void changeTheme(String name)
    {
        Map config = FrameworkConfig.getInstance().getConfig();
        config.put("default-theme", name);
        FrameworkConfig.getInstance().setConfig(config);
    }

    public static Map checkDate(String format, String inputString, String startFormat, String startDate, String endFormat, String endDate)
        throws Exception
    {
        Map returnMap = new HashMap();
        SimpleDateFormat sfDate = new SimpleDateFormat(format);
        Date d;
        try
        {
            sfDate.setLenient(false);
            d = sfDate.parse(inputString);
            String tmp = inputString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
            if(!StringUtils.isNumeric(tmp))
                returnMap.put("error", "\u975E\u6CD5\u683C\u5F0F");
        }
        catch(Exception e)
        {
            returnMap.put("error", (new StringBuilder()).append("\u60A8\u8F93\u5165\u7684\u65E5\u671F\"").append(FormatUtils.toSafeHtml(inputString)).append("\"\u683C\u5F0F\u6216\u6570\u636E\u6709\u8BEF\uFF0C\u8BF7\u6309\u7167\"").append(format).append("\"\u8F93\u5165!").toString());
            return returnMap;
        }
        if(StringUtils.isNotBlank(startDate))
        {
            SimpleDateFormat sfDate2 = new SimpleDateFormat(startFormat);
            Date start = sfDate2.parse(startDate);
            if(d.getTime() < start.getTime())
            {
                returnMap.put("error", (new StringBuilder()).append("\u60A8\u8F93\u5165\u7684\u7ED3\u675F\u65E5\u671F\u65E9\u4E8E\"").append(FormatUtils.toSafeHtml(startDate)).append("\"\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165").toString());
                return returnMap;
            }
        }
        if(StringUtils.isNotBlank(endDate))
        {
            SimpleDateFormat sfDate2 = new SimpleDateFormat(endFormat);
            Date end = sfDate2.parse(endDate);
            if(d.getTime() > end.getTime())
            {
                returnMap.put("error", (new StringBuilder()).append("\u60A8\u8F93\u5165\u7684\u8D77\u59CB\u65E5\u671F\u665A\u4E8E\"").append(FormatUtils.toSafeHtml(endDate)).append("\"\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165").toString());
                return returnMap;
            }
        }
        returnMap.put("value", sfDate.format(d));
        return returnMap;
    }
}
