// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgTypePredicate.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.spirit.org.server.service.OperationType;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            EnumPredicate

public final class OrgTypePredicate extends EnumPredicate
{

    public OrgTypePredicate(boolean notFlag, OperationType operator, Object valueObject)
    {
        super(notFlag, operator, valueObject, true);
    }

    protected String getName()
    {
        return "orgtype";
    }

    public static final String NAME = "orgtype";
}
