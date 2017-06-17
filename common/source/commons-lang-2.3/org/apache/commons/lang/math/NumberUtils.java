// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumberUtils.java

package org.apache.commons.lang.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;

public class NumberUtils
{

    public NumberUtils()
    {
    }

    /**
     * @deprecated Method stringToInt is deprecated
     */

    public static int stringToInt(String str)
    {
        return toInt(str);
    }

    public static int toInt(String str)
    {
        return toInt(str, 0);
    }

    /**
     * @deprecated Method stringToInt is deprecated
     */

    public static int stringToInt(String str, int defaultValue)
    {
        return toInt(str, defaultValue);
    }

    public static int toInt(String str, int defaultValue)
    {
        if(str == null)
            return defaultValue;
        try
        {
            return Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return defaultValue;
        }
    }

    public static long toLong(String str)
    {
        return toLong(str, 0L);
    }

    public static long toLong(String str, long defaultValue)
    {
        if(str == null)
            return defaultValue;
        try
        {
            return Long.parseLong(str);
        }
        catch(NumberFormatException nfe)
        {
            return defaultValue;
        }
    }

    public static float toFloat(String str)
    {
        return toFloat(str, 0.0F);
    }

    public static float toFloat(String str, float defaultValue)
    {
        if(str == null)
            return defaultValue;
        try
        {
            return Float.parseFloat(str);
        }
        catch(NumberFormatException nfe)
        {
            return defaultValue;
        }
    }

    public static double toDouble(String str)
    {
        return toDouble(str, 0.0D);
    }

    public static double toDouble(String str, double defaultValue)
    {
        if(str == null)
            return defaultValue;
        try
        {
            return Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return defaultValue;
        }
    }

    public static Number createNumber(String str)
        throws NumberFormatException
    {
        if(str == null)
            return null;
        if(StringUtils.isBlank(str))
            throw new NumberFormatException("A blank string is not a valid number");
        if(str.startsWith("--"))
            return null;
        if(str.startsWith("0x") || str.startsWith("-0x"))
            return createInteger(str);
        char lastChar = str.charAt(str.length() - 1);
        int decPos = str.indexOf('.');
        int expPos = str.indexOf('e') + str.indexOf('E') + 1;
        String mant;
        String dec;
        if(decPos > -1)
        {
            if(expPos > -1)
            {
                if(expPos < decPos)
                    throw new NumberFormatException(str + " is not a valid number.");
                dec = str.substring(decPos + 1, expPos);
            } else
            {
                dec = str.substring(decPos + 1);
            }
            mant = str.substring(0, decPos);
        } else
        {
            if(expPos > -1)
                mant = str.substring(0, expPos);
            else
                mant = str;
            dec = null;
        }
        String exp;
        if(!Character.isDigit(lastChar))
        {
            if(expPos > -1 && expPos < str.length() - 1)
                exp = str.substring(expPos + 1, str.length() - 1);
            else
                exp = null;
            String numeric = str.substring(0, str.length() - 1);
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
                    throw new NumberFormatException(str + " is not a valid number.");

            case 70: // 'F'
            case 102: // 'f'
                try
                {
                    Float f = createFloat(numeric);
                    if(!f.isInfinite() && (f.floatValue() != 0.0F || allZeros))
                        return f;
                }
                catch(NumberFormatException nfe) { }
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
                throw new NumberFormatException(str + " is not a valid number.");
            }
        }
        if(expPos > -1 && expPos < str.length() - 1)
            exp = str.substring(expPos + 1, str.length());
        else
            exp = null;
        if(dec == null && exp == null)
        {
            try
            {
                return createInteger(str);
            }
            catch(NumberFormatException nfe) { }
            try
            {
                return createLong(str);
            }
            catch(NumberFormatException nfe)
            {
                return createBigInteger(str);
            }
        }
        boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
        try
        {
            Float f = createFloat(str);
            if(!f.isInfinite() && (f.floatValue() != 0.0F || allZeros))
                return f;
        }
        catch(NumberFormatException nfe) { }
        try
        {
            Double d = createDouble(str);
            if(!d.isInfinite() && (d.doubleValue() != 0.0D || allZeros))
                return d;
        }
        catch(NumberFormatException nfe) { }
        return createBigDecimal(str);
    }

    private static boolean isAllZeros(String str)
    {
        if(str == null)
            return true;
        for(int i = str.length() - 1; i >= 0; i--)
            if(str.charAt(i) != '0')
                return false;

        return str.length() > 0;
    }

    public static Float createFloat(String str)
    {
        if(str == null)
            return null;
        else
            return Float.valueOf(str);
    }

    public static Double createDouble(String str)
    {
        if(str == null)
            return null;
        else
            return Double.valueOf(str);
    }

    public static Integer createInteger(String str)
    {
        if(str == null)
            return null;
        else
            return Integer.decode(str);
    }

    public static Long createLong(String str)
    {
        if(str == null)
            return null;
        else
            return Long.valueOf(str);
    }

    public static BigInteger createBigInteger(String str)
    {
        if(str == null)
            return null;
        else
            return new BigInteger(str);
    }

    public static BigDecimal createBigDecimal(String str)
    {
        if(str == null)
            return null;
        if(StringUtils.isBlank(str))
            throw new NumberFormatException("A blank string is not a valid number");
        else
            return new BigDecimal(str);
    }

    public static long min(long array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        long min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    public static int min(int array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        int min = array[0];
        for(int j = 1; j < array.length; j++)
            if(array[j] < min)
                min = array[j];

        return min;
    }

    public static short min(short array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        short min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    public static byte min(byte array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        byte min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    public static double min(double array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        double min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    public static float min(float array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        float min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    public static long max(long array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        long max = array[0];
        for(int j = 1; j < array.length; j++)
            if(array[j] > max)
                max = array[j];

        return max;
    }

    public static int max(int array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        int max = array[0];
        for(int j = 1; j < array.length; j++)
            if(array[j] > max)
                max = array[j];

        return max;
    }

    public static short max(short array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        short max = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] > max)
                max = array[i];

        return max;
    }

    public static byte max(byte array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        byte max = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] > max)
                max = array[i];

        return max;
    }

    public static double max(double array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        double max = array[0];
        for(int j = 1; j < array.length; j++)
            if(array[j] > max)
                max = array[j];

        return max;
    }

    public static float max(float array[])
    {
        if(array == null)
            throw new IllegalArgumentException("The Array must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("Array cannot be empty.");
        float max = array[0];
        for(int j = 1; j < array.length; j++)
            if(array[j] > max)
                max = array[j];

        return max;
    }

    public static long min(long a, long b, long c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static int min(int a, int b, int c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static short min(short a, short b, short c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static byte min(byte a, byte b, byte c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }

    public static double min(double a, double b, double c)
    {
        return Math.min(Math.min(a, b), c);
    }

    public static float min(float a, float b, float c)
    {
        return Math.min(Math.min(a, b), c);
    }

    public static long max(long a, long b, long c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static int max(int a, int b, int c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static short max(short a, short b, short c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static byte max(byte a, byte b, byte c)
    {
        if(b > a)
            a = b;
        if(c > a)
            a = c;
        return a;
    }

    public static double max(double a, double b, double c)
    {
        return Math.max(Math.max(a, b), c);
    }

    public static float max(float a, float b, float c)
    {
        return Math.max(Math.max(a, b), c);
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
        if(StringUtils.isEmpty(str))
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

    public static final Long LONG_ZERO = new Long(0L);
    public static final Long LONG_ONE = new Long(1L);
    public static final Long LONG_MINUS_ONE = new Long(-1L);
    public static final Integer INTEGER_ZERO = new Integer(0);
    public static final Integer INTEGER_ONE = new Integer(1);
    public static final Integer INTEGER_MINUS_ONE = new Integer(-1);
    public static final Short SHORT_ZERO = new Short((short)0);
    public static final Short SHORT_ONE = new Short((short)1);
    public static final Short SHORT_MINUS_ONE = new Short((short)-1);
    public static final Byte BYTE_ZERO = new Byte((byte)0);
    public static final Byte BYTE_ONE = new Byte((byte)1);
    public static final Byte BYTE_MINUS_ONE = new Byte((byte)-1);
    public static final Double DOUBLE_ZERO = new Double(0.0D);
    public static final Double DOUBLE_ONE = new Double(1.0D);
    public static final Double DOUBLE_MINUS_ONE = new Double(-1D);
    public static final Float FLOAT_ZERO = new Float(0.0F);
    public static final Float FLOAT_ONE = new Float(1.0F);
    public static final Float FLOAT_MINUS_ONE = new Float(-1F);

}
