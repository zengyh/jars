// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTableAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoTableAction
    implements ITableAware
{

    public DemoTableAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        String sql = "select t.*,CAST(t.demodate AS TIMESTAMP) as demoTime from DemoObject t where 1 = 1";
        return (new MetaDBTemplate()).createSqlQuery(sql);
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }

    public static String testCustCol(Map data)
    {
        return "<a href='#' title='\u6D4B\u8BD5\u81EA\u5B9A\u4E49tip'>\u54C8\u54C8</a>";
    }
}
