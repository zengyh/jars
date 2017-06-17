// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgMobileService.java

package com.sinitek.sirm.org.busin.service;

import java.util.List;
import java.util.Map;

public interface IOrgMobileService
{

    public abstract List findAllEmployees();

    public abstract List findAllEmployees(Map map);

    public abstract List getuserlist(Map map);

    public abstract int updateEmployeeYdsbh(Map map);
}
