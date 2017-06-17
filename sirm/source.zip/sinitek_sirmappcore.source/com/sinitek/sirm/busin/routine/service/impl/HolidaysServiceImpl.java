// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HolidaysServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.IRTHolidays;
import com.sinitek.sirm.busin.routine.service.IHolidaysService;
import com.sinitek.sirm.common.utils.TimeUtil;
import java.util.*;

public class HolidaysServiceImpl extends AbstractMetaDBContextSupport
    implements IHolidaysService
{

    public HolidaysServiceImpl()
    {
    }

    public List getAllHolidays(int year)
    {
        return getAllDaysByType(year, 0);
    }

    public void saveHolidays(IRTHolidays holidays)
    {
        holidays.save();
    }

    public IRTHolidays getHolidays(int objid)
    {
        return (IRTHolidays)getMetaDBContext().get("RTHOLIDAYS", objid);
    }

    public void delHolidays(int year)
    {
        String sql = "delete from RT_Holidays where objid in (select objid from RT_Holidays where year=:year and type = 0)";
        Map map = new HashMap();
        if(year != 0)
        {
            map.put("year", Integer.valueOf(year));
            ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sql);
            updater.setParameters(map);
            updater.executeUpdate();
        }
    }

    public boolean checkHolidays(Date date)
    {
        boolean bf = false;
        StringBuilder _builder = new StringBuilder(" 1=1");
        Map _parm = new HashMap();
        if(null != date)
        {
            _builder.append("and to_date(holidaystime,'YYYY-MM-DD')=to_date(:date,'YYYY-MM-DD')");
            _builder.append(" and type = 0");
            _parm.put("date", TimeUtil.formatDate(date, "yyyy-MM-dd"));
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTHOLIDAYS", _builder.toString());
            iMetaDBQuery.setParameters(_parm);
            List list = iMetaDBQuery.getResult();
            if(list.size() > 0)
                bf = true;
        }
        return bf;
    }

    public List checkHolidays(Date startdate, Date enddate)
    {
        StringBuilder _builder = new StringBuilder(" 1=1 ");
        Map _parm = new HashMap();
        if(null != startdate)
        {
            _builder.append(" and to_date(holidaystime,'YYYY-MM-DD')>=to_date(:startdate,'YYYY-MM-DD')");
            _builder.append(" and type = 0");
            _parm.put("startdate", TimeUtil.formatDate(startdate, "yyyy-MM-dd"));
        }
        if(null != enddate)
        {
            _builder.append(" and to_date(holidaystime,'YYYY-MM-DD')<=to_date(:enddate,'YYYY-MM-DD')");
            _builder.append(" and type = 0");
            _parm.put("enddate", TimeUtil.formatDate(enddate, "yyyy-MM-dd"));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTHOLIDAYS", _builder.toString());
        iMetaDBQuery.setParameters(_parm);
        return iMetaDBQuery.getResult();
    }

    public List getHolidays(Date startdate, Date enddate)
    {
        StringBuilder _builder = new StringBuilder(" 1=1 ");
        Map _parm = new HashMap();
        if(null != startdate)
        {
            _builder.append(" and to_date(holidaystime,'YYYY-MM-DD')>=to_date(:startdate,'YYYY-MM-DD')");
            _parm.put("startdate", TimeUtil.formatDate(startdate, "yyyy-MM-dd"));
        }
        if(null != enddate)
        {
            _builder.append(" and to_date(holidaystime,'YYYY-MM-DD')<=to_date(:enddate,'YYYY-MM-DD')");
            _parm.put("enddate", TimeUtil.formatDate(enddate, "yyyy-MM-dd"));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTHOLIDAYS", _builder.toString());
        iMetaDBQuery.setParameters(_parm);
        return iMetaDBQuery.getResult();
    }

    public List getHolidaysByDate(Date startdate, Date enddate)
    {
        List result = new ArrayList();
        List holidaysList = checkHolidays(startdate, enddate);
        IRTHolidays holidays;
        for(Iterator i$ = holidaysList.iterator(); i$.hasNext(); result.add(TimeUtil.formatDate(holidays.getHolidaysTime(), "yyyy-MM-dd")))
            holidays = (IRTHolidays)i$.next();

        return result;
    }

    public List getAllTradedays(int year)
    {
        return getAllDaysByType(year, 2);
    }

    private List getAllDaysByType(int year, int type)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(year != 0)
        {
            sql.append(" and year=:year");
            map.put("year", Integer.valueOf(year));
        }
        if(type != -1)
        {
            sql.append(" and type =:type");
            map.put("type", Integer.valueOf(type));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTHOLIDAYS", sql.toString());
        iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public List getAllWorkdays(int year)
    {
        return getAllDaysByType(year, 3);
    }

    public boolean checkWorkdays(Date date)
    {
        boolean bf = false;
        StringBuilder _builder = new StringBuilder(" 1=1");
        Map _parm = new HashMap();
        if(null != date)
        {
            _builder.append("and to_date(holidaystime,'YYYY-MM-DD')=to_date(:date,'YYYY-MM-DD')");
            _builder.append(" and type = 3");
            _parm.put("date", TimeUtil.formatDate(date, "yyyy-MM-dd"));
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTHOLIDAYS", _builder.toString());
            iMetaDBQuery.setParameters(_parm);
            List list = iMetaDBQuery.getResult();
            if(list.size() > 0)
                bf = true;
        }
        return bf;
    }

    public void delWorkdays(int year)
    {
        String sql = "delete from RT_Holidays where objid in (select objid from RT_Holidays where year=:year and type = 3)";
        Map map = new HashMap();
        if(year != 0)
        {
            map.put("year", Integer.valueOf(year));
            ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sql);
            updater.setParameters(map);
            updater.executeUpdate();
        }
    }

    public void saveTradedays(IRTHolidays holidays)
    {
        holidays.save();
    }

    public void delTradedays(int year)
    {
        String sql = "delete from RT_Holidays where objid in (select objid from RT_Holidays where year=:year and type = 2)";
        Map map = new HashMap();
        if(year != 0)
        {
            map.put("year", Integer.valueOf(year));
            ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sql);
            updater.setParameters(map);
            updater.executeUpdate();
        }
    }
}
