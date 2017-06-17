// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrSubstitutor.java

package org.apache.commons.lang.text;

import java.util.*;

// Referenced classes of package org.apache.commons.lang.text:
//            StrLookup, StrBuilder, StrMatcher

public class StrSubstitutor
{

    public static String replace(Object source, Map valueMap)
    {
        return (new StrSubstitutor(valueMap)).replace(source);
    }

    public static String replace(Object source, Map valueMap, String prefix, String suffix)
    {
        return (new StrSubstitutor(valueMap, prefix, suffix)).replace(source);
    }

    public static String replaceSystemProperties(Object source)
    {
        return (new StrSubstitutor(StrLookup.systemPropertiesLookup())).replace(source);
    }

    public StrSubstitutor()
    {
        this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map valueMap)
    {
        this(StrLookup.mapLookup(valueMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map valueMap, String prefix, String suffix)
    {
        this(StrLookup.mapLookup(valueMap), prefix, suffix, '$');
    }

    public StrSubstitutor(Map valueMap, String prefix, String suffix, char escape)
    {
        this(StrLookup.mapLookup(valueMap), prefix, suffix, escape);
    }

    public StrSubstitutor(StrLookup variableResolver)
    {
        this(variableResolver, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(StrLookup variableResolver, String prefix, String suffix, char escape)
    {
        setVariableResolver(variableResolver);
        setVariablePrefix(prefix);
        setVariableSuffix(suffix);
        setEscapeChar(escape);
    }

    public StrSubstitutor(StrLookup variableResolver, StrMatcher prefixMatcher, StrMatcher suffixMatcher, char escape)
    {
        setVariableResolver(variableResolver);
        setVariablePrefixMatcher(prefixMatcher);
        setVariableSuffixMatcher(suffixMatcher);
        setEscapeChar(escape);
    }

    public String replace(String source)
    {
        if(source == null)
            return null;
        StrBuilder buf = new StrBuilder(source);
        if(!substitute(buf, 0, source.length()))
            return source;
        else
            return buf.toString();
    }

    public String replace(String source, int offset, int length)
    {
        if(source == null)
            return null;
        StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
        if(!substitute(buf, 0, length))
            return source.substring(offset, offset + length);
        else
            return buf.toString();
    }

    public String replace(char source[])
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(source.length)).append(source);
            substitute(buf, 0, source.length);
            return buf.toString();
        }
    }

    public String replace(char source[], int offset, int length)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
            substitute(buf, 0, length);
            return buf.toString();
        }
    }

    public String replace(StringBuffer source)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(source.length())).append(source);
            substitute(buf, 0, buf.length());
            return buf.toString();
        }
    }

    public String replace(StringBuffer source, int offset, int length)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
            substitute(buf, 0, length);
            return buf.toString();
        }
    }

    public String replace(StrBuilder source)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(source.length())).append(source);
            substitute(buf, 0, buf.length());
            return buf.toString();
        }
    }

    public String replace(StrBuilder source, int offset, int length)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
            substitute(buf, 0, length);
            return buf.toString();
        }
    }

    public String replace(Object source)
    {
        if(source == null)
        {
            return null;
        } else
        {
            StrBuilder buf = (new StrBuilder()).append(source);
            substitute(buf, 0, buf.length());
            return buf.toString();
        }
    }

    public boolean replaceIn(StringBuffer source)
    {
        if(source == null)
            return false;
        else
            return replaceIn(source, 0, source.length());
    }

    public boolean replaceIn(StringBuffer source, int offset, int length)
    {
        if(source == null)
            return false;
        StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
        if(!substitute(buf, 0, length))
        {
            return false;
        } else
        {
            source.replace(offset, offset + length, buf.toString());
            return true;
        }
    }

    public boolean replaceIn(StrBuilder source)
    {
        if(source == null)
            return false;
        else
            return substitute(source, 0, source.length());
    }

    public boolean replaceIn(StrBuilder source, int offset, int length)
    {
        if(source == null)
            return false;
        else
            return substitute(source, offset, length);
    }

    protected boolean substitute(StrBuilder buf, int offset, int length)
    {
        return substitute(buf, offset, length, null) > 0;
    }

    private int substitute(StrBuilder buf, int offset, int length, List priorVariables)
    {
        StrMatcher prefixMatcher = getVariablePrefixMatcher();
        StrMatcher suffixMatcher = getVariableSuffixMatcher();
        char escape = getEscapeChar();
        boolean top = priorVariables == null;
        boolean altered = false;
        int lengthChange = 0;
        char chars[] = buf.buffer;
        int bufEnd = offset + length;
        for(int pos = offset; pos < bufEnd;)
        {
            int startMatchLen = prefixMatcher.isMatch(chars, pos, offset, bufEnd);
            if(startMatchLen == 0)
                pos++;
            else
            if(pos > offset && chars[pos - 1] == escape)
            {
                buf.deleteCharAt(pos - 1);
                chars = buf.buffer;
                lengthChange--;
                altered = true;
                bufEnd--;
            } else
            {
                int startPos = pos;
                pos += startMatchLen;
                int endMatchLen = 0;
                while(pos < bufEnd) 
                {
                    endMatchLen = suffixMatcher.isMatch(chars, pos, offset, bufEnd);
                    if(endMatchLen == 0)
                    {
                        pos++;
                        continue;
                    }
                    String varName = new String(chars, startPos + startMatchLen, pos - startPos - startMatchLen);
                    pos += endMatchLen;
                    int endPos = pos;
                    if(priorVariables == null)
                    {
                        priorVariables = new ArrayList();
                        priorVariables.add(new String(chars, offset, length));
                    }
                    checkCyclicSubstitution(varName, priorVariables);
                    priorVariables.add(varName);
                    String varValue = resolveVariable(varName, buf, startPos, endPos);
                    if(varValue != null)
                    {
                        int varLen = varValue.length();
                        buf.replace(startPos, endPos, varValue);
                        altered = true;
                        int change = substitute(buf, startPos, varLen, priorVariables);
                        change += varLen - (endPos - startPos);
                        pos += change;
                        bufEnd += change;
                        lengthChange += change;
                        chars = buf.buffer;
                    }
                    priorVariables.remove(priorVariables.size() - 1);
                    break;
                }
            }
        }

        if(top)
            return altered ? 1 : 0;
        else
            return lengthChange;
    }

    private void checkCyclicSubstitution(String varName, List priorVariables)
    {
        if(!priorVariables.contains(varName))
        {
            return;
        } else
        {
            StrBuilder buf = new StrBuilder(256);
            buf.append("Infinite loop in property interpolation of ");
            buf.append(priorVariables.remove(0));
            buf.append(": ");
            buf.appendWithSeparators(priorVariables, "->");
            throw new IllegalStateException(buf.toString());
        }
    }

    protected String resolveVariable(String variableName, StrBuilder buf, int startPos, int endPos)
    {
        StrLookup resolver = getVariableResolver();
        if(resolver == null)
            return null;
        else
            return resolver.lookup(variableName);
    }

    public char getEscapeChar()
    {
        return escapeChar;
    }

    public void setEscapeChar(char escapeCharacter)
    {
        escapeChar = escapeCharacter;
    }

    public StrMatcher getVariablePrefixMatcher()
    {
        return prefixMatcher;
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher prefixMatcher)
    {
        if(prefixMatcher == null)
        {
            throw new IllegalArgumentException("Variable prefix matcher must not be null!");
        } else
        {
            this.prefixMatcher = prefixMatcher;
            return this;
        }
    }

    public StrSubstitutor setVariablePrefix(char prefix)
    {
        return setVariablePrefixMatcher(StrMatcher.charMatcher(prefix));
    }

    public StrSubstitutor setVariablePrefix(String prefix)
    {
        if(prefix == null)
            throw new IllegalArgumentException("Variable prefix must not be null!");
        else
            return setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix));
    }

    public StrMatcher getVariableSuffixMatcher()
    {
        return suffixMatcher;
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher suffixMatcher)
    {
        if(suffixMatcher == null)
        {
            throw new IllegalArgumentException("Variable suffix matcher must not be null!");
        } else
        {
            this.suffixMatcher = suffixMatcher;
            return this;
        }
    }

    public StrSubstitutor setVariableSuffix(char suffix)
    {
        return setVariableSuffixMatcher(StrMatcher.charMatcher(suffix));
    }

    public StrSubstitutor setVariableSuffix(String suffix)
    {
        if(suffix == null)
            throw new IllegalArgumentException("Variable suffix must not be null!");
        else
            return setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix));
    }

    public StrLookup getVariableResolver()
    {
        return variableResolver;
    }

    public void setVariableResolver(StrLookup variableResolver)
    {
        this.variableResolver = variableResolver;
    }

    public static final char DEFAULT_ESCAPE = 36;
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrLookup variableResolver;

}
