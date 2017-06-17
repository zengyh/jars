// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoSelector.java

package com.sinitek.spirit.webcontrol.selector;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.spirit.webcontrol.selector:
//            ClassifiedObject, SelectorData, IClassifiedSelectorAware, ILetterSelectorAware

public class DemoSelector
    implements IClassifiedSelectorAware, ILetterSelectorAware
{

    public DemoSelector()
    {
    }

    public List genClassifiedSelector(Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        List result = new ArrayList();
        for(int i = 0; i < 5; i++)
        {
            ClassifiedObject object = new ClassifiedObject((new StringBuilder()).append("").append(i).toString(), (new StringBuilder()).append("\u5206\u7C7B").append(i).toString());
            for(int j = 0; j < 10; j++)
                object.add(new SelectorData((new StringBuilder()).append(i).append("_").append(j).toString(), (new StringBuilder()).append("\u5185\u5BB9\u5F88\u957F\u5F88\u957F\u5F88\u957F\u5F88\u957F\u5F88\u957F").append(i).append("_").append(j).toString()));

            result.add(object);
        }

        return result;
    }

    public List genLetterSelector(Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        List result = new ArrayList();
        for(int i = 0; i < 50; i++)
            result.add(new SelectorData((new StringBuilder()).append(i).append("").toString(), getChinese(i)));

        return result;
    }

    private String getChinese(long seed)
        throws Exception
    {
        seed = (new Date()).getTime() + seed;
        Random random = new Random(seed);
        int highPos = 176 + Math.abs(random.nextInt(39));
        int lowPos = 161 + Math.abs(random.nextInt(93));
        byte b[] = new byte[2];
        b[0] = (new Integer(highPos)).byteValue();
        b[1] = (new Integer(lowPos)).byteValue();
        String str = new String(b, "GB2312");
        return str;
    }
}
