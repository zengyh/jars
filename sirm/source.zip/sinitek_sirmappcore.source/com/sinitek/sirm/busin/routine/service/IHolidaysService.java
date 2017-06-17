// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IHolidaysService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.sirm.busin.routine.entity.IRTHolidays;
import java.util.Date;
import java.util.List;

public interface IHolidaysService
{

    public abstract List getAllHolidays(int i);

    public abstract void saveHolidays(IRTHolidays irtholidays);

    public abstract IRTHolidays getHolidays(int i);

    public abstract void delHolidays(int i);

    public abstract boolean checkHolidays(Date date);

    public abstract List checkHolidays(Date date, Date date1);

    public abstract List getHolidays(Date date, Date date1);

    public abstract List getHolidaysByDate(Date date, Date date1);

    public abstract List getAllTradedays(int i);

    public abstract List getAllWorkdays(int i);

    public abstract boolean checkWorkdays(Date date);

    public abstract void delWorkdays(int i);

    public abstract void saveTradedays(IRTHolidays irtholidays);

    public abstract void delTradedays(int i);
}
