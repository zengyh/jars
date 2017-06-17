// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityListTag.java

package com.sinitek.spirit.webcontrol.tag.param;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.lang.StringUtils;

public class EntityListTag extends BodyTagSupport
{

    public EntityListTag()
    {
    }

    public int doEndTag()
        throws JspException
    {
        String sql = "1=1";
        if(StringUtils.isNotBlank(orderBy))
            sql = (new StringBuilder()).append(sql).append(" order by ").append(orderBy).toString();
        List list = (new MetaDBTemplate()).createQuery(MetaDBContextFactory.getInstance().getEntity(entity), sql).getResult();
        List result = new ArrayList();
        Map requestMap;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(requestMap))
        {
            Object aList = i$.next();
            requestMap = new HashMap();
            requestMap.putAll((Map)aList);
            if(aList instanceof IMetaObject)
            {
                requestMap.put("objid", Integer.valueOf(((IMetaObject)aList).getId()));
                requestMap.put("createtimestamp", ((IMetaObject)aList).getCreateTimestamp());
                requestMap.put("updatetimestamp", ((IMetaObject)aList).getUpdateTimestamp());
            }
        }

        pageContext.setAttribute(var, result);
        return 1;
    }

    public String getEntity()
    {
        return entity;
    }

    public void setEntity(String entity)
    {
        this.entity = entity;
    }

    public String getVar()
    {
        return var;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    private String entity;
    private String var;
    private String orderBy;
}
