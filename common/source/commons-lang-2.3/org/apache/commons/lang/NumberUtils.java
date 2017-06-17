// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumberUtils.java

package org.apache.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

// Referenced classes of package org.apache.commons.lang:
//            StringUtils

/**
 * @deprecated Class NumberUtils is deprecated
 */

public final class NumberUtils
{

    public NumberUtils()
    {
    }

    public static int stringToInt(String str)
    {
        return stringToInt(str, 0);
    }

    public static int stringToInt(String str, int defaultValue)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return defaultValue;
        }
    }

    public static Number createNumber(String val)
        throws NumberFormatException
    {
        if(val == null)
            return null;
        if(val.length() == 0)
            throw new NumberFormatException("\"\" is not a valid number.");
        if(val.startsWith("--"))
            return null;
        if(val.startsWith("0x") || val.startsWith("-0x"))
            return createInteger(val);
        char lastChar = val.charAt(val.length() - 1);
        int decPos = val.indexOf('.');
        int expPos = val.indexOf('e') + val.indexOf('E') + 1;
        String mant;
        String dec;
        if(decPos > -1)
        {
            if(expPos > -1)
            {
                if(expPos < decPos)
                    throw new NumberFormatException(val + " is not a valid number.");
                dec = val.substring(decPos + 1, expPos);
            } else
            {
                dec = val.substring(decPos + 1);
            }
            mant = val.substring(0, decPos);
        } else
        {
            if(expPos > -1)
                mant = val.substring(0, expPos);
            else
                mant = val;
            dec = null;
        }
        String exp;
        if(!Character.isDigit(lastChar))
        {
            if(expPos > -1 && expPos < val.length() - 1)
                exp = val.substring(expPos + 1, val.length() - 1);
            else
                exp = null;
            String numeric = val.substring(0, val.length() - 1);
            boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
            switch(lastChar)
            {
            case 76: // 'L'
            case 108: // 'l'
                if(dec == null && exp == null && (numeric.charAt(0) == '-' && isDigits(numeric.substring(1)) || isDigits(numeric)))
                    try
                    {
                        return createLong(numeric);
                    }
                    catch(NumberFormatException nfe)
                    {
                        return createBigInteger(numeric);
                    }
                else
                    throw new NumberFormatException(val + " is not a valid number.");

            case 70: // 'F'
            case 102: // 'f'
                try
                {
                    Float f = createFloat(numeric);
                    if(!f.isInfinite() && (f.floatValue() != 0.0F || allZeros))
                        return f;
                }
                catch(NumberFormatException e) { }
                // fall through

            case 68: // 'D'
            case 100: // 'd'
                try
                {
                    Double d = createDouble(numeric);
                    if(!d.isInfinite() && ((double)d.floatValue() != 0.0D || allZeros))
                        return d;
                }
                catch(NumberFormatException nfe) { }
                try
                {
                    return createBigDecimal(numeric);
                }
                catch(NumberFormatException e) { }
                // fall through

            default:
                throw new NumberFormatException(val + " is not a valid number.");
            }
        }
        if(expPos > -1 && expPos < val.length() - 1)
            exp = val.substring(expPos + 1, val.length());
        else
            exp = null;
        if(dec == null && exp == null)
        {
            try
            {
                return createInteger(val);
            }
            catch(NumberFormatException nfe) { }
            try
            {
                return createLong(val);
            }
            catch(NumberFormatException nfe)
            {
                return createBigInteger(val);
            }
        }
        boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
        try
        {
            Float f = createFloat(val);
            if(!f.isInfinite() && (f.floatValue() != 0.0F || allZeros))
                return f;
        }
        catch(NumberFormatException nfe) { }
        try
        {
            Double d = createDouble(val);
            if(!d.isInfinite() && (d.doubleValue() != 0.0D || allZeros))
                return d;
        }
        catch(NumberFormatException nfe) { }
        return createBigDecimal(val);
    }

    private static boolean isAllZeros(String s)
    {
        if(s == null)
            return true;
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) != '0')
                return false;

        return s.length() > 0;
    }

    public static Float createFloat(String val)
    {
        return Float.valueOf(val);
    }

    public static Double createDouble(String val)
    {
        return Double.valueOf(val);
    }

    public static Integer createInteger(String val)
    {
        return Integer.decode(val);
    }

    public static Long createLong(String val)
    {
        return Long.valueOf(val);
    }

    public static BigInteger createBigInteger(String val)
    {
        BigInteger bi = new BigInteger(val);
        return bi;
    }

    public static BigDecimal createBigDecimal(String val)
    {
        BigDecimal bd = new BigDecimal(val);
        return bd;
    }

    public static long minimum(long a, long b, long c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static int minimum(int a, int b, int c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static long maximum(long a, long b, long c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static int maximum(int a, int b, int c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static int compare(double lhs, double rhs)
    {
        if(lhs < rhs)
            return -1;
        if(lhs > rhs)
            return 1;
        long lhsBits = Double.doubleToLongBits(lhs);
        long rhsBits = Double.doubleToLongBits(rhs);
        if(lhsBits == rhsBits)
            return 0;
        return lhsBits >= rhsBits ? 1 : -1;
    }

    public static int compare(float lhs, float rhs)
    {
        if(lhs < rhs)
            return -1;
        if(lhs > rhs)
            return 1;
        int lhsBits = Float.floatToIntBits(lhs);
        int rhsBits = Float.floatToIntBits(rhs);
        if(lhsBits == rhsBits)
            return 0;
        return lhsBits >= rhsBits ? 1 : -1;
    }

    public static boolean isDigits(String str)
    {
        if(str == null || str.length() == 0)
            return false;
        for(int i = 0; i < str.length(); i++)
            if(!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNumber(String str)
    {
        if(StringUtils.isEmpty(str))
            return false;
        char chars[] = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        int start = chars[0] != '-' ? 0 : 1;
        int i;
        if(sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x')
        {
            i = start + 2;
            if(i == sz)
                return false;
            for(; i < chars.length; i++)
                if((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F'))
                    return false;

            return true;
        }
        sz--;
        for(i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; i++)
            if(chars[i] >= '0' && chars[i] <= '9')
            {
                foundDigit = true;
                allowSigns = false;
            } else
            if(chars[i] == '.')
            {
                if(hasDecPoint || hasExp)
                    return false;
                hasDecPoint = true;
            } else
            if(chars[i] == 'e' || chars[i] == 'E')
            {
                if(hasExp)
                    return false;
                if(!foundDigit)
                    return false;
                hasExp = true;
                allowSigns = true;
            } else
            if(chars[i] == '+' || chars[i] == '-')
            {
                if(!allowSigns)
                    return false;
                allowSigns = false;
                foundDigit = false;
            } else
            {
                return false;
            }

        if(i < chars.length)
        {
            if(chars[i] >= '0' && chars[i] <= '9')
                return true;
            if(chars[i] == 'e' || chars[i] == 'E')
                return false;
            if(!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F'))
                return foundDigit;
            if(chars[i] == 'l' || chars[i] == 'L')
                return foundDigit && !hasExp;
            else
                return false;
        } else
        {
            return !allowSigns && foundDigit;
        }
    }
}
