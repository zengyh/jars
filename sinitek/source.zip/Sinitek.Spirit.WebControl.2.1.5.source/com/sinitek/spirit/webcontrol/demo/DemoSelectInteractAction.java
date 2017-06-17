// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoSelectInteractAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.interact.InteractiveSelectAware;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoSelectInteractAction
    implements InteractiveSelectAware
{

    public DemoSelectInteractAction()
    {
    }

    public Map getData(String key, HttpServletRequest request)
    {
        Map map = new LinkedHashMap();
        for(int i = 0; i < 10; i++)
            map.put((new StringBuilder()).append(key).append("_sub").append(i).toString(), (new StringBuilder()).append(key).append("_val").append(i).toString());

        return map;
    }
}
