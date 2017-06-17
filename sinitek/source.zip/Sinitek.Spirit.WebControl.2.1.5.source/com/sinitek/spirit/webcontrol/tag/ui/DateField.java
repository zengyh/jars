// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateField.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.common.FrameworkConfig;
import com.sinitek.spirit.webcontrol.utils.DataParserUtils;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextField

public class DateField extends TextField
{

    public DateField(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "datefield";
    }

    protected String getDefaultTemplate()
    {
        return "datefield-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "datefield");
        if(StringUtils.isNotBlank(dateFormat))
        {
            addProperty("dateFormat", dateFormat.replaceAll("yy", "y").replaceAll("M", "m"), "yy-mm-dd");
        } else
        {
            dateFormat = "yyyy-MM-dd";
            addProperty("dateFormat", "yy-mm-dd");
        }
        if(StringUtils.isNotBlank(timeFormat))
        {
            addProperty("timeFormat", timeFormat.replaceAll("h", "H"), "hh:mm:ss");
        } else
        {
            if("false".equals(showSecond))
                timeFormat = "HH:mm";
            else
                timeFormat = "HH:mm:ss";
            addProperty("timeFormat", "hh:mm:ss");
        }
        addProperty("appendText", appendText);
        addProperty("type", type, "date");
        addProperty("autoSize", autoSize);
        addProperty("changeMonth", changeMonth, "true");
        addProperty("changeYear", changeYear, "true");
        addProperty("checkDate", checkDate, "true");
        addProperty("startDate", startDate);
        addProperty("endDate", endDate);
        addProperty("showSecond", showSecond, "true");
        addProperty("minuteStep", minuteStep, "5");
        String _firstDay = "1";
        if(StringUtils.isNotBlank(FrameworkConfig.getInstance().getParam("ui.datefield.firstDay")))
            _firstDay = FrameworkConfig.getInstance().getParam("ui.datefield.firstDay");
        addProperty("firstDay", firstDay, _firstDay);
        String format;
        if("time".equals(type))
            format = timeFormat;
        else
        if("datetime".equals(type))
            format = (new StringBuilder()).append(dateFormat).append(" ").append(timeFormat).toString();
        else
            format = dateFormat;
        if(defaultDate instanceof Date)
        {
            String sdefaultDate = DataParserUtils.formatDate(defaultDate, format);
            addProperty("sDefaultValue", (new StringBuilder()).append("'").append(sdefaultDate).append("'").toString());
            addProperty("defaultValueType", "string");
        } else
        if(defaultDate != null)
        {
            addProperty("sDefaultValue", (new StringBuilder()).append("'").append(defaultDate).append("'").toString());
            addProperty("defaultValueType", "string");
        }
        addProperty("maxDate", maxDate);
        addProperty("minDate", minDate);
        addProperty("yearRange", yearRange);
        String width;
        if("datetime".equals(type))
        {
            if("false".equals(showSecond))
                width = "120px";
            else
                width = "140px";
        } else
        {
            width = "90px";
        }
        addProperty("width", width);
        if("time".equals(type))
            format = dateFormat;
        else
        if("datetime".equals(type))
            format = (new StringBuilder()).append(dateFormat).append(" ").append(timeFormat).toString();
        else
            format = dateFormat;
        addProperty("format", format);
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public void setType(String type)
    {
        this.type = type;
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

    public void setShowSecond(String showSecond)
    {
        this.showSecond = showSecond;
    }

    public void setFirstDay(String firstDay)
    {
        this.firstDay = firstDay;
    }

    public void setMinuteStep(String minuteStep)
    {
        this.minuteStep = minuteStep;
    }

    private static final String TEMPLATE = "datefield";
    private static final String TEMPLATE_CLOSE = "datefield-close";
    private String dateFormat;
    private String timeFormat;
    private String appendText;
    private String autoSize;
    private String changeMonth;
    private String changeYear;
    private Date defaultDate;
    private String maxDate;
    private String minDate;
    private String yearRange;
    private String type;
    private String checkDate;
    private String startDate;
    private String endDate;
    private String showSecond;
    private String minuteStep;
    private String firstDay;
}
