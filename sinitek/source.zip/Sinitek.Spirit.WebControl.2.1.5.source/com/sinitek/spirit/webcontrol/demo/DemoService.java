// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoService.java

package com.sinitek.spirit.webcontrol.demo;

import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package com.sinitek.spirit.webcontrol.demo:
//            IDemoService

public class DemoService
    implements IDemoService
{

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }

    private DemoService()
    {
    }

    public static IDemoService getInstance()
    {
        if(instance == null)
            instance = new DemoService();
        return instance;
    }

    private static IDemoService instance;
    private JdbcTemplate jdbcTemplate;
}
