// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoCurdAjaxAction.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import com.sinitek.spirit.webcontrol.utils.FormatUtils;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.spirit.webcontrol.demo:
//            DemoObjectImpl, IDemoObject

public class DemoCurdAjaxAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DemoCurdAjaxAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        Map paras = new HashMap();
        StringBuilder sql = new StringBuilder("1 = 1");
        QueryUtils.buildLike("DemoString", (String)map.get("DemoString"), sql, paras);
        QueryUtils.buildEqual("DemoInteger", map.get("DemoInteger"), sql, paras);
        QueryUtils.buildEqual("DemoEnum", map.get("DemoEnum"), sql, paras);
        IMetaDBQuery query = (new MetaDBTemplate()).createQuery("DEMOOBJECT", sql.toString());
        if(!paras.isEmpty())
            query.setParameters(paras);
        return query;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return "";
    }

    public static void addUpdate(Map param, HttpServletRequest request)
        throws Exception
    {
        String type = (String)param.get("type");
        if("0".equals(type))
        {
            IDemoObject o = new DemoObjectImpl();
            o.setDemoString((String)param.get("DemoString"));
            o.setDemoDate(FormatUtils.stringToDate((String)param.get("DemoDate"), "yyyy-MM-dd"));
            if(param.get("DemoEnum") != null)
                o.setDemoEnumEnumValue(new Integer((String)param.get("DemoEnum")));
            o.setDemoInteger(FormatUtils.stringToInteger((String)param.get("DemoInteger")));
            if(param.get("DemoEntity") != null)
                o.setDemoForeignEntityValue(new Integer((String)param.get("DemoEntity")));
            (new MetaDBTemplate()).callExecutor(new IMetaDBTemplateExecutor(o) {

                public void doInTransaction(IMetaDBContext ctx)
                {
                    o.save();
                }

                final IDemoObject val$o;

            
            {
                o = idemoobject;
                super();
            }
            }
);
        } else
        {
            String objid = (String)param.get("objid");
            IDemoObject o = (IDemoObject)(new MetaDBTemplate()).get("DEMOOBJECT", (new Integer(objid)).intValue());
            o.setDemoString((String)param.get("DemoString"));
            o.setDemoDate(FormatUtils.stringToDate((String)param.get("DemoDate"), "yyyy-MM-dd"));
            if(param.get("DemoEnum") != null)
                o.setDemoEnumEnumValue(new Integer((String)param.get("DemoEnum")));
            o.setDemoInteger(FormatUtils.stringToInteger((String)param.get("DemoInteger")));
            if(param.get("DemoEntity") != null)
                o.setDemoForeignEntityValue(new Integer((String)param.get("DemoEntity")));
            (new MetaDBTemplate()).callExecutor(new IMetaDBTemplateExecutor(o) {

                public void doInTransaction(IMetaDBContext ctx)
                {
                    o.save();
                }

                final IDemoObject val$o;

            
            {
                o = idemoobject;
                super();
            }
            }
);
        }
    }

    public Object callCheckBoxPlugin(final List data, Map param, HttpServletRequest request)
    {
        (new MetaDBTemplate()).callExecutor(new IMetaDBTemplateExecutor() {

            public void doInTransaction(IMetaDBContext ctx)
            {
                IDemoObject demoObject;
                for(Iterator i$ = data.iterator(); i$.hasNext(); demoObject.delete())
                {
                    Map o = (Map)i$.next();
                    String objid = (String)o.get("objid");
                    demoObject = (IDemoObject)(new MetaDBTemplate()).get("DEMOOBJECT", (new Integer(objid)).intValue());
                }

            }

            final List val$data;
            final DemoCurdAjaxAction this$0;

            
            {
                this$0 = DemoCurdAjaxAction.this;
                data = list;
                super();
            }
        }
);
        return Boolean.valueOf(true);
    }
}
