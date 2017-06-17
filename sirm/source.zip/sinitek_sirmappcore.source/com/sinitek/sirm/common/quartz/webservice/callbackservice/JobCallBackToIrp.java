// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobCallBackToIrp.java

package com.sinitek.sirm.common.quartz.webservice.callbackservice;

import com.sinitek.sirm.common.quartz.service.AbstractJob;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.Job;

public class JobCallBackToIrp
{

    public JobCallBackToIrp()
    {
    }

    public String jobBackResult(String classbname, String parameters, String desc)
    {
        LOGGER.info("\u3010JobCallBackToIrp\u3011\u3010\u63A5\u53D7job\u670D\u52A1\u8BF7\u6C42\u3011");
        try
        {
            LOGGER.info((new StringBuilder()).append("\u3010JobCallBackToIrp\u3011\u6267\u884C\uFF1A\u3010").append(desc).append("\u3011\u3010").append(classbname).append("\u3011").toString());
            Job job = (Job)(Job)Class.forName(classbname).newInstance();
            if(job instanceof AbstractJob)
                ((AbstractJob)job).setData(getParameter(parameters));
            job.execute(null);
            LOGGER.info("\u3010JobCallBackToIrp\u3011\u3010\u5B8C\u6210job\u670D\u52A1\u8BF7\u6C42\u3011");
            return "SUCCESS";
        }
        catch(ClassNotFoundException e)
        {
            LOGGER.error((new StringBuilder()).append("\u3010JobCallBackToIrp\u3011\u4E0D\u80FD\u627E\u5230").append(classbname).toString(), e);
            return "ERROR";
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u3010JobCallBackToIrp\u3011\u6267\u884C\u3010").append(classbname).append("\u3011\u5931\u8D25").toString(), e);
        }
        return "ERROR";
    }

    private Map getParameter(String parameters)
    {
        Map map = null;
        if(parameters == null || "".equals(parameters))
            return map;
        int count = 0;
        map = new HashMap();
        String params[] = parameters.split("\\[\\]");
        if(params != null && params.length > 0)
        {
            String arr$[] = params;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String param = arr$[i$];
                if(param == null || "".equals(param))
                    continue;
                String contexts[] = param.split("<>");
                if("String".equals(contexts[1]))
                {
                    String value = contexts[2];
                    map.put(contexts[0], value);
                }
                if("Integer".equals(contexts[1]))
                {
                    String value = contexts[2];
                    map.put(contexts[0], Integer.valueOf(Integer.parseInt(value)));
                }
                if("Double".equals(contexts[1]))
                {
                    String value = contexts[2];
                    map.put(contexts[0], Double.valueOf(Double.parseDouble(value)));
                }
                if("Long".equals(contexts[1]))
                {
                    String value = contexts[2];
                    map.put(contexts[0], Long.valueOf(Long.parseLong(value)));
                }
                System.out.println((new StringBuilder()).append("\u53C2\u6570\u3010").append(count).append("\u3011\u3010").append(contexts[0]).append("\u3011\u3010").append(contexts[1]).append("\u3011\u503C\u3010").append(contexts[2]).append("\u3011\u3010").append(contexts[3]).append("\u3011").toString());
                count++;
            }

        }
        return map;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/quartz/webservice/callbackservice/JobCallBackToIrp);

}
