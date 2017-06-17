// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExportService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.Map;

public interface IWorkflowExportService
{

    public abstract Map getProcessTotal(int i);

    public abstract int putProcessTotal(Map map);

    public abstract int putProcessTotal(Map map, int i);

    public abstract void saveProcess_new(Map map)
        throws Exception;

    public abstract int putProcessModel(Map map);
}
