// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CronExpressionEx.java

package com.sinitek.sirm.common.quartz;

import java.text.ParseException;
import java.util.Set;
import java.util.StringTokenizer;
import org.quartz.CronExpression;

public class CronExpressionEx extends CronExpression
{

    public CronExpressionEx(String cronExpression)
        throws ParseException
    {
        super(cronExpression);
        StringTokenizer exprsTok = new StringTokenizer(cronExpression, " \t", false);
        secondsExp = exprsTok.nextToken().trim();
        minutesExp = exprsTok.nextToken().trim();
        hoursExp = exprsTok.nextToken().trim();
        daysOfMonthExp = exprsTok.nextToken().trim();
        monthsExp = exprsTok.nextToken().trim();
        daysOfWeekExp = exprsTok.nextToken().trim();
    }

    public Set getSecondsSet()
    {
        return seconds;
    }

    public String getSecondsField()
    {
        return getExpressionSetSummary(seconds);
    }

    public Set getMinutesSet()
    {
        return minutes;
    }

    public String getMinutesField()
    {
        return getExpressionSetSummary(minutes);
    }

    public Set getHoursSet()
    {
        return hours;
    }

    public String getHoursField()
    {
        return getExpressionSetSummary(hours);
    }

    public Set getDaysOfMonthSet()
    {
        return daysOfMonth;
    }

    public String getDaysOfMonthField()
    {
        return getExpressionSetSummary(daysOfMonth);
    }

    public Set getMonthsSet()
    {
        return months;
    }

    public String getMonthsField()
    {
        return getExpressionSetSummary(months);
    }

    public Set getDaysOfWeekSet()
    {
        return daysOfWeek;
    }

    public String getDaysOfWeekField()
    {
        return getExpressionSetSummary(daysOfWeek);
    }

    public String getSecondsExp()
    {
        return secondsExp;
    }

    public String getMinutesExp()
    {
        return minutesExp;
    }

    public String getHoursExp()
    {
        return hoursExp;
    }

    public String getDaysOfMonthExp()
    {
        return daysOfMonthExp;
    }

    public String getMonthsExp()
    {
        return monthsExp;
    }

    public String getDaysOfWeekExp()
    {
        return daysOfWeekExp;
    }

    public static final Integer ALL_SPEC = new Integer(99);
    public static final int NO_SPEC_INT = 98;
    private String secondsExp;
    private String minutesExp;
    private String hoursExp;
    private String daysOfMonthExp;
    private String monthsExp;
    private String daysOfWeekExp;

}
