// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISpiritUserConfigAware.java

package com.sinitek.spirit.webcontrol.userconfig;

import javax.servlet.http.HttpServletRequest;

public interface ISpiritUserConfigAware
{

    public abstract Integer loadPageSize(String s, String s1, HttpServletRequest httpservletrequest);

    public abstract void savePageSize(String s, String s1, Integer integer, HttpServletRequest httpservletrequest);
}
