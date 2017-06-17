// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CurdPropertyPageAction.java

package com.sinitek.spirit.webcontrol.autogen;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class CurdPropertyPageAction
    implements ITableAware
{

    public CurdPropertyPageAction()
    {
    }

    public Object getResult(Map map, HttpServletRequest request)
        throws Exception
    {
        String type = (String)map.get("type");
        List propertyList;
        if("0".equals(type))
            propertyList = getListByEntity(map);
        else
            propertyList = getListBySql(map);
        afterTreatment(propertyList);
        return propertyList;
    }

    private void afterTreatment(List propertyList)
    {
        for(int i = 0; i < propertyList.size(); i++)
        {
            String otherInfo = "";
            String otherMsg = "";
            boolean isReadOnly = false;
            Map pmap = (Map)propertyList.get(i);
            String pisnotnull = (String)pmap.get("propertyisnotnull");
            String ptype = (String)pmap.get("propertytype");
            String pinfo = (String)pmap.get("propertyinfo");
            StringBuilder resultType = new StringBuilder("<select onchange='changeResultType(this);' name='resultType' style='width:100px'>");
            StringBuilder queryHtml = new StringBuilder("<select onchange='changeSelectType(this);' name='queryType' style='width:100px'>");
            queryHtml.append("<option value='text'>\u6587\u672C\u6846</option>");
            resultType.append("<option value='string'>\u5B57\u7B26\u4E32</option>");
            if("enum".equalsIgnoreCase(ptype))
            {
                queryHtml.append("<option value='select' selected='selected'>\u9009\u62E9\u6846</option>");
                resultType.append("<option value='enum' selected='selected'>\u679A\u4E3E\u7C7B</option>");
                otherInfo = (String)pmap.get("enumname");
                otherMsg = "\u8F93\u5165\u7C7B\u540D,\u4F8B\u5982\uFF1Acom.xxx.UserType";
            } else
            if("date".equalsIgnoreCase(ptype))
            {
                queryHtml.append("<option value='date' selected='selected'>\u65E5\u671F\u8F93\u5165\u6846</option>");
                resultType.append("<option value='date' selected='selected'>\u65E5\u671F</option>");
                otherInfo = "yyyy-MM-dd";
                otherMsg = "\u8F93\u5165\u6837\u5F0F,\u9ED8\u8BA4:yyyy-MM-dd";
            } else
            if("double".equalsIgnoreCase(ptype) || "number".equalsIgnoreCase(ptype))
            {
                resultType.append("<option value='number' selected='selected'>\u6570\u5B57</option>");
                otherInfo = "2";
                otherMsg = "\u8F93\u5165\u5C0F\u6570\u4F4D\u6570,\u9ED8\u8BA4:2  m\u8868\u793A\u91D1\u989D i\u8868\u793A\u6574\u578B";
            } else
            if("integer".equalsIgnoreCase(ptype))
            {
                resultType.append("<option value='number' selected='selected'>\u6570\u5B57</option>");
                otherInfo = "i";
                otherMsg = "\u8F93\u5165\u5C0F\u6570\u4F4D\u6570,\u9ED8\u8BA4:2  m\u8868\u793A\u91D1\u989D i\u8868\u793A\u6574\u578B";
            } else
            if("boolean".equalsIgnoreCase(ptype))
            {
                queryHtml.append("<option value='select' selected='selected'>\u9009\u62E9\u6846</option>");
                resultType.append("<option value='boolean' selected='selected'>boolean</option>");
                otherInfo = "\u662F:\u5426";
                otherMsg = "\u8F93\u5165true:false,\u4F8B\u5982\uFF1A\u662F:\u5426";
            } else
            {
                resultType.append("<option value='date'>\u65E5\u671F</option>");
                resultType.append("<option value='number'>\u6570\u5B57</option>");
                resultType.append("<option value='boolean'>boolean</option>");
                resultType.append("<option value='enum'>\u679A\u4E3E\u7C7B</option>");
                isReadOnly = true;
            }
            resultType.append("<input style='width:300px' type='text' value='").append(otherInfo).append("' size='12' type='other' title='").append(otherInfo).append("'").append(isReadOnly ? " readOnly='readOnly'" : "").append("/>");
            resultType.append("<br><label style='color:red' name='msg'>").append(otherMsg).append("</label>");
            resultType.append("</select>");
            queryHtml.append("</select>");
            pmap.put("propertyname", StringUtils.capitalize((String)pmap.get("propertyname")));
            pmap.put("propertyinfo", (new StringBuilder()).append("<input style='width:95%'  type='text' name='propertyinfo' value='").append(pinfo == null ? "" : pinfo).append("' size='10'/>").toString());
            pmap.put("resulttype", resultType.toString());
            pmap.put("querytype", queryHtml.toString());
            pmap.put("num", Integer.valueOf(i + 1));
            pmap.put("allownull", (new StringBuilder()).append("<input type='checkbox' name='allowNull'").append("1".equals(pisnotnull) ? "" : " checked='checked'").append("/>").toString());
        }

    }

    private List getListBySql(Map map)
    {
        List list = (new MetaDBTemplate()).createSqlQuery((String)map.get("sql")).getResult();
        List result = new ArrayList();
        Map sqlResultMap = (Map)list.get(0);
        Map tmp;
        for(Iterator i$ = sqlResultMap.keySet().iterator(); i$.hasNext(); result.add(tmp))
        {
            Object o = i$.next();
            tmp = new HashMap();
            String key = (String)o;
            tmp.put("propertyname", key);
            Object object = sqlResultMap.get(key);
            if(object instanceof BigDecimal)
            {
                tmp.put("propertytype", "number");
                continue;
            }
            if(object instanceof Double)
            {
                tmp.put("propertytype", "double");
                continue;
            }
            if(object instanceof Integer)
            {
                tmp.put("propertytype", "integer");
                continue;
            }
            if(object instanceof Date)
                tmp.put("propertytype", "date");
            else
                tmp.put("propertytype", "string");
        }

        return result;
    }

    private List getListByEntity(Map map)
    {
        Map paras = new HashMap();
        StringBuilder querySql = new StringBuilder("select * from metadb_property t where 1=1\n");
        QueryUtils.buildEqual("t.entityname", map.get("entityName"), querySql, paras);
        IMetaDBQuery result = (new MetaDBTemplate()).createSqlQuery(querySql.toString());
        if(!paras.isEmpty())
            result.setParameters(paras);
        return result.getResult();
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request)
    {
        return null;
    }

    public void checkSql(Map map, HttpServletRequest request)
    {
        int count = (new MetaDBTemplate()).createSqlQuery((String)map.get("sql")).getResultCount();
        if(count == 0)
            throw new RuntimeException("\u7ED3\u679C\u4E2D\u81F3\u5C11\u6709\u4E00\u4E2A\u8BB0\u5F55\u624D\u80FD\u751F\u6210");
        else
            return;
    }
}
