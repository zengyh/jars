// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestEventProcess.java

package com.sinitek.sirm.common.engine.event.test;

import com.sinitek.sirm.common.engine.event.utils.*;
import java.io.PrintStream;

public class TestEventProcess
    implements IEventProcess
{

    public TestEventProcess()
    {
    }

    public void process(AbstractEventObject e)
    {
        e.getEventTester().writeMessage("class TestEventProcess process....");
        Object str = e.getData("String");
        Object obj = e.getData("Object");
        Object list = e.getData("List");
        System.out.print((new StringBuilder()).append("String:").append(str).toString());
        System.out.print((new StringBuilder()).append("Object:").append(obj).toString());
        System.out.print((new StringBuilder()).append("List:").append(list).toString());
    }
}
