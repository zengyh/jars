// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRadioBoxPluginAware.java

package com.sinitek.spirit.webcontrol.table;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IRadioBoxPluginAware
{

    public abstract Object callRadioBoxPlugin(Map map, Map map1, HttpServletRequest httpservletrequest)
        throws Exception;
}
