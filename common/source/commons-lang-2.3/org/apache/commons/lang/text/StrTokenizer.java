// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrTokenizer.java

package org.apache.commons.lang.text;

import java.util.*;

// Referenced classes of package org.apache.commons.lang.text:
//            StrBuilder, StrMatcher

public class StrTokenizer
    implements ListIterator, Cloneable
{

    private static StrTokenizer getCSVClone()
    {
        return (StrTokenizer)CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance()
    {
        return getCSVClone();
    }

    public static StrTokenizer getCSVInstance(String input)
    {
        StrTokenizer tok = getCSVClone();
        tok.reset(input);
        return tok;
    }

    public static StrTokenizer getCSVInstance(char input[])
    {
        StrTokenizer tok = getCSVClone();
        tok.reset(input);
        return tok;
    }

    private static StrTokenizer getTSVClone()
    {
        return (StrTokenizer)TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance()
    {
        return getTSVClone();
    }

    public static StrTokenizer getTSVInstance(String input)
    {
        StrTokenizer tok = getTSVClone();
        tok.reset(input);
        return tok;
    }

    public static StrTokenizer getTSVInstance(char input[])
    {
        StrTokenizer tok = getTSVClone();
        tok.reset(input);
        return tok;
    }

    public StrTokenizer()
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        chars = null;
    }

    public StrTokenizer(String input)
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        if(input != null)
            chars = input.toCharArray();
        else
            chars = null;
    }

    public StrTokenizer(String input, char delim)
    {
        this(input);
        setDelimiterChar(delim);
    }

    public StrTokenizer(String input, String delim)
    {
        this(input);
        setDelimiterString(delim);
    }

    public StrTokenizer(String input, StrMatcher delim)
    {
        this(input);
        setDelimiterMatcher(delim);
    }

    public StrTokenizer(String input, char delim, char quote)
    {
        this(input, delim);
        setQuoteChar(quote);
    }

    public StrTokenizer(String input, StrMatcher delim, StrMatcher quote)
    {
        this(input, delim);
        setQuoteMatcher(quote);
    }

    public StrTokenizer(char input[])
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        chars = input;
    }

    public StrTokenizer(char input[], char delim)
    {
        this(input);
        setDelimiterChar(delim);
    }

    public StrTokenizer(char input[], String delim)
    {
        this(input);
        setDelimiterString(delim);
    }

    public StrTokenizer(char input[], StrMatcher delim)
    {
        this(input);
        setDelimiterMatcher(delim);
    }

    public StrTokenizer(char input[], char delim, char quote)
    {
        this(input, delim);
        setQuoteChar(quote);
    }

    public StrTokenizer(char input[], StrMatcher delim, StrMatcher quote)
    {
        this(input, delim);
        setQuoteMatcher(quote);
    }

    public int size()
    {
        checkTokenized();
        return tokens.length;
    }

    public String nextToken()
    {
        if(hasNext())
            return tokens[tokenPos++];
        else
            return null;
    }

    public String previousToken()
    {
        if(hasPrevious())
            return tokens[--tokenPos];
        else
            return null;
    }

    public String[] getTokenArray()
    {
        checkTokenized();
        return (String[])tokens.clone();
    }

    public List getTokenList()
    {
        checkTokenized();
        List list = new ArrayList(tokens.length);
        for(int i = 0; i < tokens.length; i++)
            list.add(tokens[i]);

        return list;
    }

    public StrTokenizer reset()
    {
        tokenPos = 0;
        tokens = null;
        return this;
    }

    public StrTokenizer reset(String input)
    {
        reset();
        if(input != null)
            chars = input.toCharArray();
        else
            chars = null;
        return this;
    }

    public StrTokenizer reset(char input[])
    {
        reset();
        chars = input;
        return this;
    }

    public boolean hasNext()
    {
        checkTokenized();
        return tokenPos < tokens.length;
    }

    public Object next()
    {
        if(hasNext())
            return tokens[tokenPos++];
        else
            throw new NoSuchElementException();
    }

    public int nextIndex()
    {
        return tokenPos;
    }

    public boolean hasPrevious()
    {
        checkTokenized();
        return tokenPos > 0;
    }

    public Object previous()
    {
        if(hasPrevious())
            return tokens[--tokenPos];
        else
            throw new NoSuchElementException();
    }

    public int previousIndex()
    {
        return tokenPos - 1;
    }

    public void remove()
    {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public void set(Object obj)
    {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public void add(Object obj)
    {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void checkTokenized()
    {
        if(tokens == null)
            if(chars == null)
            {
                List split = tokenize(null, 0, 0);
                tokens = (String[])split.toArray(new String[split.size()]);
            } else
            {
                List split = tokenize(chars, 0, chars.length);
                tokens = (String[])split.toArray(new String[split.size()]);
            }
    }

    protected List tokenize(char chars[], int offset, int count)
    {
        if(chars == null || count == 0)
            return Collections.EMPTY_LIST;
        StrBuilder buf = new StrBuilder();
        List tokens = new ArrayList();
        for(int pos = offset; pos >= 0 && pos < count;)
        {
            pos = readNextToken(chars, pos, count, buf, tokens);
            if(pos >= count)
                addToken(tokens, "");
        }

        return tokens;
    }

    private void addToken(List list, String tok)
    {
        if(tok == null || tok.length() == 0)
        {
            if(isIgnoreEmptyTokens())
                return;
            if(isEmptyTokenAsNull())
                tok = null;
        }
        list.add(tok);
    }

    private int readNextToken(char chars[], int start, int len, StrBuilder workArea, List tokens)
    {
        int removeLen;
        for(; start < len; start += removeLen)
        {
            removeLen = Math.max(getIgnoredMatcher().isMatch(chars, start, start, len), getTrimmerMatcher().isMatch(chars, start, start, len));
            if(removeLen == 0 || getDelimiterMatcher().isMatch(chars, start, start, len) > 0 || getQuoteMatcher().isMatch(chars, start, start, len) > 0)
                break;
        }

        if(start >= len)
        {
            addToken(tokens, "");
            return -1;
        }
        int delimLen = getDelimiterMatcher().isMatch(chars, start, start, len);
        if(delimLen > 0)
        {
            addToken(tokens, "");
            return start + delimLen;
        }
        int quoteLen = getQuoteMatcher().isMatch(chars, start, start, len);
        if(quoteLen > 0)
            return readWithQuotes(chars, start + quoteLen, len, workArea, tokens, start, quoteLen);
        else
            return readWithQuotes(chars, start, len, workArea, tokens, 0, 0);
    }

    private int readWithQuotes(char chars[], int start, int len, StrBuilder workArea, List tokens, int quoteStart, int quoteLen)
    {
        workArea.clear();
        int pos = start;
        boolean quoting = quoteLen > 0;
        int trimStart = 0;
        while(pos < len) 
            if(quoting)
            {
                if(isQuote(chars, pos, len, quoteStart, quoteLen))
                {
                    if(isQuote(chars, pos + quoteLen, len, quoteStart, quoteLen))
                    {
                        workArea.append(chars, pos, quoteLen);
                        pos += quoteLen * 2;
                        trimStart = workArea.size();
                    } else
                    {
                        quoting = false;
                        pos += quoteLen;
                    }
                } else
                {
                    workArea.append(chars[pos++]);
                    trimStart = workArea.size();
                }
            } else
            {
                int delimLen = getDelimiterMatcher().isMatch(chars, pos, start, len);
                if(delimLen > 0)
                {
                    addToken(tokens, workArea.substring(0, trimStart));
                    return pos + delimLen;
                }
                if(quoteLen > 0 && isQuote(chars, pos, len, quoteStart, quoteLen))
                {
                    quoting = true;
                    pos += quoteLen;
                } else
                {
                    int ignoredLen = getIgnoredMatcher().isMatch(chars, pos, start, len);
                    if(ignoredLen > 0)
                    {
                        pos += ignoredLen;
                    } else
                    {
                        int trimmedLen = getTrimmerMatcher().isMatch(chars, pos, start, len);
                        if(trimmedLen > 0)
                        {
                            workArea.append(chars, pos, trimmedLen);
                            pos += trimmedLen;
                        } else
                        {
                            workArea.append(chars[pos++]);
                            trimStart = workArea.size();
                        }
                    }
                }
            }
        addToken(tokens, workArea.substring(0, trimStart));
        return -1;
    }

    private boolean isQuote(char chars[], int pos, int len, int quoteStart, int quoteLen)
    {
        for(int i = 0; i < quoteLen; i++)
            if(pos + i >= len || chars[pos + i] != chars[quoteStart + i])
                return false;

        return true;
    }

    public StrMatcher getDelimiterMatcher()
    {
        return delimMatcher;
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher delim)
    {
        if(delim == null)
            delimMatcher = StrMatcher.noneMatcher();
        else
            delimMatcher = delim;
        return this;
    }

    public StrTokenizer setDelimiterChar(char delim)
    {
        return setDelimiterMatcher(StrMatcher.charMatcher(delim));
    }

    public StrTokenizer setDelimiterString(String delim)
    {
        return setDelimiterMatcher(StrMatcher.stringMatcher(delim));
    }

    public StrMatcher getQuoteMatcher()
    {
        return quoteMatcher;
    }

    public StrTokenizer setQuoteMatcher(StrMatcher quote)
    {
        if(quote != null)
            quoteMatcher = quote;
        return this;
    }

    public StrTokenizer setQuoteChar(char quote)
    {
        return setQuoteMatcher(StrMatcher.charMatcher(quote));
    }

    public StrMatcher getIgnoredMatcher()
    {
        return ignoredMatcher;
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher ignored)
    {
        if(ignored != null)
            ignoredMatcher = ignored;
        return this;
    }

    public StrTokenizer setIgnoredChar(char ignored)
    {
        return setIgnoredMatcher(StrMatcher.charMatcher(ignored));
    }

    public StrMatcher getTrimmerMatcher()
    {
        return trimmerMatcher;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher trimmer)
    {
        if(trimmer != null)
            trimmerMatcher = trimmer;
        return this;
    }

    public boolean isEmptyTokenAsNull()
    {
        return emptyAsNull;
    }

    public StrTokenizer setEmptyTokenAsNull(boolean emptyAsNull)
    {
        this.emptyAsNull = emptyAsNull;
        return this;
    }

    public boolean isIgnoreEmptyTokens()
    {
        return ignoreEmptyTokens;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean ignoreEmptyTokens)
    {
        this.ignoreEmptyTokens = ignoreEmptyTokens;
        return this;
    }

    public String getContent()
    {
        if(chars == null)
            return null;
        else
            return new String(chars);
    }

    public Object clone()
    {
        try
        {
            return cloneReset();
        }
        catch(CloneNotSupportedException ex)
        {
            return null;
        }
    }

    Object cloneReset()
        throws CloneNotSupportedException
    {
        StrTokenizer cloned = (StrTokenizer)super.clone();
        if(cloned.chars != null)
            cloned.chars = (char[])cloned.chars.clone();
        cloned.reset();
        return cloned;
    }

    public String toString()
    {
        if(tokens == null)
            return "StrTokenizer[not tokenized yet]";
        else
            return "StrTokenizer" + getTokenList();
    }

    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char chars[];
    private String tokens[];
    private int tokenPos;
    private StrMatcher delimMatcher;
    private StrMatcher quoteMatcher;
    private StrMatcher ignoredMatcher;
    private StrMatcher trimmerMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;

    static 
    {
        CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
        CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
        TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
        TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
    }
}
