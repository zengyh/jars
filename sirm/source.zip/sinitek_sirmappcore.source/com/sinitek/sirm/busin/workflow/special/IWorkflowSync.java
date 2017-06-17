// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowSync.java

package com.sinitek.sirm.busin.workflow.special;

import java.util.Map;

public interface IWorkflowSync
{

    public abstract Map syncTask(Map map);

    public abstract Map syncStep(Map map);

    public abstract Map syncProcess(Map map);
}
