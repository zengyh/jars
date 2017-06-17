// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Fraction.java

package org.apache.commons.lang.math;

import java.math.BigInteger;

public final class Fraction extends Number
    implements Comparable
{

    private Fraction(int numerator, int denominator)
    {
        hashCode = 0;
        toString = null;
        toProperString = null;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction getFraction(int numerator, int denominator)
    {
        if(denominator == 0)
            throw new ArithmeticException("The denominator must not be zero");
        if(denominator < 0)
        {
            if(numerator == 0x80000000 || denominator == 0x80000000)
                throw new ArithmeticException("overflow: can't negate");
            numerator = -numerator;
            denominator = -denominator;
        }
        return new Fraction(numerator, denominator);
    }

    public static Fraction getFraction(int whole, int numerator, int denominator)
    {
        if(denominator == 0)
            throw new ArithmeticException("The denominator must not be zero");
        if(denominator < 0)
            throw new ArithmeticException("The denominator must not be negative");
        if(numerator < 0)
            throw new ArithmeticException("The numerator must not be negative");
        long numeratorValue;
        if(whole < 0)
            numeratorValue = (long)whole * (long)denominator - (long)numerator;
        else
            numeratorValue = (long)whole * (long)denominator + (long)numerator;
        if(numeratorValue < 0xffffffff80000000L || numeratorValue > 0x7fffffffL)
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        else
            return new Fraction((int)numeratorValue, denominator);
    }

    public static Fraction getReducedFraction(int numerator, int denominator)
    {
        if(denominator == 0)
            throw new ArithmeticException("The denominator must not be zero");
        if(numerator == 0)
            return ZERO;
        if(denominator == 0x80000000 && (numerator & 1) == 0)
        {
            numerator /= 2;
            denominator /= 2;
        }
        if(denominator < 0)
        {
            if(numerator == 0x80000000 || denominator == 0x80000000)
                throw new ArithmeticException("overflow: can't negate");
            numerator = -numerator;
            denominator = -denominator;
        }
        int gcd = greatestCommonDivisor(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return new Fraction(numerator, denominator);
    }

    public static Fraction getFraction(double value)
    {
        int sign = value >= 0.0D ? 1 : -1;
        value = Math.abs(value);
        if(value > 2147483647D || Double.isNaN(value))
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        int wholeNumber = (int)value;
        value -= wholeNumber;
        int numer0 = 0;
        int denom0 = 1;
        int numer1 = 1;
        int denom1 = 0;
        int numer2 = 0;
        int denom2 = 0;
        int a1 = (int)value;
        int a2 = 0;
        double x1 = 1.0D;
        double x2 = 0.0D;
        double y1 = value - (double)a1;
        double y2 = 0.0D;
        double delta2 = 1.7976931348623157E+308D;
        int i = 1;
        double delta1;
        do
        {
            delta1 = delta2;
            a2 = (int)(x1 / y1);
            x2 = y1;
            y2 = x1 - (double)a2 * y1;
            numer2 = a1 * numer1 + numer0;
            denom2 = a1 * denom1 + denom0;
            double fraction = (double)numer2 / (double)denom2;
            delta2 = Math.abs(value - fraction);
            a1 = a2;
            x1 = x2;
            y1 = y2;
            numer0 = numer1;
            denom0 = denom1;
            numer1 = numer2;
            denom1 = denom2;
            i++;
        } while(delta1 > delta2 && denom2 <= 10000 && denom2 > 0 && i < 25);
        if(i == 25)
            throw new ArithmeticException("Unable to convert double to fraction");
        else
            return getReducedFraction((numer0 + wholeNumber * denom0) * sign, denom0);
    }

    public static Fraction getFraction(String str)
    {
        if(str == null)
            throw new IllegalArgumentException("The string must not be null");
        int pos = str.indexOf('.');
        if(pos >= 0)
            return getFraction(Double.parseDouble(str));
        pos = str.indexOf(' ');
        if(pos > 0)
        {
            int whole = Integer.parseInt(str.substring(0, pos));
            str = str.substring(pos + 1);
            pos = str.indexOf('/');
            if(pos < 0)
            {
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            } else
            {
                int numer = Integer.parseInt(str.substring(0, pos));
                int denom = Integer.parseInt(str.substring(pos + 1));
                return getFraction(whole, numer, denom);
            }
        }
        pos = str.indexOf('/');
        if(pos < 0)
        {
            return getFraction(Integer.parseInt(str), 1);
        } else
        {
            int numer = Integer.parseInt(str.substring(0, pos));
            int denom = Integer.parseInt(str.substring(pos + 1));
            return getFraction(numer, denom);
        }
    }

    public int getNumerator()
    {
        return numerator;
    }

    public int getDenominator()
    {
        return denominator;
    }

    public int getProperNumerator()
    {
        return Math.abs(numerator % denominator);
    }

    public int getProperWhole()
    {
        return numerator / denominator;
    }

    public int intValue()
    {
        return numerator / denominator;
    }

    public long longValue()
    {
        return (long)numerator / (long)denominator;
    }

    public float floatValue()
    {
        return (float)numerator / (float)denominator;
    }

    public double doubleValue()
    {
        return (double)numerator / (double)denominator;
    }

    public Fraction reduce()
    {
        int gcd = greatestCommonDivisor(Math.abs(numerator), denominator);
        if(gcd == 1)
            return this;
        else
            return getFraction(numerator / gcd, denominator / gcd);
    }

    public Fraction invert()
    {
        if(numerator == 0)
            throw new ArithmeticException("Unable to invert zero.");
        if(numerator == 0x80000000)
            throw new ArithmeticException("overflow: can't negate numerator");
        if(numerator < 0)
            return new Fraction(-denominator, -numerator);
        else
            return new Fraction(denominator, numerator);
    }

    public Fraction negate()
    {
        if(numerator == 0x80000000)
            throw new ArithmeticException("overflow: too large to negate");
        else
            return new Fraction(-numerator, denominator);
    }

    public Fraction abs()
    {
        if(numerator >= 0)
            return this;
        else
            return negate();
    }

    public Fraction pow(int power)
    {
        if(power == 1)
            return this;
        if(power == 0)
            return ONE;
        if(power < 0)
            if(power == 0x80000000)
                return invert().pow(2).pow(-(power / 2));
            else
                return invert().pow(-power);
        Fraction f = multiplyBy(this);
        if(power % 2 == 0)
            return f.pow(power / 2);
        else
            return f.pow(power / 2).multiplyBy(this);
    }

    private static int greatestCommonDivisor(int u, int v)
    {
        if(u > 0)
            u = -u;
        if(v > 0)
            v = -v;
        int k;
        for(k = 0; (u & 1) == 0 && (v & 1) == 0 && k < 31; k++)
        {
            u /= 2;
            v /= 2;
        }

        if(k == 31)
            throw new ArithmeticException("overflow: gcd is 2^31");
        int t = (u & 1) != 1 ? -(u / 2) : v;
        do
        {
            while((t & 1) == 0) 
                t /= 2;
            if(t > 0)
                u = -t;
            else
                v = t;
            t = (v - u) / 2;
        } while(t != 0);
        return -u * (1 << k);
    }

    private static int mulAndCheck(int x, int y)
    {
        long m = (long)x * (long)y;
        if(m < 0xffffffff80000000L || m > 0x7fffffffL)
            throw new ArithmeticException("overflow: mul");
        else
            return (int)m;
    }

    private static int mulPosAndCheck(int x, int y)
    {
        long m = (long)x * (long)y;
        if(m > 0x7fffffffL)
            throw new ArithmeticException("overflow: mulPos");
        else
            return (int)m;
    }

    private static int addAndCheck(int x, int y)
    {
        long s = (long)x + (long)y;
        if(s < 0xffffffff80000000L || s > 0x7fffffffL)
            throw new ArithmeticException("overflow: add");
        else
            return (int)s;
    }

    private static int subAndCheck(int x, int y)
    {
        long s = (long)x - (long)y;
        if(s < 0xffffffff80000000L || s > 0x7fffffffL)
            throw new ArithmeticException("overflow: add");
        else
            return (int)s;
    }

    public Fraction add(Fraction fraction)
    {
        return addSub(fraction, true);
    }

    public Fraction subtract(Fraction fraction)
    {
        return addSub(fraction, false);
    }

    private Fraction addSub(Fraction fraction, boolean isAdd)
    {
        if(fraction == null)
            throw new IllegalArgumentException("The fraction must not be null");
        if(numerator == 0)
            return isAdd ? fraction : fraction.negate();
        if(fraction.numerator == 0)
            return this;
        int d1 = greatestCommonDivisor(denominator, fraction.denominator);
        if(d1 == 1)
        {
            int uvp = mulAndCheck(numerator, fraction.denominator);
            int upv = mulAndCheck(fraction.numerator, denominator);
            return new Fraction(isAdd ? addAndCheck(uvp, upv) : subAndCheck(uvp, upv), mulPosAndCheck(denominator, fraction.denominator));
        }
        BigInteger uvp = BigInteger.valueOf(numerator).multiply(BigInteger.valueOf(fraction.denominator / d1));
        BigInteger upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(denominator / d1));
        BigInteger t = isAdd ? uvp.add(upv) : uvp.subtract(upv);
        int tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue();
        int d2 = tmodd1 != 0 ? greatestCommonDivisor(tmodd1, d1) : d1;
        BigInteger w = t.divide(BigInteger.valueOf(d2));
        if(w.bitLength() > 31)
            throw new ArithmeticException("overflow: numerator too large after multiply");
        else
            return new Fraction(w.intValue(), mulPosAndCheck(denominator / d1, fraction.denominator / d2));
    }

    public Fraction multiplyBy(Fraction fraction)
    {
        if(fraction == null)
            throw new IllegalArgumentException("The fraction must not be null");
        if(numerator == 0 || fraction.numerator == 0)
        {
            return ZERO;
        } else
        {
            int d1 = greatestCommonDivisor(numerator, fraction.denominator);
            int d2 = greatestCommonDivisor(fraction.numerator, denominator);
            return getReducedFraction(mulAndCheck(numerator / d1, fraction.numerator / d2), mulPosAndCheck(denominator / d2, fraction.denominator / d1));
        }
    }

    public Fraction divideBy(Fraction fraction)
    {
        if(fraction == null)
            throw new IllegalArgumentException("The fraction must not be null");
        if(fraction.numerator == 0)
            throw new ArithmeticException("The fraction to divide by must not be zero");
        else
            return multiplyBy(fraction.invert());
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof Fraction))
        {
            return false;
        } else
        {
            Fraction other = (Fraction)obj;
            return getNumerator() == other.getNumerator() && getDenominator() == other.getDenominator();
        }
    }

    public int hashCode()
    {
        if(hashCode == 0)
            hashCode = 37 * (629 + getNumerator()) + getDenominator();
        return hashCode;
    }

    public int compareTo(Object object)
    {
        Fraction other = (Fraction)object;
        if(this == other)
            return 0;
        if(numerator == other.numerator && denominator == other.denominator)
            return 0;
        long first = (long)numerator * (long)other.denominator;
        long second = (long)other.numerator * (long)denominator;
        if(first == second)
            return 0;
        return first >= second ? 1 : -1;
    }

    public String toString()
    {
        if(toString == null)
            toString = (new StringBuffer(32)).append(getNumerator()).append('/').append(getDenominator()).toString();
        return toString;
    }

    public String toProperString()
    {
        if(toProperString == null)
            if(numerator == 0)
                toProperString = "0";
            else
            if(numerator == denominator)
                toProperString = "1";
            else
            if(numerator == -1 * denominator)
                toProperString = "-1";
            else
            if((numerator <= 0 ? numerator : -numerator) < -denominator)
            {
                int properNumerator = getProperNumerator();
                if(properNumerator == 0)
                    toProperString = Integer.toString(getProperWhole());
                else
                    toProperString = (new StringBuffer(32)).append(getProperWhole()).append(' ').append(properNumerator).append('/').append(getDenominator()).toString();
            } else
            {
                toProperString = (new StringBuffer(32)).append(getNumerator()).append('/').append(getDenominator()).toString();
            }
        return toProperString;
    }

    private static final long serialVersionUID = 0x3b76f0847842L;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
    private final int numerator;
    private final int denominator;
    private transient int hashCode;
    private transient String toString;
    private transient String toProperString;

}
