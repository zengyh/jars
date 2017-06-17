// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowTranslateService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.Map;

public interface IWorkflowTranslateService
{

    public abstract int exportProcess(int i, int j);

    public abstract int importProcess(String s);

    public abstract int saveHistoryExampleData(Map map);
}
