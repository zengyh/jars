// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IClassifiedSelectorAware.java

package com.sinitek.spirit.webcontrol.selector;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IClassifiedSelectorAware
{

    public abstract List genClassifiedSelector(Map map, Map map1, HttpServletRequest httpservletrequest)
        throws Exception;
}
