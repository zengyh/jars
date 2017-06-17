// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoMultiSelectInteractAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.interact.InteractiveMultiSelectAware;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoMultiSelectInteractAction
    implements InteractiveMultiSelectAware
{

    public DemoMultiSelectInteractAction()
    {
    }

    public Map getData(String keys[], HttpServletRequest request)
    {
        Map map = new HashMap();
        String arr$[] = keys;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String key = arr$[i$];
            for(int i = 0; i < 10; i++)
                map.put((new StringBuilder()).append(key).append("_sub").append(i).toString(), (new StringBuilder()).append(key).append("_val").append(i).toString());

        }

        return map;
    }
}
