// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PredicateFactory.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.OperationType;
import com.sinitek.spirit.org.server.service.QueryCondition;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            OrgNamePredicate, RelationTypePredicate, OrgTypePredicate, Predicate

class PredicateFactory
{

    PredicateFactory()
    {
    }

    public static Predicate newPredicate(QueryCondition variable, boolean notFlag, OperationType operator, Object valueObject)
    {
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
            }
        }

        switch(_cls1..SwitchMap.com.sinitek.spirit.org.server.service.QueryCondition[variable.ordinal()])
        {
        case 1: // '\001'
            return new OrgNamePredicate(notFlag, operator, valueObject);

        case 2: // '\002'
            return new RelationTypePredicate(notFlag, operator, valueObject);

        case 3: // '\003'
            return new OrgTypePredicate(notFlag, operator, valueObject);
        }
        return null;
    }
}
