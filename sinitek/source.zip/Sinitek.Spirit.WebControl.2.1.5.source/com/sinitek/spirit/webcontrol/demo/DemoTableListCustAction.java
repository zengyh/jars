// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTableListCustAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.CaseInsensitiveMap;

public class DemoTableListCustAction
    implements ITableAware
{

    public DemoTableListCustAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        List list = new ArrayList();
        Integer pageSize = new Integer((String)map.get("__pageSize"));
        Integer pageNum = new Integer((String)map.get("__pageNum"));
        for(int i = 1; i <= 10; i++)
        {
            Map obj = new CaseInsensitiveMap();
            obj.put("objid", Integer.valueOf(pageNum.intValue() * 10 + i));
            obj.put("DemoString", (new StringBuilder()).append("String").append(pageNum.intValue() * 10).append(i).toString());
            obj.put("DemoInteger", Integer.valueOf(i));
            obj.put("DemoDate", new Date());
            list.add(obj);
        }

        map.put("__allRow", "100");
        return list;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }
}
