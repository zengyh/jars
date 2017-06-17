// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateFormatUtils.java

package org.apache.commons.lang.time;

import java.util.*;

// Referenced classes of package org.apache.commons.lang.time:
//            DateUtils, FastDateFormat

public class DateFormatUtils
{

    public DateFormatUtils()
    {
    }

    public static String formatUTC(long millis, String pattern)
    {
        return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, null);
    }

    public static String formatUTC(Date date, String pattern)
    {
        return format(date, pattern, DateUtils.UTC_TIME_ZONE, null);
    }

    public static String formatUTC(long millis, String pattern, Locale locale)
    {
        return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, locale);
    }

    public static String formatUTC(Date date, String pattern, Locale locale)
    {
        return format(date, pattern, DateUtils.UTC_TIME_ZONE, locale);
    }

    public static String format(long millis, String pattern)
    {
        return format(new Date(millis), pattern, null, null);
    }

    public static String format(Date date, String pattern)
    {
        return format(date, pattern, null, null);
    }

    public static String format(long millis, String pattern, TimeZone timeZone)
    {
        return format(new Date(millis), pattern, timeZone, null);
    }

    public static String format(Date date, String pattern, TimeZone timeZone)
    {
        return format(date, pattern, timeZone, null);
    }

    public static String format(long millis, String pattern, Locale locale)
    {
        return format(new Date(millis), pattern, null, locale);
    }

    public static String format(Date date, String pattern, Locale locale)
    {
        return format(date, pattern, null, locale);
    }

    public static String format(long millis, String pattern, TimeZone timeZone, Locale locale)
    {
        return format(new Date(millis), pattern, timeZone, locale);
    }

    public static String format(Date date, String pattern, TimeZone timeZone, Locale locale)
    {
        FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
        return df.format(date);
    }

    public static final FastDateFormat ISO_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
    public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
    public static final FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat ISO_DATE_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-ddZZ");
    public static final FastDateFormat ISO_TIME_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ss");
    public static final FastDateFormat ISO_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ssZZ");
    public static final FastDateFormat ISO_TIME_NO_T_FORMAT = FastDateFormat.getInstance("HH:mm:ss");
    public static final FastDateFormat ISO_TIME_NO_T_TIME_ZONE_FORMAT = FastDateFormat.getInstance("HH:mm:ssZZ");
    public static final FastDateFormat SMTP_DATETIME_FORMAT;

    static 
    {
        SMTP_DATETIME_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
    }
}
