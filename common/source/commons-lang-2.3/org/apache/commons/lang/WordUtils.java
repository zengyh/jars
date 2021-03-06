// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WordUtils.java

package org.apache.commons.lang;


// Referenced classes of package org.apache.commons.lang:
//            SystemUtils

public class WordUtils
{

    public WordUtils()
    {
    }

    public static String wrap(String str, int wrapLength)
    {
        return wrap(str, wrapLength, null, false);
    }

    public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords)
    {
        if(str == null)
            return null;
        if(newLineStr == null)
            newLineStr = SystemUtils.LINE_SEPARATOR;
        if(wrapLength < 1)
            wrapLength = 1;
        int inputLineLength = str.length();
        int offset = 0;
        StringBuffer wrappedLine = new StringBuffer(inputLineLength + 32);
        while(inputLineLength - offset > wrapLength) 
            if(str.charAt(offset) == ' ')
            {
                offset++;
            } else
            {
                int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);
                if(spaceToWrapAt >= offset)
                {
                    wrappedLine.append(str.substring(offset, spaceToWrapAt));
                    wrappedLine.append(newLineStr);
                    offset = spaceToWrapAt + 1;
                } else
                if(wrapLongWords)
                {
                    wrappedLine.append(str.substring(offset, wrapLength + offset));
                    wrappedLine.append(newLineStr);
                    offset += wrapLength;
                } else
                {
                    spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
                    if(spaceToWrapAt >= 0)
                    {
                        wrappedLine.append(str.substring(offset, spaceToWrapAt));
                        wrappedLine.append(newLineStr);
                        offset = spaceToWrapAt + 1;
                    } else
                    {
                        wrappedLine.append(str.substring(offset));
                        offset = inputLineLength;
                    }
                }
            }
        wrappedLine.append(str.substring(offset));
        return wrappedLine.toString();
    }

    public static String capitalize(String str)
    {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char delimiters[])
    {
        int delimLen = delimiters != null ? delimiters.length : -1;
        if(str == null || str.length() == 0 || delimLen == 0)
            return str;
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for(int i = 0; i < strLen; i++)
        {
            char ch = str.charAt(i);
            if(isDelimiter(ch, delimiters))
            {
                buffer.append(ch);
                capitalizeNext = true;
            } else
            if(capitalizeNext)
            {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else
            {
                buffer.append(ch);
            }
        }

        return buffer.toString();
    }

    public static String capitalizeFully(String str)
    {
        return capitalizeFully(str, null);
    }

    public static String capitalizeFully(String str, char delimiters[])
    {
        int delimLen = delimiters != null ? delimiters.length : -1;
        if(str == null || str.length() == 0 || delimLen == 0)
        {
            return str;
        } else
        {
            str = str.toLowerCase();
            return capitalize(str, delimiters);
        }
    }

    public static String uncapitalize(String str)
    {
        return uncapitalize(str, null);
    }

    public static String uncapitalize(String str, char delimiters[])
    {
        int delimLen = delimiters != null ? delimiters.length : -1;
        if(str == null || str.length() == 0 || delimLen == 0)
            return str;
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean uncapitalizeNext = true;
        for(int i = 0; i < strLen; i++)
        {
            char ch = str.charAt(i);
            if(isDelimiter(ch, delimiters))
            {
                buffer.append(ch);
                uncapitalizeNext = true;
            } else
            if(uncapitalizeNext)
            {
                buffer.append(Character.toLowerCase(ch));
                uncapitalizeNext = false;
            } else
            {
                buffer.append(ch);
            }
        }

        return buffer.toString();
    }

    public static String swapCase(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        StringBuffer buffer = new StringBuffer(strLen);
        boolean whitespace = true;
        char ch = '\0';
        char tmp = '\0';
        for(int i = 0; i < strLen; i++)
        {
            ch = str.charAt(i);
            if(Character.isUpperCase(ch))
                tmp = Character.toLowerCase(ch);
            else
            if(Character.isTitleCase(ch))
                tmp = Character.toLowerCase(ch);
            else
            if(Character.isLowerCase(ch))
            {
                if(whitespace)
                    tmp = Character.toTitleCase(ch);
                else
                    tmp = Character.toUpperCase(ch);
            } else
            {
                tmp = ch;
            }
            buffer.append(tmp);
            whitespace = Character.isWhitespace(ch);
        }

        return buffer.toString();
    }

    public static String initials(String str)
    {
        return initials(str, null);
    }

    public static String initials(String str, char delimiters[])
    {
        if(str == null || str.length() == 0)
            return str;
        if(delimiters != null && delimiters.length == 0)
            return "";
        int strLen = str.length();
        char buf[] = new char[strLen / 2 + 1];
        int count = 0;
        boolean lastWasGap = true;
        for(int i = 0; i < strLen; i++)
        {
            char ch = str.charAt(i);
            if(isDelimiter(ch, delimiters))
                lastWasGap = true;
            else
            if(lastWasGap)
            {
                buf[count++] = ch;
                lastWasGap = false;
            }
        }

        return new String(buf, 0, count);
    }

    private static boolean isDelimiter(char ch, char delimiters[])
    {
        if(delimiters == null)
            return Character.isWhitespace(ch);
        int i = 0;
        for(int isize = delimiters.length; i < isize; i++)
            if(ch == delimiters[i])
                return true;

        return false;
    }
}
