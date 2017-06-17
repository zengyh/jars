// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Constant.java

package com.sinitek.sirm.common.quartz.service.impl;

import java.util.HashMap;
import java.util.Map;

public class Constant
{

    public Constant()
    {
    }

    public static final String TRIGGERNAME = "triggerName";
    public static final String TRIGGERGROUP = "triggerGroup";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String REPEATCOUNT = "repeatCount";
    public static final String REPEATINTERVEL = "repeatInterval";
    public static final Map status;

    static 
    {
        status = new HashMap();
        status.put("ACQUIRED", "\u8FD0\u884C\u4E2D");
        status.put("PAUSED", "\u6682\u505C\u4E2D");
        status.put("WAITING", "\u7B49\u5F85\u4E2D");
    }
}
