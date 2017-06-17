// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateFieldTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextFieldTag, DateField

public class DateFieldTag extends TextFieldTag
{

    public DateFieldTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new DateField(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        DateField cmp = (DateField)component;
        cmp.setDateFormat(dateFormat);
        cmp.setTimeFormat(timeFormat);
        cmp.setAppendText(appendText);
        cmp.setAutoSize(autoSize);
        cmp.setChangeMonth(changeMonth);
        cmp.setChangeYear(changeYear);
        cmp.setDefaultDate(defaultDate);
        cmp.setMaxDate(maxDate);
        cmp.setMinDate(minDate);
        cmp.setYearRange(yearRange);
        cmp.setType(type);
        cmp.setCheckDate(checkDate);
        cmp.setStartDate(startDate);
        cmp.setEndDate(endDate);
        cmp.setShowSecond(showSecond);
        cmp.setMinuteStep(minuteStep);
        cmp.setFirstDay(firstDay);
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public void setMinuteStep(String minuteStep)
    {
        this.minuteStep = minuteStep;
    }

    public void setAppendText(String appendText)
    {
        this.appendText = appendText;
    }

    public void setAutoSize(String autoSize)
    {
        this.autoSize = autoSize;
    }

    public void setChangeMonth(String changeMonth)
    {
        this.changeMonth = changeMonth;
    }

    public void setChangeYear(String changeYear)
    {
        this.changeYear = changeYear;
    }

    public void setDefaultDate(Date defaultDate)
    {
        this.defaultDate = defaultDate;
    }

    public void setMaxDate(String maxDate)
    {
        this.maxDate = maxDate;
    }

    public void setMinDate(String minDate)
    {
        this.minDate = minDate;
    }

    public void setYearRange(String yearRange)
    {
        this.yearRange = yearRange;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }

    public void setCheckDate(String checkDate)
    {
        this.checkDate = checkDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public void setFirstDay(String firstDay)
    {
        this.firstDay = firstDay;
    }

    public void setShowSecond(String showSecond)
    {
        this.showSecond = showSecond;
    }

    private String dateFormat;
    private String appendText;
    private String autoSize;
    private String changeMonth;
    private String changeYear;
    private Date defaultDate;
    private String maxDate;
    private String minDate;
    private String yearRange;
    private String type;
    private String timeFormat;
    private String checkDate;
    private String startDate;
    private String endDate;
    private String showSecond;
    private String minuteStep;
    private String firstDay;
}
