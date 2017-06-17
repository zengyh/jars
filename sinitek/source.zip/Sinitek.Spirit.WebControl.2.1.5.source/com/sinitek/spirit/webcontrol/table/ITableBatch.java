// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITableBatch.java

package com.sinitek.spirit.webcontrol.table;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface ITableBatch
{

    public abstract List tableBatch(List list, Map map, HttpServletRequest httpservletrequest)
        throws Exception;
}
