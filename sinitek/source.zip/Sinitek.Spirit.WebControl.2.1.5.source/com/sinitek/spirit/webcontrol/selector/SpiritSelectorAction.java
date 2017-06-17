// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritSelectorAction.java

package com.sinitek.spirit.webcontrol.selector;

import com.sinitek.spirit.webcontrol.utils.PinYinUtils;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.selector:
//            IClassifiedSelectorAware, ILetterSelectorAware, SelectorData, ClassifiedObject

public class SpiritSelectorAction
{

    public SpiritSelectorAction()
    {
    }

    public List genClassifiedSelector(Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        String clazz = (String)options.get("clazz");
        try
        {
            IClassifiedSelectorAware action = (IClassifiedSelectorAware)Class.forName(clazz).newInstance();
            return action.genClassifiedSelector(options, params, request);
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    public List genLetterSelector(Map options, Map params, HttpServletRequest request)
        throws Exception
    {
        String clazz = (String)options.get("clazz");
        Map _letterMap = getLetterMap();
        try
        {
            ILetterSelectorAware action = (ILetterSelectorAware)Class.forName(clazz).newInstance();
            List list = action.genLetterSelector(options, params, request);
            Map result = new HashMap();
            Iterator i$ = list.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                SelectorData data = (SelectorData)i$.next();
                String text = data.getText();
                if(StringUtils.isNotBlank(text))
                {
                    String lt = text.substring(0, 1);
                    if(StringUtils.isAlpha(lt))
                        addToClassMap(result, data, (String)_letterMap.get(PinYinUtils.getFirstLetter(lt)));
                    else
                        addToClassMap(result, data, "\u5176\u5B83");
                }
            } while(true);
            List _result = new ArrayList();
            addToClass(result, _result, "A-G");
            addToClass(result, _result, "H-L");
            addToClass(result, _result, "M-R");
            addToClass(result, _result, "S-Z");
            addToClass(result, _result, "\u5176\u5B83");
            return _result;
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    private void addToClass(Map result, List _result, String classifyNam)
    {
        if(result.containsKey(classifyNam))
        {
            ClassifiedObject obj = new ClassifiedObject(classifyNam);
            obj.addAll((List)result.get(classifyNam));
            _result.add(obj);
        }
    }

    private void addToClassMap(Map result, SelectorData data, String classifyName)
    {
        List tmp;
        if(!result.containsKey(classifyName))
            tmp = new ArrayList();
        else
            tmp = (List)result.get(classifyName);
        tmp.add(data);
        result.put(classifyName, tmp);
    }

    private static Map getLetterMap()
    {
        if(letterMap.isEmpty())
        {
            letterMap.put("a", "A-G");
            letterMap.put("b", "A-G");
            letterMap.put("c", "A-G");
            letterMap.put("d", "A-G");
            letterMap.put("e", "A-G");
            letterMap.put("f", "A-G");
            letterMap.put("g", "A-G");
            letterMap.put("h", "H-L");
            letterMap.put("i", "H-L");
            letterMap.put("j", "H-L");
            letterMap.put("k", "H-L");
            letterMap.put("l", "H-L");
            letterMap.put("m", "M-R");
            letterMap.put("n", "M-R");
            letterMap.put("o", "M-R");
            letterMap.put("p", "M-R");
            letterMap.put("q", "M-R");
            letterMap.put("r", "M-R");
            letterMap.put("s", "S-Z");
            letterMap.put("t", "S-Z");
            letterMap.put("u", "S-Z");
            letterMap.put("v", "S-Z");
            letterMap.put("w", "S-Z");
            letterMap.put("x", "S-Z");
            letterMap.put("y", "S-Z");
            letterMap.put("z", "S-Z");
        }
        return letterMap;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/selector/SpiritSelectorAction);
    private static final Map letterMap = new HashMap();

}
