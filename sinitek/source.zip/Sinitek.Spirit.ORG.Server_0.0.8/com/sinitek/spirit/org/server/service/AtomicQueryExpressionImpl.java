// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AtomicQueryExpressionImpl.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.entity.ObjectType;
import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            IAtomicQueryExpression, QueryDir, QueryCondition, OperationType

public class AtomicQueryExpressionImpl
    implements IAtomicQueryExpression
{

    public AtomicQueryExpressionImpl()
    {
    }

    public String getExpression()
    {
        return expression;
    }

    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public void query(List inputList, List outputList)
    {
        MetaDBTemplate template = new MetaDBTemplate();
        int maxLevel = 0;
        List tempResultList = new ArrayList();
        List _resultList = new ArrayList();
        for(Iterator i$ = inputList.iterator(); i$.hasNext();)
        {
            IOrgObject inputOrgObj = (IOrgObject)i$.next();
            Map param = new HashMap();
            param.put("objId", inputOrgObj.getOrgId());
            StringBuffer hql = new StringBuffer("select obj.ORGID \nfrom SPRT_ORGRELA rela, SPRT_ORGOBJECT obj\nwhere ");
            if(queryDir == QueryDir.DOWN)
            {
                hql.append("rela.TOOBJECTID=obj.ORGID\n");
                hql.append("and rela.FROMOBJECTID=:objId\n");
            } else
            {
                hql.append("rela.FROMOBJECTID=obj.ORGID\n");
                hql.append("and rela.TOOBJECTID=:objId\n");
            }
            for(int i = 0; i < queryCondition.length; i++)
            {
                QueryCondition condition = queryCondition[i];
                OperationType type = operationType[i];
                Object value = queryValue[i];
                boolean notFlag = this.notFlag[i];
                static class _cls1
                {

                    static final int $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[];
                    static final int $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[];

                    static 
                    {
                        $SwitchMap$com$sinitek$spirit$org$server$service$OperationType = new int[OperationType.values().length];
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.EQUALS.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.GREATER.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.GREATER_OR_EQUAL.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.LESS.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.LESS_OR_EQUAL.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.IN.ordinal()] = 6;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$OperationType[OperationType.LIKE.ordinal()] = 7;
                        }
                        catch(NoSuchFieldError ex) { }
                        $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition = new int[QueryCondition.values().length];
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.ORGNAME.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.RELATIONSHIPTYPE.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.LEVEL.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError ex) { }
                        try
                        {
                            $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.ORGTYPE.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError ex) { }
                    }
                }

                switch(_cls1..SwitchMap.com.sinitek.spirit.org.server.service.QueryCondition[condition.ordinal()])
                {
                default:
                    break;

                case 1: // '\001'
                    hql.append("and obj.ORGNAME ");
                    hql.append(getSqlOpertionStr(type, notFlag));
                    hql.append(":param").append(i).append("\n");
                    param.put((new StringBuilder()).append("param").append(i).toString(), value);
                    break;

                case 2: // '\002'
                    hql.append("and rela.RELATIONTYPE ");
                    hql.append(getSqlOpertionStr(type, notFlag));
                    if(type == OperationType.IN)
                    {
                        Object types[] = (Object[])(Object[])value;
                        hql.append("(");
                        for(int j = 0; j < types.length; j++)
                        {
                            hql.append(":param").append(i).append(j);
                            param.put((new StringBuilder()).append("param").append(i).append(j).toString(), types[j].toString().toUpperCase());
                            if(j < types.length - 1)
                                hql.append(", ");
                        }

                        hql.append(")\n");
                    } else
                    {
                        hql.append(":param").append(i).append("\n");
                        param.put((new StringBuilder()).append("param").append(i).toString(), value);
                    }
                    break;

                case 3: // '\003'
                    maxLevel = ((Integer)value).intValue() - 1;
                    queryValue[i] = Integer.valueOf(maxLevel);
                    break;

                case 4: // '\004'
                    if(value.getClass().isArray())
                    {
                        Object types[] = (Object[])(Object[])value;
                        if(!notFlag)
                            hql.append("and (( 1=2");
                        else
                            hql.append("and (( 1=1");
                        boolean hasEmployeeCond = false;
                        for(int j = 0; j < types.length; j++)
                        {
                            String typeStr = types[j].toString();
                            if("employee".equalsIgnoreCase(typeStr))
                            {
                                hasEmployeeCond = true;
                                continue;
                            }
                            if(!notFlag)
                                hql.append(" or ");
                            else
                                hql.append(" and ");
                            hql.append("(obj.OBJECTTYPE=").append(ObjectType.UNIT.getEnumItemValue()).append(" and obj.UNITTYPE").append(getSqlOpertionStr(OperationType.EQUALS, notFlag)).append(":param").append(i).append(j).append(")");
                            param.put((new StringBuilder()).append("param").append(i).append(j).toString(), typeStr.toUpperCase());
                        }

                        hql.append(")");
                        if(hasEmployeeCond)
                        {
                            if(!notFlag)
                                hql.append(" or ");
                            else
                                hql.append(" and ");
                            hql.append("obj.OBJECTTYPE").append(getSqlOpertionStr(OperationType.EQUALS, notFlag)).append(ObjectType.EMPLOYEE.getEnumItemValue()).append(" ");
                        }
                        hql.append(")\n");
                        break;
                    }
                    if("employee".equalsIgnoreCase(value.toString()))
                    {
                        hql.append("and obj.OBJECTTYPE").append(getSqlOpertionStr(type, notFlag)).append(ObjectType.EMPLOYEE.getEnumItemValue()).append("\n");
                        break;
                    }
                    if(notFlag)
                        hql.append("and ( (obj.OBJECTTYPE=").append(ObjectType.UNIT.getEnumItemValue()).append(" and obj.UNITTYPE").append(getSqlOpertionStr(type, notFlag)).append(":param").append(i).append(") or obj.OBJECTYPE=").append(ObjectType.EMPLOYEE.getEnumItemValue()).append(" )\n");
                    else
                        hql.append("and (obj.OBJECTTYPE=").append(ObjectType.UNIT.getEnumItemValue()).append(" and obj.UNITTYPE=:param").append(i).append(")\n");
                    param.put((new StringBuilder()).append("param").append(i).toString(), value.toString().toUpperCase());
                    break;
                }
            }

            hql.append("order by rela.RELATIONTYPE, rela.ORDERCODE\n");
            IMetaDBQuery query = template.createSqlQuery(hql.toString());
            query.setParameters(param);
            List result = query.getResult();
            Iterator i$ = result.iterator();
            while(i$.hasNext()) 
            {
                Map row = (Map)i$.next();
                String orgid = (String)row.get("orgid");
                if(!tempResultList.contains(orgid))
                    tempResultList.add(orgid);
            }
        }

        String orgId;
        for(Iterator i$ = tempResultList.iterator(); i$.hasNext(); _resultList.add(template.load(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", orgId)))
            orgId = (String)i$.next();

        if(!tempResultList.isEmpty() && maxLevel > 0)
        {
            AtomicQueryExpressionImpl subQuery = new AtomicQueryExpressionImpl();
            subQuery.expression = expression;
            subQuery.queryDir = queryDir;
            subQuery.queryCondition = queryCondition;
            subQuery.notFlag = this.notFlag;
            subQuery.operationType = operationType;
            subQuery.queryValue = queryValue;
            List innerResultList = new ArrayList();
            subQuery.query(_resultList, innerResultList);
            _resultList.addAll(innerResultList);
        }
        outputList.addAll(_resultList);
    }

    public QueryDir getQueryDir()
    {
        return queryDir;
    }

    public void setQueryDir(QueryDir queryDir)
    {
        this.queryDir = queryDir;
    }

    public QueryCondition[] getQueryCondition()
    {
        return queryCondition;
    }

    public void setQueryCondition(QueryCondition queryCondition[])
    {
        this.queryCondition = queryCondition;
    }

    public OperationType[] getOperationType()
    {
        return operationType;
    }

    public void setOperationType(OperationType operationType[])
    {
        this.operationType = operationType;
    }

    public Object[] getQueryValue()
    {
        return queryValue;
    }

    public void setQueryValue(Object queryValue[])
    {
        this.queryValue = queryValue;
    }

    public boolean[] isNotFlag()
    {
        return notFlag;
    }

    public void setNotFlag(boolean notFlag[])
    {
        this.notFlag = notFlag;
    }

    private String getSqlOpertionStr(OperationType type, boolean notFlag)
    {
        switch(_cls1..SwitchMap.com.sinitek.spirit.org.server.service.OperationType[type.ordinal()])
        {
        case 1: // '\001'
            if(notFlag)
                return "<>";
            else
                return "=";

        case 2: // '\002'
            if(notFlag)
                return "<=";
            else
                return ">";

        case 3: // '\003'
            if(notFlag)
                return "<";
            else
                return ">=";

        case 4: // '\004'
            if(notFlag)
                return ">=";
            else
                return "<";

        case 5: // '\005'
            if(notFlag)
                return ">";
            else
                return "<=";

        case 6: // '\006'
            if(notFlag)
                return "not in ";
            else
                return "in ";

        case 7: // '\007'
            if(notFlag)
                return "not like ";
            else
                return "like ";
        }
        throw new IllegalArgumentException((new StringBuilder()).append("\u4E0D\u652F\u6301\u7684\u64CD\u4F5C\u7B26").append(type).toString());
    }

    private QueryDir queryDir;
    private String expression;
    private QueryCondition queryCondition[];
    private OperationType operationType[];
    private Object queryValue[];
    private boolean notFlag[];
}
