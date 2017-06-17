// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AtomicQueryExpressionFactory.java

package com.sinitek.spirit.org.server.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException, AtomicQueryExpressionImpl, QueryCondition, OperationType, 
//            QueryDir, IAtomicQueryExpression

public class AtomicQueryExpressionFactory
{

    public AtomicQueryExpressionFactory()
    {
    }

    public static List parseExpressions(String queryStr, Logger logger)
    {
        String atomicQuerys[] = StringUtils.split(queryStr, ":");
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("\u4ECE\u67E5\u8BE2\u8868\u8FBE\u5F0F[").append(queryStr).append("]\u4E2D\u5206\u79BB\u5F97\u5230[").append(atomicQuerys.length).append("]\u4E2A\u539F\u5B50\u67E5\u8BE2\u8868\u8FBE\u5F0F").toString());
        List ret = new ArrayList(atomicQuerys.length);
        String arr$[] = atomicQuerys;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String atomicExp = arr$[i$];
            ret.add(parseExpression(atomicExp.trim(), logger));
        }

        return ret;
    }

    public static IAtomicQueryExpression parseExpression(String atomicQueryStr, Logger logger)
    {
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("\u5F00\u59CB\u89E3\u6790\u539F\u5B50\u67E5\u8BE2\u8868\u8FBE\u5F0F[").append(atomicQueryStr).append("]").toString());
        if(StringUtils.isBlank(atomicQueryStr))
            throw new OrgServiceException("1011");
        atomicQueryStr = atomicQueryStr.trim();
        AtomicQueryExpressionImpl expression = new AtomicQueryExpressionImpl();
        expression.setExpression(atomicQueryStr);
        int conditionStartPos = atomicQueryStr.indexOf('(');
        if(conditionStartPos < 0)
            throw new OrgServiceException("1012", new Object[] {
                atomicQueryStr
            });
        int conditionEndPos = atomicQueryStr.lastIndexOf(')');
        if(conditionEndPos < 0)
            throw new OrgServiceException("1013", new Object[] {
                atomicQueryStr
            });
        if(conditionEndPos != atomicQueryStr.length() - 1)
            throw new OrgServiceException("1014", new Object[] {
                atomicQueryStr
            });
        String strQueryDir = atomicQueryStr.substring(0, conditionStartPos);
        strQueryDir = StringUtils.replace(strQueryDir, " ", "");
        if("UP".equalsIgnoreCase(strQueryDir))
        {
            if(logger.isDebugEnabled())
                logger.debug((new StringBuilder()).append("\u539F\u5B50\u67E5\u8BE2[").append(atomicQueryStr).append("]\u88AB\u89E3\u6790\u4E3A\u5411\u4E0A\u67E5\u8BE2").toString());
            expression.setQueryDir(QueryDir.UP);
        } else
        if("DOWN".equalsIgnoreCase(strQueryDir))
        {
            if(logger.isDebugEnabled())
                logger.debug((new StringBuilder()).append("\u539F\u5B50\u67E5\u8BE2[").append(atomicQueryStr).append("]\u88AB\u89E3\u6790\u4E3A\u5411\u4E0B\u67E5\u8BE2").toString());
            expression.setQueryDir(QueryDir.DOWN);
        } else
        {
            throw new OrgServiceException("1015", new Object[] {
                atomicQueryStr, strQueryDir
            });
        }
        String strCondition = atomicQueryStr.substring(conditionStartPos + 1, conditionEndPos);
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("\u5F00\u59CB\u89E3\u6790\u67E5\u8BE2\u6761\u4EF6[").append(strCondition).append("]").toString());
        int conditionCount = 0;
        String conditions[] = new String[0];
        if(StringUtils.isNotBlank(strCondition))
        {
            conditions = StringUtils.split(strCondition, ";");
            conditionCount = conditions.length;
            if(logger.isDebugEnabled())
                logger.debug((new StringBuilder()).append("\u67E5\u8BE2\u6761\u4EF6[").append(strCondition).append("]\u5305\u542B[").append(conditionCount).append("]\u4E2A\u67E5\u8BE2\u5B50\u6761\u4EF6").toString());
        }
        QueryCondition queryCondition[] = new QueryCondition[conditionCount];
        OperationType operationType[] = new OperationType[conditionCount];
        Object queryValue[] = new Object[conditionCount];
        boolean notFlag[] = new boolean[conditionCount];
        for(int i = 0; i < conditionCount; i++)
        {
            String singleCondition = conditions[i].trim();
            if(logger.isDebugEnabled())
                logger.debug((new StringBuilder()).append("\u5F00\u59CB\u89E3\u6790\u5355\u4E00\u67E5\u8BE2\u6761\u4EF6[").append(singleCondition).append("]").toString());
            StringBuffer buffer = new StringBuffer();
            int condStatus = 0;
            char arr$[] = (new StringBuilder()).append(singleCondition).append("\0").toString().toCharArray();
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                char ch = arr$[i$];
                if(ch == 0 && condStatus == 2)
                {
                    dealToken(atomicQueryStr, queryCondition, operationType, queryValue, notFlag, i, buffer, condStatus);
                    continue;
                }
                if(ch == ' ' && condStatus != 2)
                {
                    condStatus = dealToken(atomicQueryStr, queryCondition, operationType, queryValue, notFlag, i, buffer, condStatus);
                    continue;
                }
                if(ch == '!' || ch == '=' || ch == '>' || ch == '<')
                {
                    switch(ch)
                    {
                    default:
                        break;

                    case 33: // '!'
                        notFlag[i] = true;
                        break;

                    case 61: // '='
                        if(operationType[i] == OperationType.GREATER)
                        {
                            operationType[i] = OperationType.GREATER_OR_EQUAL;
                            break;
                        }
                        if(operationType[i] == OperationType.LESS)
                            operationType[i] = OperationType.LESS_OR_EQUAL;
                        else
                            operationType[i] = OperationType.EQUALS;
                        break;

                    case 62: // '>'
                        operationType[i] = OperationType.GREATER;
                        break;

                    case 60: // '<'
                        operationType[i] = OperationType.LESS;
                        break;
                    }
                    dealToken(atomicQueryStr, queryCondition, operationType, queryValue, notFlag, i, buffer, condStatus);
                    condStatus = 1;
                    continue;
                }
                buffer.append(ch);
                if(condStatus == 1 && operationType[i] != null)
                    condStatus = 2;
            }

            if(logger.isDebugEnabled())
                logger.debug((new StringBuilder()).append("\u5B8C\u6210\u89E3\u6790\u5355\u4E00\u67E5\u8BE2\u6761\u4EF6[").append(singleCondition).append("]").toString());
        }

        expression.setQueryCondition(queryCondition);
        expression.setNotFlag(notFlag);
        expression.setQueryValue(queryValue);
        expression.setOperationType(operationType);
        checkExpressConditionSupport(expression);
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("\u5B8C\u6210\u89E3\u6790\u67E5\u8BE2\u6761\u4EF6[").append(strCondition).append("]").toString());
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("\u5B8C\u6210\u89E3\u6790\u539F\u5B50\u67E5\u8BE2\u8868\u8FBE\u5F0F[").append(atomicQueryStr).append("]").toString());
        return expression;
    }

    private static void checkExpressConditionSupport(AtomicQueryExpressionImpl expression)
    {
        for(int i = 0; i < expression.getQueryCondition().length; i++)
        {
            OperationType operationType = expression.getOperationType()[i];
            QueryCondition queryCondition = expression.getQueryCondition()[i];
            static class _cls1
            {

                static final int $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[];

                static 
                {
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
                        $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.ORGTYPE.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError ex) { }
                    try
                    {
                        $SwitchMap$com$sinitek$spirit$org$server$service$QueryCondition[QueryCondition.LEVEL.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError ex) { }
                }
            }

            switch(_cls1..SwitchMap.com.sinitek.spirit.org.server.service.QueryCondition[queryCondition.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(operationType == OperationType.LESS || operationType == OperationType.LESS_OR_EQUAL || operationType == OperationType.GREATER || operationType == OperationType.GREATER_OR_EQUAL)
                    throw new OrgServiceException("1025", new Object[] {
                        expression.getExpression(), queryCondition, operationType
                    });
                break;

            case 2: // '\002'
                if(operationType == OperationType.LESS || operationType == OperationType.LESS_OR_EQUAL || operationType == OperationType.LIKE || operationType == OperationType.GREATER || operationType == OperationType.GREATER_OR_EQUAL)
                    throw new OrgServiceException("1025", new Object[] {
                        expression.getExpression(), queryCondition, operationType
                    });
                break;

            case 3: // '\003'
                if(operationType == OperationType.LESS || operationType == OperationType.LESS_OR_EQUAL || operationType == OperationType.LIKE || operationType == OperationType.GREATER || operationType == OperationType.GREATER_OR_EQUAL)
                    throw new OrgServiceException("1025", new Object[] {
                        expression.getExpression(), queryCondition, operationType
                    });
                break;

            case 4: // '\004'
                if(operationType == OperationType.IN || operationType == OperationType.LIKE)
                    throw new OrgServiceException("1025", new Object[] {
                        expression.getExpression(), queryCondition, operationType
                    });
                break;
            }
        }

    }

    private static int dealToken(String atomicQueryStr, QueryCondition queryCondition[], OperationType operationType[], Object queryValue[], boolean notFlag[], int i, StringBuffer buffer, int condStatus)
    {
        if(buffer.length() == 0)
            return condStatus;
        String token = buffer.toString();
        buffer.delete(0, buffer.length());
        if("NOT".equalsIgnoreCase(token))
        {
            if(condStatus != 1)
                throw new OrgServiceException("1016", new Object[] {
                    atomicQueryStr
                });
            notFlag[i] = true;
        } else
        {
            switch(condStatus)
            {
            default:
                break;

            case 0: // '\0'
                try
                {
                    queryCondition[i] = QueryCondition.valueOf(token.toUpperCase());
                    condStatus = 1;
                }
                catch(IllegalArgumentException iae)
                {
                    throw new OrgServiceException("1017", iae, new Object[] {
                        atomicQueryStr, token
                    });
                }
                break;

            case 1: // '\001'
                try
                {
                    operationType[i] = OperationType.valueOf(token.toUpperCase());
                    condStatus = 2;
                }
                catch(IllegalArgumentException iae)
                {
                    throw new OrgServiceException("1018", iae, new Object[] {
                        atomicQueryStr, token
                    });
                }
                break;

            case 2: // '\002'
                if(StringUtils.isBlank(token))
                    throw new OrgServiceException("1019", new Object[] {
                        atomicQueryStr
                    });
                if(token.startsWith("["))
                {
                    if(token.endsWith("]"))
                    {
                        String tokens[] = StringUtils.split(token.substring(1, token.length() - 1), ",");
                        Object values[] = new Object[tokens.length];
                        for(int j = 0; j < tokens.length; j++)
                            values[j] = parseQueryValue(atomicQueryStr, tokens[j]);

                        queryValue[i] = ((Object) (values));
                        if(operationType[i] != OperationType.IN)
                            throw new OrgServiceException("1023", new Object[] {
                                atomicQueryStr, token
                            });
                    } else
                    {
                        throw new OrgServiceException("1022", new Object[] {
                            atomicQueryStr, token
                        });
                    }
                    break;
                }
                if(operationType[i] == OperationType.IN)
                    throw new OrgServiceException("1024", new Object[] {
                        atomicQueryStr, token
                    });
                queryValue[i] = parseQueryValue(atomicQueryStr, token);
                break;
            }
        }
        return condStatus;
    }

    private static Object parseQueryValue(String atomicQueryExp, String token)
    {
        token = token.trim();
        if(token.startsWith("'"))
            if(!token.endsWith("'"))
                throw new OrgServiceException("1021", new Object[] {
                    atomicQueryExp, token
                });
            else
                return token.substring(1, token.length() - 1).trim();
        if("all".equalsIgnoreCase(token))
            return Integer.valueOf(0x7fffffff);
        try
        {
            return Integer.valueOf(Integer.parseInt(token));
        }
        catch(NumberFormatException nfe)
        {
            throw new OrgServiceException("1022", new Object[] {
                atomicQueryExp, token
            });
        }
    }
}
