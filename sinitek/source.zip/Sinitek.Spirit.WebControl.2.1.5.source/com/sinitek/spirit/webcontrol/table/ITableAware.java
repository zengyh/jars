// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITableAware.java

package com.sinitek.spirit.webcontrol.table;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface ITableAware
{

    public abstract Object getResult(Map map, HttpServletRequest httpservletrequest)
        throws Exception;

    public abstract String setDefaultOrderBy(Map map, HttpServletRequest httpservletrequest)
        throws Exception;
}
