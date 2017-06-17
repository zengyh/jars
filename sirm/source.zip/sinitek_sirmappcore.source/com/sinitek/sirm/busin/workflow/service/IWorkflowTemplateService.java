// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowTemplateService.java

package com.sinitek.sirm.busin.workflow.service;

import java.util.List;
import java.util.Map;

public interface IWorkflowTemplateService
{

    public abstract List doSpecialOperate(List list);

    public abstract List doOrdinaryOperate(List list);

    public abstract void doStepAftDoOperate(Map map);

    public abstract void doStepPreDoOperate(List list);
}
