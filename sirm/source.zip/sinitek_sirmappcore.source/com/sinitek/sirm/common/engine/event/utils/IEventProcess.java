// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEventProcess.java

package com.sinitek.sirm.common.engine.event.utils;

import java.io.Serializable;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            AbstractEventObject

public interface IEventProcess
    extends Serializable
{

    public abstract void process(AbstractEventObject abstracteventobject);
}
