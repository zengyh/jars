// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTableHqlAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoTableHqlAction
    implements ITableAware
{

    public DemoTableHqlAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        return (new MetaDBTemplate()).createQuery("DEMOOBJECT", "1=1");
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }

    public static String testCustCol(Map data)
    {
        return "\u54C8\u54C8";
    }
}
