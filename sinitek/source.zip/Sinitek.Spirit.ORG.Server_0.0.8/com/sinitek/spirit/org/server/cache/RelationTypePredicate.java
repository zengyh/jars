// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelationTypePredicate.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.OperationType;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            EnumPredicate

public final class RelationTypePredicate extends EnumPredicate
{

    public RelationTypePredicate(boolean notFlag, OperationType operator, Object valueObject)
    {
        super(notFlag, operator, valueObject, false);
    }

    protected String getName()
    {
        return "relationshiptype";
    }

    public static final String NAME = "relationshiptype";
}
