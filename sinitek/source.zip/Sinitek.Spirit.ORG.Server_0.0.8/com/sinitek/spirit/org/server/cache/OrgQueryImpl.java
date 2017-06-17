// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgQueryImpl.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.service.AtomicQueryExpressionImpl;
import com.sinitek.spirit.org.server.service.IAtomicQueryExpression;
import java.util.LinkedList;
import java.util.List;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            PathExpressionImpl, OrgQuery, PathExpression

public class OrgQueryImpl
    implements OrgQuery
{

    public OrgQueryImpl(IOrgObject fromObject, AtomicQueryExpressionImpl expression)
    {
        this.fromObject = fromObject;
        pathExpression = new PathExpressionImpl(expression);
    }

    public String getFromObjectId()
    {
        return fromObject.getOrgId();
    }

    public PathExpression getPathExpression()
    {
        return pathExpression;
    }

    public int hashCode()
    {
        int code = 1;
        code = 31 * code + fromObject.getOrgId().hashCode();
        code = 31 * code + pathExpression.hashCode();
        return code;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(obj instanceof OrgQuery)
        {
            OrgQuery query = (OrgQuery)obj;
            return fromObject.getOrgId().equals(query.getFromObjectId()) && pathExpression.equals(query.getPathExpression());
        } else
        {
            return false;
        }
    }

    public List getResult()
    {
        List input = new LinkedList();
        List output = new LinkedList();
        input.add(fromObject);
        pathExpression.getAtomicQueryExpression().query(input, output);
        return output;
    }

    private IOrgObject fromObject;
    private PathExpression pathExpression;
}
