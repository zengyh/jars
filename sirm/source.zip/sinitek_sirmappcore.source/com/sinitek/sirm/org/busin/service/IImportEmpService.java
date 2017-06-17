// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IImportEmpService.java

package com.sinitek.sirm.org.busin.service;

import java.util.List;

public interface IImportEmpService
{

    public abstract String saveEmps(List list);

    public abstract boolean checkEmpName(String s);
}
