// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InteractiveSelectAware.java

package com.sinitek.spirit.webcontrol.interact;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface InteractiveSelectAware
{

    public abstract Map getData(String s, HttpServletRequest httpservletrequest);
}
