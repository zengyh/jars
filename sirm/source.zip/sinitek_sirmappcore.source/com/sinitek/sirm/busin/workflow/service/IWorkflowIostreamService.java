// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowIostreamService.java

package com.sinitek.sirm.busin.workflow.service;

import java.io.InputStream;
import java.util.Map;

public interface IWorkflowIostreamService
{

    public abstract Map TxttoMap(String s);

    public abstract Map InputStreamToMap(InputStream inputstream);

    public abstract void MaptoTxt(Map map, String s);
}
