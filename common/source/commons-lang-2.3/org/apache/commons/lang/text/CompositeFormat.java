// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompositeFormat.java

package org.apache.commons.lang.text;

import java.text.*;

public class CompositeFormat extends Format
{

    public CompositeFormat(Format parser, Format formatter)
    {
        this.parser = parser;
        this.formatter = formatter;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
    {
        return formatter.format(obj, toAppendTo, pos);
    }

    public Object parseObject(String source, ParsePosition pos)
    {
        return parser.parseObject(source, pos);
    }

    public Format getParser()
    {
        return parser;
    }

    public Format getFormatter()
    {
        return formatter;
    }

    public String reformat(String input)
        throws ParseException
    {
        return format(parseObject(input));
    }

    private static final long serialVersionUID = 0xc3ebe0740540f8ddL;
    private final Format parser;
    private final Format formatter;
}
