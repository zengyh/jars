// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IChartAware.java

package com.sinitek.spirit.webcontrol.chart;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IChartAware
{

    public abstract Object getChartResult(Map map, Map map1, HttpServletRequest httpservletrequest)
        throws Exception;
}
