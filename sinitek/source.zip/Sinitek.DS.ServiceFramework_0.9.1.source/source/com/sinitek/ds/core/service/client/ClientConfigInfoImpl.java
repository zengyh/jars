// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientConfigInfoImpl.java

package com.sinitek.ds.core.service.client;

import com.sinitek.ds.core.service.IServiceContext;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.collections.map.CaseInsensitiveMap;

// Referenced classes of package com.sinitek.ds.core.service.client:
//            ServiceClientException, IClientConfigInfo

public class ClientConfigInfoImpl
    implements IClientConfigInfo
{
    private static class RegionMatcher
        implements Matcher
    {

        public boolean isMatch(String code)
        {
            return highLevel.compareToIgnoreCase(code) >= 0 && lowLevel.compareToIgnoreCase(code) <= 0;
        }

        private String lowLevel;
        private String highLevel;

        private RegionMatcher(String highLevel, String lowLevel)
        {
            this.highLevel = highLevel;
            this.lowLevel = lowLevel;
        }

    }

    private static class EquelsMatcher
        implements Matcher
    {

        public boolean isMatch(String code)
        {
            return source.equalsIgnoreCase(code);
        }

        private String source;

        private EquelsMatcher(String source)
        {
            this.source = source;
        }

    }

    private static class PatternMatcher
        implements Matcher
    {

        public boolean isMatch(String code)
        {
            return Pattern.matches(pattern, code);
        }

        private String pattern;

        private PatternMatcher(String pattern)
        {
            this.pattern = pattern;
        }

    }

    private static interface Matcher
    {

        public abstract boolean isMatch(String s);
    }


    public ClientConfigInfoImpl()
    {
        functionList = new ArrayList();
        callMap = new HashMap();
        cacheMap = new CaseInsensitiveMap();
    }

    public IServiceContext findContext(String functionCode)
        throws ServiceClientException
    {
        IServiceContext ctx = (IServiceContext)cacheMap.get(functionCode);
        if(ctx == null)
        {
            Iterator iter = functionList.iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                Matcher matcher = (Matcher)iter.next();
                if(matcher.isMatch(functionCode))
                {
                    ctx = (IServiceContext)callMap.get(matcher);
                    cacheMap.put(functionCode, ctx);
                }
            } while(true);
            if(ctx == null)
            {
                if(defaultContext == null)
                    throw new ServiceClientException("0004", new Object[] {
                        functionCode
                    });
                ctx = defaultContext;
                cacheMap.put(functionCode, ctx);
            }
        }
        return ctx;
    }

    public void registCall(String functionCode, IServiceContext context)
    {
        if("*".equals(functionCode))
            defaultContext = context;
        else
        if(functionCode.indexOf('-') > 0)
        {
            String temp[] = functionCode.split("-");
            Matcher matcher = new RegionMatcher(temp[1], temp[0]);
            functionList.add(matcher);
            callMap.put(matcher, context);
        } else
        if(functionCode.indexOf('*') >= 0)
        {
            Matcher matcher = new PatternMatcher(functionCode.replaceAll("[*]", ".+"));
            functionList.add(matcher);
            callMap.put(matcher, context);
        } else
        if(functionCode.indexOf('?') >= 0)
        {
            Matcher matcher = new PatternMatcher(functionCode.replaceAll("[?]", "."));
            functionList.add(matcher);
            callMap.put(matcher, context);
        } else
        {
            Matcher matcher = new EquelsMatcher(functionCode);
            functionList.add(matcher);
            callMap.put(matcher, context);
        }
    }

    private List functionList;
    private Map callMap;
    private Map cacheMap;
    private IServiceContext defaultContext;
}
