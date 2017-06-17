// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringEscapeUtils.java

package org.apache.commons.lang;

import java.io.*;
import org.apache.commons.lang.exception.NestableRuntimeException;

// Referenced classes of package org.apache.commons.lang:
//            Entities, StringUtils

public class StringEscapeUtils
{

    public StringEscapeUtils()
    {
    }

    public static String escapeJava(String str)
    {
        return escapeJavaStyleString(str, false);
    }

    public static void escapeJava(Writer out, String str)
        throws IOException
    {
        escapeJavaStyleString(out, str, false);
    }

    public static String escapeJavaScript(String str)
    {
        return escapeJavaStyleString(str, true);
    }

    public static void escapeJavaScript(Writer out, String str)
        throws IOException
    {
        escapeJavaStyleString(out, str, true);
    }

    private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes)
    {
        if(str == null)
            return null;
        try
        {
            StringWriter writer = new StringWriter(str.length() * 2);
            escapeJavaStyleString(((Writer) (writer)), str, escapeSingleQuotes);
            return writer.toString();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }

    private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote)
        throws IOException
    {
        if(out == null)
            throw new IllegalArgumentException("The Writer must not be null");
        if(str == null)
            return;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
        {
            char ch = str.charAt(i);
            if(ch > '\u0FFF')
                out.write("\\u" + hex(ch));
            else
            if(ch > '\377')
                out.write("\\u0" + hex(ch));
            else
            if(ch > '\177')
                out.write("\\u00" + hex(ch));
            else
            if(ch < ' ')
                switch(ch)
                {
                case 8: // '\b'
                    out.write(92);
                    out.write(98);
                    break;

                case 10: // '\n'
                    out.write(92);
                    out.write(110);
                    break;

                case 9: // '\t'
                    out.write(92);
                    out.write(116);
                    break;

                case 12: // '\f'
                    out.write(92);
                    out.write(102);
                    break;

                case 13: // '\r'
                    out.write(92);
                    out.write(114);
                    break;

                case 11: // '\013'
                default:
                    if(ch > '\017')
                        out.write("\\u00" + hex(ch));
                    else
                        out.write("\\u000" + hex(ch));
                    break;
                }
            else
                switch(ch)
                {
                case 39: // '\''
                    if(escapeSingleQuote)
                        out.write(92);
                    out.write(39);
                    break;

                case 34: // '"'
                    out.write(92);
                    out.write(34);
                    break;

                case 92: // '\\'
                    out.write(92);
                    out.write(92);
                    break;

                default:
                    out.write(ch);
                    break;
                }
        }

    }

    private static String hex(char ch)
    {
        return Integer.toHexString(ch).toUpperCase();
    }

    public static String unescapeJava(String str)
    {
        if(str == null)
            return null;
        try
        {
            StringWriter writer = new StringWriter(str.length());
            unescapeJava(((Writer) (writer)), str);
            return writer.toString();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void unescapeJava(Writer out, String str)
        throws IOException
    {
        if(out == null)
            throw new IllegalArgumentException("The Writer must not be null");
        if(str == null)
            return;
        int sz = str.length();
        StringBuffer unicode = new StringBuffer(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for(int i = 0; i < sz; i++)
        {
            char ch = str.charAt(i);
            if(inUnicode)
            {
                unicode.append(ch);
                if(unicode.length() == 4)
                    try
                    {
                        int value = Integer.parseInt(unicode.toString(), 16);
                        out.write((char)value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;
                    }
                    catch(NumberFormatException nfe)
                    {
                        throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
                    }
            } else
            if(hadSlash)
            {
                hadSlash = false;
                switch(ch)
                {
                case 92: // '\\'
                    out.write(92);
                    break;

                case 39: // '\''
                    out.write(39);
                    break;

                case 34: // '"'
                    out.write(34);
                    break;

                case 114: // 'r'
                    out.write(13);
                    break;

                case 102: // 'f'
                    out.write(12);
                    break;

                case 116: // 't'
                    out.write(9);
                    break;

                case 110: // 'n'
                    out.write(10);
                    break;

                case 98: // 'b'
                    out.write(8);
                    break;

                case 117: // 'u'
                    inUnicode = true;
                    break;

                default:
                    out.write(ch);
                    break;
                }
            } else
            if(ch == '\\')
                hadSlash = true;
            else
                out.write(ch);
        }

        if(hadSlash)
            out.write(92);
    }

    public static String unescapeJavaScript(String str)
    {
        return unescapeJava(str);
    }

    public static void unescapeJavaScript(Writer out, String str)
        throws IOException
    {
        unescapeJava(out, str);
    }

    public static String escapeHtml(String str)
    {
        if(str == null)
            return null;
        try
        {
            StringWriter writer = new StringWriter((int)((double)str.length() * 1.5D));
            escapeHtml(((Writer) (writer)), str);
            return writer.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void escapeHtml(Writer writer, String string)
        throws IOException
    {
        if(writer == null)
            throw new IllegalArgumentException("The Writer must not be null.");
        if(string == null)
        {
            return;
        } else
        {
            Entities.HTML40.escape(writer, string);
            return;
        }
    }

    public static String unescapeHtml(String str)
    {
        if(str == null)
            return null;
        try
        {
            StringWriter writer = new StringWriter((int)((double)str.length() * 1.5D));
            unescapeHtml(((Writer) (writer)), str);
            return writer.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void unescapeHtml(Writer writer, String string)
        throws IOException
    {
        if(writer == null)
            throw new IllegalArgumentException("The Writer must not be null.");
        if(string == null)
        {
            return;
        } else
        {
            Entities.HTML40.unescape(writer, string);
            return;
        }
    }

    public static void escapeXml(Writer writer, String str)
        throws IOException
    {
        if(writer == null)
            throw new IllegalArgumentException("The Writer must not be null.");
        if(str == null)
        {
            return;
        } else
        {
            Entities.XML.escape(writer, str);
            return;
        }
    }

    public static String escapeXml(String str)
    {
        if(str == null)
            return null;
        else
            return Entities.XML.escape(str);
    }

    public static void unescapeXml(Writer writer, String str)
        throws IOException
    {
        if(writer == null)
            throw new IllegalArgumentException("The Writer must not be null.");
        if(str == null)
        {
            return;
        } else
        {
            Entities.XML.unescape(writer, str);
            return;
        }
    }

    public static String unescapeXml(String str)
    {
        if(str == null)
            return null;
        else
            return Entities.XML.unescape(str);
    }

    public static String escapeSql(String str)
    {
        if(str == null)
            return null;
        else
            return StringUtils.replace(str, "'", "''");
    }
}
