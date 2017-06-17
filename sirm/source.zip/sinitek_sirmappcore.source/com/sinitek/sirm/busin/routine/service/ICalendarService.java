// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ICalendarService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.sirm.busin.routine.entity.ICalendar;
import java.util.Date;
import java.util.List;

public interface ICalendarService
{

    public abstract void saveCalendarService(ICalendar icalendar);

    public abstract void deleteCalendar(int i);

    public abstract ICalendar getCalendarById(int i);

    public abstract List findCalendarList(Date date, Date date1, int i);

    public abstract List findCalendarList(String s, String s1);

    public abstract List findCalendarList(Date date, Date date1);

    public abstract List findCalendaruser(String s, String s1);

    public abstract List getCalendarList(String s, String s1);

    public abstract boolean compareDataCalendarDate(Date date, Date date1, String s);

    public abstract boolean saveCalendar(ICalendar icalendar);

    public abstract void delCalenderBySourceId(String s, String s1);

    public abstract String findSameCalendarList(String s, Date date, Date date1, String s1);

    public abstract List findCalendar(String s, String s1);

    public abstract List findCalendarByRemindFlag(int i);
}
