// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowMobileService.java

package com.sinitek.sirm.tool.workflow.special;

import java.util.List;
import java.util.Map;

public interface IWorkflowMobileService
{

    public abstract List getUndoExampleTask(String s, Map map);

    public abstract int getUndoExampleTaskNum(String s);

    public abstract void dealExampleTask(Map map);

    public abstract List getDoneExampleTask(String s, Map map);

    public abstract List getExampleInfo(Map map);
}
