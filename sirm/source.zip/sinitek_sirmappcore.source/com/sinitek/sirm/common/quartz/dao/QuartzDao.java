// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QuartzDao.java

package com.sinitek.sirm.common.quartz.dao;

import com.sinitek.sirm.common.quartz.service.impl.Constant;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class QuartzDao
{

    public QuartzDao()
    {
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void save(String name, String timeexpression, String memo)
    {
        String _sql = "INSERT INTO QRTZ_EXPRESSION ( NAME, EXPRESSION, MEMO) VALUES ( ?, ?, ?)";
        Object _params[] = new Object[3];
        _params[0] = name;
        _params[1] = timeexpression;
        _params[2] = memo;
        getJdbcTemplate().update(_sql, _params);
    }

    public List getQrtzTriggers()
    {
        List results = getJdbcTemplate().queryForList("select qt.*,qjd.job_class_name,qct.cron_expression from QRTZ_TRIGGERS qt inner join qrtz_job_details qjd on qt.job_name=qjd.job_name and qt.job_group=qjd.job_group inner join qrtz_cron_triggers qct on qct.trigger_name=qt.trigger_name and qct.trigger_group=qt.trigger_group order by qct.trigger_name , start_time");
        long val = 0L;
        String temp = null;
        Map map;
        for(Iterator i$ = results.iterator(); i$.hasNext(); map.put("statu", Constant.status.get(MapUtils.getString(map, "trigger_state"))))
        {
            map = (Map)i$.next();
            temp = MapUtils.getString(map, "trigger_name");
            if(StringUtils.indexOf(temp, "&") != -1)
                map.put("display_name", StringUtils.substringBefore(temp, "&"));
            else
                map.put("display_name", temp);
            val = Long.parseLong(MapUtils.getString(map, "next_fire_time"));
            if(val > 0L)
                map.put("next_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            val = Long.parseLong(MapUtils.getString(map, "prev_fire_time"));
            if(val > 0L)
                map.put("prev_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            val = Long.parseLong(MapUtils.getString(map, "start_time"));
            if(val > 0L)
                map.put("start_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            val = Long.parseLong(MapUtils.getString(map, "end_time"));
            if(val > 0L)
                map.put("end_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
        }

        return results;
    }

    private JdbcTemplate getJdbcTemplate()
    {
        return new JdbcTemplate(dataSource);
    }

    private DataSource dataSource;
}
