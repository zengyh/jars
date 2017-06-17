// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoAutoComplete.java

package com.sinitek.spirit.webcontrol.autocomplete;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.spirit.webcontrol.autocomplete:
//            AutoCompleteData, IAutoCompleteAware

public class DemoAutoComplete
    implements IAutoCompleteAware
{

    public DemoAutoComplete()
    {
    }

    public List match(String inputValue, Map options, Map params, HttpServletRequest request)
    {
        List result = new ArrayList();
        for(int i = 0; i < 10; i++)
        {
            AutoCompleteData data = new AutoCompleteData((new StringBuilder()).append(inputValue).append(i).toString(), (new StringBuilder()).append("\u5185\u5BB9").append(inputValue).append(i).toString(), "\u5B50\u5185\u5BB9");
            Map orgDate = new HashMap();
            orgDate.put("aaa", "\u54C8\u54C8\u54C8");
            data.setOrgData(orgDate);
            result.add(data);
        }

        return result;
    }
}
