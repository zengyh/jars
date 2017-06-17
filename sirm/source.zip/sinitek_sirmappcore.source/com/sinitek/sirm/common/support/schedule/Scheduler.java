// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Scheduler.java

package com.sinitek.sirm.common.support.schedule;

import com.sinitek.base.metadb.starter.MetaDBStarter;
import com.sinitek.sirm.common.utils.IOUtil;
import com.sinitek.sirm.common.utils.ObjectUtils;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Referenced classes of package com.sinitek.sirm.common.support.schedule:
//            IScheduleInitializer, IScheduleTask

public class Scheduler
{

    public Scheduler()
    {
    }

    public static void main(String args[])
    {
        if(args.length < 1)
        {
            System.out.println("usage: schedule <taskname>");
            return;
        }
        boolean isok = init();
        if(!isok)
            return;
        String taskname;
        IScheduleTask task;
        taskname = args[0];
        task = getScheduleTask(taskname);
        if(task == null)
        {
            System.out.println((new StringBuilder()).append("cannot find [").append(taskname).append("] task.").toString());
            return;
        }
        try
        {
            loadLog4j(taskname);
            loadMetaDB();
            loadSpring();
            task.init();
            task.execute();
            task.destroy();
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.out);
        }
        return;
    }

    private static boolean init()
    {
        System.out.println("load schedule.properties");
        InputStream fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/schedule.properties");
        if(fin == null)
        {
            System.out.println("cannot find [config/schedule.properties]");
            return false;
        }
        try
        {
            properties.load(fin);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        String propvalue = properties.getProperty("initlializer");
        if(StringUtils.isNotBlank(propvalue))
            try
            {
                IScheduleInitializer initializer = (IScheduleInitializer)Class.forName(propvalue).newInstance();
                initializer.init(properties);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        return true;
    }

    private static void loadMetaDB()
    {
        System.out.println("load metadb");
        (new MetaDBStarter()).start(new Properties());
    }

    private static void loadLog4j(String taskname)
    {
        System.out.println("load log4j");
        String logfile = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(".");
        try
        {
            File file = new File(url.toURI());
            logfile = IOUtil.combinePath(new String[] {
                file.getAbsolutePath(), (new StringBuilder()).append("log\\").append(taskname).toString(), taskname
            });
        }
        catch(URISyntaxException e)
        {
            e.printStackTrace();
        }
        System.out.println((new StringBuilder()).append("logfile:").append(logfile).toString());
        Properties props = new Properties();
        props.setProperty("log4j.rootLogger", "off");
        props.setProperty("log4j.logger.com.sinitek.sirm", "debug, sirmschedule, sirmschedulefile");
        props.setProperty("log4j.appender.sirmschedule", "org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.appender.sirmschedule.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.sirmschedule.layout.ConversionPattern", "[%p][%d{yyyy-MM-dd HH:mm:ss.SSS}][%C{1}:%L] - %m%n");
        props.setProperty("log4j.appender.sirmschedulefile", "org.apache.log4j.DailyRollingFileAppender");
        props.setProperty("log4j.appender.sirmschedulefile.file", logfile);
        props.setProperty("log4j.appender.sirmschedulefile.DatePattern", "'.'yyyy-MM-dd");
        props.setProperty("log4j.appender.sirmschedulefile.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.sirmschedulefile.layout.ConversionPattern", "[%p][%d{yyyy-MM-dd HH:mm:ss.SSS}][%C{1}:%L] - %m%n");
        PropertyConfigurator.configure(props);
    }

    private static void loadSpring()
    {
        System.out.println("load spring");
        new ClassPathXmlApplicationContext("classpath*:springCfg/*.xml");
    }

    private static IScheduleTask getScheduleTask(String taskname)
    {
        System.out.println("load schedule.properties");
        String propname = (new StringBuilder()).append("task.").append(taskname).toString();
        String propvalue = properties.getProperty(propname);
        if(StringUtils.isBlank(propvalue))
        {
            System.out.println((new StringBuilder()).append("cannot read [").append(propname).append("] from schedule.properties").toString());
            return null;
        } else
        {
            return (IScheduleTask)ObjectUtils.newInstance(propvalue);
        }
    }

    private static Properties properties = new Properties();

}
