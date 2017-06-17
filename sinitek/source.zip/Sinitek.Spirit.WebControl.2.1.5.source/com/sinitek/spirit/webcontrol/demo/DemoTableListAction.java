// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTableListAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.CaseInsensitiveMap;

public class DemoTableListAction
    implements ITableAware
{

    public DemoTableListAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        List list = new ArrayList();
        for(int i = 0; i < 100; i++)
        {
            Map obj = new CaseInsensitiveMap();
            obj.put("DemoString", (new StringBuilder()).append("String").append(i).toString());
            obj.put("DemoInteger", Integer.valueOf(i));
            obj.put("DemoDate", new Date());
            list.add(obj);
        }

        return list;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }
}
