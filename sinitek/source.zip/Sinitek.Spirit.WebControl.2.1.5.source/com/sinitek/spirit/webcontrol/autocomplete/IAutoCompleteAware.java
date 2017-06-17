// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAutoCompleteAware.java

package com.sinitek.spirit.webcontrol.autocomplete;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IAutoCompleteAware
{

    public abstract List match(String s, Map map, Map map1, HttpServletRequest httpservletrequest)
        throws Exception;
}
