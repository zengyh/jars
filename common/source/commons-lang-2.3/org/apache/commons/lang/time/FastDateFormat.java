// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastDateFormat.java

package org.apache.commons.lang.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.*;
import java.util.*;
import org.apache.commons.lang.Validate;

public class FastDateFormat extends Format
{
    private static class Pair
    {

        public boolean equals(Object obj)
        {
            if(this == obj)
                return true;
            if(!(obj instanceof Pair))
            {
                return false;
            } else
            {
                Pair key = (Pair)obj;
                return (mObj1 != null ? mObj1.equals(key.mObj1) : key.mObj1 == null) && (mObj2 != null ? mObj2.equals(key.mObj2) : key.mObj2 == null);
            }
        }

        public int hashCode()
        {
            return (mObj1 != null ? mObj1.hashCode() : 0) + (mObj2 != null ? mObj2.hashCode() : 0);
        }

        public String toString()
        {
            return "[" + mObj1 + ':' + mObj2 + ']';
        }

        private final Object mObj1;
        private final Object mObj2;

        public Pair(Object obj1, Object obj2)
        {
            mObj1 = obj1;
            mObj2 = obj2;
        }
    }

    private static class TimeZoneDisplayKey
    {

        public int hashCode()
        {
            return mStyle * 31 + mLocale.hashCode();
        }

        public boolean equals(Object obj)
        {
            if(this == obj)
                return true;
            if(obj instanceof TimeZoneDisplayKey)
            {
                TimeZoneDisplayKey other = (TimeZoneDisplayKey)obj;
                return mTimeZone.equals(other.mTimeZone) && mStyle == other.mStyle && mLocale.equals(other.mLocale);
            } else
            {
                return false;
            }
        }

        private final TimeZone mTimeZone;
        private final int mStyle;
        private final Locale mLocale;

        TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale)
        {
            mTimeZone = timeZone;
            if(daylight)
                style |= 0x80000000;
            mStyle = style;
            mLocale = locale;
        }
    }

    private static class TimeZoneNumberRule
        implements Rule
    {

        public int estimateLength()
        {
            return 5;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            int offset = calendar.get(15) + calendar.get(16);
            if(offset < 0)
            {
                buffer.append('-');
                offset = -offset;
            } else
            {
                buffer.append('+');
            }
            int hours = offset / 0x36ee80;
            buffer.append((char)(hours / 10 + 48));
            buffer.append((char)(hours % 10 + 48));
            if(mColon)
                buffer.append(':');
            int minutes = offset / 60000 - 60 * hours;
            buffer.append((char)(minutes / 10 + 48));
            buffer.append((char)(minutes % 10 + 48));
        }

        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;


        TimeZoneNumberRule(boolean colon)
        {
            mColon = colon;
        }
    }

    private static class TimeZoneNameRule
        implements Rule
    {

        public int estimateLength()
        {
            if(mTimeZoneForced)
                return Math.max(mStandard.length(), mDaylight.length());
            return mStyle != 0 ? 40 : 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            if(mTimeZoneForced)
            {
                if(mTimeZone.useDaylightTime() && calendar.get(16) != 0)
                    buffer.append(mDaylight);
                else
                    buffer.append(mStandard);
            } else
            {
                TimeZone timeZone = calendar.getTimeZone();
                if(timeZone.useDaylightTime() && calendar.get(16) != 0)
                    buffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, true, mStyle, mLocale));
                else
                    buffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, false, mStyle, mLocale));
            }
        }

        private final TimeZone mTimeZone;
        private final boolean mTimeZoneForced;
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;

        TimeZoneNameRule(TimeZone timeZone, boolean timeZoneForced, Locale locale, int style)
        {
            mTimeZone = timeZone;
            mTimeZoneForced = timeZoneForced;
            mLocale = locale;
            mStyle = style;
            if(timeZoneForced)
            {
                mStandard = FastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
                mDaylight = FastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
            } else
            {
                mStandard = null;
                mDaylight = null;
            }
        }
    }

    private static class TwentyFourHourField
        implements NumberRule
    {

        public int estimateLength()
        {
            return mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            int value = calendar.get(11);
            if(value == 0)
                value = calendar.getMaximum(11) + 1;
            mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value)
        {
            mRule.appendTo(buffer, value);
        }

        private final NumberRule mRule;

        TwentyFourHourField(NumberRule rule)
        {
            mRule = rule;
        }
    }

    private static class TwelveHourField
        implements NumberRule
    {

        public int estimateLength()
        {
            return mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            int value = calendar.get(10);
            if(value == 0)
                value = calendar.getLeastMaximum(10) + 1;
            mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value)
        {
            mRule.appendTo(buffer, value);
        }

        private final NumberRule mRule;

        TwelveHourField(NumberRule rule)
        {
            mRule = rule;
        }
    }

    private static class TwoDigitMonthField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            buffer.append((char)(value / 10 + 48));
            buffer.append((char)(value % 10 + 48));
        }

        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();


        TwoDigitMonthField()
        {
        }
    }

    private static class TwoDigitYearField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(1) % 100);
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            buffer.append((char)(value / 10 + 48));
            buffer.append((char)(value % 10 + 48));
        }

        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();


        TwoDigitYearField()
        {
        }
    }

    private static class TwoDigitNumberField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            if(value < 100)
            {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            } else
            {
                buffer.append(Integer.toString(value));
            }
        }

        private final int mField;

        TwoDigitNumberField(int field)
        {
            mField = field;
        }
    }

    private static class PaddedNumberField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            if(value < 100)
            {
                for(int i = mSize; --i >= 2;)
                    buffer.append('0');

                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            } else
            {
                int digits;
                if(value < 1000)
                {
                    digits = 3;
                } else
                {
                    Validate.isTrue(value > -1, "Negative values should not be possible", value);
                    digits = Integer.toString(value).length();
                }
                for(int i = mSize; --i >= digits;)
                    buffer.append('0');

                buffer.append(Integer.toString(value));
            }
        }

        private final int mField;
        private final int mSize;

        PaddedNumberField(int field, int size)
        {
            if(size < 3)
            {
                throw new IllegalArgumentException();
            } else
            {
                mField = field;
                mSize = size;
                return;
            }
        }
    }

    private static class UnpaddedMonthField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            if(value < 10)
            {
                buffer.append((char)(value + 48));
            } else
            {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            }
        }

        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();


        UnpaddedMonthField()
        {
        }
    }

    private static class UnpaddedNumberField
        implements NumberRule
    {

        public int estimateLength()
        {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(StringBuffer buffer, int value)
        {
            if(value < 10)
                buffer.append((char)(value + 48));
            else
            if(value < 100)
            {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            } else
            {
                buffer.append(Integer.toString(value));
            }
        }

        static final UnpaddedNumberField INSTANCE_YEAR = new UnpaddedNumberField(1);
        private final int mField;


        UnpaddedNumberField(int field)
        {
            mField = field;
        }
    }

    private static class TextField
        implements Rule
    {

        public int estimateLength()
        {
            int max = 0;
            for(int i = mValues.length; --i >= 0;)
            {
                int len = mValues[i].length();
                if(len > max)
                    max = len;
            }

            return max;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            buffer.append(mValues[calendar.get(mField)]);
        }

        private final int mField;
        private final String mValues[];

        TextField(int field, String values[])
        {
            mField = field;
            mValues = values;
        }
    }

    private static class StringLiteral
        implements Rule
    {

        public int estimateLength()
        {
            return mValue.length();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            buffer.append(mValue);
        }

        private final String mValue;

        StringLiteral(String value)
        {
            mValue = value;
        }
    }

    private static class CharacterLiteral
        implements Rule
    {

        public int estimateLength()
        {
            return 1;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar)
        {
            buffer.append(mValue);
        }

        private final char mValue;

        CharacterLiteral(char value)
        {
            mValue = value;
        }
    }

    private static interface NumberRule
        extends Rule
    {

        public abstract void appendTo(StringBuffer stringbuffer, int i);
    }

    private static interface Rule
    {

        public abstract int estimateLength();

        public abstract void appendTo(StringBuffer stringbuffer, Calendar calendar);
    }


    public static FastDateFormat getInstance()
    {
        return getInstance(getDefaultPattern(), null, null);
    }

    public static FastDateFormat getInstance(String pattern)
    {
        return getInstance(pattern, null, null);
    }

    public static FastDateFormat getInstance(String pattern, TimeZone timeZone)
    {
        return getInstance(pattern, timeZone, null);
    }

    public static FastDateFormat getInstance(String pattern, Locale locale)
    {
        return getInstance(pattern, null, locale);
    }

    public static synchronized FastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale)
    {
        FastDateFormat emptyFormat = new FastDateFormat(pattern, timeZone, locale);
        FastDateFormat format = (FastDateFormat)cInstanceCache.get(emptyFormat);
        if(format == null)
        {
            format = emptyFormat;
            format.init();
            cInstanceCache.put(format, format);
        }
        return format;
    }

    public static FastDateFormat getDateInstance(int style)
    {
        return getDateInstance(style, null, null);
    }

    public static FastDateFormat getDateInstance(int style, Locale locale)
    {
        return getDateInstance(style, null, locale);
    }

    public static FastDateFormat getDateInstance(int style, TimeZone timeZone)
    {
        return getDateInstance(style, timeZone, null);
    }

    public static synchronized FastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale)
    {
        Object key = new Integer(style);
        if(timeZone != null)
            key = new Pair(key, timeZone);
        if(locale != null)
            key = new Pair(key, locale);
        FastDateFormat format = (FastDateFormat)cDateInstanceCache.get(key);
        if(format == null)
        {
            if(locale == null)
                locale = Locale.getDefault();
            try
            {
                SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateInstance(style, locale);
                String pattern = formatter.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cDateInstanceCache.put(key, format);
            }
            catch(ClassCastException ex)
            {
                throw new IllegalArgumentException("No date pattern for locale: " + locale);
            }
        }
        return format;
    }

    public static FastDateFormat getTimeInstance(int style)
    {
        return getTimeInstance(style, null, null);
    }

    public static FastDateFormat getTimeInstance(int style, Locale locale)
    {
        return getTimeInstance(style, null, locale);
    }

    public static FastDateFormat getTimeInstance(int style, TimeZone timeZone)
    {
        return getTimeInstance(style, timeZone, null);
    }

    public static synchronized FastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale)
    {
        Object key = new Integer(style);
        if(timeZone != null)
            key = new Pair(key, timeZone);
        if(locale != null)
            key = new Pair(key, locale);
        FastDateFormat format = (FastDateFormat)cTimeInstanceCache.get(key);
        if(format == null)
        {
            if(locale == null)
                locale = Locale.getDefault();
            try
            {
                SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getTimeInstance(style, locale);
                String pattern = formatter.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cTimeInstanceCache.put(key, format);
            }
            catch(ClassCastException ex)
            {
                throw new IllegalArgumentException("No date pattern for locale: " + locale);
            }
        }
        return format;
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle)
    {
        return getDateTimeInstance(dateStyle, timeStyle, null, null);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale)
    {
        return getDateTimeInstance(dateStyle, timeStyle, null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone)
    {
        return getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
    }

    public static synchronized FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale)
    {
        Object key = new Pair(new Integer(dateStyle), new Integer(timeStyle));
        if(timeZone != null)
            key = new Pair(key, timeZone);
        if(locale != null)
            key = new Pair(key, locale);
        FastDateFormat format = (FastDateFormat)cDateTimeInstanceCache.get(key);
        if(format == null)
        {
            if(locale == null)
                locale = Locale.getDefault();
            try
            {
                SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
                String pattern = formatter.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cDateTimeInstanceCache.put(key, format);
            }
            catch(ClassCastException ex)
            {
                throw new IllegalArgumentException("No date time pattern for locale: " + locale);
            }
        }
        return format;
    }

    static synchronized String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale)
    {
        Object key = new TimeZoneDisplayKey(tz, daylight, style, locale);
        String value = (String)cTimeZoneDisplayCache.get(key);
        if(value == null)
        {
            value = tz.getDisplayName(daylight, style, locale);
            cTimeZoneDisplayCache.put(key, value);
        }
        return value;
    }

    private static synchronized String getDefaultPattern()
    {
        if(cDefaultPattern == null)
            cDefaultPattern = (new SimpleDateFormat()).toPattern();
        return cDefaultPattern;
    }

    protected FastDateFormat(String pattern, TimeZone timeZone, Locale locale)
    {
        if(pattern == null)
            throw new IllegalArgumentException("The pattern must not be null");
        mPattern = pattern;
        mTimeZoneForced = timeZone != null;
        if(timeZone == null)
            timeZone = TimeZone.getDefault();
        mTimeZone = timeZone;
        mLocaleForced = locale != null;
        if(locale == null)
            locale = Locale.getDefault();
        mLocale = locale;
    }

    protected void init()
    {
        List rulesList = parsePattern();
        mRules = (Rule[])rulesList.toArray(new Rule[rulesList.size()]);
        int len = 0;
        for(int i = mRules.length; --i >= 0;)
            len += mRules[i].estimateLength();

        mMaxLengthEstimate = len;
    }

    protected List parsePattern()
    {
        DateFormatSymbols symbols = new DateFormatSymbols(mLocale);
        List rules = new ArrayList();
        String ERAs[] = symbols.getEras();
        String months[] = symbols.getMonths();
        String shortMonths[] = symbols.getShortMonths();
        String weekdays[] = symbols.getWeekdays();
        String shortWeekdays[] = symbols.getShortWeekdays();
        String AmPmStrings[] = symbols.getAmPmStrings();
        int length = mPattern.length();
        int indexRef[] = new int[1];
        for(int i = 0; i < length; i++)
        {
            indexRef[0] = i;
            String token = parseToken(mPattern, indexRef);
            i = indexRef[0];
            int tokenLen = token.length();
            if(tokenLen == 0)
                break;
            char c = token.charAt(0);
            Rule rule;
            switch(c)
            {
            case 71: // 'G'
                rule = new TextField(0, ERAs);
                break;

            case 121: // 'y'
                if(tokenLen >= 4)
                    rule = selectNumberRule(1, tokenLen);
                else
                    rule = TwoDigitYearField.INSTANCE;
                break;

            case 77: // 'M'
                if(tokenLen >= 4)
                {
                    rule = new TextField(2, months);
                    break;
                }
                if(tokenLen == 3)
                {
                    rule = new TextField(2, shortMonths);
                    break;
                }
                if(tokenLen == 2)
                    rule = TwoDigitMonthField.INSTANCE;
                else
                    rule = UnpaddedMonthField.INSTANCE;
                break;

            case 100: // 'd'
                rule = selectNumberRule(5, tokenLen);
                break;

            case 104: // 'h'
                rule = new TwelveHourField(selectNumberRule(10, tokenLen));
                break;

            case 72: // 'H'
                rule = selectNumberRule(11, tokenLen);
                break;

            case 109: // 'm'
                rule = selectNumberRule(12, tokenLen);
                break;

            case 115: // 's'
                rule = selectNumberRule(13, tokenLen);
                break;

            case 83: // 'S'
                rule = selectNumberRule(14, tokenLen);
                break;

            case 69: // 'E'
                rule = new TextField(7, tokenLen >= 4 ? weekdays : shortWeekdays);
                break;

            case 68: // 'D'
                rule = selectNumberRule(6, tokenLen);
                break;

            case 70: // 'F'
                rule = selectNumberRule(8, tokenLen);
                break;

            case 119: // 'w'
                rule = selectNumberRule(3, tokenLen);
                break;

            case 87: // 'W'
                rule = selectNumberRule(4, tokenLen);
                break;

            case 97: // 'a'
                rule = new TextField(9, AmPmStrings);
                break;

            case 107: // 'k'
                rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
                break;

            case 75: // 'K'
                rule = selectNumberRule(10, tokenLen);
                break;

            case 122: // 'z'
                if(tokenLen >= 4)
                    rule = new TimeZoneNameRule(mTimeZone, mTimeZoneForced, mLocale, 1);
                else
                    rule = new TimeZoneNameRule(mTimeZone, mTimeZoneForced, mLocale, 0);
                break;

            case 90: // 'Z'
                if(tokenLen == 1)
                    rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                else
                    rule = TimeZoneNumberRule.INSTANCE_COLON;
                break;

            case 39: // '\''
                String sub = token.substring(1);
                if(sub.length() == 1)
                    rule = new CharacterLiteral(sub.charAt(0));
                else
                    rule = new StringLiteral(sub);
                break;

            case 40: // '('
            case 41: // ')'
            case 42: // '*'
            case 43: // '+'
            case 44: // ','
            case 45: // '-'
            case 46: // '.'
            case 47: // '/'
            case 48: // '0'
            case 49: // '1'
            case 50: // '2'
            case 51: // '3'
            case 52: // '4'
            case 53: // '5'
            case 54: // '6'
            case 55: // '7'
            case 56: // '8'
            case 57: // '9'
            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 65: // 'A'
            case 66: // 'B'
            case 67: // 'C'
            case 73: // 'I'
            case 74: // 'J'
            case 76: // 'L'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 88: // 'X'
            case 89: // 'Y'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            case 98: // 'b'
            case 99: // 'c'
            case 101: // 'e'
            case 102: // 'f'
            case 103: // 'g'
            case 105: // 'i'
            case 106: // 'j'
            case 108: // 'l'
            case 110: // 'n'
            case 111: // 'o'
            case 112: // 'p'
            case 113: // 'q'
            case 114: // 'r'
            case 116: // 't'
            case 117: // 'u'
            case 118: // 'v'
            case 120: // 'x'
            default:
                throw new IllegalArgumentException("Illegal pattern component: " + token);
            }
            rules.add(rule);
        }

        return rules;
    }

    protected String parseToken(String pattern, int indexRef[])
    {
        StringBuffer buf = new StringBuffer();
        int i = indexRef[0];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')
        {
            buf.append(c);
            for(; i + 1 < length; i++)
            {
                char peek = pattern.charAt(i + 1);
                if(peek != c)
                    break;
                buf.append(c);
            }

        } else
        {
            buf.append('\'');
            boolean inLiteral = false;
            for(; i < length; i++)
            {
                c = pattern.charAt(i);
                if(c == '\'')
                {
                    if(i + 1 < length && pattern.charAt(i + 1) == '\'')
                    {
                        i++;
                        buf.append(c);
                    } else
                    {
                        inLiteral = !inLiteral;
                    }
                    continue;
                }
                if(!inLiteral && (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'))
                {
                    i--;
                    break;
                }
                buf.append(c);
            }

        }
        indexRef[0] = i;
        return buf.toString();
    }

    protected NumberRule selectNumberRule(int field, int padding)
    {
        switch(padding)
        {
        case 1: // '\001'
            return new UnpaddedNumberField(field);

        case 2: // '\002'
            return new TwoDigitNumberField(field);
        }
        return new PaddedNumberField(field, padding);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
    {
        if(obj instanceof Date)
            return format((Date)obj, toAppendTo);
        if(obj instanceof Calendar)
            return format((Calendar)obj, toAppendTo);
        if(obj instanceof Long)
            return format(((Long)obj).longValue(), toAppendTo);
        else
            throw new IllegalArgumentException("Unknown class: " + (obj != null ? obj.getClass().getName() : "<null>"));
    }

    public String format(long millis)
    {
        return format(new Date(millis));
    }

    public String format(Date date)
    {
        Calendar c = new GregorianCalendar(mTimeZone);
        c.setTime(date);
        return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
    }

    public String format(Calendar calendar)
    {
        return format(calendar, new StringBuffer(mMaxLengthEstimate)).toString();
    }

    public StringBuffer format(long millis, StringBuffer buf)
    {
        return format(new Date(millis), buf);
    }

    public StringBuffer format(Date date, StringBuffer buf)
    {
        Calendar c = new GregorianCalendar(mTimeZone);
        c.setTime(date);
        return applyRules(c, buf);
    }

    public StringBuffer format(Calendar calendar, StringBuffer buf)
    {
        if(mTimeZoneForced)
        {
            calendar = (Calendar)calendar.clone();
            calendar.setTimeZone(mTimeZone);
        }
        return applyRules(calendar, buf);
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer buf)
    {
        Rule rules[] = mRules;
        int len = mRules.length;
        for(int i = 0; i < len; i++)
            rules[i].appendTo(buf, calendar);

        return buf;
    }

    public Object parseObject(String source, ParsePosition pos)
    {
        pos.setIndex(0);
        pos.setErrorIndex(0);
        return null;
    }

    public String getPattern()
    {
        return mPattern;
    }

    public TimeZone getTimeZone()
    {
        return mTimeZone;
    }

    public boolean getTimeZoneOverridesCalendar()
    {
        return mTimeZoneForced;
    }

    public Locale getLocale()
    {
        return mLocale;
    }

    public int getMaxLengthEstimate()
    {
        return mMaxLengthEstimate;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof FastDateFormat))
            return false;
        FastDateFormat other = (FastDateFormat)obj;
        return (mPattern == other.mPattern || mPattern.equals(other.mPattern)) && (mTimeZone == other.mTimeZone || mTimeZone.equals(other.mTimeZone)) && (mLocale == other.mLocale || mLocale.equals(other.mLocale)) && mTimeZoneForced == other.mTimeZoneForced && mLocaleForced == other.mLocaleForced;
    }

    public int hashCode()
    {
        int total = 0;
        total += mPattern.hashCode();
        total += mTimeZone.hashCode();
        total += mTimeZoneForced ? 1 : 0;
        total += mLocale.hashCode();
        total += mLocaleForced ? 1 : 0;
        return total;
    }

    public String toString()
    {
        return "FastDateFormat[" + mPattern + "]";
    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        init();
    }

    private static final long serialVersionUID = 1L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static String cDefaultPattern;
    private static Map cInstanceCache = new HashMap(7);
    private static Map cDateInstanceCache = new HashMap(7);
    private static Map cTimeInstanceCache = new HashMap(7);
    private static Map cDateTimeInstanceCache = new HashMap(7);
    private static Map cTimeZoneDisplayCache = new HashMap(7);
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final boolean mTimeZoneForced;
    private final Locale mLocale;
    private final boolean mLocaleForced;
    private transient Rule mRules[];
    private transient int mMaxLengthEstimate;

}
