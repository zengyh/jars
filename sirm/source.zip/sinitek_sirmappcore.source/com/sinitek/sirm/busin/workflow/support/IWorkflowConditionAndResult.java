// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowConditionAndResult.java

package com.sinitek.sirm.busin.workflow.support;

import java.util.Map;

public interface IWorkflowConditionAndResult
{

    public abstract boolean condition(Map map, Map map1);

    public abstract int result(Map map, Map map1);
}
