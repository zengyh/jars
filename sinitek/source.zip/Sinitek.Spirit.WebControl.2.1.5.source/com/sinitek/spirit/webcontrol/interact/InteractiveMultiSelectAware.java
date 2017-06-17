// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InteractiveMultiSelectAware.java

package com.sinitek.spirit.webcontrol.interact;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface InteractiveMultiSelectAware
{

    public abstract Map getData(String as[], HttpServletRequest httpservletrequest);
}
