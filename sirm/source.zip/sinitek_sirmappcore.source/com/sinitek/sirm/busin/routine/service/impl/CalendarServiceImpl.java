// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalendarServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.ICalendar;
import com.sinitek.sirm.busin.routine.service.ICalendarService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.enumerate.OrgType;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarServiceImpl extends MetaDBContextSupport
    implements ICalendarService
{

    public CalendarServiceImpl()
    {
    }

    public void saveCalendarService(ICalendar calendar)
    {
        if(calendar != null)
        {
            if(null == calendar.getStatus())
                calendar.setStatus(Integer.valueOf(100));
            save(calendar);
        }
    }

    public List findCalendarList(Date startdate, Date enddate, int empId)
    {
        IMetaDBQuery _metaDBQuery = null;
        Map _parm = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select t.objid,t.subject,cast(t.enddate as timestamp) as enddate,cast(t.begindate as timestamp) as begindate, \n");
        _builder.append("t.content,t.sourceentity,t.sourceid,t.completestatus , t.type, t.url \n");
        _builder.append("   from rt_calendar t where 1=1 \n");
        _builder.append(" and t.status=100 \n");
        if(null != startdate)
            QueryUtils.build(">=", "begindate", startdate, _builder, _parm);
        if(null != enddate)
            QueryUtils.build("<=", "enddate", enddate, _builder, _parm);
        QueryUtils.buildEqual("empid", Integer.valueOf(empId), _builder, _parm);
        _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        if(!_parm.isEmpty())
            _metaDBQuery.setParameters(_parm);
        return _metaDBQuery.getResult();
    }

    public List findCalendarList(Date startdate, Date enddate)
    {
        IMetaDBQuery _metaDBQuery = null;
        Map _parm = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select t.objid,t.subject,to_char(t.enddate,   'YYYY-MM-DD')as enddate, to_char  (t.begindate, 'YYYY-MM-DD')as   begindate, \n");
        _builder.append("      t.content, t.sourceentity, t.sourceid , a.orgname , t.type, t.url \n");
        _builder.append("  from rt_calendar t  \n");
        _builder.append("    left join    sprt_orgobject a   on a.orgid = to_char(t.empid) \n");
        _builder.append("where     1 = 1 and t.status=100  ");
        if(null != startdate && null != enddate)
        {
            _builder.append(" and  t.begindate <=to_date('");
            _builder.append(TimeUtil.formatDate(enddate, "yyyyMMdd"));
            _builder.append("235959','YYYY-MM-DD HH24:MI:SS') and t.enddate >=to_date('");
            _builder.append(TimeUtil.formatDate(startdate, "yyyyMMdd"));
            _builder.append("000000','YYYY-MM-DD HH24:MI:SS')");
        }
        _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        if(!_parm.isEmpty())
            _metaDBQuery.setParameters(_parm);
        _metaDBQuery.setDefaultOrderBy(" t.empid ");
        return _metaDBQuery.getResult();
    }

    public List findCalendarList(String time, String empId)
    {
        IMetaDBQuery _metaDBQuery = null;
        StringBuilder sql = new StringBuilder();
        Calendar _calendar = Calendar.getInstance();
        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map _parm = new HashMap();
        sql.append("select t.objid ,t.subject ,cast(t.begindate as timestamp)begindate ,o.orgname, \n");
        sql.append("      t.sourceentity,t.sourceid, t.type, t.url \n");
        sql.append(" from rt_calendar t \n");
        sql.append("   left join sprt_orgobject o   on o.orgid = to_char(t.empid)  \n");
        sql.append("where 1 = 1 and t.status=100 ");
        if(!"".equals(empId) && null != empId)
        {
            sql.append(" and t.empid='");
            sql.append(empId);
            sql.append("'");
        }
        if(!"".equals(time) && null != time)
        {
            sql.append(" and  t.begindate <=to_date('");
            sql.append(time);
            sql.append("235959','YYYY-MM-DD HH24:MI:SS') and t.enddate >=to_date('");
            sql.append(time);
            sql.append("000000','YYYY-MM-DD HH24:MI:SS')");
        }
        sql.append(" order by t.empid ");
        _metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        _metaDBQuery.setParameters(_parm);
        return _metaDBQuery.getResult();
    }

    public List findCalendaruser(String begintime, String endtime)
    {
        IMetaDBQuery _metaDBQuery = null;
        StringBuilder sql = new StringBuilder();
        Calendar _calendar = Calendar.getInstance();
        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map _parm = new HashMap();
        sql.append("select distinct t.empid from rt_calendar t   where  1 = 1 ");
        if(!"".equals(begintime) && null != begintime && !"".equals(endtime) && null != endtime)
        {
            sql.append(" and  t.begindate <=to_date('");
            sql.append(endtime);
            sql.append("235959','YYYY-MM-DD HH24:MI:SS') and t.enddate >=to_date('");
            sql.append(begintime);
            sql.append("000000','YYYY-MM-DD HH24:MI:SS')");
        }
        _metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        _metaDBQuery.setParameters(_parm);
        return _metaDBQuery.getResult();
    }

    public List getCalendarList(String date, String userids)
    {
        IMetaDBQuery _metaDBQuery = null;
        Map _parm = new HashMap();
        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar _calendar = Calendar.getInstance();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select * from rt_calendar where 1=1");
        if(date != null && !date.equals(""))
        {
            Date _date = TimeUtil.formatDate(date, "yyyy-MM-dd");
            _calendar.setTime(_date);
            _calendar.add(6, 6 - _calendar.get(7));
            Date _enddate = TimeUtil.formatDate(_sdf.format(_calendar.getTime()), "yyyy-MM-dd");
            QueryUtils.build("<=", "enddate", _enddate, _builder, _parm);
            _calendar.add(6, 2 - _calendar.get(7));
            Date _begindate = TimeUtil.formatDate(_sdf.format(_calendar.getTime()), "yyyy-MM-dd");
            QueryUtils.build(">=", "begindate", _begindate, _builder, _parm);
        } else
        {
            _calendar.setTime(new Date());
            _calendar.add(6, 6 - _calendar.get(7));
            Date _enddate = TimeUtil.formatDate(_sdf.format(_calendar.getTime()), "yyyy-MM-dd");
            QueryUtils.build("<=", "enddate", _enddate, _builder, _parm);
            _calendar.add(6, 2 - _calendar.get(7));
            Date _begindate = TimeUtil.formatDate(_sdf.format(_calendar.getTime()), "yyyy-MM-dd");
            QueryUtils.build(">=", "begindate", _begindate, _builder, _parm);
        }
        List list = new ArrayList();
        IOrgService _service = OrgServiceFactory.getOrgService();
        List _allEmployees = _service.findAllEmployees();
        StringBuffer users = new StringBuffer();
        List _personList = new ArrayList();
        if(userids != null && !userids.equals(""))
        {
            String _strperson[] = userids.split("\\,");
            for(int i = 0; i < _strperson.length; i++)
            {
                Employee employee = new Employee();
                String _receivepersontype[] = _strperson[i].split("\\:");
                if(_receivepersontype[2].equals((new StringBuilder()).append(OrgType.DEPT.getEnumItemValue()).append("").toString()))
                {
                    _personList.addAll(_service.findEmployeeByUnitId(_receivepersontype[0]));
                    continue;
                }
                if(_receivepersontype[2].equals((new StringBuilder()).append(OrgType.POSITION.getEnumItemValue()).append("").toString()))
                {
                    _personList.addAll(_service.findEmployeeByPosId(_receivepersontype[0]));
                    continue;
                }
                if(_receivepersontype[2].equals((new StringBuilder()).append(OrgType.TEAM.getEnumItemValue()).append("").toString()))
                {
                    _personList.addAll(_service.findEmployeeByTeamId(_receivepersontype[0]));
                } else
                {
                    employee = _service.getEmployeeById(_receivepersontype[0]);
                    _personList.add(employee);
                }
            }

        }
        for(Iterator i$ = _personList.iterator(); i$.hasNext(); users.append(","))
        {
            Employee obj = (Employee)i$.next();
            users.append(obj.getId());
        }

        userids = users.toString();
        for(int i = 0; i < _allEmployees.size(); i++)
        {
            Employee _employee;
            String sql;
            if(!"".equals(userids) && null != userids)
            {
                _employee = (Employee)_allEmployees.get(i);
                if(-1 == userids.indexOf((new StringBuilder()).append(_employee.getId()).append("").toString()))
                    continue;
                sql = (new StringBuilder()).append(_builder.toString()).append(" and empid = ").append(_employee.getId()).toString();
                _metaDBQuery = getMetaDBContext().createSqlQuery(sql);
                if(_parm != null)
                    _metaDBQuery.setParameters(_parm);
                list.add(_metaDBQuery.getResult());
                continue;
            }
            _employee = (Employee)_allEmployees.get(i);
            sql = (new StringBuilder()).append(_builder.toString()).append(" and empid = ").append(_employee.getId()).toString();
            _metaDBQuery = getMetaDBContext().createSqlQuery(sql);
            if(_parm != null)
                _metaDBQuery.setParameters(_parm);
            list.add(_metaDBQuery.getResult());
        }

        return list;
    }

    public void deleteCalendar(int id)
    {
        ICalendar _calendar = (ICalendar)getMetaDBContext().get("CALENDAR", id);
        remove(_calendar);
    }

    public ICalendar getCalendarById(int id)
    {
        ICalendar _calendar = null;
        _calendar = (ICalendar)getMetaDBContext().get("CALENDAR", id);
        return _calendar;
    }

    public boolean saveCalendar(ICalendar calendar)
    {
        if(compareDataCalendarDate(calendar.getBeginDate(), calendar.getEndDate(), (new StringBuilder()).append(calendar.getEmpId()).append("").toString()))
        {
            calendar.save();
            return true;
        } else
        {
            return false;
        }
    }

    public boolean compareDataCalendarDate(Date beginTime, Date endTime, String empid)
    {
        Map params = new HashMap();
        params.put("empid", empid);
        List calendars = getMetaDBContext().query("CALENDAR", "empid = :empid and enddate is not null", params);
        for(Iterator i$ = calendars.iterator(); i$.hasNext();)
        {
            ICalendar obj = (ICalendar)i$.next();
            if(!compareData(beginTime, endTime, obj.getBeginDate(), obj.getEndDate()))
                return false;
        }

        return true;
    }

    public boolean compareData(Date sdate1, Date sdate2, Date date1, Date date2)
    {
        boolean result = true;
        if(sdate1.getTime() > date1.getTime())
        {
            if(sdate1.getTime() < date2.getTime())
                result = false;
        } else
        if(sdate2.getTime() > date1.getTime())
            result = false;
        return result;
    }

    public void delCalenderBySourceId(String sourceEntity, String sourceId)
    {
        String _sql = "sourceid=:sourceid and sourceentity=:sourceentity";
        Map _param = new HashMap();
        _param.put("sourceid", sourceId);
        _param.put("sourceentity", sourceEntity);
        List list = getMetaDBContext().query("CALENDAR", _sql, _param);
        ICalendar obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); remove(obj))
            obj = (ICalendar)i$.next();

    }

    public String findSameCalendarList(String _empid, Date _begintime, Date _endtime, String tableName)
    {
        StringBuffer allSameCalendar = new StringBuffer("\u60A8\u5F53\u524D\u7684\u65E5\u7A0B\u5B89\u6392\u6709\u51B2\u7A81\uFF1A<br />");
        String sameCalendar = null;
        String empName = findInvestigationPerson(_empid);
        Calendar calendar = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        Map params = new HashMap();
        params.put("empid", _empid);
        int j = 0;
        if(tableName != null && tableName.equals("sr_calendar"))
        {
            List calendars = getMetaDBContext().query("CALENDAR", "empid = :empid", params);
            j = 0;
            for(Iterator i$ = calendars.iterator(); i$.hasNext(); sameCalendar = null)
            {
                ICalendar obj = (ICalendar)i$.next();
                sameCalendar = new String();
                if(obj.getBeginDate() == null || obj.getEndDate() == null)
                    continue;
                calendar.setTime(obj.getBeginDate());
                calendarEnd.setTime(obj.getEndDate());
                if(_begintime.after(obj.getBeginDate()))
                {
                    if(!_begintime.before(obj.getEndDate()))
                        continue;
                    sameCalendar = (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(5)).append(" ").append("~").append(" ").append(calendarEnd.get(1)).append("-").append(calendarEnd.get(2) + 1).append("-").append(calendarEnd.get(5)).append(" :").append(obj.getSubject()).toString();
                    allSameCalendar.append((new StringBuilder()).append(sameCalendar).append("<br />").toString());
                    if(j > 3)
                        break;
                    j++;
                    continue;
                }
                if(!_endtime.after(obj.getBeginDate()))
                    continue;
                sameCalendar = (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(5)).append(" ").append("~").append(" ").append(calendarEnd.get(1)).append("-").append(calendarEnd.get(2) + 1).append("-").append(calendarEnd.get(5)).append(" :").append(obj.getSubject()).toString();
                allSameCalendar.append((new StringBuilder()).append(sameCalendar).append("<br />").toString());
                if(j > 3)
                    break;
                j++;
            }

        }
        if(allSameCalendar.length() > 18)
        {
            if(j > 3)
                allSameCalendar.append("&nbsp;&nbsp;&nbsp;............");
            allSameCalendar.append("<br />&nbsp;&nbsp;&nbsp;\u662F\u5426\u7EE7\u7EED\u6DFB\u52A0\uFF1F");
            return allSameCalendar.toString();
        } else
        {
            return null;
        }
    }

    public static String findInvestigationPerson(String _investigationPersonId)
    {
        String _personname = "";
        IOrgService _service = OrgServiceFactory.getOrgService();
        Employee employee = _service.getEmployeeById(_investigationPersonId);
        if(null != employee)
            _personname = employee.getEmpName();
        return _personname;
    }

    public List findCalendar(String sourceentity, String sourceid)
    {
        Map params = new HashMap();
        params.put("sourceentity", sourceentity);
        params.put("sourceid", sourceid);
        List calendars = getMetaDBContext().query("CALENDAR", "sourceentity=:sourceentity and sourceid=:sourceid", params);
        return calendars;
    }

    public List findCalendarByRemindFlag(int remindFlag)
    {
        Calendar calendar = Calendar.getInstance();
        Map params = new HashMap();
        params.put("remindflag", Integer.valueOf(remindFlag));
        params.put("begindate", calendar.getTime());
        List calendars = getMetaDBContext().query("CALENDAR", "remindflag=:remindflag and remindmethod != 0 and completestatus = 0 and begindate>:begindate", params);
        return calendars;
    }
}
