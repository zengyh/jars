// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgQuery.java

package com.sinitek.spirit.org.server.cache;

import java.util.List;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            PathExpression

public interface OrgQuery
{

    public abstract String getFromObjectId();

    public abstract PathExpression getPathExpression();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    public abstract List getResult();
}
