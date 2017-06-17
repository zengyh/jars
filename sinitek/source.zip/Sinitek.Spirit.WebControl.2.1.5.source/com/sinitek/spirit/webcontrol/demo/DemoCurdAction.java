// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoCurdAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class DemoCurdAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DemoCurdAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        Map paras = new HashMap();
        StringBuilder sql = new StringBuilder("select * from (SELECT * FROM DEMOOBJECT) where 1=1");
        QueryUtils.buildEqual("Entityname", map.get("Entityname"), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Updatetimestamp")))
        {
            SimpleDateFormat updatetimestampTime = new SimpleDateFormat("yyyy-MM-dd");
            QueryUtils.buildEqual("Updatetimestamp", updatetimestampTime.parse((String)map.get("Updatetimestamp")), sql, paras);
        }
        if(StringUtils.isNotBlank((String)map.get("Demodate")))
        {
            SimpleDateFormat demodateTime = new SimpleDateFormat("yyyy-MM-dd");
            QueryUtils.buildEqual("Demodate", demodateTime.parse((String)map.get("Demodate")), sql, paras);
        }
        if(StringUtils.isNotBlank((String)map.get("Demointeger")))
            QueryUtils.buildEqual("Demointeger", Integer.valueOf((String)map.get("Demointeger")), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Demoforeign")))
            QueryUtils.buildEqual("Demoforeign", Integer.valueOf((String)map.get("Demoforeign")), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Objid")))
            QueryUtils.buildEqual("Objid", Integer.valueOf((String)map.get("Objid")), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Demoenum")))
            QueryUtils.buildEqual("Demoenum", Integer.valueOf((String)map.get("Demoenum")), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Version")))
            QueryUtils.buildEqual("Version", Integer.valueOf((String)map.get("Version")), sql, paras);
        if(StringUtils.isNotBlank((String)map.get("Createtimestamp")))
        {
            SimpleDateFormat createtimestampTime = new SimpleDateFormat("yyyy-MM-dd");
            QueryUtils.buildEqual("Createtimestamp", createtimestampTime.parse((String)map.get("Createtimestamp")), sql, paras);
        }
        QueryUtils.buildEqual("Demostring", map.get("Demostring"), sql, paras);
        IMetaDBQuery result = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        if(!paras.isEmpty())
            result.setParameters(paras);
        return result;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }

    public static void addUpdate(Map map, HttpServletRequest request)
    {
        String type = (String)map.get("type");
        if(!"0".equals(type));
    }

    public Object callCheckBoxPlugin(final List data, Map param, HttpServletRequest request)
    {
        (new MetaDBTemplate()).callExecutor(new IMetaDBTemplateExecutor() {

            public void doInTransaction(IMetaDBContext ctx)
            {
                Map o;
                for(Iterator i$ = data.iterator(); i$.hasNext();)
                    o = (Map)i$.next();

            }

            final List val$data;
            final DemoCurdAction this$0;

            
            {
                this$0 = DemoCurdAction.this;
                data = list;
                super();
            }
        }
);
        return Boolean.valueOf(true);
    }
}
