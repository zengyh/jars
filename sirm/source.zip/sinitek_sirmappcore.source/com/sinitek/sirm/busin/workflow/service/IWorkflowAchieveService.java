// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowAchieveService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.Map;

public interface IWorkflowAchieveService
{

    public abstract Map judgeLinkByModule(Map map);

    public abstract Map doTaskByModule(Map map);
}
