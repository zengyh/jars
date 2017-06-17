// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDemoService.java

package com.sinitek.spirit.webcontrol.demo;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IDemoService
{

    public abstract JdbcTemplate getJdbcTemplate();
}
